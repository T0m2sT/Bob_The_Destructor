package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.GameSettings;
import com.ldtsfeup2526.bobTheDestructor.model.menu.WidgetType;
import com.ldtsfeup2526.bobTheDestructor.model.menu.MainMenu;
import com.ldtsfeup2526.bobTheDestructor.model.menu.SettingsMenu;
import com.ldtsfeup2526.bobTheDestructor.states.MainMenuState;

import java.io.IOException;
import java.util.List;

public class SettingsMenuController extends MenuController<SettingsMenu> {
    private final float[] levels = new float[]{-20f, -10f, 0f, 10f, 20f};
    private int currentVolumeIdx;

    public SettingsMenuController(SettingsMenu menu) {
        super(menu);
    }

    @Override
    protected void onQuit(Game game) throws IOException {
        game.setState(new MainMenuState(new MainMenu(), game.getSpriteLoader(), game.getSoundManager()));
    }

    @Override
    public void update(Game game, List<Action> actions) throws IOException {
        for (Action action : actions) {
            switch (action) {
                case UP:
                case DOWN:
                    break;
                case QUIT:
                    onQuit(game);
                    break;
                case SELECT:
                    currentVolumeIdx = 0;
                    for (float level : levels) {
                        if (GameSettings.getInstance().getMasterGain() == level) {
                            break;
                        }
                        currentVolumeIdx++;
                    }
                    if (getModel().getCurrentButton().getButtonType() == WidgetType.VOLUME) {
                        currentVolumeIdx = (currentVolumeIdx + 1) % levels.length;
                        GameSettings.getInstance().setMasterGain(levels[currentVolumeIdx]);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
