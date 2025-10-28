package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MenuScreen implements Screen {

    private Texture background;

    private static final int SAIR_BOTAO_WIDTH = 300;
    private static final int SAIR_BOTAO_HEIGHT = 150;
    private static final int SAIR_BOTAO_Y = 20;

    private static final int JOGAR_BOTAO_Y = 100;
    private static final int JOGAR_BOTAO_WIDTH = 300;
    private static final int JOGAR_BOTAO_HEIGHT = 150;

    private static final int LETREIRO_WIDTH = 500;
    private static final int LETREIRO_HEIGHT = 300;
    private static final int LETREIRO_Y = 250;
    private static final int LETREIRO_X = 150;

    private Music gameMusic;
    private Sound CLICK;

    MainGame game;

    Texture LETREIRO;
    Texture jogarBotaoAtivo;
    Texture jogarBotaoInativo;
    Texture sairBotaoAtivo;
    Texture sairBotaoInativo;

    private Rectangle jogarBounds;
    private Rectangle sairBounds;
    private Vector3 mousePos;

    public MenuScreen(MainGame game) {
        this.game = game;

        background = new Texture("background.png");


        jogarBotaoAtivo = new Texture("botao_ativo.png");
        jogarBotaoInativo = new Texture("botao_inativo.png");
        sairBotaoAtivo = new Texture("botao_sair_ativo.png");
        sairBotaoInativo = new Texture("botao_sair_inativo.png");
        LETREIRO = new Texture("letreiro.png");

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("musica.mp3"));
        gameMusic.setVolume(0.3f);
        gameMusic.setLooping(true);
        gameMusic.play();

        CLICK = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));

        int x = MainGame.WIDTH / 2 - JOGAR_BOTAO_WIDTH / 2;
        jogarBounds = new Rectangle(x, JOGAR_BOTAO_Y, JOGAR_BOTAO_WIDTH, JOGAR_BOTAO_HEIGHT);
        sairBounds = new Rectangle(x, SAIR_BOTAO_Y, SAIR_BOTAO_WIDTH, SAIR_BOTAO_HEIGHT);

        mousePos = new Vector3();
    }

    @Override
    public void render(float delta) {
        // Atualiza posição do mouse
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.camera.unproject(mousePos); // converte para coordenadas do mundo

        // Limpa tela
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.begin();
        game.batch.draw(background, 0, 0, MainGame.WIDTH, MainGame.HEIGHT);

        // Desenha letreiro
        game.batch.draw(LETREIRO, LETREIRO_X, LETREIRO_Y, LETREIRO_WIDTH, LETREIRO_HEIGHT);

        // Botão "Jogar"
        if (jogarBounds.contains(mousePos.x, mousePos.y)) {
            game.batch.draw(jogarBotaoAtivo, jogarBounds.x, jogarBounds.y, jogarBounds.width, jogarBounds.height);
            if (Gdx.input.isTouched()) {
                CLICK.play();
                game.setScreen(new GameScreen(game));
            }
        } else {
            game.batch.draw(jogarBotaoInativo, jogarBounds.x, jogarBounds.y, jogarBounds.width, jogarBounds.height);
        }

        // Botão "Sair"
        if (sairBounds.contains(mousePos.x, mousePos.y)) {
            game.batch.draw(sairBotaoAtivo, sairBounds.x, sairBounds.y, sairBounds.width, sairBounds.height);
            if (Gdx.input.isTouched()) {
                CLICK.play();
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(sairBotaoInativo, sairBounds.x, sairBounds.y, sairBounds.width, sairBounds.height);
        }

        game.batch.end();
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        LETREIRO.dispose();
        jogarBotaoAtivo.dispose();
        jogarBotaoInativo.dispose();
        sairBotaoAtivo.dispose();
        sairBotaoInativo.dispose();
        gameMusic.dispose();
        CLICK.dispose();
        background.dispose();
    }
}
