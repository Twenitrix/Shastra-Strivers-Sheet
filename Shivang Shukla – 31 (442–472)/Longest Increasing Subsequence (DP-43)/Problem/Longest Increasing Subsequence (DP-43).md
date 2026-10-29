# Longest Increasing Subsequence (DP-43)

**Difficulty:** Medium  
**Tags:** Array, Dynamic Programming, Binary Search

## Problem Statement

You are given an array \(A\) consisting of \(N\) integers. Your task is to determine the length of the longest strictly increasing subsequence in the array.

A subsequence is formed by removing zero or more elements without changing the relative order of the remaining elements.

A subsequence is strictly increasing if each element is greater than the element immediately before it.

Return the maximum length of any strictly increasing subsequence.

## Input Format

- The first line contains an integer \(N\), representing the number of elements.
- The second line contains \(N\) space-separated integers representing the array \(A\).

## Output Format

Print a single integer representing the length of the longest strictly increasing subsequence.

## Constraints

- \(1 \le N \le 10^5\)
- \(-10^9 \le A_i \le 10^9\), for all \(0 \le i < N\).
- All array elements are integers.
- The relative order of elements must be preserved.
- The subsequence must be strictly increasing; duplicate values cannot extend an increasing subsequence.

## Example 1

**Input**
```text id="r9m2qa"
6
5 4 11 1 16 8
```

**Output**
```text id="p3v6kb"
3
```

**Explanation**

One longest increasing subsequence is:

\[
5,\ 11,\ 16
\]

Its length is \(3\). Another valid subsequence of length \(3\) is \(4, 11, 16\).

Therefore, the answer is \(3\).

## Example 2

**Input**
```text id="w8k1nc"
4
1 2 3 4
```

**Output**
```text id="h5q7jd"
4
```

**Explanation**

The entire array is strictly increasing, so the longest increasing subsequence contains all four elements. Therefore, the answer is \(4\).