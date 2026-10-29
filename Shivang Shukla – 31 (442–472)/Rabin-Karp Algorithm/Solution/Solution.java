import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;

public class Solution {
    static final long MOD = 1000000007L;
    static final long BASE = 256L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );
        PrintWriter out = new PrintWriter(
                new BufferedOutputStream(System.out)
        );

        String text = br.readLine();
        String pattern = br.readLine();

        int n = text.length();
        int m = pattern.length();

        if (m > n) {
            out.println(-1);
            out.flush();
            return;
        }

        long patternHash = 0;
        long windowHash = 0;
        long power = 1;

        for (int i = 0; i < m - 1; i++) {
            power = (power * BASE) % MOD;
        }

        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * BASE
                    + pattern.charAt(i)) % MOD;

            windowHash = (windowHash * BASE
                    + text.charAt(i)) % MOD;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patternHash == windowHash
                    && text.regionMatches(i, pattern, 0, m)) {
                out.println(i);
                out.flush();
                return;
            }

            if (i < n - m) {
                windowHash = (windowHash
                        - text.charAt(i) * power % MOD + MOD) % MOD;

                windowHash = (windowHash * BASE
                        + text.charAt(i + m)) % MOD;
            }
        }

        out.println(-1);
        out.flush();
    }
}