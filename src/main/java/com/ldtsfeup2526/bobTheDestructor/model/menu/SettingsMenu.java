package com.ldtsfeup2526.bobTheDestructor.model.menu;

import com.ldtsfeup2526.bobTheDestructor.Game;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SettingsMenu extends Menu {
    @Override
    protected List<Widget> createWidgets() {
        Widget masterVolume = new Widget(WidgetType.MASTER_VOLUME, WidgetState.SELECTED, new Position(Game.resolution.width()/2, (Game.resolution.height()/2 - 20)));
        Widget musicVolume = new Widget(WidgetType.MUSIC_VOLUME, WidgetState.UNSELECTED, new Position(Game.resolution.width()/2, (Game.resolution.height()/2)));
        Widget sfxVolume = new Widget(WidgetType.SFX_VOLUME, WidgetState.UNSELECTED, new Position(Game.resolution.width()/2, (Game.resolution.height()/2 + 20)));

        return Arrays.asList(masterVolume, musicVolume, sfxVolume);
    }
}
