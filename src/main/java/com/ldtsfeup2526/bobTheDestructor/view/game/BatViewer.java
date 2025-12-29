package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.BatModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.BatState;
import com.ldtsfeup2526.bobTheDestructor.view.Animation;
import com.ldtsfeup2526.bobTheDestructor.view.ElementViewer;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BatViewer implements ElementViewer<BatModel> {
    private final Map<BatState, Animation> spriteMap = new HashMap<>();
    private final Map<BatModel, Map<BatState, Animation>> animationPerModel = new HashMap<>();

    public BatViewer(SpriteLoader spriteLoader) throws IOException {
        spriteMap.put(BatState.SLEEPING, new Animation("Sleep",
                new Sprite[] {
                        spriteLoader.get("sprites/bat/bat_sleep1.png"),
                        spriteLoader.get("sprites/bat/bat_sleep2.png"),
                        spriteLoader.get("sprites/bat/bat_sleep3.png"),
                        spriteLoader.get("sprites/bat/bat_sleep4.png")
                },
                0.1,
                true
                ));

        spriteMap.put(BatState.FLAPPING, new Animation("Flapping",
                new Sprite[] {
                        spriteLoader.get("sprites/bat/bat_flap1.png"),
                        spriteLoader.get("sprites/bat/bat_flap2.png"),
                        spriteLoader.get("sprites/bat/bat_flap3.png"),
                        spriteLoader.get("sprites/bat/bat_flap4.png")
                },
                0.1,
                true
        ));
    }

    @Override
    public void draw(BatModel model, GUI gui, double deltaTime) {
        Animation anim = getAnimForSingleModel(model);

        anim.update(deltaTime);
        Sprite sprite = anim.getSprites()[anim.getFrame()];
        sprite.draw(model.getPosition(), gui);
    }

    private Animation getAnimForSingleModel(BatModel model) {
        if (!animationPerModel.containsKey(model)) {
            Map<BatState, Animation> animMap = new HashMap<>();
            for (BatState batState : BatState.values()) {
                animMap.put(batState, spriteMap.get(batState).copy());
            }
            animationPerModel.put(model, animMap);
        }

        return animationPerModel.get(model).get(model.getBatState());
    }
}
