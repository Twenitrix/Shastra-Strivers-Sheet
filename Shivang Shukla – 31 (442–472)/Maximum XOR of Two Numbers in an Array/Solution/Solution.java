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
            while (c > ' ') {
                num = num * 10 + c - '0';
                c = read();
            }
            return num;
        }
    }

    static class TrieNode {
        TrieNode[] child = new TrieNode[2];
    }

    static void insert(TrieNode root, int num) {
        TrieNode node = root;

        for (int bit = 31; bit >= 0; bit--) {
            int b = (num >>> bit) & 1;

            if (node.child[b] == null) {
                node.child[b] = new TrieNode();
            }

            node = node.child[b];
        }
    }

    static int findMaxXor(TrieNode root, int num) {
        TrieNode node = root;
        int xor = 0;

        for (int bit = 31; bit >= 0; bit--) {
            int b = (num >>> bit) & 1;
            int opposite = 1 - b;

            if (node.child[opposite] != null) {
                xor |= (1 << bit);
                node = node.child[opposite];
            } else {
                node = node.child[b];
            }
        }

        return xor;
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(
                new BufferedOutputStream(System.out)
        );

        int n = fs.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = fs.nextInt();
        }

        TrieNode root = new TrieNode();

        for (int i = 0; i < n; i++) {
            insert(root, arr[i]);
        }

        int maxXor = 0;

        for (int i = 0; i < n; i++) {
            maxXor = Math.max(maxXor, findMaxXor(root, arr[i]));
        }

        out.println(maxXor);
        out.flush();
    }
}