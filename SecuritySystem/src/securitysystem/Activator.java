package securitysystem;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import alarmpublisher.Alarm;
import cctvpublisher.CCTV;
import clockpublisher.ClockPublish;

public class Activator implements BundleActivator {

	ServiceReference<?> cctvServiceReference;
	ServiceReference<?> alarmServiceReference;
	ServiceReference<?> clockServiceReference;
	ServiceRegistration<?> cctvRegistration;
	
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Started Security System Control Unit");
		CCTVUI controlUnit = new CCTVUI();
		
		try {
			cctvServiceReference = bundleContext.getServiceReference(CCTV.class.getName());
			CCTV cctvSystem = (CCTV) bundleContext.getService(cctvServiceReference);
			controlUnit.attachCCTV(cctvSystem);
		} catch (Exception e) {
			System.out.println(e + " : " + "CCTVPublisher is not started");
			
		}
		
		try {
			alarmServiceReference = bundleContext.getServiceReference(Alarm.class.getName());
			Alarm alarmSystem = (Alarm) bundleContext.getService(alarmServiceReference);
			controlUnit.attachAlarm(alarmSystem);
		} catch (Exception e) {
			System.out.println(e + " : " + "AlarmPublisher is not started");
		}
		
		
		try {
			clockServiceReference = bundleContext.getServiceReference(ClockPublish.class.getName());
			ClockPublish clockSystem = (ClockPublish) bundleContext.getService(clockServiceReference);
			controlUnit.attachClock(clockSystem);
		} catch (Exception e) {
			System.out.println(e + " : " + "AlarmPublisher is not started");
		}
		
		cctvRegistration = bundleContext.registerService(
				CCTVUI.class.getName(), controlUnit, null);
		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stopped Security System Control Unit");
		bundleContext.ungetService(cctvServiceReference);
	}

}
