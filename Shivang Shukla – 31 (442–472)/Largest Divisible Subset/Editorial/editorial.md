# Editorial: Largest Divisible Subset

## Problem Overview

Given an array of distinct positive integers, find the largest subset such that for every pair of elements `(a, b)`, either `a` is divisible by `b` or `b` is divisible by `a`.

Return any one of the largest divisible subsets.

**Example:**

Input:
```text
nums = [1, 2, 3]
```

Output:
```text
[1, 2]
```

Explanation: `1` divides `2`, so `[1, 2]` is a valid divisible subset of maximum size. `[1, 3]` is also valid.

## Approach: Dynamic Programming

The key observation is that if the array is sorted, we only need to check whether a smaller element divides a larger element.

Sort the array in ascending order. Define `dp[i]` as the length of the largest divisible subset ending at index `i`.

Initially, every element forms a subset of size `1`, so `dp[i] = 1`.

For each index `i`, check all previous indices `j`. If `nums[i] % nums[j] == 0`, the element `nums[i]` can extend the subset ending at `j`.

If `dp[j] + 1 > dp[i]`, update `dp[i]` and store `j` in a `parent` array to reconstruct the subset later.

Finally, start from the index with the maximum DP value and follow the parent links to build the answer.

## Algorithm

1. Sort the array in ascending order.
2. Initialize the DP array with `1` and the parent array with each index itself.
3. For every index `i`, examine all previous indices `j`.
4. If `nums[i]` is divisible by `nums[j]` and extending the subset improves its length, update `dp[i]` and `parent[i]`.
5. Track the index with the maximum DP value.
6. Reconstruct the subset by following parent links from that index.
7. Reverse the reconstructed subset and return it.

## Java Implementation

```java
import java.util.*;

class Solution {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);

        int[] dp = new int[n];
        int[] parent = new int[n];

        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            parent[i] = i;
        }

        int maxLen = 1;
        int lastIndex = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }

            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIndex = i;
            }
        }

        List<Integer> result = new ArrayList<>();

        while (parent[lastIndex] != lastIndex) {
            result.add(nums[lastIndex]);
            lastIndex = parent[lastIndex];
        }

        result.add(nums[lastIndex]);
        Collections.reverse(result);

        return result;
    }
}
```

## Dry Run

Input:

```text
nums = [1, 2, 4, 8]
```

The array is already sorted.

- `1`: subset `[1]`, length `1`.
- `2`: `1` divides `2`, giving `[1, 2]`, length `2`.
- `4`: both `1` and `2` divide `4`; the best subset is `[1, 2, 4]`, length `3`.
- `8`: the best subset becomes `[1, 2, 4, 8]`, length `4`.

Output:

```text
[1, 2, 4, 8]
```

## Complexity Analysis

Let \(n\) be the number of elements.

- **Time Complexity:** \(O(n^2 + n\log n)\), due to sorting and the nested DP loops. This simplifies to \(O(n^2)\).
- **Space Complexity:** \(O(n)\), for the DP array, parent array, and result.

## Key Takeaways

- Sorting makes divisibility checks easier because smaller potential divisors come first.
- Dynamic programming finds the longest valid subset ending at each index.
- The parent array reconstructs the actual subset.
- The approach is similar to the Longest Increasing Subsequence problem.

