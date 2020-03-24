/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {

    private Deque<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("FastCollinearPoints with null pointsToCheck array");
        }
        Deque<Point> usedPoints = new Deque<>();
        Point[] pointsToCheck = Arrays.copyOf(points, points.length);
        segments = new Deque<>();
        for (int iBase = 0; iBase < points.length; ++iBase) {
            Point pBase = points[iBase];
            if (pBase == null) {
                throw new IllegalArgumentException("FastCollinearPoints with null point in array");
            }
            if (isPointUsed(pBase, usedPoints)) {
                continue;
            }
            Arrays.sort(pointsToCheck, pBase.slopeOrder());
            boolean isBaseFoundAlready = false;
            // points to check is sorted by slope with the base point so collinear will be together
            // we are interested at at least 4 colinear points
            for (int iCandidate = 0; iCandidate < pointsToCheck.length - 3; ++iCandidate) {
                if (pBase.compareTo(pointsToCheck[iCandidate]) == 0) {
                    if (isBaseFoundAlready) {
                        throw new IllegalArgumentException(
                                "FastCollinearPoints with duplicate point " + pBase);
                    }
                    isBaseFoundAlready = true;
                    continue;
                }
                List<Point> collinearPointsNextToCandidate = new ArrayList<>();
                double slopeCandidate = pBase.slopeTo(pointsToCheck[iCandidate]);
                for (int step = 1; step < pointsToCheck.length - iCandidate - 1; ++step) {
                    if (pBase.slopeTo(pointsToCheck[iCandidate + step]) == slopeCandidate) {
                        collinearPointsNextToCandidate.add(pointsToCheck[iCandidate + step]);
                    }
                    else {
                        break;
                    }
                }
                if (collinearPointsNextToCandidate.size() > 1) {
                    collinearPointsNextToCandidate.add(pBase);
                    collinearPointsNextToCandidate.add(pointsToCheck[iCandidate]);
                    Collections.sort(collinearPointsNextToCandidate);
                    segments.addLast(new LineSegment(collinearPointsNextToCandidate.get(0),
                                                     collinearPointsNextToCandidate
                                                             .get(collinearPointsNextToCandidate
                                                                          .size() - 1)));
                    for (Point pointOfSegment : collinearPointsNextToCandidate) {
                        usedPoints.addLast(pointOfSegment);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private boolean isPointUsed(Point point, Deque<Point> usedPoints) {
        if (point == null || usedPoints == null || usedPoints.size() == 0) {
            return false;
        }
        for (Point usedPoint : usedPoints) {
            if (point.compareTo(usedPoint) == 0) {
                return true;
            }
        }
        return false;
    }
}
