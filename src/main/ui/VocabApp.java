package ui;

//REFERENCE: I use some method from CPSC210-TellerApp.
//           Some code structures are referenced from TellerApp.

import model.Vocab;
import model.VocabList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// Vocabulary application
public class VocabApp {
    static final String JSON_STORE = "./data/vocablist.json";
    static final String JSON_TRICKY = "./data/trickylist.json";
    private VocabList fullList; // list contain all vocab
    private VocabList quizList;      // list contain all vocab for one quiz
    private static final int FULLMARK = 10;       // total mark of one quiz if nothing wrong.
    private static final Random RNDM = new Random(); //a random
    private static int QUIZSIZE;       // number of vocab in one quiz.
    private double accuracy;         // the accuracy of one quiz
    private VocabList wrongList;     // list contain all wrong vocab in one quiz
    private String expReal;          // real meaning of word
    private Scanner input;           // user input
    private JsonWriter jsonWriter;   // write things into file
    private JsonWriter jsonWriter2;   // write wrong list into file
    private JsonReader jsonReader;   // help to load the file
    private JsonReader jsonReader2;   // help to load the file of wrong list

    // MODIFIES: this
    // EFFECTS: runs the Vocabulary application
    public VocabApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter2 = new JsonWriter(JSON_TRICKY);
        jsonReader2 = new JsonReader(JSON_TRICKY);
        runVocab();
    }

    // MODIFIES: this
    // EFFECTS: processes user input and give feedback.
    private void runVocab() {
        boolean keepGoing = true;
        String command;
        initiate();
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\td -> do the test");
        System.out.println("\ta -> add new word to Vocab Library");
        //System.out.println("\tc -> clear Vocab Library");
        System.out.println("\ts -> save Vocab Library and your wrong word list");
        System.out.println("\tl -> load Vocab Library and your wrong word list");
        System.out.println("\tp -> view your wrong word list from the latest quiz");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("d")) {
            doQuiz();
        } else if (command.equals("a")) {
            addNewVocab();
//        } else if (command.equals("c")) {
//            clearLibrary();
        } else if (command.equals("s")) {
            saveLibrary();
        } else if (command.equals("p")) {
            printWrongList();
        } else if (command.equals("l")) {
            loadLibrary();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: print the vocab list that the user get wrong during last quiz
    private void printWrongList() {
        if (wrongList.isEmpty()) {
            System.out.println("You haven't load it yet, or you haven't done any quiz yet.");
        } else {
            printList(wrongList.getList());
        }
    }

    // MODIFIES: json file vocabList
    // EFFECTS: clear all data store in file
//    private void clearLibrary() {
//    }

    // EFFECTS: saves the vocab List and wrong word list to file
    private void saveLibrary() {
        try {
            jsonWriter.open();
            jsonWriter.write(fullList);
            jsonWriter.close();
            System.out.println("Your Vocab list is already saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
        try {
            jsonWriter2.open();
            jsonWriter2.write(wrongList);
            jsonWriter2.close();
            System.out.println("Your Wrong words list is already saved to " + JSON_TRICKY);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_TRICKY);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads VocabRoom from file
    private void loadLibrary() {
        try {
            fullList = jsonReader.read();
            System.out.println("Loaded full vocab list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        try {
            wrongList = jsonReader2.read();
            System.out.println("Loaded wrong words list from " + JSON_TRICKY);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_TRICKY);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompt user for word and its meaning and add to fullList
    private void addNewVocab() {
        System.out.println("Please enter the word you want to add");
        String word = input.next();
        System.out.println("Please enter the meaning of that word");
        String meaning = input.next();
        Vocab v = new Vocab(word, meaning);
        fullList.addVocab(v);
    }

    //REQUIRES: first input must between 2 and fullList's size.
    //MODIFIES: this
    //EFFECTS: Provide user a quiz.
    private void doQuiz() {
        wrongList = new VocabList(new ArrayList<>(), "wrongList");
        System.out.println("How many words do you want?\nPlease select from 2 to " + fullList.getList().size());
        QUIZSIZE = Integer.parseInt(input.next());

        quizList = new VocabList(makeQuizList(), "quizList");

        if (quizList.isEmpty()) {
            System.out.println("please initialize Vocab Library");
        }
        for (Vocab v: quizList.getList()) {
            System.out.println(v.getWord());
            String exp = randomExp(v);
            expReal = v.getMeaning();
            System.out.println(exp);
            System.out.println("Enter 't' if you think this sentence is the correct explanation of this word");

            String command = input.next();

            rightOrWrong(v.checkAnswer(command, (expReal.equals(exp))), v);
            System.out.println("Your accuracy is now " + accuracy + "/" + FULLMARK + "\n");
        }
        System.out.println("Here is your wrong word list:");
        printList(wrongList.getList());
    }


    //MODIFIES: this
    //EFFECTS: initializes the quiz.
    private void initiate() {
        wrongList = new VocabList(new ArrayList<>(), "wrongList");
        accuracy = FULLMARK;
        fullList = new VocabList(new ArrayList<>(), "fullList");
        fullList.loadData();

        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    //REQUIRES: Vocab v is not null
    //MODIFIES: accuracy, wrongList, this
    //EFFECTS: tell the user if he/she got the right answer or not.
    //         If not, add the vocab gets wrong to the wrongList.
    //         deduct one mark for accuracy.
    public void rightOrWrong(boolean b, Vocab v) {
        if (b) {
            System.out.println("You are right!");
            System.out.println("The true explanation of " + v.getWord() + " is\n" + expReal);
        } else {
            accuracy = accuracy - (double) FULLMARK / (double) QUIZSIZE;
            wrongList.getList().add(v);
            System.out.println("You are wrong......");
            System.out.println("The true explanation of " + v.getWord() + " is\n" + expReal);
        }
    }


    //REQUIRES: Vocab v is not null
    // EFFECTS: provide correct meaning or false meaning for Vocab v (parameter), each with 50% chance.
    private String randomExp(Vocab v) {
        if (RNDM.nextInt(2) == 1) {
            return v.getMeaning();
        } else {
            return pickOne(v).getMeaning();
        }
    }

    //REQUIRES: Vocab v is not null
    // EFFECTS: pick a random explanation other than the correct meaning of Vocab v (parameter).
    private Vocab pickOne(Vocab v) {
        ArrayList<Vocab> qzl = quizList.getList();
        int randomInt = RNDM.nextInt(qzl.size());
        Vocab exp = qzl.get(randomInt);
        if (exp == v) {
            do {
                randomInt = RNDM.nextInt(qzl.size());
                exp = qzl.get(randomInt);
            } while (exp == v);
        }
        return exp;
    }

    //REQUIRES: size be an integer
    //MODIFIES: list
    //EFFECTS: generate a random ArrayList with size (size)
    //         from this.list
    public ArrayList<Vocab> makeQuizList() {
        ArrayList<Vocab> qzl = new ArrayList<>();
        ArrayList<Vocab> fll = fullList.getList();
        for (int i = 0; i < QUIZSIZE; i++) {
            int randomInt = RNDM.nextInt(fll.size());
            Vocab randomVocab = fll.get(randomInt);
            qzl.add(randomVocab);
        }
        return qzl;
    }

    //REQUIRES: Vocab v is not null
    //EFFECTS: print the whole list with word and its meaning.
    public void printList(ArrayList<Vocab> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getWord());
            System.out.println(list.get(i).getMeaning());
        }
    }

}
