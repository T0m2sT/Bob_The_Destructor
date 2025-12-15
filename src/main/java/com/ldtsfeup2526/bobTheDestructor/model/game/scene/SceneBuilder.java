package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.sounds.BackgroundMusicPlayer;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundLoader;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundPlayer;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SceneBuilder implements ISceneBuilder{
    private final SpriteLoader spriteLoader;

    public SceneBuilder(SpriteLoader spriteLoader) {
        this.spriteLoader = spriteLoader;
    }

    public Scene createScene() throws IOException {
        String caveFilePath = "caves/";
        BufferedImage image = spriteLoader.getBufferedImage(caveFilePath+"structure.png");
        Scene scene = new Scene(caveFilePath);
        scene.setBlockColliders(createColliders(image));
        scene.setSoundPlayer(createSoundPlayer());

        return scene;
    }

    private List<Collider> createColliders(BufferedImage image) {
        List<Collider> colliders = new ArrayList<>();

        for (int y = 0; y < image.getHeight(); y+=8) {
            for (int x = 0; x < image.getWidth(); x+= 8) {
                if (image.getRGB(x, y) != 0) {
                    colliders.add(new Collider(new Position(x, y)));
                }
            }
        }

        return colliders;
    }

    private SoundPlayer createSoundPlayer() {
        try {
            GameSoundtrack soundtrack = new GameSoundtrack();
            Clip gameClip = new SoundLoader().loadSound(soundtrack.getAudioInput(), soundtrack.getSoundtrackClip());
            return new BackgroundMusicPlayer(gameClip);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
