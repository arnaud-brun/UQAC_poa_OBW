package dispatcher;

import com.ViewerHTML;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Arnaud on 15/04/2016.
 */
public class DispatcherClient {
    private ServiceReference[] refs;
    private static int port = 2016;
    private static String hostname = "localhost";
    BundleContext context;
    private OutputStream clientOutput;
    private ObjectOutputStream clientObject;
    private BufferedReader serverInput;
    private Socket socket;
    private String command;

    public DispatcherClient(BundleContext c) {
        this.context = c;

        // Query for all service references matching any language.
        try {
            refs = context.getServiceReferences(ViewerHTML.class.getName(), "(Service=Viewer)");
            waitForInterraction();
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wait for an input and communicate with the server
     */
    private void waitForInterraction(){
        //Define variables
        boolean exit = false;
        Scanner keyboardInput = new Scanner(System.in);
        int commandNum;

        //Ask the server the list of possible newsletters
        ArrayList<String> newsletters = askPossibleNewslettersFromServer();

        //Display the list to the client
        System.out.println("Here are the possible newsletters :");
        System.out.println("[0] Exit program.");
        for(int i = 1 ; i < newsletters.size() ; i++){
            System.out.println("[" + i + "] " + newsletters.get(i) );
        }
        System.out.println("\n");

        //infinite loop to handle specific newsletter or promotion of specific trademark
        while (true) {

            //Get the client input
            System.out.print("Input : ");
            try {
                commandNum = Integer.parseInt(keyboardInput.nextLine());
            } catch (NumberFormatException e) {
                commandNum = -1;
            }

            //Define what to do with the input
            if(commandNum > 0 && commandNum < newsletters.size()){
                interactWithServer(newsletters.get(commandNum));
            } else if (commandNum != 0) {
                interactWithServer("error");
            } else {
                break;
            }
        }
    }

    /**
     * Ask the server the list of all possible newsletters to display
     * @return a list of string defining newsletters
     */
    private ArrayList<String> askPossibleNewslettersFromServer(){
        //Create a news array list and ask the server
        ArrayList<String> newsletters = new ArrayList();
        String serverResponse = getMessageFromServer("list-newsletters");

        //Define the array
        newsletters.add("exit");
        for(String newsletter : serverResponse.split(", ")){
            newsletters.add(newsletter);
        }

        //Return the array
        return newsletters;
    }

    /**
     * Send a command to the server and display an
     * HTML page.
     * @param command - The command to send
     */
    private void interactWithServer(String command) {
        //get disponibilities from server
        String pathToFile = getMessageFromServer(command);

        ViewerHTML v = (ViewerHTML) context.getService(refs[0]);

        v.setHTMLUrl(pathToFile);
        v.display();
    }

    /**
     * Send a command to the server and retrieve its
     * responses
     * @param command - The command to send
     * @return a message from the server
     */
    private String getMessageFromServer(String command) {
        String stringToReturn = "";
        try {
            //Create a socket to the server
            socket = new Socket(hostname, port);

            //Create an output stream to send an object to the server
            clientOutput = socket.getOutputStream();
            clientObject = new ObjectOutputStream(clientOutput);

            //Create an input stream to receive messages from the server
            serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Send a command to the server
            clientObject.writeObject(command);

            //Read the server response
            String line;
            while ((line = serverInput.readLine()) != null) {
                stringToReturn += line;
            }

            //If the host is unreachable, display an error message
        } catch (UnknownHostException e) {
            System.out.println("Error : error with the hostname address. Please check the IP.");
            System.exit(-2);

            //If something happen during the execution, display an error message
        } catch (Exception e) {
            System.out.println("Error : cannot connect to server.");
            System.exit(-2);
        } finally {
            //Close the streams and the socket
            try {
                serverInput.close();
                clientObject.close();
                clientOutput.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringToReturn;
    }
}