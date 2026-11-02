# Maximum Rectangle Area with All 1's (DP-55)

**Difficulty:** Hard  
**Tags:** Array, Dynamic Programming, Matrix, Stack

## Problem Statement

You are given a binary matrix of size \(N \times M\) containing only \(0\)s and \(1\)s.

Your task is to find the area of the largest rectangular submatrix that contains only \(1\)s.

A rectangle must consist of consecutive rows and consecutive columns. Every element inside the selected rectangle must be equal to \(1\).

Return the maximum possible area of such a rectangle. If the matrix contains no \(1\)s, return \(0\).

## Input Format

- The first line contains two integers \(N\) and \(M\), representing the number of rows and columns.
- The next \(N\) lines each contain \(M\) space-separated integers representing the binary matrix.

## Output Format

Print a single integer representing the maximum area of a rectangle containing only \(1\)s.

## Constraints

- \(1 \le N, M \le 200\)
- \(matrix[i][j] \in \{0,1\}\), for all \(0 \le i < N\) and \(0 \le j < M\).
- The matrix contains exactly \(N\) rows and \(M\) columns.
- The rectangle must consist of consecutive rows and consecutive columns.
- Every element inside the rectangle must be \(1\).
- The maximum possible area is at most \(N \times M\).

## Example 1

**Input**
```text id="x4p9kc"
4 5
1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0
```

**Output**
```text id="j6w2ra"
6
```

**Explanation**

The largest rectangle containing only \(1\)s has \(3\) rows and \(2\) columns, giving an area of:

\[
3 \times 2 = 6
\]

Therefore, the maximum area is \(6\).

## Example 2

**Input**
```text id="m8q3vd"
2 3
0 0 0
0 0 0
```

**Output**
```text id="n5k7yb"
0
```

**Explanation**

The matrix contains no \(1\)s. Therefore, no rectangle containing only \(1\)s can be formed, and the maximum area is \(0\).