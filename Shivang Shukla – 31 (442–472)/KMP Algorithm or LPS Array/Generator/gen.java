import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: KMP string matching (0-based indices), -1 if none.
    private static String solve(String text, String pat) {
        int n = text.length(), m = pat.length();
        int[] lps = new int[m];
        for (int i = 1, len = 0; i < m; ) {
            if (pat.charAt(i) == pat.charAt(len)) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = 0; i < n; ) {
            if (text.charAt(i) == pat.charAt(j)) {
                i++; j++;
                if (j == m) {
                    if (sb.length() > 0) sb.append(' ');
                    sb.append(i - m);
                    j = lps[j - 1];
                }
            } else if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return sb.length() == 0 ? "-1" : sb.toString();
    }

    private static void writeTestCase(int index, String text, String pat) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir  = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        String inFileName  = String.format("input%02d.txt",  index);
        String outFileName = String.format("output%02d.txt", index);

        File inFile  = new File(inputDir,  inFileName);
        File outFile = new File(outputDir, outFileName);

        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(text);
            pw.println(pat);
        }

        String ans = solve(text, pat);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    // Random lowercase string of given length.
    private static String randomString(int len, Random rng) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append((char) ('a' + rng.nextInt(26)));
        return sb.toString();
    }

    // Random string over a small alphabet (to force many matches).
    private static String randomSmallAlpha(int len, int k, Random rng) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append((char) ('a' + rng.nextInt(k)));
        return sb.toString();
    }

    // Repeat char c, len times.
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
        writeTestCase(tc++, "ababcabcabababd", "ababd");

        // TC 01: example 2 (overlapping)
        writeTestCase(tc++, "aaaaa", "aa");

        // TC 02: pattern == text, single occurrence
        writeTestCase(tc++, "abc", "abc");

        // TC 03: pattern not in text
        writeTestCase(tc++, "abc", "xyz");

        // TC 04: pattern longer than text
        writeTestCase(tc++, "ab", "abc");

        // TC 05: single-char text, single-char pattern (match)
        writeTestCase(tc++, "a", "a");

        // TC 06: single-char text, single-char pattern (no match)
        writeTestCase(tc++, "a", "b");

        // TC 07: pattern == "a", matches every position
        writeTestCase(tc++, "aaaa", "a");

        // TC 08: all same char, pattern single
        writeTestCase(tc++, "zzzzzz", "z");

        // TC 09: all same char, full pattern
        writeTestCase(tc++, "aaaaa", "aaaaa");

        // TC 10: occurrence at start only
        writeTestCase(tc++, "abcde", "ab");

        // TC 11: occurrence at end only
        writeTestCase(tc++, "xyzabc", "abc");

        // TC 12: occurrence in middle
        writeTestCase(tc++, "xxabcyy", "abc");

        // TC 13: multiple non-overlapping
        writeTestCase(tc++, "abcXabcXabc", "abc");

        // TC 14: overlapping "aba"
        writeTestCase(tc++, "ababa", "aba");

        // TC 15: pattern with self-prefix ("aab")
        writeTestCase(tc++, "aabaabaab", "aab");

        // TC 16: classic LPS stress "abababab"
        writeTestCase(tc++, "ababababab", "abab");

        // TC 17: period-3 pattern
        writeTestCase(tc++, "abcabcabcabc", "abc");

        // TC 18: pattern at every position (single char repeated)
        writeTestCase(tc++, "aaaaaaaaaa", "aaa");

        // TC 19: pattern occurs exactly twice, far apart
        writeTestCase(tc++, "abcdeXYZabcde", "abcde");

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int tn = 1 + rng.nextInt(15);
            int pn = 1 + rng.nextInt(tn);            // pattern <= text length
            String text = randomString(tn, rng);
            String pat;
            if (rng.nextBoolean()) {
                // random pattern
                pat = randomString(pn, rng);
            } else {
                // guarantee a match by sampling from text
                int start = rng.nextInt(tn - pn + 1);
                pat = text.substring(start, start + pn);
            }
            writeTestCase(tc++, text, pat);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int tn = 100 + rng.nextInt(901);         // 100..1000
            int pn = 1 + rng.nextInt(Math.min(tn, 50));   // small pattern
            String text = randomSmallAlpha(tn, 3, rng);   // small alphabet → matches
            String pat;
            if (rng.nextBoolean()) {
                pat = randomSmallAlpha(pn, 3, rng);
            } else {
                int start = rng.nextInt(tn - pn + 1);
                pat = text.substring(start, start + pn);
            }
            writeTestCase(tc++, text, pat);
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: 1e6 'a's, pattern "a" -> 1e6 matches
        writeTestCase(tc++, repeat('a', 1_000_000), "a");

        // TC 81: 1e6 'a's, pattern "aaaaa" -> ~1e6 matches
        writeTestCase(tc++, repeat('a', 1_000_000), "aaaaa");

        // TC 82: 1e6 'a's, pattern 1e6 'a's -> exactly 1 match (whole text)
        writeTestCase(tc++, repeat('a', 1_000_000), repeat('a', 1_000_000));

        // TC 83: 1e6 'a's, pattern length 500000 of 'a' -> 500001 matches
        writeTestCase(tc++, repeat('a', 1_000_000), repeat('a', 500_000));

        // TC 84: worst-case LPS pattern "aaaa...ab" against "aaaa...a"
        {
            String text = repeat('a', 1_000_000);
            char[] parr = new char[500_000];
            Arrays.fill(parr, 'a');
            parr[parr.length - 1] = 'b';
            writeTestCase(tc++, text, new String(parr));
        }

        // TC 85: no match, worst-case traversal (random text vs pattern len 1e6)
        writeTestCase(tc++, randomString(1_000_000, rng), randomString(1_000_000, rng));

        // TC 86: "ab" repeated 5e5 times, pattern "abab"
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 500_000; i++) sb.append("ab");
            writeTestCase(tc++, sb.toString(), "abab");
        }

        // TC 87: "abc" repeated, pattern "abcabc"
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 333_333; i++) sb.append("abc");
            sb.append('a'); // length 1e6 exactly
            writeTestCase(tc++, sb.toString(), "abcabc");
        }

        // TC 88: text over alphabet {a,b}, pattern from text (guaranteed match)
        {
            String text = randomSmallAlpha(1_000_000, 2, rng);
            int start = rng.nextInt(1_000_000 - 1000 + 1);
            String pat = text.substring(start, start + 1000);
            writeTestCase(tc++, text, pat);
        }

        // TC 89: text over alphabet {a,b}, random pattern length 1000 (likely no match)
        writeTestCase(tc++, randomSmallAlpha(1_000_000, 2, rng), randomSmallAlpha(1000, 2, rng));

        // TC 90 - 94: 1e6 random over 26 letters, random pattern (small, likely none or few)
        for (int k = 0; k < 5; k++) {
            int tn = 1_000_000;
            int pn = 10 + rng.nextInt(91);           // 10..100
            writeTestCase(tc++, randomString(tn, rng), randomString(pn, rng));
        }

        // TC 95: 1e6 text, pattern = last 500 chars of text (guaranteed 1 match at end)
        {
            String text = randomString(1_000_000, rng);
            String pat = text.substring(1_000_000 - 500);
            writeTestCase(tc++, text, pat);
        }

        // TC 96: 1e6 text, pattern = first 500 chars (match at start)
        {
            String text = randomString(1_000_000, rng);
            String pat = text.substring(0, 500);
            writeTestCase(tc++, text, pat);
        }

        // TC 97: text = "abcabcabc...", pattern = "abcabcabc...abc" (half length)
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 333_334; i++) sb.append("abc");
            String text = sb.substring(0, 1_000_000);
            String pat = text.substring(0, 500_001); // ends with 'a'
            writeTestCase(tc++, text, pat);
        }

        // TC 98: text = "abab...", pattern = "abab...a" (odd length ends with 'a')
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 500_000; i++) sb.append("ab");
            String text = sb.toString();             // 1e6 chars, "abab...ab"
            // pattern "abab...a" of length 999999
            String pat = text.substring(0, 999_999);
            writeTestCase(tc++, text, pat);
        }

        // TC 99: pattern is 1e6 'a's, text has single 'b' at the end -> no match, worst case
        {
            String text = repeat('a', 999_999) + "b";
            writeTestCase(tc++, text, repeat('a', 1_000_000));
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for KMP Algorithm or LPS Array.");
    }
}