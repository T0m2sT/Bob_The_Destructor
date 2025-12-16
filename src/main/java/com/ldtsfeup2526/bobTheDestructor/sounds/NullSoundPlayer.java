package com.ldtsfeup2526.bobTheDestructor.sounds;

import javax.sound.sampled.Clip;

public final class NullSoundPlayer implements SoundPlayer {
    @Override public void start() { /* do nothing */ }
    @Override public void stop() { /* do nothing */ }
    @Override public void setSound(Clip sound) { /* do nothing */ }
    @Override public Clip getSound() { return null; }
}