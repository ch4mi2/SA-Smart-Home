package alarmpublisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	ServiceRegistration<?> alarmRegistration;
	
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Alarm Publisher Start");
		Alarm alarm = new Alarm();
		alarmRegistration = context.registerService(
				Alarm.class.getName(), alarm, null);
		
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("Alarm Publisher Stop");
		alarmRegistration.unregister();
	}
}
