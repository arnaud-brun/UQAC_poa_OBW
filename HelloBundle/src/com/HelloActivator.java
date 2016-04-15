package com;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Created by Arnaud on 15/04/2016.
 */
public class HelloActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("    [DONE] Hello bundle has been properly initialized\n");

        HelloHTMLRenderer swingApp = new HelloHTMLRenderer();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("    [STOP] Hello bundle has been stopped");

    }
}
