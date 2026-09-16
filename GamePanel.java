package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
    private boolean paused = false;

    private int timeSeconds = 0;

    private Timer timer;

    private int moves = 0;

    private JLabel timeLabel;
    private JLabel movesLabel;
    private JLabel statusLabel;

    private JButton restartButton;
    private JButton pauseButton;

    private JComboBox<String> difficultyBox;

    private String difficulty;

    public GamePanel() {

        difficulty = "Easy";

        maze = new Maze(difficulty);

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
        pauseButton = new JButton("Pause");

        difficultyBox = new JComboBox<>(
                new String[]{"Easy", "Medium", "Hard"}
        );

        Font font = new Font("Arial", Font.BOLD, 13);

        timeLabel.setFont(font);
        movesLabel.setFont(font);
        statusLabel.setFont(font);
        restartButton.setFont(font);
        pauseButton.setFont(font);
        difficultyBox.setFont(font);

        difficultyBox.setSelectedItem(difficulty);

        difficultyBox.addActionListener(e -> {

            String selected =
                    (String) difficultyBox.getSelectedItem();

            if (!selected.equals(difficulty)) {

                difficulty = selected;

                restartGame();
            }
        });

        restartButton.setFocusable(false);

        restartButton.addActionListener(e -> restartGame());

        pauseButton.setFocusable(false);

        pauseButton.addActionListener(e -> togglePause());

        infoPanel.add(new JLabel("Difficulty:"));
        infoPanel.add(difficultyBox);
        infoPanel.add(timeLabel);
        infoPanel.add(movesLabel);
        infoPanel.add(statusLabel);
        infoPanel.add(pauseButton);
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

        if (paused) {

            g.setColor(Color.BLACK);

            g.setFont(new Font("Arial", Font.BOLD, 30));

            g.drawString(
                    "PAUSED",
                    maze.getCols() * TILE_SIZE / 2 - 60,
                    maze.getRows() * TILE_SIZE / 2
            );
        }

        if (gameWon) {

            g.setColor(Color.GREEN);

            g.setFont(new Font("Arial", Font.BOLD, 28));

            g.drawString(
                    "YOU WIN!",
                    maze.getCols() * TILE_SIZE / 2 - 65,
                    maze.getRows() * TILE_SIZE / 2
            );
        }
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

        if (e.getKeyCode() == KeyEvent.VK_P) {

            togglePause();

            return;
        }

        if (gameWon || paused) {

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

                pauseButton.setEnabled(false);

                repaint();

                java.awt.Toolkit.getDefaultToolkit().beep();

                JOptionPane.showMessageDialog(
                        this,
                        "Congratulations!\n"
                        + "You completed the "
                        + difficulty
                        + " maze!\n\n"
                        + "Time: "
                        + timeSeconds
                        + " seconds\n"
                        + "Moves: "
                        + moves
                        + "\n\n"
                        + "Click Restart to play again."
                );
            }
        }
    }

    private void togglePause() {

        if (gameWon) {
            return;
        }

        if (!gameStarted) {
            return;
        }

        if (!paused) {

            paused = true;

            timer.stop();

            pauseButton.setText("Resume");

            statusLabel.setText("Status: Paused");

        } else {

            paused = false;

            timer.start();

            pauseButton.setText("Pause");

            statusLabel.setText("Status: Playing");
        }

        repaint();
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

        } else if (paused) {

            statusLabel.setText("Status: Paused");

        } else if (!gameWon) {

            statusLabel.setText("Status: Playing");
        }
    }

    public void restartGame() {

        timer.stop();

        maze = new Maze(difficulty);

        player.setPosition(
                maze.getStartRow(),
                maze.getStartCol()
        );

        gameWon = false;
        gameStarted = false;
        paused = false;

        timeSeconds = 0;
        moves = 0;

        pauseButton.setText("Pause");
        pauseButton.setEnabled(true);

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