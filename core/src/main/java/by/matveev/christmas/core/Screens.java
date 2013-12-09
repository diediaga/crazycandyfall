/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */
package by.matveev.christmas.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Logger;

import java.util.Stack;

/**
 * @author Alexey Matveev
 */
public final class Screens {

    private static final Stack<AbstractScreen> stack = new Stack<AbstractScreen>();

    private static final Logger LOG = new Logger("Screens", com.badlogic.gdx.utils.Logger.DEBUG);

    private Screens() {
    }

    public static void set(AbstractScreen screen) {
        while (hasScreens()) {
            final AbstractScreen old = stack.pop();
            old.hide();
            old.unload();
            old.dispose();
        }
        push(screen);
    }


    public static void replace(AbstractScreen screen) {
        if (hasScreens()) {
            final AbstractScreen oldScreen = stack.pop();
            oldScreen.hide();
            oldScreen.unload();
            oldScreen.dispose();

            stack.add(0, screen);
            show(screen);
        }
    }

    public static void push(AbstractScreen screen) {
        if (hasScreens()) {
            final AbstractScreen old = stack.peek();
            old.hide();
        }
        stack.push(screen);

        show(screen);
    }

    public static void pop() {
        if (hasScreens()) {
            final AbstractScreen oldScreen = stack.pop();
            oldScreen.hide();
            oldScreen.unload();
            oldScreen.dispose();

            if (hasScreens()) {
                show(stack.peek());
            }
        }
    }

    public static void dispose() {
        while (hasScreens()) {
            stack.pop().dispose();
        }
    }

    public static Screen current() {
        if (hasScreens()) return stack.peek();
        throw new IllegalStateException("screens stack is empty");
    }

    private static boolean hasScreens() {
        return !stack.isEmpty();
    }

    private static void show(AbstractScreen screen) {
        screen.load();
        screen.show();
        screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
