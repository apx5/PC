package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.locks.Condition;

public class Server {

    private final int port;
    private DataBase db;
    private ServerSocket serverSocket;
    private boolean goOff;
    private Socket cl_socket;

    public Server(int port, DataBase db) throws IOException {
        this.port = port;
        this.db = db;
        this.goOff = false;
        this.cl_socket = null;
    }

    public void startServer(){
        try{
            System.out.println("The server is ready to accept connections!");
             this.serverSocket = new ServerSocket(this.port);
             Thread t = new Thread(new ServerListener());
             t.start();

             while(!goOff){
                     cl_socket = serverSocket.accept();
                     new Thread(new ServerWorker(cl_socket,db)).start();
            }
        } catch (SocketException e){}
          catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("The system is going down!");
    }

    public class ServerListener implements Runnable{
        public void ServerListener(){}

        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        public void run(){
            String message;
            try{
                while((message = systemIn.readLine()) != null && !message.equalsIgnoreCase("exit"));
                goOff = true;
                serverSocket.close();
            }
            catch (SocketException e){}
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}