import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: O(N*M) using per-row histogram heights + monotonic stack.
    private static long solve(int n, int m, int[][] mat) {
        int[] heights = new int[m];
        long best = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                heights[j] = (mat[i][j] == 1) ? heights[j] + 1 : 0;
            }
            // largest rectangle in histogram for this row
            Deque<Integer> stack = new ArrayDeque<>();
            for (int j = 0; j <= m; j++) {
                int cur = (j == m) ? 0 : heights[j];
                while (!stack.isEmpty() && heights[stack.peek()] >= cur) {
                    int h = heights[stack.pop()];
                    int left = stack.isEmpty() ? -1 : stack.peek();
                    long area = (long) h * (j - left - 1);
                    if (area > best) best = area;
                }
                stack.push(j);
            }
        }
        return best;
    }

    private static void writeTestCase(int index, int n, int m, int[][] mat) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir  = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        File inFile  = new File(inputDir,  String.format("input%02d.txt",  index));
        File outFile = new File(outputDir, String.format("output%02d.txt", index));

        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(n + " " + m);
            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < m; j++) {
                    sb.append(mat[i][j]);
                    if (j < m - 1) sb.append(' ');
                }
                pw.println(sb.toString());
            }
        }

        long ans = solve(n, m, mat);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    private static int[][] filled(int n, int m, int v) {
        int[][] mat = new int[n][m];
        for (int i = 0; i < n; i++) Arrays.fill(mat[i], v);
        return mat;
    }

    private static int[][] randomBinary(int n, int m, int pctOne, Random rng) {
        int[][] mat = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                mat[i][j] = (rng.nextInt(100) < pctOne) ? 1 : 0;
        return mat;
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        // TC 00: example 1
        writeTestCase(tc++, 4, 5, new int[][]{
            {1,0,1,0,0},
            {1,0,1,1,1},
            {1,1,1,1,1},
            {1,0,0,1,0}
        });

        // TC 01: example 2 (all zeros)
        writeTestCase(tc++, 2, 3, new int[][]{
            {0,0,0},
            {0,0,0}
        });

        // TC 02: 1x1 = {1}
        writeTestCase(tc++, 1, 1, new int[][]{{1}});

        // TC 03: 1x1 = {0}
        writeTestCase(tc++, 1, 1, new int[][]{{0}});

        // TC 04: 1xM all ones -> area M
        writeTestCase(tc++, 1, 5, new int[][]{{1,1,1,1,1}});

        // TC 05: Nx1 all ones -> area N
        writeTestCase(tc++, 5, 1, new int[][]{{1},{1},{1},{1},{1}});

        // TC 06: 1xM all zeros
        writeTestCase(tc++, 1, 5, new int[][]{{0,0,0,0,0}});

        // TC 07: Nx1 all zeros
        writeTestCase(tc++, 5, 1, new int[][]{{0},{0},{0},{0},{0}});

        // TC 08: all ones 5x5 -> area 25
        writeTestCase(tc++, 5, 5, filled(5, 5, 1));

        // TC 09: all zeros 5x5 -> area 0
        writeTestCase(tc++, 5, 5, filled(5, 5, 0));

        // TC 10: single 1 in middle of zeros
        {
            int[][] mat = filled(5, 5, 0);
            mat[2][2] = 1;
            writeTestCase(tc++, 5, 5, mat);
        }

        // TC 11: single 1 at corner
        {
            int[][] mat = filled(5, 5, 0);
            mat[0][0] = 1;
            writeTestCase(tc++, 5, 5, mat);
        }

        // TC 12: single 0 in middle of ones -> area 4x2 or 2x4 (best 6)
        {
            int[][] mat = filled(3, 3, 1);
            mat[1][1] = 0;
            writeTestCase(tc++, 3, 3, mat);
        }

        // TC 13: identity matrix (only diagonal ones) -> area 1
        writeTestCase(tc++, 5, 5, new int[][]{
            {1,0,0,0,0},
            {0,1,0,0,0},
            {0,0,1,0,0},
            {0,0,0,1,0},
            {0,0,0,0,1}
        });

        // TC 14: checkerboard -> area 1
        {
            int[][] mat = new int[4][4];
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++)
                    mat[i][j] = (i + j) % 2;
            writeTestCase(tc++, 4, 4, mat);
        }

        // TC 15: rows alternating all-1 / all-0
        {
            int[][] mat = new int[6][4];
            for (int i = 0; i < 6; i++)
                Arrays.fill(mat[i], (i % 2 == 0) ? 1 : 0);
            writeTestCase(tc++, 6, 4, mat);
        }

        // TC 16: cols alternating all-1 / all-0
        {
            int[][] mat = new int[4][6];
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 6; j++)
                    mat[i][j] = (j % 2 == 0) ? 1 : 0;
            writeTestCase(tc++, 4, 6, mat);
        }

        // TC 17: L-shape (border ones)
        {
            int[][] mat = filled(4, 4, 0);
            for (int j = 0; j < 4; j++) mat[3][j] = 1;
            for (int i = 0; i < 4; i++) mat[i][0] = 1;
            writeTestCase(tc++, 4, 4, mat);
        }

        // TC 18: left half ones, right half zeros -> area N * (M/2)
        {
            int[][] mat = new int[5][6];
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 3; j++) mat[i][j] = 1;
            writeTestCase(tc++, 5, 6, mat);
        }

        // TC 19: top half ones, bottom half zeros
        {
            int[][] mat = new int[6][5];
            for (int i = 0; i < 3; i++) Arrays.fill(mat[i], 1);
            writeTestCase(tc++, 6, 5, mat);
        }

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 1 + rng.nextInt(6);         // 1..6
            int m = 1 + rng.nextInt(6);         // 1..6
            int pct = 30 + rng.nextInt(41);     // 30..70
            writeTestCase(tc++, n, m, randomBinary(n, m, pct, rng));
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int n = 30 + rng.nextInt(71);       // 30..100
            int m = 30 + rng.nextInt(71);
            int pct = 20 + rng.nextInt(61);     // 20..80
            writeTestCase(tc++, n, m, randomBinary(n, m, pct, rng));
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: 200x200 all ones -> area 40000
        writeTestCase(tc++, 200, 200, filled(200, 200, 1));

        // TC 81: 200x200 all zeros -> area 0
        writeTestCase(tc++, 200, 200, filled(200, 200, 0));

        // TC 82: 200x1 all ones
        writeTestCase(tc++, 200, 1, filled(200, 1, 1));

        // TC 83: 1x200 all ones
        writeTestCase(tc++, 1, 200, filled(1, 200, 1));

        // TC 84: 200x200 checkerboard (max area 1)
        {
            int[][] mat = new int[200][200];
            for (int i = 0; i < 200; i++)
                for (int j = 0; j < 200; j++)
                    mat[i][j] = (i + j) % 2;
            writeTestCase(tc++, 200, 200, mat);
        }

        // TC 85: 200x200 horizontal stripes
        {
            int[][] mat = new int[200][200];
            for (int i = 0; i < 200; i++) Arrays.fill(mat[i], (i % 2 == 0) ? 1 : 0);
            writeTestCase(tc++, 200, 200, mat);
        }

        // TC 86: 200x200 vertical stripes
        {
            int[][] mat = new int[200][200];
            for (int i = 0; i < 200; i++)
                for (int j = 0; j < 200; j++)
                    mat[i][j] = (j % 2 == 0) ? 1 : 0;
            writeTestCase(tc++, 200, 200, mat);
        }

        // TC 87 - 91: 200x200 fully random (density 50%)
        for (int k = 0; k < 5; k++) {
            writeTestCase(tc++, 200, 200, randomBinary(200, 200, 50, rng));
        }

        // TC 92: 200x200 sparse (~10% ones)
        writeTestCase(tc++, 200, 200, randomBinary(200, 200, 10, rng));

        // TC 93: 200x200 dense (~90% ones)
        writeTestCase(tc++, 200, 200, randomBinary(200, 200, 90, rng));

        // TC 94: 200x200 upper-triangular ones
        {
            int[][] mat = new int[200][200];
            for (int i = 0; i < 200; i++)
                for (int j = 0; j < 200; j++)
                    mat[i][j] = (j >= i) ? 1 : 0;
            writeTestCase(tc++, 200, 200, mat);
        }

        // TC 95: 200x200 lower-triangular ones
        {
            int[][] mat = new int[200][200];
            for (int i = 0; i < 200; i++)
                for (int j = 0; j < 200; j++)
                    mat[i][j] = (j <= i) ? 1 : 0;
            writeTestCase(tc++, 200, 200, mat);
        }

        // TC 96: 200x200 ones with a cross of zeros
        {
            int[][] mat = filled(200, 200, 1);
            for (int j = 0; j < 200; j++) mat[100][j] = 0;
            for (int i = 0; i < 200; i++) mat[i][100] = 0;
            writeTestCase(tc++, 200, 200, mat);
        }

        // TC 97: 200x200 ones with zero border
        {
            int[][] mat = filled(200, 200, 1);
            for (int j = 0; j < 200; j++) { mat[0][j] = 0; mat[199][j] = 0; }
            for (int i = 0; i < 200; i++) { mat[i][0] = 0; mat[i][199] = 0; }
            writeTestCase(tc++, 200, 200, mat);
        }

        // TC 98: 200x200 per-row density varying 40..60%
        {
            int[][] mat = new int[200][200];
            for (int i = 0; i < 200; i++) {
                int pct = 40 + rng.nextInt(21);
                for (int j = 0; j < 200; j++)
                    mat[i][j] = (rng.nextInt(100) < pct) ? 1 : 0;
            }
            writeTestCase(tc++, 200, 200, mat);
        }

        // TC 99: 199x199 random (odd shape)
        writeTestCase(tc++, 199, 199, randomBinary(199, 199, 60, rng));

        System.out.println("SUCCESS: Generated " + tc + " test cases for Maximum Rectangle Area with All 1's (DP-55).");
    }
}