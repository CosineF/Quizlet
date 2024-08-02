package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class VocabListTest {
    private Vocab v1;
    private Vocab v2;
    private VocabList vocabList;

    @BeforeEach
    void runBefore() {
        v1 = new Vocab("Hi", "Meaning of Hi");
        v2 = new Vocab("Hello", "Meaning of Hello");
        vocabList = new VocabList(new ArrayList<>(), "vocabList");
    }

    @Test
    void testConstructor() {
        ArrayList<Vocab> list = vocabList.getList();
        assertEquals(0, list.size());
        assertEquals("vocabList", vocabList.getName());

        list.add(v1);
        assertEquals("Hi", list.get(0).getWord());
        assertEquals("Meaning of Hi", list.get(0).getMeaning());
        assertEquals(1, list.size());

        list.add(v2);
        assertEquals("Hello", list.get(1).getWord());
        assertEquals("Meaning of Hello", list.get(1).getMeaning());
        assertEquals(2, list.size());
    }

    @Test
    void testLoadData() {
        vocabList.loadData();
        ArrayList<Vocab> list = vocabList.getList();
        assertEquals(6, list.size());

        assertEquals("cat", list.get(0).getWord());
        assertEquals("a small domesticated carnivore", list.get(0).getMeaning());
        assertEquals("dog", list.get(1).getWord());
        assertEquals("a domesticated canid", list.get(1).getMeaning());
        assertEquals("party", list.get(2).getWord());
        assertEquals("a group gathered for a special purpose or task", list.get(2).getMeaning());
        assertEquals("study", list.get(3).getWord());
        assertEquals("the cultivation of a particular branch of learning", list.get(3).getMeaning());
        assertEquals("chair", list.get(4).getWord());
        assertEquals("a seat, especially for one person", list.get(4).getMeaning());
        assertEquals("box", list.get(5).getWord());
        assertEquals("a container, usually rectangular", list.get(5).getMeaning());
    }

    @Test
    void testEmpty() {
        assertTrue(vocabList.isEmpty());
        vocabList.loadData();
        assertFalse(vocabList.isEmpty());
    }

    @Test
    void testAddVocab() {
        vocabList.addVocab(v1);
        assertEquals(1, vocabList.getList().size());
        assertEquals("Hi", vocabList.getList().get(0).getWord());
        assertEquals("Meaning of Hi", vocabList.getList().get(0).getMeaning());

        vocabList.addVocab(v2);
        assertEquals(2, vocabList.getList().size());
        assertEquals("Hello", vocabList.getList().get(1).getWord());
        assertEquals("Meaning of Hello", vocabList.getList().get(1).getMeaning());
    }

}
