package com.ldtsfeup2526.bobTheDestructor.sounds;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public abstract class SoundManager implements VolumeControl{
    protected final SoundLoader soundLoader;
    protected float masterVolume = 1;
    protected float musicVolume = 1;
    protected float sfxVolume = 1;

    public SoundManager(SoundLoader soundLoader) {
        this.soundLoader = soundLoader;
    }

    protected float linearToDb(float volume) {
        if (volume <= 0f){
            return -80f;
        }
        return (float) (20.0 * Math.log10(volume));
    }

    protected float dbToLinear(float db) {
        return (float) Math.pow(10.0, db / 20.0);
    }

    protected void applyVolumeToClip(Clip clip, float volume) {
        if (clip == null) return;

        if (!clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) return;

        float finalVolume = masterVolume * volume;

        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        control.setValue(linearToDb(finalVolume));
    }


    public abstract void playMusic(String soundFilePath);
    public abstract void stopMusic();

    public abstract void playSFX(String soundFilePath);

}
