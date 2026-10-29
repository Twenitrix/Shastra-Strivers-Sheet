# Partition Array for Maximum Sum

**Difficulty:** Medium  
**Tags:** Array, Dynamic Programming

## Problem Statement

You are given an integer array \(A\) of length \(N\) and an integer \(K\).

Your task is to partition the array into contiguous, non-empty subarrays such that the length of each subarray is at most \(K\).

After partitioning, replace every element in each subarray with the maximum element of that subarray.

The score of a partition is the sum of all elements in the resulting array.

Find the maximum possible score that can be obtained by choosing the partition optimally.

## Input Format

- The first line contains two integers \(N\) and \(K\), representing the array length and the maximum allowed subarray length.
- The second line contains \(N\) space-separated integers representing the array \(A\).

## Output Format

Print a single integer representing the maximum possible score after partitioning the array optimally.

## Constraints

- \(1 \le N \le 500\)
- \(1 \le K \le N\)
- \(0 \le A_i \le 10^9\), for all \(0 \le i < N\).
- All array elements are integers.
- Every subarray must contain at least one element.
- The length of each subarray must be at most \(K\).
- The relative order of elements must remain unchanged.
- The final score must be maximized over all valid partitions.

## Example 1

**Input**
```text id="k5m8qa"
7 3
1 15 7 9 2 5 10
```

**Output**
```text id="r2n6vd"
84
```

**Explanation**

One optimal partition is:

`[1, 15, 7] | [9, 2, 5] | [10]`

Replace every element in each subarray with its maximum:

`[15, 15, 15] | [9, 9, 9] | [10]`

The total score is:

\[
15 \times 3 + 9 \times 3 + 10 = 82
\]

A better partition is:

`[1, 15, 7] | [9] | [2, 5, 10]`

The resulting score is:

\[
15 \times 3 + 9 + 10 \times 3 = 84
\]

Therefore, the maximum possible score is \(84\).

## Example 2

**Input**
```text id="v4p9wb"
5 1
1 2 3 4 5
```

**Output**
```text id="j7x3nc"
15
```

**Explanation**

Since \(K=1\), every subarray must contain exactly one element. No elements can be grouped together.

The score is:

\[
1+2+3+4+5=15
\]

Therefore, the maximum possible score is \(15\).