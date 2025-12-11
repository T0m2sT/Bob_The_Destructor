package com.ldtsfeup2526.bobTheDestructor.controller.game;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.game.Scene;

import javax.sound.sampled.Control;
import java.io.IOException;
import java.util.List;

public class SceneController extends Controller<Scene> {
    public SceneController(Scene scene) {
        super(scene);
    }

    @Override
    public void update(Game game, List<Action> actions) throws IOException {

    }
}
