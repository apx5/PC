package Server;

import java.io.PrintWriter;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Multicast implements Runnable{
    private PrintWriter pw;
    private DataBase db;
    private int counter;

    public Multicast(PrintWriter pw, DataBase db){
        this.pw = pw;
        this.db = db;
        this.counter = db.msgCounter;
    }

    @Override
    public void run(){
        while (true){
            while(counter == db.msgCounter){
                try {
                    db.lock.lock();
                    db.multicast_wait.await();
                    db.lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            pw.println(db.getAverage() + " average cases.");
            pw.flush();
            counter = db.msgCounter;
        }

    }



}

