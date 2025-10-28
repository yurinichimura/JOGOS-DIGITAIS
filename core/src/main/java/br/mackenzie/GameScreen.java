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
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameScreen implements Screen {

    private ObstacleSystem obstacleSystem;

    private Texture MacDoodle;
    private Sprite MacDoodleSprite;

    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private OrthographicCamera camera;

    private ParallaxBackground parallaxBg;

    private final MainGame game;

    public GameScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Limita o FPS (opcional)
        Gdx.graphics.setForegroundFPS(60);

        // Mundo lógico em UNIDADES (não pixels)
        float worldWidth  = 8f;
        float worldHeight = 5f;

        // Câmera + viewport em unidades do mundo
        camera = new OrthographicCamera();
        viewport = new FitViewport(worldWidth, worldHeight, camera);
        camera.position.set(worldWidth / 2f, worldHeight / 2f, 0f);
        camera.update();

        // Carrega player
        MacDoodle = new Texture("MacDoodle.png");
        MacDoodleSprite = new Sprite(MacDoodle);
        MacDoodleSprite.setSize(1.4f, 1.4f);

        // Posição inicial (centro do mundo)
        MacDoodleSprite.setPosition(
            worldWidth / 2f - MacDoodleSprite.getWidth() / 2f,
            worldHeight / 2f - MacDoodleSprite.getHeight() / 2f
        );

        // Obstáculos estáticos aleatórios em UNIDADES do mundo
        // (não use WIDTH/HEIGHT em pixels aqui)
        obstacleSystem = new ObstacleSystem(
            6,            // quantidade de obstáculos (ajuste se quiser)
            worldWidth,
            worldHeight
        );

        // Parallax em unidades do mundo
        parallaxBg = new ParallaxBackground(worldWidth, worldHeight);

        // Usa o SpriteBatch global do jogo
        spriteBatch = game.batch;
    }


    private void input() {
        float speed = 4f; // unidades por segundo
        float delta = Gdx.graphics.getDeltaTime();

        float dx = 0f;
        float dy = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            dx += 1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            dx -= 1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            dy += 1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            dy -= 1f;
        }

        // normaliza diagonal pra não andar mais rápido em 45°
        if (dx != 0f || dy != 0f) {
            float len = (float)Math.sqrt(dx*dx + dy*dy);
            dx /= len;
            dy /= len;
        }

        MacDoodleSprite.translate(dx * speed * delta, dy * speed * delta);
    }

    private void logic() {
        // NÃO vamos mais limitar o MacDoodle nas bordas
        // então removemos o MathUtils.clamp

        float w = MacDoodleSprite.getWidth();
        float h = MacDoodleSprite.getHeight();

        // câmera segue o player
        camera.position.set(
            MacDoodleSprite.getX() + w / 2f,
            MacDoodleSprite.getY() + h / 2f,
            0f
        );
        camera.update();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

        // desenha paralaxe antes do player
        parallaxBg.draw(spriteBatch, camera);

        // desenha player
        MacDoodleSprite.draw(spriteBatch);

        spriteBatch.end();
    }

    @Override
    public void render(float delta) {
        // ... mantenha sua lógica já existente (input, update etc.)

        // Projeção correta
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

        // Desenhe seu fundo/parallax antes (se tiver método próprio)
        if (parallaxBg != null) {
            parallaxBg.draw(spriteBatch, camera); // ou o método que você já usa
        }

        // Player
        if (MacDoodleSprite != null) {
            MacDoodleSprite.draw(spriteBatch);
        }

        // *** NOVO: Obstáculos ***
        if (obstacleSystem != null) {
            obstacleSystem.draw(spriteBatch);
        }

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        if (viewport != null) {
            viewport.update(width, height, true); // true recentra a câmera
        }
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        if (MacDoodle != null) MacDoodle.dispose();
        if (obstacleSystem != null) obstacleSystem.dispose();
        if (parallaxBg != null) parallaxBg.dispose(); // se sua classe tiver dispose()
    }
}
