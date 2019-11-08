import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Brute Force Approach (time complexity n^4)
 */

public class BruteCollinearPoints {
    private LinkedList<LineSegment> results;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        int N = points.length; // Number of Input Points
        results = new LinkedList<LineSegment>();

        for (int p = 0; p < N-3; p++) {
            for (int q = p+1; q < N-2; q++) {
                for (int r = q+1; r < N-1; r++) {
                    for (int s = r+1; s < N; s++) {
                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r]) &&
                            points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
                            Point[] temp = {points[p], points[q], points[r], points[s]};
                            Arrays.sort(temp);
                            results.add(new LineSegment(temp[0], temp[3]));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return results.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] arraySegments = new LineSegment[results.size()];
        return results.toArray(arraySegments);
    }

    /**
     * Code provided by Algorithm Pt.1 Course to check solution
     */
    public static void main(String[] args) {
        String inFile = "test cases/input8.txt";
        In in = new In(inFile);
//        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
           if (segment != null) {
               StdOut.println(segment);
               segment.draw();
           }
        }
       StdDraw.show();
    }
}
