package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {

    private String host;
    private int port;
    private BufferedReader in;
    private PrintWriter out;

    public Client(String host,int port){
        this.host = host;
        this.port = port;

    }

    public void menu(BufferedReader systemIn) throws IOException {
        int counter = 0;
        System.out.print("Welcome to SARS-CoV-2 Survey\n");
        String s;
        String username, password, region,response="Nok";
        do{
            System.out.print("Please select an option:\n1- Sign In\n2- Sign up\n");
            s = systemIn.readLine();

            switch (s) {
                case "1":
                    out.println("1");
                    out.flush();
                    System.out.println("Enter your username: ");
                    username = systemIn.readLine();
                    out.println(username);
                    out.flush();
                    System.out.println("Enter your password: ");
                    password = systemIn.readLine();
                    out.println(password);
                    out.flush();

                    if ((response= in.readLine()) != null && response.equals("ok")) {
                        System.out.println("Login successfull!");
                    }
                    else {
                        System.out.println("Invalid username or password");
                    }
                    break;
                case "2":
                    out.println("2");
                    out.flush();
                    System.out.println("Enter your username: ");
                    username = systemIn.readLine();
                    out.println(username);
                    out.flush();
                    System.out.println("Enter your password: ");
                    password = systemIn.readLine();
                    out.println(password);
                    out.flush();
                    System.out.println("Enter your region: ");
                    region = systemIn.readLine();
                    out.println(region);
                    out.flush();

                    if ((response= in.readLine()) != null && response.equals("ok")) {
                        System.out.println("Register successfull!");
                    }
                    else {
                        System.out.println("Username already exists");
                    }
                    break;
            }
        } while ((!s.equals("1") && !s.equals("2")) || !response.equals("ok"));
    }

    public void options(BufferedReader systemIn) throws IOException{
        String j,cases ,cases_response;

        do {
            System.out.print("Choose an option:\n1-Send new cases\n2-Check average\n3-Check cases in your region\n0-Exit\n");
            j = systemIn.readLine();
            switch (j) {
                case "0":
                    out.println("0");
                    break;
                case "1":
                    System.out.println("Enter the new cases:");
                    cases = systemIn.readLine();
                    out.println("3");
                    out.flush();
                    out.println(cases);
                    out.flush();
                    //String i = in.readLine();
                    //System.out.println(i);
                    break;
                    //como a seguir todos vao receber a nova notificação, não há necessidade de ficar à espera de resposta do server
                case "2":
                    out.println("4");
                    out.flush();

                    break;
                case "3":
                    out.println("5");
                    out.flush();
                    break;
            }
        }while (!j.equals("0"));
    }

    public void run_connection() {
        try {
            Socket cl_socket = new Socket(host, port);

            in = new BufferedReader(new InputStreamReader(cl_socket.getInputStream()));

            out = new PrintWriter(cl_socket.getOutputStream());

            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

            String userIn = null; // do teclado
            String response; // do server
            menu(systemIn);

            //Thread que ficará à escuta para executar sempre que houver alterações aos registos
            Thread listener = new Thread(new ClientListener());
            listener.start();

            options(systemIn);

            cl_socket.shutdownOutput();
            cl_socket.shutdownInput();
            cl_socket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public class ClientListener implements Runnable{
        public void ClientListner(){}

        public void run(){
            String message;
            try{
                while((message = in.readLine()) != null){
                    System.out.println(message);
                }
            }
            catch (SocketException e){}
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
