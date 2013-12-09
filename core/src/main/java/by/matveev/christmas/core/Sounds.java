package by.matveev.christmas.core;

import com.badlogic.gdx.audio.Music;

public class Sounds {

    public static Music background;

    private Sounds() {
    }

    public static void init() {
        background = Assets.instance().get("sounds/wallpaper.ogg");
        background.setVolume(1f);
        background.setLooping(true);
    }

    public static void setEnabled(boolean enabled) {
        if (enabled && !background.isPlaying()) {
            background.play();
        } else {
            background.stop();
        }
    }

    public static void pause() {
        background.pause();
    }
}
