import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: O(n^2) DP.
    // lis[i] = length of LIS ending at i
    // cnt[i] = number of LIS of length lis[i] ending at i
    // Answer = sum of cnt[i] over all i with lis[i] == maxLen.
    private static long solve(int n, int[] a) {
        if (n == 0) return 0;
        int[] lis = new int[n];
        long[] cnt = new long[n];
        int maxLen = 1;

        for (int i = 0; i < n; i++) {
            lis[i] = 1;
            cnt[i] = 1;
            for (int j = 0; j < i; j++) {
                if (a[j] < a[i]) {
                    int cand = lis[j] + 1;
                    if (cand > lis[i]) {
                        lis[i] = cand;
                        cnt[i] = cnt[j];
                    } else if (cand == lis[i]) {
                        cnt[i] += cnt[j];
                    }
                }
            }
            if (lis[i] > maxLen) maxLen = lis[i];
        }

        long total = 0;
        for (int i = 0; i < n; i++) {
            if (lis[i] == maxLen) total += cnt[i];
        }
        return total;
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

        long ans = solve(n, a);
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
        writeTestCase(tc++, 5, new int[]{1, 3, 5, 4, 7});

        // TC 01: example 2 (all equal)
        writeTestCase(tc++, 5, new int[]{2, 2, 2, 2, 2});

        // TC 02: n = 1
        writeTestCase(tc++, 1, new int[]{42});

        // TC 03: n = 2, increasing
        writeTestCase(tc++, 2, new int[]{1, 2});

        // TC 04: n = 2, decreasing
        writeTestCase(tc++, 2, new int[]{2, 1});

        // TC 05: n = 2, equal
        writeTestCase(tc++, 2, new int[]{5, 5});

        // TC 06: strictly increasing 1..6 (single LIS)
        writeTestCase(tc++, 6, new int[]{1, 2, 3, 4, 5, 6});

        // TC 07: strictly decreasing (LIS = 1 at every position)
        writeTestCase(tc++, 6, new int[]{6, 5, 4, 3, 2, 1});

        // TC 08: all equal, n=7 (LIS = 1, count = 7)
        writeTestCase(tc++, 7, new int[]{0, 0, 0, 0, 0, 0, 0});

        // TC 09: alternating up-down
        writeTestCase(tc++, 6, new int[]{1, 3, 2, 4, 3, 5});

        // TC 10: classic known case
        writeTestCase(tc++, 8, new int[]{1, 3, 5, 4, 7, 6, 8, 9});

        // TC 11: two interleaved increasing sequences
        writeTestCase(tc++, 6, new int[]{1, 2, 5, 3, 4, 6});

        // TC 12: multiple duplicate LIS paths
        writeTestCase(tc++, 6, new int[]{1, 2, 3, 1, 2, 3});

        // TC 13: values with negatives
        writeTestCase(tc++, 5, new int[]{-3, -1, -2, 0, -1});

        // TC 14: plateau with peaks
        writeTestCase(tc++, 7, new int[]{1, 2, 2, 3, 3, 4, 4});

        // TC 15: LIS ends at all positions
        writeTestCase(tc++, 4, new int[]{1, 2, 3, 4});

        // TC 16: all distinct but not sorted
        writeTestCase(tc++, 5, new int[]{5, 1, 4, 2, 3});

        // TC 17: strictly increasing except one dup
        writeTestCase(tc++, 5, new int[]{1, 2, 2, 3, 4});

        // TC 18: two halves increasing
        writeTestCase(tc++, 6, new int[]{1, 3, 5, 2, 4, 6});

        // TC 19: values spanning range
        writeTestCase(tc++, 6, new int[]{-1000000, 0, 500000, -500000, 1000000, 250000});

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 1 + rng.nextInt(9);              // 1..9
            int[] a = randomArray(n, -10, 10, rng);
            writeTestCase(tc++, n, a);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int n = 100 + rng.nextInt(901);          // 100..1000
            int[] a = randomArray(n, -1000000, 1000000, rng);
            writeTestCase(tc++, n, a);
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 2000, strictly increasing 1..2000 (single LIS length 2000)
        {
            int n = 2000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i + 1;
            writeTestCase(tc++, n, a);
        }

        // TC 81: n = 2000, strictly decreasing
        {
            int n = 2000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = n - i;
            writeTestCase(tc++, n, a);
        }

        // TC 82: n = 2000, all equal (LIS = 1, count = 2000)
        {
            int n = 2000;
            int[] a = new int[n];
            Arrays.fill(a, 7);
            writeTestCase(tc++, n, a);
        }

        // TC 83: n = 2000, alternating high-low
        {
            int n = 2000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? (i + 1) : (n - i);
            writeTestCase(tc++, n, a);
        }

        // TC 84: n = 2000, "1,2,3,...,n/2,1,2,3,...,n/2" — many LIS paths
        {
            int n = 2000;
            int[] a = new int[n];
            int half = n / 2;
            for (int i = 0; i < half; i++) a[i] = i + 1;
            for (int i = 0; i < half; i++) a[half + i] = i + 1;
            writeTestCase(tc++, n, a);
        }

        // TC 85: n = 2000, two increasing halves with overlap
        {
            int n = 2000;
            int[] a = new int[n];
            int half = n / 2;
            for (int i = 0; i < half; i++) a[i] = i + 1;
            for (int i = 0; i < half; i++) a[half + i] = half + i + 1;
            writeTestCase(tc++, n, a);
        }

        // TC 86 - 90: n = 2000, fully random
        for (int k = 0; k < 5; k++) {
            int n = 2000;
            int[] a = randomArray(n, -1000000, 1000000, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 91: n = 2000, small range [-5, 5] (many ties)
        {
            int n = 2000;
            int[] a = randomArray(n, -5, 5, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 92: n = 2000, small range [0, 2] (many ties)
        {
            int n = 2000;
            int[] a = randomArray(n, 0, 2, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 93: n = 1999 (odd), random
        {
            int n = 1999;
            int[] a = randomArray(n, -1000000, 1000000, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 94: n = 2000, monotone increasing with one duplicate in middle
        {
            int n = 2000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i + 1;
            a[n / 2] = a[n / 2 - 1];
            writeTestCase(tc++, n, a);
        }

        // TC 95: n = 2000, alternating 1 and 2
        {
            int n = 2000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? 1 : 2;
            writeTestCase(tc++, n, a);
        }

        // TC 96: n = 2000, values cycling 1..100
        {
            int n = 2000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 100) + 1;
            writeTestCase(tc++, n, a);
        }

        // TC 97: n = 2000, mostly increasing with occasional random dips
        {
            int n = 2000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i + 1;
            for (int k = 0; k < 50; k++) {
                int idx = rng.nextInt(n);
                a[idx] = rng.nextInt(2001);
            }
            writeTestCase(tc++, n, a);
        }

        // TC 98: n = 2000, multiple plateaus at increasing values
        {
            int n = 2000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i / 10) + 1;
            writeTestCase(tc++, n, a);
        }

        // TC 99: n = 2000, values in very small range [0, 1] (all 0s and 1s)
        {
            int n = 2000;
            int[] a = randomArray(n, 0, 1, rng);
            writeTestCase(tc++, n, a);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Number of Longest Increasing Subsequences.");
    }
}