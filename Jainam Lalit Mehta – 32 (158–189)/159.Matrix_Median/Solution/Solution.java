public class Solution {

    // Binary search on the answer value. For a candidate value v, count how many
    // elements are <= v. The median is the smallest v that has at least half + 1 elements <= v.
    public int findMedian(int[][] matrix) {

        int rows = matrix.length;
        int cols = matrix[0].length;
        int total = rows * cols;
        int needed = total / 2 + 1;

        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;
        for (int[] row : matrix) {
            low = Math.min(low, row[0]);
            high = Math.max(high, row[cols - 1]);
        }

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (countAtMost(matrix, mid) >= needed) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    // Each row is sorted, so a binary search per row finds how many values are <= v.
    private int countAtMost(int[][] matrix, int value) {

        int count = 0;
        for (int[] row : matrix) {
            int lo = 0;
            int hi = row.length;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (row[mid] <= value) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
            count += lo;
        }
        return count;
    }

}
