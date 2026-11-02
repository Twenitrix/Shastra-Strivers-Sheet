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

        if (s.length() % 2 != 0) {
            out.println(-1);
            out.flush();
            return;
        }

        int open = 0;
        int close = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '{') {
                open++;
            } else if (ch == '}') {
                if (open > 0) {
                    open--;
                } else {
                    close++;
                }
            }
        }

        int result = (open + 1) / 2 + (close + 1) / 2;

        out.println(result);
        out.flush();
    }
}