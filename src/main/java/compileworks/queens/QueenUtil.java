package compileworks.queens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Boris Marn
 */
public class QueenUtil {
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
            System.out.println();

            for (int i = 0; i < chessBoard.length; i++) {
                if (i == row) {
                    System.out.print(1 + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }
        }
        System.out.println();
    }

    public static void shuffleArray(int[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public static int[] findNaive(int board) {
        int half = board / 2;
        boolean isEven = board % 2 == 0;
        List<Integer> odds = new ArrayList<>(isEven ? half : half + 1);
        List<Integer> evens = new ArrayList<>(half);
        for (int i = 0; i < half; i++) {
            odds.add(i * 2 + 1);
            evens.add(i * 2);
        }
        if (!isEven) {
            odds.add(half * 2 + 1);
        }

        switch (board % 6) {
            case 2:
                Collections.swap(odds, 0 ,1);
                odds.add(odds.remove(2));
                break;
            case 3:
                evens.add(evens.remove(0));
                odds.add(odds.remove(0));
                odds.add(odds.remove(0));
                break;
        }

        List<Integer> chessboard =  new ArrayList<>(evens);
        chessboard.addAll(odds);
        return chessboard.stream().mapToInt(Integer::intValue).toArray();
    }
}
