# Count and Say

**Difficulty:** Medium  
**Tags:** String, Recursion

## Problem Statement

The count-and-say sequence is a sequence of digit strings defined by the following rules:

- `countAndSay(1) = "1"`
- To generate `countAndSay(n)`, read the previous term and describe each group of consecutive identical digits by writing the number of digits followed by the digit itself.

Given a positive integer \(n\), return the \(n\)-th term of the count-and-say sequence.

## Input Format

A single integer \(n\), representing the position of the required term in the sequence.

## Output Format

Print the \(n\)-th term of the count-and-say sequence as a string.

## Constraints

- \(1 \le n \le 30\)
- The input \(n\) is an integer.
- The output must be generated from the previous term, beginning with `"1"`.

## Example 1

**Input**
```text
4
```

**Output**
```text
1211
```

**Explanation**

The first four terms are:

- Term 1: `"1"`
- Term 2: `"11"` — one `1`
- Term 3: `"21"` — two `1`s
- Term 4: `"1211"` — one `2`, followed by one `1`

Therefore, the fourth term is `"1211"`.

## Example 2

**Input**
```text
1
```

**Output**
```text
1
```

**Explanation**

The first term of the sequence is `"1"` by definition.