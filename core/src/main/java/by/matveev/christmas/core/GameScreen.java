/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */

package by.matveev.christmas.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class GameScreen extends AbstractScreen {

    // this is actually my tablet resolution in landscape mode. I'm using it for making the GUI pixel-exact.
    public static float SCREEN_WIDTH = 1024;
    public static float SCREEN_HEIGHT = 600;

    private GameWorld world; // contains the game world's bodies and actors.
    private GameRenderer renderer; // our custom game renderer.
    private Stage stage1; // stage that holds the GUI. Pixel-exact size.
    private OrthographicCamera guiCam; // camera for the GUI. It's the stage default camera.

    @Override
    public final void show() {
        super.show();

        this.stage1 = new Stage(); // create the GUI stage
        this.stage1.setViewport(SCREEN_WIDTH, SCREEN_HEIGHT, false); // set the GUI stage viewport to the pixel size

        world = new GameWorld();
        renderer = new GameRenderer(world);

        // add GUI actors to stage, labels, meters, buttons etc.
        final Label.LabelStyle s = new Label.LabelStyle();
        s.font = new BitmapFont();
        s.fontColor = Color.WHITE;

        Label labelStatus = new Label("TOUCH TO START", s);
        labelStatus.setPosition(Cfg.width() * 0.4f, Cfg.height() * 0.5f);
        labelStatus.setWidth(1000);
        labelStatus.setAlignment(Align.center);
        labelStatus.setFontScale(2);
        stage1.addActor(labelStatus);
        // add other GUI elements here
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        guiCam = (OrthographicCamera) stage1.getCamera();
        guiCam.position.set(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 0);

        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);
        guiCam.update();

        world.update(delta); // update the box2d world
        stage1.act(delta); // update GUI

        renderer.render(); // draw the box2d world
        stage1.draw(); // draw the GUI
    }
}