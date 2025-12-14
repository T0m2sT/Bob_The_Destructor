package com.ldtsfeup2526.bobTheDestructor.view.game;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.*;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.ElementViewer;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerViewer implements ElementViewer<PlayerModel> {
    private final Map<Class<?>, Sprite[]> spriteMap;

    public PlayerViewer(SpriteLoader spriteLoader) throws IOException {
        spriteMap = new HashMap<>();

        spriteMap.put(IdleState.class, new Sprite[] {
            spriteLoader.get("sprites/player/player1.png")
        });

        spriteMap.put(WalkingState.class, new Sprite[] {
                spriteLoader.get("sprites/player/player_walk1.png"),
                spriteLoader.get("sprites/player/player_walk2.png"),
                spriteLoader.get("sprites/player/player_walk3.png"),
                spriteLoader.get("sprites/player/player_walk4.png")
        });

        spriteMap.put(JumpingState.class, new Sprite[] {
                spriteLoader.get("sprites/player/player_jump1.png"),
                spriteLoader.get("sprites/player/player_jump2.png")
        });

        spriteMap.put(FallingState.class, new Sprite[] {
                spriteLoader.get("sprites/player/player_fall1.png"),
                spriteLoader.get("sprites/player/player_fall2.png")
        });

        for (Sprite[] spriteArray : spriteMap.values()) {
            for (Sprite sprite : spriteArray) {
                sprite.setOffset(new Position(-2, -5));
            }
        }

    }
    public void draw(PlayerModel model, GUI gui) {
        Sprite sprite = spriteMap.get(IdleState.class)[0];
        if (model.getRigidBody().getVelocity().getX() > 0) {
            sprite.draw(model.getPosition(), gui);
        } else {
            sprite.drawFlipX(model.getPosition(), gui);
        }
    }
}
