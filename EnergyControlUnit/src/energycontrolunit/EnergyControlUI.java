package energycontrolunit;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import batteryproducer.Battery;

public class EnergyControlUI {
	JPanel energyManagementPanel;
	JPanel currentSolarPanel;
	JPanel solarStatusPanel;
	JPanel batteryPanel;
	JPanel batteryStatusPanel;
	JLabel currentSolarStatus;
	JLabel batteryLevel;
	JLabel batteryStatus;
	
//
	public void startUI() {	
		currentSolarPanel = new JPanel();
		currentSolarStatus = new JLabel();
		currentSolarStatus.setHorizontalAlignment(SwingConstants.CENTER);
		currentSolarPanel.setBorder(BorderFactory.createTitledBorder("Current Solar Status"));
		currentSolarPanel.add(currentSolarStatus);
		
		batteryPanel = new JPanel();
		batteryLevel = new JLabel();
		batteryLevel.setHorizontalAlignment(SwingConstants.CENTER);
		batteryPanel.setBorder(BorderFactory.createTitledBorder("Battery Level"));
		batteryPanel.add(batteryLevel);
		
		batteryStatusPanel = new JPanel();
		batteryStatus = new JLabel();
		batteryStatus.setHorizontalAlignment(SwingConstants.CENTER);
		batteryStatusPanel.setBorder(BorderFactory.createTitledBorder("Battery Status"));
		batteryStatusPanel.add(batteryStatus);
				
		energyManagementPanel = new JPanel();
		energyManagementPanel.setBorder(BorderFactory.createTitledBorder("Energy Control Unit"));
		energyManagementPanel.add(currentSolarPanel);
		energyManagementPanel.add(batteryPanel);
		energyManagementPanel.add(batteryStatusPanel);
		energyManagementPanel.setLayout(new GridLayout(3,1, 15,15));
//
	}
//	
	public void updateBatteryLevel(Double level) {
		if(batteryLevel != null) {
			batteryLevel.setText(level.toString());
		}	
    }
//	
	public void updateSolarStatus(boolean status) {
		if(currentSolarStatus != null) {
			if(status == true) {
				currentSolarStatus.setText("Active");
			}else {
				currentSolarStatus.setText("Inactive");
			}
		}	
    }
//	
	public void updateBatteryStatus(String status) {
		if(batteryStatus!=null) {
			batteryStatus.setText(status);
		}
    }
	
//	public void updateUI(boolean solarStatus) {
//		if(currentSolarStatus != null) {
//			if (solarStatus == true) {
//				currentSolarStatus.setText("solar true");
//			} else {
//        	currentSolarStatus.setText("solar false");
//			}
//		}	
//    }
	
//	public void stopUI() {
//		this.frame.setVisible(false);
//	}
//	
	public JPanel getStatusPanel() {
		return this.energyManagementPanel;
	}
//	
}
