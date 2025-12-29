package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerState;

public interface PlayerStateListener {
    void onPlayerStateEnter(PlayerState playerState);
    void onPlayerStateExit(PlayerState playerState);
}
