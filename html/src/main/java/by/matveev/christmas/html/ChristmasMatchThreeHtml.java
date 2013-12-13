package by.matveev.christmas.html;

import by.matveev.christmas.core.ChristmasMatchThree;
import by.matveev.christmas.core.GameServices;
import by.matveev.christmas.core.Platform;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;

public class ChristmasMatchThreeHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
        Platform.init(new HtmlPlatformActions(), GameServices.NOT_SUPPORTED);
		return new ChristmasMatchThree();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
        return new GwtApplicationConfiguration((int)(480 * 0.7f), (int) (800 * 0.7f));
	}


    public Preloader.PreloaderCallback getPreloaderCallback () {
        return new Preloader.PreloaderCallback() {

            @Override
            public void error (String file) {
                System.out.println("error: " + file);
            }

            @Override
            public void update (Preloader.PreloaderState state) {
            }

        };
    }

}
