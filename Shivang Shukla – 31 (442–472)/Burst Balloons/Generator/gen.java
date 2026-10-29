import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference O(n^3) interval DP solver for Burst Balloons.
    private static long solve(int n, int[] nums) {
        int m = n + 2;
        int[] a = new int[m];
        a[0] = 1;
        a[m - 1] = 1;
        for (int i = 0; i < n; i++) a[i + 1] = nums[i];

        long[][] dp = new long[m][m];
        for (int len = 1; len <= n; len++) {
            for (int left = 1; left + len - 1 <= n; left++) {
                int right = left + len - 1;
                for (int k = left; k <= right; k++) {
                    long coins = (long) a[left - 1] * a[k] * a[right + 1]
                               + dp[left][k - 1]
                               + dp[k + 1][right];
                    if (coins > dp[left][right]) dp[left][right] = coins;
                }
            }
        }
        return dp[1][n];
    }

    private static void writeTestCase(int index, int n, int[] nums) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir  = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        String inFileName  = String.format("input%02d.txt",  index);
        String outFileName = String.format("output%02d.txt", index);

        File inFile  = new File(inputDir,  inFileName);
        File outFile = new File(outputDir, outFileName);

        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(n);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(nums[i]);
                if (i < n - 1) sb.append(' ');
            }
            pw.println(sb.toString());
        }

        long ans = solve(n, nums);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        // TC 00: example 1
        writeTestCase(tc++, 4, new int[]{3, 1, 5, 8});

        // TC 01: example 2 (n = 1)
        writeTestCase(tc++, 1, new int[]{5});

        // TC 02: n = 1, zero balloon
        writeTestCase(tc++, 1, new int[]{0});

        // TC 03: n = 1, max element
        writeTestCase(tc++, 1, new int[]{100});

        // TC 04: n = 2, both positive
        writeTestCase(tc++, 2, new int[]{1, 2});

        // TC 05: n = 2, one zero -> optimal bursts zero first
        writeTestCase(tc++, 2, new int[]{0, 5});

        // TC 06: n = 3, classic
        writeTestCase(tc++, 3, new int[]{3, 1, 5});

        // TC 07: all zeros
        writeTestCase(tc++, 5, new int[]{0, 0, 0, 0, 0});

        // TC 08: all ones
        writeTestCase(tc++, 5, new int[]{1, 1, 1, 1, 1});

        // TC 09: all same value
        writeTestCase(tc++, 6, new int[]{2, 2, 2, 2, 2, 2});

        // TC 10: increasing sequence
        writeTestCase(tc++, 5, new int[]{1, 2, 3, 4, 5});

        // TC 11: decreasing sequence
        writeTestCase(tc++, 5, new int[]{5, 4, 3, 2, 1});

        // TC 12: all max value
        writeTestCase(tc++, 4, new int[]{100, 100, 100, 100});

        // TC 13: zero in the middle
        writeTestCase(tc++, 5, new int[]{3, 0, 5, 1, 8});

        // TC 14: alternating 0 and max
        writeTestCase(tc++, 6, new int[]{0, 100, 0, 100, 0, 100});

        // TC 15: single spike
        writeTestCase(tc++, 5, new int[]{1, 1, 100, 1, 1});

        // TC 16: valley shape
        writeTestCase(tc++, 5, new int[]{10, 5, 1, 5, 10});

        // TC 17: peak shape
        writeTestCase(tc++, 5, new int[]{1, 5, 10, 5, 1});

        // TC 18: two large at ends
        writeTestCase(tc++, 4, new int[]{100, 1, 1, 100});

        // TC 19: pattern that stresses interval choice
        writeTestCase(tc++, 6, new int[]{7, 9, 8, 0, 2, 4});

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 1 + rng.nextInt(7);                // 1..7
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rng.nextInt(11); // 0..10
            writeTestCase(tc++, n, a);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int n = 20 + rng.nextInt(81);              // 20..100
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rng.nextInt(101); // 0..100
            writeTestCase(tc++, n, a);
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 300, all ones
        {
            int n = 300;
            int[] a = new int[n];
            Arrays.fill(a, 1);
            writeTestCase(tc++, n, a);
        }

        // TC 81: n = 300, all max value
        {
            int n = 300;
            int[] a = new int[n];
            Arrays.fill(a, 100);
            writeTestCase(tc++, n, a);
        }

        // TC 82: n = 300, all zeros
        {
            int n = 300;
            int[] a = new int[n];
            writeTestCase(tc++, n, a);
        }

        // TC 83: n = 300, strictly increasing 1..300 clipped at 100
        {
            int n = 300;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = Math.min(100, i + 1);
            writeTestCase(tc++, n, a);
        }

        // TC 84: n = 300, strictly decreasing (clipped)
        {
            int n = 300;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = Math.max(0, 100 - (i / 3));
            writeTestCase(tc++, n, a);
        }

        // TC 85: n = 300, alternating 0 and 100
        {
            int n = 300;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? 0 : 100;
            writeTestCase(tc++, n, a);
        }

        // TC 86 - 90: n = 300, fully random [0,100]
        for (int k = 0; k < 5; k++) {
            int n = 300;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rng.nextInt(101);
            writeTestCase(tc++, n, a);
        }

        // TC 91: n = 300, all 1s except single 100 in the middle
        {
            int n = 300;
            int[] a = new int[n];
            Arrays.fill(a, 1);
            a[n / 2] = 100;
            writeTestCase(tc++, n, a);
        }

        // TC 92: n = 300, values in tight range around 50 (47..53)
        {
            int n = 300;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = 47 + rng.nextInt(7);
            writeTestCase(tc++, n, a);
        }

        // TC 93: n = 300, low amplitude random walk
        {
            int n = 300;
            int[] a = new int[n];
            int cur = 50;
            for (int i = 0; i < n; i++) {
                cur = Math.max(0, Math.min(100, cur + rng.nextInt(11) - 5));
                a[i] = cur;
            }
            writeTestCase(tc++, n, a);
        }

        // TC 94: n = 300, all 100 except single 0 in middle
        {
            int n = 300;
            int[] a = new int[n];
            Arrays.fill(a, 100);
            a[n / 2] = 0;
            writeTestCase(tc++, n, a);
        }

        // TC 95: n = 299 (odd), all same value
        {
            int n = 299;
            int[] a = new int[n];
            Arrays.fill(a, 77);
            writeTestCase(tc++, n, a);
        }

        // TC 96: n = 300, sawtooth 0..100..0
        {
            int n = 300;
            int[] a = new int[n];
            int period = 20;
            for (int i = 0; i < n; i++) {
                int r = i % period;
                a[i] = (r < period / 2) ? (r * 10) : ((period - r) * 10);
                if (a[i] > 100) a[i] = 100;
            }
            writeTestCase(tc++, n, a);
        }

        // TC 97: n = 300, half increasing half decreasing (mountain)
        {
            int n = 300;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = (i <= n / 2) ? Math.min(100, i) : Math.min(100, n - i);
            }
            writeTestCase(tc++, n, a);
        }

        // TC 98: n = 300, all pairs of large-small
        {
            int n = 300;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? 100 : 1;
            writeTestCase(tc++, n, a);
        }

        // TC 99: n = 300, random drawn from small pool of {0, 1, 50, 99, 100}
        {
            int n = 300;
            int[] pool = {0, 1, 50, 99, 100};
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = pool[rng.nextInt(pool.length)];
            writeTestCase(tc++, n, a);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Burst Balloons.");
    }
}