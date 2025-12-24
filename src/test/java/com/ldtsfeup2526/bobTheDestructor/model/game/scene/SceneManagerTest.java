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
    private SceneBuilder sceneBuilder;
    private Scene scene;
    private PlayerModel playerModel;

    @BeforeEach
    void setUp() throws IOException {
        sceneBuilder = mock(SceneBuilder.class);
        scene = mock(Scene.class);
        playerModel = mock(PlayerModel.class);
        when(scene.getPlayerModel()).thenReturn(playerModel);
        when(sceneBuilder.createScene(anyString(), any(PlayerModel.class))).thenReturn(scene);

        sceneManager = new SceneManager(sceneBuilder);
    }

    @Test
    void testConstructor() {
        assertEquals(scene, sceneManager.getScene());
        assertEquals(sceneBuilder, sceneManager.getSceneBuilder());
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
    void testUpdateNoNewScene() throws IOException {
        when(playerModel.getPosition()).thenReturn(new Position(0, 0));

        sceneManager.update(mock(Game.class));
        assertEquals(scene, sceneManager.getScene());
    }

    @Test
    void testUpdateNewSceneBoundary() throws IOException {
        // resolution height is 160
        when(playerModel.getPosition()).thenReturn(new Position(0, 161));
        when(scene.getCurrentMineralsCollected()).thenReturn(5);

        Scene newScene = mock(Scene.class);
        when(newScene.getPlayerModel()).thenReturn(playerModel);
        when(sceneBuilder.createScene(anyString(), eq(playerModel))).thenReturn(newScene);

        sceneManager.update(mock(Game.class));

        assertEquals(newScene, sceneManager.getScene());
        assertEquals(5, sceneManager.getTotalMineralsCollected());
    }

    @Test
    void testChooseCavesUniqueness() throws IOException {
        List<String> paths = sceneManager.getCavesPathChosen();
        assertEquals(5, paths.size());
        assertEquals(5, paths.stream().distinct().count());
    }

    @Test
    void testUpdateGameOver() throws IOException {
        Game game = mock(Game.class);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);
        when(spriteLoader.get(anyString())).thenReturn(mock(Sprite.class));


        for (int i = 0; i < 5; i++) {
            when(playerModel.getPosition()).thenReturn(new Position(0, 100));
            sceneManager.update(game);
        }


        when(playerModel.getPosition()).thenReturn(new Position(0, 100));
        sceneManager.update(game);

        verify(game, atLeastOnce()).setState(any());
    }

    @Test
    void testGetCurrentCavePathIndex() {
        assertEquals(0, sceneManager.getCurrentCavePathIndex());
    }
}
