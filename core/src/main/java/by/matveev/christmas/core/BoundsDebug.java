package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

import java.util.ArrayList;
import java.util.List;

public class BoundsDebug {

    public static final List<Bounds> boundsList = new ArrayList<Bounds>();
    private static final ShapeRenderer renderer = new ShapeRenderer();

    private BoundsDebug() {
    }

    public static void addBounds(Bounds bounds) {
        boundsList.add(bounds);
    }

    public static void render(Matrix4 projection, float delta) {
        renderer.setProjectionMatrix(projection);

        for (Bounds bounds : boundsList) {
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.GREEN);
            renderer.identity();
            renderer.translate(bounds.x + bounds.width * 0.5f, bounds.y + bounds.height * 0.5f, 0f);
            renderer.rotate(0f, 0f, 1f, bounds.angle);
            renderer.rect(-bounds.width/2, -bounds.height/2, bounds.width, bounds.height);
            renderer.end();
        }
    }

}
