// MergeSort implementation
package org.example;

public final class MergeSort {
    private static final int INSERTION_CUTOFF = 32;

    private MergeSort() {}

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length <= 1) return;
        m.countAllocation(a.length);
        int[] buf = new int[a.length];
        sortInner(a, buf, 0, a.length, 0, m);
    }

    private static void sortInner(int[] a, int[] buf, int lo, int hi, int depth, Metrics m) {
        m.updateDepth(depth);
        int len = hi - lo;
        if (len <= 1) return;
        if (len <= INSERTION_CUTOFF) {
            insertionSort(a, lo, hi, m);
            return;
        }
        int mid = lo + (len >> 1);
        sortInner(a, buf, lo, mid, depth + 1, m);
        sortInner(a, buf, mid, hi, depth + 1, m);

        m.countComparison();
        if (a[mid - 1] <= a[mid]) return;

        int i = lo, j = mid, k = lo;
        while (i < mid && j < hi) {
            m.countComparison();
            buf[k++] = (a[i] <= a[j]) ? a[i++] : a[j++];
        }
        if (i < mid) System.arraycopy(a, i, buf, k, mid - i);
        else if (j < hi) System.arraycopy(a, j, buf, k, hi - j);

        System.arraycopy(buf, lo, a, lo, hi - lo);
    }

    private static void insertionSort(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i < hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                m.countComparison();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    m.countSwap();
                    j--;
                } else break;
            }
            a[j + 1] = key;
        }
    }
}
