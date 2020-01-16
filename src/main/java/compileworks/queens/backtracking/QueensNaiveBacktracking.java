package compileworks.queens.backtracking;

import compileworks.queens.util.QueensUtil;

import java.time.Duration;
import java.time.Instant;

/**
 * this is the total first algorithm I created and was not checked much and should be treated as such :)
 */
@Deprecated
public class QueensNaiveBacktracking {

    static int H = 0;
    static int SAFE = 0;

    private boolean isSafe(boolean[][] chessBoard, int x, int y) {
        SAFE++;
        //upper
        for (int i = 0; i < y; i++) {
            if (chessBoard[x][i]) {
                return false;
            }
        }
        //diagonal left
        int i = x - 1, j = y - 1;
        while (i >= 0 && j >= 0) {
            if (chessBoard[i][j]) {
                return false;
            }
            i--;
            j--;
        }
        //diagonal right
        i = x + 1;
        j = y - 1;
        while (i < chessBoard.length && j >= 0) {
            if (chessBoard[i][j]) {
                return false;
            }
            i++;
            j--;
        }

//         three queens in straight line
        int barrierLeft = y / 2;
        int barrierUp = x - (x / 2);
        int barrierDown = (x + chessBoard.length - 1) / 2;
        for (int k = y - barrierLeft; k <= y; k++) {
            for (int l = barrierUp; l <= barrierDown; l++) {
                if (chessBoard[l][k]) {
                    if (thirdPointExists(chessBoard, x, y, l, k)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean thirdPointExists(boolean[][] chessBoard, int x, int y, int x1, int y1) {
        int vectorx = x - x1, vectory = y - y1;
        int x2 = x1 - vectorx, y2 = y1 - vectory;
        while (x2 >= 0 && y2 >= 0 && x2 < chessBoard.length) {
            if (chessBoard[x2][y2]) {
                return true;
            }
            x2 = x2 - vectorx;
            y2 = y2 - vectory;
        }
        return false;
    }


    public void calculateAndPrint(int board, boolean printSolution, boolean printMetrics) {
        Instant start = Instant.now();
        boolean[][] chessBoard = new boolean[board][board];

        boolean calcRecursive = calcRecursive(chessBoard, 0);

        if (printSolution) {
            if (calcRecursive) {
                QueensUtil.printSolution(chessBoard);

            } else {
                System.out.println("no solution found");
            }
        }
        if (printMetrics) {
            Instant end = Instant.now();
            System.out.println(Duration.between(start, end)); // prints PT1M3.553S

            System.out.println("backtracking algorithm called = " + H);
            System.out.println("checking if it is safe called = " + SAFE);
        }

    }


    private boolean calcRecursive(boolean[][] chessBoard, int column) {
        H++;
        if (column >= chessBoard.length) {
            return true;
        }

        for (int i = 0; i < chessBoard.length; i++) {
            if (isSafe(chessBoard, i, column)) {
                chessBoard[i][column] = true;
                if (calcRecursive(chessBoard, column + 1)) {
                    return true;
                } else {
                    chessBoard[i][column] = false;
                }
            }
        }
        return false;
    }
}

