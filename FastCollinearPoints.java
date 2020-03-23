/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {

    private Deque<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] inPoints) {
        if (inPoints == null) {
            throw new IllegalArgumentException("FastCollinearPoints with null points array");
        }
        Point[] points = Arrays.copyOf(inPoints, inPoints.length);
        segments = new Deque<>();
        for (int i1 = 0; i1 < inPoints.length; ++i1) {
            Point p1 = inPoints[i1];
            if (p1 == null) {
                throw new IllegalArgumentException("FastCollinearPoints with null point in array");
            }
            Arrays.sort(points,
                        (pComp1, pComp2) -> (int) (p1.slopeTo(pComp1) - p1.slopeTo(pComp2)));
            boolean findP1 = false;
            for (int i2 = 0; i2 < points.length - 3; ++i2) {
                if (p1.compareTo(points[i2]) == 0) {
                    if (findP1) {
                        throw new IllegalArgumentException("duplicate point " + p1);
                    }
                    findP1 = true;
                }
                List<Point> pointsToSort = new ArrayList<>(4);
                double slopeI2 = p1.slopeTo(points[i2]);
                for (int step = 1; step < points.length - i2 - 1; ++step) {
                    if (p1.slopeTo(points[i2 + step]) == slopeI2) {
                        pointsToSort.add(points[i2 + step]);
                    }
                    else {
                        break;
                    }
                }
                if (pointsToSort.size() > 2) {
                    pointsToSort.add(p1);
                    Collections.sort(pointsToSort);
                    segments.addLast(new LineSegment(pointsToSort.get(0),
                                                     pointsToSort.get(pointsToSort.size() - 1)));
                    i2 = i2 + pointsToSort.size() - 1;
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
}
