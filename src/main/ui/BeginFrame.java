package ui;

import model.EventLog;
import model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// An initial interface of whole project.
public class BeginFrame implements ActionListener {
    private JFrame frame;
    private JLabel label;
    private JButton viewTricky;
    private JButton viewFull;
    //private JButton addVocab;

    //MODIFIES: this
    //EFFECTS: generate a menu for user to start application.
    public BeginFrame() {
        frame = new JFrame("Begin Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,500);
        frame.setResizable(false);
        frame.setLayout(null);

        elementInFrame();

        frame.add(viewTricky);
        frame.add(viewFull);
        //frame.add(addVocab);

        frame.add(label);

        //////
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("file"); // savefileAddMenuItems(file);
        JMenu edit = new JMenu("edit"); // change distance
        JMenu view = new JMenu("view"); // view old sessions
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(view);
        menuBar.setVisible(true);
        frame.setJMenuBar(menuBar);
        //////

        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.toString());
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: generate all elements that should be appeared on frame.
    private void elementInFrame() {
        ImageIcon image0 = new ImageIcon("./data/Title.png");
        Image originalImage = image0.getImage();
        Image resizedImage = originalImage.getScaledInstance(210, 180, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(resizedImage);

        label = new JLabel();
        label.setIcon(image);
        label.setBounds(100, 30, 210, 180);

        viewTricky = new JButton("View Tricky List");
        viewTricky.setBounds(100, 200, 200, 40);
        viewTricky.addActionListener(this);

        viewFull = new JButton("View Full List");
        viewFull.setBounds(100, 250, 200, 40);
        viewFull.addActionListener(this);

        JButton addVocab = new JButton("Add New Vocab");
        addVocab.setBounds(100, 300, 200, 40);
        addVocab.addActionListener(this);
        frame.add(addVocab);
    }

    //MODIFIES: this
    //EFFECTS: detect user input and lead to next frame
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewTricky) {
            new TrickyListFrame();
        }
        if (e.getSource() == viewFull) {
            new FullListFrame();
        }
//        if (e.getSource() == addVocab) {
//            new AddVocabFrame();
//        }
    }
}
