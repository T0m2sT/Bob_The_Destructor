package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.controller.game.elements.PlayerController;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.*;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.states.State;
import com.ldtsfeup2526.bobTheDestructor.view.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerViewer implements ElementViewer<PlayerModel> {
    private final Map<Class<?>, Animation> spriteMap;
    private Class<?> playerState = IdleState.class;

    public PlayerViewer(SpriteLoader spriteLoader) throws IOException {
        spriteMap = new HashMap<>();

        Animation tempAnim = new Animation("IdleAnim", new Sprite[] {
                spriteLoader.get("sprites/player/player1.png")},
                0.1,
                false
        );

        spriteMap.put(IdleState.class, tempAnim);

        tempAnim = new Animation("WalkAnim", new Sprite[] {
                spriteLoader.get("sprites/player/player_walk4.png"),
                spriteLoader.get("sprites/player/player_walk3.png"),
                spriteLoader.get("sprites/player/player_walk2.png"),
                spriteLoader.get("sprites/player/player_walk1.png")},
                0.1,
                true
        );

        spriteMap.put(WalkingState.class, tempAnim);

        tempAnim = new Animation("JumpAnim", new Sprite[] {
                spriteLoader.get("sprites/player/player_jump1.png"),
                spriteLoader.get("sprites/player/player_jump2.png")},
                0.1,
                false
        );

        spriteMap.put(JumpingState.class, tempAnim);

        tempAnim = new Animation("FallAnim", new Sprite[] {
                spriteLoader.get("sprites/player/player_fall1.png"),
                spriteLoader.get("sprites/player/player_fall2.png")},
                0.1,
                false
        );

        spriteMap.put(FallingState.class, tempAnim);

        tempAnim = new Animation("MineAnim", new Sprite[] {
                spriteLoader.get("sprites/player/player_mine1.png"),
                spriteLoader.get("sprites/player/player_mine1.png"),
                spriteLoader.get("sprites/player/player_mine2.png"),
                spriteLoader.get("sprites/player/player_mine3.png"),
                spriteLoader.get("sprites/player/player_mine3.png"),
                spriteLoader.get("sprites/player/player_mine3.png"),
                spriteLoader.get("sprites/player/player_mine3.png")},
                0.1,
                false
        );

        spriteMap.put(MiningState.class, tempAnim);

        for (Animation spriteArray : spriteMap.values()) {
            for (Sprite sprite : spriteArray.getSprites()) {
                sprite.setOffset(new Position(-2, -6));
            }
        }

    }
    public void draw(PlayerModel model, GUI gui, double deltaTime) {
        if (playerState != model.getState().getClass()) {
            spriteMap.get(playerState).stop();
            playerState = model.getState().getClass();
        }
        Animation anim = spriteMap.get(model.getState().getClass());
        anim.update(deltaTime);
        if (model.isLookingRight()) {
            anim.getSprites()[anim.getFrame()].draw(model.getPosition(), gui);
        } else {
            anim.getSprites()[anim.getFrame()].drawFlipX(model.getPosition(), gui);
        }

        if (Objects.equals(anim.getAnimName(), "MineAnim") && anim.getFrame() == 2) {
            // CODE SMELL: calling model methods
            model.notifyWhenPickaxeHit();
        }

        if (anim.isFinished()) {
            // CODE SMELL: calling model methods
            // the alternatives would change a lot of the architecture
            model.notifyWhenAnimFinished(anim.getAnimName());

        }
    }
}
