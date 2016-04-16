package dispatcher;

import com.ViewerHTML;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import java.util.Scanner;

/**
 * Created by Arnaud on 15/04/2016.
 */
public class DispatcherClient {

    BundleContext context;

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
                Scanner s = new Scanner(System.in);
                String content = s.nextLine();

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



}
