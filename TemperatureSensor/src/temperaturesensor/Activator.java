package temperaturesensor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private Thread sensorThread;
	private ServiceRegistration<?> sensorService;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Started Monitoring Temperature");
		Activator.context = bundleContext;
		SensorComponent sensorComponent = new SensorComponent(bundleContext, 2000);
		sensorThread = new Thread(sensorComponent);
		sensorThread.start();
		SensorThread sensorthread = new SensorThreadImpl(sensorComponent);
		sensorService = bundleContext.registerService(
				SensorThread.class.getName(), sensorthread, null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		sensorThread.interrupt();
		sensorService.unregister();
		System.out.println("Stopping temperature monitoring");
	}

}
