import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: classic O(n^3) bottom-up interval DP.
    // p[0..n-1] are dimensions; matrices are p[0]×p[1], ..., p[n-2]×p[n-1].
    private static long solve(int n, int[] p) {
        int m = n - 1;                       // number of matrices
        if (m <= 1) return 0;                // single matrix -> no cost
        long[][] dp = new long[m][m];
        // dp[i][j] = min cost to multiply matrices i..j (0-indexed, inclusive)
        // dp[i][i] = 0 (single matrix, no cost)
        for (int len = 2; len <= m; len++) {
            for (int i = 0; i + len - 1 < m; i++) {
                int j = i + len - 1;
                dp[i][j] = Long.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    long cost = dp[i][k] + dp[k + 1][j]
                              + (long) p[i] * p[k + 1] * p[j + 1];
                    if (cost < dp[i][j]) dp[i][j] = cost;
                }
            }
        }
        return dp[0][m - 1];
    }

    private static void writeTestCase(int index, int n, int[] p) throws Exception {
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
                sb.append(p[i]);
                if (i < n - 1) sb.append(' ');
            }
            pw.println(sb.toString());
        }

        long ans = solve(n, p);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    private static int[] randomDims(int n, int lo, int hi, Random rng) {
        int[] p = new int[n];
        for (int i = 0; i < n; i++) p[i] = lo + rng.nextInt(hi - lo + 1);
        return p;
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        // TC 00: example 1
        writeTestCase(tc++, 5, new int[]{40, 20, 30, 10, 30});

        // TC 01: example 2
        writeTestCase(tc++, 3, new int[]{10, 20, 30});

        // TC 02: N=2 (single matrix, no cost)
        writeTestCase(tc++, 2, new int[]{1, 1});

        // TC 03: N=2, max dims
        writeTestCase(tc++, 2, new int[]{500, 500});

        // TC 04: N=2, mixed extreme
        writeTestCase(tc++, 2, new int[]{1, 500});

        // TC 05: all dimensions 1 (min cost 0 for N-1 multiplications)
        writeTestCase(tc++, 6, new int[]{1, 1, 1, 1, 1, 1});

        // TC 06: all dimensions 500 (max cost scenario)
        writeTestCase(tc++, 6, new int[]{500, 500, 500, 500, 500, 500});

        // TC 07: N=4, classic 10 20 30 40
        writeTestCase(tc++, 4, new int[]{10, 20, 30, 40});

        // TC 08: alternating extremes 1 500 1 500 1
        writeTestCase(tc++, 5, new int[]{1, 500, 1, 500, 1});

        // TC 09: 10 1 10 1 10
        writeTestCase(tc++, 5, new int[]{10, 1, 10, 1, 10});

        // TC 10: increasing dims 1..5
        writeTestCase(tc++, 5, new int[]{1, 2, 3, 4, 5});

        // TC 11: decreasing dims 5..1
        writeTestCase(tc++, 5, new int[]{5, 4, 3, 2, 1});

        // TC 12: all equal 10s
        writeTestCase(tc++, 5, new int[]{10, 10, 10, 10, 10});

        // TC 13: one huge middle
        writeTestCase(tc++, 4, new int[]{1, 500, 1, 1});

        // TC 14: huge ends
        writeTestCase(tc++, 4, new int[]{500, 1, 1, 500});

        // TC 15: N=5 with unequal
        writeTestCase(tc++, 5, new int[]{40, 20, 30, 10, 30});

        // TC 16: prime dims
        writeTestCase(tc++, 5, new int[]{2, 3, 5, 7, 11});

        // TC 17: powers of two
        writeTestCase(tc++, 5, new int[]{2, 4, 8, 16, 32});

        // TC 18: N=5 large
        writeTestCase(tc++, 5, new int[]{100, 200, 300, 400, 500});

        // TC 19: N=6 mixed
        writeTestCase(tc++, 6, new int[]{5, 10, 3, 12, 5, 50});

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 2 + rng.nextInt(9);                   // 2..10
            int[] p = randomDims(n, 1, 50, rng);
            writeTestCase(tc++, n, p);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int n = 20 + rng.nextInt(41);                 // 20..60
            int[] p = randomDims(n, 1, 500, rng);
            writeTestCase(tc++, n, p);
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 100, all 500 (max cost, tests long overflow)
        {
            int n = 100;
            int[] p = new int[n];
            Arrays.fill(p, 500);
            writeTestCase(tc++, n, p);
        }

        // TC 81: n = 100, all 1 (min cost)
        {
            int n = 100;
            int[] p = new int[n];
            Arrays.fill(p, 1);
            writeTestCase(tc++, n, p);
        }

        // TC 82: n = 100, increasing 1..100
        {
            int n = 100;
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = Math.min(500, i + 1);
            writeTestCase(tc++, n, p);
        }

        // TC 83: n = 100, decreasing 500..401
        {
            int n = 100;
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = 500 - i;
            writeTestCase(tc++, n, p);
        }

        // TC 84: n = 100, alternating 1 and 500
        {
            int n = 100;
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = (i % 2 == 0) ? 1 : 500;
            writeTestCase(tc++, n, p);
        }

        // TC 85: n = 100, single large in middle, rest 1
        {
            int n = 100;
            int[] p = new int[n];
            Arrays.fill(p, 1);
            p[n / 2] = 500;
            writeTestCase(tc++, n, p);
        }

        // TC 86: n = 100, two extremes at ends, rest 1
        {
            int n = 100;
            int[] p = new int[n];
            Arrays.fill(p, 1);
            p[0] = 500;
            p[n - 1] = 500;
            writeTestCase(tc++, n, p);
        }

        // TC 87 - 91: n = 100, fully random [1, 500]
        for (int k = 0; k < 5; k++) {
            int n = 100;
            int[] p = randomDims(n, 1, 500, rng);
            writeTestCase(tc++, n, p);
        }

        // TC 92: n = 100, small dims [1, 10]
        {
            int n = 100;
            int[] p = randomDims(n, 1, 10, rng);
            writeTestCase(tc++, n, p);
        }

        // TC 93: n = 100, large dims [490, 500]
        {
            int n = 100;
            int[] p = randomDims(n, 490, 500, rng);
            writeTestCase(tc++, n, p);
        }

        // TC 94: n = 99, random full range
        {
            int n = 99;
            int[] p = randomDims(n, 1, 500, rng);
            writeTestCase(tc++, n, p);
        }

        // TC 95: n = 100, sawtooth 1..500..1 cycles of length 10
        {
            int n = 100;
            int[] p = new int[n];
            int period = 10;
            for (int i = 0; i < n; i++) {
                int r = i % period;
                p[i] = (r < period / 2) ? (50 * r + 1) : (50 * (period - r) + 1);
                if (p[i] > 500) p[i] = 500;
                if (p[i] < 1) p[i] = 1;
            }
            writeTestCase(tc++, n, p);
        }

        // TC 96: n = 100, all 250
        {
            int n = 100;
            int[] p = new int[n];
            Arrays.fill(p, 250);
            writeTestCase(tc++, n, p);
        }

        // TC 97: n = 100, linear step 1..500
        {
            int n = 100;
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = 1 + (i * 499) / (n - 1);
            writeTestCase(tc++, n, p);
        }

        // TC 98: n = 100, mostly 1s with 500s every 7th
        {
            int n = 100;
            int[] p = new int[n];
            Arrays.fill(p, 1);
            for (int i = 0; i < n; i += 7) p[i] = 500;
            writeTestCase(tc++, n, p);
        }

        // TC 99: n = 100, mostly 500s with 1s every 7th
        {
            int n = 100;
            int[] p = new int[n];
            Arrays.fill(p, 500);
            for (int i = 0; i < n; i += 7) p[i] = 1;
            writeTestCase(tc++, n, p);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Matrix Chain Multiplication — Bottom-Up (DP-49).");
    }
}