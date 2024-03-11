package clockpublisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;


public class Activator implements BundleActivator {
	ServiceRegistration clockServiceRegistration;
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Clock Publisher Start");
		ClockPublish publisherService = new ClockPublishImpl();
		clockServiceRegistration = context.registerService(
				ClockPublish.class.getName(), publisherService, null);
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("Clock Publisher Stop");
		clockServiceRegistration.unregister();
	}

}
