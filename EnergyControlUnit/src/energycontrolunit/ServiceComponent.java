package energycontrolunit;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.component.annotations.Component;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;

import batteryproducer.Battery;

@Component(
	property = {
		"event.topics=org/osgi/framework/BundleEvent/solarStatus",
		"event.topics=org/osgi/framework/BundleEvent/batteryStatus",
		"event.topics=org/osgi/framework/BundleEvent/batteryLevel"
	}
)
public class ServiceComponent implements EventHandler {

	private EventAdmin eventAdmin;
	private String currentBatteryStatus;
	private boolean currentSolarStatus;
	private String previousBatteryStatus;

    public void setEventAdmin(EventAdmin eventAdmin) {
        this.eventAdmin = eventAdmin;
    }

    public void unsetEventAdmin(EventAdmin eventAdmin) {
        this.eventAdmin = null;
    }
    
    
	public void handleEvent(Event event) {
		String topic = event.getTopic();
//		Boolean topic = (Boolean) event.getProperty("solarStatus");
		if("org/osgi/framework/BundleEvent/solarStatus".equals(topic)) {
			Boolean solarStatus = (Boolean) event.getProperty("solarStatus");
//			if(solarStatus == true) {
//				System.out.println("Current solar status : " + "Active");
//			}else {
//				System.out.println("Current solar status : " + "Inactive");
//			}
			if(Activator.UI != null && solarStatus != null) {
				Activator.UI.updateSolarStatus(solarStatus);
			}
			if (solarStatus!= null && solarStatus == false)
			{
				if(Activator.battery != null && currentBatteryStatus != "Power off") {
					Activator.battery.switchOn();
				}
			}else 
			{
				if(Activator.battery != null) {
					Activator.battery.switchOff();
				}	
			}
		}
		
		if("org/osgi/framework/BundleEvent/batteryStatus".equals(topic)){
			String batteryStatus = (String) event.getProperty("BatteryStatus");
            if (batteryStatus != null) {
            	currentBatteryStatus = batteryStatus;
//            	if(previousBatteryStatus != currentBatteryStatus) {
//            		System.out.println("Current Battery Status : " + batteryStatus);
//            		previousBatteryStatus = currentBatteryStatus;
//            	}	
            	if(Activator.UI != null && currentBatteryStatus != null){
            		Activator.UI.updateBatteryStatus(currentBatteryStatus);
            	}
            }
		}
		
		if("org/osgi/framework/BundleEvent/batteryLevel".equals(topic)) {
			Double batteryLevel = (Double) event.getProperty("BatteryLevel");
//			System.out.println("Battery Level : " + batteryLevel);
			if(Activator.UI != null && batteryLevel != null) {
				Activator.UI.updateBatteryLevel(batteryLevel);
			}
		}
	}	
}