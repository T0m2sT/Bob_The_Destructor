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
    private int currentMineralsCollected = 0;
    private SoundPlayer soundtrackPlayer;
    private SoundPlayer walkingSoundPlayer;
    private SoundPlayer miningSoundPlayer;
    private SoundPlayer jumpingSoundPlayer;

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

    public void setSoundtrackPlayer(SoundPlayer soundtrackPlayer) {this.soundtrackPlayer = soundtrackPlayer;}

    public SoundPlayer getSoundPlayer() {
        if (soundtrackPlayer == null) {
            soundtrackPlayer = new NullSoundPlayer();
        }
        return soundtrackPlayer;
    }

    public void setWalkingSoundPlayer(SoundPlayer walkingSoundPlayer) {this.walkingSoundPlayer = walkingSoundPlayer;}

    public SoundPlayer getWalkingSoundPlayer() {
        if (walkingSoundPlayer == null) {
            walkingSoundPlayer = new NullSoundPlayer();
        }
        return walkingSoundPlayer;
    }

    public void setMiningSoundPlayer(SoundPlayer miningSoundPlayer) {this.miningSoundPlayer = miningSoundPlayer;}

    public SoundPlayer getMiningSoundPlayer() {
        if (miningSoundPlayer == null) {
            miningSoundPlayer = new NullSoundPlayer();
        }
        return miningSoundPlayer;
    }

    public void setJumpingSoundPlayer(SoundPlayer jumpingSoundPlayer) {this.jumpingSoundPlayer = jumpingSoundPlayer;}

    public SoundPlayer getJumpingSoundPlayer() {
        if (jumpingSoundPlayer == null) {
            jumpingSoundPlayer = new NullSoundPlayer();
        }
        return jumpingSoundPlayer;
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
