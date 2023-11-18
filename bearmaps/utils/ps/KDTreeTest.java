package bearmaps.utils.ps;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    @Test
    public void testNaivePointSet() {
        /* @Source: Bearmaps project spec, thanks staff owo */
        Point p1 = new Point(1.1, 2.2);
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0);
        System.out.println(ret.getX());
        System.out.println(ret.getY());
    }

    @Test
    public void testKDTree() {
        Point p1 = new Point(1.1, 2.2);
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        
        KDTree test = new KDTree(List.of(p1, p2, p3));
        Point ret = test.nearest(3.0, 4.0);
        System.out.println(ret.getX());
        System.out.println(ret.getY());
    }
}