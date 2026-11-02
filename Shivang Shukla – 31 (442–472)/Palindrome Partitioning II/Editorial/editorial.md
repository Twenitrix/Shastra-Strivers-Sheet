# Editorial: Palindrome Partitioning II

## Problem Overview

Given a string `s`, partition it into substrings such that every substring is a palindrome. Return the **minimum number of cuts** required to achieve this.

A string is a palindrome if it reads the same forward and backward.

**Example:**

Input:
```text
s = "aab"
```

Output:
```text
1
```

Explanation:

The optimal partition is `"aa" | "b"`. Both substrings are palindromes, and only one cut is required.

## Approach: Dynamic Programming

Try every possible partition and choose the one requiring the minimum cuts.

Define `dp[i]` as the minimum number of cuts needed to partition the substring from index `i` to the end into palindromes.

For every index `i`, consider every possible ending index `j`:

- If `s[i...j]` is a palindrome, we can make a partition after `j`.
- If `j` is the last index, no additional cut is needed.
- Otherwise, the total cuts are `1 + dp[j + 1]`.

Take the minimum over all valid choices.

To check palindromes efficiently, precompute a table `pal[i][j]`, where `true` means the substring from `i` to `j` is a palindrome.

## Algorithm

1. Create a palindrome table and fill it using substring lengths in increasing order.
2. Initialize `dp[n] = 0`, representing no cuts needed for an empty suffix.
3. Process starting indices from right to left.
4. For each starting index `i`, examine every ending index `j`.
5. If `s[i...j]` is a palindrome:
    - If `j == n - 1`, the required cuts are `0`.
    - Otherwise, calculate `1 + dp[j + 1]`.
6. Store the minimum value in `dp[i]`.
7. Return `dp[0]`.

## Java Implementation

```java
class Solution {
    public int minCut(String s) {
        int n = s.length();
        boolean[][] pal = new boolean[n][n];

        for (int len = 1; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    if (len <= 2 || pal[i + 1][j - 1]) {
                        pal[i][j] = true;
                    }
                }
            }
        }

        int[] dp = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            int minCuts = Integer.MAX_VALUE;

            for (int j = i; j < n; j++) {
                if (pal[i][j]) {
                    if (j == n - 1) {
                        minCuts = 0;
                    } else {
                        minCuts = Math.min(minCuts, 1 + dp[j + 1]);
                    }
                }
            }

            dp[i] = minCuts;
        }

        return dp[0];
    }
}
```

## Dry Run

Input:

```text
s = "aab"
```

Possible palindrome substrings include `"a"`, `"aa"`, and `"b"`.

The optimal partition is:

```text
"aa" | "b"
```

- `"aa"` is a palindrome.
- `"b"` is a palindrome.
- Only one cut separates them.

Output:

```text
1
```

## Complexity Analysis

Let \(n\) be the length of the string.

- **Time Complexity:** \(O(n^2)\), for building the palindrome table and computing DP.
- **Space Complexity:** \(O(n^2)\), for the palindrome table, plus \(O(n)\) for the DP array.

## Key Takeaways

- The objective is to minimize cuts, not the number of palindrome substrings.
- Precomputing palindrome states avoids repeated palindrome checks.
- `dp[i]` represents the minimum cuts for the suffix starting at `i`.
- A palindrome extending to the last character requires no additional cut.
- Combining palindrome preprocessing with DP gives an \(O(n^2)\) solution.

