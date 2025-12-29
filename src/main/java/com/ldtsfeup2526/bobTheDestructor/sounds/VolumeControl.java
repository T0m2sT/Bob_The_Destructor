package com.ldtsfeup2526.bobTheDestructor.sounds;

public interface VolumeControl {
    void setMasterVolume(float volume);
    void setMusicVolume(float volume);
    void setSFXVolume(float volume);
    float getMasterVolume();
    float getMusicVolume();
    float getSFXVolume();
}
