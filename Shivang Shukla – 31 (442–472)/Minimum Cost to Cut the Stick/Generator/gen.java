import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: classic interval DP (LeetCode 1547 style).
    // Sort cuts, add sentinels 0 and N, then dp[i][j] = min cost to make all
    // cuts strictly between position i and position j (in the sorted array).
    private static long solve(int n, int[] cuts) {
        int c = cuts.length;
        int[] arr = new int[c + 2];
        arr[0] = 0;
        arr[c + 1] = n;
        for (int i = 0; i < c; i++) arr[i + 1] = cuts[i];
        Arrays.sort(arr);

        int m = arr.length;                  // m = c + 2
        long[][] dp = new long[m][m];
        // dp[i][j] = min cost to cut all positions strictly between i and j
        // For gap = 1 (adjacent indices), no cuts in between -> cost 0.
        for (int gap = 2; gap < m; gap++) {
            for (int i = 0; i + gap < m; i++) {
                int j = i + gap;
                dp[i][j] = Long.MAX_VALUE;
                long segLen = arr[j] - arr[i];
                for (int k = i + 1; k < j; k++) {
                    long cost = dp[i][k] + dp[k][j] + segLen;
                    if (cost < dp[i][j]) dp[i][j] = cost;
                }
            }
        }
        return dp[0][m - 1];
    }

    private static void writeTestCase(int index, int n, int c, int[] cuts) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir  = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        File inFile  = new File(inputDir,  String.format("input%02d.txt",  index));
        File outFile = new File(outputDir, String.format("output%02d.txt", index));

        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(n + " " + c);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < c; i++) {
                sb.append(cuts[i]);
                if (i < c - 1) sb.append(' ');
            }
            pw.println(sb.toString());
        }

        long ans = solve(n, cuts);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    // Generate c distinct cuts in [1, n-1].
    private static int[] distinctCuts(int n, int c, Random rng) {
        if (c > n - 1) c = n - 1;
        Set<Integer> s = new LinkedHashSet<>();
        int guard = 0;
        while (s.size() < c && guard < 1000000) {
            guard++;
            s.add(1 + rng.nextInt(n - 1));
        }
        int[] arr = new int[s.size()];
        int idx = 0;
        for (int x : s) arr[idx++] = x;
        return arr;
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        // TC 00: example 1
        writeTestCase(tc++, 7, 4, new int[]{1, 3, 4, 5});

        // TC 01: example 2
        writeTestCase(tc++, 9, 2, new int[]{5, 6});

        // TC 02: C = 1, single cut in middle
        writeTestCase(tc++, 10, 1, new int[]{5});

        // TC 03: C = 1, single cut near start
        writeTestCase(tc++, 10, 1, new int[]{1});

        // TC 04: C = 1, single cut near end
        writeTestCase(tc++, 10, 1, new int[]{9});

        // TC 05: N = 2, C = 1, cut at 1
        writeTestCase(tc++, 2, 1, new int[]{1});

        // TC 06: all consecutive cuts in the middle
        writeTestCase(tc++, 10, 5, new int[]{4, 5, 6, 7, 8});

        // TC 07: cuts at even positions
        writeTestCase(tc++, 10, 4, new int[]{2, 4, 6, 8});

        // TC 08: cuts at odd positions
        writeTestCase(tc++, 10, 5, new int[]{1, 3, 5, 7, 9});

        // TC 09: two cuts near one end
        writeTestCase(tc++, 100, 2, new int[]{1, 2});

        // TC 10: two cuts near other end
        writeTestCase(tc++, 100, 2, new int[]{98, 99});

        // TC 11: many small cuts in first half
        writeTestCase(tc++, 20, 9, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});

        // TC 12: cuts spread evenly
        writeTestCase(tc++, 100, 9, new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90});

        // TC 13: cuts all near middle
        writeTestCase(tc++, 100, 5, new int[]{48, 49, 50, 51, 52});

        // TC 14: one cut far from others
        writeTestCase(tc++, 100, 3, new int[]{1, 50, 99});

        // TC 15: cuts at extreme ends (1 and N-1)
        writeTestCase(tc++, 100, 2, new int[]{1, 99});

        // TC 16: N = 3, cuts = {1, 2}
        writeTestCase(tc++, 3, 2, new int[]{1, 2});

        // TC 17: N = 4, cuts = {1, 2, 3}
        writeTestCase(tc++, 4, 3, new int[]{1, 2, 3});

        // TC 18: C = 1, huge N
        writeTestCase(tc++, 1000000, 1, new int[]{500000});

        // TC 19: two cuts, symmetric around center
        writeTestCase(tc++, 100, 2, new int[]{25, 75});

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 2 + rng.nextInt(19);              // 2..20
            int c = 1 + rng.nextInt(Math.min(5, n - 1));  // 1..min(5, n-1)
            int[] cuts = distinctCuts(n, c, rng);
            writeTestCase(tc++, n, cuts.length, cuts);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int n = 1000 + rng.nextInt(9001);         // 1000..10000
            int c = 10 + rng.nextInt(41);             // 10..50
            if (c > n - 1) c = n - 1;
            int[] cuts = distinctCuts(n, c, rng);
            writeTestCase(tc++, n, cuts.length, cuts);
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: N = 1e6, C = 100, cuts at positions 1, 2, ..., 100 (clustered at start)
        {
            int n = 1000000;
            int c = 100;
            int[] cuts = new int[c];
            for (int i = 0; i < c; i++) cuts[i] = i + 1;
            writeTestCase(tc++, n, c, cuts);
        }

        // TC 81: N = 1e6, C = 100, cuts at positions N-100, ..., N-1 (clustered at end)
        {
            int n = 1000000;
            int c = 100;
            int[] cuts = new int[c];
            for (int i = 0; i < c; i++) cuts[i] = n - 100 + i;
            writeTestCase(tc++, n, c, cuts);
        }

        // TC 82: N = 1e6, C = 100, evenly spaced
        {
            int n = 1000000;
            int c = 100;
            int[] cuts = new int[c];
            for (int i = 0; i < c; i++) cuts[i] = (int) ((long) (i + 1) * n / (c + 1));
            writeTestCase(tc++, n, c, cuts);
        }

        // TC 83: N = 1e6, C = 100, all near middle (clustered)
        {
            int n = 1000000;
            int c = 100;
            int[] cuts = new int[c];
            int start = n / 2 - c / 2;
            for (int i = 0; i < c; i++) cuts[i] = start + i;
            writeTestCase(tc++, n, c, cuts);
        }

        // TC 84: N = 1e6, C = 100, halving pattern (binary split friendly)
        {
            int n = 1000000;
            int c = 100;
            int[] cuts = new int[c];
            // place cuts at fractions 1/2, 1/4, 3/4, 1/8, 3/8, 5/8, 7/8, ...
            int idx = 0;
            for (int level = 1; level < 8 && idx < c; level++) {
                int step = n >> level;
                for (int j = 1; j <= (1 << (level - 1)) && idx < c; j++) {
                    int pos = j * 2 * step - step;
                    if (pos > 0 && pos < n) cuts[idx++] = pos;
                }
            }
            // fill remaining with small distinct positions
            int fill = 1;
            while (idx < c) {
                boolean present = false;
                for (int q = 0; q < idx; q++) if (cuts[q] == fill) { present = true; break; }
                if (!present) cuts[idx++] = fill;
                fill++;
            }
            writeTestCase(tc++, n, c, cuts);
        }

        // TC 85: N = 1e6, C = 100, cuts at primes < 500
        {
            int n = 1000000;
            List<Integer> primes = new ArrayList<>();
            for (int v = 2; v < 1000 && primes.size() < 100; v++) {
                boolean isPrime = true;
                for (int d = 2; d * d <= v; d++) if (v % d == 0) { isPrime = false; break; }
                if (isPrime) primes.add(v);
            }
            int[] cuts = new int[primes.size()];
            for (int i = 0; i < primes.size(); i++) cuts[i] = primes.get(i);
            writeTestCase(tc++, n, cuts.length, cuts);
        }

        // TC 86 - 90: N = 1e6, C = 100, fully random
        for (int k = 0; k < 5; k++) {
            int n = 1000000;
            int c = 100;
            int[] cuts = distinctCuts(n, c, rng);
            writeTestCase(tc++, n, cuts.length, cuts);
        }

        // TC 91: N = 1e6, C = 100, cuts random in [1, 1000] (small positions)
        {
            int n = 1000000;
            int c = 100;
            int[] cuts = distinctCuts(1001, c, rng);   // positions in [1, 1000]
            writeTestCase(tc++, n, cuts.length, cuts);
        }

        // TC 92: N = 1e6, C = 100, cuts random in [N-1000, N-1] (large positions)
        {
            int n = 1000000;
            int c = 100;
            int[] cuts = new int[c];
            Set<Integer> s = new LinkedHashSet<>();
            while (s.size() < c) s.add(n - 1000 + rng.nextInt(1000));
            int i = 0;
            for (int x : s) cuts[i++] = x;
            writeTestCase(tc++, n, c, cuts);
        }

        // TC 93: N = 999999 (odd), C = 100, random
        {
            int n = 999999;
            int c = 100;
            int[] cuts = distinctCuts(n, c, rng);
            writeTestCase(tc++, n, cuts.length, cuts);
        }

        // TC 94: N = 1e6, C = 99 (odd count), random
        {
            int n = 1000000;
            int c = 99;
            int[] cuts = distinctCuts(n, c, rng);
            writeTestCase(tc++, n, cuts.length, cuts);
        }

        // TC 95: N = 1e6, C = 100, cuts symmetric around center
        {
            int n = 1000000;
            int c = 100;
            int[] cuts = new int[c];
            int half = c / 2;
            for (int i = 0; i < half; i++) {
                cuts[i] = 1 + i * 10;
                cuts[half + i] = n - 1 - i * 10;
            }
            writeTestCase(tc++, n, c, cuts);
        }

        // TC 96: N = 1e6, C = 100, cuts at multiples of 5000
        {
            int n = 1000000;
            int c = 100;
            int[] cuts = new int[c];
            for (int i = 0; i < c; i++) cuts[i] = (i + 1) * 5000;
            writeTestCase(tc++, n, c, cuts);
        }

        // TC 97: N = 1e6, C = 100, cuts at exponentially increasing positions
        {
            int n = 1000000;
            int c = 100;
            int[] cuts = new int[c];
            Set<Integer> s = new LinkedHashSet<>();
            long v = 1;
            while (s.size() < c && v < n) {
                s.add((int) v);
                v = v * 2 + 1;
            }
            int fill = 2;
            while (s.size() < c) {
                if (fill < n && !s.contains(fill)) s.add(fill);
                fill++;
            }
            int i = 0;
            for (int x : s) cuts[i++] = x;
            writeTestCase(tc++, n, c, cuts);
        }

        // TC 98: N = 1e6, C = 100, cuts in narrow band around quarter
        {
            int n = 1000000;
            int c = 100;
            int[] cuts = new int[c];
            int start = n / 4 - c / 2;
            for (int i = 0; i < c; i++) cuts[i] = start + i;
            writeTestCase(tc++, n, c, cuts);
        }

        // TC 99: N = 1e6, C = 100, cuts at ends and middle scattered
        {
            int n = 1000000;
            int c = 100;
            int[] cuts = new int[c];
            Set<Integer> s = new LinkedHashSet<>();
            s.add(1); s.add(n - 1); s.add(n / 2);
            s.add(n / 4); s.add(3 * n / 4);
            s.add(n / 8); s.add(7 * n / 8);
            while (s.size() < c) {
                s.add(1 + rng.nextInt(n - 1));
            }
            int i = 0;
            for (int x : s) cuts[i++] = x;
            writeTestCase(tc++, n, c, cuts);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Minimum Cost to Cut the Stick.");
    }
}