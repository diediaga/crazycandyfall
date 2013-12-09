/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */
package by.matveev.christmas.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public abstract class AbstractGame implements ApplicationListener {

    public AbstractGame() {
    }

    @Override
    public void pause() {
        Screens.current().pause();

    }

    @Override
    public void resume() {
        Screens.current().resume();
    }

    @Override
    public void render() {
        Screens.current().render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(final int width, final int height) {
        Screens.current().resize(width, height);
    }
}
