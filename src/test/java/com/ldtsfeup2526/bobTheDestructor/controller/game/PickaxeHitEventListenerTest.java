package com.ldtsfeup2526.bobTheDestructor.controller.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PickaxeHitEventListenerTest {
    @Test
    void testInterface() {
        PickaxeHitEventListener listener = playerModel -> {};
        assertNotNull(listener);
    }
}
