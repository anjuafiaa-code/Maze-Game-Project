package maze;

import java.awt.Color;
import java.awt.Graphics;

public class Maze {

    private final int[][] maze = {
        {1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,1,0,1,0,0,1},
        {1,1,1,0,1,0,1,0,1,1},
        {1,0,0,0,0,0,1,0,0,1},
        {1,0,1,1,1,0,1,1,0,1},
        {1,0,0,0,1,0,0,0,0,1},
        {1,1,1,0,1,1,1,1,0,1},
        {1,0,0,0,0,0,0,1,0,1},
        {1,0,1,1,1,1,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1}
    };

    public int getRows() {
        return maze.length;
    }

    public int getCols() {
        return maze[0].length;
    }

    public boolean isWall(int row, int col) {
        return maze[row][col] == 1;
    }

    public void draw(Graphics g, int tileSize) {

        for (int row = 0; row < maze.length; row++) {

            for (int col = 0; col < maze[row].length; col++) {

                if (maze[row][col] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(
                    col * tileSize,
                    row * tileSize,
                    tileSize,
                    tileSize
                );

                g.setColor(Color.GRAY);
                g.drawRect(
                    col * tileSize,
                    row * tileSize,
                    tileSize,
                    tileSize
                );
            }
        }
    }
}