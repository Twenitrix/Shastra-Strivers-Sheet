# Editorial: Trie Implementation and Operations

## Problem Overview

A **Trie**, also known as a prefix tree, is a tree-based data structure used to store strings and efficiently perform insertion, searching, and prefix-checking operations.

Each node represents a character, and each path from the root represents a prefix or a complete word.

For example, inserting `"apple"` and `"app"` shares the prefix `"app"` in the trie.

## Approach: Trie Data Structure

Each node contains an array of 26 child references, one for each lowercase English letter, and a boolean flag indicating whether a complete word ends at that node.

The trie supports three main operations:

- `insert(word)`: Inserts a word into the trie.
- `search(word)`: Checks whether the complete word exists.
- `startsWith(prefix)`: Checks whether any inserted word begins with the given prefix.

During each operation, traverse the trie character by character. If a required child node does not exist during insertion, create it.

## Algorithm

**Insertion**

1. Start at the root.
2. Traverse each character of the word.
3. Create a child node if it does not exist.
4. Move to the child node.
5. Mark the final node as the end of a word.

**Search**

1. Start at the root.
2. Traverse the characters of the word.
3. If a required child is missing, return `false`.
4. After traversal, return whether the final node marks the end of a word.

**Prefix Check**

1. Start at the root.
2. Traverse each character of the prefix.
3. If a required child is missing, return `false`.
4. If the entire prefix is traversed, return `true`.

## Java Implementation

```java id="n2j8cp"
class Trie {
    static class Node {
        Node[] child = new Node[26];
        boolean isEnd = false;
    }

    private final Node root = new Node();

    public void insert(String word) {
        Node node = root;

        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';

            if (node.child[index] == null) {
                node.child[index] = new Node();
            }

            node = node.child[index];
        }

        node.isEnd = true;
    }

    public boolean search(String word) {
        Node node = root;

        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';

            if (node.child[index] == null) {
                return false;
            }

            node = node.child[index];
        }

        return node.isEnd;
    }

    public boolean startsWith(String prefix) {
        Node node = root;

        for (int i = 0; i < prefix.length(); i++) {
            int index = prefix.charAt(i) - 'a';

            if (node.child[index] == null) {
                return false;
            }

            node = node.child[index];
        }

        return true;
    }
}
```

## Dry Run

Operations:

```text id="bb71qz"
insert("apple")
search("apple")
search("app")
startsWith("app")
```

Results:

- `insert("apple")`: Creates the path for `"apple"` and marks its final node.
- `search("apple")`: Returns `true` because `"apple"` ends at a marked node.
- `search("app")`: Returns `false` because `"app"` is only a prefix, not a complete inserted word.
- `startsWith("app")`: Returns `true` because the prefix path exists.

## Complexity Analysis

Let \(L\) be the length of the input word or prefix.

- **Insertion:** \(O(L)\)
- **Search:** \(O(L)\)
- **Prefix Check:** \(O(L)\)
- **Space Complexity:** \(O(26N)\), where \(N\) is the number of trie nodes in the worst case.

## Key Takeaways

- A trie stores strings by sharing their common prefixes.
- Each node represents a character transition.
- The end-of-word flag distinguishes complete words from prefixes.
- Insertion, searching, and prefix checking take linear time in the input length.
- This implementation supports lowercase English letters.

