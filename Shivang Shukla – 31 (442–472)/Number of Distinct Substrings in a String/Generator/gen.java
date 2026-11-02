import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: suffix automaton (O(n)) — counts distinct non-empty substrings.
    // Each state v (except initial) contributes (len[v] - len[link[v]]) distinct substrings.
    static class SuffixAutomaton {
        static class State {
            int len, link;
            int[] next = new int[26];
            State() { Arrays.fill(next, -1); }
        }

        List<State> st = new ArrayList<>();
        int last;

        SuffixAutomaton() {
            st.add(new State());       // state 0
            st.get(0).len = 0;
            st.get(0).link = -1;
            last = 0;
        }

        void extend(char c) {
            int cur = st.size();
            st.add(new State());
            st.get(cur).len = st.get(last).len + 1;

            int p = last;
            while (p != -1 && st.get(p).next[c - 'a'] == -1) {
                st.get(p).next[c - 'a'] = cur;
                p = st.get(p).link;
            }

            if (p == -1) {
                st.get(cur).link = 0;
            } else {
                int q = st.get(p).next[c - 'a'];
                if (st.get(p).len + 1 == st.get(q).len) {
                    st.get(cur).link = q;
                } else {
                    int clone = st.size();
                    st.add(new State());
                    st.get(clone).len = st.get(p).len + 1;
                    st.get(clone).link = st.get(q).link;
                    st.get(clone).next = st.get(q).next.clone();

                    while (p != -1 && st.get(p).next[c - 'a'] == q) {
                        st.get(p).next[c - 'a'] = clone;
                        p = st.get(p).link;
                    }
                    st.get(q).link = clone;
                    st.get(cur).link = clone;
                }
            }
            last = cur;
        }

        long countDistinct() {
            long total = 0;
            for (int v = 1; v < st.size(); v++) {
                total += (long) st.get(v).len - st.get(st.get(v).link).len;
            }
            return total;
        }
    }

    private static long solve(String s) {
        SuffixAutomaton sam = new SuffixAutomaton();
        for (int i = 0; i < s.length(); i++) sam.extend(s.charAt(i));
        return sam.countDistinct();
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
        writeTestCase(tc++, "ababa");

        // TC 01: example 2
        writeTestCase(tc++, "aaa");

        // TC 02: single char 'a'
        writeTestCase(tc++, "a");

        // TC 03: single char 'z'
        writeTestCase(tc++, "z");

        // TC 04: two distinct chars "ab"
        writeTestCase(tc++, "ab");

        // TC 05: two same chars "aa"
        writeTestCase(tc++, "aa");

        // TC 06: "abc" — all distinct substrings: 6
        writeTestCase(tc++, "abc");

        // TC 07: "aba"
        writeTestCase(tc++, "aba");

        // TC 08: "abcd" (all distinct chars, 10 substrings)
        writeTestCase(tc++, "abcd");

        // TC 09: "abcde" (all distinct, 15 substrings)
        writeTestCase(tc++, "abcde");

        // TC 10: full alphabet once (26 distinct, 26*27/2 = 351)
        {
            StringBuilder sb = new StringBuilder();
            for (char c = 'a'; c <= 'z'; c++) sb.append(c);
            writeTestCase(tc++, sb.toString());
        }

        // TC 11: reverse alphabet
        {
            StringBuilder sb = new StringBuilder();
            for (char c = 'z'; c >= 'a'; c--) sb.append(c);
            writeTestCase(tc++, sb.toString());
        }

        // TC 12: palindrome "racecar"
        writeTestCase(tc++, "racecar");

        // TC 13: "aaaaa"
        writeTestCase(tc++, "aaaaa");

        // TC 14: "ababab"
        writeTestCase(tc++, "ababab");

        // TC 15: "abcabcabc"
        writeTestCase(tc++, "abcabcabc");

        // TC 16: "aabbaabb"
        writeTestCase(tc++, "aabbaabb");

        // TC 17: alternating "azazazazaz"
        writeTestCase(tc++, "azazazazaz");

        // TC 18: "abcdefghij" (all distinct, 55 substrings)
        writeTestCase(tc++, "abcdefghij");

        // TC 19: "aabbccddee"
        writeTestCase(tc++, "aabbccddee");

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int len = 1 + rng.nextInt(20);
            writeTestCase(tc++, randomString(len, rng));
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int len = 100 + rng.nextInt(901);        // 100..1000
            writeTestCase(tc++, randomString(len, rng));
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 1e5, all 'a'
        writeTestCase(tc++, repeat('a', 100000));

        // TC 81: n = 1e5, all 'z'
        writeTestCase(tc++, repeat('z', 100000));

        // TC 82: n = 1e5, alternating "ab"
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100000; i++) sb.append((i % 2 == 0) ? 'a' : 'b');
            writeTestCase(tc++, sb.toString());
        }

        // TC 83: n = 1e5, ascending alphabet repeated
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100000; i++) sb.append((char) ('a' + (i % 26)));
            writeTestCase(tc++, sb.toString());
        }

        // TC 84: n = 1e5, descending alphabet repeated
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100000; i++) sb.append((char) ('z' - (i % 26)));
            writeTestCase(tc++, sb.toString());
        }

        // TC 85: n = 1e5, "abab" repeated
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100000; i++) sb.append((char) ('a' + (i % 4)));
            writeTestCase(tc++, sb.toString());
        }

        // TC 86: n = 1e5, single alphabet repeated 100000 times
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

        // TC 95: n = 1e5, palindrome built by mirroring
        {
            String half = randomString(50000, rng);
            StringBuilder sb = new StringBuilder(half);
            sb.append(new StringBuilder(half).reverse());
            writeTestCase(tc++, sb.toString());
        }

        // TC 96: n = 1e5, two blocks: 'a'*50000 + 'b'*50000
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 50000; i++) sb.append('a');
            for (int i = 0; i < 50000; i++) sb.append('b');
            writeTestCase(tc++, sb.toString());
        }

        // TC 97: n = 1e5, "abcdefghij" repeated
        {
            StringBuilder sb = new StringBuilder();
            String pat = "abcdefghij";
            for (int i = 0; i < 100000; i++) sb.append(pat.charAt(i % 10));
            writeTestCase(tc++, sb.toString());
        }

        // TC 98: n = 1e5, sawtooth in alphabet {a,b,c,d,e,f,g,h,i,j}
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100000; i++) {
                int r = i % 20;
                sb.append((char) ('a' + (r < 10 ? r : 19 - r)));
            }
            writeTestCase(tc++, sb.toString());
        }

        // TC 99: n = 1e5, cyclic shift pattern
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100000; i++) {
                sb.append((char) ('a' + (i * 7) % 26));
            }
            writeTestCase(tc++, sb.toString());
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Number of Distinct Substrings in a String.");
    }
}