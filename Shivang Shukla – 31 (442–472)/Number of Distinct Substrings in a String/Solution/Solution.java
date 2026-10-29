import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;

public class Solution {
    static class TrieNode {
        TrieNode[] child = new TrieNode[26];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );
        PrintWriter out = new PrintWriter(
                new BufferedOutputStream(System.out)
        );

        String s = br.readLine().trim();
        TrieNode root = new TrieNode();
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            TrieNode node = root;

            for (int j = i; j < s.length(); j++) {
                int index = s.charAt(j) - 'a';

                if (node.child[index] == null) {
                    node.child[index] = new TrieNode();
                    count++;
                }

                node = node.child[index];
            }
        }

        out.println(count);
        out.flush();
    }
}