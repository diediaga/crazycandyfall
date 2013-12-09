/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */

package by.matveev.christmas.core;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import static by.matveev.christmas.core.PhysicsUtils.meter;

/**
 * @author Alexey Matveev
 */
public class Candy extends PhysicsActor {

    private final Body body;

    private final float widthInMeters;
    private final float heightInMeters;

    public Candy(World world) {
        final TextureAtlas atlas = Assets.instance().get("gfx/game.atlas");
        final TextureAtlas.AtlasRegion region = atlas.findRegion("candy" + MathUtils.random(1, 6));

        setDrawable(new TextureRegionDrawable(region));

        widthInMeters = meter(region.getRegionWidth());
        heightInMeters = meter(region.getRegionHeight());

        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(widthInMeters, heightInMeters);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = meter(MathUtils.random(region.getRegionWidth() * 2f, Cfg.width() - region.getRegionWidth() * 2f));
        bodyDef.position.y = meter(Cfg.height());
        bodyDef.linearDamping = 0.1f;
        bodyDef.angularDamping = 0.5f;
        bodyDef.fixedRotation = false;
        bodyDef.angle = MathUtils.random(360) * MathUtils.degreesToRadians;

        body = world.createBody(bodyDef);

        final Fixture fix = body.createFixture(shape, 5);
        fix.setDensity(0.2f);
        fix.setFriction(1f);
        fix.setRestitution(0.2f);

        shape.dispose();

        setPosition(body.getPosition().x - widthInMeters, body.getPosition().y- heightInMeters);
        setSize(widthInMeters * 2, heightInMeters * 2);
        setAlign(Align.center);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setOrigin(widthInMeters * 0.5f, heightInMeters * 0.5f);
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(body.getPosition().x - widthInMeters, body.getPosition().y - heightInMeters);
    }

    public void move(float direction) {
        final Vector2 velocity = body.getLinearVelocity();
        velocity.x += direction;
        body.setLinearVelocity(velocity);
    }
}
