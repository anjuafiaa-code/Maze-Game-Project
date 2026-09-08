package maze;

public class Maze {

    private final int[][] maze = {

        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 1, 0, 1, 1, 1, 1},
        {1, 0, 1, 0, 1, 0, 1, 0, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 0, 1, 1},
        {1, 0, 1, 1, 1, 0, 1, 0, 1, 1},
        {1, 0, 0, 1, 0, 0, 1, 0, 1, 1},
        {1, 1, 1, 1, 1, 0, 0, 0, 1, 1},
        {1, 1, 0, 0, 0, 0, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

 
    private final int startRow = 1;
    private final int startCol = 1;

    
    private final int exitRow = 8;
    private final int exitCol = 8;

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
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getExitRow() {
        return exitRow;
    }

    public int getExitCol() {
        return exitCol;
    }
}