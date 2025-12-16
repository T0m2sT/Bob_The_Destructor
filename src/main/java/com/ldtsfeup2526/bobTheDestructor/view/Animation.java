package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.sun.jdi.IntegerValue;

import java.util.List;

public class Animation {
    private Sprite[] sprites;
    private double frameTime;
    private double elapsedTime;
    private int currentFrame;
    private boolean loop;
    private String name;

    public Animation(String name, Sprite[] sprites, double frameTime, boolean loop) {
        this.name = name;
        this.sprites = sprites;
        this.frameTime = frameTime;
        this.loop = loop;
    }

    public void update(double deltaTime) {
        elapsedTime += deltaTime;
        currentFrame = (int) (elapsedTime / frameTime);

        if (currentFrame >= sprites.length) {
            if (loop) {
                elapsedTime = 0;
                currentFrame = 0;
            } else {
                currentFrame = sprites.length-1;
                isFinished();
            }
        }
    }

    public int getFrame() {
        return currentFrame;
    }

    public void stop() {
        currentFrame = 0;
        elapsedTime = 0;
    }

    public Sprite[] getSprites() {
        return sprites;
    }

    public boolean isFinished() {
        return !loop && currentFrame == sprites.length - 1;
    }

    public String getAnimName() {
        return name;
    }

    public Animation copy() {
        return new Animation(name, sprites, frameTime, loop);
    }
}
