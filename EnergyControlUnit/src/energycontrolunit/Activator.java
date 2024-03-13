package energycontrolunit;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import solar.Solar;
import battery.Battery;

public class Activator implements BundleActivator {

	ServiceRegistration publishServiceRegistration;
	ServiceReference serviceReference;
	static Battery battery;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Start energy control unit");
		serviceReference = bundleContext.getServiceReference(Battery.class.getName());
		battery = (Battery) bundleContext.getService(serviceReference);
		EnergyProducer energyProducer = new EnergyProducerImpl();
		publishServiceRegistration = bundleContext.registerService(EnergyProducer.class.getName(), energyProducer, null);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stop energy control unit");
		bundleContext.ungetService(serviceReference);
	}


}
