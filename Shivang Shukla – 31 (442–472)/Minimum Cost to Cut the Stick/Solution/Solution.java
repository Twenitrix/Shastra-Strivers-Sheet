import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.Arrays;

public class Solution {
    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int num = 0;
            while (c > ' ') {
                num = num * 10 + c - '0';
                c = read();
            }
            return num;
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(
                new BufferedOutputStream(System.out)
        );

        int length = fs.nextInt();
        int n = fs.nextInt();

        int[] cuts = new int[n + 2];
        cuts[0] = 0;
        cuts[n + 1] = length;

        for (int i = 1; i <= n; i++) {
            cuts[i] = fs.nextInt();
        }

        Arrays.sort(cuts);

        long[][] dp = new long[n + 2][n + 2];

        for (int i = n; i >= 1; i--) {
            for (int j = i; j <= n; j++) {
                long minCost = Long.MAX_VALUE;

                for (int k = i; k <= j; k++) {
                    long cost = cuts[j + 1] - cuts[i - 1]
                            + dp[i][k - 1]
                            + dp[k + 1][j];

                    minCost = Math.min(minCost, cost);
                }

                dp[i][j] = minCost;
            }
        }

        out.println(dp[1][n]);
        out.flush();
    }
}