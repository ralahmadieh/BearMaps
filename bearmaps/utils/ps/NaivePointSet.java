package bearmaps.utils.ps;

import java.util.List;

public class NaivePointSet implements PointSet  {

    List<Point> points;

    public NaivePointSet(List<Point> pts) {
        points = pts;
    }

    public Point nearest(double x, double y) {
        Point pt = new Point(x, y);
        Point result = null;
        double minDistance = Integer.MAX_VALUE;
        for (Point p: points) {
            double dist = Math.sqrt(Point.distance(pt, p));
            minDistance = Math.min(minDistance, dist);
            if (dist == minDistance) {
                result = p;
            }
        }
        return result;
    }
}