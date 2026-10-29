import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;

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
            int sign = 1;

            if (c == '-') {
                sign = -1;
                c = read();
            }

            while (c > ' ') {
                num = num * 10 + c - '0';
                c = read();
            }

            return num * sign;
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(
                new BufferedOutputStream(System.out)
        );

        int n = fs.nextInt();
        int k = fs.nextInt();

        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = fs.nextInt();
        }

        int[] dp = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            int maxValue = 0;
            int maxSum = 0;

            for (int j = i; j < Math.min(n, i + k); j++) {
                maxValue = Math.max(maxValue, arr[j]);

                int length = j - i + 1;
                int sum = maxValue * length + dp[j + 1];

                maxSum = Math.max(maxSum, sum);
            }

            dp[i] = maxSum;
        }

        out.println(dp[0]);
        out.flush();
    }
}