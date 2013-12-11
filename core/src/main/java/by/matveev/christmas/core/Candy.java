/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */

package by.matveev.christmas.core;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * @author Alexey Matveev
 */
public class Candy extends Entity{

    public enum Type {
        PlusScore,
        PlusDoubleScore,
        PlusTime,
        Multiply,
        Freeze,
        MinusScore,
        MinusTime;
    }

    private Type type;

    public Candy() {
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        bounds.setWidth(getWidth() * 0.8f);
        bounds.setHeight(getHeight() * 0.8f);
        bounds.setX(getX() + (getWidth() - bounds.getWidth()) * 0.5f);
        bounds.setY(getY() + (getHeight() - bounds.getHeight()) * 0.5f);
        bounds.setAngle(getRotation());
    }

    public void setRegion(TextureRegion region) {
        TextureRegionDrawable drawable = (TextureRegionDrawable) super.getDrawable();
        if (drawable == null) {
            drawable = new TextureRegionDrawable();
            setDrawable(drawable);
        }
        drawable.setRegion(region);
        pack();
    }


    @Override
    public void reset() {
        super.reset();
        setDrawable(null);
        setType(null);
        clearActions();
        clearListeners();
    }
}
