package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Button;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;

import java.util.List;

public class Scene {
    private final String caveFilePath;
    private final PlayerModel playerModel = new PlayerModel(new Position(50, 50));
    private List<Collider> blockColliders;
    private SoundPlayer soundPlayer;

    public Scene(String caveFilePath) {
        this.caveFilePath = caveFilePath;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public String getCaveFilePath() {
        return caveFilePath;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer) {this.soundPlayer = soundPlayer;}

    public SoundPlayer getSoundPlayer() {return soundPlayer;}

    public void setBlockColliders(List<Collider> blockColliders) {
        this.blockColliders = blockColliders;
    }

    public List<Collider> getBlockColliders() {
        return blockColliders;
    }
}
