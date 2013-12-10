package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

import static com.badlogic.gdx.math.MathUtils.random;
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

        final TextureAtlas atlas = Assets.instance().get("gfx/game.atlas");

        final Table table = new Table();

        table.add(new Image(atlas.findRegion("logo"))).expandX().padBottom(30f);
        table.row();

        table.add(new Image(atlas.findRegion("santahead"))).align(Align.center);
        table.row();

        table.setPosition((Cfg.width() - table.getPrefWidth()) * 0.5f, Cfg.height() * 0.5f);
        stage.addActor(table);



//
//
//
//
//        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
//        style.fontColor = Color.WHITE;
//        style.font = Assets.instance().get("fonts/font.fnt");
//        style.pressedOffsetX= - 5f;
//        style.pressedOffsetY= - 5f;
//
//        final TextButton startButton = new TextButton("PLAY", style);
//        startButton.setPosition((Cfg.width() - startButton.getPrefWidth()) * 0.5f, Cfg.height() * 0.6f);
//        startButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                Screens.set(new PlayScreen());
//            }
//        });
//        stage.addActor(startButton);
//
//        final TextButton howToButton = new TextButton("HOW TO PLAY", style);
//        howToButton.setPosition((Cfg.width() - howToButton.getPrefWidth()) * 0.5f, Cfg.height() * 0.45f);
//        stage.addActor(howToButton);
//
//
//        final TextButton leaderboardButton = new TextButton("HIGHSCORES", style);
//        leaderboardButton.setPosition((Cfg.width() - leaderboardButton.getPrefWidth()) * 0.5f, Cfg.height() * 0.3f);
//        stage.addActor(leaderboardButton);


    }
}
