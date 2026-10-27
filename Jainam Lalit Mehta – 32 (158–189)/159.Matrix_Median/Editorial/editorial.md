# Editorial: 159. Matrix Median

## Difficulty

Hard

## Prerequisites

* Binary search on the answer
* Binary search within a sorted array
* Counting with sorted rows

## Problem Statement

Given a matrix whose rows are sorted, with an odd number of cells, find the median of all values.

## Input Format

Line 1: `rows cols`. Then `rows` lines of `cols` integers.

## Constraints

* `1 <= rows * cols <= 1000000`, and `rows * cols` is odd.
* `1 <= matrix[i][j] <= 1000000000`
* Each row is sorted.

## Output Format

A single integer.

## Explanation & Approach

### Step 1: Define the Target

With `total = rows * cols` cells, the median is the element at index `total / 2` in sorted order. Its value is the smallest value `v` for which at least `total / 2 + 1` cells are less than or equal to `v`.

### Step 2: Binary Search on the Value

The answer lies between the smallest and largest value in the matrix. Search that range. For a middle value `mid`:

* If at least `total / 2 + 1` cells are `<= mid`, the answer is `mid` or smaller, so set `high = mid`.
* Otherwise the answer is greater than `mid`, so set `low = mid + 1`.

### Step 3: Count Cells `<= value`

Each row is sorted, so a binary search finds the first position with a value greater than `value`. The number of cells `<= value` in that row is that position. Adding these counts over all rows gives the total.

### Walkthrough

```text
matrix = [[1,4,9],[2,5,6],[3,7,8]], total = 9, needed = 5

low = 1, high = 9
mid = 5: cells <= 5 are 1,4,2,5,3 = 5  >= 5, so high = 5
mid = 3: cells <= 3 are 1,2,3 = 3       < 5, so low = 4
mid = 4: cells <= 4 are 1,2,3,4 = 4     < 5, so low = 5
low = high = 5, answer 5
```

## Example

### Input

```text
3 3
1 4 9
2 5 6
3 7 8
```

### Output

```text
5
```

## Java Solution

```java
public class Solution {

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
```

## Time Complexity

The value search runs `O(log(max - min))`, which is at most about 30 iterations for values up to `10^9`. Each count checks every row with a binary search, which is `O(rows * log cols)`.

**Time Complexity: `O(rows * log(cols) * log(maxValue))`**

## Space Complexity

The solution uses a constant number of variables.

**Space Complexity: `O(1)`** auxiliary space.

## Key Takeaway

> When the answer is a value rather than a position, binary search on the value and count how many cells are at most that value. Sorted rows make each count a fast binary search.
