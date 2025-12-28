package com.ldtsfeup2526.bobTheDestructor.view.menu;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.GameSettings;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Widget;
import com.ldtsfeup2526.bobTheDestructor.model.menu.WidgetState;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.view.ElementViewer;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteInstance;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;
import com.ldtsfeup2526.bobTheDestructor.view.text.StringParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SliderViewer implements ElementViewer<Widget> {
    private final Map<WidgetState, Sprite> spriteMap = new HashMap<>();
    private final StringParser stringParser;

    public SliderViewer(SpriteLoader spriteLoader) throws IOException {
        spriteMap.put(WidgetState.UNSELECTED, spriteLoader.get("sprites/ui/sliders/arrow_left_normal.png"));
        spriteMap.put(WidgetState.SELECTED, spriteLoader.get("sprites/ui/sliders/arrow_left_selected.png"));

        this.stringParser = new StringParser(spriteLoader, "sprites/ui/letters/", 5);
    }

    @Override
    public void draw(Widget model, GUI gui, double deltaTime) {
        float value;

        switch (model.getWidgetType()) {
            case MASTER_VOLUME:
                value = GameSettings.getInstance().getMasterVolume();
                break;
            case MUSIC_VOLUME:
                value = GameSettings.getInstance().getMusicVolume();
                break;
            case SFX_VOLUME:
                value = GameSettings.getInstance().getSfxVolume();
                break;
            default:
                value = -1;
        }

        value = Math.round(value*100);

        Sprite arrowSprite = spriteMap.get(model.getWidgetState());

        List<SpriteInstance> text = stringParser.get(model.getWidgetType().toString().replace("_", " "));
        int halfWidth = text.size() * 5/2;

        List<SpriteInstance> valueText = stringParser.get(Objects.toString((int) value));
        int halfValueTextWidth = valueText.size() * 5/2;

        Position startTextPos = new Position(model.getPosition().getX() - halfWidth, model.getPosition().getY());
        Position leftArrowPos = new Position(model.getPosition().getX() - halfWidth - 7, model.getPosition().getY() + 2);
        Position rightArrowPos = new Position(model.getPosition().getX() + halfWidth + 2, model.getPosition().getY() + 2);

        Position startValueTextPos = new Position(model.getPosition().getX() - halfValueTextWidth, model.getPosition().getY()+7);

        for (SpriteInstance spriteInstance : text) {
            spriteInstance.sprite().setOffset(spriteInstance.offset());
            spriteInstance.sprite().draw(startTextPos, gui);
        }

        for (SpriteInstance spriteInstance : valueText) {
            spriteInstance.sprite().setOffset(spriteInstance.offset());
            spriteInstance.sprite().draw(startValueTextPos, gui);
        }
        arrowSprite.draw(leftArrowPos, gui);
        arrowSprite.drawFlipX(rightArrowPos, gui);
    }
}
