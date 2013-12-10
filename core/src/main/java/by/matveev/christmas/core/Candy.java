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

    private int type;

    public Candy() {
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
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
        setType(0);
        clearActions();
        clearListeners();
    }
}
