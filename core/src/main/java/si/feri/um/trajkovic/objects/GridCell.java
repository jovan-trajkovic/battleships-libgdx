package si.feri.um.trajkovic.objects;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GridCell {
    public enum State { UNCLICKED, HIT, MISS }

    private final int row, col;
    private State state = State.UNCLICKED;
    private boolean containsShip = false;

    public GridCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public TextButton createButton(Skin skin) {
        TextButton button = new TextButton("", skin);
        updateButtonAppearance(button);
        return button;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void updateButtonAppearance(TextButton button) {
        switch (state) {
            case UNCLICKED:
                button.setText("");
                break;
            case HIT:
                button.setText("X");
                button.setDisabled(true);
                break;
            case MISS:
                button.setText("O");
                button.setDisabled(true);
                break;
        }
    }

    public boolean containsShip() {
        return containsShip;
    }

    public void setContainsShip(boolean containsShip) {
        this.containsShip = containsShip;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
