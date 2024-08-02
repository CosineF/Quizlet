package persistence;

import model.Vocab;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkVocab(String word, String meaning, Vocab v) {
        assertEquals(word, v.getWord());
        assertEquals(meaning, v.getMeaning());
    }
}
