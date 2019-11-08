import com.sun.security.jgss.GSSUtil;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Brute Force Approach
 */

public class BruteCollinearPoints {
    Point[] pts;
    LineSegment[] lineSegs;
    int numSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        int N = points.length; // Number of Input Points
        pts = points;
        lineSegs = new LineSegment[N];
        numSegments = 0;

        for (int p = 0; p < N-3; p++) {
            for (int q = p+1; q < N-2; q++) {
                for (int r = q+1; r < N-1; r++) {
                    for (int s = r+1; s < N; s++) {
                        if (pts[p].slopeTo(pts[q]) == pts[p].slopeTo(pts[r]) &&
                            pts[p].slopeTo(pts[q]) == pts[p].slopeTo(pts[s])) {
                            Point[] temp = {pts[p], pts[q], pts[r], pts[s]};
                            Arrays.sort(temp);
                            lineSegs[numSegments] = new LineSegment(temp[0], temp[3]);
                            numSegments++;
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        LineSegment[] s = new LineSegment[numSegments];
        for (int i = 0; i < numSegments; i++) {

        }
        return numSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegs;
    }

    public static void main(String[] args) {
        String fileName = "test cases/input8.txt";
        In in = new In(fileName);
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
