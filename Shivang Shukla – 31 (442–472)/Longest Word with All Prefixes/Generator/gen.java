import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver:
    // 1) Put all words in a HashSet.
    // 2) For each word, verify every non-empty prefix is present.
    // 3) Among complete words, pick longest; tie-break by lexicographically smallest.
    // If none complete, return "None".
    private static String solve(String[] words) {
        Set<String> pool = new HashSet<>(Arrays.asList(words));

        String best = null;
        for (String w : words) {
            boolean complete = true;
            for (int i = 1; i <= w.length(); i++) {
                if (!pool.contains(w.substring(0, i))) {
                    complete = false;
                    break;
                }
            }
            if (!complete) continue;

            if (best == null) {
                best = w;
            } else {
                if (w.length() > best.length()) {
                    best = w;
                } else if (w.length() == best.length() && w.compareTo(best) < 0) {
                    best = w;
                }
            }
        }
        return best == null ? "None" : best;
    }

    private static void writeTestCase(int index, String[] words) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir  = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        File inFile  = new File(inputDir,  String.format("input%02d.txt",  index));
        File outFile = new File(outputDir, String.format("output%02d.txt", index));

        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(words.length);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < words.length; i++) {
                sb.append(words[i]);
                if (i < words.length - 1) sb.append(' ');
            }
            pw.println(sb.toString());
        }

        String ans = solve(words);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    private static String randomWord(int len, Random rng) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append((char) ('a' + rng.nextInt(26)));
        return sb.toString();
    }

    // Build a complete chain of "a", "ab", "abc", ..., up to len.
    private static String[] chain(String start, int len) {
        String[] w = new String[len];
        StringBuilder sb = new StringBuilder(start);
        w[0] = sb.toString();
        for (int i = 1; i < len; i++) {
            sb.append((char) ('a' + (i % 26)));
            w[i] = sb.toString();
        }
        return w;
    }

    private static String[] distinctRandom(int n, int minLen, int maxLen, Random rng) {
        Set<String> set = new LinkedHashSet<>();
        int guard = 0;
        while (set.size() < n && guard < n * 100) {
            guard++;
            int len = minLen + rng.nextInt(maxLen - minLen + 1);
            set.add(randomWord(len, rng));
        }
        return set.toArray(new String[0]);
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        // TC 00: example 1
        writeTestCase(tc++, new String[]{"n", "nin", "ninj", "ninja", "ninga", "ni"});

        // TC 01: example 2 (no complete word)
        writeTestCase(tc++, new String[]{"ab", "abc", "a", "abd"});

        // TC 02: n = 1, single-letter word -> complete
        writeTestCase(tc++, new String[]{"a"});

        // TC 03: n = 1, single non-a letter
        writeTestCase(tc++, new String[]{"z"});

        // TC 04: n = 1, longer word -> not complete
        writeTestCase(tc++, new String[]{"abc"});

        // TC 05: perfect chain "a","ab","abc","abcd"
        writeTestCase(tc++, new String[]{"a", "ab", "abc", "abcd"});

        // TC 06: perfect chain "z","zz","zzz"
        writeTestCase(tc++, new String[]{"z", "zz", "zzz"});

        // TC 07: chain missing middle element
        writeTestCase(tc++, new String[]{"a", "abc"});

        // TC 08: two chains, "a.." and "b.."
        writeTestCase(tc++, new String[]{"a", "ab", "b", "bc", "bcd"});

        // TC 09: two chains of equal length -> lexicographically smaller
        writeTestCase(tc++, new String[]{"a", "ab", "abc", "b", "bc", "bcd"});

        // TC 10: chain ending in 'z'
        writeTestCase(tc++, new String[]{"a", "az", "azz", "azzz", "azzzz"});

        // TC 11: tie-break lex — "aaa" vs "aab"
        writeTestCase(tc++, new String[]{"a", "aa", "aaa", "aab", "aaaa", "aab"});

        // TC 12: duplicate word present (spec says distinct but test robustness)
        writeTestCase(tc++, new String[]{"a", "a", "ab"});

        // TC 13: word of max length 10, complete chain
        writeTestCase(tc++, new String[]{
            "a", "ab", "abc", "abcd", "abcde",
            "abcdef", "abcdefg", "abcdefgh", "abcdefghi", "abcdefghij"
        });

        // TC 14: 10-length chain with repeated char
        writeTestCase(tc++, new String[]{
            "z", "zz", "zzz", "zzzz", "zzzzz",
            "zzzzzz", "zzzzzzz", "zzzzzzzz", "zzzzzzzzz", "zzzzzzzzzz"
        });

        // TC 15: chain + longer word that isn't complete
        writeTestCase(tc++, new String[]{"a", "ab", "abcd"});

        // TC 16: only longer words (none length 1) -> None
        writeTestCase(tc++, new String[]{"ab", "abc", "abcd"});

        // TC 17: many single-letter words only
        writeTestCase(tc++, new String[]{"a", "b", "c", "d", "e", "f", "g"});

        // TC 18: chain breaks at length 3
        writeTestCase(tc++, new String[]{"a", "ab", "abcd", "abcde"});

        // TC 19: complete word not the "obvious" alphabet chain
        writeTestCase(tc++, new String[]{"c", "co", "cod", "code", "coder"});

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 1 + rng.nextInt(8);
            String[] w = distinctRandom(n, 1, 4, rng);
            writeTestCase(tc++, w);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        // Build a guaranteed-complete chain of length up to 10 and add distractors.
        for (int k = 0; k < 30; k++) {
            int chainLen = 3 + rng.nextInt(8);       // 3..10
            String start = "" + (char) ('a' + rng.nextInt(26));
            String[] baseChain = chain(start, chainLen);
            List<String> list = new ArrayList<>(Arrays.asList(baseChain));
            int extra = rng.nextInt(20);
            for (int e = 0; e < extra; e++) {
                int len = 1 + rng.nextInt(10);
                list.add(randomWord(len, rng));
            }
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            // shuffle
            list = new ArrayList<>(s);
            Collections.shuffle(list, rng);
            if (list.size() > 1000) list = list.subList(0, 1000);
            writeTestCase(tc++, list.toArray(new String[0]));
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 1e5, perfect chain of length 10 ("a".."abcdefghij") + 99990 random
        {
            List<String> list = new ArrayList<>(Arrays.asList(
                "a", "ab", "abc", "abcd", "abcde",
                "abcdef", "abcdefg", "abcdefgh", "abcdefghi", "abcdefghij"
            ));
            while (list.size() < 100000) list.add(randomWord(1 + rng.nextInt(10), rng));
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 81: n = 1e5, only single-char words 'a'..'z' (26 distinct) → pad
        {
            List<String> list = new ArrayList<>();
            for (char c = 'a'; c <= 'z'; c++) list.add("" + c);
            while (list.size() < 100000) {
                int len = 2 + rng.nextInt(9);
                list.add(randomWord(len, rng));
            }
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 82: n = 1e5, all length-10 random (no prefixes present) → None
        {
            String[] w = distinctRandom(100000, 10, 10, rng);
            writeTestCase(tc++, w);
        }

        // TC 83: n = 1e5, "a".."abcdefghij" chain + noise (shuffled)
        {
            List<String> list = new ArrayList<>(Arrays.asList(
                "a", "ab", "abc", "abcd", "abcde",
                "abcdef", "abcdefg", "abcdefgh", "abcdefghi", "abcdefghij"
            ));
            while (list.size() < 100000) list.add(randomWord(1 + rng.nextInt(10), rng));
            Collections.shuffle(list, rng);
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 84: n = 1e5, no words with all prefixes → None
        {
            String[] w = distinctRandom(100000, 2, 10, rng);
            writeTestCase(tc++, w);
        }

        // TC 85: n = 1e5, chain of "a", "aa", ..., "aaaaaaaaaa" + noise
        {
            List<String> list = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                char[] arr = new char[i];
                Arrays.fill(arr, 'a');
                list.add(new String(arr));
            }
            while (list.size() < 100000) list.add(randomWord(1 + rng.nextInt(10), rng));
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 86: n = 1e5, chain "z","zz",..."zzzzzzzzzz" + noise
        {
            List<String> list = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                char[] arr = new char[i];
                Arrays.fill(arr, 'z');
                list.add(new String(arr));
            }
            while (list.size() < 100000) list.add(randomWord(1 + rng.nextInt(10), rng));
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 87 - 91: n = 1e5, fully random with varying lengths
        for (int k = 0; k < 5; k++) {
            String[] w = distinctRandom(100000, 1, 10, rng);
            writeTestCase(tc++, w);
        }

        // TC 92: n = 1e5, half length-1 (all 26 letters) + half random
        {
            List<String> list = new ArrayList<>();
            for (char c = 'a'; c <= 'z'; c++) list.add("" + c);
            while (list.size() < 100000) list.add(randomWord(2 + rng.nextInt(9), rng));
            writeTestCase(tc++, list.toArray(new String[0]));
        }

        // TC 93: n = 1e5, tie between "aaa.." and "aab.." chains (lex tie-break)
        {
            List<String> list = new ArrayList<>();
            // chain 1: a, aa, aaa, aaaa, aaaaa, ...
            for (int i = 1; i <= 10; i++) {
                char[] arr = new char[i];
                Arrays.fill(arr, 'a');
                list.add(new String(arr));
            }
            // chain 2: a, ab, abb, ...? Actually make second chain "aa...b" for tie.
            // Simpler: two length-10 words that are both complete but differ lexicographically.
            // Complete means all prefixes present. Let's create "aabbbbbbbb" chain fully.
            StringBuilder sb = new StringBuilder();
            // build full chain "a","aa","aab","aabb",...  to length 10
            // easier: build 2 chains sharing "a" prefix, then diverge at second char.
            // chain A: a, aa, aaa, ..., aaaaaaaaaa  (10 chars)
            // chain B: a, ab, abb, ..., abbbbbbbbb (10 chars) — prefix must be in set!
            // For chain B to be complete, "ab","abb",...,"abbbbbbbbb" must all be in set.
            // Let's add both chains fully.
            for (int i = 1; i <= 10; i++) {
                // chain A already added
                // chain B: a followed by (i-1) b's
                char[] b = new char[i];
                b[0] = 'a';
                for (int j = 1; j < i; j++) b[j] = 'b';
                list.add(new String(b));
            }
            while (list.size() < 100000) list.add(randomWord(1 + rng.nextInt(10), rng));
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 94: n = 1e5, two complete chains of same length, different starts
        {
            List<String> list = new ArrayList<>();
            // chain starting with 'a': "a","ab","abc",... length 10
            StringBuilder sb = new StringBuilder("a");
            list.add(sb.toString());
            for (int i = 1; i < 10; i++) {
                sb.append((char) ('a' + i));
                list.add(sb.toString());
            }
            // chain starting with 'b': "b","bc","bcd",... length 10
            sb = new StringBuilder("b");
            list.add(sb.toString());
            for (int i = 1; i < 10; i++) {
                sb.append((char) ('b' + i));
                list.add(sb.toString());
            }
            while (list.size() < 100000) list.add(randomWord(1 + rng.nextInt(10), rng));
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 95: n = 1e5, only lengths 2..10 (no single letters) → None
        {
            String[] w = distinctRandom(100000, 2, 10, rng);
            writeTestCase(tc++, w);
        }

        // TC 96: n = 1e5, single letters a..z + chains only for 'a'
        {
            List<String> list = new ArrayList<>();
            for (char c = 'a'; c <= 'z'; c++) list.add("" + c);
            // full chain "a".."aaaaaaaaaa"
            for (int i = 2; i <= 10; i++) {
                char[] arr = new char[i];
                Arrays.fill(arr, 'a');
                list.add(new String(arr));
            }
            while (list.size() < 100000) list.add(randomWord(2 + rng.nextInt(9), rng));
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 97: n = 1e5, alphabet {a,b} small pool, random lengths
        {
            Set<String> s = new LinkedHashSet<>();
            while (s.size() < 100000) {
                int len = 1 + rng.nextInt(10);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < len; i++) sb.append((char) ('a' + rng.nextInt(2)));
                s.add(sb.toString());
            }
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 98: n = 1e5, all distinct-length prefixes of a single long word (chain guaranteed)
        {
            List<String> list = new ArrayList<>();
            String longWord = "abcdefghij";     // length 10, complete if all prefixes present
            for (int i = 1; i <= 10; i++) list.add(longWord.substring(0, i));
            while (list.size() < 100000) list.add(randomWord(1 + rng.nextInt(10), rng));
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 99: n = 1e5, chain of "m","mn","mno",... + noise
        {
            List<String> list = new ArrayList<>();
            String chainWord = "mnopqrstuv";
            for (int i = 1; i <= 10; i++) list.add(chainWord.substring(0, i));
            while (list.size() < 100000) list.add(randomWord(1 + rng.nextInt(10), rng));
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Longest Word with All Prefixes.");
    }
}