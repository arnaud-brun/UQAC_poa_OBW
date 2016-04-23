package server;

/**
 * Created by alexandre on 15/04/16.
 */
public class Main {
    public static void main(String[] args) {
        Server server = new Server(2016);
        server.startServer();
    }
}