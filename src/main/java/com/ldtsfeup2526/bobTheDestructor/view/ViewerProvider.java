package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.view.game.MineralViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.OverlayViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.PlayerViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.SceneViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.ButtonViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.SliderViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.WallpaperViewer;

import java.io.IOException;

public class ViewerProvider {
    private final ButtonViewer buttonViewer;
    private final SliderViewer sliderViewer;
    private final WallpaperViewer wallpaperViewer;
    private final PlayerViewer playerViewer;
    private final SceneViewer sceneViewer;
    private final MineralViewer mineralViewer;
    private final OverlayViewer overlayViewer;

    public ViewerProvider(SpriteLoader spriteLoader) throws IOException {
        this.buttonViewer = new ButtonViewer(spriteLoader);
        this.wallpaperViewer = new WallpaperViewer(spriteLoader);
        this.playerViewer = new PlayerViewer(spriteLoader);
        this.sceneViewer = new SceneViewer(spriteLoader);
        this.mineralViewer = new MineralViewer(spriteLoader);
        this.overlayViewer = new OverlayViewer(spriteLoader);
        this.sliderViewer = new SliderViewer(spriteLoader);
    }

    public PlayerViewer getPlayerViewer() {
        return playerViewer;
    }

    public ButtonViewer getButtonViewer() {
        return buttonViewer;
    }

    public WallpaperViewer getWallpaperViewer() {
        return wallpaperViewer;
    }

    public SceneViewer getSceneViewer() {
        return sceneViewer;
    }

    public MineralViewer getMineralViewer() {
        return mineralViewer;
    }

    public OverlayViewer getOverlayViewer() {
        return overlayViewer;
    }

    public SliderViewer getSliderViewer() {
        return sliderViewer;
    }
}
