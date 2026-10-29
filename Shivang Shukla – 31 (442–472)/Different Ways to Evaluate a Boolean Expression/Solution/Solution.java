import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;

public class Solution {

    static final int MOD = 1000000007;

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[32768];
        private int head = 0;
        private int tail = 0;

        public FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (head >= tail) {
                head = 0;
                tail = in.read(buffer, 0, buffer.length);
                if (tail <= 0) return -1;
            }
            return buffer[head++];
        }

        public String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c = read();

            while (c <= 32) {
                if (c == -1) return null;
                c = read();
            }

            while (c > 32 && c != -1) {
                sb.append((char) c);
                c = read();
            }

            return sb.toString();
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        String s = fs.next();

        if (s == null) {
            out.flush();
            return;
        }

        int N = (s.length() + 1) / 2;

        long[][][] dp = new long[N][N][2];

        for (int i = 0; i < N; i++) {
            if (s.charAt(2 * i) == 'T') {
                dp[i][i][1] = 1;
            } else {
                dp[i][i][0] = 1;
            }
        }

        for (int len = 2; len <= N; len++) {
            for (int i = 0; i + len <= N; i++) {
                int j = i + len - 1;

                for (int k = i; k < j; k++) {
                    long lt = dp[i][k][1];
                    long lf = dp[i][k][0];
                    long rt = dp[k + 1][j][1];
                    long rf = dp[k + 1][j][0];

                    char op = s.charAt(2 * k + 1);

                    if (op == '&') {
                        dp[i][j][1] = (dp[i][j][1] + lt * rt) % MOD;
                        dp[i][j][0] = (dp[i][j][0]
                                + lt * rf + lf * rt + lf * rf) % MOD;
                    } else if (op == '|') {
                        dp[i][j][1] = (dp[i][j][1]
                                + lt * rt + lt * rf + lf * rt) % MOD;
                        dp[i][j][0] = (dp[i][j][0] + lf * rf) % MOD;
                    } else if (op == '^') {
                        dp[i][j][1] = (dp[i][j][1]
                                + lt * rf + lf * rt) % MOD;
                        dp[i][j][0] = (dp[i][j][0]
                                + lt * rt + lf * rf) % MOD;
                    }
                }
            }
        }

        out.println(dp[0][N - 1][1]);
        out.flush();
    }
}