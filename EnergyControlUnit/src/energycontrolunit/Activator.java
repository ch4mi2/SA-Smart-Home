package energycontrolunit;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import batteryproducer.Battery;
import solarproducer.Solar;

public class Activator implements BundleActivator {

	ServiceRegistration publishServiceRegistration;
	ServiceRegistration UIServiceRegistration;
	ServiceReference solarServiceReference;
	ServiceReference batteryServiceReference;

	static Battery battery;
	static Solar solar;

	static EnergyControlUI UI;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Start energy control unit");
		batteryServiceReference = bundleContext.getServiceReference(Battery.class.getName());
		battery = (Battery) bundleContext.getService(batteryServiceReference);

		solarServiceReference = bundleContext.getServiceReference(Solar.class.getName());

		
		UI = new EnergyControlUI();
		UIServiceRegistration = bundleContext.registerService(EnergyControlUI.class.getName(), UI, null);
	}
	
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stop energy control unit");
		UIServiceRegistration.unregister();
		bundleContext.ungetService(batteryServiceReference);
		bundleContext.ungetService(solarServiceReference);

	}


}
