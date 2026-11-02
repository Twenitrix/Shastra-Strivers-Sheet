import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    static final long MOD = 1_000_000_007L;
    static final long BASE = 31L;

    // Reference solver: polynomial hash H(S) = sum val(S[i]) * 31^i mod 1e9+7.
    private static long solve(String s) {
        long h = 0;
        long pow = 1;               // 31^i
        for (int i = 0; i < s.length(); i++) {
            int val = s.charAt(i) - 'a' + 1;   // a=1 ... z=26
            h = (h + val * pow) % MOD;
            pow = (pow * BASE) % MOD;
        }
        return h;
    }

    private static void writeTestCase(int index, String s) throws Exception {
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
            pw.println(s);
        }

        long ans = solve(s);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    // Random lowercase string of length len.
    private static String randomString(int len, Random rng) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append((char) ('a' + rng.nextInt(26)));
        return sb.toString();
    }

    // String filled with the same character.
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
        writeTestCase(tc++, "abc");

        // TC 01: example 2
        writeTestCase(tc++, "aa");

        // TC 02: single char 'a' (min length, min value) -> 1
        writeTestCase(tc++, "a");

        // TC 03: single char 'z' (min length, max value) -> 26
        writeTestCase(tc++, "z");

        // TC 04: single char middle
        writeTestCase(tc++, "m");

        // TC 05: "a" repeated (small values)
        writeTestCase(tc++, "aaaaa");

        // TC 06: "z" repeated (max values)
        writeTestCase(tc++, "zzzzz");

        // TC 07: strictly increasing alphabet prefix
        writeTestCase(tc++, "abcdefghij");

        // TC 08: strictly decreasing alphabet
        writeTestCase(tc++, "zyxwvutsrq");

        // TC 09: full alphabet once
        writeTestCase(tc++, "abcdefghijklmnopqrstuvwxyz");

        // TC 10: palindrome
        writeTestCase(tc++, "racecar");

        // TC 11: two-char "ab"
        writeTestCase(tc++, "ab");

        // TC 12: two-char "ba" (same letters, different order -> different hash)
        writeTestCase(tc++, "ba");

        // TC 13: classic "hello"
        writeTestCase(tc++, "hello");

        // TC 14: anagram of hello
        writeTestCase(tc++, "ohell");

        // TC 15: long run of 'a'
        writeTestCase(tc++, repeat('a', 100));

        // TC 16: long run of 'z'
        writeTestCase(tc++, repeat('z', 100));

        // TC 17: two blocks "aaa...zzz"
        writeTestCase(tc++, repeat('a', 50) + repeat('z', 50));

        // TC 18: two blocks "zzz...aaa"
        writeTestCase(tc++, repeat('z', 50) + repeat('a', 50));

        // TC 19: alternating "azazaz..."
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 60; i++) sb.append((i % 2 == 0) ? 'a' : 'z');
            writeTestCase(tc++, sb.toString());
        }

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int len = 1 + rng.nextInt(20);       // 1..20
            writeTestCase(tc++, randomString(len, rng));
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int len = 100 + rng.nextInt(901);    // 100..1000
            writeTestCase(tc++, randomString(len, rng));
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: 1e5 all 'a'
        writeTestCase(tc++, repeat('a', 100000));

        // TC 81: 1e5 all 'z'
        writeTestCase(tc++, repeat('z', 100000));

        // TC 82: 1e5 all 'm' (middle value)
        writeTestCase(tc++, repeat('m', 100000));

        // TC 83: 1e5 ascending alphabet repeated
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100000; i++) sb.append((char) ('a' + (i % 26)));
            writeTestCase(tc++, sb.toString());
        }

        // TC 84: 1e5 descending alphabet repeated
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100000; i++) sb.append((char) ('z' - (i % 26)));
            writeTestCase(tc++, sb.toString());
        }

        // TC 85: 1e5 alternating 'a','z'
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100000; i++) sb.append((i % 2 == 0) ? 'a' : 'z');
            writeTestCase(tc++, sb.toString());
        }

        // TC 86: 1e5 palindrome-ish (whole string palindrome)
        {
            String half = randomString(50000, rng);
            StringBuilder sb = new StringBuilder(half);
            sb.append(new StringBuilder(half).reverse());
            writeTestCase(tc++, sb.toString());
        }

        // TC 87 - 91: 1e5 fully random (5 tests)
        for (int k = 0; k < 5; k++) {
            writeTestCase(tc++, randomString(100000, rng));
        }

        // TC 92: 1e5 'a's followed by single 'b'
        writeTestCase(tc++, repeat('a', 99999) + "b");

        // TC 93: single 'b' followed by 1e5-1 'a's
        writeTestCase(tc++, "b" + repeat('a', 99999));

        // TC 94: 1e5 'z's with a single 'a' in the middle
        {
            StringBuilder sb = new StringBuilder(repeat('z', 100000));
            sb.setCharAt(50000, 'a');
            writeTestCase(tc++, sb.toString());
        }

        // TC 95: 99999 length (odd max-ish)
        writeTestCase(tc++, randomString(99999, rng));

        // TC 96: 1e5 of "ab" repeated -> length 1e5
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 50000; i++) sb.append("ab");
            writeTestCase(tc++, sb.toString());
        }

        // TC 97: 1e5 of "abc" pattern (truncated at exactly 1e5)
        {
            StringBuilder sb = new StringBuilder();
            String pat = "abc";
            for (int i = 0; i < 100000; i++) sb.append(pat.charAt(i % 3));
            writeTestCase(tc++, sb.toString());
        }

        // TC 98: 1e5 'a's with 'z' at every 100th position
        {
            char[] arr = new char[100000];
            Arrays.fill(arr, 'a');
            for (int i = 0; i < 100000; i += 100) arr[i] = 'z';
            writeTestCase(tc++, new String(arr));
        }

        // TC 99: 1e5 random, but drawn from small alphabet {a,b,c} (many repeats)
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100000; i++) sb.append((char) ('a' + rng.nextInt(3)));
            writeTestCase(tc++, sb.toString());
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Hashing in Strings — Theory.");
    }
}