package edu.metrostate.by8477ks;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

class View {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // declare components
    JTextField tf1;
    JButton b1;
    JLabel l1;
    JScrollPane scrollPane;
    JTextArea ta1;
    JFrame frame;
    JPanel panel;
    Dimension minimumSize = new Dimension(400, 400);


    View() { // constructor
        // instantiate components, don't forget to set bounds
        frame = new JFrame("Reverse Recursive Sort");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();

        // create listener on this instance of the View class
        Controller bController = new Controller(this);

        b1 = new JButton("Choose file");
        b1.setSize(100, 50);
        b1.addActionListener(bController);

        tf1 = new JTextField();
        tf1.setSize(200, 50);
        tf1.setMinimumSize(new Dimension(100,50));

        ta1 = new JTextArea(10, 15);
        ta1.setAutoscrolls(true);
        ta1.setText("Welcome to the recursive sorter!");
        ta1.append("\n--------------------------------");

        scrollPane = new JScrollPane(ta1);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setAutoscrolls(true);

        l1 = new JLabel("Choose your source file to sort.");

        //
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setMinimumSize(new Dimension(400,400));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panel.add(Box.createVerticalGlue());

        // add components to layout
        panel.add(tf1); // add text field to frameID
        panel.add(l1);
        panel.add(b1);
//		panel.add(ta1);
        panel.add(scrollPane);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }

}