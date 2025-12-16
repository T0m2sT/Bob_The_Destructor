package com.ldtsfeup2526.bobTheDestructor.model.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.sounds.BackgroundMusicPlayer;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundLoader;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;

import javax.sound.sampled.Clip;
import java.util.Arrays;
import java.util.List;

public class MainMenu extends Menu{
    @Override
    protected List<Button> createButtons() {
        Button start = new Button(ButtonType.PLAY, ButtonState.SELECTED, new Position(Game.resolution.getWidth()/2, (Game.resolution.getHeight()/2)-15));
        Button settings = new Button(ButtonType.CONFIG, ButtonState.UNSELECTED, new Position(Game.resolution.getWidth()/2, (Game.resolution.getHeight()/2)-5));
        Button credits = new Button(ButtonType.CREDITS, ButtonState.UNSELECTED, new Position(Game.resolution.getWidth()/2, (Game.resolution.getHeight()/2)+5));
        Button exit = new Button(ButtonType.EXIT, ButtonState.UNSELECTED, new Position(Game.resolution.getWidth()/2, (Game.resolution.getHeight()/2)+15));
        return Arrays.asList(start, settings, credits, exit);
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
