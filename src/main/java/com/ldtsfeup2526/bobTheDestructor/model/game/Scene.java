package com.ldtsfeup2526.bobTheDestructor.model.game;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.BlockModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.PlayerModel;

public class Scene {
    private PlayerModel player;
    private BlockModel[][] blocks;

    public Scene(PlayerModel player) { this.player = player; }
    public PlayerModel getPlayer() { return player; }

    public BlockModel[][] getBlocks() { return blocks; }
}
