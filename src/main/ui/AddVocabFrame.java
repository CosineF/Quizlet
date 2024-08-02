package ui;

import model.Vocab;
import model.VocabList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import static ui.VocabApp.JSON_STORE;

// A frame that shows the full words list and operations
public class AddVocabFrame implements ActionListener {
    private JFrame frame;
    private JTextField addWord;
    private JTextField addMeaning;
    private JLabel word;
    private JLabel meaning;
    private JButton done;
    private VocabList newList;
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    //MODIFIES: this
    //EFFECTS: Generate a frame for this class that pack all things together
    public AddVocabFrame() {
        loadData();
        frame = new JFrame("Add New Vocabs to Full List");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        popUpWindow();

        frame.setSize(500,500);
        frame.setLayout(null);

        addWord = new JTextField(10);
        addWord.setBounds(50, 200, 100, 20);
        addMeaning = new JTextField(30);
        addMeaning.setBounds(170, 200, 200, 20);

        word = new JLabel("Enter Word:");
        word.setBounds(52, 175, 100, 20);
        meaning = new JLabel("Enter Meaning:");
        meaning.setBounds(172, 175, 200, 20);

        done = new JButton("Done");
        done.setBounds(300, 300, 100, 70);
        done.addActionListener(this);

        frame.add(addWord);
        frame.add(addMeaning);
        frame.add(word);
        frame.add(meaning);
        frame.add(done);

        frame.setVisible(true);
    }

    // EFFECTS: pop up a saving window when user click on "close"
    // RESOURCE: GitHub, https://github.com/skylot/jadx.git
    private void popUpWindow() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Do you want to save your work before closing?",
                        "Save Reminder",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (result == JOptionPane.YES_OPTION) {
                    // Save the work
                    saveData();
                    frame.dispose(); // Close the frame
                } else if (result == JOptionPane.NO_OPTION) {
                    frame.dispose(); // Close the frame without saving
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: load data in JSON file to VocabList newList
    private void loadData() {
        try {
            newList = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //MODIFIES: vocablist.json
    //EFFECTS: store data in newList to JSON file version
    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(newList);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: detect user input and selectively add Vocab to newList
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == done) {
            String w = addWord.getText();
            String m = addMeaning.getText();
            if (!w.isEmpty() && !m.isEmpty()) {
                Vocab v = new Vocab(w, m);
                newList.addVocab(v);

                addWord.setText("");
                addMeaning.setText("");
            }
        }
    }
}
