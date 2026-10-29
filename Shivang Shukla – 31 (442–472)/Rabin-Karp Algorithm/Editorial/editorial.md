# Editorial: Rabin-Karp Algorithm

## Problem Overview

The Rabin-Karp algorithm is a string-matching algorithm used to find occurrences of a pattern `pat` inside a text `txt`.

It uses **hashing** to compare the pattern with substrings of the text. Instead of comparing every character at every position, it first compares hash values and checks characters only when the hashes match.

**Example:**

Input:
```text
txt = "ababcabc"
pat = "abc"
```

Output:
```text
Pattern found at index 2
Pattern found at index 5
```

Explanation:

The pattern `"abc"` occurs in the text starting at indices `2` and `5`.

## Approach: Rolling Hash

Calculate the hash value of the pattern and the first text window of the same length.

Then slide the window one character at a time:

1. Compare the pattern hash with the current window hash.
2. If the hashes match, compare the actual characters to avoid false matches caused by hash collisions.
3. Update the window hash using the rolling-hash formula instead of recalculating it from scratch.

For a window of length `m`, the rolling hash removes the contribution of the outgoing character, shifts the remaining hash, and adds the incoming character.

Using a prime modulus helps keep hash values within a manageable range.

## Algorithm

1. Let `n` be the text length and `m` be the pattern length.
2. Return an empty result if `m > n`.
3. Compute the pattern hash and the first window hash.
4. For each window:
    - If the hashes match, verify the characters.
    - If they match exactly, record the starting index.
    - Calculate the next window hash using the rolling-hash formula.
5. Return all matching indices.

## Java Implementation

```java
import java.util.*;

class Solution {
    public List<Integer> search(String pat, String txt) {
        List<Integer> result = new ArrayList<>();

        int n = txt.length();
        int m = pat.length();

        if (m == 0 || m > n) {
            return result;
        }

        long mod = 1000000007L;
        long base = 256;
        long power = 1;
        long patHash = 0;
        long windowHash = 0;

        for (int i = 0; i < m - 1; i++) {
            power = (power * base) % mod;
        }

        for (int i = 0; i < m; i++) {
            patHash = (patHash * base + pat.charAt(i)) % mod;
            windowHash = (windowHash * base + txt.charAt(i)) % mod;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patHash == windowHash) {
                int j = 0;

                while (j < m && txt.charAt(i + j) == pat.charAt(j)) {
                    j++;
                }

                if (j == m) {
                    result.add(i);
                }
            }

            if (i < n - m) {
                windowHash = (windowHash
                        - txt.charAt(i) * power % mod + mod) % mod;

                windowHash = (windowHash * base
                        + txt.charAt(i + m)) % mod;
            }
        }

        return result;
    }
}
```

## Dry Run

Input:

```text
txt = "ababa"
pat = "aba"
```

The window length is `3`.

- Window `"aba"` at index `0`: hashes match, and characters match. Record index `0`.
- Window `"bab"` at index `1`: hashes do not match.
- Window `"aba"` at index `2`: hashes match, and characters match. Record index `2`.

Output:

```text
[0, 2]
```

## Complexity Analysis

Let \(n\) be the text length and \(m\) be the pattern length.

- **Average Time Complexity:** \(O(n+m)\), assuming few hash collisions and constant-time rolling-hash updates.
- **Worst-Case Time Complexity:** \(O(nm)\), when many windows have matching hashes and require character-by-character verification.
- **Space Complexity:** \(O(1)\) auxiliary space, excluding the list of matching indices.

## Key Takeaways

- Rabin-Karp uses hashing to speed up string matching.
- Rolling hashes allow efficient updates when the window moves.
- Hash equality does not guarantee string equality, so verify matching windows.
- The algorithm is useful for searching patterns and can be extended to multiple-pattern matching.
- Its average performance is efficient, but hash collisions can lead to quadratic worst-case time.

