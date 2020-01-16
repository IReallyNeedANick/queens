package compileworks.queens.backtracking;

import compileworks.queens.model.ChessboardData;
import compileworks.queens.util.QueensUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Boris Marn
 */

public class QueensBacktrackingAlgorithmTest {
    @Test
    public void testNonThreeInLineSolution() {
        int boardSize = 10;
        QueensBacktrackingAlgorithm algorithm = new QueensBacktrackingAlgorithm(boardSize, false, false, false);
        for (int i = 0; i < 100; i++) {
            int[] solution = algorithm.findSolution();
            ChessboardData chessboardData = new ChessboardData(10, false, QueensUtil.convertToPositions(solution));
            for (int j = 0; j < boardSize; j++) {
                if (chessboardData.getNumberOfConflicts(j) > 0) {
                    Assert.fail("solution boardsize = " + i + " doesnt have 0 conflicts: \n");
                }
            }
        }
    }

    @Test
    public void testThreeInLineSolution() {
        int boardSize = 10;
        QueensBacktrackingAlgorithm algorithm = new QueensBacktrackingAlgorithm(boardSize, false, false, true);
        for (int i = 0; i < 20; i++) {
            int[] solution = algorithm.findSolution();
            ChessboardData chessboardData = new ChessboardData(10, true, QueensUtil.convertToPositions(solution));
            for (int j = 0; j < boardSize; j++) {
                if (chessboardData.getNumberOfConflicts(j) > 0) {
                    Assert.fail("solution boardsize = " + i + " doesnt have 0 conflicts: \n");
                }
            }
        }
    }

}
