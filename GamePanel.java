package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import maze.Maze;
import player.Player;

public class GamePanel extends JPanel implements KeyListener {

    private Maze maze;
    private Player player;

    private final int TILE_SIZE = 50;

    private boolean gameWon = false;

    public GamePanel() {

        maze = new Maze();

        
        player = new Player(
                maze.getStartRow(),
                maze.getStartCol()
        );

        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        
        for (int row = 0; row < maze.getRows(); row++) {

            for (int col = 0; col < maze.getCols(); col++) {

                int cell = maze.getCell(row, col);

                if (cell == 1) {

                    
                    g.setColor(Color.BLACK);

                    g.fillRect(
                            col * TILE_SIZE,
                            row * TILE_SIZE,
                            TILE_SIZE,
                            TILE_SIZE
                    );

                } else {

                    
                    g.setColor(Color.WHITE);

                    g.fillRect(
                            col * TILE_SIZE,
                            row * TILE_SIZE,
                            TILE_SIZE,
                            TILE_SIZE
                    );

                    
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

        
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 9));

        g.drawString(
                "START",
                maze.getStartCol() * TILE_SIZE + 7,
                maze.getStartRow() * TILE_SIZE + 12
        );

        
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 12));

        g.drawString(
                "END",
                maze.getExitCol() * TILE_SIZE + 12,
                maze.getExitRow() * TILE_SIZE + 15
        );

      
        g.setColor(Color.RED);

        g.fillOval(
                player.getCol() * TILE_SIZE + 12,
                player.getRow() * TILE_SIZE + 20,
                TILE_SIZE - 24,
                TILE_SIZE - 26
        );
    }

   
    private boolean canMove(int newRow, int newCol) {

        
        if (newRow < 0 || newRow >= maze.getRows()
                || newCol < 0 || newCol >= maze.getCols()) {

            return false;
        }

        
        return maze.getCell(newRow, newCol) == 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        
        if (gameWon) {
            return;
        }

        int newRow = player.getRow();
        int newCol = player.getCol();

        
        if (e.getKeyCode() == KeyEvent.VK_UP) {

            newRow--;

        
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

            newRow++;

        
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            newCol--;

        
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            newCol++;
        }

        
        if (canMove(newRow, newCol)) {

            player.setPosition(newRow, newCol);

            repaint();

 
            if (newRow == maze.getExitRow()
                    && newCol == maze.getExitCol()) {

                gameWon = true;

                JOptionPane.showMessageDialog(
                        this,
                        "Congratulations! You reached the END!"
                );
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}