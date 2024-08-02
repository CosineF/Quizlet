package persistence;

import model.Vocab;
import model.VocabList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

// Most tests are copied from WorkRoomApp
public class ReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            VocabList vl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyList.json");
        try {
            VocabList vl = reader.read();
            //assertEquals("emptyList", vl.getName());
            assertEquals(0, vl.getList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralList.json");
        try {
            VocabList vl = reader.read();
            //assertEquals("My work room", wr.getName());
            ArrayList<Vocab> vocabs = vl.getList();
            assertEquals(2, vocabs.size());
            checkVocab("cat", "a small domesticated carnivore", vocabs.get(0));
            checkVocab("box", "a container, usually rectangular", vocabs.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
