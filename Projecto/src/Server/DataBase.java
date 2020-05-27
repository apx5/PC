package Server;

import Exceptions.UserTakenException;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class DataBase implements Serializable {
    private ArrayList<InfoClient> client_registers;
    private HashMap<String, Integer> case_registers;
    private int total_cases;
    ReentrantLock lock_clientCases;
    ReentrantLock lock_clientRegisters;
    ReadWriteLock rwLock;
    ReentrantLock lock;
    public int msgCounter;
    Condition multicast_wait;

    public DataBase() {
        this.case_registers = new HashMap<String, Integer>();
        this.client_registers = new ArrayList<InfoClient>();
        this.total_cases = 0;
        this.lock_clientCases = new ReentrantLock();
        this.lock_clientRegisters = new ReentrantLock();
        this.msgCounter = 0;
        this.lock = new ReentrantLock();
        this.multicast_wait = lock.newCondition();
        this.rwLock = new ReentrantReadWriteLock();
    }

    public synchronized float getAverage() {
        return total_cases/(float)(150*client_registers.size());
    }//?????

    public boolean registerClient(String user, String pass, String region,PrintWriter out) throws InterruptedException {
        lock_clientRegisters.lock();
        for (InfoClient c : client_registers) {
            if (c.getUsername().equals(user)) {
                lock_clientRegisters.unlock();
                return false;
            }
        }
        InfoClient cl = new InfoClient(user, pass, region);
        client_registers.add(cl);
        //talvez nao seja necessário bloquear os case_registers
        case_registers.put(user, 0);
        lock_clientRegisters.unlock();
        return true;
    }

    public boolean updateCases(String user, int x) { // Precisa de ser revista a concorrência
        int c = case_registers.get(user);
        if(x+c <= 150) {
            lock_clientRegisters.lock();//?????
            case_registers.put(user, x + c);
            total_cases += x;
            lock_clientRegisters.unlock();
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
        lock_clientRegisters.lock(); //temos de bloquear tudo para garantir que nao entram novos registos enquanto percorre o array
        lock_clientCases.lock();
        for(InfoClient c : client_registers){
            if(c.getRegion().equals(region)){
                user = c.getUsername();
                ret+= case_registers.get(user);
            }
        }
        lock_clientRegisters.unlock();
        lock_clientCases.unlock();
        return ret;
    }

    public boolean check_login(String user, String pass,PrintWriter out) {
        InfoClient client;
        if(client_registers.size() > 0) {
            for (InfoClient cl : client_registers) {
                if (cl.getUsername().equals(user)) {
                    client = cl;
                    if (client.getPassword().equals(pass)) {
                        return true;
                    } else return false;
                }
            }
        }
        return false;
    }
}

