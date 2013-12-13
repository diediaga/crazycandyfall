package by.matveev.christmas.core;

import com.badlogic.gdx.audio.Music;

public class Sounds {

    public static Music background;
    private static boolean enabled;

    private Sounds() {
    }

    public static void init() {
        background = Assets.instance().get("sounds/music.ogg");
        background.setVolume(1f);
        background.setLooping(true);
    }

    public static void setSoundsEnabled(boolean enabled) {
        Sounds.enabled = enabled;
    }

    public static void play() {
        if (enabled && !background.isPlaying()) {
            background.play();
        }
    }

    public static void stop() {
        background.stop();
    }
}
