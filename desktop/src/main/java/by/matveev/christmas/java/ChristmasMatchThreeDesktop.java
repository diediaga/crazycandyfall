package by.matveev.christmas.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import by.matveev.christmas.core.ChristmasMatchThree;

public class ChristmasMatchThreeDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
        config.width = 480;
        config.height = 600;
		new LwjglApplication(new ChristmasMatchThree(), config);
	}
}
