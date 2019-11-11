import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

/**
 * This class implements A* search to solve a n-by-n slider puzzle using Priority Queues
 */

public class Solver {
    private SearchNode solution;
    private boolean isSolvable;

    /**
     * Create inner class SearchNode that can be used in the PQ
     * When finding the priority, we are looking at # of moves and Hamming number to determine
     * how close we are to the optimal solution.
     */
    private class SearchNode implements Comparable<SearchNode> {
        private Board b;
        private int moves;
        private SearchNode prev;

        // Constructor
        public SearchNode(Board b, int moves, SearchNode prev) {
            this.b = b;
            this.moves = moves;
            this.prev = prev;
        }

        // Compare SearchNodes by their priorities
        @Override
        public int compareTo(SearchNode o) {
            return this.priority() - o.priority();
        }

        // Priority is hamming number + number of moves done so far.
        // We are looking to minimize this.
        public int priority() {
            return this.b.hamming() + this.moves;
        }

        public Board getB() {
            return b;
        }

        public int getMoves() {
            return moves;
        }

        public SearchNode getPrev() {
            return prev;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        // Initialize the PQ with the first search node, containing initial board
        MinPQ<SearchNode> gameTree = new MinPQ<SearchNode>();
        gameTree.insert(new SearchNode(initial, 0, null));

        while (true) {
            // For each iteration, get the minimum Search Node
            SearchNode curr = gameTree.delMin();
            Board currBoard = curr.getB();

            // If solution is found, stop
            if (currBoard.isGoal()) {
                solution = curr;
                isSolvable = true;
                break;
            }

            // Run A* algorithm on puzzle instance with a twin (swapping 2 tiles that are not empty)
            // Only one of these will lead to a solvable puzzle - hence if it is the twin, puzzle is not solvable
//            if (currBoard.hamming() == 2 && currBoard.twin().isGoal()) {
//                isSolvable = false;
//                break;
//            }

            // Get current move, and assign previous board
            int moves = curr.getMoves();
            Board prevBoard;
            if (moves > 0) {
                prevBoard = curr.getPrev().getB();
            } else {
                prevBoard = null;
            }

            // Iterate through neighbors and add to PQ
            // Only do so if not null, and if neighbor isn't the same result as previous board
            // ^ Save time
            for (Board nextBoard : currBoard.neighbors()) {
                if (prevBoard != null && nextBoard.equals(prevBoard)) { continue; }

                gameTree.insert(new SearchNode(nextBoard, moves+1, curr));
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return solution.getMoves();
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (isSolvable() == false) {
            return null;
        }
        LinkedList<Board> sols = new LinkedList<Board>();
        SearchNode search = solution;
        while (search != null) {
            sols.addFirst(search.getB());
            search = search.getPrev();
        }
        return sols;
    }

    /**
     * Test Client
     * @param args unused
     */
    public static void main(String[] args) {

        // create initial board from file
        // In in = new In(args[0]);
        String inp = "test/puzzle05.txt";
        In in = new In(inp);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
