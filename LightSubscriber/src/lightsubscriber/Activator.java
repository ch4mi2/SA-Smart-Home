package lightsubscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import lightpublisher.LightPublish;
import lightpublisher.LightPublishImpl;
import securitysystem.CCTVUI;
import clockpublisher.ClockPublish;

public class Activator extends JPanel implements BundleActivator {
	ServiceRegistration publishServiceRegistration;
    private JLabel stateLabel;
    private JLabel brightnessLabel;
    private JLabel timeLabel;
    private JButton turnOnButton;
    private JButton turnOffButton;
    private JButton increaseBrightnessButton;
    private JButton decreaseBrightnessButton;
    private JPanel colorPanel;
    private ServiceReference lightServiceReference;
    private ServiceReference clockServiceReference;
    private ServiceReference cctvUIserviceReference;
    private LightPublish lightPublish;
    private ClockPublish clockPublish;
    private boolean lightOverride = false;
    private boolean partyMode = false; // Flag for party mode
    
    private JTextField dayTimeField;
    private JTextField nightTimeField;
    private int dayTime = 6;  // Default day time is 6 AM
    private int nightTime = 18; // Default night time is 6 PM

    private JCheckBox lightOverrideCheckbox;
    private JToggleButton partyModeToggle; // Toggle for party mode

    public Activator() {
        
        stateLabel = new JLabel("State: ");
        brightnessLabel = new JLabel("Brightness: ");
        timeLabel = new JLabel("Time: ");
        turnOnButton = new JButton("Turn On");
        turnOffButton = new JButton("Turn Off");
        increaseBrightnessButton = new JButton("Increase Brightness");
        decreaseBrightnessButton = new JButton("Decrease Brightness");
        colorPanel = new JPanel();
        colorPanel.setPreferredSize(new Dimension(200, 200));

        JPanel timeSettingsPanel = new JPanel(new GridLayout(1, 1, 1, 1));
        timeSettingsPanel.setBorder(BorderFactory.createTitledBorder("Time Settings"));
        dayTimeField = new JTextField(2);
        nightTimeField = new JTextField(2);
        JButton saveTimesButton = new JButton("Save Times");
        timeSettingsPanel.add(timeLabel, BorderLayout.NORTH);
        //timeSettingsPanel.add(emptyLabel);
        timeSettingsPanel.add(new JLabel("Turn off by:"));
        timeSettingsPanel.add(dayTimeField);
        timeSettingsPanel.add(new JLabel("Turn on by:"));
        timeSettingsPanel.add(nightTimeField);
        timeSettingsPanel.add(saveTimesButton);

        saveTimesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    dayTime = Integer.parseInt(dayTimeField.getText());
                    nightTime = Integer.parseInt(nightTimeField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input for day or night time.");
                }
            }
        });

        lightOverrideCheckbox = new JCheckBox("Manual Control");
        lightOverrideCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lightOverride = lightOverrideCheckbox.isSelected();
            }
        });

        partyModeToggle = new JToggleButton("Party Mode"); // Toggle button for party mode
        partyModeToggle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                partyMode = partyModeToggle.isSelected();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Light Controls"));
        buttonPanel.add(turnOnButton);
        buttonPanel.add(turnOffButton);
        buttonPanel.add(increaseBrightnessButton);
        buttonPanel.add(decreaseBrightnessButton);
        buttonPanel.add(partyModeToggle);

        turnOnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lightOverride = true;
                lightOverrideCheckbox.setSelected(true);
                lightPublish.turnOn();
                updateLabelsAndColor();
            }
        });

        turnOffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lightOverride = true;
                lightOverrideCheckbox.setSelected(true);
                lightPublish.turnOff();
                updateLabelsAndColor();
            }
        });

        increaseBrightnessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lightPublish.increaseBrightness();
                updateLabelsAndColor();
            }
        });

        decreaseBrightnessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lightPublish.decreaseBrightness();
                updateLabelsAndColor();
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(stateLabel, BorderLayout.NORTH);
        mainPanel.add(brightnessLabel, BorderLayout.CENTER);
        
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(timeSettingsPanel, BorderLayout.EAST);
        mainPanel.add(lightOverrideCheckbox, BorderLayout.SOUTH);
       

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(colorPanel, BorderLayout.WEST);
    }

    private void updateLabelsAndColor() {
        stateLabel.setText("State: " + lightPublish.getState());
        brightnessLabel.setText("Brightness: " + lightPublish.getBrightness());
        updateColor();
    }

    private void startTimer() {
        Thread updateTimeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String time = clockPublish.getTime();

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            timeLabel.setText("Time: " + time);
                        }
                    });

                    int hour = Integer.parseInt(time.substring(0, 2));
                    boolean isNightTime = (hour >= nightTime || hour < dayTime);

                    if (isNightTime && !lightOverride && (lightPublish.getState() == "off")) {
                        if (!partyMode) // Normal mode
                            lightPublish.turnOn();
                    } else if (!isNightTime && !lightOverride && (lightPublish.getState() == "on")) {
                        if (!partyMode) // Normal mode
                            lightPublish.turnOff();
                    }
                    
                    
                    updateLabelsAndColor();

                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        updateTimeThread.start();
    }

    private void updateColor() {
        if (partyMode) {
            // Continuous color change for party mode
            float hue = (System.currentTimeMillis() % 10000) / 10000f; // Continuous change based on time
            Color color = Color.getHSBColor(hue, 1f, lightPublish.getBrightness() / 100f);
            colorPanel.setBackground(color);
        } else {
            // Normal mode color
            Color color = Color.getHSBColor(60f / 360f, 1f, lightPublish.getBrightness() / 100f);
            colorPanel.setBackground(color);
        }
    }

    
    private void checkAlert(CCTVUI cctvUI) {
    	
        Thread alertThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (cctvUI.getAlarmStatus()) {
                        Color color = Color.RED; // Set color to red
                        colorPanel.setBackground(color);
                    }
                    try {
                        Thread.sleep(0); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        alertThread.start();
    }


    public void start(BundleContext context) throws Exception {
        JFrame frame = new JFrame("Light Subscriber");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);

        lightServiceReference = context.getServiceReference(LightPublish.class.getName());
        clockServiceReference = context.getServiceReference(ClockPublish.class.getName());
        cctvUIserviceReference = context.getServiceReference(CCTVUI.class.getName());
		CCTVUI cctvUI = (CCTVUI) context.getService(cctvUIserviceReference);

        if (lightServiceReference != null && clockServiceReference != null) {
            lightPublish = (LightPublish) context.getService(lightServiceReference);
            clockPublish = (ClockPublish) context.getService(clockServiceReference);
            updateLabelsAndColor();
            startTimer();
            checkAlert(cctvUI);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to retrieve service.");
        }
    }

    public void stop(BundleContext context) throws Exception {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.dispose();
        }
        context.ungetService(lightServiceReference);
        context.ungetService(clockServiceReference);
    }
}
