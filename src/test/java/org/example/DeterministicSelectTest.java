package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeterministicSelectTest {
    @Test
    void testRandomArray() {
        Random rnd = new Random();
        int[] arr = rnd.ints(200, -1000, 1000).toArray();
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        int k = arr.length / 2;
        Metrics m = new Metrics();
        int result = DeterministicSelect.select(arr.clone(), k, m);

        assertEquals(sorted[k], result);
    }

    @Test
    void testSmallArray() {
        int[] arr = {7, 2, 5, 3, 9};
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        for (int k = 0; k < arr.length; k++) {
            Metrics m = new Metrics();
            int result = DeterministicSelect.select(arr.clone(), k, m);
            assertEquals(sorted[k], result);
        }
    }
}
