package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class SceneControllerTest {
    private SceneManager sceneManager;
    private Scene scene;
    private PlayerModel player;
    private SceneController controller;
    private Game game;

    @BeforeEach
    void setUp() {
        sceneManager = mock(SceneManager.class);
        scene = mock(Scene.class);
        player = mock(PlayerModel.class);
        game = mock(Game.class);
        
        when(sceneManager.getScene()).thenReturn(scene);
        when(scene.getPlayerModel()).thenReturn(player);
        when(scene.getMineralModels()).thenReturn(new ArrayList<>());
        when(scene.getSoundPlayer()).thenReturn(mock(SoundPlayer.class));
        
        controller = new SceneController(sceneManager);
    }

    @Test
    void testUpdateQuit() throws IOException {
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);
        com.ldtsfeup2526.bobTheDestructor.view.Sprite sprite = mock(com.ldtsfeup2526.bobTheDestructor.view.Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        SoundPlayer soundPlayer = mock(SoundPlayer.class);
        when(scene.getSoundPlayer()).thenReturn(soundPlayer);
        when(soundPlayer.getSound()).thenReturn(mock(javax.sound.sampled.Clip.class));

        controller.update(game, List.of(Action.QUIT));
        verify(game).setState(any(MainMenuState.class));
        verify(soundPlayer).stop();
    }

    @Test
    void testUpdateRegular() throws IOException {
        controller.update(game, List.of(Action.LEFT));
        verify(sceneManager).update(game);
        // cleanupMinerals is called
        verify(scene, atLeastOnce()).getMineralModels();
        // playerController.update is called
        verify(player).moveLeft();
    }

    @Test
    void testCleanupMineralsNegated() {
        MineralModel mineral = mock(MineralModel.class);
        when(mineral.getState()).thenReturn(MineralState.SELECTED);
        List<MineralModel> minerals = new ArrayList<>();
        minerals.add(mineral);
        when(scene.getMineralModels()).thenReturn(minerals);
        
        controller.cleanupMinerals();
        assert(minerals.size() == 1);
    }

    @Test
    void testOnMiningFinishedWithStateMineral() {
        PlayerState state = mock(PlayerState.class);
        MineralModel mineral = mock(MineralModel.class);
        when(player.getState()).thenReturn(state);
        when(state.getMineral()).thenReturn(mineral);
        
        controller.onMiningFinished(player);
        
        verify(mineral).setState(MineralState.DESTROYED);
        verify(scene).incrementCurrentMineralsCollected();
    }

    @Test
    void testOnMiningFinishedWithSelectedMineral() {
        PlayerState state = mock(PlayerState.class);
        MineralModel mineral = mock(MineralModel.class);
        when(player.getState()).thenReturn(state);
        when(state.getMineral()).thenReturn(null);
        when(player.getMineralSelected()).thenReturn(mineral);
        
        controller.onMiningFinished(player);
        
        verify(mineral).setState(MineralState.DESTROYED);
        verify(scene).incrementCurrentMineralsCollected();
    }

    @Test
    void testConstructorAddsListener() {
        verify(player).addMiningListener(controller);
    }

    @Test
    void testCleanupMinerals() {
        MineralModel mineral = mock(MineralModel.class);
        when(mineral.getState()).thenReturn(MineralState.CLEANUP);
        List<MineralModel> minerals = new ArrayList<>();
        minerals.add(mineral);
        when(scene.getMineralModels()).thenReturn(minerals);
        
        controller.cleanupMinerals();
        assert(minerals.isEmpty());
    }
}
