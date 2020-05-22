package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final int port;
    private DataBase db;

    public Server(int port, DataBase db) throws IOException {
        this.port = port;
        this.db = db;
    }

    public void startServer(){
        int workerCounter = 1;
        try{
            ServerSocket serverSocket = new ServerSocket(this.port);
            while(true){
                Socket cl_socket = serverSocket.accept();
                System.out.println("accepted");
                new Thread(new ServerWorker(cl_socket,db,workerCounter++)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}