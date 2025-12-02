package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.states.game.PlayerState;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.elements.PlayerViewer;
import com.ldtsfeup2526.bobTheDestructor.view.elements.BlocksViewer;

import java.io.IOException;

public class GameViewer extends ScreenViewer<Scene> {

    private final PlayerViewer playerViewerIdle;
    private final PlayerViewer playerViewerMoving;
    private final PlayerViewer playerViewerJumping;
    private final BlocksViewer blocksViewer;
    private PlayerModel player;

    public GameViewer(Scene model, ViewerProvider viewerProvider) {
        super(model);
        this.playerViewerIdle = viewerProvider.getPlayerViewer(PlayerState.IDLE);
        this.playerViewerMoving = viewerProvider.getPlayerViewer(PlayerState.MOVING);
        this.playerViewerJumping = viewerProvider.getPlayerViewer(PlayerState.JUMPING);

        this.blocksViewer = viewerProvider.getBlocksViewer();

        this.player = model.getPlayer();
    }

    @Override
    public void draw(GUI gui) throws IOException {
        //gui.clear();
        drawBackground(new TextColor.RGB(30, 30, 46), gui);

        drawPlayer(player, gui);

        gui.refresh();
    }

    private void drawBackground(TextColor color, GUI gui) {
        gui.drawBackground(color);
    }

    private void drawPlayer(PlayerModel player, GUI gui) {
        if (player.getState() == PlayerState.MOVING) playerViewerMoving.draw(player.getPosition(), gui);
        else if (player.getState() == PlayerState.JUMPING) playerViewerJumping.draw(player.getPosition(), gui);
        else playerViewerIdle.draw(player.getPosition(), gui);
    }
}
