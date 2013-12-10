package by.matveev.christmas.core;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Pool;

public class Message extends Label implements Pool.Poolable{

    public Message(CharSequence text, LabelStyle style) {
        super(text, style);
    }

    @Override
    public void reset() {
        setText("");
        clearActions();
        clearListeners();
        getColor().a = 1f;
    }
}
