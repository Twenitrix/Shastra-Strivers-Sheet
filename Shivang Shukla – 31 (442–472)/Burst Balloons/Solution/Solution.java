import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;

public class Solution {

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

        public int nextInt() throws IOException {
            int c = read();

            while (c <= 32) {
                if (c == -1) return -1;
                c = read();
            }

            int res = 0;

            while (c >= '0' && c <= '9') {
                res = res * 10 + (c - '0');
                c = read();
            }

            return res;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int N = fs.nextInt();

        int[] nums = new int[N + 2];
        nums[0] = 1;
        nums[N + 1] = 1;

        for (int i = 1; i <= N; i++) {
            nums[i] = fs.nextInt();
        }

        int[][] dp = new int[N + 2][N + 2];

        for (int len = 2; len <= N + 1; len++) {
            for (int left = 0; left + len <= N + 1; left++) {
                int right = left + len;

                for (int k = left + 1; k < right; k++) {
                    int coins = dp[left][k]
                            + dp[k][right]
                            + nums[left] * nums[k] * nums[right];

                    dp[left][right] = Math.max(dp[left][right], coins);
                }
            }
        }

        out.println(dp[0][N + 1]);
        out.flush();
    }
}