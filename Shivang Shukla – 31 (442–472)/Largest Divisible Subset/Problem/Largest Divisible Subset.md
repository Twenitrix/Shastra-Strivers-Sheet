# Largest Divisible Subset

**Difficulty:** Medium  
**Tags:** Array, Dynamic Programming, Sorting

## Problem Statement

You are given an array of distinct positive integers `nums`.

Your task is to find the largest subset of `nums` such that for every pair of elements \((a,b)\) in the subset, at least one of the following conditions is satisfied:

- \(a \bmod b = 0\)
- \(b \bmod a = 0\)

In other words, one element of every pair must be divisible by the other.

Return any largest subset that satisfies this condition. If multiple subsets have the maximum possible size, you may return any one of them.

## Input Format

- The first line contains an integer \(N\), representing the number of elements.
- The second line contains \(N\) space-separated distinct positive integers.

## Output Format

- Print the elements of any largest divisible subset, separated by spaces.
- The order of the elements in the output does not affect correctness, provided the subset satisfies the divisibility condition.

## Constraints

- \(1 \le N \le 1000\)
- \(1 \le \text{nums}[i] \le 2^{31}-1\), for all \(0 \le i < N\).
- All elements of `nums` are distinct positive integers.
- The output subset must contain only elements from the input array.
- For every pair of distinct elements \(a\) and \(b\) in the output subset, \(a \bmod b = 0\) or \(b \bmod a = 0\) must hold.

## Example 1

**Input**
```text id="r7k2vx"
4
1 2 3 8
```

**Output**
```text id="m3n8qa"
1 2 8
```

**Explanation**

The subset \(\{1,2,8\}\) satisfies the divisibility condition:

- \(2 \bmod 1 = 0\)
- \(8 \bmod 2 = 0\)
- \(8 \bmod 1 = 0\)

Its size is \(3\), which is the maximum possible.

## Example 2

**Input**
```text id="k5p9wd"
3
3 5 10
```

**Output**
```text id="t4h6yb"
5 10
```

**Explanation**

The element \(10\) is divisible by \(5\), so \(\{5,10\}\) is a valid subset of size \(2\). No subset of size \(3\) satisfies the divisibility condition.