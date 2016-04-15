package host;

import org.osgi.framework.Bundle;

import java.nio.file.Paths;

public class BundleManager {

    final private HostApplication app = new HostApplication();
    final private String jarPath;

    private BundleManager() {
        this.jarPath = Paths.get("./out/bundles").toAbsolutePath().normalize().toString();
    }

    public static void main(String[] args){
        BundleManager manager = new BundleManager();
        manager.initializeBundles("HelloBundle");
    }

    private Bundle getBundleByName(String bundleName) {
        return app.getBundleByName(bundleName);
    }

    private void initializeBundle(String bundleName) {
        final String bundlePath = this.jarPath + "/" + bundleName + ".jar";
        System.out.println("    [REGISTRATION] Initializing " + bundleName + " bundle now");
        app.registerBundle(bundleName, bundlePath);
    }

    private void initializeBundles(String... bundles) {
        for(String b: bundles) {
            initializeBundle(b);
        }
    }

    private void shutdown(){
        app.shutdownApplication();
    }
}
