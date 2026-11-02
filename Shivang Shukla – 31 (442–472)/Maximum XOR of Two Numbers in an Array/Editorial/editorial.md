# Editorial: Maximum XOR of Two Numbers in an Array

## Problem Overview

Given an integer array `nums`, find the maximum XOR value obtainable by XORing any two elements in the array.

The XOR operation returns `1` when the corresponding bits differ and `0` when they are the same.

**Example:**

Input:
```text
nums = [3, 10, 5, 25, 2, 8]
```

Output:
```text
28
```

Explanation: The XOR of `5` and `25` is `5 ^ 25 = 28`, which is the maximum possible value.

## Approach: Binary Trie

A brute-force approach checks every pair, taking \(O(n^2)\) time.

We can optimize this using a binary trie, which stores the binary representation of each number.

For each number, try to find another number that maximizes the XOR result. At every bit position, choosing the opposite bit produces a `1` in the XOR result.

For example, if the current bit is `1`, prefer the trie branch containing `0`. If that branch does not exist, follow the branch containing `1`.

Process bits from the most significant bit to the least significant bit so that higher-value bits are prioritized.

## Algorithm

1. Create a binary trie with branches for `0` and `1`.
2. Insert each number into the trie using its binary representation.
3. For each number, traverse the trie from the most significant bit to the least significant bit.
4. At each bit, choose the opposite bit if that branch exists.
5. Add the corresponding power of two to the XOR result whenever an opposite bit is chosen.
6. Track the maximum XOR result across all numbers.
7. Return the maximum.

## Java Implementation

```java
class Solution {
    static class Node {
        Node[] child = new Node[2];
    }

    static class Trie {
        Node root = new Node();

        void insert(int num) {
            Node node = root;

            for (int i = 31; i >= 0; i--) {
                int bit = (num >>> i) & 1;

                if (node.child[bit] == null) {
                    node.child[bit] = new Node();
                }

                node = node.child[bit];
            }
        }

        int getMax(int num) {
            Node node = root;
            int maxXor = 0;

            for (int i = 31; i >= 0; i--) {
                int bit = (num >>> i) & 1;
                int opposite = 1 - bit;

                if (node.child[opposite] != null) {
                    maxXor |= (1 << i);
                    node = node.child[opposite];
                } else {
                    node = node.child[bit];
                }
            }

            return maxXor;
        }
    }

    public int findMaximumXOR(int[] nums) {
        Trie trie = new Trie();

        for (int i = 0; i < nums.length; i++) {
            trie.insert(nums[i]);
        }

        int maxXor = 0;

        for (int i = 0; i < nums.length; i++) {
            maxXor = Math.max(maxXor, trie.getMax(nums[i]));
        }

        return maxXor;
    }
}
```

## Dry Run

Input:

```text
nums = [2, 5]
```

Binary representations:

```text
2 = 010
5 = 101
```

XOR:

```text
010
101
---
111
```

Therefore:

\[
2 \oplus 5 = 7
\]

Output:

```text
7
```

## Complexity Analysis

Let \(n\) be the number of elements and \(B\) be the number of bits processed per number.

- **Time Complexity:** \(O(nB)\), because each insertion and query processes every bit. For 32-bit integers, this is \(O(32n)\), effectively \(O(n)\).
- **Space Complexity:** \(O(nB)\), because the trie stores up to \(B\) nodes per number.

## Key Takeaways

- A binary trie stores numbers bit by bit.
- To maximize XOR, prefer the opposite bit at each position.
- Process from the most significant bit to the least significant bit.
- The trie reduces the search from quadratic time to linear time for fixed-width integers.

