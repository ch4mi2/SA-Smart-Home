package cctvpublisher;

public interface CCTV {
	public void turnOn();
	public void turnOff();
	public void setDetected(boolean isDetected);
	public boolean getDetected();
	public boolean getIsTurnedOn();
}
