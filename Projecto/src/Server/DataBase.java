package Server;

import Exceptions.UserTakenException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class DataBase {
    private ArrayList<InfoClient> client_registers;
    private HashMap<String, Integer> case_registers;
    private HashMap<String, PrintWriter> user_pw;
    private int total_cases;

    public DataBase() {
        this.case_registers = new HashMap<String, Integer>();
        this.client_registers = new ArrayList<InfoClient>();
        this.user_pw = new HashMap<String, PrintWriter>();
        this.total_cases = 0;
    }

    public float getTotal_cases() {
        return total_cases/(float)(150*client_registers.size());
    }

    public void setTotal_cases(int total_cases) {
        this.total_cases = total_cases;
    }

    public ArrayList<InfoClient> getClient_registers() {
        return client_registers;
    }

    public HashMap<String, Integer> getCase_registers() {
        return case_registers;
    }

    public void setClient_registers(ArrayList<InfoClient> client_registers) {
        this.client_registers = client_registers;
    }

    public void setCase_registers(HashMap<String, Integer> case_registers) {
        this.case_registers = case_registers;
    }

    public boolean registerClient(String user, String pass, String region,PrintWriter out) throws InterruptedException {
        synchronized (client_registers){
            for (InfoClient c : client_registers) {
                if (c.getUsername().equals(user)) {
                    return false;
                }
            }
            // TimeUnit.SECONDS.sleep(30); //Para testar os problemas de concurrência
            InfoClient cl = new InfoClient(user, pass, region);
            client_registers.add(cl);
        };
        synchronized (case_registers) {case_registers.put(user, 0);}
        synchronized (user_pw) {user_pw.put(user, out);}
        return true;
    }

    public void disconnectUser(String user){
        this.user_pw.remove(user);
    }

    public void multicast(){
        float msg = getTotal_cases();
        for(PrintWriter pw : user_pw.values()){
            pw.println("The average has been updated to " + msg);
            pw.flush();
        }
    }

    public synchronized boolean updateCases(String user, int x) { // Precisa de ser revista a concorrência
        synchronized (case_registers){
        }
        int c = case_registers.get(user);
        if(x+c <= 150) {
            case_registers.put(user, x + c);
            total_cases += x;
            return true;
        }
        return false;
    }

    public synchronized int checkCasesByRegion(String username){
        int ret = 0; String user,region = "";
        for(InfoClient c : client_registers){
            if(c.getUsername().equals(username)){
                region = c.getRegion();
            }
        }

        for(InfoClient c : client_registers){
            if(c.getRegion().equals(region)){
                user = c.getUsername();
                ret+= case_registers.get(user);
            }
        }
        return ret;
    }

    public boolean check_login(String user, String pass,PrintWriter out) {
        InfoClient client;
        if(client_registers.size() > 0) {
            for (InfoClient cl : client_registers) {
                if (cl.getUsername().equals(user)) {
                    client = cl;
                    if (client.getPassword().equals(pass)) {
                        synchronized (user_pw) {user_pw.put(user, out);}
                        return true;
                    } else return false;
                }
            }
        }

        return false;
    }
}

