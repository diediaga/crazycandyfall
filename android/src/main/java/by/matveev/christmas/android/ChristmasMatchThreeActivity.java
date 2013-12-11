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

public class ChristmasMatchThreeActivity extends AndroidApplication
        implements GameHelper.GameHelperListener, GameServices {

    private static final String TAG = "App";

    private GameHelper gameHelper;

    public ChristmasMatchThreeActivity() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.gameHelper = new GameHelper(this);
        this.gameHelper.enableDebugLog(true, "Google Play Game Services");


        Platform.init(new AndroidPlatformActions(this), this);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useGL20 = true;
        config.useWakelock = true;
        config.useAccelerometer = true;
        initialize(new ChristmasMatchThree(), config);

        gameHelper.setup(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    public void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
        gameHelper.onActivityResult(request, response, data);
    }


    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {

    }

    @Override
    public void login() {
        try {
            runOnUiThread(new Runnable(){
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (final Exception ex) {
            Log.e(TAG, "Exception during login", ex);
        }
    }

    @Override
    public void logout() {
        // not implemented
    }

    @Override
    public void submitScore(int score) {
        gameHelper.getGamesClient().submitScore(getResources().getString(R.string.leaderboard_key), score);
    }

    @Override
    public void showLeaderboard() {
        startActivityForResult(gameHelper.getGamesClient().getLeaderboardIntent(
                getResources().getString(R.string.leaderboard_key)), 100);
    }

    @Override
    public void unlockAchievement(String id) {
        gameHelper.getGamesClient().unlockAchievement(id);
    }

    @Override
    public void showAchievements() {
        startActivityForResult(gameHelper.getGamesClient().getAchievementsIntent(), 101);
    }

    @Override
    public boolean isSigned() {
        return gameHelper.isSignedIn();
    }
}
