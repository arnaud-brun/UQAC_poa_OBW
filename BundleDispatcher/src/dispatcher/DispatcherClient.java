package dispatcher;

import com.ViewerHTML;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Arnaud on 15/04/2016.
 */
public class DispatcherClient {

    BundleContext context;
    private OutputStream clientOutput;
    private ObjectOutputStream clientObject;
    private BufferedReader serverInput;
    private Socket socket;
    private static int port = 2016;
    private static String hostname = "localhost";

    public DispatcherClient(BundleContext c){
        this.context = c;
        // init connection to server and event listener


        // fake input managmenet


        // Query for all service references matching any language.
        try {
            ServiceReference[] refs = context.getServiceReferences(
                    ViewerHTML.class.getName(), "(Service=Viewer)");
            if (refs != null)
            {
                System.out.println("Enter a html phrase");
                String content = getMessageFromClient("newsletters,walmart");

                ViewerHTML v = (ViewerHTML) context.getService(refs[0]);
                v.setHTML(content);
                v.display();
            }
            else
            {
                System.out.println("Couldn't find any viewer service...");
            }


        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
    }

    private String getMessageFromClient(String command){
        String stringToReturn = "";
        try{
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
            while((line = serverInput.readLine()) != null) {
                stringToReturn += line;
            }

            //If the host is unreachable, display an error message
        } catch (UnknownHostException e){
            System.out.println("Error : error with the hostname address. Please check the IP.");
            System.exit(-2);

            //If something happen during the execution, display an error message
        } catch (Exception e) {
            e.printStackTrace();
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
