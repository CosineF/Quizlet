package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VocabTest {

    private Vocab v1;
    private Vocab v2;

    @BeforeEach
    void runBefore() {
        v1 = new Vocab("Hi", "Meaning of Hi");
        v2 = new Vocab("Hello", "Meaning of Hello");
    }

    @Test
    void testConstructor(){
        assertEquals("Hi", v1.getWord());
        assertEquals("Meaning of Hi", v1.getMeaning());
        assertEquals("Hello", v2.getWord());
        assertEquals("Meaning of Hello", v2.getMeaning());
    }

    @Test
    void testCheckAnswer() {
        assertTrue(v1.checkAnswer("t", true));
        assertFalse(v1.checkAnswer("t", false));
        assertFalse(v1.checkAnswer("f", true));
        assertTrue(v1.checkAnswer("f", false));
    }
}

