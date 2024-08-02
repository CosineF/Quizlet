package model;

import org.json.JSONObject;

// Represents a Vocab having a word, and its explanation (meaning).
public class Vocab {

    private final String word;                    // a single vocab
    private final String meaning;                 // explanation for the vocab

    /*
    REQUIRES: meaning is the explanation of word.
              word has a non-zero length.
              meaning has a non-zero length.
    EFFECTS: build a pair of string that is the word and its explanation.
     */
    public Vocab(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public String getWord() {
        return this.word;
    }

    public String getMeaning() {
        return this.meaning;
    }

    //REQUIRES: str is not null
    //EFFECTS: produce true if user's answer is identical with the real answer
    public boolean checkAnswer(String str, boolean real) {
        if (str.equals("t")) {
            return real;
        } else {
            return !real;
        }
    }

    // EFFECT: store 'this' vocab to json object version
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("word", word);
        json.put("meaning", meaning);
        return json;
    }
}
