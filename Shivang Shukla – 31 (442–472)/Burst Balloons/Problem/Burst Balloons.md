# Burst Balloons

**Difficulty:** Hard  
**Tags:** Array, Dynamic Programming, Recursion, Memoization

## Problem Statement

You are given an array `nums` of \(N\) balloons. Each balloon has a number written on it, represented by `nums[i]`.

You must burst all the balloons. When you burst the \(i\)-th balloon, you earn `nums[left] × nums[i] × nums[right]` coins, where `left` and `right` are the adjacent balloons that remain immediately before and after the balloon being burst.

If there is no balloon on either side, treat its value as \(1\).

After bursting a balloon, it disappears, and the remaining balloons become adjacent to each other.

Your task is to determine the **maximum number of coins** you can collect by choosing the optimal order in which to burst all the balloons.

## Input Format

- The first line contains an integer \(N\), representing the number of balloons.
- The second line contains \(N\) space-separated integers representing the array `nums`.

## Output Format

Print a single integer representing the maximum number of coins that can be collected.

## Constraints

- \(1 \le N \le 300\)
- \(0 \le \text{nums}[i] \le 100\), for all \(0 \le i < N\).
- All elements of `nums` are integers.
- Every balloon must be burst exactly once.
- The order of bursting balloons can be chosen freely.

## Example 1

**Input**
```text
4
3 1 5 8
```

**Output**
```text
167
```

**Explanation**

One optimal order is to burst the balloons with values \(1\), \(5\), \(3\), and \(8\).

- Burst \(1\): \(3 \times 1 \times 5 = 15\) coins.
- Burst \(5\): \(3 \times 5 \times 8 = 120\) coins.
- Burst \(3\): \(1 \times 3 \times 8 = 24\) coins.
- Burst \(8\): \(1 \times 8 \times 1 = 8\) coins.

Total coins \(= 15 + 120 + 24 + 8 = 167\).

## Example 2

**Input**
```text
1
5
```

**Output**
```text
5
```

**Explanation**

There is only one balloon. Both adjacent values are treated as \(1\).

Coins earned \(= 1 \times 5 \times 1 = 5\).