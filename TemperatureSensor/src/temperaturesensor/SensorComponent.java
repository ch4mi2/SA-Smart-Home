package temperaturesensor;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;

class SensorComponent implements Runnable {

    private final int threshold;
    private final EventAdmin eventAdmin;
    private volatile double temp = 32;
    private boolean AC_On = false;
    private double amountChanged = 0;

    public SensorComponent(BundleContext context, int threshold) {
    	ServiceTracker<EventAdmin, Object>tracker = new ServiceTracker<EventAdmin, Object>(context, EventAdmin.class.getName(), null);
		tracker.open();
    	
		this.eventAdmin = (EventAdmin) tracker.getService();
        this.threshold = threshold;
    }

    @Override
    public void run() {
        // Simulate sensor readings
        while (true) {
            double reading = generateSensorReading();
            publishSensorEvent(reading);
            try {
                Thread.sleep(threshold); // Simulate delay between readings
            } catch (InterruptedException e) {
                System.out.println("Stopping sensor thread");
            	return;
            }
        }
    }

    private double generateSensorReading() {
        // Implement sensor reading logic here
    	int randomNum = (int) (Math.random() * (80 - 25)) + 25;
        if( AC_On || temp >= randomNum) {
        	if( amountChanged != 0 ) {
        		temp -= amountChanged;
        	} else {
        		temp -= 0.2;
        	}
        } else {
        	temp += 0.2;
        }
        return temp;
    }

    private void publishSensorEvent(double reading) {
        if (eventAdmin != null) {
            Event event = createSensorEvent(reading);
            eventAdmin.sendEvent(event);
        } else {
        	System.out.println("Event admin is null");
        }
    }

    private Event createSensorEvent(double reading) {
        Dictionary<String, Object> properties = new Hashtable<>();
        properties.put("value", reading);
        return new Event("org/osgi/framework/BundleEvent/temp", properties);
    }
    
    public void simulateAC(double amountChanged) {
    	if( amountChanged == 0 ) {
    		this.AC_On = false;
    	} else {
    		this.AC_On = true;
    	}
    	this.amountChanged = amountChanged;
    }
}
