/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */

package by.matveev.christmas.core;

/**
 * @author Alexey Matveev
 */
public class Score {

    private final int score;
    private final long date;

    public Score(int score, long date) {
        this.score = score;
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public long getDate() {
        return date;
    }
}
