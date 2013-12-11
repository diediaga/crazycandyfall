/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */

package by.matveev.christmas.core;

/**
 * @author Alexey Matveev
 */
public class Range {
    private final int start;
    private final int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int start() {
        return start;
    }

    public int end() {
        return end;
    }

    public boolean inside(int value) {
        return value >= start && value <= end;
    }

    public int length() {
        return start + end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range range = (Range) o;

        if (end != range.end) return false;
        if (start != range.start) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = start;
        result = 31 * result + end;
        return result;
    }
}
