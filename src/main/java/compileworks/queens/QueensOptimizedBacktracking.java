package compileworks.queens;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Boris Marn
 */
public class QueensOptimizedBacktracking {

    private boolean isSafe(int[] chessBoard, int x, int y) {
        SAFE++;
        //diagonals
        for (int i = 0; i < y; i++) {
            if (i + chessBoard[i] == x + y || chessBoard[i] - i == x - y) {
                return false;
            }
        }

        int barrierDown = y - (y / 2);
        int barrierLeft = x - (x / 2);
        int barrierRight = (x + chessBoard.length - 1) / 2;

        for (int i = barrierDown; i < y; i++) {
            // is part of barrier
            if (chessBoard[i] >= barrierLeft || chessBoard[i] <= barrierRight) {
                //third point exists
                if (thirdPointExists(chessBoard, x, y, chessBoard[i], i)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean thirdPointExists(int[] chessBoard, int x, int y, int x1, int y1) {
        int vectorx = x - x1, vectory = y - y1;
        int x2 = x1 - vectorx, y2 = y1 - vectory;
        while (x2 >= 0 && y2 >= 0 && x2 < chessBoard.length) {
            if (chessBoard[y2] == x2) {
                return true;
            }
            x2 = x2 - vectorx;
            y2 = y2 - vectory;
        }
        return false;
    }

    public int[] calculateAndPrint(int board, boolean printSolution, boolean printMetrics) {
        Instant start = Instant.now();
//        int[] chessBoard = NQueens.findNaive(board);

        int[] chessBoard = new int[board];
//
        for (int i = 0; i < board; i++) {
            chessBoard[i] = i;
        }
        QueenUtil.shuffleArray(chessBoard);

        if (calcRecursive(chessBoard, 0, new HashSet<>(chessBoard.length))) {
            if (printSolution) {
                System.out.println();
                System.out.print("solution:");
                QueenUtil.printSolution(chessBoard);
            }
        } else {
            System.out.println("no solution found");
        }

        if (printMetrics) {
            Instant end = Instant.now();
            System.out.println();
            System.out.println(Duration.between(start, end)); // prints PT1M3.553S

            System.out.println("backtracking algorithm called = " + H);
            System.out.println("checking if it is safe called = " + SAFE);
        }
        return chessBoard;
    }

    static int H = 0;
    static int SAFE = 0;

    private boolean calcRecursive(int[] chessBoard, int column, Set<Integer> testedElements) {
        H++;
        if (column >= chessBoard.length) {
            return true;
        }
        while (testedElements.size() < chessBoard.length - column) {
            boolean swapSuccessfull = false;
            if (testedElements.contains(chessBoard[column])) {
                for (int i = column + 1; i < chessBoard.length; i++) {
                    if (!testedElements.contains(chessBoard[i])) {
                        if ( column == 0 || isSafe(chessBoard, chessBoard[i], column)) {
                            swap(chessBoard, column, i);
                            swapSuccessfull = true;
                            break;
                        } else {
                            testedElements.add(chessBoard[i]);
                        }
                    }
                }
                if (!swapSuccessfull) {
                    return false;
                }
            }
            boolean safe = swapSuccessfull || column == 0 || isSafe(chessBoard, chessBoard[column], column);
            if (safe && calcRecursive(chessBoard, column + 1, new HashSet<>())) {
                return true;
            } else {
                testedElements.add(chessBoard[column]);
            }
        }
        return false;
    }


    private void swap(int[] chessBoard, int a, int b) {
        int a_temp = chessBoard[a];
        chessBoard[a] = chessBoard[b];
        chessBoard[b] = a_temp;
    }

}
