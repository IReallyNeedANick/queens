package compileworks.queens

import java.time.Duration
import java.time.Instant

object QueensApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        val board = 80
        val iterate = 10
        val printSolution = false
        val printMetrics = false
//        QueensNaiveBacktracking().calculateAndPrint(board, printSolution, printMetrics)
//        //        new QueensOptimizedBacktracking().calculateAndPrint(board, printSolution, printMetrics);
//        val start = Instant.now()
//        for (i in 0 until iterate) {
//            print("$i ")
//            QueensRepair().calculateAndPrint(board, printSolution, false)
//        }
//        val end = Instant.now()
//        println()
//        println(
//            Duration.ofMillis(
//                Duration.between(
//                    start,
//                    end
//                ).toMillis() / iterate
//            )
//        ) // prints PT1M3.553S
        //        QueenUtil.findNaive(100_000_000);
//        QueenUtil.printSolution(intArrayOf(3, 0, 2, 4, 1))
        val calcConflicts = QueensRepair().calcConflicts(intArrayOf(2, 5, 7, 1, 3, 0, 6, 4))
//        val calcConflicts = QueensRepair().calcConflicts(intArrayOf(3, 0, 2, 4, 1))
        println(calcConflicts)
//        println("calculating optimized backtracking")
        calc(iterate) {
            QueensOptimizedBacktracking().calculateAndPrint(board, printSolution, printMetrics)
        }
        println("calculating iterative repair")
        calc(iterate) {
            QueensRepair().calculateAndPrint(board, printSolution, printMetrics)
        }
    }

    fun calc(iterationTimes: Int, body: () -> Unit) {
        val start = Instant.now()
        for( i in 1..iterationTimes) {
            print(" " + i)
            body.invoke()
        }
        val end = Instant.now()

        println(
            Duration.ofMillis(
                Duration.between(
                    start,
                    end
                ).toMillis() / iterationTimes
            )
        )

    }
}
