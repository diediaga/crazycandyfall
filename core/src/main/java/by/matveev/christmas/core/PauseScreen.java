package by.matveev.christmas.core;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class PauseScreen extends AbstractScreen {

    private Image soundButton;

    public PauseScreen() {
    }

    @Override
    public void show() {
        super.show();

        final Label.LabelStyle s = new Label.LabelStyle();
        s.fontColor = Color.WHITE;
        s.font = Assets.instance().get("fonts/font.fnt");
        final Label label = new Label("CAME PAUSED", s);
        label.setPosition((Cfg.width() - label.getPrefWidth()) * 0.5f, Cfg.height() * 0.55f);
        label.setAlignment(Align.center, Align.center);
        stage.addActor(label);


        final TextureAtlas atlas = Assets.instance().get("gfx/game.atlas");

        stage.addAction(sequence(delay(0f, new Action() {
            @Override
            public boolean act(float delta) {
                float buttonsWidth = 0;
                float w = 0;

                final Array<Pair> buttons = new Array<Pair>();
                buttons.add(
                        new Pair<Image, ClickListener>(new Image(atlas.findRegion("continueButton")), new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Screens.pop();
                            }
                        }));
                buttons.add(new Pair<Image, ClickListener>(new Image(atlas.findRegion("replayButton")), new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Screens.set(new PlayScreen());
                    }
                }));

                buttons.add(new Pair<Image, ClickListener>(new Image(atlas.findRegion("menuButton")), new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Screens.set(new MenuScreen());
                    }
                }));


                final boolean soundsEnabled = Prefs.getBoolean(Prefs.KEY_SOUND_ENABLED, true);


                soundButton = new Image(atlas.findRegion(soundsEnabled ? "soundOnButton" : "soundOffButton"));
                buttons.add(new Pair<Image, ClickListener>(soundButton, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Prefs.setBoolean(Prefs.KEY_SOUND_ENABLED, !Prefs.getBoolean(Prefs.KEY_SOUND_ENABLED, true));

                        final TextureRegionDrawable drawable =  new TextureRegionDrawable(
                                atlas.findRegion(Prefs.getBoolean(Prefs.KEY_SOUND_ENABLED, true)
                                        ? "soundOnButton"
                                        : "soundOffButton")
                        );
                        soundButton.setDrawable(drawable);
                        soundButton.invalidateHierarchy();
                    }
                }));


                for (Pair<Image, ClickListener> btn : buttons) {
                    buttonsWidth += btn.getFirst().getPrefWidth();
                    w = btn.getFirst().getPrefWidth();
                }
                float padding = w * 0.35f;
                buttonsWidth += padding * (buttons.size - 1);

                float startX = (Cfg.width() - buttonsWidth) * 0.5f;
                float startY = Cfg.height() * 0.4f;
                for (final Pair<Image, ClickListener> btn : buttons) {
                    btn.getFirst().setPosition(startX, startY);
                    startX += w + padding;

                    btn.getFirst().setOrigin(btn.getFirst().getPrefWidth() * 0.5f, btn.getFirst().getPrefHeight() * 0.5f);
                    btn.getFirst().setScale(0f);
                    btn.getFirst().addAction(scaleTo(1f, 1f, 0.3f, Interpolation.swingOut));

                    btn.getFirst().addListener(new ClickListener() {

                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            btn.getSecond().clicked(event, x, y);
                        }

                        @Override
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            btn.getFirst().clearActions();
                            btn.getFirst().addAction(scaleTo(1.1f, 1.1f, 0.2f));
                        }

                        @Override
                        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                            btn.getFirst().clearActions();
                            btn.getFirst().addAction(scaleTo(1f, 1f, 0.2f));
                        }
                    });

                    stage.addActor(btn.getFirst());
                }
                return true;
            }
        })));
    }
}
