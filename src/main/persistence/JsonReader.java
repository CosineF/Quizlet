package persistence;

import model.Vocab;
import model.VocabList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

//Represents a reader that reads fullList from JSON data stored in file
// reference WorkRoomApp from 210.
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads vocabList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public VocabList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return getList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses vocabs from JSON object and returns it
    private VocabList getList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        VocabList list = new VocabList(new ArrayList<>(), name);
        addVocabs(list, jsonObject);
        return list;
    }

    // MODIFIES: vl
    // EFFECTS: parses Vocabs from JSON object and adds them to vl
    private void addVocabs(VocabList vl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("vocabs");
        for (Object json : jsonArray) {
            JSONObject nextVocab = (JSONObject) json;
            addThingy(vl, nextVocab);
        }
    }

    // MODIFIES: vl
    // EFFECTS: parses Vocab from JSON object and adds it to vl
    private void addThingy(VocabList vl, JSONObject jsonObject) {
        String word = jsonObject.getString("word");
        String meaning = jsonObject.getString("meaning");
        Vocab v = new Vocab(word, meaning);
        vl.addVocab(v);
    }
}
