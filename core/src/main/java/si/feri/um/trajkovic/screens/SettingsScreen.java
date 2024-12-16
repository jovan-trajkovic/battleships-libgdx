package si.feri.um.trajkovic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import si.feri.um.assets.AssetDescriptors;
import si.feri.um.assets.RegionNames;
import si.feri.um.trajkovic.BattleshipsGame;
import si.feri.um.trajkovic.GameConfig;
import si.feri.um.trajkovic.GameManager;

public class SettingsScreen extends ScreenAdapter {

    private final BattleshipsGame game;
    private final AssetManager assetManager;
    private FitViewport viewport;
    private Stage stage;
    private Skin skin;
    private TextureAtlas backgrounds;
    private GameManager gameManager;

    public SettingsScreen(BattleshipsGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        gameManager = game.getGameManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        skin = assetManager.get(AssetDescriptors.SKIN);
        backgrounds = assetManager.get(AssetDescriptors.BACKGROUNDS);

        stage.addActor(createTable());
        Gdx.input.setInputProcessor(stage);
    }

    private Actor createTable() {
        Table table = new Table();
        table.setFillParent(true);

        TextureRegion menuBackgroundRegion = backgrounds.findRegion(RegionNames.BACKGROUND2);
        menuBackgroundRegion.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        table.setBackground(new TiledDrawable(menuBackgroundRegion));

        Label titleLabel = new Label("Settings", skin, "title");
        table.add(titleLabel).colspan(3).padBottom(20).row();

        createGridSizeButtons(table);
        createGameModeButtons(table);

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        table.add(backButton).colspan(3).padTop(20);

        return table;
    }

    private void createGridSizeButtons(Table table) {
        Label gridLabel = new Label("Grid Size:", skin);
        table.add(gridLabel).left().pad(10);

        TextButton grid8x8 = new TextButton("8x8", skin);
        TextButton grid9x9 = new TextButton("9x9", skin);
        TextButton grid10x10 = new TextButton("10x10", skin);

        updateSizeSelection(grid8x8, grid9x9, grid10x10);

        grid8x8.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.saveGridSize(8);
                updateSizeSelection(grid8x8, grid9x9, grid10x10);
            }
        });
        grid9x9.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.saveGridSize(9);
                updateSizeSelection(grid8x8, grid9x9, grid10x10);
            }
        });
        grid10x10.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.saveGridSize(10);
                updateSizeSelection(grid8x8, grid9x9, grid10x10);
            }
        });

        table.add(grid8x8).pad(10);
        table.add(grid9x9).pad(10);
        table.add(grid10x10).pad(10).row();
    }

    public void updateSizeSelection(TextButton b1, TextButton b2, TextButton b3) {
        b1.setDisabled(false); b2.setDisabled(false); b3.setDisabled(false);
        switch(gameManager.loadGridSize()){
            case 8:{
                b1.setDisabled(true);
                break;
            }
            case 9:{
                b2.setDisabled(true);
                break;
            }
            case 10:{
                b3.setDisabled(true);
                break;
            }
        }
    }

    private void createGameModeButtons(Table table) {
        Label modeLabel = new Label("Game Mode:", skin);
        table.add(modeLabel).left().pad(10);

        TextButton singleplayerButton = new TextButton("Singleplayer", skin);
        TextButton multiplayerButton = new TextButton("Multiplayer", skin);
        updateGameModeSelection(singleplayerButton, multiplayerButton);

        singleplayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.saveGameMode("Singleplayer");
                updateGameModeSelection(singleplayerButton, multiplayerButton);
            }
        });
        multiplayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.saveGameMode("Multiplayer");
                updateGameModeSelection(singleplayerButton, multiplayerButton);
            }
        });

        table.add(singleplayerButton).pad(10);
        table.add(multiplayerButton).pad(10).row();
    }

    public void updateGameModeSelection(TextButton b1, TextButton b2) {
        b1.setDisabled(false); b2.setDisabled(false);
        switch(gameManager.loadGameMode()){
            case "Singleplayer":{
                b1.setDisabled(true);
                break;
            }
            case "Multiplayer":{
                b2.setDisabled(true);
                break;
            }
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
