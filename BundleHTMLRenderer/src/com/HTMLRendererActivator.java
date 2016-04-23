package com;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Hashtable;

/**
 * Created by Arnaud on 15/04/2016.
 */
public class HTMLRendererActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        //Define  the service properties for referencing it
        Hashtable<String, String> prop = new Hashtable<String, String>();
        prop.put("Service", "Viewer");

        //Register a service with the given properties
        bundleContext.registerService(ViewerHTML.class.getName(), new HTMLRenderer(), prop);
        System.out.println("    [DONE] HTML Renderer bundle has been properly initialized\n");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("    [STOP] HTML Renderer bundle has been stopped");

    }
}
