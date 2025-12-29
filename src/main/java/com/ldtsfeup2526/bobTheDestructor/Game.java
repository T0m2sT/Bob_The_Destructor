package com.ldtsfeup2526.bobTheDestructor;

import com.ldtsfeup2526.bobTheDestructor.controller.input.ActionParser;
import com.ldtsfeup2526.bobTheDestructor.gui.GUILanterna;
import com.ldtsfeup2526.bobTheDestructor.gui.Resolution;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.sounds.GameSoundLoader;
import com.ldtsfeup2526.bobTheDestructor.sounds.GameSoundManager;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.states.State;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.GameSpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class Game {
    public static final Resolution resolution = new Resolution(165, 90);
    private final int PIXEL_SIZE = 7;
    private final GUILanterna gui;
    private final SpriteLoader spriteLoader = new GameSpriteLoader();
    private final SoundManager soundManager = new GameSoundManager(new GameSoundLoader());
    private ActionParser actionParser = new ActionParser();
    private State<?> state;

    public Game() throws IOException, URISyntaxException, FontFormatException {
        System.out.println("Starting GUI... ");
        gui = new GUILanterna(actionParser.getInputReader(), resolution, PIXEL_SIZE, "Bob, The Destructor");

        setState(new MainMenuState(new MainMenu(), spriteLoader, getSoundManager()));
    }

    public static void main(String[] args) {
        try {
            Game game = new Game();
            game.run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException, InterruptedException {
        int FPS = 60;
        long deltaTime = 1000/FPS;

        while (this.state != null) {
            long startTime = System.currentTimeMillis();

            /*
            List<Action> actions = actionParser.get();
            if (actions.size() != 0) {
                System.out.println(actions);
            }*/

            state.update(this, gui, actionParser, 1.0/FPS);

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = deltaTime - elapsedTime;

            if (sleepTime > 0) Thread.sleep(sleepTime);
        }

        gui.close();
    }

    public void setState(State<?> state) throws IOException {
        if (this.state != null) {
            this.state.onExit(this);
        }

        this.state = state;

        if (Objects.isNull(state)) {
            return;
        }

        state.onEnter(this);
        actionParser.notifyStateChange(state);
    }

    public SpriteLoader getSpriteLoader() {
        return spriteLoader;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
}