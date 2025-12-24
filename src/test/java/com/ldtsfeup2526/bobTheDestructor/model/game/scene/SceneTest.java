package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SceneTest {
    private Scene scene;
    private PlayerModel playerModel;
    private List<MineralModel> mineralModels;
    private String caveFilePath = "test/path";

    @BeforeEach
    void setUp() {
        playerModel = mock(PlayerModel.class);
        mineralModels = new ArrayList<>(Arrays.asList(mock(MineralModel.class), mock(MineralModel.class)));
        scene = new Scene(caveFilePath, playerModel, mineralModels);
    }

    @Test
    void testConstructor() {
        assertEquals(caveFilePath, scene.getCaveFilePath());
        assertEquals(playerModel, scene.getPlayerModel());
        assertEquals(mineralModels, scene.getMineralModels());
    }

    @Test
    void testSoundPlayer() {
        assertNotNull(scene.getSoundPlayer());

        SoundPlayer soundPlayer = mock(SoundPlayer.class);
        scene.setSoundPlayer(soundPlayer);
        assertEquals(soundPlayer, scene.getSoundPlayer());
        
        scene.setSoundPlayer(null);
        assertNotNull(scene.getSoundPlayer());
    }

    @Test
    void testBlockCollidersAndCollision() {
        Collider c1 = mock(Collider.class);
        Collider c2 = mock(Collider.class);
        List<Collider> colliders = Arrays.asList(c1, c2);
        scene.setBlockColliders(colliders);
        assertEquals(colliders, scene.getBlockColliders());

        Collider testCollider = mock(Collider.class);
        when(c1.isColliderOver(testCollider)).thenReturn(false);
        when(c2.isColliderOver(testCollider)).thenReturn(true);

        assertTrue(scene.check(testCollider));

        when(c2.isColliderOver(testCollider)).thenReturn(false);
        assertFalse(scene.check(testCollider));
    }

    @Test
    void testMineralsCollected() {
        assertEquals(0, scene.getCurrentMineralsCollected());
        scene.incrementCurrentMineralsCollected();
        assertEquals(1, scene.getCurrentMineralsCollected());
        scene.setCurrentMineralsCollected(10);
        assertEquals(10, scene.getCurrentMineralsCollected());
    }
}
