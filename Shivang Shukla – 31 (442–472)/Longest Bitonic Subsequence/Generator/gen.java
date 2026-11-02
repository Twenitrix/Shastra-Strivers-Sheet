import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: LIS from left + LDS from right, combine at each peak.
    // lis[i] = length of strictly increasing subseq ending at i (left to right)
    // lds[i] = length of strictly decreasing subseq starting at i (right to left)
    // bitonic ending/peaking at i = lis[i] + lds[i] - 1
    private static int solve(int n, int[] a) {
        int[] lis = new int[n];
        int[] lds = new int[n];

        for (int i = 0; i < n; i++) {
            lis[i] = 1;
            for (int j = 0; j < i; j++) {
                if (a[j] < a[i] && lis[j] + 1 > lis[i]) lis[i] = lis[j] + 1;
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            lds[i] = 1;
            for (int j = n - 1; j > i; j--) {
                if (a[j] < a[i] && lds[j] + 1 > lds[i]) lds[i] = lds[j] + 1;
            }
        }

        int best = 0;
        for (int i = 0; i < n; i++) {
            int cand = lis[i] + lds[i] - 1;
            if (cand > best) best = cand;
        }
        return best;
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
        writeTestCase(tc++, 8, new int[]{1, 11, 2, 10, 4, 5, 2, 1});

        // TC 01: example 2 (strictly increasing)
        writeTestCase(tc++, 5, new int[]{1, 2, 3, 4, 5});

        // TC 02: n = 1
        writeTestCase(tc++, 1, new int[]{42});

        // TC 03: n = 2, increasing
        writeTestCase(tc++, 2, new int[]{1, 2});

        // TC 04: n = 2, decreasing
        writeTestCase(tc++, 2, new int[]{2, 1});

        // TC 05: n = 2, equal
        writeTestCase(tc++, 2, new int[]{5, 5});

        // TC 06: strictly decreasing
        writeTestCase(tc++, 5, new int[]{5, 4, 3, 2, 1});

        // TC 07: all equal (no strict inc/dec, answer = 1)
        writeTestCase(tc++, 5, new int[]{7, 7, 7, 7, 7});

        // TC 08: mountain shape (classic bitonic)
        writeTestCase(tc++, 5, new int[]{1, 3, 5, 4, 2});

        // TC 09: valley shape (answer from either side)
        writeTestCase(tc++, 5, new int[]{5, 3, 1, 2, 4});

        // TC 10: increasing then decreasing with plateau at peak
        writeTestCase(tc++, 6, new int[]{1, 2, 5, 5, 4, 3});

        // TC 11: negative values, increasing
        writeTestCase(tc++, 4, new int[]{-5, -3, -1, 0});

        // TC 12: negative values, decreasing
        writeTestCase(tc++, 4, new int[]{0, -1, -3, -5});

        // TC 13: all negative mountain
        writeTestCase(tc++, 5, new int[]{-5, -3, -1, -2, -4});

        // TC 14: mix pos/neg
        writeTestCase(tc++, 6, new int[]{-2, 0, 3, 1, -1, -3});

        // TC 15: extremes of value range
        writeTestCase(tc++, 6, new int[]{-10000, -5000, 10000, 5000, 0, -10000});

        // TC 16: two peaks — best bitonic uses one
        writeTestCase(tc++, 7, new int[]{1, 5, 2, 8, 3, 2, 1});

        // TC 17: increasing with occasional equal (breaks strictness)
        writeTestCase(tc++, 6, new int[]{1, 2, 2, 3, 4, 5});

        // TC 18: decreasing with occasional equal
        writeTestCase(tc++, 6, new int[]{9, 8, 8, 7, 6, 5});

        // TC 19: alternating high-low (many local peaks)
        writeTestCase(tc++, 8, new int[]{1, 100, 2, 99, 3, 98, 4, 97});

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 1 + rng.nextInt(10);                // 1..10
            int[] a = randomArray(n, -20, 20, rng);
            writeTestCase(tc++, n, a);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int n = 20 + rng.nextInt(81);               // 20..100
            int[] a = randomArray(n, -10000, 10000, rng);
            writeTestCase(tc++, n, a);
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 1000, strictly increasing 1..1000
        {
            int n = 1000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i + 1;
            writeTestCase(tc++, n, a);
        }

        // TC 81: n = 1000, strictly decreasing
        {
            int n = 1000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = n - i;
            writeTestCase(tc++, n, a);
        }

        // TC 82: n = 1000, all equal
        {
            int n = 1000;
            int[] a = new int[n];
            Arrays.fill(a, 42);
            writeTestCase(tc++, n, a);
        }

        // TC 83: n = 1000, perfect mountain 1..500,499..1
        {
            int n = 1000;
            int[] a = new int[n];
            int half = n / 2;
            for (int i = 0; i < half; i++) a[i] = i + 1;
            for (int i = half; i < n; i++) a[i] = n - i;
            writeTestCase(tc++, n, a);
        }

        // TC 84: n = 1000, perfect valley 500..1,2..500
        {
            int n = 1000;
            int[] a = new int[n];
            int half = n / 2;
            for (int i = 0; i < half; i++) a[i] = half - i;
            for (int i = half; i < n; i++) a[i] = i - half + 1;
            writeTestCase(tc++, n, a);
        }

        // TC 85: n = 1000, alternating high/low extremes
        {
            int n = 1000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? -10000 : 10000;
            writeTestCase(tc++, n, a);
        }

        // TC 86 - 90: n = 1000, fully random [-10000, 10000]
        for (int k = 0; k < 5; k++) {
            int n = 1000;
            int[] a = randomArray(n, -10000, 10000, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 91: n = 1000, random [-100, 100] (small range → many ties)
        {
            int n = 1000;
            int[] a = randomArray(n, -100, 100, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 92: n = 1000, sawtooth up/down
        {
            int n = 1000;
            int[] a = new int[n];
            int period = 20;
            for (int i = 0; i < n; i++) {
                int r = i % period;
                a[i] = (r < period / 2) ? (r * 500) : ((period - r) * 500);
            }
            writeTestCase(tc++, n, a);
        }

        // TC 93: n = 1000, increasing then flat then decreasing
        {
            int n = 1000;
            int[] a = new int[n];
            int t1 = n / 3, t2 = 2 * n / 3;
            for (int i = 0; i < t1; i++) a[i] = i + 1;
            for (int i = t1; i < t2; i++) a[i] = t1;
            for (int i = t2; i < n; i++) a[i] = n - i;
            writeTestCase(tc++, n, a);
        }

        // TC 94: n = 1000, two big mountains joined
        {
            int n = 1000;
            int[] a = new int[n];
            int half = n / 2;
            for (int i = 0; i < half; i++) a[i] = (i < half / 2) ? (i + 1) : (half - i);
            for (int i = half; i < n; i++) {
                int r = i - half;
                a[i] = (r < half / 2) ? (r + 1) : (half - r);
            }
            writeTestCase(tc++, n, a);
        }

        // TC 95: n = 999 (odd), random [-10000, 10000]
        {
            int n = 999;
            int[] a = randomArray(n, -10000, 10000, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 96: n = 1000, mostly increasing with a few dips
        {
            int n = 1000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i + 1;
            for (int k = 0; k < 30; k++) {
                int idx = rng.nextInt(n);
                a[idx] = rng.nextInt(10001) - 5000;
            }
            writeTestCase(tc++, n, a);
        }

        // TC 97: n = 1000, mostly decreasing with a few bumps
        {
            int n = 1000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = n - i;
            for (int k = 0; k < 30; k++) {
                int idx = rng.nextInt(n);
                a[idx] = rng.nextInt(10001) - 5000;
            }
            writeTestCase(tc++, n, a);
        }

        // TC 98: n = 1000, single peak at very end (max at last)
        {
            int n = 1000;
            int[] a = new int[n];
            for (int i = 0; i < n - 1; i++) a[i] = i + 1;
            a[n - 1] = 10000;
            writeTestCase(tc++, n, a);
        }

        // TC 99: n = 1000, peak at very start (max at index 0, then decreasing)
        {
            int n = 1000;
            int[] a = new int[n];
            a[0] = 10000;
            for (int i = 1; i < n; i++) a[i] = n - i;
            writeTestCase(tc++, n, a);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Longest Bitonic Subsequence.");
    }
}