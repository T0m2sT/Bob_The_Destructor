package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.view.game.MineralViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.OverlayViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.PlayerViewer;
import com.ldtsfeup2526.bobTheDestructor.view.game.SceneViewer;
import com.ldtsfeup2526.bobTheDestructor.view.menu.*;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;

import java.io.IOException;

public class ViewerProvider {
    private final TitleViewer titleViewer;
    private final ButtonViewer buttonViewer;
    private final SliderViewer sliderViewer;
    private final WallpaperViewer wallpaperViewer;
    private final PlayerViewer playerViewer;
    private final SceneViewer sceneViewer;
    private final MineralViewer mineralViewer;
    private final OverlayViewer overlayViewer;
    private final LogoViewer logoViewer;

    public ViewerProvider(SpriteLoader spriteLoader) throws IOException {
        this.titleViewer = new TitleViewer(spriteLoader);
        this.buttonViewer = new ButtonViewer(spriteLoader);
        this.wallpaperViewer = new WallpaperViewer(spriteLoader);
        this.playerViewer = new PlayerViewer(spriteLoader);
        this.sceneViewer = new SceneViewer(spriteLoader);
        this.mineralViewer = new MineralViewer(spriteLoader);
        this.overlayViewer = new OverlayViewer(spriteLoader);
        this.sliderViewer = new SliderViewer(spriteLoader);
        this.logoViewer = new LogoViewer(spriteLoader);
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

    public TitleViewer getTitleViewer() {
        return titleViewer;
    }

    public LogoViewer getLogoViewer() {
        return logoViewer;
    }
}
