# Rabin-Karp Algorithm

**Difficulty:** Medium  
**Tags:** String, Pattern Matching, Hashing

## Problem Statement

You are given a text string \(T\) of length \(N\) and a pattern string \(P\) of length \(M\).

Your task is to find all occurrences of the pattern \(P\) in the text \(T\) using the Rabin-Karp string matching algorithm.

An occurrence is a position in the text where the substring of length \(M\) exactly matches the pattern.

Use **0-based indexing** to report the starting positions of all occurrences.

If the pattern does not occur in the text, print `-1`.

## Input Format

- The first line contains the text string \(T\).
- The second line contains the pattern string \(P\).

## Output Format

- Print all starting indices where the pattern occurs in the text, in increasing order, separated by spaces.
- If no occurrence exists, print `-1`.

## Constraints

- \(1 \le |T| \le 10^5\)
- \(1 \le |P| \le 10^5\)
- Both strings contain only lowercase English letters (`a`–`z`).
- \(N = |T|\) and \(M = |P|\).
- The pattern may occur multiple times, including at overlapping positions.
- If \(M > N\), no occurrence exists.

## Example 1

**Input**
```text id="h3k7qw"
ababcabcabababd
abab
```

**Output**
```text id="m9v2rt"
0 8
```

**Explanation**

The pattern `abab` occurs in the text starting at indices \(0\) and \(8\).

Both positions are reported in increasing order.

## Example 2

**Input**
```text id="p5d8nx"
aaaaa
aaa
```

**Output**
```text id="q6w4zb"
0 1 2
```

**Explanation**

The pattern `aaa` occurs at indices \(0\), \(1\), and \(2\).

These occurrences overlap, but all must be reported.

## Example 3

**Input**
```text id="t2c6vf"
abcdef
xyz
```

**Output**
```text id="w8n3jy"
-1
```

**Explanation**

The pattern `xyz` does not occur anywhere in the text, so the output is `-1`.