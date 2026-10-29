# Editorial: Shortest Palindrome

## Problem Overview

Given a string `s`, add characters only to the **beginning** of the string to make it a palindrome. Return the shortest palindrome that can be formed.

**Example 1:**

Input:
```text
s = "aacecaaa"
```

Output:
```text
"aaacecaaa"
```

**Example 2:**

Input:
```text
s = "abcd"
```

Output:
```text
"dcbabcd"
```

Explanation:

For `"abcd"`, the longest palindromic prefix is `"a"`. Reverse the remaining suffix `"bcd"` to obtain `"dcb"` and add it to the beginning.

## Approach: KMP and LPS Array

The key observation is that we should find the **longest palindromic prefix** of the string. Every character after this prefix must be added to the beginning in reverse order.

To find this prefix efficiently, construct a combined string:

\[
combined = s + "\#" + reverse(s)
\]

Here, `#` is a separator that does not occur in the input string.

Build the Longest Prefix Suffix (LPS) array for this combined string. Its last value gives the length of the longest prefix of `s` that is also a suffix of `reverse(s)`. This corresponds to the longest palindromic prefix of `s`.

Reverse the remaining suffix of `s` and prepend it to the original string.

## Algorithm

1. Reverse the input string.
2. Construct `combined = s + "#" + reverse(s)`.
3. Compute the LPS array using the KMP algorithm.
4. Let the final LPS value be the length of the longest palindromic prefix.
5. Take the suffix after that prefix and reverse it.
6. Prepend the reversed suffix to the original string.
7. Return the result.

## Java Implementation

```java
class Solution {
    public String shortestPalindrome(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        String combined = s + "#" + rev;

        int n = combined.length();
        int[] lps = new int[n];

        for (int i = 1; i < n; i++) {
            int j = lps[i - 1];

            while (j > 0 && combined.charAt(i) != combined.charAt(j)) {
                j = lps[j - 1];
            }

            if (combined.charAt(i) == combined.charAt(j)) {
                j++;
            }

            lps[i] = j;
        }

        int prefixLength = lps[n - 1];
        String suffix = s.substring(prefixLength);

        return new StringBuilder(suffix).reverse().toString() + s;
    }
}
```

## Dry Run

Input:

```text
s = "abcd"
```

Reverse the string:

```text
dcba
```

Construct the combined string:

```text
abcd#dcba
```

The final LPS value is `1`, meaning the longest palindromic prefix is `"a"`.

The remaining suffix is:

```text
bcd
```

Reverse the suffix:

```text
dcb
```

Prepend it to the original string:

```text
dcbabcd
```

Output:

```text
"dcbabcd"
```

## Complexity Analysis

Let \(n\) be the length of the input string.

- **Time Complexity:** \(O(n)\), for reversing the string, constructing the combined string, and computing the LPS array.
- **Space Complexity:** \(O(n)\), for the combined string, reversed string, and LPS array.

## Key Takeaways

- Only characters may be added to the beginning of the string.
- Finding the longest palindromic prefix minimizes the number of added characters.
- The KMP LPS array identifies this prefix efficiently.
- The separator prevents matches from crossing the boundary between the original and reversed strings.
- The solution runs in linear time.

