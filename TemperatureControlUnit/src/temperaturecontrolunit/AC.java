package temperaturecontrolunit;

import clockpublisher.ClockPublish;
import temperaturesensor.SensorThread;

interface AC {

	public void turnOff();
	public void turnOn();
	public double getCurrentTemp();
	public double getRequiredTemperature();
	public int getPower();
	public boolean getStatus();
	public String getBatteryStatus();
	public void setPower(int power);
	public void setCurrentTemp(double currentTemperature);
	public void setBatteryStatus(String status);
	public void setRequiredTemperature(double requiredTemperature);
	public void attachClock(ClockPublish clock);
	public void attachSensorThread(SensorThread sensorThread);
	
}
