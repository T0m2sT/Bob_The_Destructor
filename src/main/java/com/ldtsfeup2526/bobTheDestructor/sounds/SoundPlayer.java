package com.ldtsfeup2526.bobTheDestructor.sounds;

import javax.sound.sampled.Clip;

public interface SoundPlayer {

    void start();
    void stop();

    void setSound(Clip sound);
    Clip getSound();
}
