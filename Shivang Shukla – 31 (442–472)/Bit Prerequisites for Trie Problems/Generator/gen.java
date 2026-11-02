import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    static final int INF = 1000000000;

    private static String solve(int n, int[] a) {
        int andRes = a[0];
        int orRes  = a[0];
        int xorRes = a[0];
        for (int i = 1; i < n; i++) {
            andRes &= a[i];
            orRes  |= a[i];
            xorRes ^= a[i];
        }
        return andRes + " " + orRes + " " + xorRes;
    }

    private static void writeTestCase(int index, int n, int[] a) throws Exception {
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
                sb.append(a[i]);
                if (i < n - 1) sb.append(' ');
            }
            pw.println(sb.toString());
        }

        String ans = solve(n, a);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        // TC 00: example 1
        writeTestCase(tc++, 4, new int[]{5, 3, 7, 1});

        // TC 01: example 2
        writeTestCase(tc++, 3, new int[]{2, 2, 5});

        // TC 02: n = 1, single zero
        writeTestCase(tc++, 1, new int[]{0});

        // TC 03: n = 1, max element
        writeTestCase(tc++, 1, new int[]{1000000000});

        // TC 04: n = 1, small element
        writeTestCase(tc++, 1, new int[]{7});

        // TC 05: all zeros
        writeTestCase(tc++, 5, new int[]{0, 0, 0, 0, 0});

        // TC 06: single zero among non-zeros -> AND = 0
        writeTestCase(tc++, 5, new int[]{5, 3, 0, 7, 1});

        // TC 07: all same value, even count -> XOR = 0
        writeTestCase(tc++, 6, new int[]{42, 42, 42, 42, 42, 42});

        // TC 08: all same value, odd count -> XOR = value
        writeTestCase(tc++, 5, new int[]{42, 42, 42, 42, 42});

        // TC 09: all max value, even count
        writeTestCase(tc++, 4, new int[]{1000000000, 1000000000, 1000000000, 1000000000});

        // TC 10: powers of two (disjoint bits) -> AND = 0
        writeTestCase(tc++, 5, new int[]{1, 2, 4, 8, 16});

        // TC 11: all bits set in every element
        writeTestCase(tc++, 3, new int[]{1073741823, 1073741823, 1073741823});

        // TC 12: two equal elements -> XOR = 0
        writeTestCase(tc++, 2, new int[]{12345, 12345});

        // TC 13: two elements differing in one bit
        writeTestCase(tc++, 2, new int[]{8, 12});

        // TC 14: increasing powers-ish
        writeTestCase(tc++, 6, new int[]{1, 3, 7, 15, 31, 63});

        // TC 15: one large + many small
        writeTestCase(tc++, 6, new int[]{1000000000, 1, 2, 3, 4, 5});

        // TC 16: alternating 0 and max
        writeTestCase(tc++, 6, new int[]{0, 1000000000, 0, 1000000000, 0, 1000000000});

        // TC 17: near-equal values differing in last bit
        writeTestCase(tc++, 2, new int[]{1000000000, 999999999});

        // TC 18: values with high bit set (2^29 + small)
        writeTestCase(tc++, 4, new int[]{536870912, 536870913, 536870914, 536870915});

        // TC 19: two 1s and one 0
        writeTestCase(tc++, 3, new int[]{1, 1, 0});

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 1 + rng.nextInt(10);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rng.nextInt(100);
            writeTestCase(tc++, n, a);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int n = 50 + rng.nextInt(151);            // 50..200
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rng.nextInt(1000000);
            writeTestCase(tc++, n, a);
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 1e5, all equal (even count -> XOR = 0)
        {
            int n = 100000;
            int[] a = new int[n];
            Arrays.fill(a, 123456789);
            writeTestCase(tc++, n, a);
        }

        // TC 81: n = 1e5, all zeros
        {
            int n = 100000;
            int[] a = new int[n];
            writeTestCase(tc++, n, a);
        }

        // TC 82: n = 1e5, all max value (even count)
        {
            int n = 100000;
            int[] a = new int[n];
            Arrays.fill(a, 1000000000);
            writeTestCase(tc++, n, a);
        }

        // TC 83: n = 1e5, strictly increasing 0..99999
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i;
            writeTestCase(tc++, n, a);
        }

        // TC 84: n = 1e5, one zero + rest random -> AND = 0
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rng.nextInt(1000000001);
            a[rng.nextInt(n)] = 0;
            writeTestCase(tc++, n, a);
        }

        // TC 85: n = 1e5, powers of two cycling 2^0..2^29
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = 1 << (i % 30);
            writeTestCase(tc++, n, a);
        }

        // TC 86: n = 1e5, alternating 0 and max (even n)
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? 0 : 1000000000;
            writeTestCase(tc++, n, a);
        }

        // TC 87 - 91: n = 1e5, random full range [0, 1e9]
        for (int k = 0; k < 5; k++) {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rng.nextInt(1000000001);
            writeTestCase(tc++, n, a);
        }

        // TC 92: n = 1e5, bit-30 set, vary low 20 bits
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (1 << 29) | rng.nextInt(1 << 20);
            writeTestCase(tc++, n, a);
        }

        // TC 93: n = 1e5, alternating two distinct values (even n -> XOR=0)
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (i % 2 == 0) ? 12345 : 67890;
            writeTestCase(tc++, n, a);
        }

        // TC 94: n = 99999 (odd), all same value -> XOR = value
        {
            int n = 99999;
            int[] a = new int[n];
            Arrays.fill(a, 987654321);
            writeTestCase(tc++, n, a);
        }

        // TC 95: n = 1e5, descending 99999..0
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = n - 1 - i;
            writeTestCase(tc++, n, a);
        }

        // TC 96: n = 1e5, values in tight range around a base -> high AND
        {
            int n = 100000;
            int[] a = new int[n];
            int base = 1000000000;
            for (int i = 0; i < n; i++) a[i] = base + rng.nextInt(1000);
            writeTestCase(tc++, n, a);
        }

        // TC 97: n = 1e5, low amplitude random walk
        {
            int n = 100000;
            int[] a = new int[n];
            int cur = 500000000;
            for (int i = 0; i < n; i++) {
                cur = Math.max(0, Math.min(1000000000, cur + rng.nextInt(21) - 10));
                a[i] = cur;
            }
            writeTestCase(tc++, n, a);
        }

        // TC 98: n = 1e5, values 2^k - 1 (all low bits set)
        {
            int n = 100000;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = (1 << (i % 30)) - 1;
            writeTestCase(tc++, n, a);
        }

        // TC 99: n = 1e5, random drawn from a small pool (many duplicates)
        {
            int n = 100000;
            int[] a = new int[n];
            int[] pool = new int[50];
            for (int i = 0; i < 50; i++) pool[i] = rng.nextInt(1000000001);
            for (int i = 0; i < n; i++) a[i] = pool[rng.nextInt(50)];
            writeTestCase(tc++, n, a);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Bit Prerequisites for Trie Problems.");
    }
}