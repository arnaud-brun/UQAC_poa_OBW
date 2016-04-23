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
    /**
     * Create a connection with the client
     * @param clientSock
     */
    public ClientHandler(Socket clientSock){
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
            if(objectInput.getClass() == String.class){
                if(isValidCommand((String) objectInput)) {
                    treatCommand((String) objectInput);
                } else {
                    treatCommand("error");
                }
            }

        //Display an error message if there is a problem
        } catch (ClassNotFoundException e) {
            sendToClient("[ERROR] Invalid command.");
        } catch (FileNotFoundException e){
            sendToClient("[ERROR] Couldn't find file needed.");
        } catch (IOException e) {
            sendToClient("[ERROR] Invalid command.");
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
     * Check if a command is valid
     * @param command
     * @return true or false
     */
    private boolean isValidCommand(String command){

        //Define lists of possible commands
        ArrayList<String> optionList = new ArrayList<>();
        optionList.add("newsletters");
        optionList.add("promotions");

        ArrayList<String> contextList = new ArrayList<>();
        contextList.add("walmart");
        contextList.add("cup-cake");
        contextList.add("kreative");
        contextList.add("launch.it");
        contextList.add("sports-experts");

        //Check if the command is well formed
        if (command.split(",").length != 2){
            System.out.println(command.split(",").toString());
            return false;
        }

        //Check the first argument
        if (!optionList.contains(command.split(",")[0])){
            System.out.println(optionList.contains(command.split(",")[0]));
            return false;
        }

        //Check the second argument
        if (!contextList.contains(command.split(",")[1])){
            System.out.println(contextList.contains(command.split(",")[1]));
            return false;
        }

        //If all checks ar OK, return true
        return true;
    }

    /**
     * Send a message to the client
     * @param message
     */
    private void sendToClient(String message){

        //Send the message to the client
        if(output != null){
            System.out.println(message);
            output.write(message);
            output.flush();
        }
    }

    /**
     * Retrieve a String from an html file
     * @param commandArray - the array defining the command
     * @return the string retrieved
     */
    private String getPathToFile(String[] commandArray) {
        //Define where to get the file
        return pathToResources + commandArray[0] + "/" + commandArray[1] + "/" + commandArray[1] + ".html";
    }

    /**
     * Treat a command
     * @param command - a String defining the command
     */
    private void treatCommand(String command) {
        if(command != "error") {
            String[] commandArray = command.split(",");
            String stringToReturn = getPathToFile(commandArray);
            sendToClient(stringToReturn);
        } else {
            sendToClient(pathToResources + "error.html");
        }
    }
}