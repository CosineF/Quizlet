package ui;


import javax.swing.*;
import model.*;
import persistence.JsonReader;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;

import static ui.VocabApp.JSON_TRICKY;

// A frame that shows the tricky words list
public class TrickyListFrame extends JFrame {
    private VocabList newList;
    private DefaultTableModel tableModel;
    private JTable table;
    private JsonReader jsonReader = new JsonReader(JSON_TRICKY);
    private JLabel word;
    private JLabel meaning;

    //MODIFIES: this
    //EFFECTS: Generate a frame for this class that pack all things together
    public TrickyListFrame() {
        super("Tricky Vocabulary List");

        // Show a data load pop-up
        int response = showDataLoadPopup();
        if (response != JOptionPane.YES_OPTION) {
            dispose();
        } else {
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            setSize(500, 500);

            word = new JLabel("Word:");
            word.setBounds(50, 30, 100, 20);
            meaning = new JLabel("Meaning:");
            meaning.setBounds(120, 30, 200, 20);

            loadData();
            toTable();

            setLayout(null);
            add(table);
            add(word);
            add(meaning);

            setVisible(true);
        }
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
    //EFFECTS: load data in JSON file to VocabList newList
    private void loadData() {
        try {
            newList = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_TRICKY);
        }
    }

    //RESOURCE: https://github.com/gonzalezjo/quizlet-downloader.git
    //EFFECTS: produce a pop-up window that ask user to load data.
    private static int showDataLoadPopup() {
        String message = "Do you want to load and view Tricky List?";
        String title = "Data Load Confirmation";
        int optionType = JOptionPane.YES_NO_OPTION;
        int messageType = JOptionPane.QUESTION_MESSAGE;

        return JOptionPane.showConfirmDialog(null, message, title, optionType, messageType);
    }
}