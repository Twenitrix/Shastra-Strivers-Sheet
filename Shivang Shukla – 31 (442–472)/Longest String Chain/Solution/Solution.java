import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.Arrays;

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

        int N = fs.nextInt();
        String[] words = new String[N];

        for (int i = 0; i < N; i++) {
            words[i] = fs.next();
        }

        Arrays.sort(words, (a, b) -> a.length() - b.length());

        int[] dp = new int[N];
        Arrays.fill(dp, 1);

        int ans = 1;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (words[j].length() + 1 != words[i].length()) {
                    continue;
                }

                int p = 0;
                int q = 0;

                while (p < words[j].length() && q < words[i].length()) {
                    if (words[j].charAt(p) == words[i].charAt(q)) {
                        p++;
                    }
                    q++;
                }

                if (p == words[j].length()) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            ans = Math.max(ans, dp[i]);
        }

        out.println(ans);
        out.flush();
    }
}