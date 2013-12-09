package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * @author Alexey Matveev
 */
public class MenuScreen extends AbstractScreen {

    public MenuScreen() {
    }

    @Override
    public void show() {
        super.show();

        addLogoIcons();

        stage.addAction(sequence(delay(1f), new Action() {
            @Override
            public boolean act(float v) {
                Screens.set(new PlayScreen());
                return true;
            }
        }));

    }

    private void addLogoIcons() {
        final TextureAtlas iconsAtlas = Assets.instance().get("gfx/icons.atlas");

        final int iconsCount = iconsAtlas.getRegions().size;

        float totalWith = 42 * iconsCount;
        float padding = 20;
        totalWith += padding * (iconsCount - 1);

        float startX = (Cfg.width() - totalWith) * 0.5f;
        float startY = Cfg.height() * 0.7f;

        final String prefix = "icon";

        for (int ix = 1; ix <= iconsCount; ix++) {
            final Image image = new Image(iconsAtlas.findRegion(prefix + ix));
            image.setPosition(startX, startY);
            image.getColor().a = 0f;
            image.addAction(sequence(delay(ix * 0.1f), alpha(1f, 0.5f)));
            startX += 42 + padding;
            stage.addActor(image);
        }
    }
}
