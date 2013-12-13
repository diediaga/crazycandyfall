package by.matveev.christmas.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

public class ControlTypeScreen extends AbstractScreen {

    private TextureAtlas resources;
    private Label.LabelStyle style;

    public ControlTypeScreen() {
    }

    @Override
    public void show() {
        super.show();

        if (Gdx.app.getType() != Application.ApplicationType.Android) {
            Screens.set(new PlayScreen(PlayScreen.ControlType.Touch));
            return;
        }

        resources = Assets.instance().get("gfx/game.atlas");

        style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        style.font = Assets.instance().get("fonts/font.fnt");

        final Label message = new Label("select\ncontrol type:", style);
        message.setAlignment(Align.center, Align.center);
        message.setPosition((Cfg.width() - message.getPrefWidth()) * 0.5f, Cfg.height() * 0.7f);
        stage.addActor(message);

        final Table table = new Table();

        final Image fingersButton = new Image(resources.findRegion("controlTouchButton"));
        fingersButton.setOrigin(fingersButton.getPrefWidth() * 0.5f, fingersButton.getPrefHeight() * 0.5f);
        fingersButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Screens.set(new PlayScreen(PlayScreen.ControlType.Touch));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                fingersButton.clearActions();
                fingersButton.addAction(scaleTo(1.05f, 1.05f, 0.2f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                fingersButton.clearActions();
                fingersButton.addAction(scaleTo(1f, 1f, 0.2f));
            }
        });
        table.add(fingersButton).center().padRight(100f).padBottom(40f);

        final Image tiltButton = new Image(resources.findRegion("controlTiltButton"));
        tiltButton.setOrigin(tiltButton.getPrefWidth() * 0.5f, tiltButton.getPrefHeight() * 0.5f);
        tiltButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Screens.set(new PlayScreen(PlayScreen.ControlType.Accelerometer));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                tiltButton.clearActions();
                tiltButton.addAction(scaleTo(1.05f, 1.05f, 0.2f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                tiltButton.clearActions();
                tiltButton.addAction(scaleTo(1f, 1f, 0.2f));
            }
        });
        table.add(tiltButton).center().padBottom(40f);


        table.row();

        final Label fingersLabel = new Label("touch", style);
        fingersLabel.setAlignment(Align.center, Align.center);
        table.add(fingersLabel).center().padRight(100f);

        final Label tiltLabel = new Label("tilt", style);
        tiltLabel.setAlignment(Align.center, Align.center);
        table.add(tiltLabel).center();

        table.pack();

        table.setPosition((Cfg.width() - table.getPrefWidth()) * 0.5f,
                (Cfg.height() - table.getPrefHeight()) * 0.5f);
        stage.addActor(table);

    }
}
