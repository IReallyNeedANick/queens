package compileworks.queens.repair;

import compileworks.queens.model.ChessboardData;
import compileworks.queens.util.QueensUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Boris Marn
 */
public class QueensRepairAlgorithmTest {

    @Test
    public void testNonThreeInLineSolution() {
        for (int i = 8; i < 40; i++) {
            QueensRepairAlgorithm algorithm = new QueensRepairAlgorithm(i, false, false, false);
            int[] solution = algorithm.findSolution();
            ChessboardData chessboardData = new ChessboardData(i, false, QueensUtil.convertToPositions(solution));
            for (int j = 0; j < i; j++) {
                if (chessboardData.getNumberOfConflicts(j) > 0) {
                    QueensUtil.printSolution(chessboardData.convertToBoard());
                    new ChessboardData(i, true, QueensUtil.convertToPositions(solution));
                    Assert.fail("solution boardsize = " + i + " doesnt have 0 conflicts: \n");
                }
            }
        }
    }

    @Test
    public void testThreeInLineSolution() {
        int boardSize = 8;
        QueensRepairAlgorithm algorithm = new QueensRepairAlgorithm(boardSize, false, true, true);
        for (int i = 8; i < 20; i++) {
            int[] solution = algorithm.findSolution();
            ChessboardData chessboardData = new ChessboardData(boardSize, true, QueensUtil.convertToPositions(solution));
            for (int j = 0; j < boardSize; j++) {
                if (chessboardData.getNumberOfConflicts(j) > 0) {
                    new ChessboardData(boardSize, true, QueensUtil.convertToPositions(solution));
                    QueensUtil.printSolution(chessboardData.convertToBoard());
                    Assert.fail("solution boardsize = " + i + " doesnt have 0 conflicts for index = " + j);
                }
            }
        }
    }
}
