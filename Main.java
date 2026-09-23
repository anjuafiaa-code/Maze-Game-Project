package game;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        JFrame frame =
                new JFrame("Maze Adventure");

        GamePanel gamePanel =
                new GamePanel();

        frame.add(gamePanel);

        frame.setSize(900, 700);

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setLocationRelativeTo(null);

        frame.setResizable(false);

        frame.setVisible(true);

        gamePanel.requestFocusInWindow();
    }
}