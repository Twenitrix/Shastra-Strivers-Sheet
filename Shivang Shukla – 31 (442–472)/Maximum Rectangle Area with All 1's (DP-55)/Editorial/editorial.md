# Editorial: Maximum Rectangle Area with All 1's (DP-55)

## Problem Overview

Given a binary matrix containing only `0`s and `1`s, find the area of the largest rectangle containing only `1`s.

The rectangle must consist entirely of cells containing `1`.

**Example:**

Input:
```text
matrix = [
  [1, 0, 1, 0, 0],
  [1, 0, 1, 1, 1],
  [1, 1, 1, 1, 1],
  [1, 0, 0, 1, 0]
]
```

Output:
```text
6
```

Explanation: The largest rectangle containing only `1`s has an area of `6`.

## Approach: Histogram and Monotonic Stack

The main idea is to convert each matrix row into a histogram and find the largest rectangle in that histogram.

Maintain an array `height` where `height[j]` represents the number of consecutive `1`s ending at the current row in column `j`.

For each row:

- If the cell contains `1`, increment its height.
- If the cell contains `0`, reset its height to `0`.
- Calculate the largest rectangle area in the resulting histogram.

To find the largest histogram rectangle, use a monotonic increasing stack of indices. When a shorter bar is encountered, pop taller bars and calculate the maximum rectangle that can end at the current position.

Repeat for every row and keep the maximum area.

## Algorithm

1. Initialize a `height` array with all values set to `0`.
2. Traverse each row of the matrix.
3. Update the histogram heights using the current row.
4. Use a monotonic stack to calculate the largest rectangle area in the histogram.
5. Update the overall maximum area.
6. Return the maximum area after processing all rows.

## Java Implementation

```java
import java.util.*;

class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] height = new int[cols];
        int maxArea = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    height[j]++;
                } else {
                    height[j] = 0;
                }
            }

            maxArea = Math.max(maxArea, largestRectangleArea(height));
        }

        return maxArea;
    }

    private int largestRectangleArea(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int n = height.length;

        for (int i = 0; i <= n; i++) {
            int currentHeight = i == n ? 0 : height[i];

            while (!stack.isEmpty()
                    && height[stack.peek()] > currentHeight) {
                int h = height[stack.pop()];
                int width;

                if (stack.isEmpty()) {
                    width = i;
                } else {
                    width = i - stack.peek() - 1;
                }

                maxArea = Math.max(maxArea, h * width);
            }

            stack.push(i);
        }

        return maxArea;
    }
}
```

## Dry Run

Consider the matrix:

```text
1 1 1
1 1 1
```

After processing the first row:

```text
height = [1, 1, 1]
```

The largest rectangle area is `3`.

After processing the second row:

```text
height = [2, 2, 2]
```

The largest rectangle area is:

\[
2 \times 3 = 6
\]

Output:

```text
6
```

## Complexity Analysis

Let \(m\) be the number of rows and \(n\) be the number of columns.

- **Time Complexity:** \(O(mn)\), because each row updates the histogram in \(O(n)\) time, and the stack processes each histogram index in amortized \(O(n)\) time.
- **Space Complexity:** \(O(n)\), for the histogram heights and monotonic stack.

## Key Takeaways

- Convert each row into a histogram of consecutive ones.
- Use a monotonic stack to find the largest rectangle in each histogram.
- Every rectangle in the matrix can be considered through its bottom row.
- The overall maximum across all histograms gives the answer.

