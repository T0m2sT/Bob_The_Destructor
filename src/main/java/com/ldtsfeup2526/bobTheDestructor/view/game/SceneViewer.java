package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.Animation;
import com.ldtsfeup2526.bobTheDestructor.view.ElementViewer;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneViewer implements ElementViewer<Scene> {
    private final Map<String, Sprite[]> spriteMap = new HashMap<>();
    private final Animation backgroundAnim;
    private final SpriteLoader spriteLoader;

    public SceneViewer(SpriteLoader spriteLoader) throws IOException {
        this.spriteLoader = spriteLoader;

        backgroundAnim = new Animation("BackgroundAnim", new Sprite[] {
                spriteLoader.get("background/bg1.png"),
                spriteLoader.get("background/bg2.png"),
                spriteLoader.get("background/bg3.png")},
                0.125f,
                true
        );
    }

    public void retrieveCaves(List<String> cavesPath) throws IOException {
        for (String path : cavesPath) {
            spriteMap.put(path, new Sprite[] {
                    spriteLoader.get(path+"structure.png"),
                    spriteLoader.get(path+"details.png")
            });
        }
    }

    public void draw(Scene model, GUI gui, double deltaTime) {
        Sprite structureSprite = spriteMap.get(model.getCaveFilePath())[0];
        Sprite detailsSprite = spriteMap.get(model.getCaveFilePath())[1];

        backgroundAnim.update(deltaTime);
        backgroundAnim.getSprites()[backgroundAnim.getFrame()].draw(new Position(0, 0), gui);

        structureSprite.draw(new Position(0, 0), gui);
        detailsSprite.draw(new Position(0, 0), gui);
    }

}
