package si.feri.um.trajkovic.objects;

import com.badlogic.gdx.utils.Array;

public class Ship {
    private final Array<GridCell> cells;
    private int shipHealth;
    public Ship(Array<GridCell> cells) {
        this.cells = cells;
        for (GridCell cell : cells) {
            cell.setContainsShip(true);
        }
        shipHealth = cells.size;
    }

    public void shipHit(){
        shipHealth--;
        System.out.println("Ship hit");
    }

    public boolean isDestroyed(){
        return shipHealth == 0;
    }

    public Array<GridCell> getCells() {return cells;}

    public boolean containsCell(int row, int col) {
        for (GridCell cell : cells) {
            if (cell.getRow() == row && cell.getCol() == col) {
                return true;
            }
        }
        return false;
    }
}
