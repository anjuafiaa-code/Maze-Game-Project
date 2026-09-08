package game;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Maze Game");

        GamePanel gamePanel = new GamePanel();

        frame.add(gamePanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}