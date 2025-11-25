package com.ldtsfeup2526.bobTheDestructor;

import com.ldtsfeup2526.bobTheDestructor.controller.Game;

public class Application {
    public static void main(String[] args) {
        try {
            System.out.println("Starting game...");

            Game game = new Game();

            game.run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}