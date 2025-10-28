package br.mackenzie;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class ObstacleSystem {
    public final Array<Obstacle> obstacles = new Array<>();

    public ObstacleSystem(int count, float worldWidth, float worldHeight) {
        spawn(count, worldWidth, worldHeight);
    }

    private void spawn(int count, float worldWidth, float worldHeight) {
        obstacles.clear();
        int ow = Obstacle.getWidth();
        int oh = Obstacle.getHeight();

        for (int i = 0; i < count; i++) {
            float x = MathUtils.random(0f, Math.max(0f, worldWidth  - ow));
            float y = MathUtils.random(0f, Math.max(0f, worldHeight - oh));
            obstacles.add(new Obstacle(x, y)); // estáticos: sem update
        }
    }

    public void draw(SpriteBatch batch) {
        for (Obstacle o : obstacles) o.draw(batch);
    }

    public void dispose() {
        Obstacle.disposeStatic();
    }
}
