import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;

public class Solution {

    static final long MOD = 1000000007;
    static final long BASE = 31;

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

        long hash = 0;
        long power = 1;

        for (int i = 0; i < s.length(); i++) {
            int value = s.charAt(i) - 'a' + 1;

            hash = (hash + value * power) % MOD;
            power = (power * BASE) % MOD;
        }

        out.println(hash);
        out.flush();
    }
}