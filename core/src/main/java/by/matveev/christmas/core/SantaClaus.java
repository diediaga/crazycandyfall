/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */
package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * @author Alexey Matveev
 */
public class SantaClaus extends Entity {

    public SantaClaus() {
        super(Assets.instance().<TextureAtlas>get("gfx/game.atlas").findRegion("santa"));
//        BoundsDebug.addBounds(bounds);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        bounds.setWidth(getWidth() * 0.7f);
        bounds.setHeight(getHeight() * 0.4f);
        bounds.setX(getX() + (getWidth() - bounds.getWidth()) * 0.5f);
        bounds.setY(getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
