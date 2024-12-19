package si.feri.um.trajkovic.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;

import si.feri.um.trajkovic.BattleshipsGame;
import si.feri.um.trajkovic.GameManager;

public class MultiplayerScreen extends ScreenAdapter {

    private final BattleshipsGame game;
    private final AssetManager assetManager;
    private final GameManager gameManager;

    public MultiplayerScreen(BattleshipsGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        gameManager = game.getGameManager();
    }
}
