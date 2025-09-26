package org.example;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int n = 10000;
        int[] arr = new Random().ints(n, 0, 100000).toArray();

        runMergeSort(Arrays.copyOf(arr, arr.length));
        runQuickSort(Arrays.copyOf(arr, arr.length));
        runDeterministicSelect(Arrays.copyOf(arr, arr.length));
        runClosestPair(n);
    }

    private static void runMergeSort(int[] arr) {
        Metrics m = new Metrics();
        long start = System.nanoTime();
        MergeSort.sort(arr, m);
        long end = System.nanoTime();
        System.out.printf("MergeSort: time=%.3f ms, comps=%d, swaps=%d, depth=%d, allocs=%d%n",
                (end - start) / 1e6, m.getComparisons(), m.getSwaps(), m.getMaxDepth(), m.getAllocations());
    }

    private static void runQuickSort(int[] arr) {
        Metrics m = new Metrics();
        long start = System.nanoTime();
        QuickSort.sort(arr, m);
        long end = System.nanoTime();
        System.out.printf("QuickSort: time=%.3f ms, comps=%d, swaps=%d, depth=%d, allocs=%d%n",
                (end - start) / 1e6, m.getComparisons(), m.getSwaps(), m.getMaxDepth(), m.getAllocations());
    }

    private static void runDeterministicSelect(int[] arr) {
        Metrics m = new Metrics();
        int k = arr.length / 2; // медиана
        long start = System.nanoTime();
        int value = DeterministicSelect.select(arr, k, m);
        long end = System.nanoTime();
        System.out.printf("DeterministicSelect: k=%d, value=%d, time=%.3f ms, comps=%d, swaps=%d, depth=%d, allocs=%d%n",
                k, value, (end - start) / 1e6, m.getComparisons(), m.getSwaps(), m.getMaxDepth(), m.getAllocations());
    }

    private static void runClosestPair(int n) {
        Random rnd = new Random();
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPair.Point(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
        }
        Metrics m = new Metrics();
        long start = System.nanoTime();
        double dist = ClosestPair.solve(pts, m);
        long end = System.nanoTime();
        System.out.printf("ClosestPair: dist=%.5f, time=%.3f ms, comps=%d, swaps=%d, depth=%d, allocs=%d%n",
                dist, (end - start) / 1e6, m.getComparisons(), m.getSwaps(), m.getMaxDepth(), m.getAllocations());
    }
}
