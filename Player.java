package player;

import java.awt.Color;
import java.awt.Graphics;

public class Player {

    private int row;
    private int col;

    public Player(int startRow, int startCol) {
        row = startRow;
        col = startCol;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void move(int newRow, int newCol) {
        row = newRow;
        col = newCol;
    }

    public void draw(Graphics g, int tileSize) {

        g.setColor(Color.RED);

        g.fillOval(
            col * tileSize + 10,
            row * tileSize + 10,
            tileSize - 20,
            tileSize - 20
        );
    }
}