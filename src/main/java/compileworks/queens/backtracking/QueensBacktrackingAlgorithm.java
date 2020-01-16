package compileworks.queens.backtracking;

import compileworks.queens.model.Line;
import compileworks.queens.model.QueenPosition;
import compileworks.queens.util.QueensUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * This is a simple implementation of a backtracking algorithm for solving n-queens problem (also three in line problem)
 * There are 2 notable differences that make this algorithm a little faster:
 *   - initial board is shuffled and already created in a way that no queens attacks other queen on x and y axis
 *   - elements are always swapped so the consequence is that you don't have to check for y and x attacks
 *
 * @author Boris Marn
 */
public class QueensBacktrackingAlgorithm {

    private final int boardSize;
    private final boolean printSolution;
    private final boolean printDuration;
    private final boolean solveNInLine;

    public QueensBacktrackingAlgorithm(int boardSize,
                                       boolean printSolution,
                                       boolean printDuration,
                                       boolean solveNInLine) {
        this.boardSize = boardSize;
        this.printSolution = printSolution;
        this.printDuration = printDuration;
        this.solveNInLine = solveNInLine;
    }

    public int[] findSolution() {
        Instant start = Instant.now();

//        int[] chessBoard = QueensUtil.findNaive(board);
        int[] chessBoard = QueensUtil.createShuffledBoard(boardSize);

        if (calculateSolution(chessBoard, 0)) {
            if (printSolution) {
                System.out.println("Solution:");
                QueensUtil.printSolution(chessBoard);
            }
            if (printDuration) {
                Instant end = Instant.now();
                System.out.println("Solution for backtracking algorithm found in: " + Duration.between(start, end));
            }
        } else {
            System.out.println("no solution found");
        }

        return chessBoard;
    }


    private boolean calculateSolution(int[] chessBoard, int column) {
        Set<Integer> testedYPositions = new HashSet<>();

        if (column >= boardSize) {
            return true;
        }
        while (testedYPositions.size() < boardSize - column) {
            boolean elementSwapped = false;
            if (testedYPositions.contains(chessBoard[column])) {
                for (int i = column + 1; i < boardSize; i++) {
                    if (!testedYPositions.contains(chessBoard[i])) {
                        if (column == 0 || isPlacementSafe(chessBoard, column, chessBoard[i])) {
                            QueensUtil.swapElement(chessBoard, column, i);
                            elementSwapped = true;
                            break;
                        } else {
                            testedYPositions.add(chessBoard[i]);
                        }
                    }
                }
                if (!elementSwapped) {
                    return false;
                }
            }
            boolean safe = elementSwapped || column == 0 || isPlacementSafe(chessBoard, column, chessBoard[column]);

            if (safe && calculateSolution(chessBoard, column + 1)) {
                return true;
            } else {
                testedYPositions.add(chessBoard[column]);
            }
        }
        return false;
    }

    private boolean isPlacementSafe(int[] chessBoard, int x, int y) {
        //diagonals
        for (int i = 0; i < x; i++) {
            if (i + chessBoard[i] == x + y || chessBoard[i] - i == y - x) {
                return false;
            }
        }

        if (x >= 2 && solveNInLine) {
            return isQueenNInLine(chessBoard, x, y);
        } else {
            return true;
        }
    }

    public static boolean isQueenNInLine(int[] chessBoard, int x, int y) {
        QueenPosition[] positions = QueensUtil.convertToPositions(chessBoard, x);
        Map<Line, Set<QueenPosition>> lineToPositions = new HashMap<>();
        QueenPosition lastPosition = new QueenPosition(x, y);

        for (int i = 0; i < x; i++) {
            double slope, xIntercept, yIntercept;
            xIntercept = lastPosition.x - positions[i].x;
            yIntercept = lastPosition.y - positions[i].y;
            slope = yIntercept / xIntercept;
            Line currLine = new Line(positions[i], slope);
            if (Objects.isNull(lineToPositions.get(currLine))) {
                lineToPositions.put(currLine, new HashSet<>());
            } else {
                return false;
            }
            lineToPositions.get(currLine).add(positions[i]);
            lineToPositions.get(currLine).add(lastPosition);

        }
        return true;
    }
}
