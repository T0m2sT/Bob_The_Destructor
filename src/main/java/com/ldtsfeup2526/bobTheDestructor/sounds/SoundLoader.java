package com.ldtsfeup2526.bobTheDestructor.sounds;

import javax.sound.sampled.*;
import java.io.IOException;

public interface SoundLoader {
    Clip get(String soundFilePath);
}
