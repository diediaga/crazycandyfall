/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */
package by.matveev.christmas.core;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author Alexey Matveev
 */
public final class ActorUtils {

    private ActorUtils() {
    }

    public static void removeSafely(Actor target) {
        if (target != null) target.remove();
    }

    public static boolean hasActions(final Actor target) {
        return target == null ? false : target.getActions().size > 0;
    }
}
