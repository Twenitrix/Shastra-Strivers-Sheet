# Longest Bitonic Subsequence

**Difficulty:** Medium  
**Tags:** Array, Dynamic Programming, Binary Search

## Problem Statement

You are given an array of \(N\) integers. Your task is to find the length of the longest bitonic subsequence.

A **bitonic subsequence** is a subsequence that first strictly increases and then strictly decreases. It may also be entirely increasing or entirely decreasing.

A subsequence is obtained by deleting zero or more elements from the array without changing the relative order of the remaining elements.

Return the maximum possible length of a bitonic subsequence.

## Input Format

- The first line contains an integer \(N\), representing the number of elements.
- The second line contains \(N\) space-separated integers representing the array \(A\).

## Output Format

Print a single integer representing the length of the longest bitonic subsequence.

## Constraints

- \(1 \le N \le 1000\)
- \(-10^4 \le A_i \le 10^4\), for all \(0 \le i < N\).
- All array elements are integers.
- The subsequence must preserve the original relative order of the elements.
- The increasing and decreasing parts must be strictly increasing and strictly decreasing, respectively.

## Example 1

**Input**
```text
8
1 11 2 10 4 5 2 1
```

**Output**
```text
6
```

**Explanation**

One longest bitonic subsequence is:

\[
1,\ 2,\ 10,\ 4,\ 2,\ 1
\]

It first increases from \(1\) to \(2\) to \(10\), then decreases from \(10\) to \(4\) to \(2\) to \(1\).

Its length is \(6\).

## Example 2

**Input**
```text
5
1 2 3 4 5
```

**Output**
```text
5
```

**Explanation**

The entire array is strictly increasing. Since a completely increasing subsequence is also considered bitonic, the answer is \(5\).