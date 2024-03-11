package cctvpublisher;

public class CCTVImpl implements CCTV {
	private boolean isTurnedOn;
	private boolean isDetected;
	
	public CCTVImpl() {
		this.isTurnedOn = false;
		this.isDetected = false;
	}
	
	@Override
	public void turnOn() {
		this.isTurnedOn = true;
	}

	@Override
	public void turnOff() {
		this.isTurnedOn = false;
	}

	@Override
	public void setDetected(boolean isDetected) {
		this.isDetected = isDetected;
	}

	@Override
	public boolean getDetected() {
		return this.isDetected;
	}
	
	@Override
	public boolean getIsTurnedOn() {
		return this.isTurnedOn;
	}

}
