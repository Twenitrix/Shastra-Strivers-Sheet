# Editorial: Longest Word with All Prefixes

## Problem Overview

Given an array of strings `words`, find the longest word such that every prefix of that word is also present in the array.

If multiple words have the same maximum length, return the lexicographically smallest one. If no valid word exists, return an empty string.

**Example:**

Input:
```text
words = ["n", "ni", "nin", "ninj", "ninja", "ninga"]
```

Output:
```text
"ninja"
```

Explanation: Every prefix of `"ninja"` — `"n"`, `"ni"`, `"nin"`, `"ninj"`, and `"ninja"` — exists in the array.

## Approach: Sorting and HashSet

The main idea is to check whether every prefix of a word exists.

First, sort the words lexicographically. If two words have the same length, lexicographical order helps ensure that the smaller word is considered first.

Store all words in a `HashSet` for efficient lookup.

For each word, check every prefix by gradually increasing its length from `1` to the full word length.

If all prefixes exist, the word is valid. Update the answer if it is longer than the current answer. Because the words are sorted lexicographically, keeping the first word among equal-length candidates gives the required tie-breaking behavior.

## Algorithm

1. Sort the array lexicographically.
2. Store all words in a `HashSet`.
3. Initialize the answer as an empty string.
4. Traverse each word.
5. Check whether every prefix of that word exists in the set.
6. If all prefixes exist and the word is longer than the current answer, update the answer.
7. Return the answer.

## Java Implementation

```java id="a7d3pn"
import java.util.*;

class Solution {
    public String completeString(int n, String[] a) {
        Arrays.sort(a);

        HashSet<String> set = new HashSet<>();

        for (int i = 0; i < n; i++) {
            set.add(a[i]);
        }

        String ans = "";

        for (int i = 0; i < n; i++) {
            String prefix = "";
            boolean valid = true;

            for (int j = 0; j < a[i].length(); j++) {
                prefix += a[i].charAt(j);

                if (!set.contains(prefix)) {
                    valid = false;
                    break;
                }
            }

            if (valid && a[i].length() > ans.length()) {
                ans = a[i];
            }
        }

        return ans.isEmpty() ? "None" : ans;
    }
}
```

## Dry Run

Input:

```text id="k3pxmf"
words = ["a", "ap", "app", "appl", "apple"]
```

Check `"apple"`:

- `"a"` exists.
- `"ap"` exists.
- `"app"` exists.
- `"appl"` exists.
- `"apple"` exists.

All prefixes are present, so `"apple"` is valid and becomes the answer.

Output:

```text id="p7f5va"
apple
```

## Complexity Analysis

Let \(n\) be the number of words and \(L\) be the maximum word length.

- **Time Complexity:** Sorting takes \(O(n\log n)\) comparisons, and checking prefixes takes up to \(O(nL^2)\) character work with the string concatenation used here.
- **Space Complexity:** \(O(nL)\), for storing all words in the hash set, excluding sorting overhead.

## Key Takeaways

- Every prefix of a valid word must exist in the array.
- A `HashSet` makes prefix lookups efficient.
- Sorting lexicographically helps handle equal-length answers.
- The longest valid word is returned; if none exists, return `"None"` for the Striver/GFG version of this problem.

