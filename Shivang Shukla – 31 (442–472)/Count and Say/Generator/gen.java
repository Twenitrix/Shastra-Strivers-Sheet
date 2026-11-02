import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: iterative count-and-say.
    private static String solve(int n) {
        String cur = "1";
        for (int k = 2; k <= n; k++) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < cur.length()) {
                int j = i;
                while (j < cur.length() && cur.charAt(j) == cur.charAt(i)) j++;
                int count = j - i;
                sb.append(count).append(cur.charAt(i));
                i = j;
            }
            cur = sb.toString();
        }
        return cur;
    }

    private static void writeTestCase(int index, int n) throws Exception {
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
        }

        String ans = solve(n);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----
        // Constraints: 1 <= n <= 30. Only 30 distinct inputs exist,
        // so hand-crafted cases cover the full meaningful range and
        // boundary behaviour; random repeats follow.

        // TC 00: example 2 (n = 1, minimum)
        writeTestCase(tc++, 1);

        // TC 01: n = 2
        writeTestCase(tc++, 2);

        // TC 02: n = 3
        writeTestCase(tc++, 3);

        // TC 03: example 1 (n = 4)
        writeTestCase(tc++, 4);

        // TC 04: n = 5
        writeTestCase(tc++, 5);

        // TC 05: n = 6
        writeTestCase(tc++, 6);

        // TC 06: n = 7
        writeTestCase(tc++, 7);

        // TC 07: n = 8
        writeTestCase(tc++, 8);

        // TC 08: n = 9
        writeTestCase(tc++, 9);

        // TC 09: n = 10 (first double-digit length appears)
        writeTestCase(tc++, 10);

        // TC 10: n = 11
        writeTestCase(tc++, 11);

        // TC 11: n = 12
        writeTestCase(tc++, 12);

        // TC 12: n = 13
        writeTestCase(tc++, 13);

        // TC 13: n = 14
        writeTestCase(tc++, 14);

        // TC 14: n = 15
        writeTestCase(tc++, 15);

        // TC 15: n = 16
        writeTestCase(tc++, 16);

        // TC 16: n = 17
        writeTestCase(tc++, 17);

        // TC 17: n = 18
        writeTestCase(tc++, 18);

        // TC 18: n = 19
        writeTestCase(tc++, 19);

        // TC 19: n = 20
        writeTestCase(tc++, 20);

        // ---- Category B: Every remaining n in [21, 30] exactly once (20 - 29) ----
        for (int n = 21; n <= 30; n++) {
            writeTestCase(tc++, n);
        }

        // ---- Category C: Random small tests, n in [1, 30] (30 - 59) ----
        for (int k = 0; k < 30; k++) {
            int n = 1 + rng.nextInt(30);
            writeTestCase(tc++, n);
        }

        // ---- Category D: Random medium tests, n in [10, 30] (60 - 79) ----
        for (int k = 0; k < 20; k++) {
            int n = 10 + rng.nextInt(21);   // 10..30
            writeTestCase(tc++, n);
        }

        // ---- Category E: Stress / boundary-heavy (80 - 99) ----
        // Repeat the extreme boundaries many times so that any off-by-one
        // or recursion-depth bug gets hammered.

        // TC 80-84: n = 30 five times (max allowed)
        for (int k = 0; k < 5; k++) writeTestCase(tc++, 30);

        // TC 85-89: n = 1 five times (min allowed)
        for (int k = 0; k < 5; k++) writeTestCase(tc++, 1);

        // TC 90: n = 29 (just below max)
        writeTestCase(tc++, 29);

        // TC 91: n = 2 (near min)
        writeTestCase(tc++, 2);

        // TC 92-99: random n in [25, 30] (heavy tail near max)
        for (int k = 0; k < 8; k++) {
            int n = 25 + rng.nextInt(6);    // 25..30
            writeTestCase(tc++, n);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Count and Say.");
    }
}