package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.states.game.PlayerState;
import com.ldtsfeup2526.bobTheDestructor.view.elements.PlayerViewer;
import com.ldtsfeup2526.bobTheDestructor.view.elements.BlocksViewer;
import com.ldtsfeup2526.bobTheDestructor.view.elements.PlayerViewerIdle;
import com.ldtsfeup2526.bobTheDestructor.view.elements.PlayerViewerJumping;
import com.ldtsfeup2526.bobTheDestructor.view.elements.PlayerViewerMoving;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class ViewerProvider {
    private final Map<PlayerState, PlayerViewer> playerViewers;
    private final BlocksViewer blocksViewer;

    public ViewerProvider(SpriteLoader spriteLoader) throws IOException {
        this.playerViewers = new EnumMap<>(PlayerState.class);
        playerViewers.put(PlayerState.IDLE, new PlayerViewerIdle(spriteLoader));
        playerViewers.put(PlayerState.MOVING, new PlayerViewerMoving(spriteLoader));
        playerViewers.put(PlayerState.JUMPING, new PlayerViewerJumping(spriteLoader));

        this.blocksViewer = new BlocksViewer(spriteLoader);
    }

    public PlayerViewer getPlayerViewer(PlayerState state) {
        return playerViewers.get(state);
    }

    public BlocksViewer getBlocksViewer() {return blocksViewer;}
}
