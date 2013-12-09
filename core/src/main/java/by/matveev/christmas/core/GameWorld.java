/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */

package by.matveev.christmas.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameWorld {

    // here we set up the actual viewport size of the game in meters.
    public static float UNIT_WIDTH = 6.4f; // 6.4 meters width
    public static float UNIT_HEIGHT = 3.75f; // 3.75 meters height

    public static final Vector2 GRAVITY = new Vector2(0, -9.8f);

    public final Stage worldStage; // stage containing game actors (not GUI, but actual game elements)
    public World box2dWorld; // box2d world
    public Bob bob; // our playing character

    public GameWorld() {

        box2dWorld = new World(GRAVITY, true);
        worldStage = new Stage();
        worldStage.setViewport(UNIT_WIDTH, UNIT_HEIGHT, true);

        createWorld();
    }

    private void createWorld() {

        // create box2d bodies and the respective actors here.
        bob = new Bob(this.box2dWorld);
        worldStage.addActor(bob);

        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(10f, 0.1f);
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.x = 0.5f;
        groundBodyDef.position.y = 0.5f;
        Body groundBody = box2dWorld.createBody(groundBodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        groundBody.createFixture(fixtureDef);
        groundShape.dispose();
        // add more game elements here
    }

    public void update(float delta) {

        // perform game logic here
        box2dWorld.step(delta, 3, 3); // update box2d world
        worldStage.act(delta); // update game stage
    }
}