# Editorial: Z Function

## Problem Overview

The **Z-function** is a string-processing technique used to determine how many characters match between the string's prefix and the substring starting at each index.

For a string `s`, `Z[i]` represents the length of the longest substring starting at index `i` that matches a prefix of `s`.

By convention, `Z[0]` is usually set to `0`.

**Example:**

Input:
```text
s = "abacaba"
```

Output:
```text
Z = [0, 0, 1, 0, 3, 0, 1]
```

Explanation:

- `Z[2] = 1` because the substring `"acaba"` starts with `"a"`.
- `Z[4] = 3` because the substring `"aba"` matches the prefix `"aba"`.
- `Z[6] = 1` because the character `"a"` matches the prefix's first character.

## Approach: Z-Box Optimization

A straightforward solution compares the prefix with the substring starting at every index, taking \(O(n^2)\) time in the worst case.

The optimized algorithm maintains a **Z-box** represented by two boundaries, `left` and `right`. This box identifies the rightmost substring that matches a prefix of the original string.

For each index `i`:

1. If `i > right`, compare characters directly to calculate `Z[i]`.
2. Otherwise, initialize `Z[i]` using the previously computed Z-values within the current box.
3. Extend the match while characters remain equal.
4. If the match extends beyond `right`, update the Z-box boundaries.

This reuses previously computed information and achieves linear time.

## Algorithm

1. Create a Z-array of length `n`, initialized to zero.
2. Initialize `left = 0` and `right = 0`.
3. Traverse indices from `1` to `n - 1`.
4. If `i <= right`, initialize `Z[i]` with the smaller of `right - i + 1` and `Z[i - left]`.
5. Extend the match while the corresponding characters are equal.
6. Update `left` and `right` if the new match extends farther.
7. Return the Z-array.

## Java Implementation

```java id="p3k6vm"
class Solution {
    public int[] zFunction(String s) {
        int n = s.length();
        int[] z = new int[n];

        int left = 0;
        int right = 0;

        for (int i = 1; i < n; i++) {
            if (i <= right) {
                z[i] = Math.min(right - i + 1, z[i - left]);
            }

            while (i + z[i] < n &&
                   s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }

            if (i + z[i] - 1 > right) {
                left = i;
                right = i + z[i] - 1;
            }
        }

        return z;
    }
}
```

## Dry Run

Input:

```text
s = "aaaa"
```

- `Z[1] = 3`, because `"aaa"` matches the prefix `"aaa"`.
- `Z[2] = 2`, because `"aa"` matches the prefix `"aa"`.
- `Z[3] = 1`, because `"a"` matches the prefix `"a"`.

Output:

```text
[0, 3, 2, 1]
```

## Complexity Analysis

Let \(n\) be the length of the string.

- **Time Complexity:** \(O(n)\), because the Z-box avoids repeatedly comparing previously matched characters.
- **Space Complexity:** \(O(n)\), for the Z-array.

## Key Takeaways

- `Z[i]` measures the longest prefix match starting at index `i`.
- The Z-box stores the rightmost segment already known to match a prefix.
- Reusing previous Z-values avoids redundant comparisons.
- The algorithm computes the entire Z-array in linear time.
- The Z-function is useful for pattern matching, prefix analysis, and string algorithms.

