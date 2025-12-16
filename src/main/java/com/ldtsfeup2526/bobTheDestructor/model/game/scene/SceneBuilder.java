package com.ldtsfeup2526.bobTheDestructor.model.game.scene;

import com.ldtsfeup2526.bobTheDestructor.model.game.elements.Player.PlayerModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.elements.game.MineralModel;
import com.ldtsfeup2526.bobTheDestructor.model.game.physics.Collider;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Vector;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SceneBuilder implements ISceneBuilder{
    private final SpriteLoader spriteLoader;
    private final Random random = new Random();
    private final float probabilityOfMineralSpawn = 0.4f;

    public SceneBuilder(SpriteLoader spriteLoader) {
        this.spriteLoader = spriteLoader;
    }

    public Scene createScene(String caveFilePath, PlayerModel playerModel) throws IOException {
        BufferedImage structureImage = spriteLoader.getBufferedImage(caveFilePath+"structure.png");
        BufferedImage enterImage = spriteLoader.getBufferedImage(caveFilePath+"enter.png");
        BufferedImage mineralImage = spriteLoader.getBufferedImage(caveFilePath+"ores.png");

        playerModel.getRigidBody().setPosition(new Vector(findEntrancePos(enterImage)));
        Scene scene = new Scene(caveFilePath, playerModel, createMinerals(mineralImage));
        scene.setBlockColliders(createColliders(structureImage));

        return scene;
    }

    private List<Collider> createColliders(BufferedImage image) {
        List<Collider> colliders = new ArrayList<>();

        for (int y = 0; y < image.getHeight(); y+=8) {
            for (int x = 0; x < image.getWidth(); x+= 8) {
                if ((image.getRGB(x, y) >> 24) != 0) {
                    colliders.add(new Collider(new Position(x, y)));
                }
            }
        }

        return colliders;
    }

    private Position findEntrancePos(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y+=8) {
            for (int x = 0; x < image.getWidth(); x+= 8) {
                if ((image.getRGB(x, y) >> 24) != 0) {
                    return new Position(x, y);
                }
            }
        }

        return new Position(15, 15);
    }

    private List<MineralModel> createMinerals(BufferedImage image) {
        List<MineralModel> mineralModels = new ArrayList<>();

        for (int y = 0; y < image.getHeight(); y+=8) {
            for (int x = 0; x < image.getWidth(); x+= 8) {
                if ((image.getRGB(x, y) >> 24) != 0) {
                    if (random.nextFloat() > probabilityOfMineralSpawn) {
                        continue;
                    }

                    mineralModels.add(new MineralModel(new Position(x, y), Integer.toHexString(image.getRGB(x, y))));
                    System.out.println(Integer.toHexString(image.getRGB(x, y)));
                }
            }
        }

        return mineralModels;
    }

}
