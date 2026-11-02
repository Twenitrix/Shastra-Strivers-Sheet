import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: O(n log n) patience-sorting LIS with reconstruction.
    // Stores predecessor links to reconstruct one LIS.
    private static int[] solve(int n, int[] a) {
        int[] tails = new int[n];       // tails[l] = index into a[] of smallest tail of LIS of length l+1
        int[] tailsLen = new int[n];    // length of LIS ending at a[tails[l]]
        int[] prev = new int[n];        // predecessor index for reconstruction
        int len = 0;

        for (int i = 0; i < n; i++) {
            int x = a[i];
            int lo = 0, hi = len;
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (a[tails[mid]] < x) lo = mid + 1;
                else hi = mid;
            }
            prev[i] = (lo > 0) ? tails[lo - 1] : -1;
            tails[lo] = i;
            tailsLen[i] = lo + 1;
            if (lo == len) len++;
        }

        // reconstruct
        int[] res = new int[len];
        int cur = tails[len - 1];
        for (int i = len - 1; i >= 0; i--) {
            res[i] = a[cur];
            cur = prev[cur];
        }
        return res;
    }

    private static void writeTestCase(int index, int n, int[] a) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir  = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        File inFile  = new File(inputDir,  String.format("input%02d.txt",  index));
        File outFile = new File(outputDir, String.format("output%02d.txt", index));

        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(n);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(a[i]);
                if (i < n - 1) sb.append(' ');
            }
            pw.println(sb.toString());
        }

        int[] ans = solve(n, a);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ans.length; i++) {
                sb.append(ans[i]);
                if (i < ans.length - 1) sb.append(' ');
            }
            pw.println(sb.toString());
        }
    }

    private static int[] randomArray(int n, int lo, int hi, Random rng) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = lo + rng.nextInt(hi - lo + 1);
        return a;
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        // TC 00: example 1
        writeTestCase(tc++, 8, new int[]{10, 9, 2, 5, 3, 7, 101, 18});

        // TC 01: example 2
        writeTestCase(tc++, 6, new int[]{1, 2, 2, 3, 4, 5});

        // TC 02: n = 1
        writeTestCase(tc++, 1, new int[]{42});

        // TC 03: n = 2, increasing
        writeTestCase(tc++, 2, new int[]{1, 2});

        // TC 04: n = 2, decreasing
        writeTestCase(tc++, 2, new int[]{2, 1});

        // TC 05: n = 2, equal
        writeTestCase(tc++, 2, new int[]{5, 5});

        // TC 06: strictly increasing
        writeTestCase(tc++, 5, new int[]{1, 2, 3, 4, 5});

        // TC 07: strictly decreasing (LIS = any single element)
        writeTestCase(tc++, 5, new int[]{5, 4, 3, 2, 1});

        // TC 08: all equal (LIS = 1 element)
        writeTestCase(tc++, 5, new int[]{7, 7, 7, 7, 7});

        // TC 09: negatives, increasing
        writeTestCase(tc++, 5, new int[]{-1000000000, -100, -1, 0, 1000000000});

        // TC 10: negatives, decreasing
        writeTestCase(tc++, 5, new int[]{1000000000, 0, -1, -100, -1000000000});

        // TC 11: alternating high/low
        writeTestCase(tc++, 8, new int[]{1, 10, 2, 9, 3, 8, 4, 7});

        // TC 12: two interleaved increasing sequences
        writeTestCase(tc++, 8, new int[]{1, 5, 2, 6, 3, 7, 4, 8});

        // TC 13: plateau groups
        writeTestCase(tc++, 9, new int[]{1, 1, 1, 2, 2, 2, 3, 3, 3});

        // TC 14: mountain shape
        writeTestCase(tc++, 7, new int[]{1, 2, 3, 4, 3, 2, 1});

        // TC 15: valley shape
        writeTestCase(tc++, 7, new int[]{4, 3, 2, 1, 2, 3, 4});

        // TC 16: sorted with one disruption
        writeTestCase(tc++, 6, new int[]{1, 2, 6, 3, 4, 5});

        // TC 17: LIS at end
        writeTestCase(tc++, 5, new int[]{5, 4, 3, 1, 2});

        // TC 18: LIS at start
        writeTestCase(tc++, 5, new int[]{1, 2, 5, 4, 3});

        // TC 19: extremes at ends
        writeTestCase(tc++, 4, new int[]{-1000000000, 1000000000, -1000000000, 1000000000});

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 1 + rng.nextInt(10);
            int[] a = randomArray(n, -20, 20, rng);
            writeTestCase(tc++, n, a);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int n = 1000 + rng.nextInt(9001);       // 1000..10000
            int[] a = randomArray(n, -1000000000, 1000000000, rng);
            writeTestCase(tc++, n, a);
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 1e5, strictly increasing
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i + 1;
            writeTestCase(tc++, n, a);
        }

        // TC 81: n = 1e5, strictly decreasing
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = n - i;
            writeTestCase(tc++, n, a);
        }

        // TC 82: n = 1e5, all equal
        {
            int n = 100000;
            int[] a = new int[n];
            Arrays.fill(a, 42);
            writeTestCase(tc++, n, a);
        }

        // TC 83: n = 1e5, sawtooth small period
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i % 50;
            writeTestCase(tc++, n, a);
        }

        // TC 84: n = 1e5, zigzag extremes
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? -1000000000 : 1000000000;
            writeTestCase(tc++, n, a);
        }

        // TC 85: n = 1e5, mountain
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i < n / 2) ? i : (n - i);
            writeTestCase(tc++, n, a);
        }

        // TC 86: n = 1e5, valley
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i < n / 2) ? (n / 2 - i) : (i - n / 2);
            writeTestCase(tc++, n, a);
        }

        // TC 87 - 91: n = 1e5, fully random
        for (int k = 0; k < 5; k++) {
            int n = 100000;
            int[] a = randomArray(n, -1000000000, 1000000000, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 92: n = 1e5, small range [-50, 50]
        {
            int n = 100000;
            int[] a = randomArray(n, -50, 50, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 93: n = 1e5, shuffled permutation 0..99999
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i;
            for (int i = n - 1; i > 0; i--) {
                int j = rng.nextInt(i + 1);
                int t = a[i]; a[i] = a[j]; a[j] = t;
            }
            writeTestCase(tc++, n, a);
        }

        // TC 94: n = 99999 (odd), random
        {
            int n = 99999;
            int[] a = randomArray(n, -1000000000, 1000000000, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 95: n = 1e5, mostly increasing with 100 drops
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i;
            for (int k = 0; k < 100; k++) {
                int idx = rng.nextInt(n);
                a[idx] = rng.nextInt(2000000001) - 1000000000;
            }
            writeTestCase(tc++, n, a);
        }

        // TC 96: n = 1e5, LIS ≈ 2 pattern
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? (i / 2) : (n - i / 2);
            writeTestCase(tc++, n, a);
        }

        // TC 97: n = 1e5, all distinct decreasing with steps
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = 500000000 - i * 5000;
            writeTestCase(tc++, n, a);
        }

        // TC 98: n = 1e5, values cycling 0..999
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i * 37) % 1000;
            writeTestCase(tc++, n, a);
        }

        // TC 99: n = 1e5, two halves: first low random, second high random
        {
            int n = 100000;
            int[] a = new int[n];
            int half = n / 2;
            for (int i = 0; i < half; i++) a[i] = rng.nextInt(1000);
            for (int i = half; i < n; i++) a[i] = 1000 + rng.nextInt(1000);
            writeTestCase(tc++, n, a);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Print Longest Increasing Subsequence.");
    }
}