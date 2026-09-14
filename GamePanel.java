package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import maze.Maze;
import player.Player;

public class GamePanel extends JPanel implements KeyListener {

    private Maze maze;
    private Player player;

    private final int TILE_SIZE = 50;

    // Game state
    private boolean gameWon = false;
    private boolean gameStarted = false;

    // Timer
    private int timeSeconds = 0;
    private Timer timer;

    // Move count
    private int moves = 0;

    public GamePanel() {

        maze = new Maze();

        // Player starts at START position
        player = new Player(
                maze.getStartRow(),
                maze.getStartCol()
        );

        setFocusable(true);
        addKeyListener(this);

        // Timer runs every 1 second
        timer = new Timer(1000, e -> {
            timeSeconds++;
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // Draw maze
        for (int row = 0; row < maze.getRows(); row++) {

            for (int col = 0; col < maze.getCols(); col++) {

                int cell = maze.getCell(row, col);

                if (cell == 1) {

                    // Wall
                    g.setColor(Color.BLACK);

                    g.fillRect(
                            col * TILE_SIZE,
                            row * TILE_SIZE,
                            TILE_SIZE,
                            TILE_SIZE
                    );

                } else {

                    // Path
                    g.setColor(Color.WHITE);

                    g.fillRect(
                            col * TILE_SIZE,
                            row * TILE_SIZE,
                            TILE_SIZE,
                            TILE_SIZE
                    );

                    // Border
                    g.setColor(Color.LIGHT_GRAY);

                    g.drawRect(
                            col * TILE_SIZE,
                            row * TILE_SIZE,
                            TILE_SIZE,
                            TILE_SIZE
                    );
                }
            }
        }

        // Draw START
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 9));

        g.drawString(
                "START",
                maze.getStartCol() * TILE_SIZE + 7,
                maze.getStartRow() * TILE_SIZE + 12
        );

        // Draw END
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 12));

        g.drawString(
                "END",
                maze.getExitCol() * TILE_SIZE + 12,
                maze.getExitRow() * TILE_SIZE + 15
        );

        // Draw Player
        g.setColor(Color.RED);

        g.fillOval(
                player.getCol() * TILE_SIZE + 12,
                player.getRow() * TILE_SIZE + 20,
                TILE_SIZE - 24,
                TILE_SIZE - 26
        );
    }

    // Check whether player can move
    private boolean canMove(int newRow, int newCol) {

        // Check maze boundary
        if (newRow < 0 || newRow >= maze.getRows()
                || newCol < 0 || newCol >= maze.getCols()) {

            return false;
        }

        // Player can move only on path
        return maze.getCell(newRow, newCol) == 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // Press R to restart
        if (e.getKeyCode() == KeyEvent.VK_R) {

            restartGame();
            return;
        }

        // Stop movement after winning
        if (gameWon) {
            return;
        }

        int newRow = player.getRow();
        int newCol = player.getCol();

        // Up
        if (e.getKeyCode() == KeyEvent.VK_UP) {

            newRow--;

        // Down
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

            newRow++;

        // Left
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            newCol--;

        // Right
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            newCol++;
        }

        // Move only if destination is a path
        if (canMove(newRow, newCol)) {

            // Start timer on first valid movement
            if (!gameStarted) {

                gameStarted = true;
                timer.start();
            }

            // Update player position
            player.setPosition(newRow, newCol);

            // Count valid moves
            moves++;

            repaint();

            // Check winning condition
            if (newRow == maze.getExitRow()
                    && newCol == maze.getExitCol()) {

                gameWon = true;

                // Stop timer
                timer.stop();

                JOptionPane.showMessageDialog(
                        this,
                        "Congratulations! You reached the END!\n"
                        + "Time: " + timeSeconds + " seconds\n"
                        + "Moves: " + moves + "\n\n"
                        + "Press R to Restart"
                );
            }
        }
    }

    // Restart and reset the game
    public void restartGame() {

        // Stop timer
        timer.stop();

        // Reset player position
        player.setPosition(
                maze.getStartRow(),
                maze.getStartCol()
        );

        // Reset game state
        gameWon = false;
        gameStarted = false;

        // Reset timer and moves
        timeSeconds = 0;
        moves = 0;

        repaint();

        requestFocusInWindow();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}