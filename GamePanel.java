package game;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import maze.Maze;

public class GamePanel extends JPanel {

    private Maze maze;

    private final int TILE_SIZE = 50;

    public GamePanel() {

        maze = new Maze();

        setPreferredSize(new Dimension(
            maze.getCols() * TILE_SIZE,
            maze.getRows() * TILE_SIZE
        ));
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        maze.draw(g, TILE_SIZE);
    }
}