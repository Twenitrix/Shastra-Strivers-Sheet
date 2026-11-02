# Number of Longest Increasing Subsequences

**Difficulty:** Medium  
**Tags:** Array, Dynamic Programming, Binary Search

## Problem Statement

You are given an integer array \(A\) of length \(N\).

An increasing subsequence is a sequence obtained by deleting zero or more elements from the array without changing the relative order of the remaining elements, such that every element is strictly greater than the previous element.

Your task is to find the **number of distinct subsequences that have the maximum possible length** among all strictly increasing subsequences of the array.

Two subsequences are considered different if they use different positions in the original array, even if their values are identical.

Return the total number of longest increasing subsequences.

## Input Format

- The first line contains an integer \(N\), representing the number of elements.
- The second line contains \(N\) space-separated integers representing the array \(A\).

## Output Format

Print a single integer representing the number of longest increasing subsequences.

## Constraints

- \(1 \le N \le 2000\)
- \(-10^6 \le A_i \le 10^6\), for all \(0 \le i < N\).
- All array elements are integers.
- The subsequence must be strictly increasing.
- Subsequences are distinguished by their original array indices.
- The number of longest increasing subsequences fits within a signed 32-bit integer.

## Example 1

**Input**
```text id="g5k8qa"
5
1 3 5 4 7
```

**Output**
```text id="r2m6nd"
2
```

**Explanation**

The longest increasing subsequences have length \(4\).

They are:

- \(1,3,5,7\)
- \(1,3,4,7\)

There are \(2\) such subsequences, so the answer is \(2\).

## Example 2

**Input**
```text id="k7p3wb"
5
2 2 2 2 2
```

**Output**
```text id="m4x9rd"
5
```

**Explanation**

Since all elements are equal, the maximum length of a strictly increasing subsequence is \(1\).

Each of the five array positions forms a different subsequence of length \(1\). Therefore, the answer is \(5\).