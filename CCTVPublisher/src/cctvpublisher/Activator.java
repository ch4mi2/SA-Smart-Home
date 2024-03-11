package cctvpublisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;


public class Activator implements BundleActivator {

	ServiceRegistration<?> cctvRegistration;
	
	
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("CCTV Publisher Start");
		CCTV publisherService = new CCTVImpl();
		cctvRegistration = context.registerService(
				CCTV.class.getName(), publisherService, null);
		
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("CCTV Publisher Stop");
		cctvRegistration.unregister();
	}

}
