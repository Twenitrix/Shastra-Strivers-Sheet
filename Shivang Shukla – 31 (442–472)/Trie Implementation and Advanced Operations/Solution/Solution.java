import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;

public class Solution {
    static class TrieNode {
        TrieNode[] child = new TrieNode[26];
        int prefixCount = 0;
        int wordCount = 0;
    }

    static class Trie {
        TrieNode root = new TrieNode();

        void insert(String word) {
            TrieNode node = root;

            for (int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 'a';

                if (node.child[index] == null) {
                    node.child[index] = new TrieNode();
                }

                node = node.child[index];
                node.prefixCount++;
            }

            node.wordCount++;
        }

        int countWordsEqualTo(String word) {
            TrieNode node = root;

            for (int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 'a';

                if (node.child[index] == null) {
                    return 0;
                }

                node = node.child[index];
            }

            return node.wordCount;
        }

        int countWordsStartingWith(String prefix) {
            TrieNode node = root;

            for (int i = 0; i < prefix.length(); i++) {
                int index = prefix.charAt(i) - 'a';

                if (node.child[index] == null) {
                    return 0;
                }

                node = node.child[index];
            }

            return node.prefixCount;
        }

        void erase(String word) {
            if (countWordsEqualTo(word) == 0) {
                return;
            }

            TrieNode node = root;
            node = root;

            for (int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 'a';
                node.child[index].prefixCount--;
                node = node.child[index];
            }

            node.wordCount--;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );
        PrintWriter out = new PrintWriter(
                new BufferedOutputStream(System.out)
        );

        Trie trie = new Trie();
        int q = Integer.parseInt(br.readLine().trim());

        for (int i = 0; i < q; i++) {
            String[] input = br.readLine().trim().split("\\s+");
            int type = Integer.parseInt(input[0]);
            String word = input[1];

            if (type == 1) {
                trie.insert(word);
            } else if (type == 2) {
                trie.erase(word);
            } else if (type == 3) {
                out.println(trie.countWordsEqualTo(word) > 0 ? "YES" : "NO");
            } else if (type == 4) {
                out.println(trie.countWordsStartingWith(word));
            }
        }

        out.flush();
    }
}