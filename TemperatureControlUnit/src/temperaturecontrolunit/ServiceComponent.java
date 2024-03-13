package temperaturecontrolunit;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

@Component(
	property = {
		"event.topics=org/osgi/framework/BundleEvent/temp"
	}
)
public class ServiceComponent implements EventHandler {

	public void handleEvent(Event event) {
		double temp = (double) event.getProperty("value");
		if( Activator.unit != null ) {
			Activator.unit.setCurrentTemp(temp);
			if( Activator.UI != null ) {
				Activator.UI.updateUI();
			}
		}
	}

}