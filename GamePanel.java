package game;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
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

    private boolean gameWon = false;
    private boolean gameStarted = false;

    private int timeSeconds = 0;

    private Timer timer;

    private int moves = 0;

    private JLabel timeLabel;
    private JLabel movesLabel;
    private JLabel statusLabel;

    private JButton restartButton;

    private JComboBox<String> difficultyBox;

    private String difficulty;

    private Color backgroundColor = new Color(15, 23, 42);
    private Color panelColor = new Color(30, 41, 59);
    private Color wallColor = new Color(37, 99, 235);
    private Color wallBorder = new Color(96, 165, 250);
    private Color pathColor = new Color(241, 245, 249);
    private Color startColor = new Color(251, 191, 36);
    private Color exitColor = new Color(34, 197, 94);
    private Color playerColor = new Color(239, 68, 68);

    public GamePanel() {

        difficulty = "Easy";

        maze = new Maze(difficulty);

        player = new Player(
                maze.getStartRow(),
                maze.getStartCol()
        );

        setLayout(new BorderLayout());

        setBackground(backgroundColor);

        JPanel infoPanel = new JPanel(
                new BorderLayout()
        );

        infoPanel.setBackground(panelColor);

        infoPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );

        JPanel leftPanel = new JPanel();

        leftPanel.setBackground(panelColor);

        JPanel rightPanel = new JPanel();

        rightPanel.setBackground(panelColor);

        JLabel titleLabel = new JLabel(
                "  MAZE ADVENTURE  "
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        titleLabel.setForeground(Color.WHITE);

        JLabel difficultyText = new JLabel(
                "Difficulty"
        );

        difficultyText.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        difficultyText.setForeground(
                new Color(203, 213, 225)
        );

        difficultyBox = new JComboBox<>(
                new String[]{
                    "Easy",
                    "Medium",
                    "Hard",
                    "Extra Hard"
                }
        );

        difficultyBox.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        difficultyBox.setFocusable(false);

        difficultyBox.setBackground(Color.WHITE);

        timeLabel = new JLabel(
                "Time: 0 sec"
        );

        movesLabel = new JLabel(
                "Moves: 0"
        );

        statusLabel = new JLabel(
                "Status: Ready"
        );

        Font infoFont =
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                );

        timeLabel.setFont(infoFont);

        movesLabel.setFont(infoFont);

        statusLabel.setFont(infoFont);

        timeLabel.setForeground(
                new Color(125, 211, 252)
        );

        movesLabel.setForeground(
                new Color(196, 181, 253)
        );

        statusLabel.setForeground(
                new Color(134, 239, 172)
        );

        restartButton = new JButton(
                "Restart"
        );

        restartButton.setFont(infoFont);

        restartButton.setFocusable(false);

        restartButton.setBackground(
                new Color(59, 130, 246)
        );

        restartButton.setForeground(Color.WHITE);

        restartButton.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        15,
                        8,
                        15
                )
        );

        leftPanel.add(titleLabel);

        leftPanel.add(difficultyText);

        leftPanel.add(difficultyBox);

        rightPanel.add(timeLabel);

        rightPanel.add(movesLabel);

        rightPanel.add(statusLabel);

        rightPanel.add(restartButton);

        infoPanel.add(
                leftPanel,
                BorderLayout.WEST
        );

        infoPanel.add(
                rightPanel,
                BorderLayout.EAST
        );

        add(
                infoPanel,
                BorderLayout.NORTH
        );

        setFocusable(true);

        addKeyListener(this);

        difficultyBox.addActionListener(e -> {

            String selected =
                    (String) difficultyBox
                            .getSelectedItem();

            if (!selected.equals(difficulty)) {

                difficulty = selected;

                restartGame();
            }
        });

        restartButton.addActionListener(e ->
                restartGame()
        );

        timer = new Timer(
                1000,
                e -> {

                    timeSeconds++;

                    updateLabels();
                }
        );
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 =
                (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int availableWidth = getWidth();

        int availableHeight =
                getHeight() - 80;

        int tileWidth =
                availableWidth / maze.getCols();

        int tileHeight =
                availableHeight / maze.getRows();

        int tileSize =
                Math.min(
                        tileWidth,
                        tileHeight
                );

        if (tileSize < 1) {

            g2.dispose();

            return;
        }

        int mazeWidth =
                maze.getCols() * tileSize;

        int mazeHeight =
                maze.getRows() * tileSize;

        int startX =
                (availableWidth - mazeWidth) / 2;

        int startY =
                80
                + (availableHeight - mazeHeight) / 2;

        g2.setColor(
                new Color(15, 23, 42)
        );

        g2.fillRoundRect(
                startX - 10,
                startY - 10,
                mazeWidth + 20,
                mazeHeight + 20,
                20,
                20
        );

        for (
                int row = 0;
                row < maze.getRows();
                row++
        ) {

            for (
                    int col = 0;
                    col < maze.getCols();
                    col++
            ) {

                int cell =
                        maze.getCell(
                                row,
                                col
                        );

                int x =
                        startX
                        + col * tileSize;

                int y =
                        startY
                        + row * tileSize;

                if (cell == 1) {

                    GradientPaint wallGradient =
                            new GradientPaint(
                                    x,
                                    y,
                                    wallColor,
                                    x + tileSize,
                                    y + tileSize,
                                    new Color(
                                            30,
                                            64,
                                            175
                                    )
                            );

                    g2.setPaint(wallGradient);

                    g2.fillRoundRect(
                            x + 1,
                            y + 1,
                            tileSize - 2,
                            tileSize - 2,
                            6,
                            6
                    );

                    g2.setColor(wallBorder);

                    g2.drawRoundRect(
                            x + 1,
                            y + 1,
                            tileSize - 2,
                            tileSize - 2,
                            6,
                            6
                    );

                } else {

                    g2.setColor(pathColor);

                    g2.fillRect(
                            x,
                            y,
                            tileSize,
                            tileSize
                    );

                    g2.setColor(
                            new Color(
                                    226,
                                    232,
                                    240
                            )
                    );

                    g2.drawRect(
                            x,
                            y,
                            tileSize,
                            tileSize
                    );
                }
            }
        }

        int startXCell =
                startX
                + maze.getStartCol()
                * tileSize;

        int startYCell =
                startY
                + maze.getStartRow()
                * tileSize;

        g2.setColor(startColor);

        g2.fillRoundRect(
                startXCell + 3,
                startYCell + 3,
                tileSize - 6,
                tileSize - 6,
                10,
                10
        );

        g2.setColor(
                new Color(
                        120,
                        53,
                        15
                )
        );

        if (tileSize >= 18) {

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            Math.max(
                                    9,
                                    tileSize / 4
                            )
                    )
            );

            g2.drawString(
                    "START",
                    startXCell
                    + tileSize / 6,
                    startYCell
                    + tileSize / 2
            );
        }

        int exitX =
                startX
                + maze.getExitCol()
                * tileSize;

        int exitY =
                startY
                + maze.getExitRow()
                * tileSize;

        g2.setColor(exitColor);

        g2.fillRoundRect(
                exitX + 3,
                exitY + 3,
                tileSize - 6,
                tileSize - 6,
                10,
                10
        );

        g2.setColor(Color.WHITE);

        if (tileSize >= 18) {

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            Math.max(
                                    9,
                                    tileSize / 4
                            )
                    )
            );

            g2.drawString(
                    "EXIT",
                    exitX + tileSize / 5,
                    exitY + tileSize / 2
            );
        }

        int playerX =
                startX
                + player.getCol()
                * tileSize;

        int playerY =
                startY
                + player.getRow()
                * tileSize;

        int playerSize =
                Math.max(
                        8,
                        tileSize - 8
                );

        int circleX =
                playerX
                + (tileSize - playerSize) / 2;

        int circleY =
                playerY
                + (tileSize - playerSize) / 2;

        g2.setColor(
                new Color(
                        127,
                        29,
                        29
                )
        );

        g2.fillOval(
                circleX + 2,
                circleY + 3,
                playerSize,
                playerSize
        );

        g2.setColor(playerColor);

        g2.fillOval(
                circleX,
                circleY,
                playerSize,
                playerSize
        );

        g2.setColor(Color.WHITE);

        if (playerSize >= 15) {

            int eyeSize =
                    Math.max(
                            2,
                            playerSize / 8
                    );

            g2.fillOval(
                    circleX
                    + playerSize / 3,
                    circleY
                    + playerSize / 3,
                    eyeSize,
                    eyeSize
            );

            g2.fillOval(
                    circleX
                    + playerSize / 2,
                    circleY
                    + playerSize / 3,
                    eyeSize,
                    eyeSize
            );
        }

        if (gameWon) {

            g2.setColor(
                    new Color(
                            6,
                            78,
                            59,
                            210
                    )
            );

            g2.fillRoundRect(
                    startX,
                    startY,
                    mazeWidth,
                    mazeHeight,
                    15,
                    15
            );

            g2.setColor(
                    new Color(
                            134,
                            239,
                            172
                    )
            );

            g2.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            Math.max(
                                    25,
                                    tileSize * 2
                            )
                    )
            );

            String text =
                    "YOU WIN!";

            int textWidth =
                    g2.getFontMetrics()
                            .stringWidth(text);

            g2.drawString(
                    text,
                    startX
                    + (mazeWidth - textWidth)
                    / 2,
                    startY
                    + mazeHeight / 2
            );
        }

        g2.dispose();
    }

    private boolean canMove(
            int newRow,
            int newCol
    ) {

        if (
                newRow < 0
                || newRow >= maze.getRows()
                || newCol < 0
                || newCol >= maze.getCols()
        ) {

            return false;
        }

        return maze.getCell(
                newRow,
                newCol
        ) == 0;
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

        int newRow =
                player.getRow();

        int newCol =
                player.getCol();

        if (
                e.getKeyCode()
                == KeyEvent.VK_UP
        ) {

            newRow--;

        } else if (
                e.getKeyCode()
                == KeyEvent.VK_DOWN
        ) {

            newRow++;

        } else if (
                e.getKeyCode()
                == KeyEvent.VK_LEFT
        ) {

            newCol--;

        } else if (
                e.getKeyCode()
                == KeyEvent.VK_RIGHT
        ) {

            newCol++;

        } else {

            return;
        }

        if (
                canMove(
                        newRow,
                        newCol
                )
        ) {

            if (!gameStarted) {

                gameStarted = true;

                timer.start();

                statusLabel.setText(
                        "Status: Playing"
                );
            }

            player.setPosition(
                    newRow,
                    newCol
            );

            moves++;

            updateLabels();

            repaint();

            if (
                    newRow
                    == maze.getExitRow()
                    && newCol
                    == maze.getExitCol()
            ) {

                gameWon = true;

                timer.stop();

                statusLabel.setText(
                        "Status: Won"
                );

                repaint();

                int choice =
                        JOptionPane.showOptionDialog(
                                this,
                                "Congratulations!\n\n"
                                + "You completed the "
                                + difficulty
                                + " maze!\n\n"
                                + "Time: "
                                + timeSeconds
                                + " seconds\n"
                                + "Moves: "
                                + moves
                                + "\n\n"
                                + "Click Restart to play again.",
                                "Maze Completed",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                new Object[]{
                                    "Restart"
                                },
                                "Restart"
                        );

                if (choice == 0) {

                    restartGame();
                }
            }
        }
    }

    private void updateLabels() {

        timeLabel.setText(
                "Time: "
                + timeSeconds
                + " sec"
        );

        movesLabel.setText(
                "Moves: "
                + moves
        );

        if (!gameStarted) {

            statusLabel.setText(
                    "Status: Ready"
            );

        } else if (!gameWon) {

            statusLabel.setText(
                    "Status: Playing"
            );
        }
    }

    public void restartGame() {

        timer.stop();

        maze =
                new Maze(difficulty);

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