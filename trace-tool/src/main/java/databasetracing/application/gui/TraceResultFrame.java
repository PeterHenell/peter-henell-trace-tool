package databasetracing.application.gui;


import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class TraceResultFrame extends JFrame implements ActionListener {
    private static final long serialVersionUID = 4656628213574021920L;


    public TraceResultFrame(BufferedImage im) {
        super("Trace Result");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(Box.createVerticalGlue()); // takes all extra space
        JScrollPane scrollPane = new JScrollPane(new JLabel(new ImageIcon(im)));
        contentPane.add(scrollPane);
        contentPane.add(Box.createVerticalStrut(5)); // spacer
    }


    // Make the button do the same thing as the default close operation
    // (DISPOSE_ON_CLOSE).
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }
}