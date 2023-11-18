package bearmaps.utils.ps;

import java.util.List;

public class KDTree implements PointSet{

    Node root;

    public class Node {
        Point p;
        Node left;
        Node right;
        boolean wrtX;

        public Node(Point pt, Node lft, Node r, boolean bool) {
            p = pt;
            left = lft;
            right = r;
            wrtX = bool;
        }

        private void addHelper(Point point) {
            if (this.left == null && comparePoints(this, point) > 0) {
                this.left = new Node(point, null, null, !this.wrtX);
            } else if (this.right == null && comparePoints(this, point) < 0) {
                this.right = new Node(point, null, null, !this.wrtX);
            } else {
                if (comparePoints(this, point) > 0) {
                    this.left.addHelper(point);
                } else {
                    this.right.addHelper(point);
                }
            }
        }
    }

    public KDTree(List<Point> points) {
        root = null;
        for (Point p : points) {
            add(p);
        }
    }

    private void add(Point point) {
        if (root == null) {
            root = new Node(point, null, null, true);
        } else {
            root.addHelper(point);
        }
    }

    public Point nearest(double x, double y) {
        Point pt = new Point(x, y);
        Node result = nearestHelper(root, pt, root);
        return result.p;
    }

    private static Node nearestHelper(Node curr, Point goal, Node best) {
        /* @Source: pseudo code from lecture 5 slides */
        if (curr == null) {
            return best;
        }
        double minDistance = Math.sqrt(Point.distance(goal, best.p));
        double dist = Math.sqrt(Point.distance(goal, curr.p));
        if (dist < minDistance) {
            best = curr;
        }
        double min = Point.distance(best.p, goal);

        Node goodSide, badSide;
        if (comparePoints(curr, goal) < 0) {
            goodSide = curr.right;
            badSide = curr.left;
        } else {
            goodSide = curr.left;
            badSide = curr.right;
        }

        best = nearestHelper(goodSide, goal, best);

        if (useBadSide(curr, goal, min)) {
            best = nearestHelper(badSide, goal, best);
        }

        return best;
    }

    private static boolean useBadSide(Node side, Point goal, Double currDist) {
        Point comparing;
        if (side.wrtX) {
            comparing = new Point(side.p.getX(), goal.getY());
        } else {
            comparing = new Point(goal.getX(), side.p.getY());
        }
        if (Point.distance(comparing, goal) < currDist) {
            return true;
        }
        return false;
    }

    private static double comparePoints(Node a, Point b) {
        if (a.wrtX) {
            return a.p.getX() - b.getX();
        }
        return a.p.getY() - b.getY();
    }
}