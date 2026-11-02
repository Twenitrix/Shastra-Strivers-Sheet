# Editorial: Count Square Submatrices with All Ones

## Problem Overview

Given an \(m \times n\) binary matrix, find the total number of square submatrices that contain only `1`s.

A square submatrix can have any side length, from \(1\) up to the minimum of the matrix's dimensions.

**Example:**

Input:
```text
matrix = [
  [0, 1, 1, 1],
  [1, 1, 1, 1],
  [0, 1, 1, 1]
]
```

Output:
```text
15
```

## Approach: Dynamic Programming

The main observation is that a square of side length greater than one can be formed only if the current cell and its neighboring squares support it.

Define `dp[i][j]` as the side length of the largest all-ones square whose bottom-right corner is at cell `(i, j)`.

- If `matrix[i][j] == 0`, then `dp[i][j] = 0`.
- If the cell is in the first row or first column and contains `1`, then `dp[i][j] = 1`.
- Otherwise, if the cell contains `1`, calculate:

\[
dp[i][j] = 1 + \min
\begin{cases}
dp[i-1][j] \\
dp[i][j-1] \\
dp[i-1][j-1]
\end{cases}
\]

The minimum of the top, left, and top-left values determines the largest square that can be extended to the current cell.

Every square size ending at a cell contributes to the answer. Therefore, add each `dp[i][j]` to the total.

## Algorithm

1. Initialize a DP matrix of the same dimensions as the input.
2. Traverse every cell of the matrix.
3. If the cell contains `0`, set its DP value to `0`.
4. If it contains `1` and lies on the first row or column, set its DP value to `1`.
5. Otherwise, calculate its DP value using the minimum of the top, left, and top-left neighbors, plus one.
6. Add each DP value to the total answer.
7. Return the total.

## Java Implementation

```java
class Solution {
    public int countSquares(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = 1 + Math.min(
                            dp[i - 1][j],
                            Math.min(dp[i][j - 1], dp[i - 1][j - 1])
                        );
                    }

                    count += dp[i][j];
                }
            }
        }

        return count;
    }
}
```

## Dry Run

Consider the matrix:

```text
1 1
1 1
```

The DP matrix becomes:

```text
1 1
1 2
```

- The top-left cell contributes `1` square.
- The top-right cell contributes `1` square.
- The bottom-left cell contributes `1` square.
- The bottom-right cell contributes `2` squares: one of size \(1 \times 1\) and one of size \(2 \times 2\).

Total:

\[
1 + 1 + 1 + 2 = 5
\]

Output: `5`

## Complexity Analysis

Let \(m\) be the number of rows and \(n\) be the number of columns.

- **Time Complexity:** \(O(mn)\), because every cell is processed once.
- **Space Complexity:** \(O(mn)\), because the DP matrix stores one value for every cell.

## Key Takeaways

- Dynamic programming avoids repeatedly checking every possible square.
- `dp[i][j]` represents the largest all-ones square ending at `(i, j)`.
- The minimum of three neighboring DP values determines the largest square.
- Summing all DP values gives the total number of square submatrices containing only ones.

