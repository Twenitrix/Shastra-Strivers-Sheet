# Editorial: Hashing in Strings — Theory

## Problem Overview

String hashing is a technique used to convert a string into a numerical value called a hash. It helps compare strings efficiently and is commonly used in pattern matching, duplicate detection, and substring comparison.

The main idea is to represent characters using numbers and combine them mathematically to obtain a hash value.

## Approach: Polynomial Rolling Hash

A common technique is polynomial hashing. Each character is assigned a numerical value, and the hash is calculated using a base and a modulus.

For a string \(s\) of length \(n\), one form of the hash is:

\[
H(s)=\left(\sum_{i=0}^{n-1} val(s_i)\cdot p^i\right)\bmod M
\]

Where:

- \(val(s_i)\) is the numerical value of a character.
- \(p\) is a chosen base, often a small prime.
- \(M\) is a large prime modulus to control the hash value.

For example, assign `a = 1`, `b = 2`, and `c = 3`. Using base \(p=31\), the hash of `"abc"` is:

\[
H=1+2(31)+3(31^2)=2946
\]

The result can then be reduced modulo \(M\).

## Algorithm

1. Initialize the hash value to `0`.
2. Choose a base `p` and a large prime modulus `M`.
3. Traverse the string from left to right.
4. Convert each character into a numerical value.
5. Multiply the current hash by `p`, add the character value, and take modulo `M`.
6. Return the final hash value.

## Java Implementation

```java
class Solution {
    public long stringHash(String s) {
        long hash = 0;
        long p = 31;
        long mod = 1000000007;

        for (int i = 0; i < s.length(); i++) {
            hash = (hash * p + (s.charAt(i) - 'a' + 1)) % mod;
        }

        return hash;
    }
}
```

## Dry Run

For `s = "abc"`, using base `31` and modulus `1000000007`:

- Character `a`: hash = `1`
- Character `b`: hash = `(1 * 31) + 2 = 33`
- Character `c`: hash = `(33 * 31) + 3 = 1026`

Final hash: `1026`

## Complexity Analysis

Let \(n\) be the length of the string.

- **Time Complexity:** \(O(n)\), because each character is processed once.
- **Space Complexity:** \(O(1)\), because only a fixed number of variables are used.

## Key Takeaways

- Hashing maps strings to numerical values for efficient comparison.
- Polynomial rolling hash is useful for substring and pattern-matching problems.
- A modulus keeps hash values within a manageable range.
- **Hash collisions are possible:** different strings can produce the same hash, so a hash match does not always guarantee string equality.
- Using a suitable base and modulus reduces collision probability but does not eliminate it.

