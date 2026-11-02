# Editorial: Trie Implementation and Advanced Operations

## Problem Overview

A **Trie**, also called a prefix tree, is a tree-based data structure used to store and search strings efficiently.

Each node represents a character, and paths from the root represent prefixes or complete words.

Advanced trie operations commonly include:

- Inserting a word.
- Searching for a complete word.
- Checking whether a prefix exists.
- Counting words equal to a given string.
- Counting words starting with a given prefix.
- Erasing a word.

## Approach: Trie with Frequency Counters

Each trie node stores:

- `child[26]`: References to the children for lowercase English letters.
- `endCount`: Number of inserted words ending at this node.
- `prefixCount`: Number of inserted words passing through this node.

The frequency counters make duplicate words and prefix queries easier to handle.

During insertion, increment `prefixCount` for each node visited and increment `endCount` at the final node.

During deletion, first verify that the word exists. Then decrement the relevant counters and remove nodes that are no longer needed.

## Algorithm

**Insert a word**

1. Start at the root.
2. For each character, create the child node if it does not exist.
3. Move to the child and increment its `prefixCount`.
4. Increment `endCount` at the final node.

**Search a word**

1. Traverse the trie using the word's characters.
2. If any required child is missing, return `false`.
3. Return `true` only if the final node has `endCount > 0`.

**Count words with a prefix**

1. Traverse the prefix.
2. If a required child is missing, return `0`.
3. Return the final node's `prefixCount`.

**Erase a word**

1. Confirm that the complete word exists.
2. Decrement the prefix counter along its path.
3. Decrement the terminal node's end counter.
4. Remove unnecessary nodes from the bottom upward.

## Java Implementation

```java id="8b1p2d"
class Trie {
    static class Node {
        Node[] child = new Node[26];
        int endCount = 0;
        int prefixCount = 0;
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
            node.prefixCount++;
        }

        node.endCount++;
    }

    public boolean search(String word) {
        Node node = findNode(word);
        return node != null && node.endCount > 0;
    }

    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;
    }

    public int countWordsEqualTo(String word) {
        Node node = findNode(word);
        return node == null ? 0 : node.endCount;
    }

    public int countWordsStartingWith(String prefix) {
        Node node = findNode(prefix);
        return node == null ? 0 : node.prefixCount;
    }

    public void erase(String word) {
        if (countWordsEqualTo(word) == 0) {
            return;
        }

        Node node = root;

        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            node.child[index].prefixCount--;
            node = node.child[index];
        }

        node.endCount--;
        node = root;

        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            Node next = node.child[index];

            if (next.prefixCount == 0) {
                node.child[index] = null;
                return;
            }

            node = next;
        }
    }

    private Node findNode(String s) {
        Node node = root;

        for (int i = 0; i < s.length(); i++) {
            int index = s.charAt(i) - 'a';

            if (node.child[index] == null) {
                return null;
            }

            node = node.child[index];
        }

        return node;
    }
}
```

## Dry Run

Consider these operations:

```text id="j1r2ne"
insert("apple")
insert("app")
insert("apple")
```

The trie stores `"apple"` twice and `"app"` once.

Results:

- `search("app")` → `true`
- `countWordsEqualTo("apple")` → `2`
- `countWordsStartingWith("app")` → `3`
- `erase("apple")`
- `countWordsEqualTo("apple")` → `1`

After erasing one occurrence of `"apple"`, the other `"apple"` and `"app"` remain in the trie.

## Complexity Analysis

Let \(L\) be the length of the input word or prefix.

- **Insertion:** \(O(L)\)
- **Search:** \(O(L)\)
- **Prefix check:** \(O(L)\)
- **Counting words:** \(O(L)\)
- **Erasing a word:** \(O(L)\)
- **Space Complexity:** \(O(N \times 26)\) in the worst case, where \(N\) is the total number of trie nodes. Each node stores 26 child references.

## Key Takeaways

- A trie shares common prefixes between words.
- `endCount` supports duplicate words.
- `prefixCount` efficiently counts words sharing a prefix.
- Deletion must update counters without removing nodes used by other words.
- This implementation assumes lowercase English letters from `'a'` to `'z'`.

