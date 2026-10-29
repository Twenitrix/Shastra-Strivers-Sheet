# Number of Distinct Substrings in a String

**Difficulty:** Medium  
**Tags:** String, Hashing, Trie, Suffix Array

## Problem Statement

You are given a string \(S\) consisting of lowercase English letters.

A substring is a contiguous sequence of characters within a string. Two substrings are considered distinct if their character sequences differ.

Your task is to count the total number of distinct **non-empty substrings** of \(S\).

If the same substring occurs multiple times in different positions, it must be counted only once.

## Input Format

- The first line contains a string \(S\), consisting of lowercase English letters.

## Output Format

Print a single integer representing the number of distinct non-empty substrings of \(S\).

## Constraints

- \(1 \le |S| \le 10^5\)
- \(S_i \in \{a,b,c,\ldots,z\}\), for all \(0 \le i < |S|\).
- The string contains only lowercase English letters.
- Only non-empty substrings are counted.
- Substrings with identical character sequences are counted once, regardless of their positions.

## Example 1

**Input**
```text id="g4m8qa"
ababa
```

**Output**
```text id="n7k2rd"
9
```

**Explanation**

The distinct non-empty substrings are:

`a`, `b`, `ab`, `ba`, `aba`, `bab`, `abab`, `baba`, `ababa`.

There are \(9\) distinct substrings, so the answer is \(9\).

## Example 2

**Input**
```text id="v5p3wb"
aaa
```

**Output**
```text id="j8x6nc"
3
```

**Explanation**

The distinct non-empty substrings are:

- `"a"`
- `"aa"`
- `"aaa"`

Although `"a"` and `"aa"` occur multiple times, each distinct substring is counted only once. Therefore, the answer is \(3\).