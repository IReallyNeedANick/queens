package compileworks.queens;

import compileworks.queens.repair.QueensRepairAlgorithm;
import org.junit.Ignore;
import org.junit.Test;

public class QueensApplicationTests {

    @Test
    @Ignore
    public void runForFun() {
        int boardSize = 5000;
        boolean printSolution = false;
        boolean printDuration = true;
        boolean solveNInLine = false;
        for (int i = 0; i < 100; i++) {

            new QueensRepairAlgorithm(boardSize, printSolution, printDuration, solveNInLine)
                    .findSolution();
        }
//        new QueensBacktrackingAlgorithm(boardSize, printSolution, printDuration, solveNInLine)
//                .findSolution();

    }
}
