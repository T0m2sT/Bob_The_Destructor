package com.ldtsfeup2526.bobTheDestructor.view;

import com.ldtsfeup2526.bobTheDestructor.view.sprite.Sprite;

public class Animation {
    private final String name;
    private final Sprite[] sprites;
    private final double frameTime;
    private final boolean loop;
    private double elapsedTime;
    private int currentFrame;
    private double cooldownTime = 0;
    private double currentCooldownTime = 0;

    public Animation(String name, Sprite[] sprites, double frameTime, boolean loop) {
        this.name = name;
        this.sprites = sprites;
        this.frameTime = frameTime;
        this.loop = loop;
    }

    public void update(double deltaTime) {
        if (currentCooldownTime > 0) {
            currentCooldownTime -= deltaTime;
            return;
        }

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
            currentCooldownTime = cooldownTime;
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
       Animation anim = new Animation(name, sprites, frameTime, loop);
       anim.setCooldownTime(cooldownTime);
       return anim;
    }

    public void setCooldownTime(double cooldownTime) {
        currentCooldownTime = cooldownTime;
        this.cooldownTime = cooldownTime;
    }

    public double getCooldownTime() {
        return cooldownTime;
    }
}
