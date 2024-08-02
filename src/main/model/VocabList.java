package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


// An ArrayList<> that contain elements Vocab.
public class VocabList {

    private final ArrayList<Vocab> vocabs;
    private final String name;

    //EFFECTS: build a list of Vocab(s)
    public VocabList(ArrayList<Vocab> vocabs, String name) {
        this.vocabs = vocabs;
        this.name = name;
    }

    //EFFECTS: return the list store in 'this' VocabList
    public ArrayList<Vocab> getList() {
        return this.vocabs;
    }

    //EFFECTS: return the name of list.
    public String getName() {
        return this.name;
    }

    // MODIFIES: this
    // EFFECTS: load data into the object
    public void loadData() {

        ArrayList<Vocab> list = this.getList();

        list.add(new Vocab("cat",
                "a small domesticated carnivore"));
        list.add(new Vocab("dog",
                "a domesticated canid"));
        list.add(new Vocab("party",
                "a group gathered for a special purpose or task"));
        list.add(new Vocab("study",
                "the cultivation of a particular branch of learning"));
        list.add(new Vocab("chair",
                "a seat, especially for one person"));
        list.add(new Vocab("box",
                "a container, usually rectangular"));
    }

    // EFFECTS: see if the list is empty.
    public boolean isEmpty() {
        return this.getList().isEmpty();
    }

    // REQUIRES: v is not in 'this' list
    // MODIFIES: this
    // EFFECTS: add a new word and its meaning to 'this' list
    public void addVocab(Vocab v) {
        ArrayList<Vocab> list = this.getList();
        list.add(v);
        EventLog.getInstance().logEvent(
                new Event("Added Vocab ( " + v.getWord() + ", "
                        + v.getMeaning() + " ) to VocabList " + this.getName()));
    }

    // EFFECT: store 'this' list to json to store in file
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("vocabs", this.saveList());
        EventLog.getInstance().logEvent(
                new Event("Save VocabList " + this.getName() + " to JSON file"));
        return json;
    }

    // EFFECTS: store 'this' list to JsonArray Version
    private JSONArray saveList() {
        JSONArray jsonArray = new JSONArray();

        for (Vocab v: this.getList()) {
            jsonArray.put(v.toJson());
        }

        return jsonArray;
    }

}
