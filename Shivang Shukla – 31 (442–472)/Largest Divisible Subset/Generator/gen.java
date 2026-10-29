import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: sort, then O(n^2) DP.
    private static int[] solve(int n, int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);

        int[] dp = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dp, 1);
        Arrays.fill(parent, -1);

        int bestLen = 1, bestEnd = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (a[i] % a[j] == 0 && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }
            if (dp[i] > bestLen) {
                bestLen = dp[i];
                bestEnd = i;
            }
        }

        int[] res = new int[bestLen];
        int idx = bestLen - 1;
        int cur = bestEnd;
        while (cur != -1) {
            res[idx--] = a[cur];
            cur = parent[cur];
        }
        return res;
    }

    private static boolean isValidSubset(int[] sub, int[] nums) {
        Set<Integer> pool = new HashSet<>();
        for (int x : nums) pool.add(x);
        for (int x : sub) if (!pool.contains(x)) return false;
        for (int i = 0; i < sub.length; i++) {
            for (int j = i + 1; j < sub.length; j++) {
                if (sub[i] % sub[j] != 0 && sub[j] % sub[i] != 0) return false;
            }
        }
        return true;
    }

    private static void writeTestCase(int index, int[] nums) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir  = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        File inFile  = new File(inputDir,  String.format("input%02d.txt",  index));
        File outFile = new File(outputDir, String.format("output%02d.txt", index));

        int n = nums.length;
        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(n);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(nums[i]);
                if (i < n - 1) sb.append(' ');
            }
            pw.println(sb.toString());
        }

        int[] ans = solve(n, nums);
        if (!isValidSubset(ans, nums)) {
            throw new IllegalStateException("Internal: reference produced invalid subset at test " + index);
        }

        try (PrintWriter pw = new PrintWriter(outFile)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ans.length; i++) {
                sb.append(ans[i]);
                if (i < ans.length - 1) sb.append(' ');
            }
            pw.println(sb.toString());
        }
    }

    private static int[] randomDistinct(int k, int maxVal, Random rng) {
        Set<Integer> s = new LinkedHashSet<>();
        while (s.size() < k) s.add(1 + rng.nextInt(maxVal));
        int[] arr = new int[k];
        int i = 0;
        for (int x : s) arr[i++] = x;
        return arr;
    }

    private static int[] chainArray(int k, int maxVal, Random rng) {
        LinkedHashSet<Integer> s = new LinkedHashSet<>();
        long cur = 1;
        s.add((int) cur);
        while (s.size() < k) {
            long mult = 2 + rng.nextInt(5);
            long nxt = cur * mult;
            if (nxt > maxVal) {
                nxt = cur * 2;
                if (nxt > maxVal) {
                    nxt = cur * (1 + rng.nextInt((int) Math.max(1, maxVal / cur)));
                    if (nxt > maxVal || nxt <= cur) break;
                }
            }
            s.add((int) nxt);
            cur = nxt;
        }
        int[] arr = new int[s.size()];
        int i = 0;
        for (int x : s) arr[i++] = x;
        return arr;
    }

    private static int[] firstKPrimes(int k) {
        int limit = 20000;
        boolean[] sieve = new boolean[limit + 1];
        Arrays.fill(sieve, true);
        sieve[0] = sieve[1] = false;
        for (int i = 2; (long) i * i <= limit; i++) {
            if (sieve[i]) for (int j = i * i; j <= limit; j += i) sieve[j] = false;
        }
        int[] res = new int[k];
        int idx = 0;
        for (int i = 2; i <= limit && idx < k; i++) if (sieve[i]) res[idx++] = i;
        return Arrays.copyOf(res, idx);
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        writeTestCase(tc++, new int[]{1, 2, 3, 8});                       // 00 example 1
        writeTestCase(tc++, new int[]{3, 5, 10});                        // 01 example 2
        writeTestCase(tc++, new int[]{7});                               // 02 n=1
        writeTestCase(tc++, new int[]{2, 4});                            // 03 2|4
        writeTestCase(tc++, new int[]{3, 5});                            // 04 coprime
        writeTestCase(tc++, new int[]{1, 5, 7, 11, 13});                 // 05 contains 1
        writeTestCase(tc++, new int[]{1, 2, 4, 8, 16, 32});              // 06 chain
        writeTestCase(tc++, new int[]{32, 16, 8, 4, 2, 1});              // 07 chain shuffled
        writeTestCase(tc++, new int[]{2, 3, 5, 7, 11, 13});              // 08 pairwise coprime
        writeTestCase(tc++, new int[]{1, 2, 4, 8, 3, 9, 27});            // 09 two chains
        writeTestCase(tc++, new int[]{2147483647});                      // 10 max int alone
        writeTestCase(tc++, new int[]{1, 2147483647});                   // 11 1 + max int

        { // 12 powers of 2
            int[] arr = new int[31];
            for (int i = 0; i <= 30; i++) arr[i] = 1 << i;
            writeTestCase(tc++, arr);
        }

        writeTestCase(tc++, new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29}); // 13 primes
        writeTestCase(tc++, new int[]{3, 6, 9, 12, 15, 18, 21, 24, 27, 30}); // 14 mult of 3
        writeTestCase(tc++, new int[]{1, 2, 3, 5, 8, 13, 21, 34, 55});   // 15 1 + fib
        writeTestCase(tc++, new int[]{2, 4, 8, 16, 3, 9, 27, 81});       // 16 interleaved
        writeTestCase(tc++, new int[]{1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800}); // 17 factorial
        writeTestCase(tc++, new int[]{2, 1000000000, 999999998, 500000000, 250000000, 125000000}); // 18 big + divisor
        writeTestCase(tc++, new int[]{1, 2, 4, 3, 6, 12, 5, 10, 20, 7, 14, 28}); // 19 many chains

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 1 + rng.nextInt(8);
            int maxVal = 1 + rng.nextInt(50);
            writeTestCase(tc++, randomDistinct(Math.min(n, maxVal), maxVal, rng));
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int n = 30 + rng.nextInt(71);
            int maxVal = 100 + rng.nextInt(10000);
            writeTestCase(tc++, randomDistinct(n, maxVal, rng));
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        { // 80: powers of 2 + multiples of 2^20 padding
            LinkedHashSet<Integer> s = new LinkedHashSet<>();
            for (int i = 0; i <= 30; i++) s.add(1 << i);
            long big = 1L << 20;
            long v = big * 3;
            while (s.size() < 1000 && v <= Integer.MAX_VALUE) {
                if (!s.contains((int) v)) s.add((int) v);
                v += big;
            }
            int[] arr = new int[s.size()];
            int idx = 0;
            for (int x : s) arr[idx++] = x;
            writeTestCase(tc++, arr);
        }

        { // 81: 1..1000
            int[] arr = new int[1000];
            for (int i = 0; i < 1000; i++) arr[i] = i + 1;
            writeTestCase(tc++, arr);
        }

        writeTestCase(tc++, chainArray(31, Integer.MAX_VALUE, rng));      // 82 chain by mult

        { // 83: all even 2..2000
            int[] arr = new int[1000];
            for (int i = 0; i < 1000; i++) arr[i] = 2 * (i + 1);
            writeTestCase(tc++, arr);
        }

        { // 84: powers of 2 + multiples of 2^20
            int[] arr = new int[1000];
            arr[0] = 1;
            for (int i = 1; i < 31; i++) arr[i] = 1 << i;
            long v = (1L << 20) * 3;
            int idx = 31;
            while (idx < 1000 && v <= Integer.MAX_VALUE) {
                arr[idx++] = (int) v;
                v += (1L << 20);
            }
            writeTestCase(tc++, Arrays.copyOf(arr, idx));
        }

        writeTestCase(tc++, firstKPrimes(1000));                          // 85 1000 primes
        writeTestCase(tc++, randomDistinct(1000, 1_000_000, rng));        // 86 random [1,1e6]

        for (int k = 0; k < 5; k++) {                                     // 87-91 random [1,2e9]
            writeTestCase(tc++, randomDistinct(1000, 2_000_000_000, rng));
        }

        { // 92: odd multiples of 3
            int[] arr = new int[1000];
            int idx = 0;
            for (int v = 3; v <= 6000 && idx < 1000; v += 6) arr[idx++] = v;
            writeTestCase(tc++, Arrays.copyOf(arr, idx));
        }

        { // 93: factorial chain + padding
            LinkedHashSet<Integer> s = new LinkedHashSet<>();
            long f = 1;
            s.add((int) f);
            for (int i = 2; i <= 12; i++) {
                f *= i;
                if (f > Integer.MAX_VALUE) break;
                s.add((int) f);
            }
            long largest = 1;
            for (int x : s) largest = Math.max(largest, x);
            long v = largest * 2;
            while (s.size() < 1000 && v <= Integer.MAX_VALUE) {
                if (!s.contains((int) v)) s.add((int) v);
                v += largest;
            }
            int[] arr = new int[s.size()];
            int idx = 0;
            for (int x : s) arr[idx++] = x;
            writeTestCase(tc++, arr);
        }

        { // 94: half even, half primes
            int[] arr = new int[1000];
            int idx = 0;
            for (int i = 1; i <= 500; i++) arr[idx++] = 2 * i;
            for (int p : firstKPrimes(500)) arr[idx++] = p;
            writeTestCase(tc++, arr);
        }

        writeTestCase(tc++, randomDistinct(999, 1_000_000_000, rng));      // 95 n=999

        { // 96: base-2 chain + padding
            int[] arr = new int[1000];
            arr[0] = 1;
            for (int i = 1; i < 31; i++) arr[i] = 1 << i;
            int idx = 31;
            long base = 1L << 20;
            while (idx < 1000) {
                long mult = 3 + rng.nextInt(2000);
                long v = base * mult;
                if (v > Integer.MAX_VALUE) break;
                arr[idx++] = (int) v;
            }
            writeTestCase(tc++, Arrays.copyOf(arr, idx));
        }

        { // 97: base-3 chain + padding
            LinkedHashSet<Integer> s = new LinkedHashSet<>();
            long cur = 1;
            while (s.size() < 1000 && cur <= Integer.MAX_VALUE) {
                s.add((int) cur);
                cur *= 3;
            }
            long largest = 1;
            for (int x : s) largest = Math.max(largest, x);
            long v = largest * 2;
            while (s.size() < 1000 && v <= Integer.MAX_VALUE) {
                if (!s.contains((int) v)) s.add((int) v);
                v += largest;
            }
            int[] arr = new int[s.size()];
            int idx = 0;
            for (int x : s) arr[idx++] = x;
            writeTestCase(tc++, arr);
        }

        { // 98: 1..1000 again
            int[] arr = new int[1000];
            for (int i = 0; i < 1000; i++) arr[i] = i + 1;
            writeTestCase(tc++, arr);
        }

        { // 99: divisible-biased random
            LinkedHashSet<Integer> s = new LinkedHashSet<>();
            while (s.size() < 1000) {
                int base = 1 + rng.nextInt(1_000_000);
                int mult = 1 + rng.nextInt(1000);
                long v = (long) base * mult;
                if (v >= 1 && v <= Integer.MAX_VALUE) s.add((int) v);
            }
            int[] arr = new int[s.size()];
            int idx = 0;
            for (int x : s) arr[idx++] = x;
            writeTestCase(tc++, arr);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Largest Divisible Subset.");
    }
}