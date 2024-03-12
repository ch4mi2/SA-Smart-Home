package securitysystem;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import alarmpublisher.Alarm;
import cctvpublisher.CCTV;
import cctvpublisher.CCTVImpl;    

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
	CCTV cctv;
	Alarm alarm;
	
	public void attachCCTV(CCTV cctv) {
		this.cctv = cctv;
	}

	public void attachAlarm(Alarm alarm) {
		this.alarm = alarm;
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
				cctvDetectedLabel.setText("");
				cctvOnOffLabel.setText("CCTV Turned Off");
			}
		});
		this.detectedBtn = new JButton("Detect");
		this.detectedBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cctv.setDetected(true);
				if(alarm != null) {
					alarm.setRinging(true);					
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
			}
		});
		this.turnOffAlarmBtn = new JButton("Alarm Turn Off");
		this.turnOffAlarmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alarm.setRinging(false);
				cctv.setDetected(false);
			}
		});
		this.btnCtrlPanel = new JPanel();
		//this.btnCtrlPanel.setBounds(10,10,200,200);  
		this.btnCtrlPanel.add(turnOnBtn);
		this.btnCtrlPanel.add(detectedBtn);
		this.btnCtrlPanel.add(turnOnAlarmBtn);
		this.btnCtrlPanel.add(turnOffBtn);
		this.btnCtrlPanel.add(notDetectedBtn);
		this.btnCtrlPanel.add(turnOffAlarmBtn);
		this.btnCtrlPanel.setLayout(new GridLayout(2,3));
		
		this.cctvOnOffLabel = new JLabel();
		this.cctvDetectedLabel = new JLabel();
		this.alarmStatusLabel = new JLabel();
		this.alarmStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.cctvDetectedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.cctvOnOffLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.infoPanel = new JPanel();
		//this.infoPanel.setBounds(10,10,200,200); 
		this.infoPanel.setLayout(new GridLayout(1,3));
		this.infoPanel.add(cctvOnOffLabel);
		this.infoPanel.add(cctvDetectedLabel);
		this.infoPanel.add(alarmStatusLabel);
		
//	    this.frame.setSize(500,500);  
//	    this.frame.setLayout(new GridLayout(2,1));  
//	    this.frame.add(this.infoPanel);
//	    this.frame.add(this.btnCtrlPanel);
//	    this.frame.setVisible(true); 
		
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
	
	
}
