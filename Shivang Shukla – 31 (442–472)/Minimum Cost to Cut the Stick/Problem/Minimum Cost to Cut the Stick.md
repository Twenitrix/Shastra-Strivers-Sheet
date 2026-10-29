# Minimum Cost to Cut the Stick

**Difficulty:** Hard  
**Tags:** Dynamic Programming, Recursion, Memoization, Tabulation

## Problem Statement

You are given a wooden stick of length \(N\) and an array `cuts` containing the positions at which the stick must be cut.

Each cut must be made at one of the specified positions. The cost of making a cut is equal to the length of the stick segment being cut at that moment.

After a cut, the stick is divided into two smaller segments. The order in which the cuts are performed can be chosen freely.

Your task is to determine the minimum total cost required to perform all the specified cuts.

## Input Format

- The first line contains two integers \(N\) and \(C\), representing the stick length and the number of cuts.
- The second line contains \(C\) space-separated integers representing the positions in the `cuts` array.

## Output Format

Print a single integer representing the minimum total cost required to perform all cuts.

## Constraints

- \(2 \le N \le 10^6\)
- \(1 \le C \le 100\)
- \(1 \le \text{cuts}[i] \le N-1\), for all \(0 \le i < C\).
- All cut positions are distinct integers.
- Every cut position lies strictly inside the original stick.
- Every specified cut must be performed exactly once.
- The total cost is minimized by choosing the order of cuts optimally.

## Example 1

**Input**
```text id="f5k8qa"
7 4
1 3 4 5
```

**Output**
```text id="v2m6rd"
16
```

**Explanation**

The stick has length \(7\), and cuts must be made at positions \(1,3,4,5\).

One optimal order is:

- Cut at position \(3\): cost \(7\).
- Cut at position \(5\): cost \(4\).
- Cut at position \(1\): cost \(3\).
- Cut at position \(4\): cost \(2\).

Total cost:

\[
7+4+3+2=16
\]

Therefore, the minimum total cost is \(16\).

## Example 2

**Input**
```text id="x9r3wb"
9 2
5 6
```

**Output**
```text id="p4j7nc"
15
```

**Explanation**

- Cut at position \(5\) first: cost \(9\).
- Cut at position \(6\) in the remaining segment of length \(4\): cost \(4\).

Alternatively, cutting at position \(6\) first costs \(9\), followed by a cut at position \(5\) costing \(6\).

The minimum total cost is \(9+4=13\), so the correct output is `13`.