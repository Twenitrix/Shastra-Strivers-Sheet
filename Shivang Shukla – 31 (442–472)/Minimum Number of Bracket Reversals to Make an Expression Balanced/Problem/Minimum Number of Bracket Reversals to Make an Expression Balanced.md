# Minimum Number of Bracket Reversals to Make an Expression Balanced

**Difficulty:** Medium  
**Tags:** String, Stack, Greedy

## Problem Statement

You are given a string \(S\) consisting only of opening brackets `{` and closing brackets `}`.

A string is considered balanced if every opening bracket has a corresponding closing bracket and the brackets are correctly matched.

In one operation, you can reverse any single bracket:

- Change `{` to `}`.
- Change `}` to `{`.

Your task is to find the minimum number of reversals required to make the given string balanced.

If it is impossible to make the string balanced, return \(-1\).

## Input Format

- The first line contains a string \(S\) consisting only of `{` and `}`.

## Output Format

Print a single integer representing the minimum number of bracket reversals required to balance the string. Print \(-1\) if balancing is impossible.

## Constraints

- \(1 \le |S| \le 10^5\)
- \(S_i \in \{\texttt{\{},\texttt{\}}\}\), for all \(0 \le i < |S|\).
- The string contains only opening and closing curly brackets.
- Each operation reverses exactly one bracket.
- The final string must have correctly matched brackets.
- A balanced string must contain an even number of brackets.

## Example 1

**Input**
```text id="c5v8qa"
}}{{ 
```

**Output**
```text id="r2m6nd"
2
```

**Explanation**

The string contains two opening brackets and two closing brackets, but their order is unbalanced.

Reverse the first two closing brackets to obtain `{{}}`.

The resulting string is balanced, requiring \(2\) reversals.

## Example 2

**Input**
```text id="k7p3wb"
}}}
```

**Output**
```text id="m4x9rd"
-1
```

**Explanation**

The string has length \(3\), which is odd. Reversing brackets does not change the length, and a balanced bracket string must have an even length.

Therefore, it is impossible to balance the string, and the answer is \(-1\).