package by.matveev.christmas.core;

/**
 * @author Alexey Matveev
 */
public final class CommonUtils {

    private CommonUtils() {
    }

    private final static StringBuilder builder = new StringBuilder();

    public static String format(final int milliseconds) {
        int seconds =  (milliseconds / 1000) % 60 ;
        int minutes =  ((milliseconds / (1000*60)) % 60);

        builder.delete(0, builder.length());

        if (minutes < 10) {
            builder.append(0);
        }
        builder.append(minutes);
        builder.append(":");

        if (seconds < 10) {
            builder.append(0);
        }
        builder.append(seconds);


        return builder.toString();
    }
}
