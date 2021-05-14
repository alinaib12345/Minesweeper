package model;

import java.util.Objects;

public class Tile {
    private int x, y;
    private int bombsAround = 0;
    private boolean hasBomb = false;
    private boolean isOpened = false;
    private boolean isFlagged = false;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBombsAround() {
        return bombsAround;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public void setBombsAround(int bombs) {
        bombsAround = bombs;
    }

    public void setOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }

    public void setFlag(boolean hasFlag) {
        this.isFlagged = hasFlag;
    }

    public void setBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return x == tile.x &&
                y == tile.y &&
                bombsAround == tile.bombsAround &&
                hasBomb == tile.hasBomb &&
                isOpened == tile.isOpened &&
                isFlagged == tile.isFlagged;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, bombsAround, hasBomb, isOpened, isFlagged);
    }
}