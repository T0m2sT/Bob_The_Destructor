package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.GameSettings;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Menu;

import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class MenuController<T extends Menu> extends Controller<T> {
    private final ButtonController buttonController;

    public MenuController(T model) {
        super(model);
        this.buttonController = new ButtonController(getModel());

        if (getModel().getSoundPlayer().getSound() != null) {
            if (getModel().getSoundPlayer().getSound().isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) getModel().getSoundPlayer().getSound().getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-20.0f + GameSettings.getInstance().getMasterGain());
            } else {
                System.err.println("MASTER_GAIN control not supported on this Clip.");
            }

            getModel().getSoundPlayer().start();
        }
    }

    @Override
    public void update(Game game, List<Action> actions) throws IOException {
        for (Action action : actions) {
            switch (action) {
                case UP:
                case LEFT:
                    this.getModel().moveUp();
                    break;
                case DOWN:
                case RIGHT:
                    this.getModel().moveDown();
                    break;
                case QUIT:
                    onQuit(game);
                    break;
                default:
                    buttonController.update(game, List.of(action));
                    break;
            }
        }
    }

    protected abstract void onQuit(Game game);
}
