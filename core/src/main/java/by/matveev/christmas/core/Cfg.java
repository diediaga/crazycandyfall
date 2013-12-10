/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */
package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.Color;

/**
 * @author Alexey Matveev
 */
public class Cfg {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    // Timing
    public static final int INITIAL_TIME = 60 * 1000;
    public static final int WARNING_TIME_THRESHOLD = 10 * 1000;

    // Colors
    public static final Color COLOR_BACKGROUND = new Color(0xc0392bff);
    public static final Color COLOR_CELL_BACKGROUND = new Color(0xe74c3cff);


    public static final float GRAVITY = -0.98f;


    // Size
    public static final int CELL_WIDTH = 60;
    public static final int CELL_HEIGHT = 60;

    public static final int GRID_WIDTH = 6;
    public static final int GRID_HEIGHT = 7;
    public static final int SPACING = 15;

    private Cfg() {}

    public static int width() {
        return WIDTH;
    }

    public static int height() {
        return HEIGHT;
    }
}
