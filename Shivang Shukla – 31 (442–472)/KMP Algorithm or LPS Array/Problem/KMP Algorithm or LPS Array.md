# KMP Algorithm or LPS Array

**Difficulty:** Medium  
**Tags:** String, Pattern Matching, KMP Algorithm

## Problem Statement

You are given a text string \(T\) and a pattern string \(P\). Your task is to find all occurrences of \(P\) in \(T\) using the Knuth-Morris-Pratt (KMP) string-matching algorithm.

The KMP algorithm uses an array called the **Longest Proper Prefix which is also a Suffix (LPS)** array to avoid repeatedly comparing characters that have already been matched.

For every position \(i\) in the pattern, the LPS value represents the length of the longest proper prefix of the substring \(P[0 \ldots i]\) that is also a suffix of that substring.

Print the starting indices of all occurrences of \(P\) in \(T\), using **0-based indexing**. If the pattern does not occur in the text, print `-1`.

## Input Format

- The first line contains the string \(T\), representing the text.
- The second line contains the string \(P\), representing the pattern.

## Output Format

Print the starting indices of all occurrences of \(P\) in \(T\), separated by spaces, in increasing order.

If no occurrence exists, print `-1`.

## Constraints

- \(1 \le |T| \le 10^6\)
- \(1 \le |P| \le 10^6\)
- Both strings contain only lowercase English letters.
- The matching indices are 0-based.
- All overlapping occurrences must be reported.

## Example 1

**Input**
```text id="d3q7va"
ababcabcabababd
ababd
```

**Output**
```text id="x5m8kc"
10
```

**Explanation**

The pattern `ababd` occurs in the text starting at index \(10\).

Therefore, the output is `10`.

## Example 2

**Input**
```text id="n4t2px"
aaaaa
aa
```

**Output**
```text id="v9c6kw"
0 1 2 3
```

**Explanation**

The pattern `aa` occurs at indices \(0\), \(1\), \(2\), and \(3\). These occurrences overlap, and all of them must be reported.