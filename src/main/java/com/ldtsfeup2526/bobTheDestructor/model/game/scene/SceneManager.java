package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.sun.tools.attach.VirtualMachine.list;

public class SceneManager {
    private final SceneBuilder sceneBuilder;
    private Scene scene;
    private int numberOfCaves = 10;
    private List<String> cavesPathChosen;
    private int currentCavePathIndex;
    private final PlayerModel playerModel;

    public SceneManager (SceneBuilder sceneBuilder) throws IOException {
        cavesPathChosen = chooseCaves();
        this.sceneBuilder = sceneBuilder;
        this.scene = sceneBuilder.createScene(getNextCavePath(), new PlayerModel(new Position(0, 0)));
        this.playerModel = scene.getPlayerModel();
    }

    public Scene getScene() {
        return scene;
    }

    public SceneBuilder getSceneBuilder() {
        return sceneBuilder;
    }

    private List<String> chooseCaves() {
        List<String> caveList = new ArrayList<>();
        for (int i = 0; i < numberOfCaves; i++) {
            caveList.add("caves/cave" + String.valueOf(i) + "/");
        }

        Collections.shuffle(caveList);
        caveList.subList(5, caveList.size()).clear();

        return caveList;
    }

    private String getNextCavePath() {
        if (currentCavePathIndex >= cavesPathChosen.size()) {
            return null;
        }

        String path = cavesPathChosen.get(currentCavePathIndex);
        currentCavePathIndex++;

        return path;
    }

    public List<String> getCavesPathChosen() {
        return cavesPathChosen;
    }

    public void update(Game game) throws IOException {
        PlayerModel playerModel = scene.getPlayerModel();
        if (playerModel.getPosition().getY() > Game.resolution.getHeight()) {
            String path = getNextCavePath();
            //System.out.println(path);
            if (Objects.equals(path, null)) {
                getScene().getSoundPlayer().stop();
                game.setState(new MainMenuState(new MainMenu(), game.getSpriteLoader()));
                getScene().getSoundPlayer().stop();
                return;
            }
            this.scene = sceneBuilder.createScene(path, playerModel);
        }
    }
}
