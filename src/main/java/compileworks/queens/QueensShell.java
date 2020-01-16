package compileworks.queens;

import compileworks.queens.backtracking.QueensBacktrackingAlgorithm;
import compileworks.queens.model.ShellBooleanValue;
import compileworks.queens.repair.QueensRepairAlgorithm;
import compileworks.queens.util.QueensUtil;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class QueensShell {

    boolean printSolution = true;
    boolean printDuration = true;
    boolean solveNInLine = true;

    @ShellMethod(value = "Calculate solution to the n-queens problem with the iterative repair algorithm.")
    public void iterativeAlgorithm(@ShellOption(defaultValue = "8") int boardSize) {
        new QueensRepairAlgorithm(boardSize, printSolution, printDuration, solveNInLine)
                .findSolution();
    }

    @ShellMethod(value = "Calculate solution to the n-queens problem with the backtracking algorithm.")
    public void backtrackingAlgorithm(@ShellOption(defaultValue = "8") int boardSize) {
        new QueensBacktrackingAlgorithm(boardSize, printSolution, printDuration, solveNInLine)
                .findSolution();
    }
    @ShellMethod(value = "Prints the simple, O(n) solution for n queens problem without three in line checks.")
    public void staircaseSolution(@ShellOption(defaultValue = "8") int boardSize) {
        QueensUtil.printSolution(QueensUtil.staircaseSolution(boardSize));
    }

    @ShellMethod(value = "change parameters for the queens solution problem.")
    public void changeParams(@ShellOption(defaultValue = "NULL") ShellBooleanValue printSolution,
                                      @ShellOption(defaultValue = "NULL") ShellBooleanValue printDuration,
                                      @ShellOption(defaultValue = "NULL") ShellBooleanValue solveNInLine) {

        if (printSolution.value != null) {
            this.printSolution = printSolution.value;
        }
        if (printDuration.value != null) {
            this.printDuration = printDuration.value;
        }
        if (solveNInLine.value != null) {
            this.solveNInLine = solveNInLine.value;
        }
    }
}

