package by.matveev.christmas.core;


import com.badlogic.gdx.math.Rectangle;

public class Bounds extends Rectangle {

    public float angle;

    public Bounds() {
    }

    public Bounds(float x, float y, float width, float height, float angle) {
        super(x, y, width, height);
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
