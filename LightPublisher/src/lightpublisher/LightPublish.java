package lightpublisher;

public interface LightPublish {
    public String turnOn();
    public String turnOff();
    public int increaseBrightness();
    public int decreaseBrightness();
    public String getState();
    public int getBrightness();
    public void setAlert(boolean value);
    public boolean getAlert();
    public void setBrightness(int brightness);
}
