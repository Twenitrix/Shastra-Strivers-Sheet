import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    static final long MOD = 1_000_000_007L;

    // Reference solver: interval DP counting parenthesizations evaluating to T / F.
    private static long solve(String expr) {
        int len = expr.length();
        // operands at even indices, operators at odd indices
        int numOperands = (len + 1) / 2;

        long[][] T = new long[numOperands][numOperands];
        long[][] F = new long[numOperands][numOperands];

        for (int i = 0; i < numOperands; i++) {
            char c = expr.charAt(2 * i);
            if (c == 'T') T[i][i] = 1;
            else          F[i][i] = 1;
        }

        for (int gap = 1; gap < numOperands; gap++) {
            for (int i = 0; i + gap < numOperands; i++) {
                int j = i + gap;
                long waysT = 0, waysF = 0;
                for (int k = i; k < j; k++) {
                    char op = expr.charAt(2 * k + 1);
                    long lt = T[i][k], lf = F[i][k];
                    long rt = T[k + 1][j], rf = F[k + 1][j];

                    if (op == '&') {
                        waysT = (waysT + lt * rt) % MOD;
                        waysF = (waysF + lt * rf + lf * rt + lf * rf) % MOD;
                    } else if (op == '|') {
                        waysT = (waysT + lt * rt + lt * rf + lf * rt) % MOD;
                        waysF = (waysF + lf * rf) % MOD;
                    } else { // '^'
                        waysT = (waysT + lt * rf + lf * rt) % MOD;
                        waysF = (waysF + lt * rt + lf * rf) % MOD;
                    }
                }
                T[i][j] = waysT % MOD;
                F[i][j] = waysF % MOD;
            }
        }
        return T[0][numOperands - 1];
    }

    private static void writeTestCase(int index, String expr) throws Exception {
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
            pw.println(expr);
        }

        long ans = solve(expr);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    // Helper: build a random valid expression with `k` operands.
    private static String randomExpr(int k, Random rng) {
        StringBuilder sb = new StringBuilder();
        char[] ops = {'&', '|', '^'};
        for (int i = 0; i < k; i++) {
            sb.append(rng.nextBoolean() ? 'T' : 'F');
            if (i < k - 1) sb.append(ops[rng.nextInt(3)]);
        }
        return sb.toString();
    }

    // Helper: build an expression with all operands = `operand`, all operators = `op`.
    private static String uniformExpr(int k, char operand, char op) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            sb.append(operand);
            if (i < k - 1) sb.append(op);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        // TC 00: single operand T (no operators)
        writeTestCase(tc++, "T");

        // TC 01: single operand F
        writeTestCase(tc++, "F");

        // TC 02: two operands T&T
        writeTestCase(tc++, "T&T");

        // TC 03: two operands T|F
        writeTestCase(tc++, "T|F");

        // TC 04: two operands T^F
        writeTestCase(tc++, "T^F");

        // TC 05: two operands F&F
        writeTestCase(tc++, "F&F");

        // TC 06: example 2 -> 2
        writeTestCase(tc++, "T^F|F");

        // TC 07: three operands T|T|T (only one parenthesization grouping is logically equivalent; count ways)
        writeTestCase(tc++, "T|T|T");

        // TC 08: three operands F&F&F
        writeTestCase(tc++, "F&F&F");

        // TC 09: T&T&T
        writeTestCase(tc++, "T&T&T");

        // TC 10: all XOR of T's, odd count -> true
        writeTestCase(tc++, "T^T^T");

        // TC 11: all XOR of T's, even count -> false
        writeTestCase(tc++, "T^T^T^T");

        // TC 12: mixed short expression
        writeTestCase(tc++, "T|F&T");

        // TC 13: expression starting with F
        writeTestCase(tc++, "F|T");

        // TC 14: expression that is all-false
        writeTestCase(tc++, "F|F&F");

        // TC 15: example 1 -> 3 (per statement's own explanation)
        writeTestCase(tc++, "T|T&F^T");

        // TC 16: nested operator variety
        writeTestCase(tc++, "T^F^T|F");

        // TC 17: T with single op repeated
        writeTestCase(tc++, "T&T|T^T");

        // TC 18: F with mixed ops
        writeTestCase(tc++, "F|F^F&F");

        // TC 19: alternating truth values
        writeTestCase(tc++, "T&F|T&F|T");

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int operands = 1 + rng.nextInt(5);        // 1..5 operands
            writeTestCase(tc++, randomExpr(operands, rng));
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int operands = 10 + rng.nextInt(31);     // 10..40 operands
            writeTestCase(tc++, randomExpr(operands, rng));
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: 100 operands, all T, all AND -> 1 way to be true
        writeTestCase(tc++, uniformExpr(100, 'T', '&'));

        // TC 81: 100 operands, all T, all OR -> 1 way
        writeTestCase(tc++, uniformExpr(100, 'T', '|'));

        // TC 82: 100 operands, all T, all XOR (even -> result F)
        writeTestCase(tc++, uniformExpr(100, 'T', '^'));

        // TC 83: 100 operands, all F, all AND
        writeTestCase(tc++, uniformExpr(100, 'F', '&'));

        // TC 84: 100 operands, all F, all OR
        writeTestCase(tc++, uniformExpr(100, 'F', '|'));

        // TC 85: 100 operands, all F, all XOR
        writeTestCase(tc++, uniformExpr(100, 'F', '^'));

        // TC 86: 99 operands all T, all OR (odd, but all-true)
        writeTestCase(tc++, uniformExpr(99, 'T', '|'));

        // TC 87 - 91: 100 operands, fully random ops/operands
        for (int k = 0; k < 5; k++) {
            writeTestCase(tc++, randomExpr(100, rng));
        }

        // TC 92: 100 operands, all T, random ops
        {
            StringBuilder sb = new StringBuilder();
            char[] ops = {'&', '|', '^'};
            for (int i = 0; i < 100; i++) {
                sb.append('T');
                if (i < 99) sb.append(ops[rng.nextInt(3)]);
            }
            writeTestCase(tc++, sb.toString());
        }

        // TC 93: 100 operands, all F, random ops
        {
            StringBuilder sb = new StringBuilder();
            char[] ops = {'&', '|', '^'};
            for (int i = 0; i < 100; i++) {
                sb.append('F');
                if (i < 99) sb.append(ops[rng.nextInt(3)]);
            }
            writeTestCase(tc++, sb.toString());
        }

        // TC 94: 99 operands, alternating T/F, random ops
        {
            StringBuilder sb = new StringBuilder();
            char[] ops = {'&', '|', '^'};
            for (int i = 0; i < 99; i++) {
                sb.append((i % 2 == 0) ? 'T' : 'F');
                if (i < 98) sb.append(ops[rng.nextInt(3)]);
            }
            writeTestCase(tc++, sb.toString());
        }

        // TC 95: 99 operands, alternating T/F, all XOR
        writeTestCase(tc++, uniformExpr(99, 'T', '^'));

        // TC 96: 100 operands all T, all AND except last op is OR
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100; i++) {
                sb.append('T');
                if (i < 99) sb.append((i == 98) ? '|' : '&');
            }
            writeTestCase(tc++, sb.toString());
        }

        // TC 97: 100 operands, ops cycling &,|,^
        {
            StringBuilder sb = new StringBuilder();
            char[] cyc = {'&', '|', '^'};
            for (int i = 0; i < 100; i++) {
                sb.append(rng.nextBoolean() ? 'T' : 'F');
                if (i < 99) sb.append(cyc[i % 3]);
            }
            writeTestCase(tc++, sb.toString());
        }

        // TC 98: 99 operands, all T, ops cycling &,|,^
        {
            StringBuilder sb = new StringBuilder();
            char[] cyc = {'&', '|', '^'};
            for (int i = 0; i < 99; i++) {
                sb.append('T');
                if (i < 98) sb.append(cyc[i % 3]);
            }
            writeTestCase(tc++, sb.toString());
        }

        // TC 99: 100 operands, bias toward AND (mostly F result but many counts)
        {
            StringBuilder sb = new StringBuilder();
            char[] ops = {'&', '&', '&', '|', '^'};
            for (int i = 0; i < 100; i++) {
                sb.append(rng.nextBoolean() ? 'T' : 'F');
                if (i < 99) sb.append(ops[rng.nextInt(ops.length)]);
            }
            writeTestCase(tc++, sb.toString());
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Different Ways to Evaluate a Boolean Expression.");
    }
}