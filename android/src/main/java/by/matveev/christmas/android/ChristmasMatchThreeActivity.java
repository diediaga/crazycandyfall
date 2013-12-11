package by.matveev.christmas.android;

import android.os.Bundle;
import by.matveev.christmas.core.ChristmasMatchThree;
import by.matveev.christmas.core.Platform;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class ChristmasMatchThreeActivity extends AndroidApplication {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Platform.init(new AndroidPlatformActions(this));

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useGL20 = true;
        initialize(new ChristmasMatchThree(), config);
    }
}
