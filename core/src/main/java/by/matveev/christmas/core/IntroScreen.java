package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class IntroScreen extends AbstractScreen {

    public IntroScreen() {
    }

    @Override
    public void show() {
        super.show();

        final TextureAtlas atlas = Assets.instance().get("gfx/game.atlas");

        final Image penguinImage = new Image(atlas.findRegion("penguin"));
        penguinImage.setPosition((Cfg.width() - penguinImage.getPrefWidth()) * 0.5f, Cfg.height() * 0.3f);
        penguinImage.setOrigin(penguinImage.getPrefWidth() * 0.5f, penguinImage.getPrefHeight() * 0.5f);
        penguinImage.setScale(0f);
        penguinImage.addAction(scaleTo(1f, 1f, 0.3f, Interpolation.swingOut));
        stage.addActor(penguinImage);

        stage.addAction(sequence(delay(2f), run(new Runnable() {
            @Override
            public void run() {
                final Image speechImage = new Image(atlas.findRegion("speech"));
                speechImage.setPosition((Cfg.width() - speechImage.getPrefWidth()) * 0.4f, Cfg.height() * 0.5f);
                speechImage.getColor().a = 0f;
                speechImage.addAction(alpha(1f, 0.5f));
                stage.addActor(speechImage);
            }
        }), delay(1f), run(new Runnable() {
            @Override
            public void run() {
                final Vector3 [] positions = {
                        new Vector3(Cfg.width() * 0.17f, Cfg.height() * 0.3f, 0.8f),
                        new Vector3(Cfg.width() * 0.6f, Cfg.height() * 0.3f, 0.7f),
                        new Vector3(Cfg.width() * 0.5f, Cfg.height() * 0.42f, 0.6f),
                        new Vector3(Cfg.width() * 0.25f, Cfg.height() * 0.42f, 0.5f),
                };
                for (int ix = 0; ix < positions.length; ix++) {
                    final Vector3 position = positions[ix];
                    final Image image = new Image(atlas.findRegion("penguin"));
                    image.setPosition(position.x, position.y);
                    image.setOrigin(image.getPrefWidth() * 0.5f, image.getPrefHeight() * 0.5f);
                    image.setScale(0f);
                    image.addAction(sequence(delay(ix * 0.3f), scaleTo(position.z, position.z, 0.3f, Interpolation.swingOut)));
                    stage.addActor(image);
                }
            }
        }), delay(2f), run(new Runnable() {
            @Override
            public void run() {
                Screens.push(new MenuScreen());
            }
        })));




    }

}
