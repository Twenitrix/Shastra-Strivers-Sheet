# Z Function

**Difficulty:** Medium  
**Tags:** String, Pattern Matching, Z Algorithm

## Problem Statement

You are given a string \(S\) of length \(N\).

The **Z-function** of a string is an array \(Z\) of length \(N\), where \(Z[i]\) represents the length of the longest substring starting at index \(i\) that matches a prefix of the string \(S\).

In other words, \(Z[i]\) is the maximum number of consecutive characters that match between \(S[i \ldots N-1]\) and the prefix of \(S\).

For this problem, define \(Z[0]=0\).

Your task is to calculate and print the Z-function of the given string.

## Input Format

- The first line contains a string \(S\).

## Output Format

Print \(N\) space-separated integers representing the Z-function of \(S\).

## Constraints

- \(1 \le N \le 10^6\)
- \(N = |S|\).
- \(S\) contains only lowercase English letters (`a`–`z`).
- \(Z[0]=0\).
- For every index \(i\), \(0 \le i < N\), the value \(Z[i]\) must satisfy \(0 \le Z[i] \le N-i\).

## Example 1

**Input**
```text
abacaba
```

**Output**
```text
0 0 1 0 3 0 1
```

**Explanation**

- At index \(0\), \(Z[0]=0\) by definition.
- At index \(1\), `b` does not match the prefix starting with `a`, so \(Z[1]=0\).
- At index \(2\), the substring starts with `a`, matching one character of the prefix, so \(Z[2]=1\).
- At index \(4\), the substring `aba` matches the prefix `aba`, so \(Z[4]=3\).
- At index \(6\), the substring `a` matches the first character of the prefix, so \(Z[6]=1\).

Therefore, the Z-function is `0 0 1 0 3 0 1`.

## Example 2

**Input**
```text
aaaaa
```

**Output**
```text
0 4 3 2 1
```

**Explanation**

Every character is `a`, so each suffix matches the prefix for as many characters as remain.

- At index \(1\), four characters match.
- At index \(2\), three characters match.
- At index \(3\), two characters match.
- At index \(4\), one character matches.

Hence, the Z-function is `0 4 3 2 1`.