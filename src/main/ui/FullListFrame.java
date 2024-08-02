package ui;

import javax.swing.*;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import static ui.VocabApp.JSON_STORE;
import static ui.VocabApp.JSON_TRICKY;

// A frame that shows the full words list and operations
public class FullListFrame extends JFrame implements ActionListener {
    private VocabList newList;
    private DefaultTableModel tableModel;
    private JTable table;
    private JsonReader jsonReader = new JsonReader(JSON_STORE);
    private JLabel word;
    private JLabel meaning;
    private JLabel addTrickyQ;
    private JLabel hint;
    private JTextField trickyWord;
    private JButton add;
    private JLabel feedback;
    private VocabList trickyList;
    private JsonWriter jsonWriter2 = new JsonWriter(JSON_TRICKY);   // write wrong list into file
    private JsonReader jsonReader2 = new JsonReader(JSON_TRICKY);   // help to load the file of wrong list

    //MODIFIES: this
    //EFFECTS: Generate a frame for this class that pack all things together
    public FullListFrame() {
        super("Full Vocabulary List");
        // Show a data load pop-up
        int response = showDataLoadPopup();
        if (response != JOptionPane.YES_OPTION) {
            // User clicked "No" or closed the pop-up, so exit the application
            dispose();
        } else {
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            popUpWindow();

            setSize(500, 500);

            elementInFrame();
            loadData();
            toTable();

            setLayout(null);
            add(table);
            add(word);
            add(meaning);
            add(addTrickyQ);
            add(hint);
            add(trickyWord);
            add(add);
            add(feedback);

            setVisible(true);
        }
    }

    //MODIFIES: this
    //EFFECTS: generate all elements that should be appeared on frame.
    private void elementInFrame() {
        word = new JLabel("Word:");
        word.setBounds(50, 30, 100, 20);
        meaning = new JLabel("Meaning:");
        meaning.setBounds(120, 30, 200, 20);

        addTrickyQ = new JLabel("Do you want to add one to Tricky Word List?");
        addTrickyQ.setBounds(50, 300, 300, 20);
        hint = new JLabel("Please enter the word here: ");
        hint.setBounds(50, 330, 300, 20);

        trickyWord = new JTextField();
        trickyWord.setBounds(50, 350, 100, 20);
        add = new JButton("Add");
        add.setBounds(300, 350, 70, 40);
        add.addActionListener(this);

        feedback = new JLabel("");
        feedback.setBounds(100, 400, 250, 40);
    }


    //MODIFIES: this
    //EFFECTS: Change a VocabList to JTable form and store in tableModel
    private void toTable() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Meaning");
        table = new JTable(tableModel);
        table.setBounds(50, 50, 400, 200);
        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);

        for (Vocab vocab : newList.getList()) {
            tableModel.addRow(new String[]{vocab.getWord(), vocab.getMeaning()});
        }
    }

    //MODIFIES: this
    //EFFECTS: load data in JSON file to VocabList newList and trickyList
    private void loadData() {
        try {
            newList = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        try {
            trickyList = jsonReader2.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_TRICKY);
        }
    }

    //MODIFIES: trickyList.json
    //EFFECTS: store data in trickyList to JSON file version
    private void saveData() {
        try {
            jsonWriter2.open();
            jsonWriter2.write(trickyList);
            jsonWriter2.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_TRICKY);
        }
    }

    // EFFECTS: pop up a saving window when user click on "close"
    // RESOURCE: GitHub, https://github.com/skylot/jadx.git
    private void popUpWindow() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        FullListFrame.this,
                        "Do you want to save your work before closing?",
                        "Save Reminder",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    // Save the work
                    saveData();
                    dispose(); // Close the frame
                } else if (result == JOptionPane.NO_OPTION) {
                    dispose(); // Close the frame without saving
                }
                // If user selects Cancel, do nothing
            }
        });
    }

    //EFFECTS: produce true if vocabList has element v.
    private Boolean containV(VocabList vocabList, Vocab v) {
        for (Vocab vrn : vocabList.getList()) {
            if (v.getWord().equals(vrn.getWord()) && v.getMeaning().equals(vrn.getMeaning())) {
                return true;
            }
        }
        return false;
    }

    //RESOURCE: https://github.com/gonzalezjo/quizlet-downloader.git
    //EFFECTS: produce a pop-up window that ask user to load data.
    private static int showDataLoadPopup() {
        String message = "Do you want to load and view Full List?";
        String title = "Data Load Confirmation";
        int optionType = JOptionPane.YES_NO_OPTION;
        int messageType = JOptionPane.QUESTION_MESSAGE;

        return JOptionPane.showConfirmDialog(null, message, title, optionType, messageType);
    }

    //MODIFIES: this
    //EFFECTS: detect user input and selectively add Vocab to trickyList
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            String w = trickyWord.getText();
            Boolean contain = false;
            for (Vocab v: newList.getList()) {
                if (v.getWord().equals(w)) {
                    if (containV(trickyList, v)) {
                        feedback.setText("Tricky List already had this Vocab.");
                        feedback.setForeground(Color.RED);
                    } else {
                        trickyList.addVocab(v);
                        feedback.setText("Add operation completed.");
                        feedback.setForeground(Color.GREEN);
                    }
                    contain = true;
                }
            }
            if (!contain) {
                feedback.setText("Didn't find this Vocab in full List.");
                feedback.setForeground(Color.RED);
            }
        }
    }
}