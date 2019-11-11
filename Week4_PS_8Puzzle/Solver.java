import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

/**
 * This class implements A* search to solve a n-by-n slider puzzle using Priority Queues
 */

public class Solver {

    private class SearchNode {
        private Board b;
        private int moves;
        private SearchNode prev;

        public void SearchNode(Board b, int moves, SearchNode prev) {
            this.b = b;
            this.moves = moves;
            this.prev = prev;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> gameTree = new MinPQ<SearchNode>();
        

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return true;
    }

    // min number of moves to solve initial board
    public int moves() {
        return 0;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return null;
    }

    /**
     * Test Client
     * @param args unused
     */
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
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
