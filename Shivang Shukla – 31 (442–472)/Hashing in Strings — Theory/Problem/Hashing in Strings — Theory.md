# Hashing in Strings — Theory

**Difficulty:** Easy  
**Tags:** String, Hashing, Modular Arithmetic

## Problem Statement

String hashing is a technique used to convert a string into a numerical value called a hash. It is commonly used in string matching and substring comparison problems.

You are given a string \(S\) consisting of lowercase English letters. Calculate its polynomial hash using the following formula:

\[
H(S)=\left(\sum_{i=0}^{N-1} val(S_i)\times 31^i\right)\bmod (10^9+7)
\]

where:

- \(N\) is the length of the string.
- \(val(a)=1,\ val(b)=2,\ldots,\ val(z)=26\).
- \(31\) is the base used for hashing.
- \(10^9+7\) is the modulus.

The index \(i\) starts from \(0\). Print the resulting hash value.

## Input Format

- The first line contains a string \(S\) consisting of lowercase English letters.

## Output Format

Print a single integer representing the polynomial hash of \(S\).

## Constraints

- \(1 \le |S| \le 10^5\)
- \(S_i \in \{a,b,c,\ldots,z\}\), for all \(0 \le i < |S|\).
- The string contains only lowercase English letters.
- Base \(B=31\).
- Modulus \(M=10^9+7\).

## Example 1

**Input**
```text
abc
```

**Output**
```text
2946
```

**Explanation**

The character values are \(a=1\), \(b=2\), and \(c=3\).

\[
H(S)=(1\times31^0+2\times31^1+3\times31^2)\bmod(10^9+7)
\]

\[
=(1+62+2883)\bmod(10^9+7)=2946
\]

Therefore, the hash value is \(2946\).

## Example 2

**Input**
```text
aa
```

**Output**
```text
32
```

**Explanation**

Both characters have the value \(1\).

\[
H(S)=(1\times31^0+1\times31^1)\bmod(10^9+7)
\]

\[
=(1+31)\bmod(10^9+7)=32
\]

Therefore, the hash value is \(32\).

## Note

Different strings can sometimes produce the same hash value. This is called a **hash collision**. Choosing a suitable base and modulus helps reduce collisions but does not eliminate them completely.