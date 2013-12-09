
/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */

package by.matveev.christmas.core;

/**
 * @author Alexey Matveev
 */
public enum Sharing {

    Twitter {
        @Override
        public void share(String text, String url) {
            open("http://twitter.com/intent/tweet?source=sharethiscom&text=" + text + "&url=" + url);
        }
    },
    Facebook {
        @Override
        public void share(String text, String url) {
            open("https://www.facebook.com/sharer.php?u=" + url + "&t=" + text);
        }
    },
    Linkedin {
        @Override
        public void share(String text, String url) {
            open("http://www.linkedin.com/shareArticle?mini=true&url=" + url + "&title=" + text);
        }
    },
    Vk {
        @Override
        public void share(String text, String url) {
            open("http://vk.com/share.php?url=" + url + "&title=" + text /*"&description=[DESC]*/ + "&noparse=true");
        }
    },
    GooglePlus {
        @Override
        public void share(String text, String url) {
            open(" https://plus.google.com/share?url=" + url);
        }
    };

    private static void open(String url) {
        Platform.actions().openUrl(url);
    }

    public abstract void share(String text, String url);
}
