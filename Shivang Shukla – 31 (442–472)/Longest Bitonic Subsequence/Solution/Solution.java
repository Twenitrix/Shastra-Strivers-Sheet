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
            boolean neg = false;

            if (c == '-') {
                neg = true;
                c = read();
            }

            while (c >= '0' && c <= '9') {
                res = res * 10 + (c - '0');
                c = read();
            }

            return neg ? -res : res;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int N = fs.nextInt();
        int[] arr = new int[N];

        for (int i = 0; i < N; i++) {
            arr[i] = fs.nextInt();
        }

        int[] inc = new int[N];
        int[] dec = new int[N];

        for (int i = 0; i < N; i++) {
            inc[i] = 1;

            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    inc[i] = Math.max(inc[i], inc[j] + 1);
                }
            }
        }

        for (int i = N - 1; i >= 0; i--) {
            dec[i] = 1;

            for (int j = N - 1; j > i; j--) {
                if (arr[j] < arr[i]) {
                    dec[i] = Math.max(dec[i], dec[j] + 1);
                }
            }
        }

        int ans = 0;

        for (int i = 0; i < N; i++) {
            ans = Math.max(ans, inc[i] + dec[i] - 1);
        }

        out.println(ans);
        out.flush();
    }
}