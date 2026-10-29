# Editorial: Number of Longest Increasing Subsequences

## Problem Overview

Given an integer array `nums`, find the number of longest strictly increasing subsequences (LIS).

A subsequence is formed by deleting zero or more elements without changing the order of the remaining elements.

**Example:**

Input:
```text
nums = [1, 3, 5, 4, 7]
```

Output:
```text
2
```

Explanation:

The longest increasing subsequences are `[1, 3, 5, 7]` and `[1, 3, 4, 7]`.

Both have length `4`, so the answer is `2`.

## Approach: Dynamic Programming

Use two arrays:

- `dp[i]`: Length of the longest increasing subsequence ending at index `i`.
- `count[i]`: Number of increasing subsequences of length `dp[i]` ending at index `i`.

Initially, every element forms a subsequence of length `1`, so `dp[i] = 1` and `count[i] = 1`.

For every index `i`, check all previous indices `j`.

If `nums[j] < nums[i]`, the element at `i` can extend a subsequence ending at `j`.

- If extending it produces a longer subsequence, update `dp[i]` and copy `count[j]`.
- If it produces another subsequence of the same maximum length, add `count[j]` to `count[i]`.

Finally, sum the counts of all positions whose `dp[i]` equals the global LIS length.

## Algorithm

1. Initialize `dp` and `count` with all values equal to `1`.
2. Set `maxLength = 1`.
3. For each index `i`, examine all previous indices `j`.
4. If `nums[j] < nums[i]`:
    - If `dp[j] + 1 > dp[i]`, update the length and set `count[i] = count[j]`.
    - If `dp[j] + 1 == dp[i]`, add `count[j]` to `count[i]`.
5. Track the maximum LIS length.
6. Sum `count[i]` for every index whose `dp[i]` equals that maximum length.
7. Return the sum.

## Java Implementation

```java id="p5kq8a"
class Solution {
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int[] count = new int[n];

        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            count[i] = 1;
        }

        int maxLength = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        count[i] = count[j];
                    } else if (dp[j] + 1 == dp[i]) {
                        count[i] += count[j];
                    }
                }
            }

            maxLength = Math.max(maxLength, dp[i]);
        }

        int result = 0;

        for (int i = 0; i < n; i++) {
            if (dp[i] == maxLength) {
                result += count[i];
            }
        }

        return result;
    }
}
```

## Dry Run

Input:

```text
nums = [1, 2, 2, 3]
```

The longest increasing subsequences are:

- `[1, 2, 3]`, using the first `2`.
- `[1, 2, 3]`, using the second `2`.

The maximum length is `3`, and there are two subsequences of that length.

Output:

```text
2
```

## Complexity Analysis

Let \(n\) be the length of the array.

- **Time Complexity:** \(O(n^2)\), because every element is compared with all preceding elements.
- **Space Complexity:** \(O(n)\), for the `dp` and `count` arrays.

## Key Takeaways

- `dp[i]` stores the LIS length ending at index `i`.
- `count[i]` stores how many subsequences achieve that length.
- Replace the count when a longer subsequence is found.
- Add to the count when another subsequence of the same maximum length is found.
- Sum counts only at indices that achieve the global LIS length.

