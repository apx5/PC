package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private String host;
    private int port;

    public Client(String host,int port){
        this.host = host;
        this.port = port;

    }

    public void run_connection() {
        try {
            Socket cl_socket = new Socket(host, port);

            BufferedReader in = new BufferedReader(new InputStreamReader(cl_socket.getInputStream()));

            PrintWriter out = new PrintWriter(cl_socket.getOutputStream());

            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

            String userIn;
            String response;
            while ((userIn = systemIn.readLine())!=null && !userIn.equals("quit")) {
                out.println(userIn);
                out.flush();

                response = in.readLine();
                System.out.println(response);
            }

            cl_socket.shutdownOutput();
            cl_socket.shutdownInput();
            cl_socket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
