package com.ldtsfeup2526.bobTheDestructor.view.screens;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.view.ViewerProvider;
import com.ldtsfeup2526.bobTheDestructor.view.game.PlayerViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.SceneViewer;

import java.io.IOException;

public class GameViewer extends ScreenViewer<SceneManager> {

    private final PlayerViewer playerViewer;
    private final SceneViewer sceneViewer;

    public GameViewer(SceneManager model, ViewerProvider viewerProvider) throws IOException {
        super(model);
        this.playerViewer = viewerProvider.getPlayerViewer();
        this.sceneViewer = viewerProvider.getSceneViewer();
        this.sceneViewer.retrieveCaves(model.getCavesPathChosen());
    }

    @Override
    public void draw(GUI gui, double deltaTime) throws IOException {

        //gui.clear();
        gui.drawBackground(new TextColor.RGB(30, 30, 46));
        sceneViewer.draw(getModel().getScene(), gui, deltaTime);
        playerViewer.draw(getModel().getScene().getPlayerModel(), gui, deltaTime);

        /* Collision Visualizer, only for testing
        for (Collider c : getModel().getScene().getBlockColliders()) {
            gui.drawPixel(c.getPosition(), new TextColor.RGB(255, 255, 255));
        }
        */

        gui.refresh();
    }
}
