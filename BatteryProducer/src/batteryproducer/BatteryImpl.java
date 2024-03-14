package batteryproducer;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;

public class BatteryImpl implements Battery {

	private boolean isBatteryOn = false;
	private static double batteryLevel = 100.0;
	private static boolean batteryIncreasing = false;
	private static boolean batteryDecreasing = false;
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
		batteryDecreasing = true;
		batteryIncreasing = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
            	while (batteryLevel > 0 && batteryIncreasing == false && batteryDecreasing == true) {
                    try {
                       Thread.sleep(1000); // Sleep for 1 second
                       batteryLevel -= 1;
                       batteryLevel = Math.max(0, batteryLevel);
                       publishBatteryLevel(batteryLevel);
                       if(batteryLevel!=0) {
                    	   if(batteryLevel <= 100 && batteryLevel > 50) {
                    		   message = "Started using Battery";
                    		   publishMessage(message);
                    	   }
                    	   if(batteryLevel <= 50 && batteryLevel > 25) {
                    		   message = "Essentail devices only";
                    		   publishMessage(message);
                    	   }else if(batteryLevel <= 25) {
                    		   message = "Power Saving Mode";
                    		   publishMessage(message);
                    	   }
                       }else if(batteryLevel == 0){
                    	   message="Power off";
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
		batteryIncreasing = true;
		batteryDecreasing = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
            	while (batteryLevel <=100 && batteryIncreasing == true && batteryDecreasing == false ) {
                    try {
                       Thread.sleep(500); // Sleep for 0.5 second
                       batteryLevel += 1;
                       batteryLevel = Math.min(100, batteryLevel);
                       if(batteryLevel <= 100.0) {
                    	   publishBatteryLevel(batteryLevel);
                       }
                       if(batteryLevel == 100) {
                   			publishMessage("Charged");
                   		}else {
                   			publishMessage("Charging");
                   		}
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            	
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
	
	private void publishBatteryLevel(double batteryLevel) {
        if (eventAdmin != null) {
            Dictionary<String, Object> properties = new Hashtable<>();
            properties.put("BatteryLevel",batteryLevel);
            Event event = new Event("org/osgi/framework/BundleEvent/batteryLevel", properties);
            eventAdmin.sendEvent(event);
        } else {
            System.out.println("Event admin is null");
        }
    }
}
