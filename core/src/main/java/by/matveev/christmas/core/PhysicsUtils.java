/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */

package by.matveev.christmas.core;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Alexey Matveev
 */
public final class PhysicsUtils {

    private PhysicsUtils() {
    }

    public static final float PIXEL_TO_METER_RATIO = 100f;

    public static float meter(float value) {
        return value / PIXEL_TO_METER_RATIO;
    }

    public static float pixel(float value) {
        return value * PIXEL_TO_METER_RATIO;
    }


}
