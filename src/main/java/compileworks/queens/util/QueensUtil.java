package compileworks.queens.util;

import compileworks.queens.model.QueenPosition;

import java.util.*;

/**
 * Utility functions that deal with the printing, creating boards, and conversion of objects
 *
 * @author Boris Marn
 */
public class QueensUtil {
    public static void printSolution(boolean[][] chessBoard) {
        for (boolean[] row : chessBoard) {
            System.out.println();
            for (boolean value : row) {
                System.out.print(value ? 1 + " " : 0 + " ");
                System.out.println();
            }
        }
    }

    public static void printSolution(int[] chessBoard) {
        for (int row : chessBoard) {
            for (int i = 0; i < chessBoard.length; i++) {
                if (i == row) {
                    System.out.print(1 + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
    }

    public static int[] createShuffledBoard(int boardSize) {
        int[] chessBoard = new int[boardSize];
        for (int i = 0; i < boardSize; i++) {
            chessBoard[i] = i;
        }
        // it seems that shuffled board works faster on average
        QueensUtil.shuffleBoard(chessBoard);
        return chessBoard;
    }

    public static void shuffleBoard(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            QueensUtil.swapElement(ar, index, i);
        }
    }

    public static QueenPosition[] convertToPositions(int[] chessBoard) {
        return QueensUtil.convertToPositions(chessBoard, chessBoard.length);
    }

    public static int[] convertToBoard(Collection<QueenPosition> positions) {
        int[] board = new int[positions.size()];
        positions.forEach(position -> board[position.x] = position.y);
        return board;
    }

    public static QueenPosition[] convertToPositions(int[] chessBoard, int upUntil) {
        QueenPosition[] queenPositions = new QueenPosition[chessBoard.length];
        for (int i = 0; i < upUntil; i++) {
            queenPositions[i] = new QueenPosition(i, chessBoard[i]);
        }
        return queenPositions;
    }

    public static void swapElement(int[] chessBoard, int a, int b) {
        int a_temp = chessBoard[a];
        chessBoard[a] = chessBoard[b];
        chessBoard[b] = a_temp;
    }

    /**
     * if you dont need to also solve the queens problem for three in line this is  as O=(n) simple solution as it gets
     */
    public static int[] staircaseSolution(int boardSize) {
        int half = boardSize / 2;
        boolean isEven = boardSize % 2 == 0;
        List<Integer> odds = new ArrayList<>(isEven ? half : half + 1);
        List<Integer> evens = new ArrayList<>(half);
        for (int i = 0; i < half; i++) {
            evens.add(i * 2 + 1);
            odds.add(i * 2);
        }
        if (!isEven) {
            odds.add(half * 2);
        }

        switch (boardSize % 6) {
            case 2:
                Collections.swap(odds, 0, 1);
                odds.add(odds.remove(2));
                break;
            case 3:
                evens.add(evens.remove(0));
                odds.add(odds.remove(0));
                odds.add(odds.remove(0));
                break;
        }

        List<Integer> chessboard = new ArrayList<>(evens);
        chessboard.addAll(odds);
        return chessboard.stream().mapToInt(Integer::intValue).toArray();
    }
}
