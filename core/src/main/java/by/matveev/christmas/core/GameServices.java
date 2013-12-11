package by.matveev.christmas.core;

/**
 * @author Alexey Matveev
 */
public interface GameServices {

    public void login();
    public void logout();

    public void submitScore(int score);
    public void showLeaderboard();

    public void unlockAchievement(String id);
    public void showAchievements();

    public boolean isSigned();

    public static final GameServices NOT_SUPPORTED = new GameServices() {

        @Override
        public void login() {
        }

        @Override
        public void logout() {
        }

        @Override
        public void submitScore(int score) {
        }

        @Override
        public void showLeaderboard() {
        }

        @Override
        public void unlockAchievement(String id) {
        }

        @Override
        public void showAchievements() {
        }

        @Override
        public boolean isSigned() {
            return false;
        }
    };
}
