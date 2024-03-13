package clockpublisher;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockPublishImpl implements ClockPublish {

    private static final long SIMULATED_DAY_MILLISECONDS = 20000; //change this to the amount you want
    private long startTime;

    public ClockPublishImpl() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public String getTime() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        // Calculate the simulated time based on elapsed time and scale factor
        long simulatedTime = (elapsedTime * 86400000) / SIMULATED_DAY_MILLISECONDS; // 86400000 ms = 24 hours
        
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(new Date(simulatedTime));
    }
    
    public double getMilliSeconds() {
    	return System.currentTimeMillis() - startTime;
    }
}
