package com.ldtsfeup2526.bobTheDestructor.view.menu;

import com.ldtsfeup2526.bobTheDestructor.gui.GUI;
import com.ldtsfeup2526.bobTheDestructor.model.spatials.Position;
import com.ldtsfeup2526.bobTheDestructor.model.menu.Widget;
import com.ldtsfeup2526.bobTheDestructor.model.menu.WidgetState;
import com.ldtsfeup2526.bobTheDestructor.model.menu.WidgetType;
import com.ldtsfeup2526.bobTheDestructor.view.ElementViewer;
import com.ldtsfeup2526.bobTheDestructor.view.Sprite;
import com.ldtsfeup2526.bobTheDestructor.view.SpriteLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ButtonViewer implements ElementViewer<Widget> {
    private final Map<WidgetType, Map<WidgetState, Sprite>> spriteMap = new HashMap<>();
    private final Map<WidgetType, Sprite> iconMap = new HashMap<>();
    private final Sprite pickaxeIcon;

    public ButtonViewer(SpriteLoader spriteLoader) throws IOException {
        Map<WidgetState, Sprite> tempMap;
        for (WidgetType widgetType : WidgetType.values()) {
            tempMap = new HashMap<>();
            tempMap.put(WidgetState.UNSELECTED, spriteLoader.get("sprites/ui/buttons/" + widgetType.name().toLowerCase() + "/button1.png"));
            tempMap.put(WidgetState.SELECTED, spriteLoader.get("sprites/ui/buttons/" + widgetType.name().toLowerCase() + "/button2.png"));
            tempMap.put(WidgetState.CLICKED, spriteLoader.get("sprites/ui/buttons/" + widgetType.name().toLowerCase() + "/button3.png"));
            spriteMap.put(widgetType, tempMap);

            for (Sprite s : tempMap.values()) {
                s.center();
            }

            iconMap.put(widgetType, spriteLoader.get("sprites/ui/buttons/" + widgetType.name().toLowerCase() + "/icon.png"));
        }
        for (Sprite s : iconMap.values()) {
            s.center();
        }

        pickaxeIcon = spriteLoader.get("sprites/ui/buttons/selected_icon.png");
        pickaxeIcon.center();
    }

    public void draw(Widget widget, GUI gui, double deltaTime) {
        Map<WidgetState, Sprite> buttonTypeSprites = spriteMap.get(widget.getButtonType());
        Sprite sprite = buttonTypeSprites.get(widget.getButtonState());

        sprite.draw(widget.getPosition(), gui);

        iconMap.get(widget.getButtonType()).draw(new Position(
                sprite.getSize().getX()/2 + widget.getPosition().getX() + 5,
                widget.getPosition().getY()),
                gui
        );

        if (widget.getButtonState() != WidgetState.UNSELECTED) {
            pickaxeIcon.draw(new Position(
                            widget.getPosition().getX() - sprite.getSize().getX() / 2 - 4,
                            widget.getPosition().getY()),
                    gui
            );
        }
    }

}
