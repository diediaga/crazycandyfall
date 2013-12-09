package by.matveev.christmas.core;

import com.badlogic.gdx.utils.Timer;

/**
 * @author Alexey Matveev
 */
public final class Countdown {

    private final Callback<Integer> callback;
    private Timer timer;

    private int time;

    public Countdown(int time, Callback<Integer> callback) {
        this.time = time;
        this.callback = callback;
    }

    public void start() {
        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                time -= 1 * 1000;
                callback.result(time);
            }
        }, 1, 1);
    }

    public void pause() {
        timer.stop();
    }

    public void resume() {
        timer.start();
    }

}