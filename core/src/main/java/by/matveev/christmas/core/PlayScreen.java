/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */
package by.matveev.christmas.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * @author Alexey Matveev
 */
public class PlayScreen extends AbstractScreen {

    private HeadUpDisplay display;

    private Timer countdownTimer;
    private Timer spawnTimer;

    private int currentTime;

    private boolean started;

    private SantaClaus santaClaus;
    private Group candiesGroup;

    private int score;

    private Pool<Candy> candyPool;
    private Pool<Message> labelPool;
    private TextureAtlas atlas;

    public PlayScreen() {
    }

    @Override
    public void show() {
        super.show();

        final Label.LabelStyle s = new Label.LabelStyle();
        s.fontColor = Color.WHITE;
        s.font = Assets.instance().get("fonts/font.fnt");
        final Label label = new Label("TAP TO START!", s);
        label.setPosition((Cfg.width() - label.getPrefWidth()) * 0.5f, (Cfg.height() - label.getPrefHeight()) * 0.5f);
        createSantaClaus();


        stage.addActor(label);
        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                label.remove();


                started = true;

                createHeadUpDisplays();
                createCandiesGroup();

                atlas = Assets.instance().get("gfx/game.atlas");
                candyPool = new Pool<Candy>() {
                    @Override
                    protected Candy newObject() {
                        return new Candy();
                    }
                };

                final Label.LabelStyle style = new Label.LabelStyle();
                style.fontColor = Color.WHITE;
                style.font = Assets.instance().get("fonts/font.fnt");

                labelPool = new Pool<Message>() {
                    @Override
                    protected Message newObject() {
                        return new Message("", style);
                    }
                };


                currentTime = Cfg.INITIAL_TIME;
                countdownTimer = new Timer();
                countdownTimer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        currentTime -= 1000;
                        display.setTime(currentTime);

                        if (currentTime <= 0) {
                            Screens.set(new GameOverScreen(score));
                        }
                    }
                }, 1, 1);

                spawnTimer = new Timer();
                spawnTimer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        createCandy();
                    }
                }, 0, 0.3f);


            }
        });


    }

    private void createCandiesGroup() {
        candiesGroup = new Group();
        candiesGroup.setSize(Cfg.width(), Cfg.height());
        stage.addActor(candiesGroup);
    }

    private void createSantaClaus() {
        santaClaus = new SantaClaus();
        santaClaus.setY(0);
        santaClaus.setX((Cfg.width() - santaClaus.getPrefWidth()) * 0.5f);
        stage.addActor(santaClaus);
    }

    private void createHeadUpDisplays() {
        display = new HeadUpDisplay();
        stage.addActor(display);
    }

    private void createCandy() {
        final Candy candy = candyPool.obtain();

        final int type =  random(1, 8);

        candy.setRegion(atlas.findRegion("candy" + type));
        candy.setType(type);
        candy.setRotation(random(360f));
        candy.setY(Cfg.height() + candy.getHeight());
        candy.setX(random(Cfg.width() * 0.2f, Cfg.width() * 0.8f));
        candy.setOrigin(candy.getWidth() * 0.5f, candy.getHeight() * 0.5f);
        candy.addAction(repeat(-1, rotateBy(10f, 0.1f)));
        candy.addAction(repeat(-1, moveBy(0, -MathUtils.random(25, 45), 0.1f)));
        candiesGroup.addActor(candy);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        BoundsDebug.render(stage.getCamera().combined, delta);

        if (started) {

        input();

        for (Actor child : candiesGroup.getChildren()) {
            final Candy candy = (Candy) child;
            if (candy.bounds().overlaps(santaClaus.bounds())) {

                if (candy.getType() < 7) {
                    // plain type
                    score++;
                    display.setScore(score);
                    final Message label = labelPool.obtain();
                    label.setPosition(candy.getX(), candy.getY());
                    label.setText("+1");
                    label.addAction(sequence(parallel(moveBy(0, 50, 0.5f), alpha(0, 0.5f)), new Action() {
                        @Override
                        public boolean act(float delta) {
                            label.remove();
                            labelPool.free(label);
                            return true;
                        }
                    }));
                    stage.addActor(label);
                } else if (candy.getType() == 7) {
                    currentTime += 1 * 1000;
                    display.setBonusTime(currentTime);
                    final Message label = labelPool.obtain();
                    label.setPosition(candy.getX(), candy.getY());
                    label.setText("+00:01");
                    label.addAction(sequence(parallel(moveBy(0, 50, 0.5f), alpha(0, 0.5f)), new Action() {
                        @Override
                        public boolean act(float delta) {
                            label.remove();
                            labelPool.free(label);
                            return true;
                        }
                    }));
                    stage.addActor(label);
                }  else if (candy.getType() == 8) {
                    score *= 2;
                    display.setScore(score);
                    final Message label = labelPool.obtain();
                    label.setPosition(candy.getX(), candy.getY());
                    label.setText("x2");
                    label.addAction(sequence(parallel(moveBy(0, 50, 0.5f), alpha(0, 0.5f)), new Action() {
                        @Override
                        public boolean act(float delta) {
                            label.remove();
                            labelPool.free(label);
                            return true;
                        }
                    }));
                    stage.addActor(label);
                }




                final Message label = labelPool.obtain();
                label.setPosition(candy.getX(), candy.getY());
                label.setText("+1");
                label.addAction(sequence(parallel(moveBy(0, 50, 0.5f), alpha(0, 0.5f)), new Action() {
                    @Override
                    public boolean act(float delta) {
                        label.remove();
                        labelPool.free(label);
                        return true;
                    }
                }));
                stage.addActor(label);

                candy.remove();
                candyPool.free(candy);
            }

            if (candy.getY() + candy.getPrefHeight() < 0) {
                candyPool.free(candy);
                candy.remove();
            }
        }
        }
    }

    private void input() {
        switch (Gdx.app.getType()) {
            case Android:
                santaClaus.setX(santaClaus.getX() + (-Gdx.input.getAccelerometerX()));
                break;
            default:
                santaClaus.setX(Gdx.input.getX() - santaClaus.getPrefWidth() * 0.5f);
        }


        if (santaClaus.getX() < 0) {
            santaClaus.setX(0);
        }

        if (santaClaus.getX() > Cfg.width() - santaClaus.getWidth()) {
            santaClaus.setX(Cfg.width() - santaClaus.getWidth());
        }
    }

    @Override
    public void hide() {
        super.hide();
        spawnTimer.stop();
        spawnTimer.clear();

        countdownTimer.stop();
        countdownTimer.clear();
    }
}
