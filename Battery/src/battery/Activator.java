package battery;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventAdmin;

public class Activator implements BundleActivator {

	ServiceRegistration publishServiceRegistration;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Battery Publisher Started.");
		ServiceReference<EventAdmin> serviceRef = bundleContext.getServiceReference(EventAdmin.class);
        EventAdmin eventAdmin = bundleContext.getService(serviceRef);
		Battery batteryService = new BatteryImpl(eventAdmin);
		publishServiceRegistration = bundleContext.registerService(Battery.class.getName(), batteryService, null);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Battery Publisher Stopped.");
		publishServiceRegistration.unregister();
	}


}
