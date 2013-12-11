package by.matveev.christmas.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import by.matveev.christmas.core.PlatformActions;

public class AndroidPlatformActions implements PlatformActions{

    private final Context context;

    public AndroidPlatformActions(Context context) {
        this.context = context;
    }

    @Override
    public void openUrl(String url) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
}
