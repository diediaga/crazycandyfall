package by.matveev.christmas.core;

import com.badlogic.gdx.Gdx;
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

import java.util.Arrays;
import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * @author Alexey Matveev
 */
public class GameOverScreen extends AbstractScreen {

    public GameOverScreen(int score) {

        final Label.LabelStyle s = new Label.LabelStyle();
        s.fontColor = Color.WHITE;
        s.font = Assets.instance().get("fonts/font.fnt");
        final Label label = new Label("YOU COLLECTED\n" + score + "\ncandies", s);
        label.setPosition((Cfg.width() - label.getPrefWidth()) * 0.5f, Cfg.height() * 0.7f);
        label.setAlignment(Align.center, Align.center);
        stage.addActor(label);


        final TextureAtlas atlas = Assets.instance().get("gfx/game.atlas");

        final Image logoImg = new Image(atlas.findRegion("gameover_logo"));
        logoImg.setPosition((Cfg.width() - logoImg.getPrefWidth()) * 0.5f, Cfg.height() * 0.38f);
        stage.addActor(logoImg);

        addSocialButtons();

        stage.addAction(sequence(delay(0.5f, new Action() {
            @Override
            public boolean act(float delta) {
                float buttonsWidth = 0;
                float w = 0;

                final Pair [] buttons = {
                        new Pair<Image, ClickListener>(new Image(atlas.findRegion("replayButton")), new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Screens.set(new PlayScreen());
                            }
                        }),
                        new Pair<Image, ClickListener>(new Image(atlas.findRegion("exitButton")),new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Gdx.app.exit();
                            }
                        })
                };


                for (Pair<Image, ClickListener> btn : buttons) {
                    buttonsWidth += btn.getFirst().getPrefWidth();
                    w = btn.getFirst().getPrefWidth();
                }
                float padding = w * 0.35f;
                buttonsWidth += padding * (buttons.length - 1);

                float startX = (Cfg.width() - buttonsWidth) * 0.5f;
                float startY = Cfg.height() * 0.12f;
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
    private void addSocialButtons() {
        final List<Pair<Sharing, String>> social = Arrays.asList(
                new Pair<Sharing, String>(Sharing.Facebook, "facebook"),
                new Pair<Sharing, String>(Sharing.Twitter, "twitter"),
                new Pair<Sharing, String>(Sharing.Linkedin, "linkedin"),
                new Pair<Sharing, String>(Sharing.GooglePlus, "gplus"),
                new Pair<Sharing, String>(Sharing.Vk, "vk")

        );
        final TextureAtlas atlas = Assets.instance().get("gfx/game.atlas");


        float buttonsWidth = 48 * social.size();
        float padding = 20;
        buttonsWidth += padding * (social.size() - 1);

        float startX = (Cfg.width() - buttonsWidth) * 0.5f;
        float startY = Cfg.height() * 0.27f;
        for (final Pair<Sharing, String> socialButton : social) {
            final Image image = new Image(atlas.findRegion(socialButton.getSecond()));
            image.setPosition(startX, startY);
            image.setOrigin(image.getPrefWidth() * 0.5f, image.getPrefHeight() * 0.5f);
            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    socialButton.getFirst().share("", "");
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    image.clearActions();
                    image.addAction(scaleTo(1.05f, 1.05f, 0.2f));
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    image.clearActions();
                    image.addAction(scaleTo(1f, 1f, 0.2f));
                }
            });
            startX += 48 + padding;
            stage.addActor(image);
        }

    }

}
