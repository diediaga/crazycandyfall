/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */

package by.matveev.christmas.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexey Matveev
 */
public class Highscores {

    private static Highscores instance;


    private FileHandle file;




    public Highscores() {
        if (Gdx.files.isExternalStorageAvailable()) {
            file = Gdx.files.external(".christmascandyfall/1");
        }
    }

    public static Highscores instance() {
        if (instance == null) {
            instance = new Highscores();
        }
        return instance;
    }

    public void submit(int score) {
        if (file != null) {
            StringBuilder sb = new StringBuilder();
            if (file.exists()) {
                sb.append(file.readString());
            }
            sb.append(score + "" +
                    "\t" + System.currentTimeMillis() + "\n");
            file.writeString(sb.toString(), true);
        }

    }

    public List<Score> getScores() {
         if (file == null || !file.exists()) return Collections.emptyList();

        final List<Score> scores = new ArrayList<Score>();

        final String string = file.readString();
        if (string == null || string.isEmpty()) {
            return Collections.emptyList();
        }


        final String[] tokens = String.valueOf(string).split("\n");
        for (String token : tokens) {
            final String[] chunks = token.split("\t");
            scores.add(new Score(Integer.parseInt(chunks[0]), Long.parseLong(chunks[1])));
        }
        return scores;
    }

}
