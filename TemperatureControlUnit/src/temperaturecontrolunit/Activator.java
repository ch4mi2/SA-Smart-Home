package temperaturecontrolunit;

import clockpublisher.ClockPublish;
import temperaturesensor.SensorThread;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

	
	static SensorThread sensorThread;
	static AC unit;
	static TempControlUI UI;
	private static BundleContext context;
	
	private ClockPublish clock;
	private ServiceTracker<ClockPublish, Object> clockServiceTracker;
	private ServiceTracker<SensorThread, Object> sensorThreadTracker;
	private ServiceRegistration<?> tempUIService;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		clockServiceTracker = 
				new ServiceTracker<ClockPublish, Object>(bundleContext, ClockPublish.class.getName(), null);
		clockServiceTracker.open();
		clock = (ClockPublish) clockServiceTracker.getService();
		
		sensorThreadTracker = 
				new ServiceTracker<SensorThread, Object>(bundleContext, SensorThread.class.getName(), null);
		sensorThreadTracker.open();
		sensorThread = (SensorThread) sensorThreadTracker.getService();
		
		unit = new ACImpl();
		unit.attachClock(clock);
		unit.attachSensorThread(sensorThread);
		
		UI = new TempControlUI();
		UI.attachUnit(unit);
		
		tempUIService = bundleContext.registerService(TempControlUI.class.getName(), UI, null);
		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		clockServiceTracker.close();
		sensorThreadTracker.close();
		tempUIService.unregister();
		System.out.println("Stopping Temperature control unit");
	}

}
