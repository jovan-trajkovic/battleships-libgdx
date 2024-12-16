package si.feri.um.trajkovic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import si.feri.um.trajkovic.screens.IntroScreen;

public class BattleshipsGame extends Game {
    private SpriteBatch batch;
    private AssetManager assetManager;
    private GameManager gameManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        gameManager = new GameManager();

        setScreen(new IntroScreen(this));
    }

/*    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();

        batch.end();
    }*/

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
    }
}
