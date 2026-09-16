package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import maze.Maze;
import player.Player;

public class GamePanel extends JPanel implements KeyListener {

    private Maze maze;
    private Player player;

    private final int TILE_SIZE = 50;

    private boolean gameWon = false;
    private boolean gameStarted = false;

    private int timeSeconds = 0;
    private Timer timer;

    private int moves = 0;

    private JLabel timeLabel;
    private JLabel movesLabel;
    private JLabel statusLabel;

    private JButton restartButton;

    public GamePanel() {

        maze = new Maze();

        player = new Player(
                maze.getStartRow(),
                maze.getStartCol()
        );

        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();

        timeLabel = new JLabel("Time: 0 sec");
        movesLabel = new JLabel("Moves: 0");
        statusLabel = new JLabel("Status: Ready");

        restartButton = new JButton("Restart");

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        timeLabel.setFont(labelFont);
        movesLabel.setFont(labelFont);
        statusLabel.setFont(labelFont);
        restartButton.setFont(labelFont);

        restartButton.setFocusable(false);

        restartButton.addActionListener(e -> restartGame());

        infoPanel.add(timeLabel);
        infoPanel.add(movesLabel);
        infoPanel.add(statusLabel);
        infoPanel.add(restartButton);

        add(infoPanel, BorderLayout.NORTH);

        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(1000, e -> {
            timeSeconds++;
            updateLabels();
        });
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

        if (e.getKeyCode() == KeyEvent.VK_R) {

            restartGame();

            return;
        }

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

            if (!gameStarted) {

                gameStarted = true;

                timer.start();

                statusLabel.setText("Status: Playing");
            }

            player.setPosition(newRow, newCol);

            moves++;

            updateLabels();

            repaint();

            if (newRow == maze.getExitRow()
                    && newCol == maze.getExitCol()) {

                gameWon = true;

                timer.stop();

                statusLabel.setText("Status: Won");

                JOptionPane.showMessageDialog(
                        this,
                        "Congratulations! You reached the END!\n"
                        + "Time: " + timeSeconds + " seconds\n"
                        + "Moves: " + moves + "\n\n"
                        + "Press R or click Restart"
                );
            }
        }
    }

    private void updateLabels() {

        timeLabel.setText(
                "Time: " + timeSeconds + " sec"
        );

        movesLabel.setText(
                "Moves: " + moves
        );

        if (!gameStarted) {

            statusLabel.setText("Status: Ready");

        } else if (!gameWon) {

            statusLabel.setText("Status: Playing");
        }
    }

    public void restartGame() {

        timer.stop();

        player.setPosition(
                maze.getStartRow(),
                maze.getStartCol()
        );

        gameWon = false;
        gameStarted = false;

        timeSeconds = 0;
        moves = 0;

        updateLabels();

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