# Trie Implementation and Operations

**Difficulty:** Medium  
**Tags:** Trie, String, Data Structures

## Problem Statement

You are required to implement a Trie (Prefix Tree) that supports inserting words, searching for complete words, and checking whether a prefix exists.

A Trie is a tree-based data structure in which each node represents a character. Words sharing common prefixes can share the same path in the Trie.

Implement the following operations:

1. **INSERT** `word`: Insert the given word into the Trie.
2. **SEARCH** `word`: Print `true` if the complete word exists in the Trie; otherwise, print `false`.
3. **STARTSWITH** `prefix`: Print `true` if at least one stored word starts with the given prefix; otherwise, print `false`.

A word is considered present only when its complete path ends at a node marked as the end of a word. A prefix does not need to represent a complete word.

Duplicate insertions must not affect search results.

## Input Format

- The first line contains an integer \(Q\), representing the number of operations.
- Each of the next \(Q\) lines contains an operation name followed by a word or prefix.

The supported operations are:

- `INSERT word`
- `SEARCH word`
- `STARTSWITH prefix`

## Output Format

For each `SEARCH` and `STARTSWITH` operation, print `true` or `false` on a separate line.

The `INSERT` operation does not produce any output.

## Constraints

- \(1 \le Q \le 10^5\)
- \(1 \le |word|, |prefix| \le 10^3\)
- The total number of characters across all operations does not exceed \(10^6\).
- All words and prefixes contain only lowercase English letters (`a`–`z`).
- Duplicate insertions are allowed.
- A word must be marked as complete only at its final character.
- A prefix is considered present if at least one stored word begins with it.

## Example 1

**Input**
```text id="p4n8sx"
6
INSERT apple
INSERT app
SEARCH app
SEARCH appl
STARTSWITH appl
STARTSWITH banana
```

**Output**
```text id="r7m2vd"
true
false
true
false
```

**Explanation**

- `app` exists as a complete word, so `SEARCH app` returns `true`.
- `appl` is a prefix of `apple` but is not stored as a complete word, so `SEARCH appl` returns `false`.
- Since `apple` starts with `appl`, `STARTSWITH appl` returns `true`.
- No stored word starts with `banana`, so the last operation returns `false`.

## Example 2

**Input**
```text id="c6w3qy"
5
INSERT cat
INSERT car
SEARCH cat
SEARCH cab
STARTSWITH ca
```

**Output**
```text id="m9k5zb"
true
false
true
```

**Explanation**

- `cat` exists in the Trie, so its search result is `true`.
- `cab` has not been inserted, so its search result is `false`.
- Both `cat` and `car` start with `ca`, so `STARTSWITH ca` returns `true`.