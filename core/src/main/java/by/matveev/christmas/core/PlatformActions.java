/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */

package by.matveev.christmas.core;

public interface PlatformActions {

    public static final PlatformActions NOT_SUPPORTED = new PlatformActions() {
        @Override
        public void openUrl(String url) {

        }
    };

    public void openUrl(String url);

}
