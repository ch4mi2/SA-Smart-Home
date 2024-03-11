package securitysystem;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import cctvpublisher.CCTV;
import cctvpublisher.CCTVImpl;    

public class CCTVUI {
	JFrame frame;
	JButton turnOn;
	JButton turnOff;
	JButton detected;
	JButton notDetected;
	JPanel btnCtrlPanel;
	JLabel cctvOnOff;
	JLabel cctvDetected;
	JPanel infoPanel;
	CCTV cctv;
	
	public void attachCCTV(CCTV cctv) {
		this.cctv = cctv;
	}

	public void startUI() {
		
		this.frame = new JFrame();
		
		this.turnOn = new JButton("Turn On");
		this.turnOn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cctv.turnOn();
				detected.setEnabled(true);
				notDetected.setEnabled(true);
				cctvOnOff.setText("CCTV Turned On");
			}
		});
		this.turnOff = new JButton("Turn Off");
		this.turnOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cctv.turnOff();
				cctv.setDetected(false);
				detected.setEnabled(false);
				notDetected.setEnabled(false);
				cctvDetected.setText("");
				cctvOnOff.setText("CCTV Turned Off");
			}
		});
		this.detected = new JButton("Detect");
		this.detected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cctv.setDetected(true);
				cctvDetected.setText("Intruder Detected");
			}
		});
		this.notDetected = new JButton("Not Detect");
		this.notDetected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cctv.setDetected(false);
				cctvDetected.setText("Intruder not Detected");
			}
		});
		this.btnCtrlPanel = new JPanel();
		
		this.cctvOnOff = new JLabel();
		this.cctvDetected = new JLabel();
		this.cctvDetected.setHorizontalAlignment(SwingConstants.CENTER);
		this.cctvOnOff.setHorizontalAlignment(SwingConstants.CENTER);
		this.infoPanel = new JPanel();
		
		//this.btnCtrlPanel.setBounds(10,10,200,200);  
		this.btnCtrlPanel.add(turnOn);
		this.btnCtrlPanel.add(detected);
		this.btnCtrlPanel.add(turnOff);
		this.btnCtrlPanel.add(notDetected);
		this.btnCtrlPanel.setLayout(new GridLayout(2,2));
		
		//this.infoPanel.setBounds(10,10,200,200); 
		this.infoPanel.setLayout(new GridLayout(1,2));
		this.infoPanel.add(cctvOnOff);
		this.infoPanel.add(cctvDetected);
		
	    this.frame.setSize(500,500);  
	    this.frame.setLayout(new GridLayout(2,1));  
	    this.frame.add(this.infoPanel);
	    this.frame.add(this.btnCtrlPanel);
	    this.frame.setVisible(true); 
	}
	
	public void stopUI() {
		this.frame.setVisible(false);
	}
	
}
