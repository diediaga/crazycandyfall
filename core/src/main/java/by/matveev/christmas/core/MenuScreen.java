package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

/**
 * @author Alexey Matveev
 */
public class MenuScreen extends AbstractScreen {

    private final Group effectGroup = new Group();

    public MenuScreen() {
    }

    @Override
    public void show() {
        super.show();


        effectGroup.setSize(Cfg.width(), Cfg.height());
        stage.addActor(effectGroup);

        final Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
//                final Candy candy = new Candy();
//                candy.setRotation(random(360f));
//                candy.setY(Cfg.height() + candy.getHeight());
//                candy.setX(random(Cfg.width()));
//                candy.setOrigin(candy.getWidth() * 0.5f, candy.getHeight() * 0.5f);
//                candy.addAction(repeat(-1, rotateBy(10f, 0.1f)));
//                candy.addAction(repeat(-1, moveBy(0, -5, 0.1f)));
//                candy.getColor().a = 40f / 255f;
//                effectGroup.addActor(candy);
            }
        }, 1f, 0.5f);


        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.fontColor = Color.WHITE;
        style.font = Assets.instance().get("fonts/font.fnt");
        style.pressedOffsetX= - 5f;
        style.pressedOffsetY= - 5f;

        final TextButton startButton = new TextButton("Play", style);
        startButton.setPosition((Cfg.width() - startButton.getPrefWidth()) * 0.5f, Cfg.height() * 0.6f);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Screens.set(new PlayScreen());
            }
        });
        stage.addActor(startButton);

        final TextButton howToButton = new TextButton("How to play", style);
        howToButton.setPosition((Cfg.width() - howToButton.getPrefWidth()) * 0.5f, Cfg.height() * 0.45f);
        stage.addActor(howToButton);


        final TextButton leaderboardButton = new TextButton("Highscores", style);
        leaderboardButton.setPosition((Cfg.width() - leaderboardButton.getPrefWidth()) * 0.5f, Cfg.height() * 0.3f);
        stage.addActor(leaderboardButton);


    }
}
