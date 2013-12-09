package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * @author Alexey Matveev
 */
public class Cell extends WidgetGroup {

    private final Image background;
    private final Image icon;

    private boolean selected;

    private final int type;
    private int row;
    private int col;

    public Cell(int type, int row, int col, TextureRegion maskRegion, TextureRegion iconRegion) {
        this.type = type;
        this.row = row;
        this.col = col;
        setSize(Cfg.CELL_WIDTH, Cfg.CELL_HEIGHT);

        background = new Image(maskRegion);
        background.setColor(Cfg.COLOR_CELL_BACKGROUND);
        background.setFillParent(true);
        addActor(background);

        icon = new Image(iconRegion);
        icon.setPosition((getPrefWidth() - icon.getPrefWidth()) * 0.5f,
                (getPrefHeight() - icon.getPrefHeight()) * 0.5f);
        icon.setOrigin(icon.getPrefWidth() * 0.5f, icon.getPrefHeight() * 0.5f);
        addActor(icon);
    }

    public int getType() {
        return type;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            icon.addAction(repeat(-1, sequence(scaleTo(0.85f, 0.85f, 0.4f), scaleTo(1.05f, 1.05f, 0.4f))));
        } else {
            icon.setScale(1f);
            icon.clearActions();
        }
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public float getPrefWidth() {
        return Cfg.CELL_WIDTH;
    }

    @Override
    public float getPrefHeight() {
        return Cfg.CELL_HEIGHT;
    }
}
