package alarmpublisher;

public class Alarm {
	private boolean isRinging;
	
	public Alarm() {
		this.isRinging = false;
	}

	public boolean isRinging() {
		return isRinging;
	}

	public void setRinging(boolean isRinging) {
		this.isRinging = isRinging;
	}
	
}
