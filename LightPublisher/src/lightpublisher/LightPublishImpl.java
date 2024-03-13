package lightpublisher;

public class LightPublishImpl implements LightPublish {
    private String state = "off";
    private int brightness;
    private boolean alert = false;

    @Override
    public String turnOn() {
        this.state = "on";
        this.brightness = 100; 
        return "Lights turned on.";
    }

    @Override
    public String turnOff() {
        this.state = "off";
        this.brightness = 0; 
        return "Lights turned off.";
    }

    @Override
    public int increaseBrightness() {
        if (brightness < 100) {
            brightness++;
        }
        return brightness;
    }

    @Override
    public int decreaseBrightness() {
        if (brightness > 0) {
            brightness--;
        }
        return brightness;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public int getBrightness() {
        return brightness;
    }
    
    @Override
    public void setAlert(boolean value) {
    	alert = value;
    }
    
    @Override
    public boolean getAlert() {
    	return alert;
    }

	@Override
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}
}
