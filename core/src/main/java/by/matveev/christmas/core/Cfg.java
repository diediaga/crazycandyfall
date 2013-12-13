/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */
package by.matveev.christmas.core;

/**
 * @author Alexey Matveev
 */
public class Cfg {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    public static final int INITIAL_TIME = 60 * 1000;
    public static final int BONUS_TIME = 7 * 1000;
    public static final int WARNING_TIME_THRESHOLD = 10 * 1000;

    public static final int FROZEN_TIMER_DELAY = 5 * 1000;

    public static final float SANTA_VELOCITY = 30f;

    public static final float MIN_CANDY_VELOCITY = 25;
    public static final float MAX_CANDY_VELOCITY = 45;

    public static final float MIN_BONUS_VELOCITY = 35;
    public static final float MAX_BONUS_VELOCITY = 60;


    private Cfg() {}

    public static int width() {
        return WIDTH;
    }

    public static int height() {
        return HEIGHT;
    }
}
