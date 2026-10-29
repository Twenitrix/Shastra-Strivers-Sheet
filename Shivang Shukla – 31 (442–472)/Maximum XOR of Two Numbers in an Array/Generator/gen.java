import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: O(31 * n) bitwise greedy using HashSet prefixes.
    private static long solve(int n, int[] nums) {
        int mask = 0;
        int ans = 0;
        for (int bit = 30; bit >= 0; bit--) {
            mask |= (1 << bit);
            Set<Integer> prefixes = new HashSet<>();
            for (int x : nums) prefixes.add(x & mask);

            int candidate = ans | (1 << bit);
            for (int pref : prefixes) {
                if (prefixes.contains(pref ^ candidate)) {
                    ans = candidate;
                    break;
                }
            }
        }
        return ans;
    }

    private static void writeTestCase(int index, int n, int[] nums) throws Exception {
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

    // FIXED: uses long arithmetic to avoid int overflow when range = 2^31.
    private static int[] randomArray(int n, int lo, int hi, Random rng) {
        int[] a = new int[n];
        long range = (long) hi - lo + 1;
        for (int i = 0; i < n; i++) {
            long v = lo + (long) (rng.nextDouble() * range);
            a[i] = (int) v;
        }
        return a;
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        // TC 00: example 1
        writeTestCase(tc++, 6, new int[]{3, 10, 5, 25, 2, 8});

        // TC 01: example 2
        writeTestCase(tc++, 3, new int[]{1, 2, 3});

        // TC 02: n = 2, both zero
        writeTestCase(tc++, 2, new int[]{0, 0});

        // TC 03: n = 2, one zero one max
        writeTestCase(tc++, 2, new int[]{0, 2147483647});

        // TC 04: n = 2, both max
        writeTestCase(tc++, 2, new int[]{2147483647, 2147483647});

        // TC 05: n = 2, both equal
        writeTestCase(tc++, 2, new int[]{12345, 12345});

        // TC 06: n = 2, complementary bits
        writeTestCase(tc++, 2, new int[]{0, 2147483647});

        // TC 07: all zeros
        writeTestCase(tc++, 5, new int[]{0, 0, 0, 0, 0});

        // TC 08: all max
        writeTestCase(tc++, 4, new int[]{2147483647, 2147483647, 2147483647, 2147483647});

        // TC 09: all same mid value
        writeTestCase(tc++, 6, new int[]{1000000000, 1000000000, 1000000000, 1000000000, 1000000000, 1000000000});

        // TC 10: powers of two
        writeTestCase(tc++, 5, new int[]{1, 2, 4, 8, 16});

        // TC 11: increasing powers 2^0..2^30
        {
            int n = 31;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = 1 << i;
            writeTestCase(tc++, n, a);
        }

        // TC 12: max and max-1 (XOR = 1)
        writeTestCase(tc++, 2, new int[]{2147483647, 2147483646});

        // TC 13: two halves complementary (30-bit)
        writeTestCase(tc++, 2, new int[]{0, 1073741823});

        // TC 14: 0, 2^30, 2^31-1 — max XOR
        writeTestCase(tc++, 3, new int[]{0, 1073741824, 2147483647});

        // TC 15: consecutive 0..7
        writeTestCase(tc++, 8, new int[]{0, 1, 2, 3, 4, 5, 6, 7});

        // TC 16: consecutive 1..10
        {
            int n = 10;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i + 1;
            writeTestCase(tc++, n, a);
        }

        // TC 17: alternating bit pattern
        {
            int a = 0x55555555;
            int c = a ^ 0x7FFFFFFF;
            writeTestCase(tc++, 3, new int[]{a, c, 0});
        }

        // TC 18: just below the 2^31 boundary
        writeTestCase(tc++, 3, new int[]{1073741823, 1073741824, 2147483647});

        // TC 19: two values differing in only high bit
        writeTestCase(tc++, 2, new int[]{0, 1073741824});

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 2 + rng.nextInt(9);              // 2..10
            int[] a = randomArray(n, 0, 255, rng);
            writeTestCase(tc++, n, a);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int n = 1000 + rng.nextInt(9001);        // 1000..10000
            int[] a = randomArray(n, 0, 1000000, rng);
            writeTestCase(tc++, n, a);
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 2e5, all zeros
        {
            int n = 200000;
            int[] a = new int[n];
            writeTestCase(tc++, n, a);
        }

        // TC 81: n = 2e5, all max
        {
            int n = 200000;
            int[] a = new int[n];
            Arrays.fill(a, Integer.MAX_VALUE);
            writeTestCase(tc++, n, a);
        }

        // TC 82: n = 2e5, one zero + all max
        {
            int n = 200000;
            int[] a = new int[n];
            Arrays.fill(a, Integer.MAX_VALUE);
            a[n / 2] = 0;
            writeTestCase(tc++, n, a);
        }

        // TC 83: n = 2e5, strictly increasing 0..199999
        {
            int n = 200000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i;
            writeTestCase(tc++, n, a);
        }

        // TC 84: n = 2e5, powers of 2 cycling
        {
            int n = 200000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = 1 << (i % 31);
            writeTestCase(tc++, n, a);
        }

        // TC 85: n = 2e5, alternating 0 and max
        {
            int n = 200000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? 0 : Integer.MAX_VALUE;
            writeTestCase(tc++, n, a);
        }

        // TC 86 - 90: n = 2e5, fully random full int range
        for (int k = 0; k < 5; k++) {
            int n = 200000;
            int[] a = randomArray(n, 0, Integer.MAX_VALUE, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 91: n = 2e5, small range [0, 2^20 - 1]
        {
            int n = 200000;
            int[] a = randomArray(n, 0, (1 << 20) - 1, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 92: n = 2e5, random in [2^30, 2^31 - 1]
        {
            int n = 200000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = (1 << 30) + rng.nextInt(1 << 30);
            }
            writeTestCase(tc++, n, a);
        }

        // TC 93: n = 2e5, tiny range [0, 1023]
        {
            int n = 200000;
            int[] a = randomArray(n, 0, 1023, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 94: n = 199999 (odd), random full range
        {
            int n = 199999;
            int[] a = randomArray(n, 0, Integer.MAX_VALUE, rng);
            writeTestCase(tc++, n, a);
        }

        // TC 95: n = 2e5, two halves complement in 31-bit space
        {
            int n = 200000;
            int[] a = new int[n];
            int half = n / 2;
            for (int i = 0; i < half; i++) {
                a[i] = rng.nextInt(1 << 30);
            }
            for (int i = 0; i < n - half; i++) {
                a[half + i] = a[i] ^ Integer.MAX_VALUE;
            }
            writeTestCase(tc++, n, a);
        }

        // TC 96: n = 2e5, values 0..(n-1) shuffled
        {
            int n = 200000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i;
            for (int i = n - 1; i > 0; i--) {
                int j = rng.nextInt(i + 1);
                int t = a[i]; a[i] = a[j]; a[j] = t;
            }
            writeTestCase(tc++, n, a);
        }

        // TC 97: n = 2e5, all odd numbers
        {
            int n = 200000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = 2 * i + 1;
            writeTestCase(tc++, n, a);
        }

        // TC 98: n = 2e5, all even numbers
        {
            int n = 200000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = 2 * i;
            writeTestCase(tc++, n, a);
        }

        // TC 99: n = 2e5, two distinct values (0 and 2^30-1)
        {
            int n = 200000;
            int[] a = new int[n];
            Arrays.fill(a, 1073741823);
            a[n / 2] = 0;
            writeTestCase(tc++, n, a);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Maximum XOR of Two Numbers in an Array.");
    }
}