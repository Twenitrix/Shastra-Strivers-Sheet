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
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int N = fs.nextInt();
        int[] nums = new int[N];

        for (int i = 0; i < N; i++) {
            nums[i] = fs.nextInt();
        }

        Arrays.sort(nums);

        int[] dp = new int[N];
        int[] prev = new int[N];

        Arrays.fill(dp, 1);

        for (int i = 0; i < N; i++) {
            prev[i] = i;

            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
        }

        int maxLen = 0;
        int last = 0;

        for (int i = 0; i < N; i++) {
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                last = i;
            }
        }

        int[] ans = new int[maxLen];
        int index = maxLen - 1;

        while (prev[last] != last) {
            ans[index--] = nums[last];
            last = prev[last];
        }

        ans[index] = nums[last];

        for (int i = 0; i < maxLen; i++) {
            out.print(ans[i]);

            if (i < maxLen - 1) {
                out.print(" ");
            }
        }

        out.println();
        out.flush();
    }
}