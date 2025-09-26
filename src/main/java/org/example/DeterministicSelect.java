package org.example;

import java.util.Arrays;

public final class DeterministicSelect {
    private DeterministicSelect() {}

    public static int select(int[] a, int k, Metrics m) {
        if (a == null || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Bad input");
        }
        int[] copy = Arrays.copyOf(a, a.length); // Create a copy to avoid modifying the input array
        return selectInner(copy, 0, a.length - 1, k, 0, m);
    }

    private static int selectInner(int[] a, int lo, int hi, int k, int depth, Metrics m) {
        m.updateDepth(depth);
        if (lo == hi) {
            return a[lo];
        }

        int pivotValue = medianOfMedians(a, lo, hi, m);
        int pivotIndex = partition(a, lo, hi, pivotValue, m);

        if (k == pivotIndex) {
            return a[pivotIndex];
        } else if (k < pivotIndex) {
            return selectInner(a, lo, pivotIndex - 1, k, depth + 1, m);
        } else {
            return selectInner(a, pivotIndex + 1, hi, k, depth + 1, m);
        }
    }

    private static int partition(int[] a, int lo, int hi, int pivotValue, Metrics m) {
        // Find the index of the pivot value
        int pivotIndex = lo;
        boolean pivotFound = false;
        for (int i = lo; i <= hi; i++) {
            if (a[i] == pivotValue) {
                pivotIndex = i;
                pivotFound = true;
                break;
            }
        }
        if (!pivotFound) {
            throw new IllegalStateException("Pivot value not found in array");
        }

        // Move pivot to the end
        swap(a, pivotIndex, hi, m);

        // Lomuto partition scheme
        int storeIndex = lo;
        for (int i = lo; i < hi; i++) {
            m.countComparison();
            if (a[i] <= pivotValue) { // Changed to <= to handle duplicates correctly
                swap(a, storeIndex, i, m);
                storeIndex++;
            }
        }
        swap(a, storeIndex, hi, m); // Move pivot to its final position
        return storeIndex;
    }

    private static int medianOfMedians(int[] a, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        if (n <= 5) {
            Arrays.sort(a, lo, hi + 1);
            return a[lo + n / 2];
        }

        // Calculate number of medians
        int numMedians = (int) Math.ceil((double) n / 5);
        int[] medians = new int[numMedians];
        int medianIndex = 0;

        // Compute median of each group of 5 elements
        for (int i = lo; i <= hi; i += 5) {
            int subHi = Math.min(i + 4, hi);
            Arrays.sort(a, i, subHi + 1);
            medians[medianIndex++] = a[i + (subHi - i) / 2];
        }

        // Recursively find the median of medians
        return select(medians, numMedians / 2, m);
    }

    private static void swap(int[] a, int i, int j, Metrics m) {
        if (i == j) {
            return;
        }
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
        m.countSwap();
    }
}