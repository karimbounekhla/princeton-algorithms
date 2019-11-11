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

    private class SearchNode implements Comparable<SearchNode> {
        private Board b;
        private int moves;
        private SearchNode prev;

        public SearchNode(Board b, int moves, SearchNode prev) {
            this.b = b;
            this.moves = moves;
            this.prev = prev;
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.priority() - o.priority();
        }

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

        MinPQ<SearchNode> gameTree = new MinPQ<SearchNode>();
        gameTree.insert(new SearchNode(initial, 0, null));

        while (true) {
            SearchNode curr = gameTree.delMin();
            Board currBoard = curr.getB();

            if (currBoard.isGoal()) {
                solution = curr;
                isSolvable = true;
                break;
            }

            int moves = curr.getMoves();
            Board prevBoard;
            if (moves > 0) {
                prevBoard = curr.getPrev().getB();
            } else {
                prevBoard = null;
            }

            for (Board nextBoard : currBoard.neighbors()) {
                if (!nextBoard.equals(prevBoard)) {
                    gameTree.insert(new SearchNode(nextBoard, moves+1, curr));
                }
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
        String inp = "test/puzzle04.txt";
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
