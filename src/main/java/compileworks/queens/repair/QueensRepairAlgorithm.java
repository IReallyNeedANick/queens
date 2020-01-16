package compileworks.queens.repair;

import compileworks.queens.model.ChessboardData;
import compileworks.queens.util.QueensUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.List;


/**
 *  This is an implementation of a n queens problem solution algorithm that deals with iterative repair.
 *  It is actually very efficient if you dont want to also solve three in line problem, otherwise it is
 *  worse than the backtracking solution.
 *
 *  when you move the queen only the conflict for moved queen is calculated and all states are stored in ChessboardData object
 *
 * @author Boris Marn
 */
public class QueensRepairAlgorithm {

    private final int boardSize;
    private final boolean printSolution;
    private final boolean printDuration;
    private final boolean solveNInLine;
    private final long infinityCheck;

    public QueensRepairAlgorithm(int boardSize,
                                 boolean printSolution,
                                 boolean printDuration,
                                 boolean solveNInLine) {
        this.boardSize = boardSize;
        this.printSolution = printSolution;
        this.printDuration = printDuration;
        this.solveNInLine = solveNInLine;
        this.infinityCheck = boardSize > Math.cbrt(Long.MAX_VALUE) ? Long.MAX_VALUE : (long) boardSize * boardSize * boardSize;
    }


    public int[] findSolution() {
        if (boardSize < 4 || (boardSize >= 5 && boardSize < 8 && solveNInLine)) {
            System.out.println("no solution exists for given criteria");
            return null;
        }

        Instant start = Instant.now();

        int[] chessBoard = calcQueens();
        if (printSolution) {
            System.out.println();
            System.out.println("solution:");
            QueensUtil.printSolution(chessBoard);
        }

        if (printDuration) {
            Instant end = Instant.now();
            System.out.println("Solution for iterative repair algorithm found in: " + Duration.between(start, end));
        }
        return chessBoard;
    }

    private int[] calcQueens() {
        long loopCount = 0;
        ChessboardData chessboardData = new ChessboardData(boardSize, solveNInLine);
        while (true) {
            if (loopCount++ > infinityCheck) {
                loopCount = infinityCheck;
                chessboardData = new ChessboardData(boardSize, solveNInLine);
            }

            List<Integer> maxConflictIndexes = chessboardData.getMaxConflictIndexes();

            boolean queenPlaced = false;
            for (Integer ofMax : maxConflictIndexes) {

                int value = chessboardData.getNumberOfConflicts(ofMax);
                if (value == 0) {
                    return chessboardData.convertToBoard();
                } else {
                    queenPlaced = chessboardData.findAndPlaceOnBetterPosition(ofMax);
                    if (queenPlaced) {
                        break;
                    }
                }
            }
            if (!queenPlaced) {
                loopCount = 0;
                chessboardData = new ChessboardData(boardSize, solveNInLine);
            }
        }
    }
}
