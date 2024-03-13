package temperaturecontrolunit;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

@Component(
	property = {
		"event.topics=org/osgi/framework/BundleEvent/temp",
		"event.topics=org/osgi/framework/BundleEvent/batteryStatus",
		"event.topics=org/osgi/framework/BundleEvent/solarStatus"
	}
)
public class ServiceComponent implements EventHandler {
	
	Boolean solarStatus;
	String batteryStatus;

	public void handleEvent(Event event) {
		
		if (event.getTopic().equals("org/osgi/framework/BundleEvent/temp")) {
			double temp = (double) event.getProperty("value");
			if( Activator.unit != null ) {
				Activator.unit.setCurrentTemp(temp);
				if( Activator.UI != null ) {
					Activator.UI.updateUI();
				}
			}
		} 
		
		if(event.getTopic().equals("org/osgi/framework/BundleEvent/solarStatus")) {
			solarStatus = (Boolean) event.getProperty("solarStatus");
			if( batteryStatus!= null &&  batteryStatus.equals("Power off")) {
				if(solarStatus == true) {
					Activator.unit.turnOn();
					Activator.unit.setBatteryStatus("Power On");
					if( Activator.UI != null ) {
						Activator.UI.updateUI();
					}
				} else if (solarStatus == false) {
					Activator.unit.turnOff();
					Activator.unit.setBatteryStatus(batteryStatus);
					if( Activator.UI != null ) {
						Activator.UI.updateUI();
					}
				}
			}
		}
		
		if(event.getTopic().equals("org/osgi/framework/BundleEvent/batteryStatus")) {
			batteryStatus = (String) event.getProperty("BatteryStatus");
		}
	}

}