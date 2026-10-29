# Maximum XOR of Two Numbers in an Array

**Difficulty:** Medium  
**Tags:** Array, Bit Manipulation, Trie

## Problem Statement

You are given an integer array `nums` containing \(N\) non-negative integers.

Your task is to find the maximum value of the bitwise XOR operation between any two elements at different indices in the array.

For two integers \(a\) and \(b\), the bitwise XOR operation compares their binary representations and sets a bit to \(1\) when the corresponding bits differ.

Find and return the maximum possible value of:

\[
nums[i] \oplus nums[j]
\]

where \(0 \le i < j < N\).

## Input Format

- The first line contains an integer \(N\), representing the number of elements.
- The second line contains \(N\) space-separated non-negative integers representing `nums`.

## Output Format

Print a single integer representing the maximum XOR value obtainable from any two elements at different indices.

## Constraints

- \(2 \le N \le 2 \times 10^5\)
- \(0 \le nums[i] \le 2^{31}-1\), for all \(0 \le i < N\).
- All array elements are integers.
- The two selected elements must be at different indices.
- Duplicate values are allowed.

## Example 1

**Input**
```text id="f4m8qa"
6
3 10 5 25 2 8
```

**Output**
```text id="v7k2nd"
28
```

**Explanation**

The maximum XOR is obtained by selecting \(5\) and \(25\):

\[
5 \oplus 25 = 00101_2 \oplus 11001_2 = 11100_2 = 28
\]

Therefore, the answer is \(28\).

## Example 2

**Input**
```text id="r3p6wc"
3
1 2 3
```

**Output**
```text id="h9t4yb"
3
```

**Explanation**

The possible XOR values are:

- \(1 \oplus 2 = 3\)
- \(1 \oplus 3 = 2\)
- \(2 \oplus 3 = 1\)

The maximum value is \(3\).