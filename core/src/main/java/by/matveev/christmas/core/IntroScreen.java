package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class IntroScreen extends AbstractScreen {

    public IntroScreen() {
    }

    @Override
    public void show() {
        super.show();

        final TextureAtlas atlas = Assets.instance().get("gfx/game.atlas");

        final Group group = new Group();
        group.setSize(Cfg.width(), Cfg.height());
        group.setY(Cfg.height() * 0.05f);
        stage.addActor(group);


        final Image penguinImage = new Image(atlas.findRegion("penguin"));
        penguinImage.setPosition((Cfg.width() - penguinImage.getPrefWidth()) * 0.5f, Cfg.height() * 0.3f);
        penguinImage.setOrigin(penguinImage.getPrefWidth() * 0.5f, penguinImage.getPrefHeight() * 0.5f);
        penguinImage.setScale(0f);
        penguinImage.addAction(scaleTo(1f, 1f, 0.3f, Interpolation.swingOut));
        group.addActor(penguinImage);

        group.addAction(sequence(run(new Runnable() {
            @Override
            public void run() {

            }
        }), delay(1f), run(new Runnable() {
            @Override
            public void run() {
                final Vector3[] positions = {
                        new Vector3(Cfg.width() * 0.17f, Cfg.height() * 0.3f, 0.8f),
                        new Vector3(Cfg.width() * 0.6f, Cfg.height() * 0.3f, 0.7f),
                };
                for (int ix = 0; ix < positions.length; ix++) {
                    final Vector3 position = positions[ix];
                    final Image image = new Image(atlas.findRegion("penguin"));
                    image.setPosition(position.x, position.y);
                    image.setOrigin(image.getPrefWidth() * 0.5f, image.getPrefHeight() * 0.5f);
                    image.setScale(0f);
                    image.addAction(sequence(delay(ix * 0.4f), scaleTo(position.z, position.z, 0.3f, Interpolation.swingOut), delay(1f), new Action() {
                        @Override
                        public boolean act(float delta) {
                            final Image speechImage = new Image(atlas.findRegion("speech"));
                            speechImage.setPosition((Cfg.width() - speechImage.getPrefWidth()) * 0.57f, Cfg.height() * 0.43f);
                            speechImage.getColor().a = 0f;
                            speechImage.addAction(alpha(1f, 0.5f));
                            group.addActor(speechImage);
                            return true;
                        }
                    }));
                    group.addActor(image);

                }
            }
        }), delay(2f), run(new Runnable() {
            @Override
            public void run() {
                final Image logo = new Image(atlas.findRegion("logo"));
                logo.setPosition((Cfg.width() - logo.getPrefWidth()) * 0.5f, Cfg.height() * 0.7f);
                logo.getColor().a = 0f;
                logo.addAction(alpha(1f, 0.5f));
                stage.addActor(logo);
            }
        }), delay(0.5f), run(new Runnable() {
            @Override
            public void run() {
                final Image img = new Image(atlas.findRegion("playButton"));
                img.setPosition((Cfg.width() - img.getPrefWidth()) * 0.5f, Cfg.height() * 0.13f);
                img.setOrigin(img.getPrefWidth() * 0.5f, img.getPrefHeight() * 0.5f);
                img.setScale(0f);
                img.addAction(scaleTo(1f, 1f, 0.3f, Interpolation.swingOut));
                img.addListener(new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Screens.set(new PlayScreen());
                    }

                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        img.clearActions();
                        img.addAction(scaleTo(1.1f, 1.1f, 0.2f));
                    }

                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        img.clearActions();
                        img.addAction(scaleTo(1f, 1f, 0.2f));
                    }
                });
                stage.addActor(img);

                stage.addAction(sequence(delay(0.3f), new Action() {
                    @Override
                    public boolean act(float delta) {
                        final Image scoreImg = new Image(atlas.findRegion("scoresButton"));
                        scoreImg.setPosition(Cfg.width() * 0.75f, Cfg.height() * 0.05f);

                        scoreImg.setOrigin(scoreImg.getPrefWidth() * 0.5f, scoreImg.getPrefHeight() * 0.5f);
                        scoreImg.setScale(0f);
                        scoreImg.addAction(scaleTo(1f, 1f, 0.3f, Interpolation.swingOut));
                        scoreImg.addListener(new ClickListener() {

                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Screens.set(new PlayScreen());
                            }

                            @Override
                            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                scoreImg.clearActions();
                                scoreImg.addAction(scaleTo(1.1f, 1.1f, 0.2f));
                            }

                            @Override
                            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                                scoreImg.clearActions();
                                scoreImg.addAction(scaleTo(1f, 1f, 0.2f));
                            }
                        });
                        stage.addActor(scoreImg);


                        final Image infoImage = new Image(atlas.findRegion("infoButton"));
                        infoImage.setPosition(Cfg.width() * 0.05f, Cfg.height() * 0.05f);

                        infoImage.setOrigin(infoImage.getPrefWidth() * 0.5f, infoImage.getPrefHeight() * 0.5f);
                        infoImage.setScale(0f);
                        infoImage.addAction(scaleTo(1f, 1f, 0.3f, Interpolation.swingOut));
                        infoImage.addListener(new ClickListener() {

                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Screens.set(new PlayScreen());
                            }

                            @Override
                            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                infoImage.clearActions();
                                infoImage.addAction(scaleTo(1.1f, 1.1f, 0.2f));
                            }

                            @Override
                            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                                infoImage.clearActions();
                                infoImage.addAction(scaleTo(1f, 1f, 0.2f));
                            }
                        });
                        stage.addActor(infoImage);
                        return true;
                    }
                }));


            }
        })));




    }

}
