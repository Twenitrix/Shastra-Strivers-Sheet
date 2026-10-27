# Problem 158: Find a Peak Element II

**Difficulty:** Medium | **Tags:** Array, Binary Search, Matrix

## Problem Statement

You are given a grid `grid` with `rows` rows and `cols` columns. Each cell holds a positive integer, and no two side-by-side cells hold the same value.

A cell is a **peak** if its value is strictly greater than the values of all its neighbours to the left, right, up, and down. The grid is surrounded by an outer border with the value `-1`, so a border cell only has to beat its neighbours inside the grid.

Find any peak and return its position as `[row, col]`, using 0-based indexes. Several peaks may exist, and any one of them is accepted.

The algorithm must run in `O(rows * log(cols))` or `O(cols * log(rows))` time.

## Example 1

```text
Input: grid = [[1,4],[3,2]]
Output: [0,1]
Explanation: Both 3 and 4 are peaks, so [1,0] and [0,1] are both accepted.
```

## Example 2

```text
Input: grid = [[10,20,15],[21,30,14],[7,16,32]]
Output: [1,1]
Explanation: Both 30 and 32 are peaks, so [1,1] and [2,2] are both accepted.
```

## Constraints

* `1 <= rows, cols <= 500`
* `1 <= grid[i][j] <= 100000`
* No two adjacent cells have the same value.

## Input Format

The first line contains two integers, `rows` and `cols`. Each of the next `rows` lines contains `cols` integers separated by single spaces.

## Output Format

Print the row and column of one peak as two integers separated by a space, for example `0 1`.

Any peak is accepted. In the test files, the output lists every valid peak, one per line, sorted by row and then column. The checker accepts any line of the output file.

For Example 1 the input file is:

```text
2 2
1 4
3 2
```

and the output file is:

```text
0 1
1 0
```

## Approach

Binary search on the columns.

1. Take the middle column `mid`.
2. Find the largest value in that column. Call its row `bestRow`. This cell is at least as big as every cell above and below it in the column.
3. Compare the cell with its left and right neighbours.
   * If it is larger than both, it is a peak, so return it.
   * If the right neighbour is larger, a peak must exist in the columns to the right. Search there.
   * Otherwise a peak exists in the columns to the left. Search there.

Each step halves the column range, and each step scans one column, so the total time is `O(rows * log(cols))`.
