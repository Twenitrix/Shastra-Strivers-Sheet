# Editorial: Bit Prerequisites for Trie Problems

## 1. Problem Overview

Bit manipulation and binary tries are useful for solving problems involving XOR operations on integers.

Before implementing XOR-based trie problems, we need to understand the following concepts:

- Binary representation of integers.
- Bitwise XOR operation.
- Properties of XOR.
- Checking individual bits.
- Greedy bit selection.
- Binary trie structure.

These concepts help efficiently solve problems such as Maximum XOR of Two Numbers and Maximum XOR With an Element From Array.

## 2. Binary Representation

Every non-negative integer can be represented using binary digits, consisting only of `0` and `1`.

For example:

- Decimal `5` = Binary `101`
- Decimal `3` = Binary `011`
- Decimal `7` = Binary `111`

For integers within the range \(0\) to \(10^9\), we can use 31 bits, indexed from bit `30` down to bit `0`.

## 3. Bitwise XOR Operation

The XOR operator `^` compares two bits and returns `1` when they differ and `0` when they are equal.

| A | B | A ^ B |
|---|---|---|
| 0 | 0 | 0 |
| 0 | 1 | 1 |
| 1 | 0 | 1 |
| 1 | 1 | 0 |

For example:

```text
5 = 101
3 = 011
---------
5 ^ 3 = 110 = 6
```

Therefore, `5 ^ 3 = 6`.

## 4. Important XOR Properties

The following properties are frequently used in XOR problems:

1. `a ^ a = 0`
2. `a ^ 0 = a`
3. `a ^ b = b ^ a`
4. `(a ^ b) ^ c = a ^ (b ^ c)`
5. If `a ^ b = c`, then `a ^ c = b`.

These properties are useful for simplifying XOR expressions and understanding how bits contribute to the result.

## 5. Why Binary Tries Are Useful

A binary trie stores numbers bit by bit instead of storing each number as a complete value.

Each node can have two children:

- `0` child
- `1` child

For each number, we insert its bits from the most significant bit to the least significant bit.

For example, inserting `5` and `3` creates paths corresponding to:

```text
5 = 101
3 = 011
```

Shared prefixes use the same nodes, reducing repeated work when processing numbers with similar binary prefixes.

## 6. Greedy Strategy for Maximum XOR

Suppose we want to maximize `x ^ y`.

At every bit position, the best choice is generally to select the opposite bit of the corresponding bit in `x`, if that branch exists in the trie.

- If the current bit of `x` is `0`, prefer `1`.
- If the current bit of `x` is `1`, prefer `0`.
- If the preferred branch does not exist, follow the available branch.

This strategy prioritizes setting higher-order bits of the XOR result because they contribute more to its numeric value.

## 7. Java Implementation: XOR and Bit Extraction

```java
class Solution {
    public int xorExample(int a, int b) {
        return a ^ b;
    }

    public int getBit(int num, int position) {
        return (num >> position) & 1;
    }
}
```

The expression `(num >> position) & 1` extracts the bit at the specified position.

## 8. Complexity Analysis

### Bit Extraction

- Time Complexity: \(O(1)\)
- Auxiliary Space: \(O(1)\)

### Binary Trie

For integers represented using \(B\) bits:

- Inserting one number: \(O(B)\)
- Querying the maximum XOR: \(O(B)\)
- Space for \(N\) inserted numbers: \(O(NB)\) in the worst case.

For 31-bit integers, \(B = 31\), so insertion and querying take constant time with respect to the number of stored elements.

## 9. Key Takeaways

- XOR produces `1` when two corresponding bits differ.
- Binary tries store integers one bit at a time.
- Maximum XOR queries greedily prefer opposite bits, starting from the most significant bit.
- Understanding bit extraction and XOR properties is essential before implementing advanced trie problems.
