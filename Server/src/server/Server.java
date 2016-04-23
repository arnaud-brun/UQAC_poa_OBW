package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by alexandre on 15/04/16.
 */
public class Server {
    private static ServerSocket serverSocket;
    private Socket clientSock;

    /**
     * Create a server that listens on the port defined
     *
     * @param port
     */
    public Server(int port) {
        try {
            //Create the ServerSocket
            serverSocket = new ServerSocket(port);
            System.out.println("Listening to port " + port);
        } catch (IOException e) {
            //If the serveur can't open a socket, display an error message
            System.out.println("[ERROR] Couldn't open a socket on port " + port + ".");
            System.exit(-2);
        }
    }

    /**
     * Starts the server
     */
    public void startServer() {
        //The server runs continuously
        while (true) {
            try {
                //Accept the connection from the client
                clientSock = serverSocket.accept();
                new ClientHandler(clientSock).run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
