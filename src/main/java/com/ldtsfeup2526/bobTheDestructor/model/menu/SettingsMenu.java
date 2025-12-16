package com.ldtsfeup2526.bobTheDestructor.model.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.sounds.BackgroundMusicPlayer;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundLoader;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;
import com.sun.tools.javac.Main;

import javax.sound.sampled.Clip;
import java.util.Collections;
import java.util.List;

public class SettingsMenu extends Menu {
    @Override
    protected List<Button> createButtons() {
        Button volume = new Button(ButtonType.VOLUME, ButtonState.SELECTED, new Position(Game.resolution.getWidth()/2, (Game.resolution.getHeight()/2)));
        return Collections.singletonList(volume);
    }

    @Override
    protected SoundPlayer createSoundPlayer() {
        try {
            MainMenuSoundtrack soundtrack = new MainMenuSoundtrack();
            Clip mainMenuClip = new SoundLoader().loadSound(soundtrack.getAudioInput(), soundtrack.getSoundtrackClip());
            return new BackgroundMusicPlayer(mainMenuClip);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
