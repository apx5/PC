package Server;

import Exceptions.UserTakenException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {
    private ArrayList<InfoClient> client_registers;
    private HashMap<String, Integer> case_registers;
    private int total_cases;

    public DataBase() {
        this.case_registers = new HashMap<String, Integer>();
        this.client_registers = new ArrayList<InfoClient>();
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

    public synchronized boolean registerClient(String user, String pass, String region) {
        for (InfoClient c : client_registers) {
            if (c.getUsername().equals(user)) {
                return false;
            }
        }
        InfoClient cl = new InfoClient(user, pass, region);
        client_registers.add(cl);
        case_registers.put(user, 0);
        return true;
    }

    public synchronized boolean updateCases(String user, int x) {
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

    public boolean check_login(String user, String pass) {
        InfoClient client;
        for (InfoClient cl : client_registers) {
            if (cl.getUsername().equals(user)) {
                client = cl;
                if (client.getPassword().equals(pass)) {
                    return true;
                } else return false;
            }
        }
        return false;
    }
}

