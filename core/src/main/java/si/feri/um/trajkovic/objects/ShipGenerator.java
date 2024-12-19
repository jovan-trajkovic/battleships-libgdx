package si.feri.um.trajkovic.objects;

import com.badlogic.gdx.utils.Array;
import java.util.Random;

public class ShipGenerator {
    public static Array<Ship> generateShips(int gridSize) {
        Array<Ship> ships = new Array<>();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(gridSize);
                int col = random.nextInt(gridSize);
                int length = i + 2;
                boolean horizontal = random.nextBoolean();

                Array<GridCell> cells = new Array<>();
                boolean overlaps = false;

                for (int j = 0; j < length; j++) {
                    int r = row + (horizontal ? 0 : j);
                    int c = col + (horizontal ? j : 0);
                    if (r >= gridSize || c >= gridSize) overlaps = true;

                    for(Ship ship : ships) {
                        if (ship.containsCell(r, c)) {
                            overlaps = true;
                            break;
                        }
                    }

                    if (overlaps) break;

                    cells.add(new GridCell(r, c));
                }

                if (cells.size == length) {
                    ships.add(new Ship(cells));
                    placed = true;
                    System.out.println("Ship created");
                }
            }
            System.out.println();
        }
        return ships;
    }
}
