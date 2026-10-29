# Shortest Palindrome

**Difficulty:** Hard  
**Tags:** String, Two Pointers, KMP Algorithm

## Problem Statement

You are given a string \(S\) consisting of lowercase English letters.

Your task is to find the shortest palindrome that can be formed by adding characters **only to the beginning** of the string.

A palindrome is a string that reads the same forward and backward.

You must preserve the original string as a suffix of the resulting palindrome. You cannot insert or remove characters within the string, or append characters to its end.

Print the resulting shortest palindrome.

## Input Format

- The first line contains a string \(S\).

## Output Format

Print the shortest palindrome that can be formed by adding characters only to the beginning of \(S\).

## Constraints

- \(1 \le |S| \le 10^5\)
- \(S\) contains only lowercase English letters (`a`–`z`).
- Only characters may be added to the beginning of the original string.
- The original string must remain unchanged and appear as a suffix of the result.
- The resulting string must be a palindrome.
- The number of added characters must be minimized.

## Example 1

**Input**
```text id="a4n7qx"
aacecaaa
```

**Output**
```text id="b8m2vr"
aaacecaaa
```

**Explanation**

Adding one character `a` to the beginning produces `aaacecaaa`, which is a palindrome.

Only one character is required, so this is the shortest possible result.

## Example 2

**Input**
```text id="c5p9wd"
abcd
```

**Output**
```text id="d7k3fz"
dcbabcd
```

**Explanation**

The longest palindromic prefix of `abcd` is `a`.

Reverse the remaining suffix `bcd` to obtain `dcb` and add it to the beginning.

The resulting string `dcbabcd` is a palindrome and is the shortest possible result.