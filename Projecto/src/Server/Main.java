package Server;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        DataBase db = new DataBase();
        try{
            FileInputStream fis = new FileInputStream("Survey1");
            ObjectInputStream ois = new ObjectInputStream(fis);
            db = (DataBase) ois.readObject();
            ois.close();
        }
        catch(FileNotFoundException | ClassNotFoundException e) {
            System.out.println("File not found or corrupted file!");
        }

        Server server = new Server(12345,db);
        server.startServer();

        FileOutputStream fos = new FileOutputStream("Survey1");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(db);
        oos.flush();
        oos.close();

        System.exit(0);

    }




}
