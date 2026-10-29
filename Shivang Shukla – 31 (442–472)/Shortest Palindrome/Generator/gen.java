import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: KMP-based longest palindromic prefix.
    // Trick: build `s + '#' + reverse(s)`, compute LPS array.
    // The last value of LPS = length of longest palindromic prefix of s.
    // Shortest palindrome = reverse(s[L..]) + s, where L = palindromic prefix length.
    private static String solve(String s) {
        int n = s.length();
        StringBuilder sb = new StringBuilder(s);
        sb.append('#');
        for (int i = n - 1; i >= 0; i--) sb.append(s.charAt(i));

        int[] lps = new int[sb.length()];
        for (int i = 1; i < sb.length(); i++) {
            int j = lps[i - 1];
            while (j > 0 && sb.charAt(i) != sb.charAt(j)) {
                j = lps[j - 1];
            }
            if (sb.charAt(i) == sb.charAt(j)) j++;
            lps[i] = j;
        }

        int L = lps[sb.length() - 1];   // longest palindromic prefix length
        StringBuilder ans = new StringBuilder();
        for (int i = n - 1; i >= L; i--) ans.append(s.charAt(i));
        ans.append(s);
        return ans.toString();
    }

    private static void writeTestCase(int index, String s) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir  = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        File inFile  = new File(inputDir,  String.format("input%02d.txt",  index));
        File outFile = new File(outputDir, String.format("output%02d.txt", index));

        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(s);
        }

        String ans = solve(s);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    private static String randomString(int len, Random rng) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append((char) ('a' + rng.nextInt(26)));
        return sb.toString();
    }

    private static String randomSmallAlpha(int len, int k, Random rng) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append((char) ('a' + rng.nextInt(k)));
        return sb.toString();
    }

    private static String repeat(char c, int len) {
        char[] arr = new char[len];
        Arrays.fill(arr, c);
        return new String(arr);
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        // TC 00: example 1
        writeTestCase(tc++, "aacecaaa");

        // TC 01: example 2
        writeTestCase(tc++, "abcd");

        // TC 02: n = 1, single char (already palindrome)
        writeTestCase(tc++, "a");

        // TC 03: n = 1, another char
        writeTestCase(tc++, "z");

        // TC 04: n = 2, "aa" (already palindrome)
        writeTestCase(tc++, "aa");

        // TC 05: n = 2, "ab" → add 'b' at front → "bab"
        writeTestCase(tc++, "ab");

        // TC 06: n = 3, "aaa" (already palindrome)
        writeTestCase(tc++, "aaa");

        // TC 07: n = 3, "aba" (already palindrome)
        writeTestCase(tc++, "aba");

        // TC 08: n = 3, "abc" → "cbabc"
        writeTestCase(tc++, "abc");

        // TC 09: n = 4, "abba" (already palindrome)
        writeTestCase(tc++, "abba");

        // TC 10: n = 4, "abab" → palindromic prefix "aba" → add 'b' → "babab"
        writeTestCase(tc++, "abab");

        // TC 11: n = 4, "aabb" → prefix "aa" → "bbaabb"
        writeTestCase(tc++, "aabb");

        // TC 12: n = 5, "abcba" (palindrome)
        writeTestCase(tc++, "abcba");

        // TC 13: n = 5, "abcde" → no pal prefix beyond 'a' → "edcbabcde"
        writeTestCase(tc++, "abcde");

        // TC 14: n = 6, "abcdef" → "fedcbabcdef"
        writeTestCase(tc++, "abcdef");

        // TC 15: n = 6, "aabbaa" (palindrome)
        writeTestCase(tc++, "aabbaa");

        // TC 16: n = 7, "aacecaa" → pal prefix of length 7? No, "aacecaa" is palindrome → 0 added
        writeTestCase(tc++, "aacecaa");

        // TC 17: n = 7, "aacecaaa" (example 1 again, pal prefix "aacecaa")
        writeTestCase(tc++, "aacecaaa");

        // TC 18: n = 8, "abcddcba" (palindrome)
        writeTestCase(tc++, "abcddcba");

        // TC 19: n = 8, "abcddcbb" → prefix "abcddcb" ends in b not a... let solver handle
        writeTestCase(tc++, "abcddcbb");

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int len = 1 + rng.nextInt(15);           // 1..15
            int alpha = 1 + rng.nextInt(4);          // 1..4 alphabet
            writeTestCase(tc++, randomSmallAlpha(len, alpha, rng));
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int len = 100 + rng.nextInt(901);        // 100..1000
            writeTestCase(tc++, randomString(len, rng));
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 1e5, all 'a' (already palindrome)
        writeTestCase(tc++, repeat('a', 100000));

        // TC 81: n = 1e5, all 'z' (already palindrome)
        writeTestCase(tc++, repeat('z', 100000));

        // TC 82: n = 1e5, "ab" alternating (palindromic prefix length 1)
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100000; i++) sb.append((i % 2 == 0) ? 'a' : 'b');
            writeTestCase(tc++, sb.toString());
        }

        // TC 83: n = 1e5, ascending alphabet cycle
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100000; i++) sb.append((char) ('a' + (i % 26)));
            writeTestCase(tc++, sb.toString());
        }

        // TC 84: n = 1e5, palindrome by mirroring (even length)
        {
            String half = randomString(50000, rng);
            StringBuilder sb = new StringBuilder(half);
            sb.append(new StringBuilder(half).reverse());
            writeTestCase(tc++, sb.toString());
        }

        // TC 85: n = 1e5, odd-length palindrome
        {
            String half = randomString(49999, rng);
            char center = (char) ('a' + rng.nextInt(26));
            StringBuilder sb = new StringBuilder(half);
            sb.append(center);
            sb.append(new StringBuilder(half).reverse());
            writeTestCase(tc++, sb.toString());
        }

        // TC 86: n = 1e5, worst-case (no palindromic prefix > 1) — "abcde...xyz" repeated
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100000; i++) sb.append((char) ('a' + (i % 26)));
            writeTestCase(tc++, sb.toString());
        }

        // TC 87 - 91: n = 1e5, fully random
        for (int k = 0; k < 5; k++) {
            writeTestCase(tc++, randomString(100000, rng));
        }

        // TC 92: n = 1e5, small alphabet {a,b}
        writeTestCase(tc++, randomSmallAlpha(100000, 2, rng));

        // TC 93: n = 1e5, small alphabet {a,b,c}
        writeTestCase(tc++, randomSmallAlpha(100000, 3, rng));

        // TC 94: n = 99999 (odd), random
        writeTestCase(tc++, randomString(99999, rng));

        // TC 95: n = 1e5, "abab..." (period 2)
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 50000; i++) sb.append("ab");
            writeTestCase(tc++, sb.toString());
        }

        // TC 96: n = 1e5, "abc" repeated
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 33333; i++) sb.append("abc");
            sb.append('a');
            writeTestCase(tc++, sb.toString());
        }

        // TC 97: n = 1e5, "aabbcc" repeated
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16666; i++) sb.append("aabbcc");
            sb.append("aabb");
            writeTestCase(tc++, sb.toString());
        }

        // TC 98: n = 1e5, near-palindrome (one char different in middle)
        {
            String half = randomString(50000, rng);
            StringBuilder sb = new StringBuilder(half);
            sb.append(new StringBuilder(half).reverse());
            sb.setCharAt(50000, (char) ('a' + ((sb.charAt(50000) - 'a' + 1) % 26)));
            writeTestCase(tc++, sb.toString());
        }

        // TC 99: n = 1e5, "a" * 99999 + "b" (almost palindrome)
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 99999; i++) sb.append('a');
            sb.append('b');
            writeTestCase(tc++, sb.toString());
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Shortest Palindrome.");
    }
}