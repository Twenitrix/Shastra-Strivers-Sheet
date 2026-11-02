# Maximum XOR with an Element from an Array

**Difficulty:** Hard  
**Tags:** Array, Bit Manipulation, Trie, Binary Search

## Problem Statement

You are given an integer array `nums` and a list of queries. Each query consists of two integers \(x\) and \(m\).

For each query \((x,m)\), find the maximum value of \(x \oplus a\), where \(a\) is an element of `nums` satisfying \(a \le m\).

If no element in `nums` is less than or equal to \(m\), return \(-1\) for that query.

Your task is to answer all queries independently and return the results in the same order as the queries were given.

## Input Format

- The first line contains two integers \(N\) and \(Q\), representing the number of array elements and the number of queries.
- The second line contains \(N\) space-separated integers representing `nums`.
- The next \(Q\) lines each contain two integers \(x\) and \(m\), representing a query.

## Output Format

Print \(Q\) space-separated integers, where each integer is the answer to the corresponding query in the original order.

If no valid array element exists for a query, print \(-1\) for that query.

## Constraints

- \(1 \le N, Q \le 10^5\)
- \(0 \le nums[i] \le 10^9\), for all \(0 \le i < N\).
- \(0 \le x,m \le 10^9\), for every query \((x,m)\).
- All array elements and query values are integers.
- An element \(a\) is eligible for a query only if \(a \le m\).
- Each query must be answered independently.
- The output order must match the input query order.

## Example 1

**Input**
```text id="q5n8vb"
6 3
0 1 2 3 4 7
1 3
5 6
3 7
```

**Output**
```text id="k2r7xd"
3 2 4
```

**Explanation**

- Query \((1,3)\): Eligible elements are \(0,1,2,3\). The maximum XOR is \(1 \oplus 2 = 3\).
- Query \((5,6)\): Eligible elements are \(0,1,2,3,4\). The maximum XOR is \(5 \oplus 3 = 6\). Thus, the correct answer for this query is \(6\).
- Query \((3,7)\): All array elements are eligible. The maximum XOR is \(3 \oplus 4 = 7\).

Therefore, the correct output is `3 6 7`.

## Example 2

**Input**
```text id="w4p9ca"
3 2
5 8 12
2 4
10 6
```

**Output**
```text id="j7m3kf"
-1 15
```

**Explanation**

- Query \((2,4)\): No array element is less than or equal to \(4\), so the answer is \(-1\).
- Query \((10,6)\): Only \(5\) is eligible, and \(10 \oplus 5 = 15\).

Therefore, the output is `-1 15`.