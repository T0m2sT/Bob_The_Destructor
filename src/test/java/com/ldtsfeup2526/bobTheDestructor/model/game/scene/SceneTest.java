package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SceneTest {
    private Scene scene;
    private PlayerModel playerModel;
    private List<MineralModel> mineralModels;

    @BeforeEach
    void setUp() {
        playerModel = mock(PlayerModel.class);
        mineralModels = new ArrayList<>();
        scene = new Scene("path", playerModel, mineralModels);
    }

    @Test
    void testGetters() {
        assertEquals("path", scene.getCaveFilePath());
        assertEquals(playerModel, scene.getPlayerModel());
        assertEquals(mineralModels, scene.getMineralModels());
    }

    @Test
    void testBlockColliders() {
        List<Collider> colliders = new ArrayList<>();
        Collider c1 = mock(Collider.class);
        Collider c2 = mock(Collider.class);
        colliders.add(c1);
        colliders.add(c2);
        scene.setBlockColliders(colliders);
        assertEquals(colliders, scene.getBlockColliders());

        Collider checkCol = mock(Collider.class);
        when(c1.isColliderOver(checkCol)).thenReturn(false);
        when(c2.isColliderOver(checkCol)).thenReturn(true);
        assertEquals(c2, scene.check(checkCol));

        when(c2.isColliderOver(checkCol)).thenReturn(false);
        assertNull(scene.check(checkCol));
    }

    @Test
    void testMineralsCollected() {
        assertEquals(0, scene.getCurrentMineralsCollected());
        scene.setCurrentMineralsCollected(5);
        assertEquals(5, scene.getCurrentMineralsCollected());
        scene.incrementCurrentMineralsCollected();
        assertEquals(6, scene.getCurrentMineralsCollected());
    }

    @Test
    void testUnselectAllMinerals() {
        MineralModel m1 = new MineralModel(new Position(0,0), "other", 0);
        m1.setState(MineralState.SELECTED);
        MineralModel m2 = new MineralModel(new Position(0,0), "other", 0);
        m2.setState(MineralState.UNSELECTED);
        mineralModels.add(m1);
        mineralModels.add(m2);

        scene.unselectAllMinerals();
        assertEquals(MineralState.UNSELECTED, m1.getState());
        assertEquals(MineralState.UNSELECTED, m2.getState());
    }

    @Test
    void testCleanupMinerals() {
        MineralModel m1 = new MineralModel(new Position(0,0), "other", 0);
        m1.setState(MineralState.CLEANUP);
        MineralModel m2 = new MineralModel(new Position(0,0), "other", 0);
        m2.setState(MineralState.UNSELECTED);
        mineralModels.add(m1);
        mineralModels.add(m2);

        scene.cleanupMinerals();
        assertEquals(1, mineralModels.size());
        assertTrue(mineralModels.contains(m2));
    }
}
