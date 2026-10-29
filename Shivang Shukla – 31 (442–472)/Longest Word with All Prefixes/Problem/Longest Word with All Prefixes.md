# Longest Word with All Prefixes

**Difficulty:** Medium  
**Tags:** Array, String, Trie, Sorting

## Problem Statement

You are given an array of strings `words`. A word is considered **complete** if every non-empty prefix of that word is also present in the given array.

For example, the word `"ninja"` is complete if `"n"`, `"ni"`, `"nin"`, `"ninj"`, and `"ninja"` are all present in the array.

Your task is to find the longest complete word in the array.

If multiple complete words have the same maximum length, return the lexicographically smallest one. If no complete word exists, return `"None"`.

## Input Format

- The first line contains an integer \(N\), representing the number of words.
- The second line contains \(N\) space-separated strings representing the array `words`.

## Output Format

Print the longest complete word.

If multiple words have the same maximum length, print the lexicographically smallest one. If no complete word exists, print `None`.

## Constraints

- \(1 \le N \le 10^5\)
- \(1 \le |\text{words}[i]| \le 10\), for all \(0 \le i < N\).
- Each word contains only lowercase English letters (`a`–`z`).
- The total number of characters across all words does not exceed \(10^6\).
- A word is complete only if every non-empty prefix of that word is present in the input array.
- Lexicographical order follows the usual dictionary order.

## Example 1

**Input**
```text id="g4x8mz"
6
n nin ninj ninja ninga ni
```

**Output**
```text id="j2q6vr"
ninja
```

**Explanation**

The prefixes `"n"`, `"ni"`, `"nin"`, `"ninj"`, and `"ninja"` are all present in the array.

Therefore, `"ninja"` is a complete word and is the longest complete word in the array.

## Example 2

**Input**
```text id="a7k3wp"
4
ab abc a abd
```

**Output**
```text id="f5r9tc"
None
```

**Explanation**

The word `"a"` is present, but `"b"` is not. Therefore, `"ab"`, `"abc"`, and `"abd"` are not complete words. No word satisfies the complete-word condition, so the answer is `None`.