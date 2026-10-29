# Editorial: Number of Distinct Substrings in a String

## Problem Overview

Given a string `s`, count the number of distinct non-empty substrings it contains.

A substring is a contiguous sequence of characters from the original string. If the same substring appears multiple times, count it only once.

**Example:**

Input:
```text
s = "ababa"
```

Output:
```text
9
```

Explanation:

The distinct non-empty substrings are:

`a`, `b`, `ab`, `ba`, `aba`, `bab`, `abab`, `baba`, `ababa`.

Therefore, the answer is `9`.

## Approach: Trie

A string of length `n` has \(O(n^2)\) possible substrings. Generating all substrings and storing them in a set is a straightforward solution, but storing and comparing strings can require substantial additional time and space.

A trie provides another approach.

For every starting position, insert the suffix beginning at that position into the trie, one character at a time. Whenever a new trie node is created, it represents a substring that has not been encountered before.

Therefore, the total number of newly created nodes equals the number of distinct non-empty substrings.

## Algorithm

1. Create an empty trie.
2. Initialize `count = 0`.
3. For every starting index `i` in the string:
    - Start at the trie root.
    - Traverse characters from index `i` to the end.
    - If the corresponding child node does not exist, create it and increment `count`.
    - Move to the child node.
4. Return `count`.

## Java Implementation

```java
class Solution {
    static class Node {
        Node[] child = new Node[26];
    }

    public int countDistinctSubstring(String s) {
        Node root = new Node();
        int count = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            Node node = root;

            for (int j = i; j < n; j++) {
                int index = s.charAt(j) - 'a';

                if (node.child[index] == null) {
                    node.child[index] = new Node();
                    count++;
                }

                node = node.child[index];
            }
        }

        return count;
    }
}
```

## Dry Run

Input:

```text
s = "aba"
```

**Starting at index 0:** Insert substrings `a`, `ab`, and `aba`. Three new nodes are created.

**Starting at index 1:** Insert `b` and `ba`. Both require new nodes, so the count increases by two.

**Starting at index 2:** The substring `a` already exists in the trie, so no new node is created.

The distinct substrings are:

`a`, `ab`, `aba`, `b`, `ba`

Output:

```text
5
```

## Complexity Analysis

Let \(n\) be the length of the string.

- **Time Complexity:** \(O(n^2)\), because every possible substring is traversed through the trie.
- **Space Complexity:** \(O(n^2)\), because the trie can contain up to \(O(n^2)\) nodes.

## Key Takeaways

- A string can have up to \(\frac{n(n+1)}{2}\) non-empty substrings.
- A trie shares common prefixes between substrings.
- Each newly created trie node represents one distinct substring.
- Counting new nodes avoids storing every substring separately.
- This solution assumes the input contains lowercase English letters.

