package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.soundEffects.JumpingSound;
import com.ldtsfeup2526.bobTheDestructor.model.game.soundEffects.MiningSound;
import com.ldtsfeup2526.bobTheDestructor.model.game.soundEffects.WalkingSound;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.sounds.*;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;

import javax.sound.sampled.Clip;
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

    private SoundPlayer soundtrackPlayer;
    private SoundPlayer walkingSoundPlayer;
    private SoundPlayer miningSoundPlayer;
    private SoundPlayer jumpingSoundPlayer;

    public SceneManager () throws IOException {
        cavesPathChosen = chooseCaves();
        initializeSounds();
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
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

    private void initializeSounds() {
        this.soundtrackPlayer = createSoundtrackPlayer();
        this.walkingSoundPlayer = createSoundEffectsPlayer(WalkingSound::new);
        this.miningSoundPlayer = createSoundEffectsPlayer(MiningSound::new);
        this.jumpingSoundPlayer = createSoundEffectsPlayer(JumpingSound::new);

        if (soundtrackPlayer != null) soundtrackPlayer.start();
    }

    private SoundPlayer createSoundtrackPlayer() {
        try {
            GameSoundtrack soundtrack = new GameSoundtrack();
            Clip gameClip = new SoundLoader().loadSound(soundtrack.getAudioInput(), soundtrack.getSoundtrackClip());
            return new BackgroundMusicPlayer(gameClip);
        } catch (Exception e) {
            return new NullSoundPlayer();
        }
    }

    private SoundPlayer createSoundEffectsPlayer(java.util.concurrent.Callable<com.ldtsfeup2526.bobTheDestructor.model.game.soundEffects.SoundEffects> soundFactory) {
        try {
            com.ldtsfeup2526.bobTheDestructor.model.game.soundEffects.SoundEffects sound = soundFactory.call();
            Clip clip = new SoundLoader().loadSound(sound.getAudioInput(), sound.getSoundtrackClip());
            return new SoundEffectsPlayer(clip);
        } catch (Exception e) {
            return new NullSoundPlayer();
        }
    }

    public SoundPlayer getSoundtrackPlayer() { return soundtrackPlayer; }
    public SoundPlayer getWalkingSoundPlayer() { return walkingSoundPlayer; }
    public SoundPlayer getMiningSoundPlayer() { return miningSoundPlayer; }
    public SoundPlayer getJumpingSoundPlayer() { return jumpingSoundPlayer; }

    public void stopAllSounds() {
        if (soundtrackPlayer != null) soundtrackPlayer.stop();
        if (walkingSoundPlayer != null) walkingSoundPlayer.stop();
        if (miningSoundPlayer != null) miningSoundPlayer.stop();
        if (jumpingSoundPlayer != null) jumpingSoundPlayer.stop();
    }
}
