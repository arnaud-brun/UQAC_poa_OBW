package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by alexandre on 15/04/16.
 */
public class ClientHandler implements Runnable {

    private PrintWriter output = null; //The writer that will send messages to the client
    private Socket clientSock;
    private InputStream clientInput;
    private ObjectInputStream clientObject;
    private Object objectInput;
    private String pathToResources = System.getProperty("user.dir") + "/resources/";
    private ArrayList<String> newslettersList = new ArrayList<>();

    /**
     * Create a connection with the client
     *
     * @param clientSock
     */
    public ClientHandler(Socket clientSock) {
        setPossibleNewslettersToSend();
        this.clientSock = clientSock;
    }

    @Override
    public void run() {
        System.out.println("New thread.");
        try {
            //Retrieve objects send by the client
            output = new PrintWriter(new OutputStreamWriter(clientSock.getOutputStream()));

            //Get the client object
            clientInput = clientSock.getInputStream();
            clientObject = new ObjectInputStream(clientInput);
            objectInput = clientObject.readObject();

            //If the object is a Commande, then treat the object.
            if (objectInput.getClass() == String.class) {
                if(objectInput.equals("list-newsletters")){
                    sendToClient(newslettersList.toString().replace("[", "").replace("]", ""));
                } else if (isValidCommand((String) objectInput)) {
                    treatCommand((String) objectInput);
                } else {
                    treatCommand("error");
                }
            }

        //Display an error message if there is a problem
        } catch (ClassNotFoundException e) {
            sendToClient("[ERROR] Invalid command.");
        } catch (FileNotFoundException e) {
            sendToClient("[ERROR] Couldn't find file needed.");
        } catch (IOException e) {
            sendToClient("[ERROR] Invalid command.");

        //In any case, close the connection
        } finally {
            try {
                clientObject.close();
                clientInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieve the possible newsletters to display
     */
    private void setPossibleNewslettersToSend(){
        //Get the newsletter directory
        File directory = new File(pathToResources + "/newsletters");

        //List all the folders from the directory
        for(File file : directory.listFiles()) {
            if (file.isDirectory()) {
                newslettersList.add(file.getName());
            }
        }
    }

    /**
     * Check if a command is valid
     *
     * @param command
     * @return true or false
     */
    private boolean isValidCommand(String command) {
        //Check if the command is in the newsletter list
        if (newslettersList.contains(command)){
            return true;
        }
        return false;
    }

    /**
     * Send a message to the client
     *
     * @param message
     */
    private void sendToClient(String message) {

        //Send the message to the client
        if (output != null) {
            System.out.println(message);
            output.write(message);
            output.flush();
        }
    }

    /**
     * Retrieve a String from an html file
     *
     * @param command - the command given by the client
     * @return the string retrieved
     */
    private String getPathToFile(String command) {
        //Define where to get the file
        return pathToResources + "newsletters/" + command + "/" + command + ".html";
    }

    /**
     * Treat a command
     *
     * @param command - a String defining the command
     */
    private void treatCommand(String command) {
        if (command != "error") {
            String stringToReturn = getPathToFile(command);
            sendToClient(stringToReturn);
        } else {
            sendToClient(pathToResources + "error.html");
        }
    }
}