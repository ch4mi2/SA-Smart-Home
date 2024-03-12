package securitysystem;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import alarmpublisher.Alarm;
import cctvpublisher.CCTV;

public class Activator implements BundleActivator {

	ServiceReference<?> cctvServiceReference;
	ServiceReference<?> alarmServiceReference;
	ServiceRegistration<?> cctvRegistration;
	
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Started Security System Control Unit");
		CCTVUI controlUnit = new CCTVUI();
		
		try {
			cctvServiceReference = bundleContext.getServiceReference(CCTV.class.getName());
			CCTV cctvSystem = (CCTV) bundleContext.getService(cctvServiceReference);
			controlUnit.attachCCTV(cctvSystem);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		try {
			alarmServiceReference = bundleContext.getServiceReference(Alarm.class.getName());
			Alarm alarmSystem = (Alarm) bundleContext.getService(alarmServiceReference);
			controlUnit.attachAlarm(alarmSystem);
		} catch (Exception e) {
			System.out.println(e);
		}
		cctvRegistration = bundleContext.registerService(
				CCTVUI.class.getName(), controlUnit, null);
		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stopped Security System Control Unit");
		bundleContext.ungetService(cctvServiceReference);
	}

}
