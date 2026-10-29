# Editorial: Longest String Chain

## Problem Overview

Given an array of strings `words`, find the length of the longest possible string chain.

A string `prev` is a predecessor of `current` if exactly one character can be inserted anywhere in `prev` to form `current`.

Each word in a chain must be a predecessor of the next word.

**Example:**

Input:
```text
words = ["a", "b", "ba", "bca", "bda", "bdca"]
```

Output:
```text
4
```

Explanation: One longest chain is `"a" → "ba" → "bca" → "bdca"`.

## Approach: Dynamic Programming

The main observation is that a predecessor must be exactly one character shorter than the current word.

First, sort the words by their lengths in ascending order. This ensures that shorter potential predecessors are processed before longer words.

Define `dp[i]` as the length of the longest string chain ending at `words[i]`.

Initially, every word forms a chain of length `1`.

For each word, check all previously processed words. If the previous word is exactly one character shorter and can become the current word by inserting one character, update:

\[
dp[i] = \max(dp[i], dp[j] + 1)
\]

To check whether one word is a predecessor of another, use two pointers. If the characters match, advance both pointers. If they differ, advance only the pointer for the longer word. At most one extra character may be skipped.

The answer is the maximum value in the DP array.

## Algorithm

1. Sort the words by their lengths.
2. Initialize the DP array with `1`.
3. For every word, check all shorter words processed before it.
4. If their lengths differ by one and the shorter word is a valid predecessor, update the DP value.
5. Track the maximum chain length.
6. Return the maximum value.

## Java Implementation

```java
import java.util.*;

class Solution {
    public int longestStrChain(String[] words) {
        Arrays.sort(words, (a, b) -> a.length() - b.length());

        int n = words.length;
        int[] dp = new int[n];
        int maxLen = 1;

        for (int i = 0; i < n; i++) {
            dp[i] = 1;

            for (int j = 0; j < i; j++) {
                if (words[i].length() == words[j].length() + 1
                        && isPredecessor(words[j], words[i])) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }

    private boolean isPredecessor(String shorter, String longer) {
        int i = 0;
        int j = 0;

        while (i < shorter.length() && j < longer.length()) {
            if (shorter.charAt(i) == longer.charAt(j)) {
                i++;
            }

            j++;
        }

        return i == shorter.length();
    }
}
```

## Dry Run

Input:

```text
words = ["a", "ba", "bca", "bdca"]
```

The words are already sorted by length.

- `"a"`: chain length `1`.
- `"ba"`: `"a"` is its predecessor, so chain length `2`.
- `"bca"`: `"ba"` is its predecessor, so chain length `3`.
- `"bdca"`: `"bca"` is its predecessor, so chain length `4`.

Output:

```text
4
```

## Complexity Analysis

Let \(n\) be the number of words and \(L\) be the maximum word length.

- **Time Complexity:** \(O(n^2L)\), because each pair of words may require an \(O(L)\) predecessor check. Sorting takes \(O(n\log n)\).
- **Space Complexity:** \(O(n)\) auxiliary space for the DP array, excluding sorting overhead.

## Key Takeaways

- Sort words by length before applying DP.
- A predecessor must be exactly one character shorter.
- Two pointers efficiently check the predecessor relationship.
- Each DP value represents the longest chain ending at a particular word.

