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

        String s = "1";

        for (int i = 2; i <= N; i++) {
            StringBuilder next = new StringBuilder();
            int j = 0;

            while (j < s.length()) {
                char ch = s.charAt(j);
                int count = 0;

                while (j < s.length() && s.charAt(j) == ch) {
                    count++;
                    j++;
                }

                next.append(count);
                next.append(ch);
            }

            s = next.toString();
        }

        out.println(s);
        out.flush();
    }
}