# Longest Increasing Subsequence

**Difficulty:** Medium  
**Tags:** Array, Dynamic Programming, Binary Search

## Problem Statement

You are given an integer array \(A\) of length \(N\). Your task is to find the length of the **Longest Increasing Subsequence (LIS)**.

An increasing subsequence is a sequence obtained by deleting zero or more elements from the array without changing the relative order of the remaining elements, such that every element is strictly greater than the previous element.

Return the maximum possible length of such a subsequence.

## Input Format

- The first line contains an integer \(N\), representing the number of elements.
- The second line contains \(N\) space-separated integers representing the array \(A\).

## Output Format

Print a single integer representing the length of the longest strictly increasing subsequence.

## Constraints

- \(1 \le N \le 2500\)
- \(-10^4 \le A_i \le 10^4\), for all \(0 \le i < N\).
- All array elements are integers.
- The subsequence must preserve the relative order of the original array.
- The subsequence must be strictly increasing; equal consecutive values are not allowed.

## Example 1

**Input**
```text
8
10 9 2 5 3 7 101 18
```

**Output**
```text
4
```

**Explanation**

One longest increasing subsequence is:

\[
2,\ 3,\ 7,\ 18
\]

Its length is \(4\). Therefore, the answer is \(4\).

## Example 2

**Input**
```text
4
7 7 7 7
```

**Output**
```text
1
```

**Explanation**

All elements are equal, so no strictly increasing subsequence can contain more than one element. Therefore, the answer is \(1\).