/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */
package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

/**
 * @author Alexey Matveev
 */
public class SantaClaus extends PhysicsActor {

    private final Body body;

    private final float widthInMeters;
    private final float heightInMeters;

    public SantaClaus(World world) {
        final TextureAtlas atlas = Assets.instance().<TextureAtlas>get("gfx/game.atlas");
        final TextureAtlas.AtlasRegion region = atlas.findRegion("santa");

        setDrawable(new TextureRegionDrawable(region));

        widthInMeters = PhysicsUtils.meter(region.getRegionWidth());
        heightInMeters = PhysicsUtils.meter(region.getRegionHeight());

        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(widthInMeters, heightInMeters);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.x = 2f;
        bodyDef.position.y = 2f;
        bodyDef.linearDamping = 0.1f;
        bodyDef.angularDamping = 0.5f;
        bodyDef.fixedRotation = true;


        body = world.createBody(bodyDef);

        final Fixture fix = body.createFixture(shape, 10);
        fix.setDensity(1);
        fix.setFriction(1f);

        shape.dispose();

        setPosition(body.getPosition().x - widthInMeters, PhysicsUtils.meter(20));
        setSize(widthInMeters * 2, heightInMeters * 2);
        setAlign(Align.center);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setOrigin(widthInMeters * 0.5f, heightInMeters * 0.5f);
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(body.getPosition().x - widthInMeters, PhysicsUtils.meter(20));
    }

    public void move(float direction) {
        final Vector2 pos = body.getPosition();
        body.setTransform(pos.x + direction, pos.y, pos.angle());
//        final Vector2 velocity = body.getLinearVelocity();
//        velocity.x += direction;
//        body.setLinearVelocity(velocity);
    }
}
