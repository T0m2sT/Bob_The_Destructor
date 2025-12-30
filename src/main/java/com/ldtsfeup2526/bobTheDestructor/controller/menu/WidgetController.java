package com.ldtsfeup2526.bobTheDestructor.controller.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.controller.Controller;
import com.ldtsfeup2526.bobTheDestructor.controller.input.Action;
import com.ldtsfeup2526.bobTheDestructor.model.GameSettings;
import com.ldtsfeup2526.bobTheDestructor.model.credits.Credits;
import com.ldtsfeup2526.bobTheDestructor.model.game.scene.SceneManager;
import com.ldtsfeup2526.bobTheDestructor.model.menu.*;
import com.ldtsfeup2526.bobTheDestructor.sounds.SoundManager;
import com.ldtsfeup2526.bobTheDestructor.states.CreditsState;
import com.ldtsfeup2526.bobTheDestructor.states.SettingsMenuState;
import com.ldtsfeup2526.bobTheDestructor.states.GameState;
import com.ldtsfeup2526.bobTheDestructor.view.sprite.SpriteLoader;

import java.io.IOException;
import java.util.List;

public class WidgetController extends Controller<Menu> {
    private SoundManager soundManager;
    public WidgetController(Menu menu, SoundManager soundManager) {
        super(menu);
        this.soundManager = soundManager;
    }

    @Override
    public void update(Game game, List<Action> actions, double deltaTime) throws IOException {
        GameSettings gameSettings = GameSettings.getInstance();
        for (Action action : actions) {
            Widget currentWidget = getModel().getCurrentWidget();
            if (currentWidget == null || currentWidget.getWidgetType() == null) continue;
            WidgetType type = currentWidget.getWidgetType();
            if (type == WidgetType.PLAY) {
                if (action == Action.SELECT) {
                    SpriteLoader spriteLoader = game.getSpriteLoader();
                    game.setState(new GameState(new SceneManager(), spriteLoader, game.getSoundManager()));
                }
            } else if (type == WidgetType.CONFIG) {
                if (action == Action.SELECT) {
                    SpriteLoader spriteLoader = game.getSpriteLoader();
                    game.setState(new SettingsMenuState(new SettingsMenu(), spriteLoader, game.getSoundManager()));
                }
            } else if (type == WidgetType.CREDITS) {
                game.setState(new CreditsState(new Credits(), game.getSpriteLoader(), game.getSoundManager()));
            } else if (type == WidgetType.EXIT) {
                if (action == Action.SELECT) {
                    game.setState(null);
                }
            } else if (type == WidgetType.MASTER_VOLUME) {
                if (action == Action.LEFT) {
                    gameSettings.setMasterVolume(Math.max(gameSettings.getMasterVolume()-0.1f, 0));
                } else if (action == Action.RIGHT) {
                    gameSettings.setMasterVolume(Math.min(gameSettings.getMasterVolume()+0.1f, 1));
                }
                soundManager.setMasterVolume(gameSettings.getMasterVolume());
                soundManager.updateVolumes();
            } else if (type == WidgetType.MUSIC_VOLUME) {
                if (action == Action.LEFT) {
                    gameSettings.setMusicVolume(Math.max(gameSettings.getMusicVolume()-0.1f, 0));
                } else if (action == Action.RIGHT) {
                    gameSettings.setMusicVolume(Math.min(gameSettings.getMusicVolume()+0.1f, 1));
                }
                soundManager.setMusicVolume(gameSettings.getMusicVolume());
                soundManager.updateVolumes();
            } else {
                if (action == Action.LEFT) {
                    gameSettings.setSfxVolume(Math.max(gameSettings.getSfxVolume()-0.1f, 0));
                } else if (action == Action.RIGHT) {
                    gameSettings.setSfxVolume(Math.min(gameSettings.getSfxVolume()+0.1f, 1));
                }
                soundManager.setSFXVolume(gameSettings.getSfxVolume());
                soundManager.updateVolumes();
            }
        }
    }

    public void updateWidgetState() {
        for (Widget widget : getModel().getWidgets()) {
            widget.setWidgetState(WidgetState.UNSELECTED);
        }

        getModel().getCurrentWidget().setWidgetState(WidgetState.SELECTED);
    }
}
