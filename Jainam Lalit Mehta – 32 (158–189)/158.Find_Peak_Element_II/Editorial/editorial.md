# Editorial: 158. Find a Peak Element II

## Difficulty

Medium

## Prerequisites

* Binary search on an index range
* Finding the maximum of a column
* The 1D peak element idea, extended to a grid

## Problem Statement

Given a grid with no two adjacent cells equal, return the position of any cell that is strictly greater than all its in-grid neighbours.

## Input Format

Line 1: `rows cols`. Then `rows` lines of `cols` integers.

## Constraints

* `1 <= rows, cols <= 500`
* `1 <= grid[i][j] <= 100000`
* No two adjacent cells are equal.

## Output Format

One peak as `row col`. The test files list all peaks, and any of them is accepted.

## Explanation & Approach

### Step 1: Reduce to a 1D Problem

Binary search on the columns. Choose the middle column `mid`. Within that column, find the row with the largest value, `bestRow`.

Because `grid[bestRow][mid]` is the largest cell in its column, it is already greater than the cells above and below it. It only needs to be compared with its left and right neighbours.

### Step 2: Decide Which Side to Keep

* If `grid[bestRow][mid]` is greater than both `grid[bestRow][mid - 1]` and `grid[bestRow][mid + 1]`, it is a peak. Return it. The border value `-1` handles a missing neighbour at the grid edge.
* If the right neighbour is greater, follow the increase to the right. The columns to the right must contain a peak, because the values can't keep rising forever: they stop at the grid edge, where the border value `-1` is smaller than everything.
* Otherwise the left neighbour is greater, and the same argument gives a peak on the left.

Adjacent values are never equal, so the two neighbours can't tie with the centre cell.

### Step 3: Narrow the Range

Keep only the side that must contain a peak, and repeat from Step 1. Each step halves the column range.

### Walkthrough

```text
grid = [[10,20,15],[21,30,14],[7,16,32]]

low=0, high=2, mid=1
  column 1 = [20, 30, 16], largest is 30 at row 1
  left = 21, right = 14
  30 > 21 and 30 > 14, so return [1,1]
```

## Example

### Input

```text
2 2
1 4
3 2
```

### Output

```text
0 1
1 0
```

## Java Solution

```java
public class Solution {

    public int[] locatePeak(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int low = 0;
        int high = cols - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            int bestRow = 0;
            for (int r = 1; r < rows; r++) {
                if (grid[r][mid] > grid[bestRow][mid]) {
                    bestRow = r;
                }
            }

            int value = grid[bestRow][mid];
            int leftValue = mid > 0 ? grid[bestRow][mid - 1] : -1;
            int rightValue = mid < cols - 1 ? grid[bestRow][mid + 1] : -1;

            if (value > leftValue && value > rightValue) {
                return new int[]{bestRow, mid};
            } else if (rightValue > value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return new int[]{-1, -1};
    }
}
```

## Time Complexity

The binary search runs `O(log cols)` times. Each iteration scans one column of `rows` cells.

**Time Complexity: `O(rows * log(cols))`**, which meets the required `O(m log n)` bound.

## Space Complexity

The solution uses a constant number of variables.

**Space Complexity: `O(1)`** auxiliary space.

## Key Takeaway

> Binary search on the columns, and use the column maximum to decide which side must contain a peak. The maximum in a column is always a peak candidate in that column, so only its left and right neighbours need checking.
