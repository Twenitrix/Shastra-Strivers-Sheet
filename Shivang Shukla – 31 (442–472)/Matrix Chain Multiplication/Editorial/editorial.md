# Editorial: Matrix Chain Multiplication

## Problem Overview

Given an array `arr` of dimensions representing a sequence of matrices, find the minimum number of scalar multiplications required to multiply the entire chain.

If the array has length `N`, there are `N - 1` matrices. Matrix `i` has dimensions `arr[i - 1] × arr[i]`.

Matrix multiplication is associative, meaning the order of grouping can change the number of operations without changing the final result.

**Example:**

Input:
```text
arr = [10, 20, 30, 40, 50]
```

Output:
```text
38000
```

Explanation: The optimal grouping is `(A1 × A2) × (A3 × A4)`.

- `A1 × A2`: \(10 \times 20 \times 30 = 6000\)
- `A3 × A4`: \(30 \times 40 \times 50 = 60000\)
- Final multiplication: \(10 \times 30 \times 50 = 15000\)

Total: \(6000 + 60000 + 15000 = 81000\).

The minimum is actually obtained by `((A1 × A2) × A3) × A4`:

- `A1 × A2`: \(10 \times 20 \times 30 = 6000\)
- Result × `A3`: \(10 \times 30 \times 40 = 12000\)
- Result × `A4`: \(10 \times 40 \times 50 = 20000\)

Total: \(38000\).

## Approach: Dynamic Programming

The key observation is that every possible multiplication order can be represented by choosing the final split point.

Let `dp[i][j]` represent the minimum number of scalar multiplications needed to multiply matrices from `i` to `j`.

- If `i == j`, only one matrix exists, so the cost is `0`.
- Otherwise, try every split point `k` from `i` to `j - 1`.

For each split:

- Multiply matrices `i` through `k`.
- Multiply matrices `k + 1` through `j`.
- Multiply the two resulting matrices.

The cost of the final multiplication is:

\[
arr[i-1] \times arr[k] \times arr[j]
\]

Therefore:

\[
dp[i][j] =
\min_{i \leq k < j}
\left(
dp[i][k] + dp[k+1][j] +
arr[i-1]arr[k]arr[j]
\right)
\]

The answer is `dp[1][N - 1]`.

## Algorithm

1. Create a DP table of size `N × N`.
2. Initialize diagonal entries to `0`.
3. Consider chains in increasing order of length.
4. For each chain from `i` to `j`, try every split point `k`.
5. Calculate the left cost, right cost, and final multiplication cost.
6. Store the minimum cost in `dp[i][j]`.
7. Return `dp[1][N - 1]`.

## Java Implementation

```java
class Solution {
    static int matrixMultiplication(int N, int arr[]) {
        int[][] dp = new int[N][N];

        for (int i = N - 1; i >= 1; i--) {
            for (int j = i + 1; j < N; j++) {
                int minCost = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j]
                            + arr[i - 1] * arr[k] * arr[j];

                    minCost = Math.min(minCost, cost);
                }

                dp[i][j] = minCost;
            }
        }

        return dp[1][N - 1];
    }
}
```

## Dry Run

Input:

```text
arr = [10, 20, 30]
```

There are two matrices:

- `A1`: \(10 \times 20\)
- `A2`: \(20 \times 30\)

Only one multiplication is possible:

\[
10 \times 20 \times 30 = 6000
\]

Therefore, the minimum number of scalar multiplications is `6000`.

## Complexity Analysis

Let \(N\) be the length of the dimension array.

- **Time Complexity:** \(O(N^3)\), because there are two DP dimensions and each state tries all possible split points.
- **Space Complexity:** \(O(N^2)\), for the DP table.

## Key Takeaways

- Matrix Chain Multiplication minimizes the cost of multiplying a sequence of matrices.
- The answer depends on the optimal split point.
- Try every possible split and select the minimum cost.
- Dynamic programming avoids recalculating costs for the same subchains.

