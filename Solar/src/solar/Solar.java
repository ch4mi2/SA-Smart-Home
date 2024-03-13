package solar;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;

public class Solar implements Runnable{

	private final EventAdmin eventAdmin;

    public Solar(EventAdmin eventAdmin) {
        this.eventAdmin = eventAdmin;
    }

    public Solar(BundleContext context) {
        ServiceTracker<EventAdmin, Object> tracker = new ServiceTracker<>(context, EventAdmin.class.getName(), null);
        tracker.open();
        this.eventAdmin = (EventAdmin) tracker.getService();
//        System.out.println(eventAdmin);
    }

    @Override
    public void run() {
        while (true) {
        	System.out.println("Running Solar thread");
            boolean solarStatus = generateSolarStatus();
            publishSolarStatus(solarStatus);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Stopping solar status generator thread");
                return;
            }
        }
    }

    private boolean generateSolarStatus() {
        return (System.currentTimeMillis() % 2) == 0;
    }

    private void publishSolarStatus(boolean solarStatus) {
        if (eventAdmin != null) {
            Dictionary<String, Object> properties = new Hashtable<>();
            properties.put("solarStatus", solarStatus);
            Event event = new Event("org/osgi/framework/BundleEvent/solarStatus", properties);
            eventAdmin.sendEvent(event);
        } else {
            System.out.println("Event admin is null");
        }
    }
	

}
