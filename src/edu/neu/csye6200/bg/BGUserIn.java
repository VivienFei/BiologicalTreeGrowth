package edu.neu.csye6200.bg;

/**
 * A UserInterface
 *
 * @author fjj1213
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;//20171124
import javax.swing.JTextField;//20171124
import javax.swing.JLabel;//20171124

public class BGUserIn extends BGApp {

    private static Logger log = Logger.getLogger(BGUserIn.class.getName());

    protected JPanel mainPanel = null;
    protected JPanel northPanel = null;
    protected JButton startBtn = null;
    protected JButton stopBtn = null;
    protected JButton pauseBtn = null;
    protected JButton resumeBtn = null;

    private static BGCanvas bgPanel = null;

    protected static JComboBox<Object> ruleComb = null;
    protected static JTextField countTxt = null;
    protected JLabel countLabel = null;

    private BGGenerationSet bgGenerationSet = null;

    /**
     * constructor
     */
    public BGUserIn() {

        frame.setSize(1500, 800); // initial Frame size
        frame.setTitle("BGUserInterfaceApp");
        menuMgr.createDefaultActions(); // Set up default menu items	
        showUI(); // Cause the Swing Dispatch thread to display the JFrame

    }

    /**
     * BGUserIn application starting point
     *
     * @param args
     */
    public static void main(String[] args) {

        BGUserIn wapp = new BGUserIn();
        log.info("BG User Interface started");
    }

    /**
     * Create a main panel that will hold the bulk of our application display
     */
    @Override
    public JPanel getMainPanel() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(BorderLayout.NORTH, getNorthPanel());

        bgPanel = new BGCanvas();
        mainPanel.add(BorderLayout.CENTER, bgPanel);
        frame.setVisible(true);
        //bgPanel.setBackground(Color.white);

        return mainPanel;
    }

    /**
     * Create a top panel that will hold control buttons
     *
     * @return
     */
    public JPanel getNorthPanel() {
        northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        northPanel.setBackground(Color.orange);

        startBtn = new JButton("Start");
        startBtn.addActionListener(this);
        startBtn.setFocusPainted(false);
        northPanel.add(startBtn);

        stopBtn = new JButton("Stop"); // Allow the app to hear about button pushes
        stopBtn.addActionListener(this);
        northPanel.add(stopBtn);

        pauseBtn = new JButton("Pause"); // Allow the app to hear about button
        // pushes
        pauseBtn.addActionListener(this);
        northPanel.add(pauseBtn);

        resumeBtn = new JButton("Resume"); // Allow the app to hear about button
        // pushes
        resumeBtn.addActionListener(this);
        northPanel.add(resumeBtn);

        countLabel = new JLabel("Generation count:"); // Allow the app to hear Input button pushes
        northPanel.add(countLabel);

        countTxt = new JTextField("8", 2); // Allow the app to hear Input button pushes
        countTxt.addActionListener(this);
        northPanel.add(countTxt);

        final String def[] = {"Rule1", "Rule2", "Rule3"};
        ruleComb = new JComboBox<Object>(def);

        ruleComb.setMaximumRowCount(5);
        ruleComb.setEditable(false);
        ruleComb.addActionListener(this); // Allow the app to hear about combox pushes
        northPanel.add(ruleComb);

        return northPanel;
    }

    @Override
    //The related action for the buttons
    public void actionPerformed(ActionEvent ae) {
        log.info("We received an ActionEvent " + ae);

        if ("Start".equals(ae.getActionCommand())) {

            int maxGen = Integer.parseInt(countTxt.getText());
            String rule = ruleComb.getSelectedItem().toString();
            start(maxGen, rule);
        } else if ("Stop".equals(ae.getActionCommand())) {

            stop();
        } else if ("Pause".equals(ae.getActionCommand())) {

            pause();
        } else if ("Resume".equals(ae.getActionCommand())) {

            resume();
        }

    }

    public void start(int maxGen, String rule) {

        bgGenerationSet = new BGGenerationSet();
        bgGenerationSet.setMaxGen(maxGen);
        BGRule bgRule = null;
        if ("Rule1".equals(rule)) {
            bgRule = new BGRule(1, 2, 30);
        } else if ("Rule2".equals(rule)) {
            bgRule = new BGRule(1, 3, 15);
        } else if ("Rule3".equals(rule)) {
            bgRule = new BGRule(1, 3, 40);
        }

        Position start = new Position(0.0, 0.0);
        double stLength = 10.0;
        double stAngle = 0 * Math.PI / 180.0;
        BGStem rootStem = new BGStem(start, stLength, stAngle);
        BGGeneration bgGeneration = new BGGeneration(bgRule, rootStem);

        bgGenerationSet.init(bgGeneration);
        bgGenerationSet.run();
        bgPanel.setBgGenerationSet(bgGenerationSet);

        bgPanel.paint(bgPanel.getGraphics());
        bgPanel.drawBG(bgPanel.getGraphics(), true);

    }

    public void stop() {
        bgGenerationSet.setDone(true);
        bgPanel.setSleep(false);

    }
    
    public void pause() {
		//bgGenerationSet.setDone(false);
		bgPanel.setPause(true);

	}
	public void resume() {
		//bgGenerationSet.setDone(false);
		
		bgPanel.getDraw().resume();
	}

    @Override
    public void windowOpened(WindowEvent e) {
        log.info("Window opened");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        log.info("Window closing");
    }

    @Override
    public void windowClosed(WindowEvent e) {
        log.info("Window closed");
    }

    @Override
    public void windowIconified(WindowEvent e) {
        log.info("Window iconified");
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        log.info("Window deiconified");
    }

    @Override
    public void windowActivated(WindowEvent e) {
        log.info("Window activated");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        log.info("Window deactivated");
    }

}
