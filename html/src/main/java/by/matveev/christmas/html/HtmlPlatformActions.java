package by.matveev.christmas.html;

import by.matveev.christmas.core.PlatformActions;
import com.google.gwt.user.client.Window;

public class HtmlPlatformActions implements PlatformActions {

    @Override
    public void openUrl(String url) {
        Window.open(url, "_blank", "");
    }
}
