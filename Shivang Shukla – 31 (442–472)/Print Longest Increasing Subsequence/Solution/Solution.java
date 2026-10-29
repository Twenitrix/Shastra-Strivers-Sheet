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
        int[] arr = new int[n];
        int[] dp = new int[n];
        int[] parent = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = fs.nextInt();
            dp[i] = 1;
            parent[i] = i;
        }

        int maxLength = 1;
        int lastIndex = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }

            if (dp[i] > maxLength) {
                maxLength = dp[i];
                lastIndex = i;
            }
        }

        int[] lis = new int[maxLength];
        int index = maxLength - 1;

        while (parent[lastIndex] != lastIndex) {
            lis[index--] = arr[lastIndex];
            lastIndex = parent[lastIndex];
        }

        lis[index] = arr[lastIndex];

        for (int i = 0; i < maxLength; i++) {
            if (i > 0) {
                out.print(" ");
            }
            out.print(lis[i]);
        }

        out.println();
        out.flush();
    }
}