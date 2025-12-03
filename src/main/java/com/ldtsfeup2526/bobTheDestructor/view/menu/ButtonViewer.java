package com.ldtsfeup2526.bobTheDestructor.view.menu;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.Position;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Button;
import com.ldtsfeup2526.bobTheDestructor.model.menu.ButtonState;
import com.ldtsfeup2526.bobTheDestructor.model.menu.ButtonType;
import com.ldtsfeup2526.bobTheDestructor.view.ElementViewer;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ButtonViewer implements ElementViewer<Button> {
    private final Map<ButtonType, Map<ButtonState, Sprite>> spriteMap = new HashMap<>();
    private final Map<ButtonType, Sprite> iconMap = new HashMap<>();

    public ButtonViewer(SpriteLoader spriteLoader) throws IOException {
        Map<ButtonState, Sprite> tempMap;
        for (ButtonType buttonType : ButtonType.values()) {
            tempMap = new HashMap<>();
            tempMap.put(ButtonState.UNSELECTED, spriteLoader.get("sprites/ui/buttons/" + buttonType.name().toLowerCase() + "/button1.png"));
            tempMap.put(ButtonState.SELECTED, spriteLoader.get("sprites/ui/buttons/" + buttonType.name().toLowerCase() + "/button2.png"));
            tempMap.put(ButtonState.CLICKED, spriteLoader.get("sprites/ui/buttons/" + buttonType.name().toLowerCase() + "/button3.png"));
            spriteMap.put(buttonType, tempMap);

            for (Sprite s : tempMap.values()) {
                s.center();
            }

            iconMap.put(buttonType, spriteLoader.get("sprites/ui/buttons/" + buttonType.name().toLowerCase() + "/icon.png"));
        }
        for (Sprite s : iconMap.values()) {
            s.center();
        }
    }

    public void draw(Button button, GUI gui) {
        Map<ButtonState, Sprite> buttonTypeSprites = spriteMap.get(button.getButtonType());
        Sprite sprite = buttonTypeSprites.get(button.getButtonState());

        sprite.draw(button.getPosition(), gui);

        iconMap.get(button.getButtonType()).draw(new Position(
                sprite.getSize().getX()/2 + button.getPosition().getX() + 5,
                button.getPosition().getY()),
                gui
        );
    }

}
