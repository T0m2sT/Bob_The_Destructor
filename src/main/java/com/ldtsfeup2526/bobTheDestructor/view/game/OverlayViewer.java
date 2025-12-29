package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.*;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteInstance;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.text.NumberParser;

import java.io.IOException;

public class OverlayViewer implements ElementViewer<SceneManager> {
    private final Sprite overlay;
    private final Sprite marker;
    private final NumberParser[] numberParsers;

    public OverlayViewer(SpriteLoader spriteLoader) throws IOException {
        overlay = spriteLoader.get("sprites/ui_overlay/ui_overlay.png");
        marker = spriteLoader.get("sprites/ui_overlay/marker.png");
        numberParsers = new NumberParser[] {
                new NumberParser(spriteLoader, "sprites/ui_overlay/num_large/num", 5),
                new NumberParser(spriteLoader, "sprites/ui_overlay/num_small/num", 4)
        };
    }

    @Override
    public void draw(SceneManager model, GUI gui, double deltaTime) {
        overlay.draw(new Position(0, 0), gui);
        //System.out.println(model.getCurrentCavePathIndex());
        marker.draw(new Position(157, 20 + model.getCurrentCavePathIndex() * 15), gui);

        for (SpriteInstance spriteInstance : numberParsers[0].get(String.valueOf(model.getTotalMineralsCollected()))) {
            spriteInstance.sprite().setOffset(spriteInstance.offset());
            spriteInstance.sprite().draw(new Position(8, 2), gui);
        }

        for (SpriteInstance spriteInstance : numberParsers[1].get(String.valueOf(model.getScene().getCurrentMineralsCollected()))) {
            spriteInstance.sprite().setOffset(spriteInstance.offset());
            spriteInstance.sprite().draw(new Position(5, 8), gui);
        }
    }
}
