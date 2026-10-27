import java.io.*;
import java.util.*;

public class gen1 {

    static final String BASE_DIR = "Testcases1";

    static int id = 0;

    // A test is kept only if its grid size and set of peak positions are new.
    static Set<String> seen = new HashSet<>();

    public static void main(String[] args) throws Exception {

        new File(BASE_DIR + File.separator + "Input").mkdirs();
        new File(BASE_DIR + File.separator + "Output").mkdirs();

        // The two examples from the statement
        add(new int[][]{{1, 4}, {3, 2}});
        add(new int[][]{{10, 20, 15}, {21, 30, 14}, {7, 16, 32}});

        // Every grid up to 3 x 3 with values from {1, 2, 3} and no equal neighbours.
        // This covers every small shape and every peak pattern in it.
        for (int m = 1; m <= 3; m++) {
            for (int n = 1; n <= 3; n++) {
                int cells = m * n;
                int total = 1;
                for (int i = 0; i < cells; i++) total *= 3;
                for (int code = 0; code < total; code++) {
                    int[][] g = new int[m][n];
                    int x = code;
                    for (int i = 0; i < cells; i++) {
                        g[i / n][i % n] = 1 + x % 3;
                        x /= 3;
                    }
                    if (noEqualNeighbours(g)) add(g);
                }
            }
        }

        // One peak placed at every cell of a checkerboard, for sizes 1 to 6.
        // This covers each corner, each border cell, and interior cells.
        for (int m = 1; m <= 6; m++) {
            for (int n = 1; n <= 6; n++) {
                for (int pr = 0; pr < m; pr++) {
                    for (int pc = 0; pc < n; pc++) {
                        int[][] g = checkerboard(m, n);
                        g[pr][pc] = 3;
                        add(g);
                    }
                }
            }
        }

        // Large grids: a peak at each corner, each edge midpoint and the centre
        int big = 500;
        int[][] positions = {
            {0, 0}, {0, big - 1}, {big - 1, 0}, {big - 1, big - 1},
            {0, big / 2}, {big - 1, big / 2}, {big / 2, 0}, {big / 2, big - 1},
            {big / 2, big / 2}
        };
        for (int[] p : positions) {
            int[][] g = checkerboard(big, big);
            g[p[0]][p[1]] = 3;
            add(g);
        }

        // Long single row and column with the peak at either end and in the middle
        for (int[] p : new int[][]{{0, 0}, {0, 499}, {0, 250}}) {
            int[][] g = checkerboard(1, big);
            g[p[0]][p[1]] = 3;
            add(g);
        }
        for (int[] p : new int[][]{{0, 0}, {499, 0}, {250, 0}}) {
            int[][] g = checkerboard(big, 1);
            g[p[0]][p[1]] = 3;
            add(g);
        }

        // Values that only increase toward one border: the peak lies on that border
        add(towardTop(7, 7));
        add(towardTop(4, 6));
        add(towardTop(1, 5));

        // Random small grids with values that repeat often (never side by side)
        Random rnd = new Random(158);
        for (int t = 0; t < 60; t++) {
            int m = 1 + rnd.nextInt(8);
            int n = 1 + rnd.nextInt(8);
            add(randomGrid(rnd, m, n, 2 + rnd.nextInt(19)));
        }

        // Random grids with values up to 100000, the largest allowed
        for (int t = 0; t < 10; t++) {
            int m = 20 + rnd.nextInt(60);
            int n = 20 + rnd.nextInt(60);
            add(randomGrid(rnd, m, n, 100000));
        }

        // Maximum size in both directions
        add(randomGrid(rnd, 500, 500, 100000));
        add(randomGrid(rnd, 500, 500, 100000));
        add(randomGrid(rnd, 500, 1, 100000));
        add(randomGrid(rnd, 1, 500, 100000));

        System.out.println("Generated " + id + " test cases.");
    }

    static boolean noEqualNeighbours(int[][] g) {
        int m = g.length, n = g[0].length;
        for (int r = 0; r < m; r++)
            for (int c = 0; c < n; c++) {
                if (c > 0 && g[r][c] == g[r][c - 1]) return false;
                if (r > 0 && g[r][c] == g[r - 1][c]) return false;
            }
        return true;
    }

    // Alternating 1 and 2: no equal neighbours, and every cell is a local maximum only if raised.
    static int[][] checkerboard(int m, int n) {
        int[][] g = new int[m][n];
        for (int r = 0; r < m; r++)
            for (int c = 0; c < n; c++)
                g[r][c] = (r + c) % 2 == 0 ? 1 : 2;
        return g;
    }

    // Rows strictly increase toward row 0, so the largest values sit on the top border.
    static int[][] towardTop(int m, int n) {
        int[][] g = new int[m][n];
        for (int r = 0; r < m; r++)
            for (int c = 0; c < n; c++)
                g[r][c] = (m - r) * 10 + (c % 2 == 0 ? 1 : 2);
        return g;
    }

    // Random values in [1, maxValue]; a cell is re-rolled if it equals its left or upper neighbour.
    static int[][] randomGrid(Random rnd, int m, int n, int maxValue) {
        int[][] g = new int[m][n];
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                int v;
                do {
                    v = 1 + rnd.nextInt(maxValue);
                } while ((c > 0 && g[r][c - 1] == v) || (r > 0 && g[r - 1][c] == v));
                g[r][c] = v;
            }
        }
        return g;
    }

    // Every cell strictly greater than all in-bounds neighbours (the outside is -1).
    static List<int[]> allPeaks(int[][] g) {
        int m = g.length, n = g[0].length;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        List<int[]> peaks = new ArrayList<>();
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                boolean peak = true;
                for (int k = 0; k < 4 && peak; k++) {
                    int nr = r + dr[k], nc = c + dc[k];
                    if (nr < 0 || nc < 0 || nr >= m || nc >= n) continue;
                    if (g[nr][nc] >= g[r][c]) peak = false;
                }
                if (peak) peaks.add(new int[]{r, c});
            }
        }
        return peaks;
    }

    // Writes the grid unless a test with the same size and peak positions was already written.
    static boolean add(int[][] g) throws Exception {

        int m = g.length, n = g[0].length;
        if (m < 1 || m > 500 || n < 1 || n > 500) throw new IllegalStateException("size out of range");
        for (int[] row : g) {
            if (row.length != n) throw new IllegalStateException("not rectangular");
            for (int x : row) {
                if (x < 1 || x > 100000) throw new IllegalStateException("value out of range");
            }
        }
        if (!noEqualNeighbours(g)) throw new IllegalStateException("equal neighbours");

        List<int[]> peaks = allPeaks(g);
        StringBuilder sig = new StringBuilder(m + "x" + n + "|");
        for (int[] p : peaks) sig.append(p[0]).append(',').append(p[1]).append(';');
        if (!seen.add(sig.toString())) {
            return false;
        }

        String inputFile = BASE_DIR + File.separator + "Input" + File.separator
                + String.format("input%03d.txt", id);
        String outputFile = BASE_DIR + File.separator + "Output" + File.separator
                + String.format("output%03d.txt", id);

        try (PrintWriter in = new PrintWriter(new FileWriter(inputFile));
             PrintWriter out = new PrintWriter(new FileWriter(outputFile))) {

            in.print(m + " " + n + "\r\n");
            for (int[] row : g) {
                StringBuilder sb = new StringBuilder();
                for (int c = 0; c < n; c++) {
                    if (c > 0) sb.append(' ');
                    sb.append(row[c]);
                }
                in.print(sb + "\r\n");
            }
            for (int[] p : peaks) {
                out.print(p[0] + " " + p[1] + "\r\n");
            }
        }

        id++;
        return true;
    }
}
