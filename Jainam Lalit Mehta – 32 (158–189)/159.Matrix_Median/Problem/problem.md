# Problem 159: Matrix Median

**Difficulty:** Medium | **Tags:** Array, Binary Search, Matrix

## Problem Statement

You are given a matrix `matrix` with `rows` rows and `cols` columns. Each row is sorted in non-decreasing order. The total number of cells, `rows * cols`, is odd.

Find the median of all the values in the matrix. The median is the middle value after all cells are put in one sorted list.

## Example 1

```text
Input: matrix = [[1,4,9],[2,5,6],[3,7,8]]
Output: 5
Explanation: Sorted, the values are 1 2 3 4 5 6 7 8 9. The median is 5.
```

## Example 2

```text
Input: matrix = [[1,3,8],[2,3,4],[1,2,5]]
Output: 3
Explanation: Sorted, the values are 1 1 2 2 3 3 4 5 8. The median is 3.
```

## Constraints

* `1 <= rows, cols <= 100000`
* `1 <= rows * cols <= 1000000`
* `rows * cols` is odd.
* `1 <= matrix[i][j] <= 1000000000`
* Each row is sorted in non-decreasing order.

## Input Format

The first line contains two integers, `rows` and `cols`. Each of the next `rows` lines contains `cols` integers separated by single spaces.

## Output Format

Print a single integer: the median.

For Example 1 the input file is:

```text
3 3
1 4 9
2 5 6
3 7 8
```

and the output file is:

```text
5
```

## Approach

Binary search on the answer value rather than sorting all the cells.

1. Start with the range from the smallest value to the largest value in the matrix.
2. For a candidate value `mid`, count how many cells are less than or equal to `mid`. Each row is sorted, so a binary search per row gives its count.
3. The median is the smallest value with at least `rows * cols / 2 + 1` cells less than or equal to it. If the count reaches that amount, search lower. Otherwise search higher.

Each count takes `O(rows * log cols)`, and the search runs `O(log(max value))` times.
