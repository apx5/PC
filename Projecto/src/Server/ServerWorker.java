package Server;

import Exceptions.UserTakenException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerWorker implements Runnable{

    private Socket socket;
    private int id;
    private DataBase db;
    private String user;


    public ServerWorker(Socket socket,DataBase db, int id){
        this.socket = socket;
        this.id = id;
        this.db = db;
    }

    private void menu(BufferedReader in,PrintWriter out) throws IOException {
        String username, password, region, s,new_cases;

        do{
            s = in.readLine();
            switch (s) {
                case "1":
                    System.out.println("caso login");
                    username = in.readLine();
                    password = in.readLine();


                    if (db.check_login(username, password)) {
                        user = username;
                        out.println("ok");

                    } else {
                        out.println("Nok");
                    }
                    out.flush();
                    break;
                case "2":
                    System.out.println("caso registo");
                    username = in.readLine();
                    password = in.readLine();
                    region = in.readLine();


                    if (db.registerClient(username, password, region)) {
                        user = username;
                        out.println("ok");
                    } else {
                        out.println("Nok");
                    }
                    out.flush();
                    break;
                case "3":
                    System.out.println("caso envio");
                    new_cases = in.readLine();
                    int cases = Integer.parseInt(new_cases);
                    if(db.updateCases(this.user,cases)){
                        out.println("Cases updated.");
                        out.flush();
                        //multicast();
                    } else {
                        out.println("Case limit reached!");
                        out.flush();
                    }
                    break;
                case "4":
                    System.out.println("caso consulta");
                    out.println(db.getTotal_cases());
                    out.flush();
                    break;
                case "5":
                    System.out.println("caso consulta reg");
                    out.println(db.checkCasesByRegion(user));
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





        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
