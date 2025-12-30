package com.ldtsfeup2526.bobTheDestructor.model.credits;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreditsTest {
    @Test
    void testGetCredits() {
        Credits credits = new Credits();
        String[] content = credits.getCredits();
        assertEquals(6, content.length);
        assertEquals("Programmers:", content[0]);
        assertEquals("Alexis Ramos", content[1]);
        assertEquals("Pedro Tomas Teixeira", content[2]);
        assertEquals("Rafael Pinho e Silva", content[3]);
        assertEquals("Artists:", content[4]);
        assertEquals("Mich", content[5]);
    }
}
