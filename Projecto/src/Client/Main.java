package Client;

public class Main {

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1",12345);

        client.run_connection();
    }
}
