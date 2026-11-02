# Problem: Best Time to Buy and Sell Stock with Transaction Fee

**Difficulty:** Medium | **Tags:** Arrays, Dynamic Programming, Greedy, Stocks

---

## Problem Statement

You are given an integer array `prices`, where `prices[i]` represents the price of a stock on the \(i\)-th day.

You are also given an integer `fee`, representing the transaction fee charged for each completed transaction.

You can perform any number of transactions, but you must sell the stock before buying again. You may hold at most one share at a time.

Find the **maximum profit** you can achieve.

A transaction consists of buying one share and selling it later. The transaction fee is charged when selling the stock.

## Input Format

- The first line contains two integers `n` and `fee`, representing the number of days and the transaction fee.
- The second line contains `n` space-separated integers representing the stock prices for each day.

## Output Format

Print a single integer representing the maximum profit achievable.

## Constraints

- \(1 \le n \le 5 \times 10^4\)
- \(1 \le prices[i] \le 5 \times 10^4\)
- \(0 \le fee \le 5 \times 10^4\)

## Examples

### Example 1

**Input:**
```text
6 2
1 3 2 8 4 9
```

**Output:**
```text
8
```

**Explanation:**

- Buy on day 1 at price `1`.
- Sell on day 4 at price `8`, paying a fee of `2`. Profit = `8 - 1 - 2 = 5`.
- Buy on day 5 at price `4`.
- Sell on day 6 at price `9`, paying a fee of `2`. Profit = `9 - 4 - 2 = 3`.

Total profit = `5 + 3 = 8`.

### Example 2

**Input:**
```text
5 3
1 3 7 5 10
```

**Output:**
```text
6
```

**Explanation:**

- Buy at price `1` and sell at price `10`, paying a fee of `3`.
- Total profit = `10 - 1 - 3 = 6`.

### Example 3

**Input:**
```text
4 5
5 4 3 2
```

**Output:**
```text
0
```

**Explanation:**

Stock prices keep decreasing, so no profitable transaction is possible. The maximum profit is `0`.

