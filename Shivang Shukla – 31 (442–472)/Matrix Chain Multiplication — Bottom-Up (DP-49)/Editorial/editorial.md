# Editorial: Matrix Chain Multiplication — Bottom-Up (DP-49)

## Problem Overview

Given an array `arr` representing the dimensions of a sequence of matrices, determine the minimum number of scalar multiplications required to multiply the entire chain.

If `arr` has length `N`, there are `N - 1` matrices, where matrix `i` has dimensions `arr[i - 1] × arr[i]`.

**Example:**

Input:
```text
arr = [10, 20, 30, 40]
```

Output:
```text
18000
```

Explanation: The optimal grouping is `(A1 × A2) × A3`.

- Multiply `A1` and `A2`: \(10 \times 20 \times 30 = 6000\)
- Multiply the result by `A3`: \(10 \times 30 \times 40 = 12000\)

Total cost: `18000`.

## Approach: Bottom-Up Dynamic Programming

The bottom-up approach solves smaller subproblems first and uses their results to calculate larger subproblems.

Define `dp[i][j]` as the minimum multiplication cost for matrices `i` through `j`.

The base case is:

\[
dp[i][i] = 0
\]

A single matrix requires no multiplication.

For each chain, try every possible split point `k`, where `i <= k < j`. The total cost is:

\[
dp[i][k] + dp[k+1][j] + arr[i-1]\cdot arr[k]\cdot arr[j]
\]

The first two terms represent the costs of multiplying the left and right subchains. The final term represents multiplying their resulting matrices.

Process the starting index `i` from right to left and the ending index `j` from `i + 1` to the end. This ensures that the smaller subproblems are already computed.

## Algorithm

1. Create a two-dimensional DP table of size `N × N`.
2. Initialize diagonal entries to `0`.
3. Traverse `i` from `N - 1` down to `1`.
4. For each `i`, traverse `j` from `i + 1` to `N - 1`.
5. Try every split point `k` between `i` and `j - 1`.
6. Calculate the multiplication cost and update the minimum.
7. Store the minimum in `dp[i][j]`.
8. Return `dp[1][N - 1]`.

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

Therefore:

```text
Output: 6000
```

## Complexity Analysis

Let \(N\) be the length of the dimension array.

- **Time Complexity:** \(O(N^3)\), because every DP state considers all possible split points.
- **Space Complexity:** \(O(N^2)\), for storing the DP table.

## Key Takeaways

- Bottom-up DP solves smaller chains before larger chains.
- A single matrix has multiplication cost zero.
- Every possible split point must be considered to find the minimum cost.
- Tabulation avoids the recursive call overhead of a memoized solution.

