package compileworks.queens.repair;

import compileworks.queens.model.ChessboardData;
import compileworks.queens.model.QueenPosition;
import compileworks.queens.util.QueensUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Boris Marn
 */
public class ChessboardDataTest {

    /**
     * 0 0 0 1
     * 1 0 0 0
     * 0 1 0 0
     * 0 0 1 0
     * conflicts: 1 2 3 2
     */
    int[] board4 = {3, 0, 1, 2};

    /**
     * testing:
     * 0 0 0 1 0
     * 0 1 0 0 0 --> 2
     * 1 0 0 0 0
     * 0 0 1 0 0
     * 0 0 0 0 1
     * conflicts: 0 2 2 1 2
     */
    int[] board5 = {3, 1, 0, 2, 4};

    /**
     * testing:
     * 0 0 0 1 0 0 0 0 | 3
     * 0 0 0 0 0 0 1 0 | 6
     * 0 0 1 0 0 0 0 0 | 2
     * 0 0 0 0 0 0 0 1 | 7
     * 0 1 0 0 0 0 0 0 | 1
     * 0 0 0 0 1 0 0 0 | 4
     * 1 0 0 0 0 0 0 0 | 0
     * 0 0 0 0 0 1 0 0 | 5
     * conflicts: 2 0 2 0 2 0 2 0
     */
    int[] board8 = {3, 6, 2, 7, 1, 4, 0, 5};

    @Test
    public void testNoConflicts() {
        for (int i = 4; i < 20; i++) {
            ChessboardData chessboardData = new ChessboardData(i, false, QueensUtil.convertToPositions(QueensUtil.staircaseSolution(i)));
            for (int j = 0; j < i; j++) {
                if (chessboardData.getNumberOfConflicts(j) > 0) {
                    Assert.fail("solution boardsize = " + i + " doesnt have 0 conflicts: \n");
                }
            }
        }
    }


    @Test
    public void testDiagonalConflicts() {
        ChessboardData chessboardData = new ChessboardData(board4.length, true, QueensUtil.convertToPositions(board4));
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(0));
        Assert.assertEquals(2, chessboardData.getNumberOfConflicts(1));
        Assert.assertEquals(3, chessboardData.getNumberOfConflicts(2));
        Assert.assertEquals(2, chessboardData.getNumberOfConflicts(3));
    }

    @Test
    public void testThreeInLineConflicts() {
        ChessboardData chessboardData = new ChessboardData(board5.length, true, QueensUtil.convertToPositions(board5));
        Assert.assertEquals(0, chessboardData.getNumberOfConflicts(0));
        Assert.assertEquals(2, chessboardData.getNumberOfConflicts(1));
        Assert.assertEquals(2, chessboardData.getNumberOfConflicts(2));
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(3));
        Assert.assertEquals(2, chessboardData.getNumberOfConflicts(4));
    }

    @Test
    public void testThreeInLineBoard8Conflicts() {
        ChessboardData chessboardData = new ChessboardData(board8.length, true, QueensUtil.convertToPositions(board8));
        Assert.assertEquals(3, chessboardData.getNumberOfConflicts(0));
        Assert.assertEquals(0, chessboardData.getNumberOfConflicts(1));
        Assert.assertEquals(3, chessboardData.getNumberOfConflicts(2));
        Assert.assertEquals(0, chessboardData.getNumberOfConflicts(3));
        Assert.assertEquals(3, chessboardData.getNumberOfConflicts(4));
        Assert.assertEquals(0, chessboardData.getNumberOfConflicts(5));
        Assert.assertEquals(3, chessboardData.getNumberOfConflicts(6));
        Assert.assertEquals(0, chessboardData.getNumberOfConflicts(7));
    }

    @Test
    public void testAddingRemovingQueen() {
        ChessboardData chessboardData = new ChessboardData(board5.length, true, QueensUtil.convertToPositions(board5));
        chessboardData.findAndPlaceOnBetterPosition(1); //places queen from y=1 to y=3
        /*
         * testing:
         * 0 0 0 1 0
         * 0 0 0 1 0
         * 1 0 0 0 0
         * 0 0 1 0 0
         * 0 0 0 0 1
         * conflicts: 1 1 1 1 1
         */
        Assert.assertEquals(5, chessboardData.getMaxConflictIndexes().size());
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(0));
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(1));
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(2));
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(3));
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(4));

        chessboardData.findAndPlaceOnBetterPosition(4); //places queen from y=4 to y=1
        /*
         * testing:
         * 0 0 0 1 0
         * 0 0 0 1 0
         * 1 0 0 0 0
         * 0 0 1 0 0
         * 0 1 0 0 0
         * conflicts: 1 1 0 1 1
         */
        Assert.assertEquals(4, chessboardData.getMaxConflictIndexes().size());
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(0));
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(1));
        Assert.assertEquals(0, chessboardData.getNumberOfConflicts(2));
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(3));
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(4));

        chessboardData.findAndPlaceOnBetterPosition(4); //places queen from y=1 to y=4
        /*
         * testing:
         * 0 0 0 1 0
         * 0 0 0 1 0
         * 1 0 0 0 0
         * 0 0 1 0 0
         * 0 0 0 0 1
         * conflicts: 1 1 1 1 1
         */
        Assert.assertEquals(5, chessboardData.getMaxConflictIndexes().size());
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(0));
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(1));
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(2));
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(3));
        Assert.assertEquals(1, chessboardData.getNumberOfConflicts(4));
    }

    @Test
    public void maxConflictsTest() {
        List<QueenPosition> queenPositions = Arrays.asList(new QueenPosition(0, 9),
                new QueenPosition(1, 2),
                new QueenPosition(2, 9),
                new QueenPosition(3, 1),
                new QueenPosition(4, 3),
                new QueenPosition(5, 8),
                new QueenPosition(6, 0),
                new QueenPosition(7, 7),
                new QueenPosition(8, 4),
                new QueenPosition(9, 6));
        ChessboardData chessboardData = new ChessboardData(10, false, (QueenPosition[]) queenPositions.toArray());
        Set<Integer> maxConflictIndexes = new HashSet<>(chessboardData.getMaxConflictIndexes());
        Assert.assertEquals(2, maxConflictIndexes.size());
        Assert.assertTrue(maxConflictIndexes.contains(0));
        Assert.assertTrue(maxConflictIndexes.contains(2));
    }
}
