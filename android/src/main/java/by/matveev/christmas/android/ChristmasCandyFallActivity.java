package by.matveev.christmas.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import by.matveev.christmas.core.ChristmasMatchThree;
import by.matveev.christmas.core.GameServices;
import by.matveev.christmas.core.Platform;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class ChristmasCandyFallActivity extends AndroidApplication  {

    private static final String TAG = "App";

    public ChristmasCandyFallActivity() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Platform.init(new AndroidPlatformActions(this), GameServices.NOT_SUPPORTED);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useGL20 = true;
        config.useWakelock = true;
        config.useAccelerometer = true;
        initialize(new ChristmasMatchThree(), config);
    }
}
