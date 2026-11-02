# Editorial: Minimum Cost to Cut the Stick

## Problem Overview

You are given a stick of length `n` and an array `cuts`, where each element represents a position at which the stick must be cut.

Every time a cut is made, its cost equals the length of the stick segment being cut. The stick is divided into two smaller segments after each cut.

Find the minimum total cost required to perform all the cuts.

**Example:**

Input:
```text
n = 7
cuts = [1, 3, 4, 5]
```

Output:
```text
16
```

Explanation:

One optimal order is to cut at positions `3`, `1`, `5`, and `4`.

- Cut at `3`: cost = `7`
- Cut at `1`: cost = `3`
- Cut at `5`: cost = `4`
- Cut at `4`: cost = `2`

Total cost = \(7 + 3 + 4 + 2 = 16\).

## Approach: Interval Dynamic Programming

Choosing the first cut determines the two smaller segments that must be cut afterward. Trying every possible cutting order directly is inefficient.

Instead, use interval DP.

Add the endpoints `0` and `n` to the cuts array, then sort it. Let `dp[i][j]` represent the minimum cost of performing all cuts between `cuts[i]` and `cuts[j]`.

For every possible first cut `k` between `i` and `j`, the total cost is:

\[
\begin{aligned}
cost ={}& cuts[j]-cuts[i]\\
&+ dp[i][k]+dp[k][j]
\end{aligned}
\]

The segment's length is paid once for making the first cut. The remaining cuts are handled by the two subproblems.

Choose the minimum cost among all possible values of `k`.

## Algorithm

1. Add `0` and `n` to the cuts array.
2. Sort the array.
3. Create a DP table initialized to zero.
4. Consider intervals containing increasing numbers of cuts.
5. For each interval `(i, j)`, try every cut `k` between them.
6. Calculate the cost of choosing `k` as the first cut.
7. Store the minimum cost in `dp[i][j]`.
8. Return `dp[0][c + 1]`, where `c` is the original number of cuts.

## Java Implementation

```java
import java.util.*;

class Solution {
    public int minCost(int n, int[] cuts) {
        int c = cuts.length;
        int[] arr = new int[c + 2];

        arr[0] = 0;
        arr[c + 1] = n;

        for (int i = 0; i < c; i++) {
            arr[i + 1] = cuts[i];
        }

        Arrays.sort(arr);

        int[][] dp = new int[c + 2][c + 2];

        for (int i = c; i >= 1; i--) {
            for (int j = i; j <= c; j++) {
                int minCost = Integer.MAX_VALUE;

                for (int k = i; k <= j; k++) {
                    int cost = arr[j + 1] - arr[i - 1]
                            + dp[i][k - 1]
                            + dp[k + 1][j];

                    minCost = Math.min(minCost, cost);
                }

                dp[i][j] = minCost;
            }
        }

        return dp[1][c];
    }
}
```

## Dry Run

Input:

```text
n = 7
cuts = [1, 3, 4, 5]
```

After adding endpoints and sorting:

```text
arr = [0, 1, 3, 4, 5, 7]
```

One optimal cutting sequence is:

1. Cut at `3`: cost = `7`. The segments are `[0, 3]` and `[3, 7]`.
2. Cut at `1`: cost = `3`.
3. Cut at `5`: cost = `4`.
4. Cut at `4`: cost = `2`.

Total:

\[
7+3+4+2=16
\]

Therefore, the minimum cost is `16`.

## Complexity Analysis

Let \(c\) be the number of cuts.

- **Time Complexity:** \(O(c^3)\), because each interval considers every possible first cut.
- **Space Complexity:** \(O(c^2)\), for the DP table.

## Key Takeaways

- This is an interval DP problem.
- The cost of the first cut equals the current segment's length.
- Each possible first cut divides the problem into two independent subproblems.
- Sorting the cut positions makes interval boundaries easy to calculate.
- Bottom-up DP avoids repeatedly solving the same subproblems.

