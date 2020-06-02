package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class ServerWorker implements Runnable{

    private Socket socket;
    private DataBase db;
    private String user;
    private Thread t;
    private ReentrantLock lock;
    private PrintWriter out;


    public ServerWorker(Socket socket,DataBase db){
        this.socket = socket;
        this.db = db;
        this.user = "";
        this.t = null;
        this.lock = new ReentrantLock();
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
                        lock.lock();
                        out.println("ok");
                        lock.unlock();
                        t = new Thread(new Multicast());
                        t.start();
                    } else {
                        lock.lock();
                        out.println("Nok");
                        lock.unlock();
                    }
                    out.flush();
                    break;
                case "2":
                    username = in.readLine();
                    password = in.readLine();
                    region = in.readLine();
                    if (db.registerClient(username, password, region,out)) {
                        this.user = username;
                        lock.lock();
                        out.println("ok");
                        lock.unlock();
                        t = new Thread(new Multicast());
                        t.start();
                    } else {
                        lock.lock();
                        out.println("Nok");
                        lock.unlock();
                    }
                    out.flush();
                    break;
                case "3":
                    new_cases = in.readLine();
                    int cases = Integer.parseInt(new_cases);
                    if(db.updateCases(this.user,cases)){
                        lock.lock();
                        out.println("Cases updated.");
                        out.flush();
                        lock.unlock();
                        db.lock.lock();
                        db.msgCounter++;
                        db.multicast_wait.signalAll();
                        db.lock.unlock();
                    } else {
                        lock.lock();
                        out.println("Case limit reached!");
                        out.flush();
                        lock.unlock();
                    }
                    break;
                case "4":
                    lock.lock();
                    out.println(db.getAverage() + " average.");
                    out.flush();
                    lock.unlock();
                    break;
                case "5":
                    lock.lock();
                    out.println(db.checkCasesByRegion(user) + " cases in your region.");
                    out.flush();
                    lock.unlock();
                    break;
            }

        }while(!s.equals("0"));
    }

    @Override
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            menu(in,out);

            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();

        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public class Multicast implements Runnable { // criada para controlar a escrita por duas threads no mesmo PrintWritter do cliente
        private int counter;

        public Multicast() {
            this.counter = db.msgCounter;
        }

        @Override
        public void run() {
            while (true) {
                while (counter == db.msgCounter) {
                    try {
                        db.lock.lock();
                        db.multicast_wait.await();
                        db.lock.unlock();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.lock();
                out.println("Average has been updated to " + db.getAverage() + ".");
                out.flush();
                counter = db.msgCounter;
                lock.unlock();
            }
        }
    }
}

