# Matrix Chain Multiplication

**Difficulty:** Hard  
**Tags:** Dynamic Programming, Recursion, Memoization

## Problem Statement

You are given an array \(p\) of \(N\) positive integers representing the dimensions of a sequence of \(N-1\) matrices.

The dimensions of matrix \(A_i\) are \(p_{i-1} \times p_i\), for \(1 \le i \le N-1\).

Your task is to determine the minimum number of scalar multiplications required to multiply the entire chain of matrices.

Matrix multiplication is associative, meaning the matrices can be grouped in different ways without changing the final result. However, different parenthesizations may require different numbers of scalar multiplications.

Find the minimum cost among all valid ways of parenthesizing the matrix chain. The order of the matrices must remain unchanged.

## Input Format

- The first line contains an integer \(N\), representing the number of dimensions.
- The second line contains \(N\) space-separated integers representing the array \(p\).

## Output Format

Print a single integer representing the minimum number of scalar multiplications required.

## Constraints

- \(2 \le N \le 100\)
- \(1 \le p_i \le 500\), for all \(0 \le i < N\).
- All dimensions are positive integers.
- The number of matrices is \(N-1\).
- The relative order of matrices cannot be changed; only their parenthesization may vary.

## Example 1

**Input**
```text id="u7m2kc"
5
40 20 30 10 30
```

**Output**
```text id="w9q4fd"
26000
```

**Explanation**

The dimensions represent four matrices:

- \(A_1: 40 \times 20\)
- \(A_2: 20 \times 30\)
- \(A_3: 30 \times 10\)
- \(A_4: 10 \times 30\)

One optimal parenthesization is \(((A_1(A_2A_3))A_4)\).

- Multiplying \(A_2\) and \(A_3\) costs \(20 \times 30 \times 10 = 6000\).
- Multiplying \(A_1\) by the result costs \(40 \times 20 \times 10 = 8000\).
- Multiplying that result by \(A_4\) costs \(40 \times 10 \times 30 = 12000\).

Total cost \(= 6000 + 8000 + 12000 = 26000\).

## Example 2

**Input**
```text id="v3n8ha"
3
10 20 30
```

**Output**
```text id="c5j1yb"
6000
```

**Explanation**

There are two matrices:

- \(A_1: 10 \times 20\)
- \(A_2: 20 \times 30\)

They can be multiplied directly at a cost of \(10 \times 20 \times 30 = 6000\) scalar multiplications.