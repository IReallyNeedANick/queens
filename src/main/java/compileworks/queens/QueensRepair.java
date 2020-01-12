package compileworks.queens;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author Boris Marn
 */
public class QueensRepair {

    static int SHUFFLE = 0;
    static int INFINITY_HAPPENED = 0;

    private int[] createBoard(int board, boolean shuffle) {
//        System.out.println("shuffle " + SHUFFLE);
        int[] chessBoard = new int[board];

        for (int i = 0; i < board; i++) {
            chessBoard[i] = i;
        }
        if (shuffle) {
            SHUFFLE++;
            QueenUtil.shuffleArray(chessBoard);
        }

        return chessBoard;
//        return new QueensOptimizedBacktracking().calculateAndPrint(board, false, false);
    }

    public void calculateAndPrint(int board, boolean printSolution, boolean printMetrics) {
        Instant start = Instant.now();

        int[] chessBoard = calcQueens(createBoard(board, true));
        if (chessBoard != null) {
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

            System.out.println("Shuffled = " + SHUFFLE);
            System.out.println("Infinity = " + INFINITY_HAPPENED);
        }
    }

    private int[] calcQueens(int[] chessBoard) {
        long infinityCheck = (long) chessBoard.length * chessBoard.length * chessBoard.length * chessBoard.length;
        long infinityChecker = infinityCheck;
        double max = 4;
        while (true) {
            if (infinityChecker-- < 0) {
//                System.out.println("infinity happened");
                infinityChecker = infinityCheck;
                INFINITY_HAPPENED++;
                chessBoard = createBoard(chessBoard.length, true);
            }
            int[] conflicts = calcConflicts(chessBoard);

            List<Integer> indexesOfMax = getIndexOfMax(conflicts, Integer.MAX_VALUE);
            int maxValue = indexesOfMax.get(0);
            for (int i = 0; i < 4; i++) {
                List<Integer> indexOfMax = getIndexOfMax(conflicts, maxValue);
                if (indexOfMax.isEmpty() || indexOfMax.get(0) == 0) {
                    break;
                }
                maxValue = indexOfMax.get(0);
                indexesOfMax.addAll(indexOfMax);
            }

            Collections.shuffle(indexesOfMax);
            boolean queenPlaced = false;
            for (Integer ofMax : indexesOfMax) {

                int value = conflicts[ofMax];
                if (value == 0) {
                    return chessBoard;
                } else {
                    queenPlaced = findAndPlaceQueenOnBetterSpot(chessBoard, ofMax);
                    if (queenPlaced) {
                        break;
                    }
                }
            }
            if (!queenPlaced) {
                infinityChecker = 0;
                chessBoard = createBoard(chessBoard.length, true);
            }
        }
    }

    private boolean findAndPlaceQueenOnBetterSpot(int[] chessBoard, int index) {
        int[] verticalConflicts = new int[chessBoard.length];
        int minElement = Integer.MAX_VALUE;
        int minElementIndex = -1;
        for (int i = 0; i < chessBoard.length; i++) {
            int conflict = findConflict(chessBoard, index, i);
            if (conflict == 0) {
                chessBoard[index] = i;
                return true;
            } else if (conflict < minElement) {
                minElement = conflict;
                minElementIndex = i;
            }
            verticalConflicts[i] = conflict;
        }

        if (minElement == verticalConflicts[chessBoard[index]]) {
            return false;
        } else {
            chessBoard[index] = minElementIndex;
            return true;
        }
    }

    public int[] calcConflicts(int[] chessBoard) {
        int[] conflicts = new int[chessBoard.length];
        for (int i = 0; i < chessBoard.length; i++) {
            conflicts[i] = findConflict(chessBoard, chessBoard[i], i);
        }
        return conflicts;
    }

    public int findConflict(int[] chessBoard, int x, int y) {
        int numOfConflicts = 0;

        for (int i = 0; i < chessBoard.length; i++) {
            if (i != y) {
                if (chessBoard[i] == x) {
                    numOfConflicts++;
                } else if (i + chessBoard[i] == x + y || chessBoard[i] - i == x - y) {
                    numOfConflicts++;
                }
            }
        }

        int UP = x - (x / 2);
        int DOWN = (x + chessBoard.length - 1) / 2;
        int LEFT = y - (y / 2);
        int RIGHT = (y + chessBoard.length - 1) / 2;

        for (int i = LEFT; i <= RIGHT; i++) {
            // is part of barrier
            if (i != y) {
                if (chessBoard[i] >= UP && chessBoard[i] <= DOWN) {
                    if (thirdPointExists(chessBoard, x, y, chessBoard[i], i)) {
                        numOfConflicts++;
                    }
                }
            }
        }

        return numOfConflicts;
    }

    private boolean thirdPointExists(int[] chessBoard, int x, int y, int x1, int y1) {
        int vectorx = x - x1, vectory = y - y1;
        int x2 = x1 - vectorx, y2 = y1 - vectory;
        while (x2 >= 0 && y2 >= 0 && y2 < chessBoard.length) {
            if (chessBoard[y2] == x2) {
                return true;
            }
            x2 = x2 - vectorx;
            y2 = y2 - vectory;
        }
        return false;
    }

    public List<Integer> getIndexOfMax(int[] array, int belowThis) {
        int max = array[0];
        List<Integer> indexes = new ArrayList<>();
        indexes.add(0);
        for (int i = 1; i < array.length; i++) {
            if (array[i] < belowThis) {

                if (max < array[i]) {
                    indexes.clear();
                    max = array[i];
                    indexes.add(i);
                } else if (max == array[i]) {
                    indexes.add(i);
                }
            }
        }
        return indexes;
    }


}
