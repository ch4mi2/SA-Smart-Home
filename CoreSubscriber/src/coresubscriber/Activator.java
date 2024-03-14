package coresubscriber;


import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import lightpublisher.LightPublish;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import clockpublisher.ClockPublish;
import energycontrolunit.EnergyControlUI;
import securitysystem.CCTVUI;
import temperaturecontrolunit.TempControlUI;


public class Activator implements BundleActivator {

	ServiceReference<?> clockServiceReference;
	ServiceReference<?> lightServiceReference;
	ServiceReference<?> cctvUIserviceReference;
	ServiceReference<?> temperatureControlReference;
	ServiceReference<?> energyControlReference;
	JFrame mainFrame = new JFrame("Smart Home Dashboard");
	JPanel container = new JPanel();
	ClockPublish clock;


	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Core started");
		lightServiceReference = bundleContext.getServiceReference(LightPublish.class.getName());
		LightPublish lightPublish = (LightPublish) bundleContext.getService(lightServiceReference);
		
		// Clock Service
		try {
			clockServiceReference = bundleContext.getServiceReference(ClockPublish.class.getName());
			clock =  (ClockPublish) bundleContext.getService(clockServiceReference);
			JLabel time = new JLabel();
			time.setHorizontalAlignment(SwingConstants.CENTER);
			time.setVerticalAlignment(SwingConstants.CENTER);
			Thread thread = new Thread(() -> {
				while(true) {
					time.setText(clock.getTime());
				}
			});
			thread.start();
			container.add(time);
			container.add(Box.createVerticalGlue());
		} catch (Exception e) {
			System.out.println(e);
		}
		
		//energy Service
		try {
			energyControlReference = bundleContext.getServiceReference(EnergyControlUI.class.getName());
			EnergyControlUI UI = (EnergyControlUI) bundleContext.getService(energyControlReference);
			UI.startUI();
			container.add(UI.getStatusPanel());
		}catch(Exception e) {
			System.out.println(e);
		}
		
		// Security Service
		try {
			cctvUIserviceReference = bundleContext.getServiceReference(CCTVUI.class.getName());
			CCTVUI cctvUI = (CCTVUI) bundleContext.getService(cctvUIserviceReference);
			cctvUI.startUI();
			JPanel SecurityPanel = new JPanel();
			SecurityPanel.setLayout(new GridLayout(2,1));
			SecurityPanel.setSize(300,300);  
			SecurityPanel.add(cctvUI.getInfoPanel());
			SecurityPanel.add(cctvUI.getBtnPanel());
			SecurityPanel.setBorder(BorderFactory.createTitledBorder("Security Control Unit"));
			container.add(SecurityPanel);
			container.add(Box.createVerticalGlue());
		} catch (Exception e) {
			System.out.println(e);
		}
		
		// TCU 
		try {
			temperatureControlReference = bundleContext.getServiceReference(TempControlUI.class.getName());
			TempControlUI tempUI = (TempControlUI) bundleContext.getService(temperatureControlReference);
			tempUI.startUI();
			container.add(tempUI.getTempPanel());
			container.add(Box.createVerticalGlue());
		} catch(NullPointerException e) {
			System.out.println(e.getLocalizedMessage() + ": TCU not availabe");
		}
		
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); 
		
		mainFrame.setSize(800,800);  
		mainFrame.add(container);
		mainFrame.setVisible(true);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("core stopped");
		bundleContext.ungetService(clockServiceReference);
		bundleContext.ungetService(lightServiceReference);
		bundleContext.ungetService(cctvUIserviceReference);
		bundleContext.ungetService(temperatureControlReference);
	}

}
