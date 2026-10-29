# Matrix Chain Multiplication — Bottom-Up (DP-49)

**Difficulty:** Hard  
**Tags:** Dynamic Programming, Tabulation, Matrix Chain Multiplication

## Problem Statement

You are given an array \(p\) of \(N\) positive integers representing the dimensions of \(N-1\) matrices.

The dimensions of matrix \(A_i\) are \(p_{i-1} \times p_i\), where \(1 \le i \le N-1\).

Your task is to find the minimum number of scalar multiplications required to multiply all the matrices.

Matrix multiplication is associative, so the matrices can be grouped in different ways while preserving their order. Each valid grouping may have a different multiplication cost.

Determine the minimum possible cost of multiplying the entire matrix chain.

## Input Format

- The first line contains an integer \(N\), representing the number of dimensions.
- The second line contains \(N\) space-separated integers representing the array \(p\).

## Output Format

Print a single integer representing the minimum number of scalar multiplications required.

## Constraints

- \(2 \le N \le 100\)
- \(1 \le p_i \le 500\), for all \(0 \le i < N\).
- All matrix dimensions are positive integers.
- The number of matrices is \(N-1\).
- The relative order of matrices must remain unchanged.
- Only the placement of parentheses can be changed.

## Example 1

**Input**
```text id="k8m3vq"
5
40 20 30 10 30
```

**Output**
```text id="p5x2nd"
26000
```

**Explanation**

The four matrices have dimensions:

- \(A_1: 40 \times 20\)
- \(A_2: 20 \times 30\)
- \(A_3: 30 \times 10\)
- \(A_4: 10 \times 30\)

An optimal grouping is \(((A_1(A_2A_3))A_4)\).

The multiplication costs are:

- \(A_2A_3: 20 \times 30 \times 10 = 6000\)
- \(A_1(A_2A_3): 40 \times 20 \times 10 = 8000\)
- \((A_1(A_2A_3))A_4: 40 \times 10 \times 30 = 12000\)

Total cost \(= 6000 + 8000 + 12000 = 26000\).

## Example 2

**Input**
```text id="d9w4ra"
3
10 20 30
```

**Output**
```text id="t2h7jc"
6000
```

**Explanation**

There are two matrices with dimensions \(10 \times 20\) and \(20 \times 30\).

The cost of multiplying them is:

\[
10 \times 20 \times 30 = 6000
\]

Therefore, the minimum multiplication cost is \(6000\).