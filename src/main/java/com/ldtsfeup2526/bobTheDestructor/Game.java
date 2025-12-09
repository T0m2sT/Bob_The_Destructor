package com.ldtsfeup2526.bobTheDestructor;

import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.controller.input.ActionParser;
import com.ldtsfeup2526.bobTheDestructor.gui.GUILanterna;
import com.ldtsfeup2526.bobTheDestructor.gui.Resolution;
import com.ldtsfeup2526.bobTheDestructor.model.game.Scene;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.states.State;
import com.ldtsfeup2526.bobTheDestructor.states.game.GameState;
import com.ldtsfeup2526.bobTheDestructor.states.game.MainMenuState;
import com.ldtsfeup2526.bobTheDestructor.view.*;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Game {
    public static final Resolution resolution = new Resolution(160, 90);
    private final int PIXEL_SIZE = 7;
    private final GUILanterna gui;
    private final SpriteLoader spriteLoader = new GameSpriteLoader();
    private ActionParser actionParser = new ActionParser();
    private State<?> state;

    public Game() throws IOException, URISyntaxException, FontFormatException {
        System.out.println("Starting GUI... ");
        gui = new GUILanterna(actionParser.getInputReader(), resolution, PIXEL_SIZE, "Bob, The Destructor");

        this.state = new MainMenuState(new MainMenu(), spriteLoader);
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
        int FPS = 30;
        long deltaTime = 1000/FPS;

        while (this.state != null) {
            long startTime = System.currentTimeMillis();

            /*
            List<Action> actions = actionParser.get();
            if (actions.size() != 0) {
                System.out.println(actions);
            }*/

            state.update(this, gui, actionParser);

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = deltaTime - elapsedTime;

            if (sleepTime > 0) Thread.sleep(sleepTime);
        }

        gui.close();
    }

    public void setState(State<?> state) {
        this.state = state;
    }
}