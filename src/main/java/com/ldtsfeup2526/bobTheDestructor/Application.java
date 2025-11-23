package com.ldtsfeup2526.bobTheDestructor;

import java.io.IOException;

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