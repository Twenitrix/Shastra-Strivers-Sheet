# Palindrome Partitioning II

**Difficulty:** Hard  
**Tags:** String, Dynamic Programming, Recursion, Memoization

## Problem Statement

You are given a string \(S\). Your task is to partition the string into substrings such that every substring in the partition is a palindrome.

A palindrome is a string that reads the same forward and backward.

You may place cuts between any two adjacent characters of the string. Find the **minimum number of cuts** required to divide the string into palindromic substrings.

If the entire string is already a palindrome, no cuts are required.

## Input Format

- The first line contains a string \(S\) consisting of lowercase English letters.

## Output Format

Print a single integer representing the minimum number of cuts required to partition the string into palindromic substrings.

## Constraints

- \(1 \le |S| \le 2000\)
- \(S_i \in \{a,b,c,\ldots,z\}\), for all \(0 \le i < |S|\).
- The string contains only lowercase English letters.
- Every substring in the final partition must be non-empty.
- Each cut must be placed between two adjacent characters.
- If \(S\) is already a palindrome, the answer must be \(0\).

## Example 1

**Input**
```text id="p6m8qa"
aab
```

**Output**
```text id="r3k2nd"
1
```

**Explanation**

The string can be partitioned as:

`aa | b`

Both `"aa"` and `"b"` are palindromes. Only one cut is required, so the answer is \(1\).

## Example 2

**Input**
```text id="v5p3wb"
racecar
```

**Output**
```text id="j8x6nc"
0
```

**Explanation**

The entire string `"racecar"` is a palindrome. Therefore, no cuts are required, and the answer is \(0\).