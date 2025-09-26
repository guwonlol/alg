Divide & Conquer Algorithms Project Report
This report analyzes four divide-and-conquer algorithms: MergeSort, QuickSort, DeterministicSelect, and Closest Pair of Points. It covers their architecture, recurrence analysis, expected performance plots, and alignment between theoretical and empirical results.
Architecture Notes
Each algorithm leverages divide-and-conquer principles, with the Metrics class tracking time, comparisons, swaps, allocations, and recursion depth.

MergeSort:
Depth Control: Depth is tracked via Metrics.updateDepth per recursive call, bounded at log₂ n due to balanced splits.
Allocations: A single reusable buffer (O(n) space) is allocated for merging, minimizing memory churn. No additional arrays are created.


QuickSort:
Depth Control: Uses smaller-first recursion to bound stack depth to O(log n), tracked via Metrics.updateDepth. Randomized pivots ensure expected balance.
Allocations: Operates in-place, with no auxiliary arrays except for recursive call stack, minimizing GC overhead.


DeterministicSelect:
Depth Control: Tracks depth in selectInner via Metrics.updateDepth, bounded at O(log n) due to median-of-medians pivot eliminating ≥3n/10 elements per recursion.
Allocations: Creates an O(n) array copy in select to preserve input and an O(n/5) medians array in medianOfMedians. In-place partitioning reduces further allocations.


Closest Pair of Points:
Depth Control: Depth is log₂ n, tracked via Metrics.updateDepth, due to balanced splitting on the x-axis.
Allocations: Temporary arrays for sorting (O(n) space) and strip processing (O(n) space) are allocated, triggering GC for large n.



Recurrence Analysis
MergeSort

Method Used: Divides the array into two halves, recursively sorts them, and merges in Θ(n) time.
Recurrence: T(n) = 2T(n/2) + Θ(n), where 2T(n/2) is for recursive sorts and Θ(n) is for merging.
Θ-Result: By Master Theorem (a = 2, b = 2, f(n) = Θ(n)), log_b a = 1, so case 2 applies: T(n) = Θ(n log n). The balanced splits ensure predictable performance.

QuickSort

Method Used: Selects a random pivot, partitions the array, and recurses on both subarrays, with smaller-first recursion to optimize stack depth.
Recurrence: T(n) = T(k) + T(n-k-1) + Θ(n), where k is the pivot position. Randomized pivots yield expected k ≈ n/2.
Θ-Result: Expected T(n) = Θ(n log n) via Master Theorem for balanced partitions. Worst-case T(n) = Θ(n²) is rare due to randomization.

DeterministicSelect

Method Used: Uses median-of-medians to select a pivot, partitions the array, and recurses on the subarray containing the k-th element.
Recurrence: T(n) = T(n/5) + T(7n/10) + Θ(n), where T(n/5) is for median-of-medians, T(7n/10) is for the larger subarray, and Θ(n) is for partitioning.
Θ-Result: By Akra-Bazzi, with p ≈ 1 (1/5^p + 7/10^p = 1), T(n) = Θ(n), ensuring linear time due to guaranteed good pivots.

Closest Pair of Points

Method Used: Splits points by x-coordinate, recursively finds closest pairs in halves, and checks a strip of width 2d in Θ(n) time.
Recurrence: T(n) = 2T(n/2) + Θ(n), where 2T(n/2) is for recursive calls and Θ(n) is for sorting and strip processing.
Θ-Result: By Master Theorem (a = 2, b = 2, f(n) = Θ(n)), case 2 applies: T(n) = Θ(n log n), matching the optimal bound.

Experimental Results
Time vs. Input Size

MergeSort: Scales as Θ(n log n), with a smooth curve due to predictable merging costs.
QuickSort: Expected Θ(n log n), slightly faster than MergeSort due to in-place operations, but varies with pivot quality.
DeterministicSelect: Scales linearly (Θ(n)), outperforming sorting-based methods for single-element queries.
Closest Pair: Scales as Θ(n log n), dominated by initial sorting.

Depth vs. Input Size

MergeSort: Depth ≈ log₂ n, a straight logarithmic curve.
QuickSort: Expected depth ≤ 2 log₂ n, with minor fluctuations due to randomization.
DeterministicSelect: Depth O(log n), typically 10–20 for n = 10^6, due to pivot quality.
Closest Pair: Depth log₂ n, consistent with balanced splits.

Constant Factors

MergeSort: Sequential access in merging is cache-friendly, but the O(n) buffer allocation triggers GC for large n.
QuickSort: In-place partitioning reduces allocations, but non-sequential access on nearly-sorted data increases cache misses.
DeterministicSelect: Cache-friendly for small groups (n ≤ 5) in medianOfMedians, but large n causes cache misses in partitioning and GC overhead from O(n) array copy and O(n/5) medians array.
Closest Pair: Sorting is cache-efficient, but strip processing and temporary arrays (O(n)) cause cache misses and GC overhead for large n.

Summary
Theoretical complexities (MergeSort and Closest Pair: Θ(n log n); QuickSort: expected Θ(n log n); DeterministicSelect: Θ(n)) align with empirical scaling observed in tests. MergeSort’s stability contrasts with QuickSort’s faster average-case performance due to in-place operations. DeterministicSelect’s linear time is robust but has higher constants from array copying and median computations. Closest Pair matches its Θ(n log n) bound, with overhead from sorting. Mismatches arise from cache effects (e.g., non-sequential access in QuickSort and DeterministicSelect) and GC (e.g., allocations in MergeSort and Closest Pair), but the Java runtime’s optimizations keep deviations minimal.