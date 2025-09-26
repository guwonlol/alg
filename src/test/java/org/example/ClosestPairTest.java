package org.example;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClosestPairTest {
    @Test
    void testSmallBruteForceComparison() {
        Random rnd = new Random();
        ClosestPair.Point[] pts = new ClosestPair.Point[50];
        for (int i = 0; i < pts.length; i++) {
            pts[i] = new ClosestPair.Point(rnd.nextDouble(), rnd.nextDouble());
        }

        Metrics m = new Metrics();
        double fast = ClosestPair.solve(pts, m);
        double brute = bruteForce(pts);

        assertEquals(brute, fast, 1e-9);
    }

    private static double bruteForce(ClosestPair.Point[] pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x() - pts[j].x();
                double dy = pts[i].y() - pts[j].y();
                double d = Math.sqrt(dx * dx + dy * dy);
                if (d < best) best = d;
            }
        }
        return best;
    }
}
