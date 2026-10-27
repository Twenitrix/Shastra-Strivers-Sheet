import java.io.*;
import java.util.*;

public class gen1 {

    static final String BASE_DIR = "Testcases1";
    static final int TARGET = 110;

    static int id = 0;

    // Two cases are the same only if their whole matrix is identical, so every kept case is unique.
    static Set<String> seen = new HashSet<>();

    public static void main(String[] args) throws Exception {

        new File(BASE_DIR + File.separator + "Input").mkdirs();
        new File(BASE_DIR + File.separator + "Output").mkdirs();

        // Examples and the "Now Your Turn" input
        add(new int[][]{{1, 4, 9}, {2, 5, 6}, {3, 7, 8}});
        add(new int[][]{{1, 3, 8}, {2, 3, 4}, {1, 2, 5}});
        add(new int[][]{{1, 4, 15}, {2, 5, 6}, {3, 8, 11}});

        // Smallest matrix, and single rows and columns
        add(new int[][]{{7}});
        add(new int[][]{{1, 2, 3, 4, 5}});
        add(new int[][]{{5, 5, 5}});
        add(new int[][]{{1}, {2}, {3}, {4}, {5}});
        add(new int[][]{{2}, {2}, {2}});

        // Median equals the smallest value, the largest value, and a middle value
        add(new int[][]{{1, 1, 1}, {1, 1, 1}, {1, 5, 9}});
        add(new int[][]{{9, 9, 9}, {9, 9, 9}, {1, 2, 3}});
        add(new int[][]{{1, 1, 1}, {1, 2, 2}, {3, 4, 5}});

        // Values at the limits of the constraints
        add(new int[][]{{1000000000, 1000000000, 1000000000},
                        {1000000000, 1000000000, 1000000000},
                        {1000000000, 1000000000, 1000000000}});
        add(new int[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1000000000}});
        add(new int[][]{{1, 1000000000, 1000000000}, {1000000000, 1000000000, 1000000000}, {1000000000, 1000000000, 1000000000}});

        // Largest shapes allowed: N*M odd and at most 10^6
        add(randomMatrix(new Random(1), 999, 1001, 1000000000));
        add(randomMatrix(new Random(2), 99999, 9, 1000000000));
        add(randomMatrix(new Random(3), 9, 99999, 1000000000));
        add(randomMatrix(new Random(4), 1, 99999, 1000000000));
        add(randomMatrix(new Random(5), 99999, 1, 1000000000));

        // Random matrices of odd size, with small and large value ranges, until the target is reached.
        // Medium and small shapes are generated first, then the rest of the target count is filled.
        int[][] shapes = {
            {1, 1}, {1, 3}, {3, 1}, {1, 5}, {5, 1}, {1, 7}, {7, 1},
            {3, 3}, {3, 5}, {5, 3}, {5, 5}, {3, 7}, {7, 3}, {7, 7},
            {9, 9}, {1, 9}, {9, 1}, {11, 11}, {5, 9}, {9, 5}, {13, 15},
            {21, 31}, {31, 21}, {101, 101}, {333, 333}
        };
        int[] ranges = {3, 10, 100, 1000000000};
        Random rnd = new Random(159);
        int attempts = 0;
        while (id < TARGET && attempts < 100000) {
            attempts++;
            int[] s = shapes[rnd.nextInt(shapes.length)];
            int m = s[0], n = s[1];
            if (m * n > 10000 && rnd.nextInt(4) != 0) continue;
            int range = ranges[rnd.nextInt(ranges.length)];
            add(randomMatrix(rnd, m, n, range));
        }

        System.out.println("Generated " + id + " test cases.");
    }

    // Each row is sorted, as the problem requires. Values are in [1, maxValue].
    static int[][] randomMatrix(Random rnd, int m, int n, int maxValue) {
        int[][] mat = new int[m][n];
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                mat[r][c] = 1 + rnd.nextInt(maxValue);
            }
            Arrays.sort(mat[r]);
        }
        return mat;
    }

    static boolean rowsSorted(int[][] mat) {
        for (int[] row : mat)
            for (int c = 1; c < row.length; c++)
                if (row[c] < row[c - 1]) return false;
        return true;
    }

    // Reference: flatten, sort, and take the middle element (total is odd).
    static int median(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[] flat = new int[m * n];
        int k = 0;
        for (int[] row : mat)
            for (int x : row)
                flat[k++] = x;
        Arrays.sort(flat);
        return flat[flat.length / 2];
    }

    static void add(int[][] mat) throws Exception {

        int m = mat.length, n = mat[0].length;
        if (m < 1 || m > 100000 || n < 1 || n > 100000) throw new IllegalStateException("size out of range");
        if ((long) m * n > 1000000L || ((long) m * n) % 2 == 0) throw new IllegalStateException("N*M invalid");
        for (int[] row : mat) {
            if (row.length != n) throw new IllegalStateException("not rectangular");
            for (int x : row) {
                if (x < 1 || x > 1000000000) throw new IllegalStateException("value out of range");
            }
        }
        if (!rowsSorted(mat)) throw new IllegalStateException("row not sorted");

        // Exact-content signature: identical matrices are written only once
        StringBuilder sig = new StringBuilder(m + "x" + n + ":");
        for (int[] row : mat) for (int x : row) sig.append(x).append(',');
        if (!seen.add(sig.toString())) {
            return;
        }

        String inputFile = BASE_DIR + File.separator + "Input" + File.separator
                + String.format("input%03d.txt", id);
        String outputFile = BASE_DIR + File.separator + "Output" + File.separator
                + String.format("output%03d.txt", id);

        try (PrintWriter in = new PrintWriter(new FileWriter(inputFile));
             PrintWriter out = new PrintWriter(new FileWriter(outputFile))) {

            in.print(m + " " + n + "\r\n");
            for (int[] row : mat) {
                StringBuilder sb = new StringBuilder();
                for (int c = 0; c < n; c++) {
                    if (c > 0) sb.append(' ');
                    sb.append(row[c]);
                }
                in.print(sb + "\r\n");
            }
            out.print(median(mat) + "\r\n");
        }

        id++;
    }
}
