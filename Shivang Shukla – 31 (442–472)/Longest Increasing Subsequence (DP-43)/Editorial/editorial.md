# Editorial: Longest Increasing Subsequence (DP-43)

## Problem Overview

Given an integer array `arr`, find the length of the longest strictly increasing subsequence.

A subsequence is formed by selecting elements in their original order without requiring them to be adjacent.

**Example:**

Input:
```text
arr = [10, 9, 2, 5, 3, 7, 101, 18]
```

Output:
```text
4
```

Explanation: One longest increasing subsequence is `[2, 3, 7, 101]`.

## Approach: Binary Search and Greedy

The standard DP solution takes \(O(n^2)\) time. We can optimize it to \(O(n\log n)\) using binary search.

Maintain an array called `tails`, where `tails[i]` stores the smallest possible ending value of an increasing subsequence of length `i + 1`.

For each element:

- If it is greater than every value in `tails`, append it.
- Otherwise, use binary search to find the first element greater than or equal to it and replace that element.

Replacing a value with a smaller one keeps the possibility of building longer subsequences open.

**Important:** `tails` does not necessarily represent an actual subsequence. Its length represents the length of the LIS.

## Algorithm

1. Initialize an empty array `tails`.
2. Traverse every element of the input array.
3. Use binary search to find the first position in `tails` whose value is greater than or equal to the current element.
4. If no such position exists, append the element.
5. Otherwise, replace the element at that position.
6. Return the length of `tails`.

## Java Implementation

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] tails = new int[n];
        int len = 0;

        for (int i = 0; i < n; i++) {
            int left = 0;
            int right = len;

            while (left < right) {
                int mid = left + (right - left) / 2;

                if (tails[mid] < nums[i]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            tails[left] = nums[i];

            if (left == len) {
                len++;
            }
        }

        return len;
    }
}
```

## Dry Run

Input:
```text
nums = [3, 1, 2, 5]
```

- `3`: append → `tails = [3]`
- `1`: replace `3` → `tails = [1]`
- `2`: append → `tails = [1, 2]`
- `5`: append → `tails = [1, 2, 5]`

The length of `tails` is `3`.

Output:
```text
3
```

## Complexity Analysis

Let \(n\) be the length of the array.

- **Time Complexity:** \(O(n\log n)\), because each element requires a binary search.
- **Space Complexity:** \(O(n)\), for the `tails` array.

## Key Takeaways

- Binary search optimizes the LIS solution from \(O(n^2)\) to \(O(n\log n)\).
- Replace the first element greater than or equal to the current value.
- The `tails` array stores the smallest possible ending values for different subsequence lengths.
- The length of `tails` gives the LIS length, but its contents are not necessarily the actual LIS.

