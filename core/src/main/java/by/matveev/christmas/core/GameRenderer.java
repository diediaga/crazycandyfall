/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */

package by.matveev.christmas.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class GameRenderer
{
    GameWorld world;
    OrthographicCamera camera;
    Box2DDebugRenderer renderer;

    public GameRenderer(GameWorld world)
    {
        this.world = world;
        this.renderer = new Box2DDebugRenderer();

        // we obtain a reference to the game stage camera. The camera is scaled to box2d meter units
        this.camera = (OrthographicCamera) world.worldStage.getCamera();

        // center the camera on bob (optional)
        camera.position.x = world.bob.body.getPosition().x;
        camera.position.y = world.bob.body.getPosition().y;
    }

    public void render()
    {
//        // have the camera follow bob
//        camera.position.x = world.bob.body.getPosition().x;
//        camera.position.y = world.bob.body.getPosition().y;

        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // box2d debug renderering (optional)
        camera.update();
        renderer.render(world.box2dWorld, camera.combined);

        // game stage rendering
        world.worldStage.draw();
    }
}