package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Obstacle {
    private static Texture tex;
    public final Rectangle bounds;

    private static void ensureTex() {
        if (tex == null) tex = new Texture("obstacle.png"); // já na pasta assets
    }

    public static int getWidth()  { ensureTex(); return tex.getWidth(); }
    public static int getHeight() { ensureTex(); return tex.getHeight(); }

    public Obstacle(float x, float y) {
        ensureTex();
        bounds = new Rectangle(x, y, tex.getWidth(), tex.getHeight());
    }

    public void draw(SpriteBatch batch) {
        batch.draw(tex, bounds.x, bounds.y);
    }

    public static void disposeStatic() {
        if (tex != null) { tex.dispose(); tex = null; }
    }
}
