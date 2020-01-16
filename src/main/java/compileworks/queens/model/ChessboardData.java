package compileworks.queens.model;

import compileworks.queens.util.QueensUtil;

import java.util.*;

/**
 * This is the object that keeps all the conflicts and positions that generate them.
 *
 * @author Boris Marn
 */
public class ChessboardData {

    /**
     * stores in how many conflicts the queen on the x axis is
     */
    private int[] conflicts;
    /**
     * stores all positions. No conflict are possible on x because we always move queens on y axis.
     */
    private Map<Integer, QueenPosition> xPositions = new HashMap<>();
    /**
     * Stores all positions on y axis. If there are 2 queens in the set this means they are in conflict.
     */
    private Map<Integer, Set<QueenPosition>> yPositions = new HashMap<>();
    /**
     * stores all positions for key (x+y)
     */
    private Map<Integer, Set<QueenPosition>> xPlusYPositions = new HashMap<>();
    /**
     * stores all positions for key (y-x)
     */
    private Map<Integer, Set<QueenPosition>> yMinusXPositions = new HashMap<>();
    /**
     * stores all possible lines between queens except diagonals and horizontal/vertical.
     */
    private Map<Line, Set<QueenPosition>> linePositions = new HashMap<>();

    private boolean solveNInLine;
    private Random random = new Random();

    /**
     * creates Chessboard data object with queens placed on random non overlapping (x,y) spots
     * and all the conflicts are calculated
     *
     * @param boardSize            nxn size of a chessboard
     * @param solveNInLine should we also calculate conflicts for three in line queens?
     */
    public ChessboardData(int boardSize, boolean solveNInLine) {
        this.conflicts = new int[boardSize];
        this.solveNInLine = solveNInLine;
        calculateInitialConflicts(QueensUtil.convertToPositions(QueensUtil.createShuffledBoard(boardSize)));
    }

    public ChessboardData(int boardSize, boolean solveNInLine, QueenPosition[] queenPositions) {
        this.conflicts = new int[boardSize];
        this.solveNInLine = solveNInLine;
        calculateInitialConflicts(queenPositions);
    }

    /**
     * @return returns the list (in random order) of positions that have max conflict on chessboard
     */
    public List<Integer> getMaxConflictIndexes() {
        int max = conflicts[0];
        List<Integer> indexes = new ArrayList<>();
        indexes.add(0);
        for (int i = 1; i < conflicts.length; i++) {
            if (max < conflicts[i]) {
                indexes.clear();
                max = conflicts[i];
                indexes.add(i);
            } else if (max == conflicts[i]) {
                indexes.add(i);
            }
        }
        Collections.shuffle(indexes);
        return indexes;
    }

    public int[] convertToBoard() {
        return QueensUtil.convertToBoard(xPositions.values());
    }

    public int getNumberOfConflicts(int x) {
        return conflicts[x];
    }

    /**
     * finds and places the queen on a better x axis spot
     *
     * @return returns false if there are only worse spots left, otherwise returns true
     */
    public boolean findAndPlaceOnBetterPosition(int x) {
        QueenPosition verticalPosition = new QueenPosition(x, xPositions.get(x).y);
        long minElement = Integer.MAX_VALUE;
        List<Integer> minElementIndex = new ArrayList<>();
        for (int y = 0; y < conflicts.length; y++) {
            if (y != verticalPosition.y) {
                final int finalY = y;

                long numOfConflicts = !solveNInLine ? 0 :
                        linePositions
                                .keySet()
                                .stream()
                                .filter(line -> line.isQueenOnLine(new QueenPosition(verticalPosition.x, finalY)))
                                .count();
                if (yPositions.get(y) != null) {
                    numOfConflicts += yPositions.get(y).size();
                }
                if (xPlusYPositions.get(y + verticalPosition.x) != null) {
                    numOfConflicts += xPlusYPositions.get(y + verticalPosition.x).size();
                }
                if (yMinusXPositions.get(y - verticalPosition.x) != null) {
                    numOfConflicts += yMinusXPositions.get(y - verticalPosition.x).size();
                }

                if (numOfConflicts < minElement) {
                    minElement = numOfConflicts;
                    minElementIndex.clear();
                    minElementIndex.add(y);
                } else if (numOfConflicts == minElement) {
                    minElementIndex.add(y);
                }
                if (numOfConflicts == 0) {
                    break;
                }
            }
        }

        if (conflicts[verticalPosition.x] < minElement) {
            return false;
        }


        QueenPosition newPosition = new QueenPosition(
                verticalPosition.x,
                minElementIndex.get(random.nextInt(minElementIndex.size())));

        removeQueen(verticalPosition);
        addQueen(newPosition, true);

        return true;
    }

    private void addQueen(QueenPosition position, boolean calculateConflicts) {
        addQueen(position.y, position, yPositions, calculateConflicts);
        addQueen(position.y + position.x, position, xPlusYPositions, calculateConflicts);
        addQueen(position.y - position.x, position, yMinusXPositions, calculateConflicts);

        xPositions.put(position.x, position);

        if (calculateConflicts) {
            if (solveNInLine) {
                Map<QueenPosition, Line> positionsToAddLines = new HashMap<>();
                yPositions.keySet().forEach(y -> yPositions.get(y).forEach(pos -> {
                    if (pos.y != position.y &&
                            pos.y + pos.x != position.y + position.x &&
                            pos.y - pos.x != position.y - position.x) {

                        double slope, xIntercept, yIntercept;
                        xIntercept = position.x - pos.x;
                        yIntercept = position.y - pos.y;
                        slope = yIntercept / xIntercept;
                        Line currLine = new Line(pos, slope);
                        positionsToAddLines.put(pos, currLine);
                    }
                }));
                positionsToAddLines.forEach((value, key) -> addQueenOnLine(value, position, key));
            }

        }
    }

    private void addQueen(Integer key, QueenPosition position, Map<Integer, Set<QueenPosition>> map, boolean addConflict) {
        if (!map.containsKey(key)) {
            map.put(key, new HashSet<>());
        }
        map.get(key).add(position);
        if (addConflict) {

            if (map.get(key).size() >= 2) {
                map.get(key).forEach(pos -> conflicts[pos.x]++);
            }
        }
    }

    private void addQueenOnLine(QueenPosition a, QueenPosition b,
                                Line line) {

        if (Objects.isNull(linePositions.get(line))) {
            linePositions.put(line, new HashSet<>());
        }

        boolean aAdded = linePositions.get(line).add(a);
        boolean bAdded = linePositions.get(line).add(b);
        int size = linePositions.get(line).size();
        if (size >= 3) {

            if (aAdded && size > 3) {
                conflicts[a.x] = conflicts[a.x] + size - 3;
            } else if (bAdded && size > 3) {
                conflicts[b.x] = conflicts[b.x] + size - 3;
            }
            linePositions.get(line).forEach(position -> conflicts[position.x]++);
        }
    }

    private void removeQueen(QueenPosition a) {

        removeConflict(a, yPositions, a.y);
        removeConflict(a, xPlusYPositions, a.y + a.x);
        removeConflict(a, yMinusXPositions, a.y - a.x);

        if (solveNInLine) {

            HashSet<Line> linesToRemove = new HashSet<>();
            linePositions.keySet().forEach(line -> {
                Set<QueenPosition> positions = linePositions.get(line);
                if (positions.contains(a)) {
                    if (positions.size() == 2) {
                        linesToRemove.add(line);
                    } else if (positions.size() > 2) {
                        positions.forEach(position -> conflicts[position.x]--);
                        positions.remove(a);
                    }
                }
            });
            linesToRemove.forEach(line -> linePositions.remove(line));
        }

        conflicts[a.x] = 0;
        xPositions.remove(a.x);
    }

    private void removeConflict(QueenPosition position, Map<Integer, Set<QueenPosition>> conflictMap, Integer key) {

        Set<QueenPosition> positions = conflictMap.get(key);
        if (positions == null) {
            return;
        }
        if (positions.size() >= 2) {
            positions.forEach(queenPosition -> conflicts[queenPosition.x] -= 1);
            conflictMap.get(key).remove(position);
        } else {
            conflictMap.remove(key);
        }
    }

    private void calculateInitialConflicts(QueenPosition[] queenPositions) {
        for (int i = 0; i < queenPositions.length; i++) {
            addQueen(queenPositions[i], false);
            for (int j = i + 1; j < queenPositions.length; j++) {
                if (queenPositions[i].y == queenPositions[j].y) {
                    conflicts[i]++;
                    conflicts[j]++;
                } else if (queenPositions[j].y - queenPositions[j].x == queenPositions[i].y - queenPositions[i].x) {
                    conflicts[i]++;
                    conflicts[j]++;
                } else if (queenPositions[j].y + queenPositions[j].x == queenPositions[i].y + queenPositions[i].x) {
                    conflicts[i]++;
                    conflicts[j]++;
                } else if (solveNInLine) {
                    double slope, xIntercept, yIntercept; // Default slope paralell to y-axis
                    xIntercept = queenPositions[j].x - queenPositions[i].x;
                    yIntercept = queenPositions[j].y - queenPositions[i].y;
                    slope = yIntercept / xIntercept;
                    Line currLine = new Line(queenPositions[i], slope);
                    addQueenOnLine(queenPositions[j], queenPositions[i], currLine);

                }
            }
        }
    }
}

