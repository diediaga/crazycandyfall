/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */

package by.matveev.christmas.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;

/**
 * @author Alexey Matveev
 */
public abstract class AbstractScreen implements Screen, Disposable {

    protected final Stage stage;

    public AbstractScreen() {
        this.stage = new Stage();
        this.stage.addListener(new HardwareButtonsInterceptor());
    }

    public void load() {
    }

    public void unload() {
    }

    protected void onHardKeyPressed(int keyCode) {
    }

    @Override
    public void render(final float delta) {
        Gdx.graphics.getGL20().glClearColor(231f / 255f, 76f / 255f, 60f / 255, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(final int width, final int height) {
        final Vector2 size = Scaling.fit.apply(Cfg.width(), Cfg.height(), width, height);
        final int viewportX = (int) (width - size.x) / 2;
        final int viewportY = (int) (height - size.y) / 2;
        final int viewportWidth = (int) size.x;
        final int viewportHeight = (int) size.y;
        Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
        stage.setViewport(Cfg.width(), Cfg.height(), true, viewportX, viewportY, viewportWidth, viewportHeight);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.clear();
    }

    private class HardwareButtonsInterceptor extends ClickListener {

        private final int[] buttons = {Input.Keys.BACK, Input.Keys.MENU, Input.Keys.ESCAPE};

        private HardwareButtonsInterceptor() {
        }

        @Override
        public boolean keyDown(InputEvent event, int keycode) {
            for (int ix = 0; ix < buttons.length; ix++) {
                if (buttons[ix] == keycode) {
                    onHardKeyPressed(keycode);
                    return true;
                }
            }
            return super.keyDown(event, keycode);
        }
    }
}
