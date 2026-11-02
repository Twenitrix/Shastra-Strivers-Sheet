# Editorial: Maximum XOR with an Element from an Array

## Problem Overview

Given an integer array `nums` and a list of queries, answer each query of the form `[x, m]`.

For each query, find the maximum value of `x XOR num`, where `num` is an element of `nums` satisfying `num <= m`.

If no element satisfies the condition, return `-1` for that query.

**Example:**

Input:
```text
nums = [0, 1, 2, 3, 4]
queries = [[3, 1], [1, 3], [5, 6]]
```

Output:
```text
[3, 3, 7]
```

Explanation:

- Query `[3, 1]`: eligible values are `0` and `1`. Maximum XOR is `3 XOR 0 = 3`.
- Query `[1, 3]`: eligible values are `0`, `1`, `2`, and `3`. Maximum XOR is `1 XOR 2 = 3`.
- Query `[5, 6]`: all array elements are eligible. Maximum XOR is `5 XOR 2 = 7`.

## Approach: Offline Queries and Binary Trie

A binary trie allows us to find the maximum XOR between a number and any number stored in the trie.

However, each query also has a restriction: the chosen number must be less than or equal to `m`.

To handle this efficiently, sort the array and process queries in increasing order of `m`.

For each query:

1. Insert every array element less than or equal to `m` into the trie.
2. Query the trie to find the maximum XOR with `x`.
3. If no elements have been inserted, return `-1`.

Since the queries are sorted, elements only need to be inserted once. Store each query's original index so that answers can be returned in their original order.

## Algorithm

1. Sort `nums` in ascending order.
2. Store each query along with its original index.
3. Sort the queries by their limit `m`.
4. Initialize an empty binary trie.
5. For each sorted query, insert all remaining numbers satisfying `nums[i] <= m`.
6. If the trie is empty, store `-1` for that query.
7. Otherwise, find the maximum XOR with `x` using the trie.
8. Return the answers in the original query order.

## Java Implementation

```java id="xq72nm"
import java.util.*;

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
            int result = 0;

            for (int i = 31; i >= 0; i--) {
                int bit = (num >>> i) & 1;
                int opposite = 1 - bit;

                if (node.child[opposite] != null) {
                    result |= (1 << i);
                    node = node.child[opposite];
                } else {
                    node = node.child[bit];
                }
            }

            return result;
        }

        boolean isEmpty() {
            return root.child[0] == null && root.child[1] == null;
        }
    }

    public int[] maximizeXor(int[] nums, int[][] queries) {
        Arrays.sort(nums);

        int q = queries.length;
        int[][] sortedQueries = new int[q][3];

        for (int i = 0; i < q; i++) {
            sortedQueries[i][0] = queries[i][0];
            sortedQueries[i][1] = queries[i][1];
            sortedQueries[i][2] = i;
        }

        Arrays.sort(sortedQueries, (a, b) -> Integer.compare(a[1], b[1]));

        int[] ans = new int[q];
        Trie trie = new Trie();
        int i = 0;

        for (int j = 0; j < q; j++) {
            int x = sortedQueries[j][0];
            int limit = sortedQueries[j][1];
            int index = sortedQueries[j][2];

            while (i < nums.length && nums[i] <= limit) {
                trie.insert(nums[i]);
                i++;
            }

            if (trie.isEmpty()) {
                ans[index] = -1;
            } else {
                ans[index] = trie.getMax(x);
            }
        }

        return ans;
    }
}
```

## Dry Run

Input:

```text id="2x7r6s"
nums = [0, 1, 2]
queries = [[3, 1], [1, 0]]
```

The sorted queries are:

```text id="zwbdc5"
[1, 0] with original index 1
[3, 1] with original index 0
```

- Query `[1, 0]`: insert `0`. Maximum XOR is `1 XOR 0 = 1`. Store `1` at index `1`.
- Query `[3, 1]`: insert `1`. Maximum XOR is `3 XOR 0 = 3`. Store `3` at index `0`.

Output in original query order:

```text id="0wnzcp"
[3, 1]
```

## Complexity Analysis

Let \(n\) be the array length, \(q\) be the number of queries, and \(B\) be the number of bits processed per number.

- **Time Complexity:** \(O(n\log n + q\log q + (n+q)B)\), for sorting, trie insertions, and queries.
- **Space Complexity:** \(O(nB + q)\), for the trie, sorted queries, and answer array.

## Key Takeaways

- A binary trie efficiently maximizes XOR.
- Offline query processing handles the upper bound `m`.
- Sorting queries by `m` allows each eligible number to be inserted only once.
- Preserve original query indices to return answers in the required order.
- Return `-1` when no array element satisfies the query's limit.

