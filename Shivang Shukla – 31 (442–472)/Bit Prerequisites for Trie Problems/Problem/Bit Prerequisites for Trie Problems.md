# Bit Prerequisites for Trie Problems

**Difficulty:** Easy  
**Tags:** Bit Manipulation, Binary Representation, Bitwise Operators

## Problem Statement

You are given an array of \(N\) non-negative integers. Your task is to calculate the bitwise AND, bitwise OR, and bitwise XOR of all elements in the array.

The bitwise AND operation sets a bit to \(1\) only when the corresponding bit is \(1\) in every element. The bitwise OR operation sets a bit to \(1\) when the corresponding bit is \(1\) in at least one element. The bitwise XOR operation sets a bit to \(1\) when the corresponding bits differ.

Calculate and print the three results in the specified order.

## Input Format

- The first line contains an integer \(N\), representing the number of elements.
- The second line contains \(N\) space-separated non-negative integers \(A_1, A_2, \ldots, A_N\).

## Output Format

Print three space-separated integers representing:

1. The bitwise AND of all elements.
2. The bitwise OR of all elements.
3. The bitwise XOR of all elements.

## Constraints

- \(1 \le N \le 10^5\)
- \(0 \le A_i \le 10^9\), for all \(1 \le i \le N\).
- All array elements are integers.
- Duplicate elements are allowed.

## Example 1

**Input**
```text
4
5 3 7 1
```

**Output**
```text
1 7 0
```

**Explanation**

The binary representations are:

- \(5 = 101_2\)
- \(3 = 011_2\)
- \(7 = 111_2\)
- \(1 = 001_2\)

- Bitwise AND: \(101 \,\&\, 011 \,\&\, 111 \,\&\, 001 = 001_2 = 1\).
- Bitwise OR: \(101 \,|\, 011 \,|\, 111 \,|\, 001 = 111_2 = 7\).
- Bitwise XOR: \(101 \,\oplus\, 011 \,\oplus\, 111 \,\oplus\, 001 = 000_2 = 0\).

## Example 2

**Input**
```text
3
2 2 5
```

**Output**
```text
0 7 5
```

**Explanation**

The binary representations are \(2 = 010_2\) and \(5 = 101_2\).

- Bitwise AND: \(010 \,\&\, 010 \,\&\, 101 = 000_2 = 0\).
- Bitwise OR: \(010 \,|\, 010 \,|\, 101 = 111_2 = 7\).
- Bitwise XOR: \(010 \,\oplus\, 010 \,\oplus\, 101 = 101_2 = 5\).