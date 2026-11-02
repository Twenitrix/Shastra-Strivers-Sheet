import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference O(n) DP solver matching the Java Solution in the problem.
    private static long solve(int n, int fee, int[] prices) {
        long cash = 0;
        long hold = -(long) prices[0];
        for (int i = 1; i < n; i++) {
            long prevCash = cash;
            long prevHold = hold;
            cash = Math.max(prevCash, prevHold + prices[i] - fee);
            hold = Math.max(prevHold, prevCash - prices[i]);
        }
        return cash;
    }

    private static void writeTestCase(int index, int n, int fee, int[] prices) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir  = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        File inFile  = new File(inputDir,  String.format("input%02d.txt",  index));
        File outFile = new File(outputDir, String.format("output%02d.txt", index));

        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.print(n);
            pw.print(' ');
            pw.println(fee);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(prices[i]);
                if (i < n - 1) sb.append(' ');
            }
            pw.println(sb.toString());
        }

        long ans = solve(n, fee, prices);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (01 - 20) ----

        // TC 01: single day, no trade
        writeTestCase(tc++, 1, 0, new int[]{5});

        // TC 02: single day with nonzero fee
        writeTestCase(tc++, 1, 3, new int[]{7});

        // TC 03: two days, profitable, no fee
        writeTestCase(tc++, 2, 0, new int[]{1, 10});

        // TC 04: two days, profit exactly equals fee
        writeTestCase(tc++, 2, 9, new int[]{1, 10});

        // TC 05: two days, fee exceeds profit
        writeTestCase(tc++, 2, 20, new int[]{1, 10});

        // TC 06: two days, decreasing
        writeTestCase(tc++, 2, 0, new int[]{10, 1});

        // TC 07: strictly decreasing (example 3)
        writeTestCase(tc++, 4, 5, new int[]{5, 4, 3, 2});

        // TC 08: all equal
        writeTestCase(tc++, 5, 2, new int[]{5, 5, 5, 5, 5});

        // TC 09: example 2
        writeTestCase(tc++, 5, 3, new int[]{1, 3, 7, 5, 10});

        // TC 10: example 1
        writeTestCase(tc++, 6, 2, new int[]{1, 3, 2, 8, 4, 9});

        // TC 11: fee = 0, classic "best time"
        writeTestCase(tc++, 6, 0, new int[]{7, 1, 5, 3, 6, 4});

        // TC 12: fee at max, prices span min..max
        writeTestCase(tc++, 5, 50000, new int[]{1, 50000, 2, 49999, 1});

        // TC 13: max price values, small fee
        writeTestCase(tc++, 4, 1, new int[]{50000, 1, 50000, 1});

        // TC 14: min price values
        writeTestCase(tc++, 4, 0, new int[]{1, 1, 1, 1});

        // TC 15: alternating 1/10 with fee=1 (multi-trade)
        writeTestCase(tc++, 8, 1, new int[]{1, 10, 1, 10, 1, 10, 1, 10});

        // TC 16: valley then peak
        writeTestCase(tc++, 5, 0, new int[]{10, 1, 2, 3, 100});

        // TC 17: peak then valley
        writeTestCase(tc++, 5, 0, new int[]{100, 3, 2, 1, 10});

        // TC 18: flat with zero fee
        writeTestCase(tc++, 3, 0, new int[]{4, 4, 4});

        // TC 19: multi-trade alternating with fee=1
        writeTestCase(tc++, 7, 1, new int[]{1, 5, 1, 5, 1, 5, 1});

        // TC 20: flat at max price with max fee
        writeTestCase(tc++, 3, 50000, new int[]{50000, 50000, 50000});

        // ---- Category B: Small random tests (21 - 50) ----
        for (int k = 0; k < 30; k++) {
            int n = 1 + rng.nextInt(10);
            int fee = rng.nextInt(11);            // 0..10
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = 1 + rng.nextInt(20); // 1..20
            writeTestCase(tc++, n, fee, p);
        }

        // ---- Category C: Medium random tests (51 - 80) ----
        for (int k = 0; k < 30; k++) {
            int n = 50 + rng.nextInt(151);        // 50..200
            int fee = rng.nextInt(101);           // 0..100
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = 1 + rng.nextInt(1000); // 1..1000
            writeTestCase(tc++, n, fee, p);
        }

        // ---- Category D: Large / stress tests (81 - 100) ----

        // TC 81: strictly increasing 1..50000 (profit = 49999 - fee)
        {
            int n = 50000;
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = i + 1;
            writeTestCase(tc++, n, 0, p);
        }

        // TC 82: strictly decreasing 50000..1 (profit = 0)
        {
            int n = 50000;
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = n - i;
            writeTestCase(tc++, n, 0, p);
        }

        // TC 83: all equal at max price, max fee (profit = 0)
        {
            int n = 50000;
            int[] p = new int[n];
            Arrays.fill(p, 50000);
            writeTestCase(tc++, n, 50000, p);
        }

        // TC 84: alternating 1 / 50000, fee = 1 (max multi-trade gain)
        {
            int n = 50000;
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = (i % 2 == 0) ? 1 : 50000;
            writeTestCase(tc++, n, 1, p);
        }

        // TC 85: alternating 1 / 50000, fee = 50000 (all gains eaten -> 0)
        {
            int n = 50000;
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = (i % 2 == 0) ? 1 : 50000;
            writeTestCase(tc++, n, 50000, p);
        }

        // TC 86: V-shape valley at center
        {
            int n = 50000;
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = (i <= n / 2) ? (n / 2 - i + 1) : (i - n / 2 + 1);
            writeTestCase(tc++, n, 0, p);
        }

        // TC 87: inverted V (peak at center)
        {
            int n = 50000;
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = (i <= n / 2) ? (i + 1) : (n - i);
            writeTestCase(tc++, n, 0, p);
        }

        // TC 88: random full-range, random fee
        for (int k = 0; k < 5; k++) {
            int n = 40000 + rng.nextInt(10001);
            int fee = rng.nextInt(50001);
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = 1 + rng.nextInt(50000);
            writeTestCase(tc++, n, fee, p);
        }

        // TC 93 - 96: small-amplitude random walk around a base price
        for (int k = 0; k < 4; k++) {
            int n = 40000 + rng.nextInt(10001);
            int fee = rng.nextInt(50);
            int[] p = new int[n];
            int base = 1 + rng.nextInt(49900);
            for (int i = 0; i < n; i++) {
                int d = rng.nextInt(21) - 10;      // -10..10
                base = Math.max(1, Math.min(50000, base + d));
                p[i] = base;
            }
            writeTestCase(tc++, n, fee, p);
        }

        // TC 97: half increasing, half decreasing
        {
            int n = 50000;
            int[] p = new int[n];
            for (int i = 0; i < n; i++) {
                p[i] = (i < n / 2) ? (i + 1) : (n - i);
            }
            writeTestCase(tc++, n, 0, p);
        }

        // TC 98: sawtooth (repeating ramp up then crash)
        {
            int n = 50000;
            int[] p = new int[n];
            int period = 100;
            for (int i = 0; i < n; i++) {
                int r = i % period;
                p[i] = (r < period / 2) ? (r * 100 + 1) : ((period - r) * 100 + 1);
                if (p[i] > 50000) p[i] = 50000;
                if (p[i] < 1) p[i] = 1;
            }
            writeTestCase(tc++, n, 0, p);
        }

        // TC 99: two huge disjoint profitable runs
        {
            int n = 50000;
            int[] p = new int[n];
            for (int i = 0; i < n / 2; i++)     p[i] = i + 1;            // rise 1..25000
            for (int i = n / 2; i < n; i++)     p[i] = (n - i);          // fall 25000..1
            writeTestCase(tc++, n, 0, p);
        }

        // TC 100: max-scale fully random with max fee
        {
            int n = 50000;
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = 1 + rng.nextInt(50000);
            writeTestCase(tc++, n, 50000, p);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Best Time to Buy and Sell Stock with Transaction Fee.");
    }
}