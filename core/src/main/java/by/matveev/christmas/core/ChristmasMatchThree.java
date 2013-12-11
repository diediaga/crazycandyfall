/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */
package by.matveev.christmas.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public class ChristmasMatchThree extends AbstractGame {

    public ChristmasMatchThree() {
    }

    @Override
    public void create() {
        Gdx.input.setCatchBackKey(true);

        Gdx.app.setLogLevel(Application.LOG_NONE);

        Prefs.init();
        Prefs.addListener(new Prefs.PrefsListener() {
            @Override
            public void changed(String key) {
                Sounds.setEnabled(Prefs.getBoolean(Prefs.KEY_SOUND_ENABLED));
            }
        });

        Screens.set(new LoadingScreen(new Runnable() {
            @Override
            public void run() {
//                Sounds.init();
                Screens.push(new MenuScreen());
            }
        }));
    }

    @Override
    public void dispose() {
        Screens.dispose();
        Assets.instance().dispose();
    }
}
