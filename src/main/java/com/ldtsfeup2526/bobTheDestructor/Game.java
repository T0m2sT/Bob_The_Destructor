package com.ldtsfeup2526.bobTheDestructor;

import com.googlecode.lanterna.TextColor;
import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.gui.GUILanterna;
import com.ldtsfeup2526.bobTheDestructor.gui.Resolution;
import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.game.Scene;
import com.ldtsfeup2526.bobTheDestructor.states.State;
import com.ldtsfeup2526.bobTheDestructor.states.game.GameState;
import com.ldtsfeup2526.bobTheDestructor.view.*;
import com.ldtsfeup2526.bobTheDestructor.view.elements.PlayerViewer;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    private final int PIXEL_SIZE = 6;
    private Resolution resolution = new Resolution(240, 135);
    private final GUILanterna gui;
    private final SpriteLoader spriteLoader = new GameSpriteLoader();
    private InputReader inputReader;
    private State<?> state;

    public Game() throws IOException, URISyntaxException, FontFormatException {
        System.out.println( "Starting GUI... ");
        gui = new GUILanterna(resolution, PIXEL_SIZE, "Bob, The Destructor");
        inputReader = new InputReaderLanterna(gui.getScreen());

        this.state = new GameState(new Scene(), spriteLoader);
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

            state.update(gui);

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = deltaTime - elapsedTime;

            if (sleepTime > 0) Thread.sleep(sleepTime);
        }

        //gui.close();
    }

    public void setState(State<?> state) {
        this.state = state;
    }
}