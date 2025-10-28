package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Camera;

public class ParallaxBackground {

    private final Texture backTex;
    private final Texture frontTex;
    private final Texture propsTex;

    private final float worldWidth;
    private final float worldHeight;

    public ParallaxBackground(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        backTex = new Texture("back.png");
        frontTex = new Texture("front.png");
        propsTex = new Texture("props.png");

    }

    public void draw(SpriteBatch batch, Camera camera) {

        float camX = camera.position.x;
        float camY = camera.position.y;

        drawLayer(batch, backTex, 0.2f, camX, camY);

        drawLayer(batch, frontTex, 0.6f, camX, camY);

        drawLayer(batch, propsTex, 1.0f, camX, camY);
    }


    private void drawLayer(SpriteBatch batch, Texture tex, float parallaxFactor,
                           float camX, float camY) {

        float tileW = worldWidth;
        float tileH = worldHeight;

        float rawOffsetX = camX * parallaxFactor;
        float rawOffsetY = camY * parallaxFactor;

        float offsetX = rawOffsetX % tileW;
        float offsetY = rawOffsetY % tileH;

        if (offsetX < 0) offsetX += tileW;
        if (offsetY < 0) offsetY += tileH;

        float baseX = camX - worldWidth / 2f - offsetX;
        float baseY = camY - worldHeight / 2f - offsetY;

        for (int ix = 0; ix < 3; ix++) {
            for (int iy = 0; iy < 3; iy++) {
                float drawX = baseX + ix * tileW;
                float drawY = baseY + iy * tileH;
                batch.draw(tex, drawX, drawY, tileW, tileH);
            }
        }
    }

    public void dispose() {
        backTex.dispose();
        frontTex.dispose();
        propsTex.dispose();
    }
}
