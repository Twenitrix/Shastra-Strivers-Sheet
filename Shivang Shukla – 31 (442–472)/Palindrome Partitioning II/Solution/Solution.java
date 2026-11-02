import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );
        PrintWriter out = new PrintWriter(
                new BufferedOutputStream(System.out)
        );

        String s = br.readLine().trim();
        int n = s.length();

        boolean[][] palindrome = new boolean[n][n];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (j - i <= 1 || palindrome[i + 1][j - 1]) {
                        palindrome[i][j] = true;
                    }
                }
            }
        }

        int[] dp = new int[n + 1];
        dp[n] = 0;

        for (int i = n - 1; i >= 0; i--) {
            dp[i] = Integer.MAX_VALUE;

            for (int j = i; j < n; j++) {
                if (palindrome[i][j]) {
                    dp[i] = Math.min(dp[i], 1 + dp[j + 1]);
                }
            }
        }

        out.println(dp[0] - 1);
        out.flush();
    }
}