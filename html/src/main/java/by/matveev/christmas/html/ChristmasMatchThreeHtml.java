package by.matveev.christmas.html;

import by.matveev.christmas.core.ChristmasMatchThree;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class ChristmasMatchThreeHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new ChristmasMatchThree();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
