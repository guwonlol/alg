package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class QuickSortTest {
    @Test
    void testRandomArray() {
        Random rnd = new Random();
        int[] arr = rnd.ints(1000, -10000, 10000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);

        int[] copy = arr.clone();
        Metrics m = new Metrics();
        QuickSort.sort(copy, m);

        assertArrayEquals(expected, copy);
    }

    @Test
    void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = arr.clone();

        Metrics m = new Metrics();
        QuickSort.sort(arr, m);

        assertArrayEquals(expected, arr);
    }
}
