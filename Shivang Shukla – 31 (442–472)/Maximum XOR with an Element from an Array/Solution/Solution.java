import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

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

    static class BinaryTrie {
        TrieNode root = new TrieNode();

        void insert(int num) {
            TrieNode node = root;

            for (int bit = 31; bit >= 0; bit--) {
                int b = (num >>> bit) & 1;

                if (node.child[b] == null) {
                    node.child[b] = new TrieNode();
                }

                node = node.child[b];
            }
        }

        int maxXor(int num) {
            TrieNode node = root;
            int result = 0;

            for (int bit = 31; bit >= 0; bit--) {
                int b = (num >>> bit) & 1;
                int opposite = 1 - b;

                if (node.child[opposite] != null) {
                    result |= (1 << bit);
                    node = node.child[opposite];
                } else if (node.child[b] != null) {
                    node = node.child[b];
                } else {
                    return -1;
                }
            }

            return result;
        }
    }

    static class Query {
        int x;
        int limit;
        int index;

        Query(int x, int limit, int index) {
            this.x = x;
            this.limit = limit;
            this.index = index;
        }
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

        int q = fs.nextInt();
        Query[] queries = new Query[q];

        for (int i = 0; i < q; i++) {
            int x = fs.nextInt();
            int limit = fs.nextInt();
            queries[i] = new Query(x, limit, i);
        }

        Arrays.sort(arr);

        Arrays.sort(queries, (a, b) ->
                Integer.compare(a.limit, b.limit)
        );

        int[] answers = new int[q];
        BinaryTrie trie = new BinaryTrie();
        int j = 0;

        for (int i = 0; i < q; i++) {
            while (j < n && arr[j] <= queries[i].limit) {
                trie.insert(arr[j]);
                j++;
            }

            answers[queries[i].index] =
                    trie.maxXor(queries[i].x);
        }

        for (int i = 0; i < q; i++) {
            out.println(answers[i]);
        }

        out.flush();
    }
}