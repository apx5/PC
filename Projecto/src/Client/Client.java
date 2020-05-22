package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private String host;
    private int port;

    public Client(String host,int port){
        this.host = host;
        this.port = port;

    }

    public void menu(BufferedReader systemIn, BufferedReader in,PrintWriter out) throws IOException {
        System.out.print("Welcome to Covid\nPlease select an option:\n1- Sign In\n2- Sign up\n");
        String s = systemIn.readLine();
        String username, password, region,response;
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

                while((response= in.readLine()) != null && !response.equals("ok")){
                    out.println("1");
                    out.flush();
                    System.out.println("Invalid username or password");
                    System.out.println("Enter your username: ");
                    username = systemIn.readLine();
                    out.println(username);
                    out.flush();
                    System.out.println("Enter your password: ");
                    password = systemIn.readLine();
                    out.println(password);
                    out.flush();
                }
                System.out.println("Login successfull!");
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
                while((response= in.readLine()) != null && !response.equals("ok")){
                    out.println("2");
                    out.flush();
                    System.out.println("Username already exists");
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
                }
                System.out.println("Register successfull!");
                break;
        }

    }

    public void options(BufferedReader systemIn, BufferedReader in,PrintWriter out) throws IOException{
        String j,cases ,cases_response;

        do {
            System.out.print("Choose an option:\n1-Send new cases\n2-Check total cases\n3-Check cases in your region\n0-Exit\n");
            j = systemIn.readLine();
            switch (j) {
                case "1":
                    System.out.println("Enter the new cases:");
                    cases = systemIn.readLine();
                    out.println("3");
                    out.flush();
                    out.println(cases);
                    out.flush();
                    String i = in.readLine();
                    System.out.println(i);
                    break;
                    //como a seguir todos vao receber a nova notificação, não há necessidade de ficar à espera de resposta do server
                case "2":
                    out.println("4");
                    out.flush();
                    cases_response = in.readLine();
                    System.out.println(cases_response + " total cases.");
                    break;
                case "3":
                    out.println("5");
                    out.flush();
                    cases_response = in.readLine();
                    System.out.println(cases_response + " total cases in your region.");
                    break;
            }
        }while (!j.equals("0"));
    }

    public void run_connection() {
        try {
            Socket cl_socket = new Socket(host, port);

            BufferedReader in = new BufferedReader(new InputStreamReader(cl_socket.getInputStream()));

            PrintWriter out = new PrintWriter(cl_socket.getOutputStream());

            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

            String userIn = null; // do teclado
            String response; // do server
            menu(systemIn,in,out);
            //Thread que ficará à escuta para executar sempre que houver alterações aos registos
            //Thread listener = new Thread(new ClientListener());
            //listener.start();

            options(systemIn,in,out);

            cl_socket.shutdownOutput();
            cl_socket.shutdownInput();
            cl_socket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
