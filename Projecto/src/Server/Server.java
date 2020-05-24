package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final int port;
    private DataBase db;
    private ServerSocket serverSocket;

    public Server(int port, DataBase db) throws IOException {
        this.port = port;
        this.db = db;
    }

    public void startServer(){
        int workerCounter = 1;
        try{
             this.serverSocket = new ServerSocket(this.port);
            while(true){
                Socket cl_socket = serverSocket.accept();
                System.out.println("accepted " + workerCounter + ";");
                new Thread(new ServerWorker(cl_socket,db,workerCounter++)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}