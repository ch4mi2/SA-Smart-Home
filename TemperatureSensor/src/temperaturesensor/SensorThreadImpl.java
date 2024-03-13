package temperaturesensor;

public class SensorThreadImpl implements SensorThread {
	
	SensorComponent sensorThread;
	
	public SensorThreadImpl(SensorComponent sensorThread) {
		this.sensorThread = sensorThread;
	}

	@Override
	public void changeTemperature(double changedAmount) {
		sensorThread.simulateAC(changedAmount);
	}

}
