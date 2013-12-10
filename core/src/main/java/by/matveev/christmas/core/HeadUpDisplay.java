/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */
package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import static by.matveev.christmas.core.ActorUtils.hasActions;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * @author Alexey Matveev
 */
public class HeadUpDisplay extends WidgetGroup {

    private final Label labelTime;
    private final Label labelScore;

    public HeadUpDisplay() {
        final BitmapFont font = Assets.instance().get("fonts/font.fnt");

        final Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.WHITE;

        labelTime = new Label(CommonUtils.format(Cfg.INITIAL_TIME), style);
        labelScore = new Label("0", style);

        addActor(labelTime);
        addActor(labelScore);

        updateLabelsPosition();
    }

    private void updateLabelsPosition() {
        labelScore.setPosition(Cfg.width() * 0.05f, Cfg.height() * 0.9f);
        labelTime.setPosition(Cfg.width() * 0.72f, Cfg.height() * 0.9f);
    }

    public void setTime(int time) {
        if (time < Cfg.WARNING_TIME_THRESHOLD) {
            if (!hasActions(labelTime)) {
                labelTime.addAction(repeat(-1, sequence(alpha(0f, 0.5f), alpha(1f, 0.5f))));
            }
        } else {
            if (hasActions(labelTime)) labelTime.clearActions();
        }
        labelTime.setText(CommonUtils.format(time));
        updateLabelsPosition();
    }

    public void setScore(int score) {
        labelScore.setText(String.valueOf(score));
        updateLabelsPosition();
    }
}