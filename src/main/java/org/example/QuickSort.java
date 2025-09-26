// QuickSort implementation
package org.example;

import java.util.Random;

public final class QuickSort {
    private static final Random RNG = new Random();

    private QuickSort() {}

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length <= 1) return;
        sortInner(a, 0, a.length - 1, 0, m);
    }

    private static void sortInner(int[] a, int lo, int hi, int depth, Metrics m) {
        while (lo < hi) {
            m.updateDepth(depth);
            int pivotIndex = lo + RNG.nextInt(hi - lo + 1);
            int pivot = a[pivotIndex];
            int i = lo, j = hi;

            while (i <= j) {
                while (a[i] < pivot) { m.countComparison(); i++; }
                while (a[j] > pivot) { m.countComparison(); j--; }
                if (i <= j) {
                    swap(a, i, j, m);
                    i++; j--;
                }
            }

            // рекурсивно сортируем меньший участок
            if (j - lo < hi - i) {
                if (lo < j) sortInner(a, lo, j, depth + 1, m);
                lo = i;
            } else {
                if (i < hi) sortInner(a, i, hi, depth + 1, m);
                hi = j;
            }
        }
    }

    private static void swap(int[] a, int i, int j, Metrics m) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
        m.countSwap();
    }
}
