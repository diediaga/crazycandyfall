package by.matveev.christmas.core;

/**
 * Provider for platform specific features.
 */
public final class Platform {

    private static PlatformActions actions;
    private static GameServices service;

    public static void init(PlatformActions actions, GameServices service) {
        Platform.actions = actions;
        Platform.service = service;
    }

    public static PlatformActions actions() {
        return actions;
    }

    public static GameServices services() {
        return service;
    }
}
