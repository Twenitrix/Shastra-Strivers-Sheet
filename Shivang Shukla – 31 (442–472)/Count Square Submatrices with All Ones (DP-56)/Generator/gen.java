import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference O(m*n) DP solver for Count Square Submatrices with All Ones.
    private static long solve(int m, int n, int[][] mat) {
        long[][] dp = new long[m][n];
        long total = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = 1 + Math.min(dp[i - 1][j],
                                       Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                    }
                    total += dp[i][j];
                }
            }
        }
        return total;
    }

    private static void writeTestCase(int index, int m, int n, int[][] mat) throws Exception {
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
            pw.println(m + " " + n);
            for (int i = 0; i < m; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    sb.append(mat[i][j]);
                    if (j < n - 1) sb.append(' ');
                }
                pw.println(sb.toString());
            }
        }

        long ans = solve(m, n, mat);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    // Helper: build an m x n matrix with every entry = v
    private static int[][] filled(int m, int n, int v) {
        int[][] mat = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(mat[i], v);
        return mat;
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        // TC 00: example 1
        writeTestCase(tc++, 3, 4, new int[][]{
            {0,1,1,1},
            {1,1,1,1},
            {0,1,1,1}
        });

        // TC 01: example 2
        writeTestCase(tc++, 2, 2, new int[][]{
            {0,1},
            {1,0}
        });

        // TC 02: 1x1 = {0}
        writeTestCase(tc++, 1, 1, new int[][]{{0}});

        // TC 03: 1x1 = {1}
        writeTestCase(tc++, 1, 1, new int[][]{{1}});

        // TC 04: 1xn all ones
        writeTestCase(tc++, 1, 5, new int[][]{{1,1,1,1,1}});

        // TC 05: mx1 all ones
        writeTestCase(tc++, 5, 1, new int[][]{{1},{1},{1},{1},{1}});

        // TC 06: 1xn all zeros
        writeTestCase(tc++, 1, 5, new int[][]{{0,0,0,0,0}});

        // TC 07: all zeros 5x5
        writeTestCase(tc++, 5, 5, filled(5, 5, 0));

        // TC 08: all ones 5x5 -> 25 + 16 + 9 + 4 + 1 = 55
        writeTestCase(tc++, 5, 5, filled(5, 5, 1));

        // TC 09: all ones 1x1..(tiny shapes)
        writeTestCase(tc++, 2, 2, filled(2, 2, 1));

        // TC 10: all ones 3x3 -> 9 + 4 + 1 = 14
        writeTestCase(tc++, 3, 3, filled(3, 3, 1));

        // TC 11: all ones 4x4 -> 16 + 9 + 4 + 1 = 30
        writeTestCase(tc++, 4, 4, filled(4, 4, 1));

        // TC 12: identity-ish matrix (only diagonal ones)
        writeTestCase(tc++, 5, 5, new int[][]{
            {1,0,0,0,0},
            {0,1,0,0,0},
            {0,0,1,0,0},
            {0,0,0,1,0},
            {0,0,0,0,1}
        });

        // TC 13: anti-diagonal ones
        writeTestCase(tc++, 5, 5, new int[][]{
            {0,0,0,0,1},
            {0,0,0,1,0},
            {0,0,1,0,0},
            {0,1,0,0,0},
            {1,0,0,0,0}
        });

        // TC 14: one big block 3x3 of ones inside 5x5 zeros
        writeTestCase(tc++, 5, 5, new int[][]{
            {0,0,0,0,0},
            {0,1,1,1,0},
            {0,1,1,1,0},
            {0,1,1,1,0},
            {0,0,0,0,0}
        });

        // TC 15: checkerboard 6x6
        {
            int m = 6, n = 6;
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    mat[i][j] = (i + j) % 2;
            writeTestCase(tc++, m, n, mat);
        }

        // TC 16: horizontal stripes (row i is all 1 if i even else all 0)
        {
            int m = 6, n = 6;
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++)
                Arrays.fill(mat[i], (i % 2 == 0) ? 1 : 0);
            writeTestCase(tc++, m, n, mat);
        }

        // TC 17: vertical stripes
        {
            int m = 6, n = 6;
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    mat[i][j] = (j % 2 == 0) ? 1 : 0;
            writeTestCase(tc++, m, n, mat);
        }

        // TC 18: single 1 in the middle of 5x5 zeros
        {
            int m = 5, n = 5;
            int[][] mat = filled(m, n, 0);
            mat[2][2] = 1;
            writeTestCase(tc++, m, n, mat);
        }

        // TC 19: L-shape / border ones of 5x5, interior zeros
        {
            int m = 5, n = 5;
            int[][] mat = filled(m, n, 0);
            for (int j = 0; j < n; j++) { mat[0][j] = 1; mat[m-1][j] = 1; }
            for (int i = 0; i < m; i++) { mat[i][0] = 1; mat[i][n-1] = 1; }
            writeTestCase(tc++, m, n, mat);
        }

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int m = 1 + rng.nextInt(5);          // 1..5
            int n = 1 + rng.nextInt(5);          // 1..5
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    mat[i][j] = rng.nextInt(2);
            writeTestCase(tc++, m, n, mat);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int m = 20 + rng.nextInt(81);        // 20..100
            int n = 20 + rng.nextInt(81);        // 20..100
            int[][] mat = new int[m][n];
            // vary density to hit both sparse & dense regimes
            int pctOne = 20 + rng.nextInt(61);   // 20..80
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    mat[i][j] = (rng.nextInt(100) < pctOne) ? 1 : 0;
            writeTestCase(tc++, m, n, mat);
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: 300x300 all ones -> sum_{k=1..300} (301-k)^2
        writeTestCase(tc++, 300, 300, filled(300, 300, 1));

        // TC 81: 300x300 all zeros
        writeTestCase(tc++, 300, 300, filled(300, 300, 0));

        // TC 82: 300x1 all ones
        {
            int[][] mat = filled(300, 1, 1);
            writeTestCase(tc++, 300, 1, mat);
        }

        // TC 83: 1x300 all ones
        {
            int[][] mat = filled(1, 300, 1);
            writeTestCase(tc++, 1, 300, mat);
        }

        // TC 84: 300x300 checkerboard
        {
            int m = 300, n = 300;
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    mat[i][j] = (i + j) % 2;
            writeTestCase(tc++, m, n, mat);
        }

        // TC 85: 300x300 horizontal stripes (even rows ones)
        {
            int m = 300, n = 300;
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++)
                Arrays.fill(mat[i], (i % 2 == 0) ? 1 : 0);
            writeTestCase(tc++, m, n, mat);
        }

        // TC 86: 300x300 vertical stripes
        {
            int m = 300, n = 300;
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    mat[i][j] = (j % 2 == 0) ? 1 : 0;
            writeTestCase(tc++, m, n, mat);
        }

        // TC 87 - 91: 300x300 fully random (density 50%)
        for (int k = 0; k < 5; k++) {
            int m = 300, n = 300;
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    mat[i][j] = rng.nextInt(2);
            writeTestCase(tc++, m, n, mat);
        }

        // TC 92: 300x300 sparse (~10% ones)
        {
            int m = 300, n = 300;
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    mat[i][j] = (rng.nextInt(100) < 10) ? 1 : 0;
            writeTestCase(tc++, m, n, mat);
        }

        // TC 93: 300x300 dense (~90% ones)
        {
            int m = 300, n = 300;
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    mat[i][j] = (rng.nextInt(100) < 90) ? 1 : 0;
            writeTestCase(tc++, m, n, mat);
        }

        // TC 94: 300x300 upper-triangular ones (staircase)
        {
            int m = 300, n = 300;
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    mat[i][j] = (j >= i) ? 1 : 0;
            writeTestCase(tc++, m, n, mat);
        }

        // TC 95: 300x300 lower-triangular ones
        {
            int m = 300, n = 300;
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    mat[i][j] = (j <= i) ? 1 : 0;
            writeTestCase(tc++, m, n, mat);
        }

        // TC 96: 300x300 all ones except a cross of zeros
        {
            int m = 300, n = 300;
            int[][] mat = filled(m, n, 1);
            for (int j = 0; j < n; j++) mat[m / 2][j] = 0;
            for (int i = 0; i < m; i++) mat[i][n / 2] = 0;
            writeTestCase(tc++, m, n, mat);
        }

        // TC 97: 300x300 all ones except a border of zeros
        {
            int m = 300, n = 300;
            int[][] mat = filled(m, n, 1);
            for (int j = 0; j < n; j++) { mat[0][j] = 0; mat[m-1][j] = 0; }
            for (int i = 0; i < m; i++) { mat[i][0] = 0; mat[i][n-1] = 0; }
            writeTestCase(tc++, m, n, mat);
        }

        // TC 98: 300x300 random with density in 40%..60% per row (heterogeneous)
        {
            int m = 300, n = 300;
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++) {
                int pct = 40 + rng.nextInt(21);  // 40..60
                for (int j = 0; j < n; j++)
                    mat[i][j] = (rng.nextInt(100) < pct) ? 1 : 0;
            }
            writeTestCase(tc++, m, n, mat);
        }

        // TC 99: 300x300 random walk density (regions of ones/zeros)
        {
            int m = 300, n = 300;
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++) {
                int cur = 50;
                for (int j = 0; j < n; j++) {
                    cur = Math.max(5, Math.min(95, cur + rng.nextInt(11) - 5));
                    mat[i][j] = (rng.nextInt(100) < cur) ? 1 : 0;
                }
            }
            writeTestCase(tc++, m, n, mat);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Count Square Submatrices with All Ones.");
    }
}