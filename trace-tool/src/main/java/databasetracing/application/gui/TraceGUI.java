package databasetracing.application.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class TraceGUI extends WindowAdapter implements ActionListener {

    private JFrame mainFrame;
    private TraceConfigurationPanel traceConfigurationPanel;
    private TraceRunnerPanel traceRunnerPanel;


    public TraceConfigurationPanel getTraceConfigurationPanel() {
        return traceConfigurationPanel;
    }


    public TraceRunnerPanel getTraceRunnerPanel() {
        return traceRunnerPanel;
    }


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TraceGUI window = new TraceGUI();
                    window.mainFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Create the application.
     */
    public TraceGUI() {
        initialize();
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        mainFrame = new JFrame();
        mainFrame.setAlwaysOnTop(true);
        mainFrame.setResizable(false);
        mainFrame.setTitle("Trace Manager");
        mainFrame.setBounds(100, 100, 579, 110);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new BorderLayout(0, 0));

        mainFrame.addWindowListener(this);

        traceConfigurationPanel = new TraceConfigurationPanel(this, new TraceGUIConfiguration());
        mainFrame.getContentPane().add(traceConfigurationPanel, BorderLayout.NORTH);

        traceRunnerPanel = new TraceRunnerPanel(this);
        FlowLayout flowLayout = (FlowLayout) traceRunnerPanel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        mainFrame.getContentPane().add(traceRunnerPanel, BorderLayout.SOUTH);
    }


    @Override
    public void windowClosing(WindowEvent e) {
        JFrame frame = (JFrame) e.getSource();

        traceRunnerPanel.cleanup();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
        frame.dispose();
    }


    public void actionPerformed(ActionEvent e) {

    }
}
