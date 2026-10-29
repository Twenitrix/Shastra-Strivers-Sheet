import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: O(n^2) DP with precomputed palindrome table.
    // isPal[i][j] = true if S[i..j] is a palindrome.
    // dp[i] = min cuts to partition S[0..i] into palindromes.
    //   dp[i] = 0 if S[0..i] is palindrome, else
    //   dp[i] = min over j in [0..i-1] with isPal[j+1][i] of dp[j] + 1.
    private static long solve(String s) {
        int n = s.length();
        if (n <= 1) return 0;

        boolean[][] isPal = new boolean[n][n];
        for (int i = 0; i < n; i++) isPal[i][i] = true;
        for (int i = 0; i + 1 < n; i++) isPal[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                isPal[i][j] = (s.charAt(i) == s.charAt(j)) && isPal[i + 1][j - 1];
            }
        }

        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            if (isPal[0][i]) {
                dp[i] = 0;
                continue;
            }
            for (int j = 0; j < i; j++) {
                if (isPal[j + 1][i] && dp[j] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[n - 1];
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

        long ans = solve(s);
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
        writeTestCase(tc++, "aab");

        // TC 01: example 2
        writeTestCase(tc++, "racecar");

        // TC 02: n = 1, single char
        writeTestCase(tc++, "a");

        // TC 03: n = 1, another single char
        writeTestCase(tc++, "z");

        // TC 04: n = 2, "aa" — palindrome -> 0 cuts
        writeTestCase(tc++, "aa");

        // TC 05: n = 2, "ab" — 1 cut
        writeTestCase(tc++, "ab");

        // TC 06: n = 3, "aaa" — palindrome -> 0
        writeTestCase(tc++, "aaa");

        // TC 07: n = 3, "abc" — 2 cuts
        writeTestCase(tc++, "abc");

        // TC 08: n = 3, "aba" — palindrome -> 0
        writeTestCase(tc++, "aba");

        // TC 09: n = 3, "aab" — 1 cut
        writeTestCase(tc++, "aab");

        // TC 10: n = 3, "baa" — 1 cut
        writeTestCase(tc++, "baa");

        // TC 11: n = 4, "abba" — palindrome
        writeTestCase(tc++, "abba");

        // TC 12: n = 4, "abab" — 1 cut ("aba|b" or "a|bab")
        writeTestCase(tc++, "abab");

        // TC 13: n = 4, "aabb" — 1 cut ("aa|bb")
        writeTestCase(tc++, "aabb");

        // TC 14: n = 5, "abcba" — palindrome
        writeTestCase(tc++, "abcba");

        // TC 15: n = 5, "abcde" — 4 cuts (all single chars)
        writeTestCase(tc++, "abcde");

        // TC 16: n = 6, "aabbaa" — palindrome
        writeTestCase(tc++, "aabbaa");

        // TC 17: n = 6, "abcdef" — 5 cuts
        writeTestCase(tc++, "abcdef");

        // TC 18: "aab" (LeetCode classic)
        writeTestCase(tc++, "aab");

        // TC 19: "abacdc" — known answer 3? let's test
        writeTestCase(tc++, "abacdc");

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int len = 1 + rng.nextInt(20);           // 1..20
            int alpha = 2 + rng.nextInt(5);          // 2..6 alphabet size
            writeTestCase(tc++, randomSmallAlpha(len, alpha, rng));
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int len = 100 + rng.nextInt(901);        // 100..1000
            writeTestCase(tc++, randomString(len, rng));
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 2000, all 'a' — palindrome -> 0
        writeTestCase(tc++, repeat('a', 2000));

        // TC 81: n = 2000, all 'z'
        writeTestCase(tc++, repeat('z', 2000));

        // TC 82: n = 2000, alternating "ab"
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 2000; i++) sb.append((i % 2 == 0) ? 'a' : 'b');
            writeTestCase(tc++, sb.toString());
        }

        // TC 83: n = 2000, strictly increasing alphabet cycle
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 2000; i++) sb.append((char) ('a' + (i % 26)));
            writeTestCase(tc++, sb.toString());
        }

        // TC 84: n = 2000, palindrome built by mirroring
        {
            String half = randomString(1000, rng);
            StringBuilder sb = new StringBuilder(half);
            sb.append(new StringBuilder(half).reverse());
            writeTestCase(tc++, sb.toString());
        }

        // TC 85: n = 2000, odd-length palindrome
        {
            String half = randomString(999, rng);
            char center = (char) ('a' + rng.nextInt(26));
            StringBuilder sb = new StringBuilder(half);
            sb.append(center);
            sb.append(new StringBuilder(half).reverse());
            writeTestCase(tc++, sb.toString());
        }

        // TC 86: n = 2000, all distinct (worst case — 1999 cuts)
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 2000; i++) sb.append((char) ('a' + (i % 26)));
            writeTestCase(tc++, sb.toString());
        }

        // TC 87 - 91: n = 2000, fully random
        for (int k = 0; k < 5; k++) {
            writeTestCase(tc++, randomString(2000, rng));
        }

        // TC 92: n = 2000, small alphabet {a,b}
        writeTestCase(tc++, randomSmallAlpha(2000, 2, rng));

        // TC 93: n = 2000, small alphabet {a,b,c}
        writeTestCase(tc++, randomSmallAlpha(2000, 3, rng));

        // TC 94: n = 1999 (odd), random
        writeTestCase(tc++, randomString(1999, rng));

        // TC 95: n = 2000, "ab" repeated
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 1000; i++) sb.append("ab");
            writeTestCase(tc++, sb.toString());
        }

        // TC 96: n = 2000, "abc" repeated
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 666; i++) sb.append("abc");
            sb.append("ab");   // pad to 2000
            writeTestCase(tc++, sb.toString());
        }

        // TC 97: n = 2000, "aabbcc" repeated
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 333; i++) sb.append("aabbcc");
            sb.append("aa");   // pad to 2000
            writeTestCase(tc++, sb.toString());
        }

        // TC 98: n = 2000, increasing blocks "aabbccdd..." truncated
        {
            StringBuilder sb = new StringBuilder();
            int c = 0;
            while (sb.length() < 2000) {
                char ch = (char) ('a' + (c % 26));
                sb.append(ch).append(ch);
                c++;
            }
            writeTestCase(tc++, sb.substring(0, 2000));
        }

        // TC 99: n = 2000, sawtooth pattern within alphabet
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 2000; i++) {
                int r = i % 20;
                sb.append((char) ('a' + (r < 10 ? r : 19 - r)));
            }
            writeTestCase(tc++, sb.toString());
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Palindrome Partitioning II.");
    }
}