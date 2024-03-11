package coresubscriber;


import javax.swing.JFrame;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import securitysystem.CCTVUI;


public class Activator implements BundleActivator {

	ServiceReference serviceReference;
	ServiceReference serviceReference2;
	ServiceReference<?> cctvUIserviceReference;


	public void start(BundleContext bundleContext) throws Exception {
//		System.out.println("Core started");
//		serviceReference = bundleContext.getServiceReference(LightPublish.class.getName());
//		LightPublish lightPublish = (LightPublish) bundleContext.getService(serviceReference);
//		
//		serviceReference2 = bundleContext.getServiceReference(JFrame.class.getName());
//		
//		JFrame frame = (JFrame) bundleContext.getService(serviceReference2);
//		
//		frame.setVisible(true);
		
		cctvUIserviceReference = bundleContext.getServiceReference(CCTVUI.class.getName());
		CCTVUI cctvUI = (CCTVUI) bundleContext.getService(cctvUIserviceReference);
		cctvUI.startUI();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("core stopped");
		bundleContext.ungetService(serviceReference);
		bundleContext.ungetService(cctvUIserviceReference);
	}

}
