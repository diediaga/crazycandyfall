package by.matveev.christmas.core;

/**
 * Provider for platform specific features.
 */
public final class Platform {

    private static PlatformActions actions;

    public static void init(PlatformActions actions) {
        Platform.actions = actions;
    }

    public static PlatformActions actions() {
        return actions;
    }
}
