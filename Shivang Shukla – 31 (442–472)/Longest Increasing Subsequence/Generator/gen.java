import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: O(n log n) patience-sorting LIS (strictly increasing).
    // Uses lower_bound (first element >= x) so equal values don't extend the LIS.
    private static int solve(int n, int[] a) {
        int[] tails = new int[n];
        int len = 0;
        for (int x : a) {
            int lo = 0, hi = len;
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (tails[mid] < x) lo = mid + 1;
                else hi = mid;
            }
            tails[lo] = x;
            if (lo == len) len++;
        }
        return len;
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

        int ans = solve(n, a);
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
        writeTestCase(tc++, 8, new int[]{10, 9, 2, 5, 3, 7, 101, 18});

        // TC 01: example 2 (all equal)
        writeTestCase(tc++, 4, new int[]{7, 7, 7, 7});

        // TC 02: n = 1
        writeTestCase(tc++, 1, new int[]{42});

        // TC 03: n = 2, increasing
        writeTestCase(tc++, 2, new int[]{1, 2});

        // TC 04: n = 2, decreasing
        writeTestCase(tc++, 2, new int[]{2, 1});

        // TC 05: n = 2, equal
        writeTestCase(tc++, 2, new int[]{5, 5});

        // TC 06: strictly increasing 1..10
        writeTestCase(tc++, 10, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        // TC 07: strictly decreasing 10..1
        writeTestCase(tc++, 10, new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1});

        // TC 08: all equal 0s
        writeTestCase(tc++, 6, new int[]{0, 0, 0, 0, 0, 0});

        // TC 09: all negatives, increasing
        writeTestCase(tc++, 5, new int[]{-10, -8, -5, -2, -1});

        // TC 10: all negatives, decreasing
        writeTestCase(tc++, 5, new int[]{-1, -2, -5, -8, -10});

        // TC 11: value extremes at ends
        writeTestCase(tc++, 4, new int[]{-10000, 10000, -10000, 10000});

        // TC 12: alternating high-low
        writeTestCase(tc++, 8, new int[]{1, 10, 2, 9, 3, 8, 4, 7});

        // TC 13: LIS hidden in decreasing shell
        writeTestCase(tc++, 7, new int[]{5, 1, 4, 2, 3, 0, 6});

        // TC 14: plateau groups
        writeTestCase(tc++, 9, new int[]{1, 1, 1, 2, 2, 2, 3, 3, 3});

        // TC 15: mountain shape (LIS = left half + peak)
        writeTestCase(tc++, 7, new int[]{1, 2, 3, 4, 3, 2, 1});

        // TC 16: valley shape (LIS picks right half)
        writeTestCase(tc++, 7, new int[]{4, 3, 2, 1, 2, 3, 4});

        // TC 17: two interleaved increasing sequences
        writeTestCase(tc++, 8, new int[]{1, 5, 2, 6, 3, 7, 4, 8});

        // TC 18: sorted with one disruption
        writeTestCase(tc++, 6, new int[]{1, 2, 6, 3, 4, 5});

        // TC 19: single spike (max in middle)
        writeTestCase(tc++, 5, new int[]{1, 2, 10000, -1, 0});

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 1 + rng.nextInt(10);                // 1..10
            int[] a = randomArray(n, -20, 20, rng);
            writeTestCase(tc++, n, a);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int n = 100 + rng.nextInt(401);             // 100..500
            int[] a = randomArray(n, -10000, 10000, rng);
            writeTestCase(tc++, n, a);
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 2500, strictly increasing 1..2500 (clipped to 10000 max)
        {
            int n = 2500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i + 1;
            writeTestCase(tc++, n, a);
        }

        // TC 81: n = 2500, strictly decreasing
        {
            int n = 2500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = n - i;
            writeTestCase(tc++, n, a);
        }

        // TC 82: n = 2500, all equal
        {
            int n = 2500;
            int[] a = new int[n];
            Arrays.fill(a, 0);
            writeTestCase(tc++, n, a);
        }

        // TC 83: n = 2500, sawtooth (many resets)
        {
            int n = 2500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i % 50;
            writeTestCase(tc++, n, a);
        }

        // TC 84: n = 2500, zigzag high-low alternating
        {
            int n = 2500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? -10000 : 10000;
            writeTestCase(tc++, n, a);
        }

        // TC 85: n = 2500, increasing then decreasing (mountain)
        {
            int n = 2500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i < n / 2) ? i : (n - i);
            writeTestCase(tc++, n, a);
        }

        // TC 86: n = 2500, decreasing then increasing (valley)
        {
            int n = 2500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i < n / 2) ? (n / 2 - i) : (i - n / 2);
            writeTestCase(tc++, n, a);
        }

        // TC 87 - 91: n = 2500, fully random in [-10000, 10000]
        for (int k = 0; k < 5; k++) {
            int n = 2500;
            int[] a = randomArray(n, -10000, 10000, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 92: n = 2500, small range [-50, 50] (many ties, LIS bounded)
        {
            int n = 2500;
            int[] a = randomArray(n, -50, 50, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 93: n = 2500, all distinct increasing but shuffled
        {
            int n = 2500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i - 1250; // -1250..1249
            // shuffle
            for (int i = n - 1; i > 0; i--) {
                int j = rng.nextInt(i + 1);
                int t = a[i]; a[i] = a[j]; a[j] = t;
            }
            writeTestCase(tc++, n, a);
        }

        // TC 94: n = 2500, "sorted then reverse" — increasing half, decreasing half
        {
            int n = 2500;
            int[] a = new int[n];
            int half = n / 2;
            for (int i = 0; i < half; i++) a[i] = i;
            for (int i = half; i < n; i++) a[i] = n - i;
            writeTestCase(tc++, n, a);
        }

        // TC 95: n = 2499 (odd), random full range
        {
            int n = 2499;
            int[] a = randomArray(n, -10000, 10000, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 96: n = 2500, LIS = 2 (pattern: big, small, big, small, ...)
        {
            int n = 2500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? (i / 2) : (n - i / 2);
            writeTestCase(tc++, n, a);
        }

        // TC 97: n = 2500, LIS = 1 (strictly decreasing with random noise? use pure dec)
        {
            int n = 2500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = 10000 - i * 8; // all distinct decreasing
            writeTestCase(tc++, n, a);
        }

        // TC 98: n = 2500, mostly increasing with 20 random drops
        {
            int n = 2500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i;
            for (int k = 0; k < 20; k++) {
                int idx = rng.nextInt(n);
                a[idx] = rng.nextInt(20001) - 10000;
            }
            writeTestCase(tc++, n, a);
        }

        // TC 99: n = 2500, values cycling in small window [-10000, -9990]
        {
            int n = 2500;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = -10000 + rng.nextInt(11);
            writeTestCase(tc++, n, a);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Longest Increasing Subsequence.");
    }
}