import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: O((N+Q) log) via offline sort + persistent-ish trie rebuild.
    // Simpler: for each query, brute-force over eligible prefix (small tests).
    // For large tests, use an offline approach: sort queries by m, add nums as they become eligible,
    // and query a bitwise trie for max xor.
    private static long[] solve(int n, int q, int[] nums, int[][] queries) {
        // Build offline approach
        int[][] idx = new int[q][3];   // [m, x, originalIndex]
        for (int i = 0; i < q; i++) {
            idx[i][0] = queries[i][1];   // m
            idx[i][1] = queries[i][0];   // x
            idx[i][2] = i;
        }
        Arrays.sort(idx, Comparator.comparingInt(a -> a[0]));

        int[] sortedNums = nums.clone();
        Arrays.sort(sortedNums);

        long[] ans = new long[q];
        Arrays.fill(ans, -1);

        int ptr = 0;
        Trie trie = new Trie();
        int added = 0;

        for (int qi = 0; qi < q; qi++) {
            int m = idx[qi][0];
            int x = idx[qi][1];
            int orig = idx[qi][2];

            while (ptr < n && sortedNums[ptr] <= m) {
                trie.insert(sortedNums[ptr]);
                ptr++;
                added++;
            }
            if (added == 0) {
                ans[orig] = -1;
            } else {
                ans[orig] = trie.maxXor(x);
            }
        }
        return ans;
    }

    // Simple binary trie over 30 bits (values up to 1e9 < 2^30).
    static class Trie {
        static class Node {
            Node[] child = new Node[2];
        }
        Node root = new Node();

        void insert(int v) {
            Node cur = root;
            for (int b = 30; b >= 0; b--) {
                int bit = (v >>> b) & 1;
                if (cur.child[bit] == null) cur.child[bit] = new Node();
                cur = cur.child[bit];
            }
        }

        int maxXor(int x) {
            Node cur = root;
            int res = 0;
            for (int b = 30; b >= 0; b--) {
                int bit = (x >>> b) & 1;
                int want = bit ^ 1;
                if (cur.child[want] != null) {
                    res |= (1 << b);
                    cur = cur.child[want];
                } else {
                    cur = cur.child[bit];
                }
            }
            return res;
        }
    }

    private static void writeTestCase(int index, int n, int q, int[] nums, int[][] queries) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir  = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        File inFile  = new File(inputDir,  String.format("input%02d.txt",  index));
        File outFile = new File(outputDir, String.format("output%02d.txt", index));

        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(n + " " + q);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(nums[i]);
                if (i < n - 1) sb.append(' ');
            }
            pw.println(sb.toString());
            for (int i = 0; i < q; i++) {
                pw.println(queries[i][0] + " " + queries[i][1]);
            }
        }

        long[] ans = solve(n, q, nums, queries);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < q; i++) {
                sb.append(ans[i]);
                if (i < q - 1) sb.append(' ');
            }
            pw.println(sb.toString());
        }
    }

    private static int randIntInRange(Random rng, int lo, int hi) {
        long range = (long) hi - lo + 1;
        return (int) (lo + (long) (rng.nextDouble() * range));
    }

    private static int[] randomArray(int n, int lo, int hi, Random rng) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = randIntInRange(rng, lo, hi);
        return a;
    }

    private static int[][] randomQueries(int q, int xHi, int mHi, Random rng) {
        int[][] qs = new int[q][2];
        for (int i = 0; i < q; i++) {
            qs[i][0] = randIntInRange(rng, 0, xHi);
            qs[i][1] = randIntInRange(rng, 0, mHi);
        }
        return qs;
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        // TC 00: example 1
        writeTestCase(tc++, 6, 3,
            new int[]{0, 1, 2, 3, 4, 7},
            new int[][]{{1, 3}, {5, 6}, {3, 7}});

        // TC 01: example 2
        writeTestCase(tc++, 3, 2,
            new int[]{5, 8, 12},
            new int[][]{{2, 4}, {10, 6}});

        // TC 02: N=1, Q=1, single element, query m >= element
        writeTestCase(tc++, 1, 1,
            new int[]{5},
            new int[][]{{3, 10}});

        // TC 03: N=1, Q=1, m < element -> -1
        writeTestCase(tc++, 1, 1,
            new int[]{5},
            new int[][]{{3, 4}});

        // TC 04: N=1, element=0, x=0
        writeTestCase(tc++, 1, 1,
            new int[]{0},
            new int[][]{{0, 0}});

        // TC 05: N=1, element=max, x=max
        writeTestCase(tc++, 1, 1,
            new int[]{1000000000},
            new int[][]{{1000000000, 1000000000}});

        // TC 06: all queries ineligible
        writeTestCase(tc++, 3, 3,
            new int[]{5, 8, 12},
            new int[][]{{0, 1}, {1, 2}, {2, 3}});

        // TC 07: all queries eligible
        writeTestCase(tc++, 3, 3,
            new int[]{0, 5, 10},
            new int[][]{{1, 100}, {7, 100}, {100, 100}});

        // TC 08: single array element, many queries
        writeTestCase(tc++, 1, 5,
            new int[]{7},
            new int[][]{{0, 7}, {1, 7}, {7, 7}, {10, 10}, {10, 6}});

        // TC 09: all zeros in array
        writeTestCase(tc++, 4, 3,
            new int[]{0, 0, 0, 0},
            new int[][]{{0, 0}, {5, 0}, {5, 10}});

        // TC 10: powers of two array
        writeTestCase(tc++, 5, 5,
            new int[]{1, 2, 4, 8, 16},
            new int[][]{{0, 1}, {0, 2}, {0, 4}, {0, 8}, {0, 16}});

        // TC 11: consecutive 0..7, various m
        writeTestCase(tc++, 8, 4,
            new int[]{0, 1, 2, 3, 4, 5, 6, 7},
            new int[][]{{0, 0}, {0, 3}, {0, 7}, {7, 7}});

        // TC 12: single element, m exactly equal
        writeTestCase(tc++, 1, 1,
            new int[]{42},
            new int[][]{{10, 42}});

        // TC 13: single element, m = element - 1 (ineligible)
        writeTestCase(tc++, 1, 1,
            new int[]{42},
            new int[][]{{10, 41}});

        // TC 14: x = 0, various m
        writeTestCase(tc++, 4, 3,
            new int[]{1, 5, 10, 20},
            new int[][]{{0, 0}, {0, 5}, {0, 20}});

        // TC 15: m = 0, x = 0
        writeTestCase(tc++, 2, 1,
            new int[]{0, 5},
            new int[][]{{0, 0}});

        // TC 16: all elements equal
        writeTestCase(tc++, 5, 3,
            new int[]{7, 7, 7, 7, 7},
            new int[][]{{0, 7}, {5, 7}, {10, 7}});

        // TC 17: array = [0, 1e9], queries in middle
        writeTestCase(tc++, 2, 3,
            new int[]{0, 1000000000},
            new int[][]{{500000000, 1000000000}, {0, 500000000}, {1000000000, 1000000000}});

        // TC 18: query with m = max, x = max
        writeTestCase(tc++, 3, 1,
            new int[]{0, 1, 2},
            new int[][]{{1000000000, 1000000000}});

        // TC 19: alternating eligible/ineligible
        writeTestCase(tc++, 3, 4,
            new int[]{1, 5, 10},
            new int[][]{{0, 0}, {0, 5}, {0, 9}, {0, 10}});

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 1 + rng.nextInt(10);
            int q = 1 + rng.nextInt(10);
            int[] a = randomArray(n, 0, 100, rng);
            int[][] qs = randomQueries(q, 100, 100, rng);
            writeTestCase(tc++, n, q, a, qs);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int n = 1000 + rng.nextInt(9001);        // 1000..10000
            int q = 1000 + rng.nextInt(9001);
            int[] a = randomArray(n, 0, 1000000000, rng);
            int[][] qs = randomQueries(q, 1000000000, 1000000000, rng);
            writeTestCase(tc++, n, q, a, qs);
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: N = 1e5, Q = 1e5, all zeros array, various queries
        {
            int n = 100000, q = 100000;
            int[] a = new int[n];
            int[][] qs = randomQueries(q, 1000000000, 1000000000, rng);
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 81: N = 1e5, Q = 1e5, array = 0..N-1, queries random
        {
            int n = 100000, q = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i;
            int[][] qs = randomQueries(q, 1000000000, 1000000000, rng);
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 82: N = 1e5, Q = 1e5, all ineligible queries (m=0, array all positive)
        {
            int n = 100000, q = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = 1000000000;
            int[][] qs = new int[q][2];
            for (int i = 0; i < q; i++) {
                qs[i][0] = randIntInRange(rng, 0, 1000000000);
                qs[i][1] = 0;   // m = 0, all elements are 1e9 > 0 -> ineligible
            }
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 83: N = 1e5, Q = 1e5, all eligible (m = 1e9)
        {
            int n = 100000, q = 100000;
            int[] a = randomArray(n, 0, 1000000000, rng);
            int[][] qs = new int[q][2];
            for (int i = 0; i < q; i++) {
                qs[i][0] = randIntInRange(rng, 0, 1000000000);
                qs[i][1] = 1000000000;
            }
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 84: N = 1e5, Q = 1e5, powers of 2 array
        {
            int n = 100000, q = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = 1 << (i % 30);
            int[][] qs = randomQueries(q, 1000000000, 1000000000, rng);
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 85: N = 1e5, Q = 1e5, array = increasing steps
        {
            int n = 100000, q = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (int) ((long) i * 10000);
            int[][] qs = randomQueries(q, 1000000000, 1000000000, rng);
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 86 - 90: N = 1e5, Q = 1e5, all random full range
        for (int k = 0; k < 5; k++) {
            int n = 100000, q = 100000;
            int[] a = randomArray(n, 0, 1000000000, rng);
            int[][] qs = randomQueries(q, 1000000000, 1000000000, rng);
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 91: N = 1e5, Q = 1e5, both small values in [0, 1000]
        {
            int n = 100000, q = 100000;
            int[] a = randomArray(n, 0, 1000, rng);
            int[][] qs = randomQueries(q, 1000, 1000, rng);
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 92: N = 1e5, Q = 1e5, array of two values {0, 1e9}
        {
            int n = 100000, q = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? 0 : 1000000000;
            int[][] qs = randomQueries(q, 1000000000, 1000000000, rng);
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 93: N = 99999, Q = 99999 (odd sizes)
        {
            int n = 99999, q = 99999;
            int[] a = randomArray(n, 0, 1000000000, rng);
            int[][] qs = randomQueries(q, 1000000000, 1000000000, rng);
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 94: N = 1e5, Q = 1e5, queries with m tied closely to array values
        {
            int n = 100000, q = 100000;
            int[] a = randomArray(n, 0, 1000000000, rng);
            int[][] qs = new int[q][2];
            for (int i = 0; i < q; i++) {
                qs[i][0] = randIntInRange(rng, 0, 1000000000);
                qs[i][1] = a[rng.nextInt(n)];   // m equals some array element
            }
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 95: N = 1e5, Q = 1e5, all m = 0 (edge)
        {
            int n = 100000, q = 100000;
            int[] a = randomArray(n, 0, 1000000000, rng);
            a[0] = 0;   // ensure at least one eligible element
            int[][] qs = new int[q][2];
            for (int i = 0; i < q; i++) {
                qs[i][0] = randIntInRange(rng, 0, 1000000000);
                qs[i][1] = 0;
            }
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 96: N = 1e5, Q = 1e5, all m = 1e9
        {
            int n = 100000, q = 100000;
            int[] a = randomArray(n, 0, 1000000000, rng);
            int[][] qs = randomQueries(q, 1000000000, 1000000000, rng);
            for (int i = 0; i < q; i++) qs[i][1] = 1000000000;
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 97: N = 1e5, Q = 1e5, all x = 0
        {
            int n = 100000, q = 100000;
            int[] a = randomArray(n, 0, 1000000000, rng);
            int[][] qs = randomQueries(q, 0, 1000000000, rng);
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 98: N = 1e5, Q = 1e5, all x = 1e9
        {
            int n = 100000, q = 100000;
            int[] a = randomArray(n, 0, 1000000000, rng);
            int[][] qs = randomQueries(q, 1000000000, 1000000000, rng);
            for (int i = 0; i < q; i++) qs[i][0] = 1000000000;
            writeTestCase(tc++, n, q, a, qs);
        }

        // TC 99: N = 1e5, Q = 1e5, array sorted ascending (already)
        {
            int n = 100000, q = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i;
            int[][] qs = randomQueries(q, 1000000000, 1000000000, rng);
            writeTestCase(tc++, n, q, a, qs);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Maximum XOR with an Element from an Array.");
    }
}