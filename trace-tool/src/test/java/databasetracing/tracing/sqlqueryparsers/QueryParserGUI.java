package databasetracing.tracing.sqlqueryparsers;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class QueryParserGUI {

    private JFrame frame;
    private JTextField regexField;
    private JTextArea outputField;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    QueryParserGUI window = new QueryParserGUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Create the application.
     */
    public QueryParserGUI() {
        initialize();
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        // frame.setType(Type.UTILITY);
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(5, 0, 0, 0));

        regexField = new JTextField("select (distinct )?(top [0-9]+)?.+from (.+?)'(,)?(.+)?");
        frame.getContentPane().add(regexField);
        regexField.setColumns(10);

        outputField = new JTextArea();
        frame.getContentPane().add(outputField);
        outputField.setColumns(10);

        JButton btnNewButton = new JButton("Parse");

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                List<String> inputStrings = new ArrayList<String>();
                inputStrings.add(SelectQueryParserTest.selectSubQuery);
                inputStrings.add(SelectQueryParserTest.selectString);

                StringBuilder sb = new StringBuilder();

                for (String sqlString : inputStrings) {

                    sqlString = sqlString.replaceAll("''", "'");
                    Pattern p = Pattern.compile(regexField.getText(), Pattern.CASE_INSENSITIVE);
                    Matcher matcher = p.matcher(sqlString);

                    matcher.find();
                    for (int i = 0; i < matcher.groupCount(); i++) {
                        sb.append("<" + matcher.group(i + 1) + ">");
                        System.out.println(sb.toString());
                        sb.append("\n");
                    }
                }
                outputField.setText(sb.toString());
            }

        });

        frame.getContentPane().add(btnNewButton);
        regexField.requestFocus();

        frame.getRootPane().setDefaultButton(btnNewButton);
    }
}
