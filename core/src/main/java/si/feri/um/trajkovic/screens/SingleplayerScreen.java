package si.feri.um.trajkovic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import si.feri.um.assets.AssetDescriptors;
import si.feri.um.assets.RegionNames;
import si.feri.um.trajkovic.BattleshipsGame;
import si.feri.um.trajkovic.GameConfig;
import si.feri.um.trajkovic.GameManager;
import si.feri.um.trajkovic.objects.GridCell;
import si.feri.um.trajkovic.objects.Ship;
import si.feri.um.trajkovic.objects.ShipGenerator;

public class SingleplayerScreen extends ScreenAdapter {

    private final BattleshipsGame game;
    private final AssetManager assetManager;
    private final GameManager gameManager;
    private Skin skin;
    private Stage stage;
    private Table table;
    private TextureAtlas backgrounds;

    private int gridSize;
    private GridCell[][] gridCells;
    private Array<Ship> ships;
    private int moveCount = 0;
    private Label moveCountLabel;

    public SingleplayerScreen(BattleshipsGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        gameManager = game.getGameManager();
        gridSize = gameManager.loadGridSize();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT));
        skin = assetManager.get(AssetDescriptors.SKIN);
        backgrounds = assetManager.get(AssetDescriptors.BACKGROUNDS);
        Gdx.input.setInputProcessor(stage);

        createGrid();
        generateShips();
        for(Ship ship : ships) {
            for(GridCell cell : ship.getCells()){
                System.out.println(cell.getRow() + " " + cell.getCol());
            }
        }
        for(Ship ship : ships) {
            for(GridCell cell : ship.getCells()){
                gridCells[cell.getRow()][cell.getCol()].setContainsShip(true);
            }
        }
        setupUI();
    }

    private void createGrid() {
        gridCells = new GridCell[gridSize][gridSize];
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                gridCells[row][col] = new GridCell(row, col);
            }
        }
    }

    private void generateShips() {
        ships = ShipGenerator.generateShips(gridSize);
    }

    private void setupUI() {
        moveCountLabel = new Label("Move count: " + moveCount, skin);

        table = new Table();
        table.setFillParent(true);

        TextureRegion menuBackgroundRegion = backgrounds.findRegion(RegionNames.BACKGROUND2);
        menuBackgroundRegion.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        table.setBackground(new TiledDrawable(menuBackgroundRegion));

        table.add(moveCountLabel).colspan(gridSize).center().padBottom(10);
        table.row();

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                final GridCell cell = gridCells[row][col];
                TextButton button = cell.createButton(skin);
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(!button.isDisabled()) {
                            handleClick(cell);
                            cell.updateButtonAppearance(button);
                        }
                    }
                });
                table.add(button).size(50).pad(2);
            }
            table.row();
        }

        stage.addActor(table);
    }

    private void handleClick(GridCell cell) {
        moveCount++;
        moveCountLabel.setText("Move count: " + moveCount);
        if (cell.containsShip()) {
            cell.setState(GridCell.State.HIT);
        } else {
            cell.setState(GridCell.State.MISS);
        }
        for (Ship ship : ships) {
            if (ship.containsCell(cell.getRow(), cell.getCol())) {
                ship.shipHit();
            }
        }

        if (isGameOver()) {
            System.out.println("You win! Total moves: " + moveCount);
            //TODO: Switch to a endScreen where the user will save their score to the leaderboard
        }
    }

    private boolean isGameOver() {
        for (Ship ship : ships) {
            if (!ship.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
