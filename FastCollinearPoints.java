/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class FastCollinearPoints {

    private Deque<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("FastCollinearPoints with null points array");
        }
        segments = new Deque<>();
        for (int i1 = 0; i1 < points.length; ++i1) {
            Point p1 = points[i1];
            if (p1 == null) {
                throw new IllegalArgumentException("FastCollinearPoints with null point in array");
            }
            double[] slopes = new double[points.length - 1];
            int slopesIndex = 0;
            for (int i2 = 0; i2 < points.length; ++i2) {
                Point p2 = points[i2];
                if (i2 == i1) {
                    continue;
                }
                if (p2.compareTo(p1) == 0) {
                    throw new IllegalArgumentException("FastCollinearPoints with duplicate points");
                }
                slopes[slopesIndex++] = p1.slopeTo(p2);
            }
            Arrays.sort(slopes);
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
}
