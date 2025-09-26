package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeSortTest {
    @Test
    void testRandomArray() {
        Random rnd = new Random();
        int[] arr = rnd.ints(1000, -10000, 10000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);

        int[] copy = arr.clone();
        Metrics m = new Metrics();
        MergeSort.sort(copy, m);

        assertArrayEquals(expected, copy);
    }

    @Test
    void testSmallArray() {
        int[] arr = {5, 2, 9, 1, 3};
        int[] expected = arr.clone();
        Arrays.sort(expected);

        Metrics m = new Metrics();
        MergeSort.sort(arr, m);

        assertArrayEquals(expected, arr);
    }
}
