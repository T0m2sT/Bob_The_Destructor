package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.CollisionChecker;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Button;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.sounds.NullSoundPlayer;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;

import java.util.ArrayList;
import java.util.List;

public class Scene implements CollisionChecker {
    private final String caveFilePath;
    private final PlayerModel playerModel;
    private List<Collider> blockColliders;
    private final List<MineralModel> mineralModels;
    private SoundPlayer soundPlayer;
    private int currentMineralsCollected = 0;

    public Scene(String caveFilePath, PlayerModel playerModel, List<MineralModel> mineralModels) {
        this.caveFilePath = caveFilePath;
        this.playerModel = playerModel;
        this.mineralModels = mineralModels;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public String getCaveFilePath() {
        return caveFilePath;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer) {this.soundPlayer = soundPlayer;}

    public SoundPlayer getSoundPlayer() {
        if (soundPlayer == null) {
            soundPlayer = new NullSoundPlayer();
        }
        return soundPlayer;
    }

    public void setBlockColliders(List<Collider> blockColliders) {
        this.blockColliders = blockColliders;
    }

    public List<Collider> getBlockColliders() {
        return blockColliders;
    }

    public boolean check(Collider collider) {
        for (Collider c : blockColliders) {
            if (c.isColliderOver(collider)) {
                return true;
            }
        }
        return false;
    }

    public List<MineralModel> getMineralModels() {
        return mineralModels;
    }

    public int getCurrentMineralsCollected() {
        return currentMineralsCollected;
    }

    public void setCurrentMineralsCollected(int currentMineralsCollected) {
        this.currentMineralsCollected = currentMineralsCollected;
    }

    public void incrementCurrentMineralsCollected() {
        this.currentMineralsCollected++;
    }

    public void unselectAllMinerals() {
        for (MineralModel mineralModel : getMineralModels()) {
            if (mineralModel.getState() == MineralState.SELECTED) {
                mineralModel.setState(MineralState.UNSELECTED);
            }
        }
    }

    public void cleanupMinerals() {
        getMineralModels().removeIf(mineralModel -> mineralModel.getState() == MineralState.CLEANUP);
    }
}
