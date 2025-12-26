package com.ldtsfeup2526.bobTheDestructor.model.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;


import java.util.Collections;
import java.util.List;

public class SettingsMenu extends Menu {
    @Override
    protected List<Button> createButtons() {
        Button volume = new Button(ButtonType.VOLUME, ButtonState.SELECTED, new Position(Game.resolution.width()/2, (Game.resolution.height()/2)));
        return Collections.singletonList(volume);
    }
}
