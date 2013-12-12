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

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

/**
 * @author Alexey Matveev
 */
public class InfoScreen extends AbstractScreen {

    private TextureAtlas resources;
    private Label.LabelStyle style;

    private final List<Runnable> creators = new ArrayList<Runnable>();
    private int currentIndex = -1;

    public InfoScreen() {
        creators.add(new FirstPage());
        creators.add(new SecondPage());
        creators.add(new ThirdPage());
        creators.add(new FourthPage());
    }

    @Override
    public void show() {
        super.show();

        resources = Assets.instance().get("gfx/game.atlas");

        style = new Label.LabelStyle();
        style.fontColor = Color.WHITE;
        style.font = Assets.instance().get("fonts/font.fnt");

        showNext();
    }

    private final class FirstPage implements Runnable {

        @Override
        public void run() {
            stage.clear();
            final Table table = new Table();

            final Label message = new Label("collect candies.\navoid bombs.", style);
            message.setAlignment(Align.center, Align.center);
            table.add(message).center();
            table.pack();
            table.setPosition((Cfg.width() - table.getPrefWidth()) * 0.5f,
                    (Cfg.height() - table.getPrefHeight()) * 0.5f);
            stage.addActor(table);

            createForwardButton();
            createCloseButton();
        }
    }

    private final class SecondPage implements Runnable {

        @Override
        public void run() {
            stage.clear();
            final Table table = new Table();

            final Label message = new Label("candies:", style);
            message.setAlignment(Align.center, Align.center);
            table.add(message).center().colspan(2).padBottom(30f);
            table.row();
            table.add(new Image(resources.findRegion("candy1"))).center().padRight(30).padBottom(10f);
            table.add(new Label("plus score", style)).left().padBottom(10f);;
            table.row();
            table.add(new Image(resources.findRegion("candy7"))).center().padRight(30).padBottom(10f);
            table.add(new Label("plus time", style)).left().padBottom(10f);
            table.row();
            table.add(new Image(resources.findRegion("candy8"))).center().padRight(30);
            table.add(new Label("plus x2 score", style)).left();

            table.pack();
            table.setPosition((Cfg.width() - table.getPrefWidth()) * 0.5f,
                    (Cfg.height() - table.getPrefHeight()) * 0.5f);
            stage.addActor(table);

            createForwardButton();
            createBackwardButton();
            createCloseButton();
        }
    }

    private final class ThirdPage implements Runnable {

        @Override
        public void run() {
            stage.clear();
            final Table table = new Table();

            final Label message = new Label("bonuses:", style);
            message.setAlignment(Align.center, Align.center);
            table.add(message).center().colspan(2).padBottom(30f);
            table.row();
            table.add(new Image(resources.findRegion("bonus_freeze"))).center().padRight(30).padBottom(10f);
            table.add(new Label("freeze time", style)).left().padBottom(10f);;
            table.row();
            table.add(new Image(resources.findRegion("bonus_multiply"))).center().padRight(30);
            table.add(new Label("double score", style)).left();

            table.pack();
            table.setPosition((Cfg.width() - table.getPrefWidth()) * 0.5f,
                    (Cfg.height() - table.getPrefHeight()) * 0.5f);
            stage.addActor(table);

            createForwardButton();
            createBackwardButton();
            createCloseButton();
        }
    }

    private final class FourthPage implements Runnable {

        @Override
        public void run() {
            stage.clear();
            final Table table = new Table();

            final Label message = new Label("anti-bonuses:", style);
            message.setAlignment(Align.center, Align.center);
            table.add(message).center().colspan(2).padBottom(30f);
            table.row();
            table.add(new Image(resources.findRegion("antibonus_score"))).center().padRight(30).padBottom(10f);
            table.add(new Label("minus score", style)).left().padBottom(10f);;
            table.row();
            table.add(new Image(resources.findRegion("antibonus_time"))).left().padRight(30);
            table.add(new Label("minus time", style)).center();

            table.pack();
            table.setPosition((Cfg.width() - table.getPrefWidth()) * 0.5f,
                    (Cfg.height() - table.getPrefHeight()) * 0.5f);
            stage.addActor(table);

            createBackwardButton();
            createCloseButton();
        }
    }

    private void createForwardButton() {
        final Image forward = new Image(resources.findRegion("forwardButton"));
        forward.setOrigin(forward.getPrefWidth() * 0.5f, forward.getPrefHeight() * 0.5f);
        forward.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showNext();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                forward.clearActions();
                forward.addAction(scaleTo(1.05f, 1.05f, 0.2f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                forward.clearActions();
                forward.addAction(scaleTo(1f, 1f, 0.2f));
            }
        });
        forward.setPosition(Cfg.width() * 0.8f, Cfg.height() * 0.8f);
        stage.addActor(forward);

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

    private void createBackwardButton() {
        final Image backward = new Image(resources.findRegion("backwardButton"));
        backward.setOrigin(backward.getPrefWidth() * 0.5f, backward.getPrefHeight() * 0.5f);
        backward.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPrev();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                backward.clearActions();
                backward.addAction(scaleTo(1.05f, 1.05f, 0.2f));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                backward.clearActions();
                backward.addAction(scaleTo(1f, 1f, 0.2f));
            }
        });
        backward.setPosition(Cfg.width() * 0.05f, Cfg.height() * 0.8f);
        stage.addActor(backward);
    }

    private void showNext() {

        if (currentIndex + 1 < creators.size()) {
            currentIndex += 1;
            creators.get(currentIndex).run();
        }
    }

    private void showPrev() {
        if (currentIndex - 1 >= 0) {
            currentIndex -= 1;
            creators.get(currentIndex).run();
        }
    }

}
