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

        String s = br.readLine();
        int n = s.length();

        StringBuilder reversed = new StringBuilder(s).reverse();
        String combined = s + "#" + reversed;

        int[] lps = new int[combined.length()];

        for (int i = 1; i < combined.length(); i++) {
            int j = lps[i - 1];

            while (j > 0 && combined.charAt(i) != combined.charAt(j)) {
                j = lps[j - 1];
            }

            if (combined.charAt(i) == combined.charAt(j)) {
                j++;
            }

            lps[i] = j;
        }

        int prefixLength = lps[combined.length() - 1];
        String result = reversed.substring(0, n - prefixLength) + s;

        out.println(result);
        out.flush();
    }
}