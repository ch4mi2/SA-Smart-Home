package solar;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

ServiceRegistration publishServiceRegistration;
	
	private EventAdmin eventAdmin = null;

	 private Thread solarStatusThread;

	    @Override
	    public void start(BundleContext context) throws Exception {
	        solarStatusThread = new Thread(new Solar(context));
	        solarStatusThread.start();
	    }

	    @Override
	    public void stop(BundleContext context) throws Exception {
	        solarStatusThread.interrupt();
	    }


}
