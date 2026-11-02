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
        int[] length = new int[n];
        int[] count = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = fs.nextInt();
            length[i] = 1;
            count[i] = 1;
        }

        int maxLength = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    if (length[j] + 1 > length[i]) {
                        length[i] = length[j] + 1;
                        count[i] = count[j];
                    } else if (length[j] + 1 == length[i]) {
                        count[i] += count[j];
                    }
                }
            }

            maxLength = Math.max(maxLength, length[i]);
        }

        int result = 0;

        for (int i = 0; i < n; i++) {
            if (length[i] == maxLength) {
                result += count[i];
            }
        }

        out.println(result);
        out.flush();
    }
}