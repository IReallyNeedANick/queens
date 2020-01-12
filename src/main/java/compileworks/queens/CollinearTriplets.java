package compileworks.queens;

/**
 * @author Boris Marn
 */

import java.util.*;
import java.util.stream.Collectors;

public class CollinearTriplets {

    public static void main(String[] args) {
        Point2d A[] = new Point2d[8];
        A[0] = new Point2d(0, 0);
        A[1] = new Point2d(1, 1);
        A[2] = new Point2d(1, 2);
        A[3] = new Point2d(3, 3);
        A[4] = new Point2d(3, 2);
        A[5] = new Point2d(4, 2);
        A[6] = new Point2d(5, 1);
        A[7] = new Point2d(2, 4);

        System.out.println(countCollinear(A));
    }

    public static List<Set<Point2d>> countCollinear(int[] chessBoard) {
        Point2d[] points = new Point2d[chessBoard.length];
        for (int i = 0 ; i< chessBoard.length; i++ ) {
            points[i] = new Point2d(i, chessBoard[i]);
        }
        return CollinearTriplets.countCollinear(points);
    }

    private static List<Set<Point2d>> countCollinear(Point2d[] points) {
        Map<Line, Set<Point2d>> lineToPoints = new HashMap<>();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].y != points[j].y &&
                        points[i].x != points[j].x
                        &&

                        points[j].y - points[j].x != points[i].y - points[i].x &&
                        points[j].y + points[j].x != points[i].y + points[i].x
                ) {
//                    System.out.println(j+"  " +i +  "  "+ points[i].y  +" " +  points[i].x +" "+ points[j].y +" "+ points[j].x);
                    double slope, xIntercept, yIntercept; // Default slope paralell to y-axis
                    xIntercept = points[j].x - points[i].x;
                    yIntercept = points[j].y - points[i].y;
                    slope = yIntercept / xIntercept;
                    Line currLine = new Line(points[i], slope);
                    if (Objects.isNull(lineToPoints.get(currLine))) {
                        lineToPoints.put(currLine, new HashSet<>());
                    }
                    lineToPoints.get(currLine).add(points[i]);
                    lineToPoints.get(currLine).add(points[j]);
                }
            }

        }
        return lineToPoints.values()
                .stream()
                .filter(point2ds -> point2ds.size() >= 3)
                .collect(Collectors.toList());
    }

    /**
     * Line which contains the starting point and slope so that you can identify exact line
     * equals method is overridden to check whether any new line is coinciding or parallel
     */
    static class Line {
        Point2d point;
        double slope;

        public Line(Point2d point, double slope) {
            this.point = point;
            this.slope = slope;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Line)) return false;
            Line line = (Line) o;

            if (line.slope == this.slope)
                return ((((double) (line.point.y - this.point.y)) / (line.point.x - this.point.x)) == this.slope);
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(slope);
        }
    }

    static class Point2d {
        int x;
        int y;

        public Point2d(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point2d)) return false;
            Point2d point2d = (Point2d) o;
            return x == point2d.x &&
                    y == point2d.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point2d{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
