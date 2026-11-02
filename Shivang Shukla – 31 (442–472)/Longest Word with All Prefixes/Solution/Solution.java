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

    static class Node {
        Node[] child = new Node[26];
        boolean isEnd;
    }

    static void insert(Node root, String word) {
        Node curr = root;

        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';

            if (curr.child[idx] == null) {
                curr.child[idx] = new Node();
            }

            curr = curr.child[idx];
        }

        curr.isEnd = true;
    }

    static boolean hasAllPrefixes(Node root, String word) {
        Node curr = root;

        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';

            curr = curr.child[idx];

            if (curr == null || !curr.isEnd) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int N = fs.nextInt();
        String[] words = new String[N];

        for (int i = 0; i < N; i++) {
            words[i] = fs.next();
        }

        Node root = new Node();

        for (int i = 0; i < N; i++) {
            insert(root, words[i]);
        }

        String ans = "";

        for (int i = 0; i < N; i++) {
            if (hasAllPrefixes(root, words[i])) {
                if (words[i].length() > ans.length()
                        || (words[i].length() == ans.length()
                        && words[i].compareTo(ans) < 0)) {
                    ans = words[i];
                }
            }
        }

        out.println(ans.isEmpty() ? "None" : ans);
        out.flush();
    }
}