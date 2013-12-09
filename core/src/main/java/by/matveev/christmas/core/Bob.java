/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */

package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import java.lang.annotation.ElementType;

public class Bob extends Image {

    public static final float RADIUS = 0.4f; // bob is a ball with 0.8m diameter
    public final Body body; // bob's box2d body

    public Bob(World world) {


        this.setDrawable(new TextureRegionDrawable(
                Assets.instance().<TextureAtlas>get("gfx/icons.atlas").findRegion("icon7")));

        // generate bob's box2d body
        PolygonShape circle = new PolygonShape();
        circle.setAsBox(0.3f, 0.31f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = 2f;
        bodyDef.position.y = 2f;
        bodyDef.linearDamping = 0.1f;
        bodyDef.angularDamping = 0.5f;
        bodyDef.fixedRotation = false;
        bodyDef.angle = MathUtils.PI / 32;

        this.body = world.createBody(bodyDef);

        Fixture fix = body.createFixture(circle, 50);
        fix.setDensity(1);
        fix.setFriction(1f);
        fix.setRestitution(0.8f);
//        fix.setFilterData(filter);

        circle.dispose();

        // generate bob's actor
        this.setPosition(body.getPosition().x-RADIUS, body.getPosition().y-RADIUS); // set the actor position at the box2d body position
        this.setSize(RADIUS*2, RADIUS*2); // scale actor to body's size
        this.setScaling(Scaling.stretch); // stretch the texture
        this.setAlign(Align.center);
    }

    @Override
    public void act(float delta) {
        // here we override Actor's act() method to make the actor follow the box2d body
        super.act(delta);
        setOrigin(RADIUS, RADIUS);
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(body.getPosition().x-RADIUS, body.getPosition().y-RADIUS);
    }
}