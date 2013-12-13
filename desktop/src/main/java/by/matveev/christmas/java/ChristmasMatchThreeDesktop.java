package by.matveev.christmas.java;

import by.matveev.christmas.core.GameServices;
import by.matveev.christmas.core.Platform;
import by.matveev.christmas.core.PlatformActions;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import by.matveev.christmas.core.ChristmasMatchThree;

public class ChristmasMatchThreeDesktop {
	public static void main (String[] args) {
        Platform.init(PlatformActions.NOT_SUPPORTED, GameServices.NOT_SUPPORTED);


		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
//        config.width = 480 / 2;
//        config.height = 600 / 2;
        config.width = (int) (480 * 0.4f);
        config.height = (int) (800 * 0.4f);
		new LwjglApplication(new ChristmasMatchThree(), config);
	}
}
