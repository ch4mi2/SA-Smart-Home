package securitysystem;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import cctvpublisher.CCTV;

public class Activator implements BundleActivator {

	ServiceReference<?> cctvServiceReference;
	ServiceRegistration<?> cctvRegistration;
	
	

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Started Security System Control Unit");
		cctvServiceReference = bundleContext.getServiceReference(CCTV.class.getName());
		
		CCTV cctvSystem = (CCTV) bundleContext.getService(cctvServiceReference);
		
		CCTVUI controlUnit = new CCTVUI();
		controlUnit.attachCCTV(cctvSystem);
		
		cctvRegistration = bundleContext.registerService(
				CCTVUI.class.getName(), controlUnit, null);
		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stopped Security System Control Unit");
		bundleContext.ungetService(cctvServiceReference);
	}

}
