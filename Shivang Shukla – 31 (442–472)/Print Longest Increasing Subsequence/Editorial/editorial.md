# Editorial: Print Longest Increasing Subsequence

## Problem Overview

Given an integer array `arr`, find and print one of its longest strictly increasing subsequences (LIS).

A subsequence is formed by removing zero or more elements without changing the order of the remaining elements.

**Example:**

Input:
```text
arr = [5, 4, 11, 1, 16, 8]
```

Output:
```text
[4, 11, 16]
```

Explanation:

`[4, 11, 16]` is a strictly increasing subsequence of length `3`. The goal is to print an actual longest subsequence, not just its length.

## Approach: Dynamic Programming with Parent Tracking

Use two arrays:

- `dp[i]`: Length of the longest increasing subsequence ending at index `i`.
- `parent[i]`: Previous index in the subsequence ending at `i`.

Initially, every element forms a subsequence of length `1`, so `dp[i] = 1` and `parent[i] = i`.

For every index `i`, check all previous indices `j`. If `arr[j] < arr[i]` and extending the subsequence ending at `j` produces a longer subsequence, update `dp[i]` and set `parent[i] = j`.

Track the index where the maximum length occurs. Starting from that index, follow the `parent` links backward to reconstruct the subsequence. Reverse the reconstructed sequence to obtain the correct order.

## Algorithm

1. Initialize `dp[i] = 1` and `parent[i] = i`.
2. For every index `i`, examine all previous indices `j`.
3. If `arr[j] < arr[i]` and `dp[j] + 1 > dp[i]`:
    - Update `dp[i] = dp[j] + 1`.
    - Set `parent[i] = j`.
4. Track the index of the longest subsequence.
5. Follow the parent links from that index until an index points to itself.
6. Reverse the collected elements.
7. Print or return the resulting subsequence.

## Java Implementation

```java
import java.util.*;

class Solution {
    public List<Integer> longestIncreasingSubsequence(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        int[] parent = new int[n];

        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            parent[i] = i;
        }

        int maxLength = 1;
        int lastIndex = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }

            if (dp[i] > maxLength) {
                maxLength = dp[i];
                lastIndex = i;
            }
        }

        List<Integer> lis = new ArrayList<>();
        int index = lastIndex;

        while (parent[index] != index) {
            lis.add(arr[index]);
            index = parent[index];
        }

        lis.add(arr[index]);
        Collections.reverse(lis);

        return lis;
    }
}
```

## Dry Run

Input:

```text
arr = [3, 1, 2, 5]
```

The DP process finds the subsequence:

- `1` forms a subsequence of length `1`.
- `2` extends `1`, giving `[1, 2]`.
- `5` extends `[1, 2]`, giving `[1, 2, 5]`.

The parent links reconstruct the sequence in reverse order: `[5, 2, 1]`.

After reversing it, the result is:

```text
[1, 2, 5]
```

## Complexity Analysis

Let \(n\) be the length of the array.

- **Time Complexity:** \(O(n^2)\), because every element is compared with all previous elements.
- **Space Complexity:** \(O(n)\), for the DP array, parent array, and reconstructed subsequence.

## Key Takeaways

- Calculating the LIS length alone does not reconstruct the actual subsequence.
- The `parent` array records the previous index for each chosen extension.
- Follow parent links backward and reverse the result.
- Multiple valid LIS sequences may exist; the algorithm returns one of them.
- This method takes \(O(n^2)\) time.

