import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * More efficient algorithm implementation with a time complexity of n^2*log(n) using
 * slope sorting to find collinear points.
 */
public class FastCollinearPoints {
    LinkedList<LineSegment> results;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        // Sort array by slope
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        int N = points.length;

        // Use LinkedList for efficient space usage - no array resizing operations needed
        results = new LinkedList<LineSegment>();

        // Think of p as the origin
        // For each other point q, determine slope it makes with p
        // Sort points according to slope they make with p
        // Check if any 3+ adjacent point in order have equal slopes with respect to p
        for (int i = 0; i < N; i++) {
            Point p = sortedPoints[i];

            // Clone sorted array and sort by slope with respect to p
            Point[] slopeSorted = sortedPoints.clone();
            Arrays.sort(slopeSorted, p.slopeOrder());

            int x = 1; // Start at 1 because p is at index 0

            while (x < N) {
                LinkedList<Point> collinearCheck = new LinkedList<Point>();

                double slopeToCheck = p.slopeTo(slopeSorted[x]);

                // Add all CONSECUTIVE Points that have the same slope with respect to p
                do {
                    collinearCheck.add(slopeSorted[x++]);
                } while (x < N && p.slopeTo(slopeSorted[x]) == slopeToCheck);

                // If more than 3 points have been added, means there's at least 4 collinear points
                if (collinearCheck.size() >= 3 && p.compareTo(collinearCheck.getFirst()) < 0) {
                    // first point would be p
                    // But need to ensure that the p chosen is the smallest one as well
                    // Comparing p with first item of linkedlist - since array was sorted, the p
                    // that is smaller than the first item has to be the first collinear point
                    Point first = p;
                    Point last = collinearCheck.getLast();
                    results.add(new LineSegment(first, last));
                }
            }
        }

    }

    /**
     * Returns number of collinear segments
     * @return num of collinear segments
     */
    public int numberOfSegments() {
        return results.size();
    }

    /**
     * Returns array of segments, converted from Linked List
     * @return array of segment
     */
    public LineSegment[] segments() {
        LineSegment[] arrayResult = new LineSegment[results.size()];
        return results.toArray(arrayResult);
    }

    /**
     * Code provided by Algorithm Pt.1 Course to check solution
     */
    public static void main(String[] args) {
        // read the n points from a file (filename or args)
//        String in = "test cases/input8.txt";
//        In in = new In(in);
        In in = new In(args[0]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
