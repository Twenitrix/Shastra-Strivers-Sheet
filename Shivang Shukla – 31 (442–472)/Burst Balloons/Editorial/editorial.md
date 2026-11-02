# Editorial: Burst Balloons

## 1. Problem Overview

We are given an array `nums` where each element represents the value of a balloon.

When we burst balloon `i`, we gain coins equal to:

`nums[left] * nums[i] * nums[right]`

Here, `left` and `right` represent the nearest balloons that have not been burst. If there is no balloon on one side, its value is considered `1`.

Our objective is to burst all balloons in an optimal order to maximize the total coins collected.

## 2. Why Greedy Approaches Fail

Choosing the balloon with the smallest or largest value first does not always produce the maximum result.

The value gained from bursting a balloon depends on its neighboring balloons at that moment. These neighbors change as other balloons are burst.

Therefore, we need Dynamic Programming to explore the optimal order of bursting balloons.

## 3. Approach: Interval Dynamic Programming

Instead of deciding which balloon to burst first, we decide which balloon to burst **last** within a given interval.

When balloon `i` is the last balloon burst in interval `[left, right]`, all other balloons in that interval have already been removed.

Therefore, its neighbors are:

- `left - 1`, the balloon immediately before the interval.
- `right + 1`, the balloon immediately after the interval.

This allows us to calculate the coins gained when bursting `i` last.

## 4. State Definition

Let:

`dp[left][right]`

represent the maximum coins obtainable by bursting all balloons between indices `left` and `right`, inclusive.

We add a balloon with value `1` at both ends of the array to handle boundary conditions.

## 5. State Transition

If balloon `i` is burst last in the interval `[left, right]`, the total coins are:

\[
\begin{aligned}
\text{coins} ={}& a[left-1]\times a[i]\times a[right+1]\\
&+dp[left][i-1]+dp[i+1][right]
\end{aligned}
\]

We try every possible `i` from `left` to `right` and select the maximum value.

\[
dp[left][right]=\max_{i=left}^{right}
\left(
dp[left][i-1]+dp[i+1][right]+
a[left-1]a[i]a[right+1]
\right)
\]

Empty intervals contribute zero coins.

## 6. Algorithm Step-by-Step

1. Add `1` at the beginning and end of the array.
2. Create a two-dimensional DP array initialized to zero.
3. Consider intervals of increasing length, starting from length `1`.
4. For every interval `[left, right]`, try every possible balloon `i` as the last balloon to burst.
5. Calculate the coins gained from bursting `i` last, including the optimal results of the left and right subintervals.
6. Store the maximum value in `dp[left][right]`.
7. Return `dp[1][n]`, which contains the maximum coins obtainable by bursting all original balloons.

## 7. Java Implementation

```java
class Solution {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] a = new int[n + 2];

        a[0] = 1;
        a[n + 1] = 1;

        for (int i = 0; i < n; i++) {
            a[i + 1] = nums[i];
        }

        int[][] dp = new int[n + 2][n + 2];

        for (int len = 1; len <= n; len++) {
            for (int left = 1; left <= n - len + 1; left++) {
                int right = left + len - 1;

                for (int i = left; i <= right; i++) {
                    int coins = a[left - 1] * a[i] * a[right + 1]
                              + dp[left][i - 1]
                              + dp[i + 1][right];

                    dp[left][right] = Math.max(dp[left][right], coins);
                }
            }
        }

        return dp[1][n];
    }
}
```

## 8. Dry Run

Consider:

`nums = [3, 1, 5, 8]`

After adding boundary balloons:

`a = [1, 3, 1, 5, 8, 1]`

The optimal bursting order is:

1. Burst balloon `1`: gain `3 * 1 * 5 = 15`.
2. Burst balloon `5`: gain `3 * 5 * 8 = 120`.
3. Burst balloon `3`: gain `1 * 3 * 8 = 24`.
4. Burst balloon `8`: gain `1 * 8 * 1 = 8`.

Total coins:

`15 + 120 + 24 + 8 = 167`

**Final Answer: `167`**

## 9. Complexity Analysis

### Time Complexity

There are \(O(n^2)\) intervals, and each interval tries up to \(n\) possible last balloons.

\[
O(n^3)
\]

### Space Complexity

The DP table contains \(O(n^2)\) states, and the padded array requires \(O(n)\) space.

\[
O(n^2)
\]

## 10. Key Takeaways

- Greedy strategies fail because bursting a balloon changes its neighbors.
- Choosing the last balloon in an interval makes the subproblems independent.
- Interval DP solves the problem by considering every possible last balloon.
- Padding the array with `1` simplifies boundary handling.
- The optimal solution uses \(O(n^3)\) time and \(O(n^2)\) space.
