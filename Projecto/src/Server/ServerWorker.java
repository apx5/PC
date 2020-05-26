package Server;

import Exceptions.UserTakenException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ServerWorker implements Runnable{

    private Socket socket;
    private DataBase db;
    private String user;
    private Thread t;


    public ServerWorker(Socket socket,DataBase db){
        this.socket = socket;
        this.db = db;
        this.user = "";
        this.t = null;
    }


    private void menu(BufferedReader in,PrintWriter out) throws IOException, InterruptedException {
        String username, password, region, s,new_cases;

        do{
            s = in.readLine();
            if (s == null) break; // para evitar o nullpointerException quando termina a thread
            switch (s) {
                case "1":
                    username = in.readLine();
                    password = in.readLine();

                    if (db.check_login(username, password,out)) {
                        this.user = username;
                        out.println("ok");
                        t = new Thread(new Multicast(out,db));
                        t.start();
                    } else {
                        out.println("Nok");
                    }
                    out.flush();
                    break;
                case "2":
                    username = in.readLine();
                    password = in.readLine();
                    region = in.readLine();
                    if (db.registerClient(username, password, region,out)) {
                        this.user = username;
                        out.println("ok");
                        t = new Thread(new Multicast(out,db));
                        t.start();
                    } else {
                        out.println("Nok");
                    }
                    out.flush();
                    break;
                case "3":
                    new_cases = in.readLine();
                    int cases = Integer.parseInt(new_cases);
                    if(db.updateCases(this.user,cases)){
                        out.println("Cases updated.");
                        out.flush();
                        db.lock.lock();
                        db.msgCounter++;
                        db.multicast_wait.signalAll();
                        db.lock.unlock();
                    } else {
                        out.println("Case limit reached!");
                        out.flush();
                    }
                    break;
                case "4":
                    out.println(db.getAverage() + " average.");
                    out.flush();
                    break;
                case "5":
                    out.println(db.checkCasesByRegion(user) + " cases in your region.");
                    out.flush();
                    break;
            }

        }while(!s.equals("0"));
    }

    @Override
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            menu(in,out);

            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();

        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}

