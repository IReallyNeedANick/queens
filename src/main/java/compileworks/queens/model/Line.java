package compileworks.queens.model;

import java.util.Objects;

/**
 * Line which contains the starting point and slope so that you can identify exact line
 * equals method is overridden to check whether any new line is coinciding or parallel
 */
public class Line {
    public final QueenPosition position;
    public final double slope;

    public Line(QueenPosition position, double slope) {
        this.position = position;
        this.slope = slope;
    }

    public boolean isQueenOnLine(QueenPosition position) {
        return ((((double) (position.y - this.position.y)) / (position.x - this.position.x)) == this.slope);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;

        if (line.slope == this.slope)
            return ((((double) (line.position.y - this.position.y)) / (line.position.x - this.position.x)) == this.slope);
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(slope);
    }
}
