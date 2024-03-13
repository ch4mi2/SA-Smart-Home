package temperaturecontrolunit;

import clockpublisher.ClockPublish;
import temperaturesensor.SensorThread;


public class ACImpl implements AC {
	public static final double MILLISECONDS_PER_HOUR = 3600000;
	
	AC unit;
	ClockPublish clock;
	SensorThread sensorThread;
	
	boolean unitOn;
	int power;
	double currentTemperature;
	double requiredTemperature = 28;
	
	public void setPower(int power) {
		this.power = power;
		if( power >= 75 ) {
			sensorThread.changeTemperature(0.15);
		} else if( power < 75 && power >= 50 ) {
			sensorThread.changeTemperature(0.1);
		} else if( power < 50 && power >= 25 ) {
			sensorThread.changeTemperature(0.05);
		} else {
			sensorThread.changeTemperature(0);
		}
	}
	
	@Override
	public void attachClock(ClockPublish clock) {
		this.clock = clock;
	}

	@Override
	public void attachSensorThread(SensorThread sensorThread) {
		this.sensorThread = sensorThread;
	}
	
	@Override
	public void setCurrentTemp(double currentTemperature) {
		this.currentTemperature = currentTemperature;
		if (this.unitOn)
			adjustPower();
	}
	
	private void adjustPower() {
		double time = clock.getMilliSeconds();
		double tempDif = this.currentTemperature - this.requiredTemperature;
		
		if( (11* ACImpl.MILLISECONDS_PER_HOUR) <= time && time < (16 * ACImpl.MILLISECONDS_PER_HOUR)) {
			if (tempDif > 2) {
				setPower(100);
			} else if (tempDif <= 2 && tempDif > 1) {
				setPower(75);
			} else if (tempDif > 0 ) {
				setPower(50);
			} else {
				setPower(0);
			}
		} else {
			if (tempDif > 2) {
				setPower(75);
			} else if (tempDif <= 2 && tempDif > 1) {
				setPower(50);
			} else if (tempDif > 0 ) {
				setPower(25);
			} else {
				setPower(0);
			}
		}
	}

	@Override
	public void turnOff() {
		setPower(0);
		this.unitOn = false;
	}
	
	@Override
	public void turnOn() {
		this.unitOn = true;
	}

	@Override
	public double getCurrentTemp() {
		return this.currentTemperature;
	}

	@Override
	public double getRequiredTemperature() {
		return this.requiredTemperature;
	}

	@Override
	public int getPower() {
		return this.power;
	}

	@Override
	public void setRequiredTemperature(double requiredTemperature) {
		this.requiredTemperature = requiredTemperature;
	}

	@Override
	public boolean getStatus() {
		return this.unitOn;
	}


}
