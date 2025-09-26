package org.example;

public class Metrics {
    private long comparisons = 0;
    private long swaps = 0;
    private long allocations = 0;
    private int maxDepth = 0;

    public void countComparison() { comparisons++; }
    public void countSwap() { swaps++; }
    public void countAllocation(long amt) { allocations += amt; }
    public void updateDepth(int d) { if (d > maxDepth) maxDepth = d; }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getAllocations() { return allocations; }
    public int getMaxDepth() { return maxDepth; }

    @Override
    public String toString() {
        return "Metrics{" +
                "comparisons=" + comparisons +
                ", swaps=" + swaps +
                ", allocations=" + allocations +
                ", maxDepth=" + maxDepth +
                '}';
    }
}
