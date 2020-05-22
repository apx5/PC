package Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DataBase db = new DataBase();
        Server server = new Server(12345,db);
        server.startServer();
    }
}
