package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Board {
    private int rows;
    private int columns;
    private int bombs;
    private int flagsLeft;
    private boolean gameOver = false;
    private boolean lost = false;
    private boolean boardInitialized = false;

    private Tile[][] grid;

    public Board(int rows, int columns, int bombs) {
        this.rows = rows;
        this.columns = columns;
        this.bombs = bombs;
        this.flagsLeft = bombs;

        this.grid = new Tile[columns][rows];

        for (int y = 0; y < this.rows; y++) {
            for (int x = 0; x < this.columns; x++) {
                Tile tile = new Tile(x, y);
                grid[x][y] = tile;
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getBombs() {
        return bombs;
    }

    public int getFlagsLeft() {
        return flagsLeft;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isLost() {
        return lost;
    }

    public boolean isBoardInitialized() {
        return boardInitialized;
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public Tile getTile(int x, int y) {
        return grid[x][y];
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public boolean winCheck() {
        for (Tile[] row: grid) {
            for (Tile tile: row) {
                if (!tile.isOpened() && !tile.hasBomb())
                    return false;
            }
        }

        return true;
    }

    public void fillBoard(Tile tile) {
        int i = bombs;

        while (i > 0) {
            int x = (int) (Math.random() * columns);
            int y = (int) (Math.random() * rows);

            if (!grid[x][y].hasBomb() && !(x == tile.getX() && y == tile.getY())) {
                grid[x][y].setBomb(true);
                i--;
            }
        }

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Tile emptyTile = grid[x][y];
                if (!emptyTile.hasBomb()) {
                    long bombs = getNeighbours(emptyTile)
                            .stream()
                            .filter(Tile::hasBomb)
                            .count();
                    emptyTile.setBombsAround((int) bombs);
                }
            }
        }

        boardInitialized = true;
    }

    public void openTile(Tile tile) {
        if (!boardInitialized) {
            fillBoard(tile);
        }

        if (tile.isFlagged() || isGameOver())
            return;

        if (!tile.isOpened()) {
            tile.setOpened(true);

            if (winCheck()) {
                setGameOver(true);
            }

            if (tile.hasBomb()) {
                setGameOver(true);
                setLost(true);
                return;
            }

            if (tile.getBombsAround() == 0) {
                getNeighbours(tile).forEach(this::openTile);
            }
        }
    }

    public void flagTile(Tile tile) {
        if (isGameOver() || !boardInitialized)
            return;

        if (!tile.isOpened()) {
            if (tile.isFlagged()) {
                tile.setFlag(false);
                flagsLeft++;
            } else {
                if (flagsLeft == 0)
                    return;
                tile.setFlag(true);
                flagsLeft--;
            }
        }
    }

    private List<Tile> getNeighbours(Tile tile) {
        List<Tile> neighbours = new ArrayList<>();

        int[][] pointsOddRow = new int[][]{
                {-1, 0},
                {-1, 1},
                {0, -1},
                {1, 0},
                {0, 1},
                {-1, -1}
        };

        int[][] pointsEvenRow = new int[][]{
                {-1, 0},
                {0, 1},
                {1, -1},
                {1, 0},
                {1, 1},
                {0, -1},
        };

        for (int i = 0; i < 6; i++) {
            int x = tile.getY() % 2 == 0
                    ? tile.getX() + pointsOddRow[i][0]
                    : tile.getX() + pointsEvenRow[i][0];
            int y = tile.getY() % 2 == 0
                    ? tile.getY() + pointsOddRow[i][1]
                    : tile.getY() + pointsEvenRow[i][1];

            if (x >= 0 && x < this.columns && y >= 0 && y < this.rows) {
                neighbours.add(grid[x][y]);
            }
        }

        return neighbours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return rows == board.rows &&
                columns == board.columns &&
                bombs == board.bombs &&
                flagsLeft == board.flagsLeft &&
                gameOver == board.gameOver &&
                lost == board.lost &&
                boardInitialized == board.boardInitialized &&
                Arrays.deepEquals(grid, board.grid);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows, columns, bombs, flagsLeft, gameOver, lost, boardInitialized);
        result = 31 * result + Arrays.deepHashCode(grid);
        return result;
    }
}
