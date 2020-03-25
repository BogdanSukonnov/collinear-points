/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BruteCollinearPoints {

    private final Deque<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("BruteCollinearPoints with null points array");
        }
        segments = new Deque<>();
        for (int i1 = 0; i1 < points.length; ++i1) {
            if (points[i1] == null) {
                throw new IllegalArgumentException("BruteCollinearPoints with null point in array");
            }
            for (int i2 = 0; i2 < points.length; ++i2) {
                if (i2 == i1) {
                    continue;
                }
                for (int i3 = 0; i3 < points.length; ++i3) {
                    if (i3 == i2 || i3 == i1) {
                        continue;
                    }
                    for (int i4 = 0; i4 < points.length; ++i4) {
                        if (i4 == i3 || i4 == i2 || i4 == i1) {
                            continue;
                        }
                        Point p1 = points[i1];
                        Point p2 = points[i2];
                        Point p3 = points[i3];
                        Point p4 = points[i4];
                        if (p1.slopeTo(p2) == p1.slopeTo(p3) && p1.slopeTo(p2) == p1.slopeTo(p4)) {
                            List<Point> linePoints = Arrays.asList(p1, p2, p3, p4);
                            Point minPoint = Collections.min(linePoints, p1.slopeOrder());
                            Point maxPoint = Collections.max(linePoints, p1.slopeOrder());
                            segments.addLast(new LineSegment(minPoint, maxPoint));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segmentsArray = new LineSegment[segments.size()];
        int i = 0;
        for (LineSegment segment : segments) {
            segmentsArray[i] = segment;
            ++i;
        }
        return segmentsArray;
    }

    public static void main(String[] args) {

        // read the n points from a file
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
