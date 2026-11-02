# Editorial: Minimum Number of Bracket Reversals to Make an Expression Balanced

## Problem Overview

Given a string `s` containing only opening braces `{` and closing braces `}`, find the minimum number of reversals required to make the expression balanced.

A reversal changes an opening brace into a closing brace or vice versa.

If it is impossible to balance the expression, return `-1`.

**Example 1:**

Input:
```text
s = "}{{}}{{{"
```

Output:
```text
3
```

**Example 2:**

Input:
```text
s = "{{{{"
```

Output:
```text
2
```

## Approach: Greedy Counting

A balanced expression must contain an even number of braces. Therefore, if the string length is odd, balancing it is impossible.

After removing all already-balanced pairs, only two types of unmatched braces remain:

- Opening braces: `{{`
- Closing braces: `}}`

Each pair of unmatched opening braces requires one reversal, and each pair of unmatched closing braces also requires one reversal.

If `open` is the number of unmatched opening braces and `close` is the number of unmatched closing braces, the answer is:

\[
\left\lceil\frac{open}{2}\right\rceil+
\left\lceil\frac{close}{2}\right\rceil
\]

For integer calculations, this becomes:

\[
\frac{open+1}{2}+\frac{close+1}{2}
\]

## Algorithm

1. If the string length is odd, return `-1`.
2. Initialize `open = 0` and `close = 0`.
3. Traverse the string:
    - For `{`, increment `open`.
    - For `}`, if an unmatched opening brace exists, match it by decrementing `open`.
    - Otherwise, increment `close`.
4. Calculate the required reversals using the formula above.
5. Return the result.

## Java Implementation

```java
class Solution {
    int countMinReversals(String s) {
        int n = s.length();

        if (n % 2 != 0) {
            return -1;
        }

        int open = 0;
        int close = 0;

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '{') {
                open++;
            } else {
                if (open > 0) {
                    open--;
                } else {
                    close++;
                }
            }
        }

        return (open + 1) / 2 + (close + 1) / 2;
    }
}
```

## Dry Run

Input:

```text
s = "}}{{"
```

Initially, `open = 0`, `close = 0`.

- First `}`: no unmatched opening brace exists, so `close = 1`.
- Second `{`: `open = 1`.
- Third `{`: `open = 2`.
- Fourth `{`: `open = 3`.

The unmatched opening braces are `3`, and unmatched closing braces are `1`.

Required reversals:

\[
\frac{3+1}{2}+\frac{1+1}{2}=2+1=3
\]

Therefore, the answer is `3`.

## Complexity Analysis

- **Time Complexity:** \(O(n)\), because the string is traversed once.
- **Space Complexity:** \(O(1)\), because only two counters are used.

## Key Takeaways

- An odd-length expression can never be balanced using reversals.
- Match opening braces with closing braces whenever possible.
- Count the unmatched opening and closing braces separately.
- Each pair of unmatched braces of the same type requires one reversal.
- A greedy single traversal solves the problem efficiently.

