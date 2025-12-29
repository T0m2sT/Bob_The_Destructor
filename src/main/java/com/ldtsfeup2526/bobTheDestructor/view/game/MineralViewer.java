package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralState;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralType;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.PointingDirection;
import com.ldtsfeup2526.bobTheDestructor.view.Animation;
import com.ldtsfeup2526.bobTheDestructor.view.ElementViewer;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MineralViewer implements ElementViewer<MineralModel> {
    private final Map<MineralType, Animation[]> spriteMap;
    private final Random random = new Random();
    private final Map<MineralModel, Animation[]> animationPerModel = new HashMap<>();
    private final Map<MineralType, Sprite> selectedSpriteMap = new HashMap<>();

    public MineralViewer(SpriteLoader spriteLoader) throws IOException {
        spriteMap = new HashMap<>();

        Animation shineAnim = new Animation("ShineAnim", new Sprite[] {
                spriteLoader.get("sprites/gems/gem1.png"),
                spriteLoader.get("sprites/gems/gem2.png"),
                spriteLoader.get("sprites/gems/gem3.png"),
                spriteLoader.get("sprites/gems/gem4.png")},
                0.1,
                true
        );

        Animation crackAnim = new Animation("CrackAnim", new Sprite[] {
                spriteLoader.get("sprites/gems/cracked/pink_crack1.png"),
                spriteLoader.get("sprites/gems/cracked/pink_crack2.png"),
                spriteLoader.get("sprites/gems/cracked/pink_crack3.png"),
                spriteLoader.get("sprites/gems/cracked/pink_crack4.png")},
                0.05,
                false
        );

        spriteMap.put(MineralType.PINK, new Animation[] {shineAnim, crackAnim});

        shineAnim = new Animation("ShineAnim", new Sprite[] {
                spriteLoader.get("sprites/gems/gem7.png"),
                spriteLoader.get("sprites/gems/gem8.png"),
                spriteLoader.get("sprites/gems/gem9.png"),
                spriteLoader.get("sprites/gems/gem10.png")},
                0.1,
                true
        );

        crackAnim = new Animation("CrackAnim", new Sprite[] {
                spriteLoader.get("sprites/gems/cracked/yellow_crack1.png"),
                spriteLoader.get("sprites/gems/cracked/yellow_crack2.png"),
                spriteLoader.get("sprites/gems/cracked/yellow_crack3.png"),
                spriteLoader.get("sprites/gems/cracked/yellow_crack4.png")},
                0.05,
                false
        );

        spriteMap.put(MineralType.YELLOW, new Animation[] {shineAnim, crackAnim});

        shineAnim = new Animation("ShineAnim", new Sprite[] {
                spriteLoader.get("sprites/gems/gem13.png"),
                spriteLoader.get("sprites/gems/gem14.png"),
                spriteLoader.get("sprites/gems/gem15.png"),
                spriteLoader.get("sprites/gems/gem16.png")},
                0.1,
                true
        );

        crackAnim = new Animation("CrackAnim", new Sprite[] {
                spriteLoader.get("sprites/gems/cracked/blue_crack1.png"),
                spriteLoader.get("sprites/gems/cracked/blue_crack2.png"),
                spriteLoader.get("sprites/gems/cracked/blue_crack3.png"),
                spriteLoader.get("sprites/gems/cracked/blue_crack4.png")},
                0.05,
                false
        );

        spriteMap.put(MineralType.BLUE, new Animation[] {shineAnim, crackAnim});

        selectedSpriteMap.put(MineralType.PINK, spriteLoader.get("sprites/gems/gem5.png"));
        selectedSpriteMap.put(MineralType.YELLOW, spriteLoader.get("sprites/gems/gem11.png"));
        selectedSpriteMap.put(MineralType.BLUE, spriteLoader.get("sprites/gems/gem17.png"));
    }

    @Override
    public void draw(MineralModel model, GUI gui, double deltaTime) {
        Animation anim;
        if (model.getState() == MineralState.DESTROYED) {
            anim = createAnimForSingleModel(model)[1];
        } else {
            anim = createAnimForSingleModel(model)[0];
        }

        anim.update(deltaTime);
        Sprite sprite = anim.getSprites()[anim.getFrame()];
        Sprite selectedSprite = selectedSpriteMap.get(model.getType());

        switch (model.getDirection()) {
            case PointingDirection.UP:
                sprite.draw(model.getPosition(), gui);
                if (model.getState() == MineralState.SELECTED) {
                    selectedSprite.draw(model.getPosition(), gui);
                }
                break;
            case PointingDirection.DOWN:
                sprite.drawFlipY(model.getPosition(), gui);
                if (model.getState() == MineralState.SELECTED) {
                    selectedSprite.drawFlipY(model.getPosition(), gui);
                }
                break;
            case PointingDirection.LEFT:
                sprite.drawRotLeft(model.getPosition(), gui);
                if (model.getState() == MineralState.SELECTED) {
                    selectedSprite.drawRotLeft(model.getPosition(), gui);
                }
                break;
            case PointingDirection.RIGHT:
                sprite.drawRotRight(model.getPosition(), gui);
                if (model.getState() == MineralState.SELECTED) {
                    selectedSprite.drawRotRight(model.getPosition(), gui);
                }
                break;
        }


        if (anim.isFinished()) {
            model.notifyWhenAnimFinished(anim.getAnimName());
        }

    }

    private Animation[] createAnimForSingleModel(MineralModel model) {
        if (!animationPerModel.containsKey(model)) {
            Animation[] anims = spriteMap.get(model.getType());
            Animation shineAnim = anims[0].copy();
            shineAnim.setCooldownTime(random.nextDouble(1, 4));
            animationPerModel.put(model, new Animation[] {shineAnim, anims[1].copy()});
        }

        return animationPerModel.get(model);
    }
}
