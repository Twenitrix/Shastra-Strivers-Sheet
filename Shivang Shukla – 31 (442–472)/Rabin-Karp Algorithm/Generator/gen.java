import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    static final long MOD = 1_000_000_007L;
    static final long BASE = 31L;

    // Reference solver: Rabin-Karp with rolling hash.
    // Reports all 0-based starting indices, or -1 if none.
    private static String solve(String text, String pat) {
        int n = text.length(), m = pat.length();
        if (m > n) return "-1";

        long pow = 1;
        for (int i = 0; i < m - 1; i++) pow = (pow * BASE) % MOD;

        long patHash = 0, textHash = 0;
        for (int i = 0; i < m; i++) {
            patHash  = (patHash  * BASE + (pat.charAt(i)  - 'a' + 1)) % MOD;
            textHash = (textHash * BASE + (text.charAt(i) - 'a' + 1)) % MOD;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i + m <= n; i++) {
            if (textHash == patHash) {
                boolean match = true;
                for (int k = 0; k < m; k++) {
                    if (text.charAt(i + k) != pat.charAt(k)) { match = false; break; }
                }
                if (match) {
                    if (sb.length() > 0) sb.append(' ');
                    sb.append(i);
                }
            }
            if (i + m < n) {
                // roll
                long oldChar = (text.charAt(i) - 'a' + 1);
                long newChar = (text.charAt(i + m) - 'a' + 1);
                textHash = (textHash - oldChar * pow % MOD + MOD) % MOD;
                textHash = (textHash * BASE + newChar) % MOD;
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

        File inFile  = new File(inputDir,  String.format("input%02d.txt",  index));
        File outFile = new File(outputDir, String.format("output%02d.txt", index));

        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(text);
            pw.println(pat);
        }

        String ans = solve(text, pat);
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
        writeTestCase(tc++, "ababcabcabababd", "abab");

        // TC 01: example 2 (overlapping)
        writeTestCase(tc++, "aaaaa", "aaa");

        // TC 02: example 3 (no match)
        writeTestCase(tc++, "abcdef", "xyz");

        // TC 03: pattern == text (match at 0)
        writeTestCase(tc++, "abc", "abc");

        // TC 04: pattern longer than text
        writeTestCase(tc++, "ab", "abc");

        // TC 05: n = m = 1, match
        writeTestCase(tc++, "a", "a");

        // TC 06: n = m = 1, no match
        writeTestCase(tc++, "a", "b");

        // TC 07: single char pattern, every position
        writeTestCase(tc++, "aaaa", "a");

        // TC 08: single char pattern, mixed text
        writeTestCase(tc++, "ababab", "a");

        // TC 09: all same text, single char pattern
        writeTestCase(tc++, "zzzzzz", "z");

        // TC 10: occurrence only at start
        writeTestCase(tc++, "abcde", "ab");

        // TC 11: occurrence only at end
        writeTestCase(tc++, "xyzabc", "abc");

        // TC 12: occurrence in middle
        writeTestCase(tc++, "xxabcyy", "abc");

        // TC 13: multiple non-overlapping
        writeTestCase(tc++, "abcXabcXabc", "abc");

        // TC 14: overlapping "aba"
        writeTestCase(tc++, "ababa", "aba");

        // TC 15: pattern with self-prefix "aab"
        writeTestCase(tc++, "aabaabaab", "aab");

        // TC 16: period-3 pattern
        writeTestCase(tc++, "abcabcabcabc", "abc");

        // TC 17: pattern = "aa" in "aaaa"
        writeTestCase(tc++, "aaaa", "aa");

        // TC 18: pattern = "aaaa" in "aaaaaaaaaa"
        writeTestCase(tc++, "aaaaaaaaaa", "aaaa");

        // TC 19: pattern appears exactly once, at start of long text
        writeTestCase(tc++, "abc" + repeat('x', 100), "abc");

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int tn = 1 + rng.nextInt(15);
            int pn = 1 + rng.nextInt(tn);
            String text, pat;
            if (rng.nextBoolean()) {
                text = randomString(tn, rng);
                pat = randomString(pn, rng);
            } else {
                text = randomSmallAlpha(tn, 2, rng);        // {a,b} → more collisions
                pat = randomSmallAlpha(pn, 2, rng);
            }
            writeTestCase(tc++, text, pat);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int tn = 100 + rng.nextInt(901);                // 100..1000
            int pn = 1 + rng.nextInt(Math.min(tn, 50));     // small pattern
            String text = randomSmallAlpha(tn, 3, rng);     // {a,b,c}
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

        // TC 80: 1e5 'a's, pattern "a"
        writeTestCase(tc++, repeat('a', 100000), "a");

        // TC 81: 1e5 'a's, pattern "aaaaa"
        writeTestCase(tc++, repeat('a', 100000), "aaaaa");

        // TC 82: 1e5 'a's, pattern 1e5 'a's
        writeTestCase(tc++, repeat('a', 100000), repeat('a', 100000));

        // TC 83: 1e5 'a's, pattern 50000 'a's
        writeTestCase(tc++, repeat('a', 100000), repeat('a', 50000));

        // TC 84: 1e5 'a's, pattern "aaaaab" (worst-case hash collision)
        {
            char[] parr = new char[50000];
            Arrays.fill(parr, 'a');
            parr[parr.length - 1] = 'b';
            writeTestCase(tc++, repeat('a', 100000), new String(parr));
        }

        // TC 85: random text vs random pattern (likely no match)
        writeTestCase(tc++, randomString(100000, rng), randomString(100000, rng));

        // TC 86: "ab" * 50000, pattern "abab"
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 50000; i++) sb.append("ab");
            writeTestCase(tc++, sb.toString(), "abab");
        }

        // TC 87: "abc" * ..., pattern "abcabc"
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 33333; i++) sb.append("abc");
            sb.append('a');     // length 100000
            writeTestCase(tc++, sb.toString(), "abcabc");
        }

        // TC 88: text over {a,b}, pattern from text (guaranteed match)
        {
            String text = randomSmallAlpha(100000, 2, rng);
            int start = rng.nextInt(100000 - 1000 + 1);
            String pat = text.substring(start, start + 1000);
            writeTestCase(tc++, text, pat);
        }

        // TC 89: text over {a,b}, random pattern (likely no match)
        writeTestCase(tc++, randomSmallAlpha(100000, 2, rng), randomSmallAlpha(1000, 2, rng));

        // TC 90 - 94: 1e5 random over 26 letters, pattern 10..100
        for (int k = 0; k < 5; k++) {
            int pn = 10 + rng.nextInt(91);
            writeTestCase(tc++, randomString(100000, rng), randomString(pn, rng));
        }

        // TC 95: 1e5 text, pattern = last 500 chars (1 match at end)
        {
            String text = randomString(100000, rng);
            String pat = text.substring(100000 - 500);
            writeTestCase(tc++, text, pat);
        }

        // TC 96: 1e5 text, pattern = first 500 chars (1 match at start)
        {
            String text = randomString(100000, rng);
            String pat = text.substring(0, 500);
            writeTestCase(tc++, text, pat);
        }

        // TC 97: "abc" repeated, pattern half-length
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 33334; i++) sb.append("abc");
            String text = sb.substring(0, 100000);
            String pat = text.substring(0, 50001);
            writeTestCase(tc++, text, pat);
        }

        // TC 98: "abab..." text, pattern "abab...a" (odd length ends with 'a')
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 50000; i++) sb.append("ab");
            String text = sb.toString();
            String pat = text.substring(0, 99999);
            writeTestCase(tc++, text, pat);
        }

        // TC 99: pattern = 1e5 'a's, text = 99999 'a's + 'b' (no match, worst case)
        writeTestCase(tc++, repeat('a', 99999) + "b", repeat('a', 100000));

        System.out.println("SUCCESS: Generated " + tc + " test cases for Rabin-Karp Algorithm.");
    }
}