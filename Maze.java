package maze;

public class Maze {

    private final int[][] maze = {

        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 1, 0, 1, 1, 1, 1},
        {1, 1, 1, 0, 1, 0, 1, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 1, 0, 1, 1},
        {1, 0, 1, 1, 1, 0, 1, 0, 1, 1},
        {1, 0, 0, 0, 0, 0, 1, 0, 1, 1},
        {1, 1, 1, 1, 1, 0, 0, 0, 1, 1},
        {1, 0, 0, 0, 0, 0, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public int[][] getMaze() {
        return maze;
    }

    public int getRows() {
        return maze.length;
    }

    public int getCols() {
        return maze[0].length;
    }

    public int getCell(int row, int col) {
        return maze[row][col];
    }

    public int getStartRow() {
        return 1;
    }

    public int getStartCol() {
        return 1;
    }

    public int getExitRow() {
        return 8;
    }

    public int getExitCol() {
        return 8;
    }
}