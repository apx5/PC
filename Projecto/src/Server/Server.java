package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import Exceptions.*;

public class Server {

    private int port;
    private ArrayList<InfoClient> client_registers;
    private HashMap<String, Integer> case_registers;
    private int total_cases;

    public Server(int port) {
        this.port = port;
        this.total_cases = 0;
        this.client_registers = new ArrayList<InfoClient>();
        this.case_registers = new HashMap<String, Integer>();
    }

    public int getPort() {
        return port;
    }

    public int getTotal_cases() {
        return total_cases;
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

    public void setPort(int port) {
        this.port = port;
    }

    public void setClient_registers(ArrayList<InfoClient> client_registers) {
        this.client_registers = client_registers;
    }

    public void setCase_registers(HashMap<String, Integer> case_registers) {
        this.case_registers = case_registers;
    }

    public synchronized void registerClient(String user, String pass, String region) throws UserTakenException {
        for (InfoClient c : client_registers) {
            if (c.getUsername().equals(user)) {
                throw new UserTakenException();
            }
        }
        InfoClient cl = new InfoClient(user, pass, region);
        client_registers.add(cl);
        case_registers.put(user,0);
    }

    public synchronized int updateCases(String user,int x){
        int c = case_registers.get(user);
        case_registers.put(user,x+c);
        total_cases += x;
        return total_cases;
    }

}