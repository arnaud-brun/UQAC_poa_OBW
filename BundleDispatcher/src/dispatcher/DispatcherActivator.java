package dispatcher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Created by Arnaud on 15/04/2016.
 */
public class DispatcherActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        //Call the client side with the connection to server
        DispatcherClient client = new DispatcherClient(bundleContext);
        System.out.println("    [DONE] Dispatcher has been properly initialized\n");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("    [STOP] Dispatcher has been stopped");
    }
}
