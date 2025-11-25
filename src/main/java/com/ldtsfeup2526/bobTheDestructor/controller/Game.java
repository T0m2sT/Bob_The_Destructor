package com.ldtsfeup2526.bobTheDestructor.controller;

import com.ldtsfeup2526.bobTheDestructor.view.GUI;
import com.ldtsfeup2526.bobTheDestructor.view.GUILanterna;

import java.io.IOException;

public class Game {
    private GUI gui;

    public Game() throws IOException {
        System.out.println( "Starting GUI... ");
        gui = new GUILanterna();
    }

    public void run() throws IOException {

    }
}
