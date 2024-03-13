package battery;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;

public class BatteryImpl implements Battery {

	private boolean isBatteryOn = false;
	private static double batteryLevel = 100.0;
	private static boolean OffMessageAt50Sent = false;
    private static boolean OffMessageAt25Sent = false;
    private static boolean powerOffMessage = false;
	private final EventAdmin eventAdmin;
	private String message;

    public BatteryImpl(EventAdmin eventAdmin) {
        this.eventAdmin = eventAdmin;
    }

    public BatteryImpl(BundleContext context) {
        ServiceTracker<EventAdmin, Object> tracker = new ServiceTracker<>(context, EventAdmin.class.getName(), null);
        tracker.open();
        this.eventAdmin = (EventAdmin) tracker.getService();
    }
    
	@Override
	public void switchOn() {
		isBatteryOn = true;
        decreaseBatteryLevel();
	}
	
	public void switchOff() {
		isBatteryOn = false;
        increaseBatteryLevel();
	}
	
	public void decreaseBatteryLevel() {
        new Thread(new Runnable() {
            @Override
            public void run() {
            	while (batteryLevel > 0) {
                    try {
                       Thread.sleep(1000); // Sleep for 1 second
                       batteryLevel -= 0.5;
                       batteryLevel = Math.max(0, batteryLevel);
                       if(batteryLevel!=0) {
                    	   if(!OffMessageAt50Sent && batteryLevel <= 50 && batteryLevel > 25) {
                    		   message = "Essentail devices only";
                    		   OffMessageAt50Sent = true;
                    		   publishMessage(message);
                    	   }else if(!OffMessageAt25Sent  && batteryLevel <= 25) {
                    		   message = "Power Saving Mode";
                    		   OffMessageAt25Sent = true;
                    		   publishMessage(message);
                    	   }
                    	   System.out.println("Battery Level: " + batteryLevel);
                       }else if(!powerOffMessage){
                    	   message="Power off";
                    	   powerOffMessage = true;
                    	   publishMessage(message);
                       }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
       }).start();
	}
	
	public void increaseBatteryLevel() {     
        new Thread(new Runnable() {
            @Override
            public void run() {
            	while (batteryLevel <100) {
                    try {
                       Thread.sleep(1000); // Sleep for 1 second
                       batteryLevel += 0.5;
                       batteryLevel = Math.min(100, batteryLevel);
                       if(batteryLevel != 100.0) {
                    	   System.out.println("Battery Level: " + batteryLevel);
                       }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                publishMessage("Chargered");
            }
       }).start();
	} 
	
	
	
	private void publishMessage(String Message) {
        if (eventAdmin != null) {
            Dictionary<String, Object> properties = new Hashtable<>();
            properties.put("BatteryStatus",Message);
            Event event = new Event("org/osgi/framework/BundleEvent/batteryStatus", properties);
            eventAdmin.sendEvent(event);
        } else {
            System.out.println("Event admin is null");
        }
    }
}
