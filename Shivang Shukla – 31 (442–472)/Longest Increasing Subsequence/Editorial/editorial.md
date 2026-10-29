# Editorial: Longest Increasing Subsequence

## Problem Overview

Given an integer array `nums`, find the length of the longest strictly increasing subsequence.

A subsequence is formed by deleting zero or more elements without changing the order of the remaining elements.

**Example:**

Input:
```text
nums = [10, 9, 2, 5, 3, 7, 101, 18]
```

Output:
```text
4
```

Explanation: One longest increasing subsequence is `[2, 3, 7, 101]`.

## Approach: Dynamic Programming

The main idea is to calculate the longest increasing subsequence ending at each index.

Define `dp[i]` as the length of the longest increasing subsequence ending at index `i`.

Initially, set every `dp[i]` to `1`, because each element forms a subsequence of length one.

For every index `i`, check all previous indices `j`. If `nums[j] < nums[i]`, the subsequence ending at `j` can be extended by `nums[i]`.

Therefore:

\[
dp[i] = \max(dp[i], dp[j] + 1)
\]

The answer is the maximum value in the DP array.

## Algorithm

1. Initialize a DP array of size `n` with all values set to `1`.
2. Traverse the array using index `i`.
3. For each `i`, check all previous indices `j`.
4. If `nums[j] < nums[i]`, update `dp[i]` using `dp[j] + 1`.
5. Track the maximum value in the DP array.
6. Return the maximum length.

## Java Implementation

```java id="y7k5vb"
class Solution {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int maxLen = 1;

        for (int i = 0; i < n; i++) {
            dp[i] = 1;

            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }
}
```

## Dry Run

Input:

```text
nums = [3, 1, 2, 5]
```

- `3`: `dp[0] = 1`
- `1`: `dp[1] = 1`
- `2`: `1` can be extended to `[1, 2]`, so `dp[2] = 2`
- `5`: the subsequence `[1, 2]` can be extended to `[1, 2, 5]`, so `dp[3] = 3`

Final DP array:

```text
dp = [1, 1, 2, 3]
```

Output: `3`

## Complexity Analysis

Let \(n\) be the length of the array.

- **Time Complexity:** \(O(n^2)\), due to the nested loops.
- **Space Complexity:** \(O(n)\), for the DP array.

## Key Takeaways

- A subsequence preserves the relative order of elements.
- Each DP value represents the longest increasing subsequence ending at that index.
- Only strictly smaller previous elements can extend the subsequence.
- The answer is the maximum value in the DP array.

