package com.ldtsfeup2526.bobTheDestructor;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.gui.GUILanterna;
import com.ldtsfeup2526.bobTheDestructor.states.State;
import com.ldtsfeup2526.bobTheDestructor.view.InputReader;
import com.ldtsfeup2526.bobTheDestructor.view.InputReaderLanterna;

import java.io.IOException;

public class Game {
    private final GUILanterna gui;
    private InputReader inputReader;
    private State<?> state;

    public Game() throws IOException {
        System.out.println( "Starting GUI... ");
        gui = new GUILanterna();
        inputReader = new InputReaderLanterna(gui.getScreen());
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

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = deltaTime - elapsedTime;

            if (sleepTime > 0) Thread.sleep(sleepTime);
        }

        gui.close();
    }
}