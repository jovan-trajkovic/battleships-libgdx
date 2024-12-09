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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import si.feri.um.assets.AssetDescriptors;
import si.feri.um.assets.RegionNames;
import si.feri.um.trajkovic.BattleshipsGame;
import si.feri.um.trajkovic.GameConfig;

public class LeaderboardScreen extends ScreenAdapter {
    private final BattleshipsGame game;
    private final AssetManager assetManager;
    private FitViewport viewport;
    private Stage stage;
    private Skin skin;
    private TextureAtlas backgrounds;

    public LeaderboardScreen(BattleshipsGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        skin = assetManager.get(AssetDescriptors.SKIN);
        backgrounds = assetManager.get(AssetDescriptors.BACKGROUNDS);

        stage.addActor(createLeaderboard());
        Gdx.input.setInputProcessor(stage);
    }

    private void addLeaderboardEntries(Table table) {
        Table leaderboardTable = new Table();

        Array<String[]> leaderboardData = new Array<>();
        leaderboardData.add(new String[]{"Player1", "1500"});
        leaderboardData.add(new String[]{"Player2", "1200"});
        leaderboardData.add(new String[]{"Player3", "1000"});
        leaderboardData.add(new String[]{"Player4", "850"});
        leaderboardData.add(new String[]{"Player5", "700"});

        for (String[] entry : leaderboardData) {
            leaderboardTable.add(new Label(entry[0], skin)).expandX().left().pad(10);
            leaderboardTable.add(new Label(entry[1], skin)).expandX().right().pad(10).row();
        }

        ScrollPane scrollPane = new ScrollPane(leaderboardTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        table.add(scrollPane).width(400f).colspan(2).row();
    }

    private Actor createLeaderboard() {
        Table table = new Table();
        table.defaults().pad(20);
        table.setFillParent(true);
        table.center();

        TextureRegion menuBackgroundRegion = backgrounds.findRegion(RegionNames.BACKGROUND2);
        menuBackgroundRegion.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        table.setBackground(new TiledDrawable(menuBackgroundRegion));

        Label titleLabel = new Label("Leaderboard", skin);
        titleLabel.setFontScale(2);
        table.add(titleLabel).colspan(2).center().padBottom(50).row();

        table.add(new Label("Player name", skin)).left();
        table.add(new Label("Score", skin)).right().row();

        addLeaderboardEntries(table);

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        table.row().padTop(20);
        table.add(backButton).colspan(2).center();
        return table;
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
