package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import maze.Maze;
import player.Player;

public class GamePanel extends JPanel implements KeyListener {

    private Maze maze;
    private Player player;

    private final int TILE_SIZE = 50;

    public GamePanel() {

        maze = new Maze();

        // Player starting position
        player = new Player(1, 1);

        setPreferredSize(new Dimension(
            maze.getCols() * TILE_SIZE,
            maze.getRows() * TILE_SIZE
        ));

        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        maze.draw(g, TILE_SIZE);
        player.draw(g, TILE_SIZE);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int newRow = player.getRow();
        int newCol = player.getCol();

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            newRow--;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            newRow++;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            newCol--;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            newCol++;
        }

        // Move only if the new position is not a wall
        if (!maze.isWall(newRow, newCol)) {
            player.move(newRow, newCol);
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}