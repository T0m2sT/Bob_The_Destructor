package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SceneManagerTest {
    private SceneManager sceneManager;
    private Scene scene;

    @BeforeEach
    void setUp() throws IOException {
        scene = mock(Scene.class);
        sceneManager = new SceneManager();
        sceneManager.setScene(scene);
    }

    @Test
    void testGetSetScene() {
        assertEquals(scene, sceneManager.getScene());
        Scene anotherScene = mock(Scene.class);
        sceneManager.setScene(anotherScene);
        assertEquals(anotherScene, sceneManager.getScene());
    }

    @Test
    void testCavesPathChosen() {
        List<String> paths = sceneManager.getCavesPathChosen();
        assertEquals(5, paths.size());
        for (String path : paths) {
            assertTrue(path.startsWith("caves/cave"));
        }
    }

    @Test
    void testChooseCavesUniqueness() {
        List<String> paths = sceneManager.getCavesPathChosen();
        assertEquals(5, paths.size());
        assertEquals(5, paths.stream().distinct().count());
    }

    @Test
    void testTotalMineralsCollected() {
        when(scene.getCurrentMineralsCollected()).thenReturn(5);
        sceneManager.updateTotalMineralsCollected();
        assertEquals(5, sceneManager.getTotalMineralsCollected());
        
        when(scene.getCurrentMineralsCollected()).thenReturn(3);
        sceneManager.updateTotalMineralsCollected();
        assertEquals(8, sceneManager.getTotalMineralsCollected());
    }

    @Test
    void testGetCurrentCavePathIndex() {
        // Starts at -1 because it's incremented when getNextCavePath is called
        assertEquals(-1, sceneManager.getCurrentCavePathIndex());
        sceneManager.getNextCavePath();
        assertEquals(0, sceneManager.getCurrentCavePathIndex());
    }
}
