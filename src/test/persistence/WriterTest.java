package persistence;

import model.Vocab;
import model.VocabList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


// Most tests are copied from WorkRoomApp
public class WriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            VocabList vl = new VocabList(new ArrayList<>(), "testList");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            VocabList vl = new VocabList(new ArrayList<>(), "empty");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyList.json");
            writer.open();
            writer.write(vl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyList.json");
            vl = reader.read();
            assertEquals("empty", vl.getName());
            assertEquals(0, vl.getList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            VocabList vocabList = new VocabList(new ArrayList<>(), "My list");
            vocabList.addVocab(new Vocab("saw", "past tense of see"));
            vocabList.addVocab(new Vocab("was", "past tense of is"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralList.json");
            writer.open();
            writer.write(vocabList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralList.json");
            vocabList = reader.read();
            assertEquals("My list", vocabList.getName());
            ArrayList<Vocab> vocabs = vocabList.getList();
            assertEquals(2, vocabs.size());
            checkVocab("saw", "past tense of see", vocabs.get(0));
            checkVocab("was", "past tense of is", vocabs.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
