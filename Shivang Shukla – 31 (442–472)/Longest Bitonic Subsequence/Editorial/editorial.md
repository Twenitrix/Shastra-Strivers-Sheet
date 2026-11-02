# Editorial: Longest Bitonic Subsequence

## Problem Overview

Given an array of integers, find the length of the longest bitonic subsequence.

A bitonic subsequence first increases and then decreases. Either the increasing or decreasing part may be empty, but the sequence must contain at least one element.

**Example:**

Input:
```text
nums = [1, 11, 2, 10, 4, 5, 2, 1]
```

Output:
```text
6
```

Explanation: One longest bitonic subsequence is `[1, 2, 10, 5, 2, 1]`.

## Approach: Dynamic Programming

The key observation is that a bitonic subsequence can be divided into two parts:

- The longest increasing subsequence ending at each index.
- The longest decreasing subsequence starting at each index.

Define:

- `dp1[i]` as the length of the longest increasing subsequence ending at index `i`.
- `dp2[i]` as the length of the longest decreasing subsequence starting at index `i`.

Both arrays are initialized with `1`, because every individual element forms a subsequence of length one.

For every index `i`, calculate `dp1[i]` by checking all previous elements. If `nums[j] < nums[i]`, the increasing subsequence ending at `j` can be extended.

Similarly, calculate `dp2[i]` by checking all later elements. If `nums[j] < nums[i]`, the decreasing subsequence starting at `i` can continue to `j`.

Treating index `i` as the peak, the bitonic subsequence length is:

\[
dp1[i] + dp2[i] - 1
\]

Subtract one because the peak element is counted in both arrays.

The answer is the maximum value over all indices.

## Algorithm

1. Initialize `dp1` and `dp2` with `1`.
2. Calculate the longest increasing subsequence ending at every index.
3. Calculate the longest decreasing subsequence starting at every index.
4. For each index, combine the two lengths and subtract one.
5. Return the maximum combined length.

## Java Implementation

```java
class Solution {
    public int LongestBitonicSequence(int n, int[] nums) {
        int[] dp1 = new int[n];
        int[] dp2 = new int[n];

        for (int i = 0; i < n; i++) {
            dp1[i] = 1;
            dp2[i] = 1;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp1[i] = Math.max(dp1[i], dp1[j] + 1);
                }
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                if (nums[j] < nums[i]) {
                    dp2[i] = Math.max(dp2[i], dp2[j] + 1);
                }
            }
        }

        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            if (dp1[i] > 1 && dp2[i] > 1) {
                maxLen = Math.max(maxLen, dp1[i] + dp2[i] - 1);
            }
        }

        return maxLen;
    }
}
```

## Dry Run

Input:

```text
nums = [1, 2, 3, 2, 1]
```

The longest increasing subsequence lengths are:

```text
dp1 = [1, 2, 3, 2, 1]
```

The longest decreasing subsequence lengths are:

```text
dp2 = [1, 2, 3, 2, 1]
```

Taking `3` as the peak:

\[
3 + 3 - 1 = 5
\]

The longest bitonic subsequence is `[1, 2, 3, 2, 1]`.

Output: `5`

## Complexity Analysis

Let \(n\) be the number of elements.

- **Time Complexity:** \(O(n^2)\), because both DP calculations use nested loops.
- **Space Complexity:** \(O(n)\), for the two DP arrays.

## Key Takeaways

- A bitonic subsequence increases and then decreases.
- Calculate LIS ending at each index and LDS starting at each index.
- Combine both lengths using `dp1[i] + dp2[i] - 1`.
- For a strictly bitonic sequence, the peak must have both an increasing and a decreasing part, so both lengths must be greater than one.

