import model.Board;
import model.Tile;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestModel {

//For Tile:
    @Test
    void getX() {
        Tile tile = new Tile(10, 15);
        assertEquals(10, tile.getX());
    }

    @Test
    void getY() {
        Tile tile = new Tile(10, 15);
        assertEquals(15, tile.getY());
    }

    @Test
    void getBombsAround() {
        Tile tile = new Tile(10, 15);
        tile.setBombsAround(5);
        assertEquals(5, tile.getBombsAround());
    }

    @Test
    void isOpened() {
        Tile tile = new Tile(10, 15);
        tile.setOpened(true);
        assertTrue(tile.isOpened());
    }

    @Test
    void isFlagged() {
        Tile tile = new Tile(10, 15);
        tile.setFlag(true);
        assertTrue(tile.isFlagged());
    }

    @Test
    void hasBomb() {
        Tile tile = new Tile(10, 15);
        tile.setBomb(true);
        assertTrue(tile.hasBomb());
    }

    @Test
    void testEquals() {
        Tile tile1 = new Tile(10, 15);
        Tile tile2 = new Tile(10, 20);
        Tile tile3 = new Tile(10, 15);
        assertEquals(tile1, tile3);
        tile1.setFlag(true);
        assertNotEquals(tile1, tile3);
        assertNotEquals(tile2, tile3);
    }

    @Test
    void testHashCode() {
        Tile tile1 = new Tile(10, 15);
        Tile tile2 = new Tile(10, 20);
        Tile tile3 = new Tile(10, 15);
        assertEquals(tile1.hashCode(), tile3.hashCode());
        tile1.setFlag(true);
        assertNotEquals(tile1.hashCode(), tile3.hashCode());
        assertNotEquals(tile2.hashCode(), tile3.hashCode());
    }
//For Board:
    @Test
    void getRows() {
        Board board = new Board(10, 20, 30);
        assertEquals(10, board.getRows());
    }

    @Test
    void getColumns() {
        Board board = new Board(10, 20, 30);
        assertEquals(20, board.getColumns());
    }

    @Test
    void getBombs() {
        Board board = new Board(10, 20, 30);
        assertEquals(30, board.getBombs());
    }

    @Test
    void getFlagsLeft() {
        Board board = new Board(10, 20, 30);
        assertEquals(30, board.getFlagsLeft());
    }

    @Test
    void isGameOver() {
        Board board = new Board(10, 20, 30);
        board.setGameOver(true);
        assertTrue(board.isGameOver());
    }

    @Test
    void isLost() {
        Board board = new Board(10, 20, 30);
        board.setLost(true);
        assertTrue(board.isLost());
    }

    @Test
    void isBoardInitialized() {
        Board board = new Board(10, 20, 30);
        board.fillBoard(board.getTile(0,0));
        assertTrue(board.isBoardInitialized());
    }

    @Test
    void getGrid() {
        Board board = new Board(1, 3, 0);
        Tile tile1 = new Tile(0, 0);
        Tile tile2 = new Tile(1, 0);
        Tile tile3 = new Tile(2, 0);
        board.fillBoard(new Tile(0, 0));

        Tile[][] expected = new Tile[][] {{tile1}, {tile2}, {tile3}};
        assertTrue(Arrays.deepEquals(expected, board.getGrid()));
    }

    @Test
    void getTile() {
        Board board = new Board(10, 20, 30);
        Tile tile = new Tile(9, 9);
        assertEquals(tile, board.getTile(9, 9));
    }

    @Test
    void openTile() {
        Board board = new Board(10, 20, 30);
        Tile tile = new Tile(9, 9);
        assertFalse(tile.isOpened());
        board.openTile(tile);
        assertTrue(tile.isOpened());
    }

    @Test
    void fillBoard() {
        Board board1 = new Board(10, 20, 30);
        Board board2 = new Board(10, 20, 30);
        assertEquals(board1, board2);
        board1.fillBoard(board1.getTile(9, 9));
        assertNotEquals(board1, board2);
    }

    @Test
    void winCheck() {
        Board board = new Board(10, 20, 30);
        assertFalse(board.winCheck());
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (!board.getGrid()[i][j].hasBomb()) {
                    board.openTile(board.getTile(i, j));
                }
            }
        }
        assertTrue(board.winCheck());
    }

    @Test
    void flagTile() {
        Board board = new Board(10, 20, 30);
        assertFalse(board.getTile(9, 9).isFlagged());

        board.fillBoard(board.getTile(9, 9));
        board.flagTile(board.getTile(9, 9));
        assertTrue(board.getTile(9, 9).isFlagged());
    }

    @Test
    void testEquals2() {
        Board board1 = new Board(10, 20, 30);
        Board board2 = new Board(10, 20, 30);
        Board board3 = new Board(15, 20, 30);
        assertEquals(board1, board2);
        assertNotEquals(board1, board3);
    }

    @Test
    void testHashCode2() {
        Board board1 = new Board(10, 20, 30);
        Board board2 = new Board(10, 20, 30);
        Board board3 = new Board(15, 20, 30);
        assertEquals(board1.hashCode(), board2.hashCode());
        assertNotEquals(board1.hashCode(), board3.hashCode());
    }
}