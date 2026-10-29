import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: sort by length, DP over predecessor chains.
    // For each word, try removing each character and see if the resulting
    // (length-1) string exists in our map with a valid chain length.
    private static int solve(int n, String[] words) {
        // Sort by length ascending
        String[] sorted = words.clone();
        Arrays.sort(sorted, Comparator.comparingInt(String::length));

        Map<String, Integer> dp = new HashMap<>();
        int best = 1;

        for (String w : sorted) {
            int cur = 1;
            int L = w.length();
            // try removing each character to form a predecessor
            for (int i = 0; i < L; i++) {
                String pred = w.substring(0, i) + w.substring(i + 1);
                if (dp.containsKey(pred)) {
                    cur = Math.max(cur, dp.get(pred) + 1);
                }
            }
            dp.put(w, cur);
            if (cur > best) best = cur;
        }
        return best;
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
            for (String w : words) pw.println(w);
        }

        int ans = solve(words.length, words);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    // Random lowercase word of length len.
    private static String randomWord(int len, Random rng) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append((char) ('a' + rng.nextInt(26)));
        return sb.toString();
    }

    // Insert one random char into s.
    private static String insertOne(String s, char c, int pos) {
        return s.substring(0, pos) + c + s.substring(pos);
    }

    // Generate n distinct random words, distinct guaranteed.
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
        writeTestCase(tc++, new String[]{"a", "b", "ba", "bca", "bda", "bdca"});

        // TC 01: example 2
        writeTestCase(tc++, new String[]{"xbc", "pcxbcf", "xb", "cxbc", "pcxbc"});

        // TC 02: n = 1
        writeTestCase(tc++, new String[]{"a"});

        // TC 03: n = 1, max-length word
        writeTestCase(tc++, new String[]{"abcdefghijklmnop"});

        // TC 04: two words, valid chain
        writeTestCase(tc++, new String[]{"a", "ab"});

        // TC 05: two words, invalid (same length)
        writeTestCase(tc++, new String[]{"ab", "ba"});

        // TC 06: two words, length diff 2 (invalid)
        writeTestCase(tc++, new String[]{"a", "abc"});

        // TC 07: all same length -> answer 1
        writeTestCase(tc++, new String[]{"abc", "def", "ghi", "jkl"});

        // TC 08: perfect chain "a", "ab", "abc", ..., "abcdefgh" (len 8)
        {
            String[] w = new String[8];
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                sb.append((char) ('a' + i));
                w[i] = sb.toString();
            }
            writeTestCase(tc++, w);
        }

        // TC 09: chain of "a","aa","aaa",...,"aaaaaaaa" (len 8) — repeated char
        {
            String[] w = new String[8];
            for (int i = 0; i < 8; i++) {
                char[] arr = new char[i + 1];
                Arrays.fill(arr, 'a');
                w[i] = new String(arr);
            }
            writeTestCase(tc++, w);
        }

        // TC 10: chain from length 1 to 16 (max)
        {
            String[] w = new String[16];
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                sb.append((char) ('a' + (i % 26)));
                w[i] = sb.toString();
            }
            writeTestCase(tc++, w);
        }

        // TC 11: no valid chain (all random length-2 words, no length-1)
        writeTestCase(tc++, new String[]{"ab", "cd", "ef", "gh", "ij"});

        // TC 12: chain plus distractor
        writeTestCase(tc++, new String[]{"a", "ab", "abc", "xyz", "xyzw"});

        // TC 13: two equal-length chains (best is either)
        writeTestCase(tc++, new String[]{"a", "ab", "abc", "b", "bc", "bcd"});

        // TC 14: diamond — two different predecessors
        writeTestCase(tc++, new String[]{"ab", "abc", "abd", "abcd"});

        // TC 15: "a" only in middle of chain (helps break)
        writeTestCase(tc++, new String[]{"ab", "abc", "abcd", "abcde"});

        // TC 16: duplicates-free, but chain breaks at length 3
        writeTestCase(tc++, new String[]{"a", "ab", "xyz", "xyzw", "xyzwv"});

        // TC 17: 16-letter chain with repeated char in the middle
        writeTestCase(tc++, new String[]{
            "a", "aa", "aaa", "aaaa", "aaaaa",
            "aaaaaa", "aaaaaaa", "aaaaaaaa",
            "aaaaaaaaa", "aaaaaaaaaa", "aaaaaaaaaaa",
            "aaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaaa",
            "aaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaa"
        });

        // TC 18: 16-letter chain with increasing alphabet at each step (only 16 chars)
        {
            String[] w = new String[16];
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                sb.append((char) ('a' + i));
                w[i] = sb.toString();
            }
            writeTestCase(tc++, w);
        }

        // TC 19: two disconnected valid chains
        writeTestCase(tc++, new String[]{
            "a", "ab", "abc", "abcd",
            "x", "xy", "xyz", "xyzw"
        });

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int n = 1 + rng.nextInt(8);
            String[] w = distinctRandom(n, 1, 4, rng);
            writeTestCase(tc++, w);
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        // Build guaranteed chains of length up to 16 and add distractors.
        for (int k = 0; k < 30; k++) {
            int chainLen = 4 + rng.nextInt(13);      // 4..16
            List<String> list = new ArrayList<>();
            String cur = randomWord(1, rng);
            list.add(cur);
            while (cur.length() < chainLen) {
                int pos = rng.nextInt(cur.length() + 1);
                char c = (char) ('a' + rng.nextInt(26));
                cur = insertOne(cur, c, pos);
                list.add(cur);
            }
            // add some distractors
            int extra = rng.nextInt(20);
            for (int e = 0; e < extra; e++) {
                int len = 1 + rng.nextInt(16);
                list.add(randomWord(len, rng));
            }
            // dedupe
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            list = new ArrayList<>(s);
            // shuffle input order (input order doesn't matter)
            Collections.shuffle(list, rng);
            // cap to 1000
            if (list.size() > 1000) list = list.subList(0, 1000);
            writeTestCase(tc++, list.toArray(new String[0]));
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 1000, 16-length chain ("a".."abcdefghijklmnop") + 984 random distractors
        {
            List<String> list = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                sb.append((char) ('a' + i));
                list.add(sb.toString());
            }
            while (list.size() < 1000) {
                list.add(randomWord(16, rng));
            }
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            list = new ArrayList<>(s);
            writeTestCase(tc++, list.toArray(new String[0]));
        }

        // TC 81: n = 1000, "aaaaaaaaaaaaaaaa" 16-length chain + distractors
        {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                char[] arr = new char[i + 1];
                Arrays.fill(arr, 'a');
                list.add(new String(arr));
            }
            while (list.size() < 1000) {
                list.add(randomWord(16, rng));
            }
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            list = new ArrayList<>(s);
            writeTestCase(tc++, list.toArray(new String[0]));
        }

        // TC 82: n = 1000, all length 1 (answer 1)
        {
            String[] w = distinctRandom(26, 1, 1, rng);
            writeTestCase(tc++, w);
        }

        // TC 83: n = 1000, all same length (16), answer 1
        {
            String[] w = distinctRandom(1000, 16, 16, rng);
            writeTestCase(tc++, w);
        }

                // TC 84: n = 1000, half short half long, no chains
        {
            List<String> list = new ArrayList<>(Arrays.asList(distinctRandom(500, 1, 8, rng)));
            list.addAll(Arrays.asList(distinctRandom(500, 15, 16, rng)));
            writeTestCase(tc++, list.toArray(new String[0]));
        }
        // TC 85: n = 1000, chain of a's embedded in noise
        {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                char[] arr = new char[i + 1];
                Arrays.fill(arr, 'a');
                list.add(new String(arr));
            }
            while (list.size() < 1000) list.add(randomWord(16, rng));
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 86: n = 1000, chain of "abcdefghijklmnop" prefixes + noise
        {
            List<String> list = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                sb.append((char) ('a' + i));
                list.add(sb.toString());
            }
            while (list.size() < 1000) list.add(randomWord(15, rng));
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 87 - 91: n = 1000, fully random with varying lengths
        for (int k = 0; k < 5; k++) {
            String[] w = distinctRandom(1000, 1, 16, rng);
            writeTestCase(tc++, w);
        }

        // TC 92: n = 1000, 999 words length 16, one short word "a" (isolated)
        {
            Set<String> s = new LinkedHashSet<>();
            s.add("a");
            while (s.size() < 1000) s.add(randomWord(16, rng));
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 93: n = 1000, 16 word chain + 984 noise, shuffled
        {
            List<String> list = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                sb.append((char) ('a' + i));
                list.add(sb.toString());
            }
            while (list.size() < 1000) list.add(randomWord(16, rng));
            Collections.shuffle(list, rng);
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

       // TC 94: n = 1000, half length 15, half length 16 (no chain possible due to missing lengths)
        {
            List<String> list = new ArrayList<>(Arrays.asList(distinctRandom(500, 15, 15, rng)));
            list.addAll(Arrays.asList(distinctRandom(500, 16, 16, rng)));
            writeTestCase(tc++, list.toArray(new String[0]));
        }

        // TC 95: n = 500, chain of "z".."zzzz.." 16 chars + noise
        {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                char[] arr = new char[i + 1];
                Arrays.fill(arr, 'z');
                list.add(new String(arr));
            }
            while (list.size() < 500) list.add(randomWord(16, rng));
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 96: n = 1000, many random length-2 words (checking invalid-length neighbors)
        {
            Set<String> s = new LinkedHashSet<>();
            while (s.size() < 1000) {
                int len = 2 + rng.nextInt(15);
                s.add(randomWord(len, rng));
            }
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 97: n = 1000, "a" through "abcdefghijklmnop" plus their one-char-deletions
        {
            Set<String> s = new LinkedHashSet<>();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                sb.append((char) ('a' + i));
                String w = sb.toString();
                s.add(w);
                // add one-char deletions of w (length-1 strings, all length i)
                if (w.length() > 1) {
                    for (int j = 0; j < w.length(); j++) {
                        s.add(w.substring(0, j) + w.substring(j + 1));
                        if (s.size() >= 1000) break;
                    }
                }
            }
            while (s.size() < 1000) s.add(randomWord(16, rng));
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 98: n = 1000, random with alphabet {a,b} small subset
        {
            Set<String> s = new LinkedHashSet<>();
            while (s.size() < 1000) {
                int len = 1 + rng.nextInt(16);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < len; i++) sb.append((char) ('a' + rng.nextInt(2)));
                s.add(sb.toString());
            }
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        // TC 99: n = 1000, 16-length chain + heavy noise, shuffled
        {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                char[] arr = new char[i + 1];
                Arrays.fill(arr, 'a');
                list.add(new String(arr));
            }
            while (list.size() < 1000) list.add(randomWord(16, rng));
            Collections.shuffle(list, rng);
            LinkedHashSet<String> s = new LinkedHashSet<>(list);
            writeTestCase(tc++, s.toArray(new String[0]));
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Longest String Chain.");
    }
}