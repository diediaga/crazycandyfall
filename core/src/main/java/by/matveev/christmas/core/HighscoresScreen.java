package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

public class HighscoresScreen extends AbstractScreen {

    private TextureAtlas resources;
    private Label.LabelStyle style;

    public HighscoresScreen() {
    }

    @Override
    public void show() {
        super.show();

        resources = Assets.instance().get("gfx/game.atlas");

        style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        style.font = Assets.instance().get("fonts/font.fnt");


        final Table table = new Table();
        final List<Score> scores = Highscores.instance().getScores();
        if (scores.isEmpty()) {
            final Label actor = new Label("NO SCORES YET", style);
            actor.setAlignment(Align.center, Align.center);
            table.add(actor).center();
        } else {
            final Label actor1 = new Label("HIGHSCORES:", style);
            actor1.setAlignment(Align.center, Align.center);
            table.add(actor1).center().colspan(2).padBottom(20f);
            table.row();

            int count = 0;
            for (int ix = scores.size() - 1; ix >= 0; ix--) {
                count++;
                if (count > 5) break;
                Score s = scores.get(ix);
                final Label actor = new Label(String.valueOf(count) + ".", style);
                actor.setAlignment(Align.center, Align.center);
                table.add(actor).center().padRight(10);

                final Label actor2 = new Label(String.valueOf(s.getScore()), style);
                actor2.setAlignment(Align.center, Align.center);
                table.add(actor2).right();
                table.row();
            }

        }

        table.pack();
        table.setPosition((Cfg.width() - table.getPrefWidth()) * 0.5f,
                (Cfg.height() - table.getPrefHeight()) * 0.5f);
        stage.addActor(table);



        createCloseButton();
    }


    private void createCloseButton() {
        final Image close = new Image(resources.findRegion("exitButton"));
        close.setOrigin(close.getPrefWidth() * 0.5f, close.getPrefHeight() * 0.5f);
        close.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Screens.set(new MenuScreen());
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                close.clearActions();
                close.addAction(scaleTo(1.05f, 1.05f, 0.2f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                close.clearActions();
                close.addAction(scaleTo(1f, 1f, 0.2f));
            }
        });
        close.setPosition((Cfg.width() - close.getPrefWidth()) * 0.5f, Cfg.height() * 0.1f);
        stage.addActor(close);
    }
}
