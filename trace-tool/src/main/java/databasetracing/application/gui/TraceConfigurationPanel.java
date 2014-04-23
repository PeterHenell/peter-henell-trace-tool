package databasetracing.application.gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TraceConfigurationPanel extends JPanel {
    private static final long serialVersionUID = 7926143978884294678L;
    private JTextField serverName;
    private JTextField databaseName;

    private final TraceGUI traceGUI;


    /**
     * Create the panel.
     */
    public TraceConfigurationPanel(TraceGUI traceGUI, TraceGUIConfiguration config) {
        this.traceGUI = traceGUI;

        setBorder(new EmptyBorder(3, 3, 3, 3));
        setLayout(new GridLayout(2, 4, 0, 0));

        JLabel lblNewLabel = new JLabel("Server Name");
        add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Database Name");
        add(lblNewLabel_1);

        serverName = new JTextField();
        add(serverName);
        serverName.setColumns(10);
        serverName.setText(config.getServerName());

        databaseName = new JTextField();
        add(databaseName);
        databaseName.setColumns(10);
        databaseName.setText(config.getDbName());

    }


    public TraceGUIConfiguration getConfig() {
        TraceGUIConfiguration config = new TraceGUIConfiguration();
        config.setDbName(databaseName.getText());
        config.setServerName(serverName.getText());
        config.setPassword("sqldeploy");
        config.setUserName("sqldeploy");
        return config;
    }

}
