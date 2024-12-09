package si.feri.um.trajkovic.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import si.feri.um.assets.AssetDescriptors;
import si.feri.um.assets.RegionNames;
import si.feri.um.trajkovic.BattleshipsGame;
import si.feri.um.trajkovic.GameConfig;
import si.feri.um.trajkovic.screens.actors.BackgroundActor;

public class IntroScreen extends ScreenAdapter {
    public static final float INTRO_DURATION_IN_SEC = 3.65f;   // duration of the (intro) animation

    private final BattleshipsGame game;
    private final AssetManager assetManager;

    private FitViewport viewport;
    private TextureAtlas gameplayAtlas;
    private BackgroundActor background;

    private float duration = 0f;

    private Stage stage;

    public IntroScreen(BattleshipsGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        // load assets
        assetManager.load(AssetDescriptors.FONT);
        assetManager.load(AssetDescriptors.SKIN);
        assetManager.load(AssetDescriptors.SHIPS);
        assetManager.load(AssetDescriptors.BACKGROUNDS);
        assetManager.load(AssetDescriptors.LASER_IMAGE);
        assetManager.load(AssetDescriptors.SOUND_LASER);
        assetManager.finishLoading();   // blocks until all assets are loaded


        TextureAtlas backgroundAtlas = assetManager.get(AssetDescriptors.BACKGROUNDS);
        background = new BackgroundActor(backgroundAtlas.findRegion(RegionNames.BACKGROUND2),viewport.getScreenWidth(), viewport.getScreenHeight());
        gameplayAtlas = assetManager.get(AssetDescriptors.SHIPS);

        stage.addActor(background);
        stage.addActor(createShip());
        stage.addActor(createAnimation());
        stage.addActor(shootLaser());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        duration += delta;

        // go to the MenuScreen after INTRO_DURATION_IN_SEC seconds
        if (duration > INTRO_DURATION_IN_SEC) {
            game.setScreen(new MenuScreen(game));
        }


        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private Actor createShip() {
        Image badShip = new Image(gameplayAtlas.findRegion(RegionNames.SHIP5));
        // position the image to the center of the window
        badShip.setPosition(viewport.getWorldWidth() / 4f * 3 - badShip.getWidth() / 2f,
            viewport.getWorldHeight() / 2f - badShip.getHeight() / 2f);
        badShip.setScaleY(2f);
        badShip.setScaleX(1.6f);

        badShip.addAction(
            Actions.sequence(
                Actions.delay(2.15f),
                Actions.parallel(
                    Actions.rotateBy(1080, 1.5f),
                    Actions.scaleTo(0,0,1.5f)
                ),
                Actions.removeActor()
            )
        );
        return badShip;
    }

    private Actor createAnimation() {
        Image goodShip = new Image(gameplayAtlas.findRegion(RegionNames.SHIP4));

        // set positions x, y to center the image to the center of the window
        float posX = (viewport.getWorldWidth() / 4f) - goodShip.getWidth() / 2f;
        float posY = (viewport.getWorldHeight() / 2f) - goodShip.getHeight() / 2f;
        goodShip.scaleBy(1.3f);
        goodShip.setRotation(-45f);

        goodShip.setOrigin(Align.center);
        goodShip.addAction(
            Actions.sequence(
                Actions.moveTo(posX, posY, 1.5f),
                Actions.rotateBy(-45, 0.15f),
                Actions.delay(0.9f),
                Actions.moveTo(viewport.getWorldWidth(),posY,1f),
                Actions.removeActor()
            )
        );

        return goodShip;
    }

    private Actor shootLaser() {
        Image laser = new Image(assetManager.get(AssetDescriptors.LASER_IMAGE));
        Sound sound = assetManager.get(AssetDescriptors.SOUND_LASER);
        laser.setPosition(-100, -100);
        laser.setOrigin(Align.center);

        laser.addAction(
            Actions.sequence(
                Actions.delay(1.65f),
                Actions.moveTo(viewport.getWorldWidth() / 4f, viewport.getWorldHeight() / 2f - 10),
                Actions.parallel(
                    Actions.moveTo(viewport.getWorldWidth() / 4f * 3,
                        viewport.getWorldHeight() / 2f, 0.5f),
                    new Action() {
                        private boolean played = false;
                        @Override
                        public boolean act(float delta) {
                            if (!played) {
                                sound.play();
                                played = true;
                            }
                            return true;
                        }
                    }
                    ),
                Actions.removeActor()
            )
        );
        return laser;
    }
}
