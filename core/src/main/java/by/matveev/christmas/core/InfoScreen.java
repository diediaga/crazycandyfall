package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * @author Alexey Matveev
 */
public class InfoScreen extends AbstractScreen {

    public InfoScreen() {
    }

    @Override
    public void show() {
        super.show();

        final TextureAtlas resources = Assets.instance().get("gfx/game.atlas");

        final Label.LabelStyle style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        style.font = Assets.instance().get("fonts/font.fnt");

        final Table table = new Table();

        final Label message = new Label("collect candies.\navoid bombs.", style);
        message.setAlignment(Align.center, Align.center);
        table.add(message);

        table.row();


        table.pack();
        table.setPosition((Cfg.width() - table.getPrefWidth()) * 0.5f,
                (Cfg.height() - table.getPrefHeight()) * 0.5f);
        stage.addActor(table);
    }
}
