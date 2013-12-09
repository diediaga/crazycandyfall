/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */
package by.matveev.christmas.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * @author Alexey Matveev
 */
public class PlayScreen extends AbstractScreen {

    private static final float WORLD_WIDTH_IN_METERS = 5f;
    private static final float WORLD_HEIGHT_IN_METERS = 8f;

    private static final Vector2 GRAVITY = new Vector2(0, -9.8f);

    private HeadUpDisplay display;
    private Countdown timer;

    private final Stage worldStage;
    private final World world;
    private final Box2DDebugRenderer renderer;

    private SantaClaus santaClaus;

    public PlayScreen() {
        world = new World(GRAVITY, true);

        worldStage = new Stage();
        worldStage.setViewport(WORLD_WIDTH_IN_METERS, WORLD_HEIGHT_IN_METERS, true);
        renderer = new Box2DDebugRenderer(true, true, true, true, true, true);
    }

    @Override
    public void show() {
        super.show();

        createSantaClaus();

        createHeadUpDisplays();

        registerListeners();
    }

    private void registerListeners() {

    }

    private void createSantaClaus() {
        santaClaus = new SantaClaus(world);
        worldStage.addActor(santaClaus);
    }

    private void createHeadUpDisplays() {
        display = new HeadUpDisplay();
        stage.addActor(display);

        timer = new Countdown(Cfg.INITIAL_TIME, new Callback<Integer>() {
            @Override
            public void result(Integer time) {
                display.setTime(time);

                createCandy();
            }
        });
        timer.start();
    }

    private void createCandy() {
        worldStage.addActor(new Candy(world));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        input();

        world.step(delta, 3, 3);
        worldStage.act(delta);
        worldStage.draw();

        renderer.render(world, worldStage.getCamera().combined);
    }

    private void input() {
        final float direction;
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            direction = -(Gdx.input.getAccelerometerX() * 0.01f);
        } else {
            direction = Gdx.input.getDeltaX() * 0.01f;
        }
        santaClaus.move(direction);
    }
}
