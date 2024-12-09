package si.feri.um.trajkovic.screens.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackgroundActor extends Actor {
    private final TextureRegion texture;
    private final float screenWidth;
    private final float screenHeight;

    public BackgroundActor(TextureRegion texture, float screenWidth, float screenHeight) {
        this.texture = texture;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (float x = 0; x < screenWidth; x += texture.getRegionWidth()) {
            for (float y = 0; y < screenHeight; y += texture.getRegionHeight()) {
                batch.draw(texture, x, y, texture.getRegionWidth(), texture.getRegionHeight());
            }
        }
    }

}
