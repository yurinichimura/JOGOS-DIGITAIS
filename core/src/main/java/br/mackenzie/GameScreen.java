package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {

    private Texture backgroundTexture;
    private Texture MacDoodle;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Sprite MacDoodleSprite;

    private final MainGame game;

    public GameScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.graphics.setForegroundFPS(60);

        backgroundTexture = new Texture("background.png");
        MacDoodle = new Texture("MacDoodle.png");

        MacDoodleSprite = new Sprite(MacDoodle);
        MacDoodleSprite.setSize(1.4f, 1.4f);

        spriteBatch = game.batch; // usa o SpriteBatch global
        viewport = new FitViewport(8, 5);
    }

    private void input() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            MacDoodleSprite.translateX(speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            MacDoodleSprite.translateX(-speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            MacDoodleSprite.translateY(speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            MacDoodleSprite.translateY(-speed * delta);
        }
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float MacDoodleWidth = MacDoodleSprite.getWidth();
        float MacDoodleHeight = MacDoodleSprite.getHeight();

        MacDoodleSprite.setX(MathUtils.clamp(MacDoodleSprite.getX(), 0, worldWidth - MacDoodleWidth));
        MacDoodleSprite.setY(MathUtils.clamp(MacDoodleSprite.getY(), 0, worldHeight - MacDoodleHeight));
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        MacDoodleSprite.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        MacDoodle.dispose();
    }
}
