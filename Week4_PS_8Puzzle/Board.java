import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Data type that models an n-by-n board with sliding tiles - to be solved using A* Search
 * https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php
 *
 * Work in Progress
 */

public class Board {
    int[][] tiles;
    int N;
    int[] emptyPos;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.N = tiles.length; // N by N array
        setBlank();
    }

    private void setBlank() {
        emptyPos = new int[2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    emptyPos[0] = i;
                    emptyPos[1] = j;
                    return;
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
                boardView += tiles[i][j] + "   ";
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
                if (tiles[i][j] != pos) {
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
                int val = tiles[i][j];

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
        return this.hamming() == 0;
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
        ArrayList<Board> neighborsList = new ArrayList<Board>();
        // Left Neighbor
        if (checkValid(emptyPos[0], emptyPos[1]-1)) {
            int[][] neighbor = makeCopy();
            swap(neighbor, emptyPos[0], emptyPos[1], emptyPos[0], emptyPos[1]-1);
            neighborsList.add(new Board(neighbor));
        }
        // Right Neighbor
        if (checkValid(emptyPos[0], emptyPos[1]+1)) {
            int[][] neighbor = makeCopy();
            swap(neighbor, emptyPos[0], emptyPos[1], emptyPos[0], emptyPos[1]+1);
            neighborsList.add(new Board(neighbor));
        }
        // Top Neighbor
        if (checkValid(emptyPos[0]-1, emptyPos[1])) {
            int[][] neighbor = makeCopy();
            swap(neighbor, emptyPos[0], emptyPos[1], emptyPos[0]-1, emptyPos[1]);
            neighborsList.add(new Board(neighbor));
        }
        // Bottom Neighbor
        if (checkValid(emptyPos[0]+1, emptyPos[1])) {
            int[][] neighbor = makeCopy();
            swap(neighbor, emptyPos[0], emptyPos[1], emptyPos[0]+1, emptyPos[1]);
            neighborsList.add(new Board(neighbor));
        }
        return neighborsList;
    }

    private boolean checkValid(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            return false;
        }
        return true;
    }

    private int[][] makeCopy() {
        int[][] newArr = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newArr[i][j] = tiles[i][j];
            }
        }
        return newArr;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twin = tiles.clone();
        if (emptyPos[0] != 0) {
            swap(twin, 0, 0, 0, 1);
        } else {
            swap(twin, 1, 0, 1, 1);
        }
        return new Board(twin);
    }

    private void swap(int[][] board, int rowOne, int colOne, int rowTwo, int colTwo) {
        int temp = board[rowOne][colOne];
        board[rowOne][colOne] = board[rowTwo][colTwo];
        board[rowTwo][colTwo] = temp;

    }

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

        t[0][0] = 8;
        t[0][1] = 1;
        t[0][2] = 3;
        t[1][0] = 4;
        t[1][1] = 0;
        t[1][2] = 2;
        t[2][0] = 7;
        t[2][1] = 6;
        t[2][2] = 5;
        Board b = new Board(t);
        System.out.println(b);

        for (Board u : b.neighbors()) {
            System.out.println(u);
        }
    }

}
