package com.ldtsfeup2526.bobTheDestructor.states;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.IdleState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;
import java.io.IOException;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class GameStateTest {
    @Test
    void testConstructor() throws IOException {
        SceneManager model = mock(SceneManager.class);
        Scene scene = mock(Scene.class);
        PlayerModel playerModel = mock(PlayerModel.class);
        com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody rb = mock(com.ldtsfeup2526.bobTheDestructor.model.game.physics.RigidBody.class);
        
        when(model.getScene()).thenReturn(scene);
        when(model.getCavesPathChosen()).thenReturn(java.util.List.of("caves/cave0/"));
        when(model.getCurrentCavePathIndex()).thenReturn(0);
        when(scene.getPlayerModel()).thenReturn(playerModel);
        when(playerModel.getRigidBody()).thenReturn(rb);
        when(playerModel.getState()).thenReturn(new IdleState(playerModel));
        when(rb.getPosition()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector(0.0f, 0.0f));
        when(scene.getMineralModels()).thenReturn(Collections.emptyList());
        
        SoundPlayer soundPlayer = mock(SoundPlayer.class);
        Clip mockClip = mock(Clip.class);
        when(scene.getSoundPlayer()).thenReturn(soundPlayer);
        when(soundPlayer.getSound()).thenReturn(mockClip);
        
        when(scene.getCaveFilePath()).thenReturn("caves/cave0/");

        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        Sprite sprite = mock(Sprite.class);
        when(spriteLoader.get(anyString())).thenReturn(sprite);
        when(sprite.getSize()).thenReturn(new com.ldtsfeup2526.bobTheDestructor.model.spatials.Size(1, 1));
        
        java.awt.image.BufferedImage mockImage = new java.awt.image.BufferedImage(100, 100, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        when(spriteLoader.getBufferedImage(anyString())).thenReturn(mockImage);
        
        GameState state = new GameState(model, spriteLoader);
        assertNotNull(state.createController(), "Controller should be created successfully");
        assertNotNull(state.createScreenViewer(new com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider(spriteLoader)), "Viewer should be created successfully");
    }
}
