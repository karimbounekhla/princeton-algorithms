import java.util.ArrayList;
import java.util.Iterator;

/**
 * Data type that models an n-by-n board with sliding tiles
 * https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php
 *
 * Work in Progress
 */

public class Board {
    int[][] board;
    int N;
    int[] emptyPos;
    ArrayList<Board> neighbors;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.board = tiles;
        this.N = tiles.length; // N by N array
        getBlank();
        neighbors = new ArrayList<Board>();
    }

    private int[] getBlank() {
        emptyPos = new int[2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) {
                    emptyPos[0] = i;
                    emptyPos[1] = j;
                    return emptyPos;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        String boardView = "" + N;
        for (int i = 0; i < N; i++) {
            boardView += "\n ";
            for (int j = 0; j < N; j++) {
                boardView += board[i][j] + "   ";
            }
        }
        return boardView;
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        int pos = 1;
        int hamming = -1; // Start at -1 because will always have 1 added distance due to '0' tile
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != pos) {
                    hamming++;
                }
                pos++;
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = board[i][j];

                if (val == 0) {
                    continue;
                }

                int correctRow = val / N;
                int correctCol = (val % N) - 1;

                // Edge case
                if (val % N == 0) {
                    correctRow--;
                    correctCol = N-1;
                }
                manhattan += java.lang.Math.abs(correctRow-i)
                        + java.lang.Math.abs(correctCol - j);
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int [][] t = new int[N][N];
        int k = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                t[i][j] = k++;
            }
        }
        t[N-1][N-1] = 0;

        Board goal = new Board(t);

        return this.equals(goal);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass()) {

            return false;
        }
        if (this == y) {
            return true;
        }

        Board compared = (Board) y;

        if (this.dimension() == compared.dimension()
                && this.toString().equals(compared.toString())) {
            return true;
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
//    public Board twin() {
//
//    }

    // unit testing
    public static void main(String[] args) {
        int[][] t = new int[3][3];
        int k = 1;
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                t[i][j] = k++;
            }
        }
        t[2][2] = 0;

//        t[0][0] = 8;
//        t[0][1] = 1;
//        t[0][2] = 3;
//        t[1][0] = 4;
//        t[1][1] = 0;
//        t[1][2] = 2;
//        t[2][0] = 7;
//        t[2][1] = 6;
//        t[2][2] = 5;
        Board b = new Board(t);
        System.out.println(b);

        System.out.println(b.isGoal());
    }

}
