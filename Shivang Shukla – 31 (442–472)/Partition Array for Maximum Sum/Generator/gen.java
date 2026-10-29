import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: O(n*K) DP.
    // dp[i] = max score for partitioning A[0..i-1] (prefix of length i).
    // dp[0] = 0.
    // For each i in [1..n], try last block of length len in [1..min(K,i)]:
    //   maxVal = max(A[i-len .. i-1])
    //   dp[i] = max(dp[i], dp[i-len] + maxVal * len)
    private static long solve(int n, int k, int[] a) {
        long[] dp = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            long maxVal = 0;
            for (int len = 1; len <= k && len <= i; len++) {
                maxVal = Math.max(maxVal, a[i - len]);
                long cand = dp[i - len] + maxVal * len;
                if (cand > dp[i]) dp[i] = cand;
            }
        }
        return dp[n];
    }

    private static void writeTestCase(int index, int n, int k, int[] a) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir  = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        File inFile  = new File(inputDir,  String.format("input%02d.txt",  index));
        File outFile = new File(outputDir, String.format("output%02d.txt", index));

        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(n + " " + k);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(a[i]);
                if (i < n - 1) sb.append(' ');
            }
            pw.println(sb.toString());
        }

        long ans = solve(n, k, a);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
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
        writeTestCase(tc++, 7, 3, new int[]{1, 15, 7, 9, 2, 5, 10});

        // TC 01: example 2 (K=1)
        writeTestCase(tc++, 5, 1, new int[]{1, 2, 3, 4, 5});

        // TC 02: n=1, single element
        writeTestCase(tc++, 1, 1, new int[]{7});

        // TC 03: n=1, max element
        writeTestCase(tc++, 1, 1, new int[]{1000000000});

        // TC 04: n=2, K=1 (each separate)
        writeTestCase(tc++, 2, 1, new int[]{5, 10});

        // TC 05: n=2, K=2 (both together) — score = max*2
        writeTestCase(tc++, 2, 2, new int[]{5, 10});

        // TC 06: all zeros
        writeTestCase(tc++, 5, 3, new int[]{0, 0, 0, 0, 0});

        // TC 07: all equal (any partition same)
        writeTestCase(tc++, 5, 3, new int[]{7, 7, 7, 7, 7});

        // TC 08: K = n (whole array as one block)
        writeTestCase(tc++, 5, 5, new int[]{1, 2, 3, 4, 5});

        // TC 09: strictly increasing, K=2
        writeTestCase(tc++, 5, 2, new int[]{1, 2, 3, 4, 5});

        // TC 10: strictly decreasing, K=2
        writeTestCase(tc++, 5, 2, new int[]{5, 4, 3, 2, 1});

        // TC 11: max value repeated
        writeTestCase(tc++, 4, 3, new int[]{1000000000, 1000000000, 1000000000, 1000000000});

        // TC 12: single big value in middle
        writeTestCase(tc++, 5, 3, new int[]{1, 1, 100, 1, 1});

        // TC 13: descending then ascending
        writeTestCase(tc++, 6, 3, new int[]{5, 4, 3, 3, 4, 5});

        // TC 14: ascending then descending
        writeTestCase(tc++, 6, 3, new int[]{1, 2, 3, 3, 2, 1});

        // TC 15: two big values separated
        writeTestCase(tc++, 6, 2, new int[]{10, 1, 1, 10, 1, 1});

        // TC 16: all negative-like (but values >= 0, so use 0)
        writeTestCase(tc++, 5, 2, new int[]{0, 1, 0, 1, 0});

        // TC 17: alternating high/low
        writeTestCase(tc++, 6, 3, new int[]{1000000000, 0, 1000000000, 0, 1000000000, 0});

        // TC 18: K = n/2 with mixed
        writeTestCase(tc++, 6, 3, new int[]{3, 1, 4, 1, 5, 9});

        // TC 19: K = n, max value only
        writeTestCase(tc++, 5, 5, new int[]{9, 8, 7, 6, 5});

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 1 + rng.nextInt(9);               // 1..9
            int kk = 1 + rng.nextInt(n);              // 1..n
            int[] a = randomArray(n, 0, 100, rng);
            writeTestCase(tc++, n, kk, a);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int n = 100 + rng.nextInt(301);           // 100..400
            int kk = 1 + rng.nextInt(n);              // 1..n
            int[] a = randomArray(n, 0, 1000000, rng);
            writeTestCase(tc++, n, kk, a);
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n=500, K=500 (one block, whole array)
        {
            int n = 500;
            int[] a = randomArray(n, 0, 1000000000, rng);
            writeTestCase(tc++, n, n, a);
        }

        // TC 81: n=500, K=1 (no grouping)
        {
            int n = 500;
            int[] a = randomArray(n, 0, 1000000000, rng);
            writeTestCase(tc++, n, 1, a);
        }

        // TC 82: n=500, K=250 (half)
        {
            int n = 500;
            int[] a = randomArray(n, 0, 1000000000, rng);
            writeTestCase(tc++, n, 250, a);
        }

        // TC 83: n=500, K=2
        {
            int n = 500;
            int[] a = randomArray(n, 0, 1000000000, rng);
            writeTestCase(tc++, n, 2, a);
        }

        // TC 84: n=500, all max
        {
            int n = 500;
            int[] a = new int[n];
            Arrays.fill(a, 1000000000);
            writeTestCase(tc++, n, 100, a);
        }

        // TC 85: n=500, all zeros
        {
            int n = 500;
            int[] a = new int[n];
            writeTestCase(tc++, n, 100, a);
        }

        // TC 86 - 90: n=500, fully random with random K
        for (int k = 0; k < 5; k++) {
            int n = 500;
            int kk = 1 + rng.nextInt(n);
            int[] a = randomArray(n, 0, 1000000000, rng);
            writeTestCase(tc++, n, kk, a);
        }

        // TC 91: n=500, strictly increasing, K=10
        {
            int n = 500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i + 1;
            writeTestCase(tc++, n, 10, a);
        }

        // TC 92: n=500, strictly decreasing, K=10
        {
            int n = 500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = n - i;
            writeTestCase(tc++, n, 10, a);
        }

        // TC 93: n=500, alternating high/low with K=5
        {
            int n = 500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? 1000000000 : 0;
            writeTestCase(tc++, n, 5, a);
        }

        // TC 94: n=499 (odd), random, K=7
        {
            int n = 499;
            int[] a = randomArray(n, 0, 1000000, rng);
            writeTestCase(tc++, n, 7, a);
        }

        // TC 95: n=500, small values [0,10], K=5
        {
            int n = 500;
            int[] a = randomArray(n, 0, 10, rng);
            writeTestCase(tc++, n, 5, a);
        }

        // TC 96: n=500, one max in the middle, rest small
        {
            int n = 500;
            int[] a = new int[n];
            Arrays.fill(a, 1);
            a[n / 2] = 1000000000;
            writeTestCase(tc++, n, 50, a);
        }

        // TC 97: n=500, K=100, random
        {
            int n = 500;
            int[] a = randomArray(n, 0, 1000000000, rng);
            writeTestCase(tc++, n, 100, a);
        }

        // TC 98: n=500, values cycling 0..999
        {
            int n = 500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i * 37) % 1000;
            writeTestCase(tc++, n, 20, a);
        }

        // TC 99: n=500, K=499 (almost whole)
        {
            int n = 500;
            int[] a = randomArray(n, 0, 1000000000, rng);
            writeTestCase(tc++, n, 499, a);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Partition Array for Maximum Sum.");
    }
}