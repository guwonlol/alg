package org.example;

import java.util.Arrays;
import java.util.Comparator;

public final class ClosestPair {
    public record Point(double x, double y) {}

    private ClosestPair() {}

    public static double solve(Point[] pts, Metrics m) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
        Point[] sortedX = pts.clone();
        Arrays.sort(sortedX, Comparator.comparingDouble(p -> p.x));
        Point[] aux = new Point[pts.length];
        return closest(sortedX, aux, 0, pts.length, 0, m);
    }

    private static double closest(Point[] pts, Point[] aux, int lo, int hi, int depth, Metrics m) {
        m.updateDepth(depth);
        int n = hi - lo;
        if (n <= 3) return bruteForce(pts, lo, hi, m);

        int mid = lo + n / 2;
        double midx = pts[mid].x;
        double d1 = closest(pts, aux, lo, mid, depth + 1, m);
        double d2 = closest(pts, aux, mid, hi, depth + 1, m);
        double d = Math.min(d1, d2);

        mergeByY(pts, aux, lo, mid, hi);
        int len = 0;
        for (int i = lo; i < hi; i++)
            if (Math.abs(pts[i].x - midx) < d) aux[len++] = pts[i];

        for (int i = 0; i < len; i++)
            for (int j = i + 1; j < len && (aux[j].y - aux[i].y) < d; j++) {
                m.countComparison();
                d = Math.min(d, dist(aux[i], aux[j]));
            }
        return d;
    }

    private static double bruteForce(Point[] pts, int lo, int hi, Metrics m) {
        double d = Double.POSITIVE_INFINITY;
        for (int i = lo; i < hi; i++)
            for (int j = i + 1; j < hi; j++) {
                m.countComparison();
                d = Math.min(d, dist(pts[i], pts[j]));
            }
        Arrays.sort(pts, lo, hi, Comparator.comparingDouble(p -> p.y));
        return d;
    }

    private static void mergeByY(Point[] pts, Point[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid, k = lo;
        while (i < mid && j < hi)
            aux[k++] = (pts[i].y <= pts[j].y) ? pts[i++] : pts[j++];
        while (i < mid) aux[k++] = pts[i++];
        while (j < hi) aux[k++] = pts[j++];
        for (i = lo; i < hi; i++) pts[i] = aux[i];
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
