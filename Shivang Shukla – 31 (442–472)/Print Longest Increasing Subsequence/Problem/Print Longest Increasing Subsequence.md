# Print Longest Increasing Subsequence

**Difficulty:** Medium  
**Tags:** Array, Dynamic Programming, Binary Search

## Problem Statement

You are given an integer array \(A\) of length \(N\).

Your task is to find and print a **Longest Increasing Subsequence (LIS)** of the array.

A subsequence is obtained by deleting zero or more elements from the array without changing the relative order of the remaining elements.

An increasing subsequence is a subsequence in which every element is strictly greater than the previous element.

If multiple longest increasing subsequences exist, you may print any one of them.

## Input Format

- The first line contains an integer \(N\), representing the length of the array.
- The second line contains \(N\) space-separated integers representing the array \(A\).

## Output Format

- Print the elements of any longest increasing subsequence in their original relative order.
- The number of printed elements must equal the length of the longest increasing subsequence.

## Constraints

- \(1 \le N \le 10^5\)
- \(-10^9 \le A_i \le 10^9\), for all \(0 \le i < N\).
- All array elements are integers.
- The subsequence must preserve the original relative order of the selected elements.
- The subsequence must be **strictly increasing**.
- The output must contain a subsequence of maximum possible length.

## Example 1

**Input**
```text
8
10 9 2 5 3 7 101 18
```

**Output**
```text
2 3 7 18
```

**Explanation**

The subsequence `[2, 3, 7, 18]` is strictly increasing and has length \(4\).

Another valid longest increasing subsequence is `[2, 3, 7, 101]`.

Since both have the maximum possible length, either output is accepted.

## Example 2

**Input**
```text
6
1 2 2 3 4 5
```

**Output**
```text
1 2 3 4 5
```

**Explanation**

The subsequence `[1, 2, 3, 4, 5]` is strictly increasing and has length \(5\).

Only one occurrence of the value \(2\) can be included because the subsequence must be strictly increasing.

Therefore, the longest increasing subsequence has length \(5\).