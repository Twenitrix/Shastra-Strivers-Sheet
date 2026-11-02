# Count Square Submatrices with All Ones

**Difficulty:** Medium  
**Tags:** Array, Dynamic Programming, Matrix

## Problem Statement

You are given an \(m \times n\) binary matrix `matrix` containing only \(0\)s and \(1\)s.

Your task is to count the total number of square submatrices that contain only \(1\)s.

A square submatrix is a contiguous submatrix having the same number of rows and columns. Squares of every possible size must be considered.

Return the total number of such square submatrices.

## Input Format

- The first line contains two integers \(m\) and \(n\), representing the number of rows and columns.
- The next \(m\) lines each contain \(n\) space-separated integers representing the matrix.

## Output Format

Print a single integer representing the total number of square submatrices containing only \(1\)s.

## Constraints

- \(1 \le m, n \le 300\)
- \(matrix[i][j] \in \{0,1\}\), for all \(0 \le i < m\) and \(0 \le j < n\).
- The matrix contains exactly \(m\) rows and \(n\) columns.
- A valid square submatrix must contain only \(1\)s.

## Example 1

**Input**
```text
3 4
0 1 1 1
1 1 1 1
0 1 1 1
```

**Output**
```text
15
```

**Explanation**

There are:

- \(10\) square submatrices of size \(1 \times 1\).
- \(4\) square submatrices of size \(2 \times 2\).
- \(1\) square submatrix of size \(3 \times 3\).

Therefore, the total number is \(10 + 4 + 1 = 15\).

## Example 2

**Input**
```text
2 2
0 1
1 0
```

**Output**
```text
2
```

**Explanation**

There are two \(1 \times 1\) square submatrices containing only \(1\)s. No larger square submatrix contains only \(1\)s.

Therefore, the answer is \(2\).