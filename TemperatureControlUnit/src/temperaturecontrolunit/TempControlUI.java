package temperaturecontrolunit;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TempControlUI {
	JFrame frame;
	JPanel statsPanel;
	JPanel targetTempPanel;
	JPanel currentTempPanel;
	JPanel powerPanel;
	JLabel targetTemp;
	JLabel currentTemp;
	JLabel power;
	JPanel commandPanel;
	JTextField inTargetTemp;
	JButton unitToggle;
	JPanel tempPanel;
	AC unit;
	
	public void attachUnit(AC unit) {
		this.unit = unit;
	}

	public void startUI() { 
		frame = new JFrame("Tenmperature Control Unit");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		targetTempPanel = new JPanel();
		targetTemp = new JLabel();
		targetTemp.setHorizontalAlignment(SwingConstants.CENTER);
		targetTemp.setText(String.format("%.2f",unit.getRequiredTemperature()) + " C");
		targetTempPanel.setBorder(BorderFactory.createTitledBorder("Target Temperature"));
		targetTempPanel.add(targetTemp);
		
		currentTempPanel = new JPanel();
		currentTemp = new JLabel();
		currentTemp.setHorizontalAlignment(SwingConstants.CENTER);
		currentTempPanel.setBorder(BorderFactory.createTitledBorder("Current Temperature"));
		currentTempPanel.add(currentTemp);
		
		powerPanel = new JPanel();
		power = new JLabel();
		power.setText("0");
		power.setHorizontalAlignment(SwingConstants.CENTER);
		powerPanel.setBorder(BorderFactory.createTitledBorder("Working Power"));
		powerPanel.add(power);
		
		statsPanel = new JPanel();
		statsPanel.add(targetTempPanel);
		statsPanel.add(currentTempPanel);
		statsPanel.add(powerPanel);
		statsPanel.setLayout(new GridLayout(1,3,10,10));
		
		commandPanel = new JPanel();
		unitToggle = new JButton();
		unitToggle.setText(setButtonText(unit.getStatus()));
		inTargetTemp = new JTextField();
		inTargetTemp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!inTargetTemp.getText().isEmpty()) {
					unit.setRequiredTemperature(Double.parseDouble(inTargetTemp.getText()));
					targetTemp.setText(String.format("%.2f",unit.getRequiredTemperature()) + " C");
					inTargetTemp.setText("");
				}
			}
		});
		unitToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(unit.getStatus()) {
					unit.turnOff();
				} else {
					unit.turnOn();
				}
				unitToggle.setText(setButtonText(unit.getStatus()));
			}
		});
		commandPanel.add(inTargetTemp);
		commandPanel.add(unitToggle);
		commandPanel.setLayout(new GridLayout(1,2,10,10));
		
		tempPanel = new JPanel();
		tempPanel.setBorder(BorderFactory.createTitledBorder("Temperature Control Unit"));
		tempPanel.add(statsPanel);
		tempPanel.add(commandPanel);
		tempPanel.setLayout(new GridLayout(2,1, 20,20));
	}
	
	public void updateUI() {			
		if(currentTemp != null)
			currentTemp.setText(String.format("%.2f",unit.getCurrentTemp()) + " C");
		if(power != null)
			power.setText(Integer.toString(unit.getPower())+ " W");
	}
	
	public void stopUI() {
		this.frame.setVisible(false);
	}
	
	public JPanel getTempPanel() {
		return this.tempPanel;
	}
	
	private String setButtonText(boolean condition) {
		if (condition) {
			return "Turn Off";
		} else {
			return "Turn On";
		}
	}
}
