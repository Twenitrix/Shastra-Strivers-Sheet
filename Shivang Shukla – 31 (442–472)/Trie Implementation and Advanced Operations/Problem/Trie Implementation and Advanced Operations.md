# Trie Implementation and Advanced Operations

**Difficulty:** Hard  
**Tags:** Trie, String, Data Structures

## Problem Statement

You are required to implement a Trie (Prefix Tree) that supports inserting words, searching for complete words, checking whether a prefix exists, and deleting words.

A Trie is a tree-based data structure used to store strings efficiently. Each node represents a character, and paths from the root represent prefixes of stored words.

Implement the following operations:

1. **INSERT** `word`: Insert the given word into the Trie. If it already exists, do not create a duplicate entry.
2. **SEARCH** `word`: Print `true` if the complete word exists in the Trie; otherwise, print `false`.
3. **PREFIX** `prefix`: Print `true` if at least one stored word starts with the given prefix; otherwise, print `false`.
4. **DELETE** `word`: Remove the word from the Trie if it exists. If it does not exist, leave the Trie unchanged.
5. **COUNT** `prefix`: Print the number of distinct stored words that start with the given prefix.

Deleting a word must not remove nodes that are still required by another stored word.

## Input Format

- The first line contains an integer \(Q\), representing the number of operations.
- Each of the next \(Q\) lines contains an operation name followed by a word or prefix.

The operation formats are:

- `INSERT word`
- `SEARCH word`
- `PREFIX prefix`
- `DELETE word`
- `COUNT prefix`

## Output Format

For each `SEARCH`, `PREFIX`, and `COUNT` operation, print the corresponding result on a separate line.

- `SEARCH` and `PREFIX` print `true` or `false`.
- `COUNT` prints the number of distinct stored words that start with the specified prefix.
- `INSERT` and `DELETE` produce no output.

## Constraints

- \(1 \le Q \le 10^5\)
- \(1 \le |word|, |prefix| \le 10^3\)
- The total number of characters across all operations does not exceed \(10^6\).
- All words and prefixes contain only lowercase English letters (`a`–`z`).
- Duplicate insertions must not increase the number of distinct stored words.
- Deleting a nonexistent word must not change the Trie.
- Prefix counts must include each distinct stored word exactly once.

## Example 1

**Input**
```text id="f6k2mx"
10
INSERT apple
INSERT app
SEARCH app
PREFIX ap
COUNT ap
DELETE app
SEARCH app
SEARCH apple
COUNT ap
PREFIX appl
```

**Output**
```text id="v8r4nc"
true
true
2
false
true
1
true
```

**Explanation**

- `app` and `apple` are inserted into the Trie.
- `SEARCH app` returns `true` because `app` exists as a complete word.
- `PREFIX ap` returns `true` because both words begin with `ap`.
- `COUNT ap` returns `2` because two distinct words start with `ap`.
- After deleting `app`, searching for `app` returns `false`, while `apple` remains stored.
- The count for prefix `ap` becomes `1`.
- `PREFIX appl` returns `true` because `apple` starts with `appl`.

## Example 2

**Input**
```text id="n3p7wb"
6
INSERT cat
INSERT car
INSERT cat
COUNT ca
DELETE dog
SEARCH cat
```

**Output**
```text id="q5m9vd"
2
true
```

**Explanation**

- Inserting `cat` twice stores only one distinct copy.
- Both `cat` and `car` start with `ca`, so the count is `2`.
- Deleting `dog` has no effect because it does not exist in the Trie.
- Searching for `cat` returns `true`.