package com.ldtsfeup2526.bobTheDestructor.model.stats;

import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StatsTest {
    @Test
    void testStatsExactMinute() {
        SceneManager sceneManager = mock(SceneManager.class);
        when(sceneManager.getTotalMineralsCollected()).thenReturn(10);
        when(sceneManager.getTimePassed()).thenReturn(60.0);

        Stats stats = new Stats(sceneManager);

        assertEquals(10, stats.getMineralsCollected());
        assertEquals(1, stats.getMinutes());
        assertEquals(0, stats.getSeconds());
        
        // Mutate minutes/seconds calculation
        when(sceneManager.getTimePassed()).thenReturn(125.0);
        stats = new Stats(sceneManager);
        assertEquals(2, stats.getMinutes());
        assertEquals(5, stats.getSeconds());
    }
}
