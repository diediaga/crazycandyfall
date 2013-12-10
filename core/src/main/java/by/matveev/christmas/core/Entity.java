package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pool;

public class Entity extends Image  implements Pool.Poolable {

    protected final Bounds bounds = new Bounds();

    public Entity() {

    }

    public Entity(Drawable drawable) {
        super(drawable);
    }

    public Entity(TextureRegion region) {
        super(region);
    }

    public Bounds bounds() {
        return bounds;
    }

    @Override
    public void reset() {
        bounds.setAngle(0);
        bounds.setWidth(0);
        bounds.setHeight(0);
        bounds.setX(0);
        bounds.setY(0);
    }
}
