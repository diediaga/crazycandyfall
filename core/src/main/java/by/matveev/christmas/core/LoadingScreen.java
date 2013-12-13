package by.matveev.christmas.core;


import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class LoadingScreen extends AbstractScreen {

    private final Runnable onComplete;

    public LoadingScreen(Runnable onComplete) {

        this.onComplete = onComplete;
    }

    @Override
    public void load() {

        final Assets assets = Assets.instance();
        assets.loadAsset("gfx/masks.atlas", TextureAtlas.class);
        assets.loadAsset("gfx/icons.atlas", TextureAtlas.class);
        assets.loadAsset("gfx/game.atlas", TextureAtlas.class);
        assets.loadAsset("fonts/font.fnt", BitmapFont.class);
        assets.loadAsset("sounds/music.ogg", Music.class);
        assets.finishLoading();
    }

    @Override
    public void unload() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if ( Assets.instance().update()) {
            stage.addAction(Actions.sequence(Actions.delay(1f), new Action() {
                @Override
                public boolean act(float delta) {
                    if (onComplete != null) onComplete.run();
                    return true;
                }
            }));
        }

    }
}
