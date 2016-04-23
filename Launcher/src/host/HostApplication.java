package host;

import org.apache.felix.framework.Felix;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;

import java.util.HashMap;
import java.util.Map;

class HostApplication {

    private Map<String, Bundle> loadedBundle = new HashMap<>();
    private Felix felixInstance = null;

    HostApplication() {
        // Create a configuration property map and flush cache
        Map<String, String> config = new HashMap<>();
        config.put(Constants.FRAMEWORK_STORAGE_CLEAN, Constants.FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT);

        try {
            // Start new instance of the framework using configuration properties
            felixInstance = new Felix(config);
            felixInstance.start();
        } catch (Exception e) {
            System.err.println("[ERROR] Could not create framework instance: " + e);
            e.printStackTrace();
        }
    }

    void registerBundle(String bundleName, String pathToJar) {
        BundleContext bundleContext = felixInstance.getBundleContext();
        try {
            //Retrieve the bundle from its jar
            Bundle bundle = bundleContext.installBundle("file:" + pathToJar);
            bundle.start();
            this.loadedBundle.put(bundleName, bundle);
        } catch (BundleException e) {
            e.printStackTrace();
        }
    }

    Bundle getBundleByName(String name) {
        return this.loadedBundle.get(name);
    }

    void shutdownApplication() {
        // Shut down the felix framework when stopping the host application.
        try {
            felixInstance.stop();
            felixInstance.waitForStop(0);
        } catch (BundleException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}