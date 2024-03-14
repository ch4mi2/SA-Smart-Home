package securitysystem;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import alarmpublisher.Alarm;
import cctvpublisher.CCTV;
import clockpublisher.ClockPublish;    

public class CCTVUI {
	JFrame frame;
	JButton turnOnBtn;
	JButton turnOffBtn;
	JButton detectedBtn;
	JButton notDetectedBtn;
	JButton turnOnAlarmBtn;
	JButton turnOffAlarmBtn;
	JLabel alarmStatusLabel;
	JPanel btnCtrlPanel;
	JLabel cctvOnOffLabel;
	JLabel cctvDetectedLabel;
	JPanel infoPanel;
	JPanel cctvBtnPanel;
	JPanel cctvLabelPanel;
	JPanel alarmBtnPanel;
	JPanel alarmLabelPanel;
	CCTV cctv;
	Alarm alarm;
	ClockPublish clock;
	
	public void attachCCTV(CCTV cctv) {
		this.cctv = cctv;
	}

	public void attachAlarm(Alarm alarm) {
		this.alarm = alarm;
	}
	
	public void attachClock(ClockPublish clock) {
		this.clock = clock;
	} 
	
	public void startUI() {
		try {
			this.frame = new JFrame();
		
			this.turnOnBtn = new JButton("Turn On");
			this.turnOnBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cctv.turnOn();
					detectedBtn.setEnabled(true);
					notDetectedBtn.setEnabled(true);
					cctvOnOffLabel.setText("CCTV Turned On");
					if(cctv.getDetected() == false) {
						cctvDetectedLabel.setText("Intruder not Detected");						
					}
					cctvDetectedLabel.setVisible(true);
				}
			});
			this.turnOffBtn = new JButton("Turn Off");
			this.turnOffBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cctv.turnOff();
					cctv.setDetected(false);
					detectedBtn.setEnabled(false);
					notDetectedBtn.setEnabled(false);
					cctvDetectedLabel.setText("Intruder not Detected");
					cctvDetectedLabel.setVisible(false);
					cctvOnOffLabel.setText("CCTV Turned Off");
				}
			});
			this.detectedBtn = new JButton("Detect");
			this.detectedBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cctv.setDetected(true);
					if(clock != null) {
						System.out.println(clock.getTime() + " : Intruder Detected");
					}
					if(alarm != null) {
						alarm.setRinging(true);	
						alarmStatusLabel.setText("Alarm Ringing");
						if(clock != null) {
							System.out.println(clock.getTime() + " : Alarm Rang");							
						}
					}
					cctvDetectedLabel.setText("Intruder Detected");
				}
			});
			this.notDetectedBtn = new JButton("Not Detect");
			this.notDetectedBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cctv.setDetected(false);
					if(alarm != null) {
						alarm.setRinging(false);
					}
					cctvDetectedLabel.setText("Intruder not Detected");
				}
			});
			this.turnOnAlarmBtn = new JButton("Alarm Turn On");
			this.turnOnAlarmBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					alarm.setRinging(true);
					alarmStatusLabel.setText("Alarm Ringing");
					if(clock != null) {
						System.out.println(clock.getTime() + " : Alarm Rang");
					}
				}
			});
			this.turnOffAlarmBtn = new JButton("Alarm Turn Off");
			this.turnOffAlarmBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					alarm.setRinging(false);
					alarmStatusLabel.setText("Alarm not Ringing");
					if(clock != null) {
						System.out.println(clock.getTime() + " : Alarm Turned off");
					}
					if(cctv != null) {
						cctv.setDetected(false);
					}
				}
			});
			this.btnCtrlPanel = new JPanel();

			this.cctvBtnPanel = new JPanel();
			this.cctvBtnPanel.setBorder(BorderFactory.createTitledBorder("CCTV Controller"));
			this.cctvBtnPanel.add(turnOnBtn);
			this.cctvBtnPanel.add(detectedBtn);
			this.cctvBtnPanel.add(turnOffBtn);
			this.cctvBtnPanel.add(notDetectedBtn);
			this.cctvBtnPanel.setLayout(new GridLayout(2,2,10,10));
			
			
			this.alarmBtnPanel = new JPanel();
			this.alarmBtnPanel.add(turnOnAlarmBtn);
			this.alarmBtnPanel.add(turnOffAlarmBtn);
			this.alarmBtnPanel.setBorder(BorderFactory.createTitledBorder("Alarm Controller"));
			this.alarmBtnPanel.setLayout(new GridLayout(1,2,10,10));

			this.btnCtrlPanel.setLayout(new GridLayout(1,2));
			this.btnCtrlPanel.add(cctvBtnPanel);
			this.btnCtrlPanel.add(alarmBtnPanel);
			
			this.cctvOnOffLabel = new JLabel();
			this.cctvOnOffLabel.setText("CCTV Turned Off");
			this.cctvDetectedLabel = new JLabel();
			this.cctvDetectedLabel.setText("Intruder not Detected");
			this.alarmStatusLabel = new JLabel();
			this.alarmStatusLabel.setText("Alarm not Ringing");
			this.alarmStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
			this.cctvDetectedLabel.setHorizontalAlignment(SwingConstants.CENTER);
			this.alarmStatusLabel.setVerticalAlignment(SwingConstants.CENTER);
			this.cctvDetectedLabel.setVerticalAlignment(SwingConstants.CENTER);
			this.cctvOnOffLabel.setHorizontalAlignment(SwingConstants.CENTER);
			
			this.alarmLabelPanel = new JPanel();
			this.alarmLabelPanel.setBorder(BorderFactory.createTitledBorder("Alarm Status"));
			this.alarmLabelPanel.add(alarmStatusLabel);
			this.alarmLabelPanel.setLayout(new GridLayout(1,1));
			
			this.cctvLabelPanel = new JPanel();
			this.cctvLabelPanel.setBorder(BorderFactory.createTitledBorder("CCTV Status"));
			this.cctvLabelPanel.setLayout(new GridLayout(1,2));
			this.cctvLabelPanel.add(cctvOnOffLabel);
			this.cctvLabelPanel.add(cctvDetectedLabel);
			
			this.infoPanel = new JPanel();
			this.infoPanel.setLayout(new GridLayout(1,2));
			this.infoPanel.add(cctvLabelPanel);
			this.infoPanel.add(alarmLabelPanel);
			
			this.detectedBtn.setEnabled(false);
			this.notDetectedBtn.setEnabled(false);
			this.cctvDetectedLabel.setVisible(false);
			
			
			if(this.alarm == null) {
				this.turnOffAlarmBtn.setVisible(false);
				this.turnOnAlarmBtn.setVisible(false);
				this.alarmStatusLabel.setVisible(false);
			}
			
			if(this.cctv == null) {
				this.notDetectedBtn.setVisible(false);
				this.detectedBtn.setVisible(false);
				this.turnOffBtn.setVisible(false);
				this.turnOnBtn.setVisible(false);
				this.cctvOnOffLabel.setVisible(false);
				this.cctvDetectedLabel.setVisible(false);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void stopUI() {
		this.frame.setVisible(false);
	}
	
	public JPanel getInfoPanel() {
		return this.infoPanel;
	}
	
	public JPanel getBtnPanel() {
		return this.btnCtrlPanel;
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	public boolean setAlarm(boolean val) {
		if(this.alarm != null) {
			this.alarm.setRinging(val);
			if(val) {
				alarmStatusLabel.setText("Alarm Ringing");		
			}else {
				alarmStatusLabel.setText("Alarm not Ringing");
			}
			return this.alarm.isRinging();
		} 
		return false;
	}
	
	public boolean getAlarmStatus() {
		return this.alarm.isRinging();
	}
	

	
}
