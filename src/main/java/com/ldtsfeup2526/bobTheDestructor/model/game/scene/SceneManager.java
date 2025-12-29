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
    private Scene scene;
    private int numberOfCaves = 10;
    private final List<String> cavesPathChosen;
    private int currentCavePathIndex;
    private int totalMineralsCollected = 0;
    private double timePassed;

    public SceneManager () throws IOException {
        cavesPathChosen = chooseCaves();
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private List<String> chooseCaves() {
        List<String> caveList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            caveList.add("caves/cave" + i + "/");
        }

        Collections.shuffle(caveList);

        return new ArrayList<>(caveList.subList(0, 5));
    }

    public String getNextCavePath() {
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

    public int getCurrentCavePathIndex(){
        return currentCavePathIndex-1;
    }

    public int getTotalMineralsCollected() {
        return totalMineralsCollected;
    }

    public void updateTotalMineralsCollected() {
        totalMineralsCollected += scene.getCurrentMineralsCollected();
    }

    public void updateTime(double deltaTime) {
        timePassed += deltaTime;
    }

    public double getTimePassed() {
        return timePassed;
    }
}
