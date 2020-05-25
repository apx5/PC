package Server;

import Exceptions.UserTakenException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DataBase {
    private ArrayList<InfoClient> client_registers;
    private HashMap<String, Integer> case_registers;
    private HashMap<String, PrintWriter> user_pw;
    private int total_cases;
    ReentrantLock lock;

    public DataBase() {
        this.case_registers = new HashMap<String, Integer>();
        this.client_registers = new ArrayList<InfoClient>();
        this.user_pw = new HashMap<String, PrintWriter>();
        this.total_cases = 0;
        this.lock = new ReentrantLock();
    }

    public float getAverage() {
        return total_cases/(float)(150*client_registers.size());
    }

    public boolean registerClient(String user, String pass, String region,PrintWriter out) throws InterruptedException {
        lock.lock();
        for (InfoClient c : client_registers) {
            if (c.getUsername().equals(user)) {
                lock.unlock();
                return false;
            }
        }
        // TimeUnit.SECONDS.sleep(30); //Para testar os problemas de concorrência
        InfoClient cl = new InfoClient(user, pass, region);
        client_registers.add(cl);
        //lock.unlock(); //talvez nao seja necessário bloquear os case_registers
        case_registers.put(user, 0);
        //lock.unlock();?????
        user_pw.put(user, out);
        lock.unlock();
        return true;
    }

    public synchronized void disconnectUser(String user){
        this.user_pw.remove(user); // se um cliente se desconectar e houver mensagens a ser difundida
    }

    public void multicast(){
        float msg = getAverage();
        //lock.unlock(); //temos de bloquear o user_pw pois alguem pode ser removido enquanto há mensagens a ser enviadas?
        for(PrintWriter pw : user_pw.values()){
            pw.println("The average has been updated to " + msg);
            pw.flush();
        }
        //lock.unlock();
    }

    public boolean updateCases(String user, int x) { // Precisa de ser revista a concorrência
        int c = case_registers.get(user);
        if(x+c <= 150) {
            lock.lock();
            case_registers.put(user, x + c);
            total_cases += x;
            lock.unlock();
            return true;
        }
        return false;
    }

    public int checkCasesByRegion(String username){
        int ret = 0; String user,region = "";
        for(InfoClient c : client_registers){
            if(c.getUsername().equals(username)){
                region = c.getRegion();
            }
        }
        lock.lock(); //temos de bloquear tudo para garantir que nao entram novos registos enquanto percorre o array
        for(InfoClient c : client_registers){
            if(c.getRegion().equals(region)){
                user = c.getUsername();
                ret+= case_registers.get(user);
            }
        }
        lock.unlock();
        return ret;
    }

    public boolean check_login(String user, String pass,PrintWriter out) {
        InfoClient client;
        if(client_registers.size() > 0) {
            for (InfoClient cl : client_registers) {
                if (cl.getUsername().equals(user)) {
                    client = cl;
                    if (client.getPassword().equals(pass)) {
                        lock.lock(); //será que é mesmo preciso bloquear user_pw?
                        user_pw.put(user, out);
                        lock.unlock();
                        return true;
                    } else return false;
                }
            }
        }
        return false;
    }
}

