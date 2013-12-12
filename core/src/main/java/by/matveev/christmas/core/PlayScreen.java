/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */
package by.matveev.christmas.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.Timer;

import static by.matveev.christmas.core.Cfg.SANTA_VELOCITY;
import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * @author Alexey Matveev
 */
public class PlayScreen extends AbstractScreen {

    private enum State {
        Idle,
        Playing,
    }

    private State state = State.Idle;

    private Timer countdownTimer;
    private Timer spawnTimer;

    private int gameTime;
    private int bonusTime;

    private HeadUpDisplay display;

    private SantaClaus santaClaus;
    private Group candiesGroup;

    private int score;


    private TextureAtlas resources;

    private Pool<Candy> candyPool;
    private Pool<Message> labelPool;

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
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                label.remove();


                state = State.Playing;

                createHeadUpDisplays();
                createCandiesGroup();

                resources = Assets.instance().get("gfx/game.atlas");
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


                gameTime = Cfg.INITIAL_TIME;
                countdownTimer = new Timer();
                countdownTimer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        gameTime -= 1000;
                        display.setTime(gameTime);


                        bonusTime += 1000;
                        if (bonusTime >= Cfg.BONUS_TIME) {
                            if (MathUtils.randomBoolean(MathUtils.random(0.9f, 1f))) {
                                createBonus();
                            }
                            if (MathUtils.randomBoolean(MathUtils.random(0.95f, 1f))) {
                                createAntiBonus();
                            }
                            bonusTime = 0;
                        }

                        if (gameTime <= 0) {
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
                }, 0, 0.5f);


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
        final int type = random(1, 8);
        candy.setRegion(resources.findRegion("candy" + type));
        candy.setType(type < 7 ? Candy.Type.PlusScore : type == 7 ? Candy.Type.PlusTime : Candy.Type.PlusDoubleScore);
        candy.setRotation(random(360f));
        candy.setY(Cfg.height() + candy.getHeight());
        candy.setX(random(Cfg.width() * 0.07f, Cfg.width() * 0.93f));
        candy.setOrigin(candy.getWidth() * 0.5f, candy.getHeight() * 0.5f);
        candy.addAction(repeat(-1, rotateBy(10f, 0.1f)));
        candy.addAction(repeat(-1, moveBy(0, -MathUtils.random(Cfg.MIN_CANDY_VELOCITY, Cfg.MAX_CANDY_VELOCITY), 0.1f)));
        candiesGroup.addActor(candy);
    }

    private void createBonus() {
        final Candy candy = candyPool.obtain();
        final Candy.Type type = MathUtils.randomBoolean() ? Candy.Type.Freeze : Candy.Type.Multiply;
        candy.setRegion(resources.findRegion(type == Candy.Type.Freeze ? "bonus_freeze" : "bonus_multiply"));
        candy.setType(type);
        candy.setRotation(random(360f));
        candy.setY(Cfg.height() + candy.getHeight());
        candy.setX(random(Cfg.width() * 0.07f, Cfg.width() * 0.93f));
        candy.setOrigin(candy.getWidth() * 0.5f, candy.getHeight() * 0.5f);
        candy.addAction(repeat(-1, rotateBy(10f, 0.1f)));
        candy.addAction(repeat(-1, moveBy(0, -MathUtils.random(Cfg.MIN_BONUS_VELOCITY, Cfg.MAX_BONUS_VELOCITY), 0.1f)));
        candiesGroup.addActor(candy);
    }

    private void createAntiBonus() {
        final Candy candy = candyPool.obtain();
        final Candy.Type type = MathUtils.randomBoolean() ? Candy.Type.MinusScore : Candy.Type.MinusTime;
        candy.setRegion(resources.findRegion(type == Candy.Type.MinusScore ? "antibonus_score" : "antibonus_time"));
        candy.setType(type);
        candy.setRotation(random(360f));
        candy.setY(Cfg.height() + candy.getHeight());
        candy.setX(random(Cfg.width() * 0.07f, Cfg.width() * 0.93f));
        candy.setOrigin(candy.getWidth() * 0.5f, candy.getHeight() * 0.5f);
        candy.addAction(repeat(-1, rotateBy(10f, 0.1f)));
        candy.addAction(repeat(-1, moveBy(0, -MathUtils.random(Cfg.MIN_CANDY_VELOCITY, Cfg.MAX_CANDY_VELOCITY), 0.1f)));
        candiesGroup.addActor(candy);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (state == State.Idle) return;

        processInput(delta);

        final SnapshotArray<Actor> children = candiesGroup.getChildren();
        final Actor[] actors = children.begin();
        for (int i = 0, n = children.size; i < n; i++) {
            final Candy candy = (Candy) actors[i];
            if (candy.bounds().overlaps(santaClaus.bounds())) {
                affect(candy);

                candy.remove();
                candyPool.free(candy);
            }
            if (candy.getY() + candy.getPrefHeight() < 0) {
                if (candy.getParent() != null) { // check is already removed
                    candy.remove();
                    candyPool.free(candy);
                }
            }
        }
        children.end();
    }

    private void affect(final Candy candy) {
        final float cX = candy.getX();
        final float cY = candy.getY();

        switch (candy.getType()) {
            case PlusScore:
                score += 1;
                display.setScore(score);
                showPopup("+1", cX, cY);
                break;
            case PlusTime: {
                gameTime += 1 * 1000;
                display.setBonusTime(gameTime);
                showPopup("+00:01", cX, cY);
                break;
            }
            case PlusDoubleScore: {
                score += 2;
                display.setScore(score);
                showPopup("+2", cX, cY);
                break;
            }
            case Freeze:
                showMessage("FREEZE TIME");
                countdownTimer.stop();
                stage.addAction(delay(10f, run(new Runnable() {
                    @Override
                    public void run() {
                        countdownTimer.start();
                    }
                })));
                break;
            case Multiply:
                showMessage("DOUBLE SCORE");
                score *= 2;
                display.setScore(score);
                break;
            case MinusScore:
                score -= 20;
                display.setScore(score);
                showPopup("-20", cX, cY);
                Gdx.input.vibrate(1 * 1000);
                shake();
                break;
            case MinusTime:
                gameTime -= 10 * 1000;
                display.setBonusTime(gameTime);
                showPopup("-00:10", cX, cY);
                Gdx.input.vibrate(1 * 1000);
                shake();
                break;

        }
    }

    @Override
    public void pause() {
        super.pause();

        doPause();
    }

    private void doPause() {
        Screens.push(new PauseDialog());
    }

    private void doResume() {

    }

    @Override
    public void resume() {
        super.resume();

        doResume();
    }

    private void showPopup(String msg, float x, float y) {
        final Message label = labelPool.obtain();
        label.setPosition(x, y);
        label.setText(msg);
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

    private void showMessage(String message) {
        final Message label = labelPool.obtain();
        label.setText(message);
        label.setPosition((Cfg.width() - label.getPrefWidth()) * 0.5f,
                (Cfg.height() - label.getPrefHeight()) * 0.5f);
        label.setOrigin(label.getPrefWidth() * 0.5f, label.getPrefHeight() * 0.5f);
        label.setScale(1.6f);
        label.getColor().a = 0f;
        label.addAction(Actions.sequence(Actions.alpha(1, 0.5f, Interpolation.bounceOut),
                Actions.alpha(0, 0.5f, Interpolation.bounceIn), new Action() {
            @Override
            public boolean act(float delta) {
                label.remove();
                labelPool.free(label);
                return true;
            }
        }));
        stage.addActor(label);
    }

    private void processInput(float delta) {
        switch (Gdx.app.getType()) {
            case Android:
                santaClaus.setX(santaClaus.getX() + -(Gdx.input.getAccelerometerX() + SANTA_VELOCITY * delta));
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

    private void shake() {
        final float offset = 10f;
        stage.addAction(sequence(repeat(4, sequence(
                moveBy(offset, -offset, 0.08f, Interpolation.swingOut),
                moveBy(-offset, offset, 0.08f, Interpolation.swingIn))),
                run(new Runnable() {
                    @Override
                    public void run() {
                        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                    }
        })));
    }

    @Override
    public void hide() {
        super.hide();
        if (spawnTimer != null) {
        spawnTimer.stop();
        spawnTimer.clear();
        }

        if (countdownTimer != null) {
            countdownTimer.stop();
            countdownTimer.clear();
        }
    }
}
