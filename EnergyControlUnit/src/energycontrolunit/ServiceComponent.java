package energycontrolunit;

import battery.Battery;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.component.annotations.Component;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;

@Component(
	property = {
		"event.topics=org/osgi/framework/BundleEvent/solarStatus",
		"event.topics=org/osgi/framework/BundleEvent/batteryStatus"
	}
)
public class ServiceComponent implements EventHandler {

	private EventAdmin eventAdmin;
	private String currentBatteryStatus;
	private boolean currentSolarStatus;

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
			if (solarStatus!= null && solarStatus == false)
			{
				System.out.println("Solar Status : Off");
				if(Activator.battery != null && currentBatteryStatus != "Power off") {
					System.out.println("Turn on the battery.....");
					Activator.battery.switchOn();
				}
			}else 
			{
				if(Activator.battery != null) {
					Activator.battery.switchOff();
					System.out.println("Solar Status : Active");
				}	
			}
		}
		if("org/osgi/framework/BundleEvent/batteryStatus".equals(topic)){
			String batteryStatus = (String) event.getProperty("BatteryStatus");
            if (batteryStatus != null) {
            	currentBatteryStatus = batteryStatus;
                System.out.println("Battery Status: " + batteryStatus);
            }
		}
	}	
}