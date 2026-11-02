# Longest String Chain

**Difficulty:** Medium  
**Tags:** Array, String, Dynamic Programming, Sorting

## Problem Statement

You are given an array of strings `words`. A string `wordA` is considered a predecessor of `wordB` if you can insert exactly one lowercase English letter at any position in `wordA` without changing the order of its existing characters to obtain `wordB`.

A **string chain** is a sequence of words in which every word, except the first, has the previous word as its predecessor.

Your task is to find the length of the longest possible string chain that can be formed using the given words.

Each word from the input array may be used at most once in a chain.

## Input Format

- The first line contains an integer \(N\), representing the number of words.
- The next \(N\) lines each contain one string representing a word.

## Output Format

Print a single integer representing the length of the longest possible string chain.

## Constraints

- \(1 \le N \le 1000\)
- \(1 \le |\text{words}[i]| \le 16\), for all \(0 \le i < N\).
- Each word contains only lowercase English letters (`a`–`z`).
- The total number of characters across all words does not exceed \(10^4\).
- The input words are distinct.
- A valid predecessor must differ in length by exactly \(1\).
- The characters of the predecessor must appear in the same relative order in the longer word.

## Example 1

**Input**
```text
6
a
b
ba
bca
bda
bdca
```

**Output**
```text
4
```

**Explanation**

One longest string chain is:

`a → ba → bda → bdca`

Each word is formed by inserting exactly one character into the previous word while preserving the order of its existing characters.

The chain contains \(4\) words, so the answer is \(4\).

## Example 2

**Input**
```text
5
xbc
pcxbcf
xb
cxbc
pcxbc
```

**Output**
```text
5
```

**Explanation**

One longest string chain is:

`xb → xbc → cxbc → pcxbc → pcxbcf`

Each word is a valid successor of the previous word. The chain contains \(5\) words, so the answer is \(5\).