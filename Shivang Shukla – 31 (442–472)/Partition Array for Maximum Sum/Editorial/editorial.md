# Editorial: Partition Array for Maximum Sum

## Problem Overview

Given an integer array `arr` and an integer `k`, partition the array into contiguous subarrays of length at most `k`.

For each subarray, replace every element with the maximum element of that subarray. Return the maximum possible sum of the resulting array.

**Example:**

Input:
```text
arr = [1, 15, 7, 9, 2, 5, 10]
k = 3
```

Output:
```text
84
```

Explanation:

One optimal partition is `[1, 15, 7] | [9] | [2, 5, 10]`.

After replacing each element with its subarray's maximum, the array becomes `[15, 15, 15, 9, 10, 10, 10]`.

The sum is `84`.

## Approach: Dynamic Programming

Define `dp[i]` as the maximum sum obtainable by partitioning the first `i` elements.

For every position `i`, consider the last subarray ending at index `i - 1`. Its length can range from `1` to `k`, provided it does not exceed the array boundaries.

Maintain the maximum element of this last subarray while expanding it backward.

If the last subarray has length `len` and maximum element `maxElement`, its contribution is:

\[
len \times maxElement
\]

The remaining prefix contributes `dp[i - len]`.

Therefore:

\[
dp[i]=\max\left(dp[i],\ dp[i-len]+len\times maxElement\right)
\]

Initialize `dp[0] = 0`, because an empty prefix has sum zero.

## Algorithm

1. Create a DP array of size `n + 1`, initialized to zero.
2. Iterate through each prefix length `i` from `1` to `n`.
3. Initialize `maxElement = 0`.
4. Try all possible last subarray lengths from `1` to `k`.
5. Stop when the subarray length exceeds `i`.
6. Update `maxElement` with the maximum value in the chosen subarray.
7. Calculate the candidate sum using `dp[i - len] + len * maxElement`.
8. Store the maximum candidate in `dp[i]`.
9. Return `dp[n]`.

## Java Implementation

```java
class Solution {
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            int maxElement = 0;

            for (int len = 1; len <= k && len <= i; len++) {
                maxElement = Math.max(maxElement, arr[i - len]);

                int sum = dp[i - len] + len * maxElement;
                dp[i] = Math.max(dp[i], sum);
            }
        }

        return dp[n];
    }
}
```

## Dry Run

Input:

```text
arr = [1, 15, 7]
k = 2
```

- For the first element `[1]`, the sum is `1`.
- For the first two elements, `[1, 15]` can become `[15, 15]`, giving a sum of `30`.
- For all three elements:
    - Partition `[1, 15] | [7]`: sum = `30 + 7 = 37`.
    - Partition `[1] | [15, 7]`: sum = `1 + 30 = 31`.

The maximum sum is:

```text
37
```

## Complexity Analysis

Let \(n\) be the length of the array.

- **Time Complexity:** \(O(nk)\), because each prefix considers at most `k` possible final subarrays.
- **Space Complexity:** \(O(n)\), for the DP array.

## Key Takeaways

- The last subarray can have any length from `1` to `k`.
- Each element in a subarray contributes its maximum element after partitioning.
- Maintain the subarray maximum while expanding backward.
- DP combines the best partition of the previous prefix with the current subarray.
- The solution runs in \(O(nk)\) time.

