# Editorial: KMP Algorithm or LPS Array

## Problem Overview

The Knuth-Morris-Pratt (KMP) algorithm is a string-matching algorithm used to find occurrences of a pattern within a text efficiently.

A naive approach may repeatedly compare characters that have already matched. KMP avoids these unnecessary comparisons by using an array called the **LPS (Longest Proper Prefix which is also a Suffix)** array.

A proper prefix is a prefix that is not equal to the entire string.

**Example:**

```text
Text:    ababcabcabababd
Pattern: ababd
```

The pattern occurs in the text starting at index `10`.

## Approach: LPS Array

The LPS array stores the length of the longest proper prefix that is also a suffix for every prefix of the pattern.

For example, for the pattern `"abab"`:

```text
Pattern: a b a b
Index:   0 1 2 3
LPS:     0 0 1 2
```

When a mismatch occurs, KMP uses the LPS value to decide how far the pattern can shift without rechecking already matched characters.

The algorithm has two main steps:

1. Build the LPS array for the pattern.
2. Use the LPS array to search for the pattern in the text.

## Algorithm

### Step 1: Build the LPS Array

1. Initialize `lps[0] = 0`.
2. Maintain `len`, the length of the current matching prefix and suffix.
3. Compare the current pattern character with `pattern.charAt(len)`.
4. If they match, increment `len` and store it in `lps[i]`.
5. If they do not match and `len > 0`, update `len = lps[len - 1]`.
6. If they do not match and `len == 0`, store `0` and move forward.

### Step 2: Search the Pattern

1. Maintain two pointers: `i` for the text and `j` for the pattern.
2. If the characters match, increment both pointers.
3. If `j` reaches the pattern length, an occurrence is found at `i - j`.
4. After finding a match, update `j = lps[j - 1]` to continue searching for further occurrences.
5. If a mismatch occurs and `j > 0`, update `j = lps[j - 1]` without moving `i`.
6. If a mismatch occurs and `j == 0`, increment `i`.

## Java Implementation

```java
class Solution {
    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }

        int n = haystack.length();
        int m = needle.length();
        int[] lps = new int[m];

        int len = 0;
        int i = 1;

        while (i < m) {
            if (needle.charAt(i) == needle.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len > 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        i = 0;
        int j = 0;

        while (i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            }

            if (j == m) {
                return i - j;
            } else if (i < n && haystack.charAt(i) != needle.charAt(j)) {
                if (j > 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return -1;
    }
}
```

## Dry Run

Consider:

```text
Text:    ababc
Pattern: abc
```

The LPS array for `"abc"` is:

```text
Pattern: a b c
LPS:     0 0 0
```

- Compare `a` with `a`: match.
- Compare `b` with `b`: match.
- Compare `a` with `c`: mismatch.
- Since `j = 2`, update `j = lps[1] = 0` without moving the text pointer.
- Compare `a` with `a`, then `b` with `b`, then `c` with `c`.

The pattern is found starting at index `2`.

## Complexity Analysis

Let \(n\) be the text length and \(m\) be the pattern length.

- **Time Complexity:** \(O(n + m)\), because both LPS construction and pattern searching are linear.
- **Space Complexity:** \(O(m)\), for storing the LPS array.

## Key Takeaways

- KMP avoids repeating comparisons after a mismatch.
- The LPS array identifies the longest reusable prefix.
- When a mismatch occurs, use `lps[j - 1]` to update the pattern pointer.
- KMP performs string matching in linear time.

