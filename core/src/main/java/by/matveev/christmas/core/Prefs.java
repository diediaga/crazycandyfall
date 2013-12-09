/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */

package by.matveev.christmas.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.List;


public class Prefs {

    // Keys
    public static final String KEY_PREFS = "by.matveev.irregularverbs.prefs";
    public static final String KEY_THEME = "theme";
    public static final String KEY_SOUND_ENABLED = "sound";
    public static final String KEY_PLAYER_LEVEL = "level";
    public static final String KEY_LEVEL_ID = "levelId";

    private static Preferences prefs;

    private static List<PrefsListener> listeners = new ArrayList<PrefsListener>();

    private Prefs() {
    }

    public static void addListener(PrefsListener listener) {
        listeners.add(listener);
    }

    public static void purge() {
        listeners.clear();
    }

    public static void init() {
        prefs = Gdx.app.getPreferences(KEY_PREFS);
    }

    public static void setString(String key, String value) {
        prefs.putString(key, value);
        prefs.flush();
        notifyChanged(key);
    }

    public static void setLong(String key, long value) {
        prefs.putLong(key, value);
        prefs.flush();
        notifyChanged(key);
    }

    public static void toggle(String key) {
        setBoolean(key, !getBoolean(key));
    }

    public static void setBoolean(String key, boolean value) {
        prefs.putBoolean(key, value);
        prefs.flush();
        notifyChanged(key);
    }

    public static boolean getBoolean(String key) {
        return prefs.getBoolean(key);
    }

    public static boolean getBoolean(String key, boolean fallback) {
        return prefs.getBoolean(key, fallback);
    }

    public static String getString(String key) {
        return prefs.getString(key);
    }

    public static String getString(String key, String fallback) {
        return prefs == null ? fallback : prefs.getString(key, fallback);
    }

    public static Long getLong(String key) {
        return prefs.getLong(key);
    }

    public static int getInt(String key) {
        return prefs.getInteger(key);
    }

    private static void notifyChanged(String key) {
        for (PrefsListener listener : listeners) {
            listener.changed(key);
        }
    }

    public static void removeListener(PrefsListener listener) {
        listeners.add(listener);
    }

    public interface PrefsListener {
        public void changed(String key);
    }

}
