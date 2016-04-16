package com;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Hashtable;

/**
 * Created by Arnaud on 15/04/2016.
 */
public class HelloActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        Hashtable<String, String> prop = new Hashtable<String, String>();
        prop.put("Service", "Viewer");
        bundleContext.registerService(ViewerHTML.class.getName(), new HelloHTMLRenderer(), prop);
        System.out.println("    [DONE] Hello bundle has been properly initialized\n");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("    [STOP] Hello bundle has been stopped");

    }
}
