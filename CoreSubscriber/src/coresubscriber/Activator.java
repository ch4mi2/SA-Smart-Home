package coresubscriber;


import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import clockpublisher.ClockPublish;
import securitysystem.CCTVUI;


public class Activator implements BundleActivator {

	ServiceReference<?> clockServiceReference;
	ServiceReference<?> serviceReference2;
	ServiceReference<?> cctvUIserviceReference;
	JFrame mainFrame = new JFrame();
	ClockPublish clock;


	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Core started");
//		serviceReference = bundleContext.getServiceReference(LightPublish.class.getName());
//		LightPublish lightPublish = (LightPublish) bundleContext.getService(serviceReference);
//		
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
			mainFrame.add(SecurityPanel);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		// Clock Service
		try {
			clockServiceReference = bundleContext.getServiceReference(ClockPublish.class.getName());
			clock =  (ClockPublish) bundleContext.getService(clockServiceReference);
			JLabel time = new JLabel();
			Thread thread = new Thread(() -> {
				while(true) {
					time.setText(clock.getTime());
				}
			});
			thread.start();
			mainFrame.add(time);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		mainFrame.setSize(500,500);  
		mainFrame.setLayout(new GridLayout(2,1));  
		mainFrame.setVisible(true);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("core stopped");
		bundleContext.ungetService(clockServiceReference);
		bundleContext.ungetService(cctvUIserviceReference);
	}

}
