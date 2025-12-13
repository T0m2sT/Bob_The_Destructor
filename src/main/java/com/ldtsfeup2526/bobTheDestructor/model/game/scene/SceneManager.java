package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import java.io.IOException;

public class SceneManager {
    private final SceneBuilder sceneBuilder;
    private Scene scene;
    public SceneManager (SceneBuilder sceneBuilder) throws IOException {
        this.sceneBuilder = sceneBuilder;
        this.scene = sceneBuilder.createScene();
    }

    public Scene getScene() {
        return scene;
    }

    public SceneBuilder getSceneBuilder() {
        return sceneBuilder;
    }
}
