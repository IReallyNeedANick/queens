package compileworks.queens.model;

import java.util.Objects;

/**
 * Object that keeps the coordinates of a queen on a chessboard
 */
public class QueenPosition {
    public final int x;
    public final int y;

    public QueenPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueenPosition)) return false;
        QueenPosition queenPosition = (QueenPosition) o;
        return x == queenPosition.x && y == queenPosition.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "QueenPosition{" + "x=" + x + ", y=" + y + '}';
    }
}
