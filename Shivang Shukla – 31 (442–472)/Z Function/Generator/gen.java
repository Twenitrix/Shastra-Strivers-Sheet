import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: O(n) Z-algorithm.
    private static int[] solve(String s) {
        int n = s.length();
        int[] z = new int[n];
        z[0] = 0;
        int l = 0, r = 0;
        for (int i = 1; i < n; i++) {
            if (i <= r) z[i] = Math.min(r - i + 1, z[i - l]);
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) z[i]++;
            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }
        return z;
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

        int[] z = solve(s);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < z.length; i++) {
                sb.append(z[i]);
                if (i < z.length - 1) sb.append(' ');
            }
            pw.println(sb.toString());
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
        writeTestCase(tc++, "abacaba");

        // TC 01: example 2
        writeTestCase(tc++, "aaaaa");

        // TC 02: n = 1
        writeTestCase(tc++, "a");

        // TC 03: n = 1, another char
        writeTestCase(tc++, "z");

        // TC 04: n = 2, "aa"
        writeTestCase(tc++, "aa");

        // TC 05: n = 2, "ab"
        writeTestCase(tc++, "ab");

        // TC 06: n = 3, "aaa"
        writeTestCase(tc++, "aaa");

        // TC 07: n = 3, "abc"
        writeTestCase(tc++, "abc");

        // TC 08: n = 3, "aba"
        writeTestCase(tc++, "aba");

        // TC 09: all distinct chars
        writeTestCase(tc++, "abcdef");

        // TC 10: "abab"
        writeTestCase(tc++, "abab");

        // TC 11: "ababab"
        writeTestCase(tc++, "ababab");

        // TC 12: "abcabcabc"
        writeTestCase(tc++, "abcabcabc");

        // TC 13: "aabaaab"
        writeTestCase(tc++, "aabaaab");

        // TC 14: palindrome "racecar"
        writeTestCase(tc++, "racecar");

        // TC 15: "aabbaabb"
        writeTestCase(tc++, "aabbaabb");

        // TC 16: full alphabet
        writeTestCase(tc++, "abcdefghijklmnopqrstuvwxyz");

        // TC 17: reverse alphabet
        writeTestCase(tc++, "zyxwvutsrqponmlkjihgfedcba");

        // TC 18: "aaaaaaaa"
        writeTestCase(tc++, "aaaaaaaa");

        // TC 19: "ababababab"
        writeTestCase(tc++, "ababababab");

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int len = 1 + rng.nextInt(20);           // 1..20
            int alpha = 1 + rng.nextInt(4);          // 1..4 alphabet
            writeTestCase(tc++, randomSmallAlpha(len, alpha, rng));
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int len = 100 + rng.nextInt(901);        // 100..1000
            writeTestCase(tc++, randomString(len, rng));
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 1e6, all 'a'
        writeTestCase(tc++, repeat('a', 1000000));

        // TC 81: n = 1e6, all 'z'
        writeTestCase(tc++, repeat('z', 1000000));

        // TC 82: n = 1e6, alternating "ab"
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 1000000; i++) sb.append((i % 2 == 0) ? 'a' : 'b');
            writeTestCase(tc++, sb.toString());
        }

        // TC 83: n = 1e6, ascending alphabet cycle
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 1000000; i++) sb.append((char) ('a' + (i % 26)));
            writeTestCase(tc++, sb.toString());
        }

        // TC 84: n = 1e6, "abc" repeated
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 333333; i++) sb.append("abc");
            sb.append('a');     // length 1000000
            writeTestCase(tc++, sb.toString());
        }

        // TC 85: n = 1e6, palindrome built by mirroring
        {
            String half = randomString(500000, rng);
            StringBuilder sb = new StringBuilder(half);
            sb.append(new StringBuilder(half).reverse());
            writeTestCase(tc++, sb.toString());
        }

        // TC 86: n = 1e6, odd palindrome
        {
            String half = randomString(499999, rng);
            char center = (char) ('a' + rng.nextInt(26));
            StringBuilder sb = new StringBuilder(half);
            sb.append(center);
            sb.append(new StringBuilder(half).reverse());
            writeTestCase(tc++, sb.toString());
        }

        // TC 87 - 91: n = 1e6, fully random
        for (int k = 0; k < 5; k++) {
            writeTestCase(tc++, randomString(1000000, rng));
        }

        // TC 92: n = 1e6, small alphabet {a,b}
        writeTestCase(tc++, randomSmallAlpha(1000000, 2, rng));

        // TC 93: n = 1e6, small alphabet {a,b,c}
        writeTestCase(tc++, randomSmallAlpha(1000000, 3, rng));

        // TC 94: n = 999999 (odd), random
        writeTestCase(tc++, randomString(999999, rng));

        // TC 95: n = 1e6, "a" * 500000 + "b" * 500000
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 500000; i++) sb.append('a');
            for (int i = 0; i < 500000; i++) sb.append('b');
            writeTestCase(tc++, sb.toString());
        }

        // TC 96: n = 1e6, "aabbcc" repeated
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 166666; i++) sb.append("aabbcc");
            sb.append("aabb");   // pad to 1e6
            writeTestCase(tc++, sb.toString());
        }

        // TC 97: n = 1e6, increasing blocks "aabbccdd..."
        {
            StringBuilder sb = new StringBuilder();
            int c = 0;
            while (sb.length() < 1000000) {
                char ch = (char) ('a' + (c % 26));
                sb.append(ch).append(ch);
                c++;
            }
            writeTestCase(tc++, sb.substring(0, 1000000));
        }

        // TC 98: n = 1e6, sawtooth pattern
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 1000000; i++) {
                int r = i % 20;
                sb.append((char) ('a' + (r < 10 ? r : 19 - r)));
            }
            writeTestCase(tc++, sb.toString());
        }

        // TC 99: n = 1e6, "ababab...ab" (period 2, even length)
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 500000; i++) sb.append("ab");
            writeTestCase(tc++, sb.toString());
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Z Function.");
    }
}