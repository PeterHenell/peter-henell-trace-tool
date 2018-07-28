package databasetracing.application.gui;

import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import databasetracing.tracing.TraceManager;
import databasetracing.tracing.TraceResult;
import databasetracing.tracing.sources.PostgresLogTraceSource;
import databasetracing.tracing.sources.TraceSource;
import databasetracing.transformers.ImageTransformer;
import databasetracing.transformers.SqlTextTransformer;

public class TraceRunnerPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 7333865059995067641L;

    private JTextField traceOutputFolder;

    private TraceManager traceManager;
    private Timer timer;
    protected final static int ONE_SECOND = 1000;
    private final TraceGUI traceGUI;

    protected final static String START_TRACE_COMMAND = "start_trace";
    protected final static String STOP_TRACE_COMMAND = "stop_trace";
    private static final String START_BUTTON_DEFAULT_TEXT = "Start";

    private JButton stopButton;

    private JButton startButton;
    private final UUID traceId;
    private JCheckBox chckbxNewCheckBox;
    private JLabel infoLabel;

    private int eventCount;
    private JRadioButton rdbtnTextOput;
    private JRadioButton rdbtnTextAndImage;
    private final ButtonGroup buttonGroup = new ButtonGroup();


    // StartTraceTask startTraceTask = new StartTraceTask();

    public TraceRunnerPanel(TraceGUI traceGUI) {
        this.traceGUI = traceGUI;
        this.traceId = UUID.randomUUID();
        startButton = new JButton(START_BUTTON_DEFAULT_TEXT);
        startButton.addActionListener(this);
        startButton.setActionCommand(START_TRACE_COMMAND);
        add(startButton);

        stopButton = new JButton("Stop");
        stopButton.setActionCommand(STOP_TRACE_COMMAND);
        stopButton.addActionListener(this);
        add(stopButton);
        stopButton.setEnabled(false);

        traceOutputFolder = new JTextField();
        traceOutputFolder.setSize(new Dimension(220, 20));
        add(traceOutputFolder);
        traceOutputFolder.setText("C:\\temp\\traces\\");

        chckbxNewCheckBox = new JCheckBox("Popup");
        add(chckbxNewCheckBox);

        rdbtnTextOput = new JRadioButton("Text output");
        buttonGroup.add(rdbtnTextOput);
        add(rdbtnTextOput);

        rdbtnTextAndImage = new JRadioButton("Text and Image");
        buttonGroup.add(rdbtnTextAndImage);
        rdbtnTextAndImage.setSelected(true);
        add(rdbtnTextAndImage);

        infoLabel = new JLabel("");
        infoLabel.setForeground(SystemColor.textHighlight);
        infoLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        add(infoLabel);

        timer = new Timer(ONE_SECOND, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                eventCount = traceManager.getEventCount();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        updateEventCount(eventCount);
                    }
                });
            }
        });

    }


    private void updateStatusText(final String text) {
        // System.out.println("setting updateStatusText " + text);
        infoLabel.setText(text);
        this.revalidate();
        this.repaint();
    }


    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (START_TRACE_COMMAND.equals(command)) {
            toggleButtons(false);
            StartTraceTask startTraceTask = new StartTraceTask();
            startTraceTask.execute();
        }
        if (STOP_TRACE_COMMAND.equals(command)) {
            toggleButtons(true);
            StopTraceTask stopTraceTask = new StopTraceTask();
            stopTraceTask.execute();
        }
    }


    private void toggleButtons(boolean startButtonEnable) {
        startButton.setEnabled(startButtonEnable);
        stopButton.setEnabled(!startButtonEnable);
    }


    private void updateEventCount(int eventCount) {
        startButton.setText(START_BUTTON_DEFAULT_TEXT + " [" + eventCount + "]");
    }


    private void startTimer() {
        timer.start();
    }


    private void stopTimer() {
        timer.stop();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                startButton.setText(START_BUTTON_DEFAULT_TEXT);
            }
        });
    }


    public void cleanup() {
        if (null != traceManager) {
            traceManager.cleanup();
        }
    }

    private class StartTraceTask extends SwingWorker<Void, String> {

        @Override
        protected Void doInBackground() {
            startTrace();
            return null;
        }


        private void startTrace() {
            publish("Starting trace...");
            eventCount = 0;

            TraceGUIConfiguration config = traceGUI.getTraceConfigurationPanel().getConfig();
            String serverName = config.getServerName();
            String databaseName = config.getDbName();
            String userName = config.getUserName();
            String password = config.getPassword();

            String outputFolder = traceOutputFolder.getText();
            if (null != traceManager) {
                traceManager.cleanup();
            }
            // need to recreate the tracemanager every time it is started in case any of the parameters have changed.
            TraceSource source = getTraceSource(serverName, databaseName, userName, password);
            traceManager = new TraceManager(source, outputFolder);
            try {
                traceManager.startTrace();
            } catch (Exception e) {
                publish("error");
                return;
            }

            startTimer();
            publish("running...");
        }


        private TraceSource getTraceSource(String serverName, String databaseName, String userName, String password) {
            // SQLServerDatabaseQueryable sqlConnection = new SQLServerDatabaseQueryable(serverName, databaseName, userName, password);
            // TraceSource source = new SQLServerTraceSource(sqlConnection, traceId);
            TraceSource source = new PostgresLogTraceSource(Paths.get(serverName), databaseName);
            return source;
        }


        @Override
        protected void process(List<String> chunks) {
            for (String text : chunks) {
                updateStatusText(text);
            }
        }
    }

    private class StopTraceTask extends SwingWorker<Void, String> {

        @Override
        protected Void doInBackground() {
            stopTrace();
            return null;
        }


        private void stopTrace() {
            System.out.println("Stopping trace");
            publish("Stopping trace...");
            stopTimer();

            traceManager.stopTrace();
            TraceResult traceResult = traceManager.collectTraceResult("GUI Trace");
            publish("saving trace results...");

            if (rdbtnTextAndImage.isSelected()) {
                traceManager.saveAllResults(new SqlTextTransformer(), new ImageTransformer());
            } else {
                traceManager.saveAllResults(new SqlTextTransformer());
            }

            traceManager.cleanup();
            if (chckbxNewCheckBox.isSelected()) {
                publish("rendering image...");
                ImageTransformer transformer = new ImageTransformer();
                BufferedImage image = transformer.transformFrom(traceResult);
                TraceResultFrame traceResultFrame = new TraceResultFrame(image);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int maxX = screenSize.width - 50;
                int maxY = screenSize.height - 50;
                traceResultFrame.setSize(maxX, maxY);
                traceResultFrame.setVisible(true);
            }
            publish("trace stopped.");
        }


        @Override
        protected void process(List<String> chunks) {
            for (String text : chunks) {
                updateStatusText(text);
            }
        }
    }
}
