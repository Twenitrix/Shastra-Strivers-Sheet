import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: Trie via HashMap children + end-of-word marker.
    private static List<String> solve(int q, String[][] ops) {
        Map<Long, Integer> child = new HashMap<>();   // (node<<5 | char) -> childNode
        List<Boolean> isEnd = new ArrayList<>();
        isEnd.add(false);                             // root is not end-of-word

        List<String> out = new ArrayList<>();

        for (int i = 0; i < q; i++) {
            String op = ops[i][0];
            String w  = ops[i][1];

            if (op.equals("INSERT")) {
                int node = 0;
                for (int j = 0; j < w.length(); j++) {
                    long key = ((long) node << 5) | (w.charAt(j) - 'a');
                    Integer nxt = child.get(key);
                    if (nxt == null) {
                        nxt = isEnd.size();
                        child.put(key, nxt);
                        isEnd.add(false);
                    }
                    node = nxt;
                }
                isEnd.set(node, true);
            } else if (op.equals("SEARCH")) {
                int node = 0;
                boolean ok = true;
                for (int j = 0; j < w.length(); j++) {
                    long key = ((long) node << 5) | (w.charAt(j) - 'a');
                    Integer nxt = child.get(key);
                    if (nxt == null) { ok = false; break; }
                    node = nxt;
                }
                out.add((ok && isEnd.get(node)) ? "true" : "false");
            } else if (op.equals("STARTSWITH")) {
                int node = 0;
                boolean ok = true;
                for (int j = 0; j < w.length(); j++) {
                    long key = ((long) node << 5) | (w.charAt(j) - 'a');
                    Integer nxt = child.get(key);
                    if (nxt == null) { ok = false; break; }
                    node = nxt;
                }
                out.add(ok ? "true" : "false");
            }
        }
        return out;
    }

    private static void writeTestCase(int index, int q, String[][] ops) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir  = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        File inFile  = new File(inputDir,  String.format("input%02d.txt",  index));
        File outFile = new File(outputDir, String.format("output%02d.txt", index));

        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(q);
            for (int i = 0; i < q; i++) pw.println(ops[i][0] + " " + ops[i][1]);
        }

        List<String> ans = solve(q, ops);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            for (String line : ans) pw.println(line);
        }
    }

    private static String randStr(int len, int alpha, Random rng) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append((char) ('a' + rng.nextInt(alpha)));
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        // TC 00: example 1
        writeTestCase(tc++, 6, new String[][]{
            {"INSERT","apple"},{"INSERT","app"},{"SEARCH","app"},
            {"SEARCH","appl"},{"STARTSWITH","appl"},{"STARTSWITH","banana"}
        });

        // TC 01: example 2
        writeTestCase(tc++, 5, new String[][]{
            {"INSERT","cat"},{"INSERT","car"},{"SEARCH","cat"},
            {"SEARCH","cab"},{"STARTSWITH","ca"}
        });

        // TC 02: n = 1, single insert
        writeTestCase(tc++, 1, new String[][]{{"INSERT","a"}});

        // TC 03: insert + search match
        writeTestCase(tc++, 2, new String[][]{{"INSERT","a"},{"SEARCH","a"}});

        // TC 04: insert + search mismatch
        writeTestCase(tc++, 2, new String[][]{{"INSERT","a"},{"SEARCH","b"}});

        // TC 05: search on empty trie
        writeTestCase(tc++, 1, new String[][]{{"SEARCH","x"}});

        // TC 06: startswith on empty trie
        writeTestCase(tc++, 1, new String[][]{{"STARTSWITH","x"}});

        // TC 07: duplicate insert (idempotent)
        writeTestCase(tc++, 4, new String[][]{
            {"INSERT","x"},{"INSERT","x"},{"SEARCH","x"},{"STARTSWITH","x"}
        });

        // TC 08: prefix longer than word
        writeTestCase(tc++, 2, new String[][]{
            {"INSERT","a"},{"STARTSWITH","abc"}
        });

        // TC 09: chain "a","ab","abc"
        writeTestCase(tc++, 5, new String[][]{
            {"INSERT","a"},{"INSERT","ab"},{"INSERT","abc"},
            {"SEARCH","a"},{"SEARCH","abc"}
        });

        // TC 10: search prefix as word (should be false)
        writeTestCase(tc++, 4, new String[][]{
            {"INSERT","apple"},{"SEARCH","appl"},{"SEARCH","apple"},{"STARTSWITH","appl"}
        });

        // TC 11: startswith full word
        writeTestCase(tc++, 4, new String[][]{
            {"INSERT","hello"},{"STARTSWITH","hello"},{"STARTSWITH","hell"},{"STARTSWITH","helloo"}
        });

        // TC 12: single char inserts, search each
        writeTestCase(tc++, 6, new String[][]{
            {"INSERT","a"},{"INSERT","b"},{"INSERT","c"},
            {"SEARCH","a"},{"SEARCH","b"},{"SEARCH","d"}
        });

        // TC 13: startswith same prefix for many words
        writeTestCase(tc++, 5, new String[][]{
            {"INSERT","apple"},{"INSERT","apply"},{"INSERT","appetite"},
            {"STARTSWITH","app"},{"STARTSWITH","appl"}
        });

        // TC 14: sibling words
        writeTestCase(tc++, 5, new String[][]{
            {"INSERT","aa"},{"INSERT","ab"},{"INSERT","ac"},
            {"STARTSWITH","a"},{"SEARCH","ad"}
        });

        // TC 15: long word insert + search
        writeTestCase(tc++, 3, new String[][]{
            {"INSERT","abcdefghij"},
            {"SEARCH","abcdefghij"},{"SEARCH","abcdefghi"}
        });

        // TC 16: startswith single char
        writeTestCase(tc++, 3, new String[][]{
            {"INSERT","xyz"},{"STARTSWITH","x"},{"STARTSWITH","y"}
        });

        // TC 17: identical prefixes with different endings
        writeTestCase(tc++, 5, new String[][]{
            {"INSERT","prefix1"},{"INSERT","prefix2"},{"INSERT","prefix3"},
            {"SEARCH","prefix2"},{"STARTSWITH","prefix"}
        });

        // TC 18: empty-word boundary not possible (min length 1), so single char
        writeTestCase(tc++, 3, new String[][]{
            {"INSERT","z"},{"SEARCH","z"},{"STARTSWITH","z"}
        });

        // TC 19: insert many, search none
        writeTestCase(tc++, 4, new String[][]{
            {"INSERT","aaa"},{"INSERT","bbb"},{"INSERT","ccc"},{"SEARCH","ddd"}
        });

        // ---- Category B: Small random tests (20 - 39) ----
        for (int k = 0; k < 20; k++) {
            int q = 10 + rng.nextInt(40);           // 10..49
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                int op = rng.nextInt(3);
                String w = randStr(1 + rng.nextInt(3), 3, rng);
                ops[i] = new String[]{
                    (op == 0) ? "INSERT" : (op == 1) ? "SEARCH" : "STARTSWITH", w
                };
            }
            writeTestCase(tc++, q, ops);
        }

        // ---- Category C: Medium random tests (40 - 59) ----
        for (int k = 0; k < 20; k++) {
            int q = 200 + rng.nextInt(800);         // 200..999
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                int op = rng.nextInt(3);
                String w = randStr(1 + rng.nextInt(8), 5, rng);
                ops[i] = new String[]{
                    (op == 0) ? "INSERT" : (op == 1) ? "SEARCH" : "STARTSWITH", w
                };
            }
            writeTestCase(tc++, q, ops);
        }

        // ---- Category D: Large / stress tests (60 - 99) ----

        // TC 60: 1e5 alternating INSERT + SEARCH, distinct words
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                if (i % 2 == 0) ops[i] = new String[]{"INSERT", "w" + (i / 2)};
                else ops[i] = new String[]{"SEARCH", "w" + (i / 2)};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 61: 1e5 alternating INSERT + STARTSWITH
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                if (i % 2 == 0) ops[i] = new String[]{"INSERT", "abc" + (i / 2)};
                else ops[i] = new String[]{"STARTSWITH", "abc"};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 62: 1e5 SEARCH on empty trie
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) ops[i] = new String[]{"SEARCH", "abc"};
            writeTestCase(tc++, q, ops);
        }

        // TC 63: 1e5 STARTSWITH on empty trie
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) ops[i] = new String[]{"STARTSWITH", "abc"};
            writeTestCase(tc++, q, ops);
        }

        // TC 64: 1e5 INSERT (no output)
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) ops[i] = new String[]{"INSERT", "w" + (i % 100)};
            writeTestCase(tc++, q, ops);
        }

        // TC 65: 1e5 single-char inserts over full alphabet
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                char c = (char) ('a' + rng.nextInt(26));
                ops[i] = new String[]{"INSERT", "" + c};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 66: chain insert "a", "aa", ..., then search each
        {
            List<String[]> t = new ArrayList<>();
            int totalChars = 0, len = 1;
            while (totalChars + len <= 50000 && t.size() < 50000) {
                char[] arr = new char[len];
                Arrays.fill(arr, 'a');
                t.add(new String[]{"INSERT", new String(arr)});
                totalChars += len;
                len++;
            }
            // search each
            len = 1;
            while (t.size() < 100000 && totalChars + len <= 1000000) {
                char[] arr = new char[len];
                Arrays.fill(arr, 'a');
                t.add(new String[]{"SEARCH", new String(arr)});
                totalChars += len;
                len++;
            }
            writeTestCase(tc++, t.size(), t.toArray(new String[0][]));
        }

        // TC 67: shared prefix chain
        {
            int q = 1000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                if (i % 3 == 0) ops[i] = new String[]{"INSERT", "prefix" + i};
                else if (i % 3 == 1) ops[i] = new String[]{"SEARCH", "prefix" + (i - 1)};
                else ops[i] = new String[]{"STARTSWITH", "prefix"};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 68: 1000 inserts, 1000 searches
        {
            int q = 2000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < 1000; i++) ops[i] = new String[]{"INSERT", "word" + i};
            for (int i = 1000; i < q; i++) ops[i] = new String[]{"SEARCH", "word" + (i - 1000)};
            writeTestCase(tc++, q, ops);
        }

        // TC 69: 1000 inserts, 1000 startswith
        {
            int q = 2000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < 1000; i++) ops[i] = new String[]{"INSERT", "ab" + i};
            for (int i = 1000; i < q; i++) ops[i] = new String[]{"STARTSWITH", "ab" + (i - 1000)};
            writeTestCase(tc++, q, ops);
        }

        // TC 70: 1000 mixed on {a,b}
        {
            int q = 1000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                int op = rng.nextInt(3);
                String w = randStr(1 + rng.nextInt(5), 2, rng);
                ops[i] = new String[]{
                    (op == 0) ? "INSERT" : (op == 1) ? "SEARCH" : "STARTSWITH", w
                };
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 71: same word inserted many times then searched
        {
            int q = 200;
            String[][] ops = new String[q][2];
            for (int i = 0; i < 100; i++) ops[i] = new String[]{"INSERT", "repeat"};
            for (int i = 100; i < q; i++) ops[i] = new String[]{"SEARCH", "repeat"};
            writeTestCase(tc++, q, ops);
        }

        // TC 72: 1e5 mixed on small alphabet {a,b}
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                int op = rng.nextInt(3);
                String w = randStr(1 + rng.nextInt(5), 2, rng);
                ops[i] = new String[]{
                    (op == 0) ? "INSERT" : (op == 1) ? "SEARCH" : "STARTSWITH", w
                };
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 73: 1e5 mixed on full alphabet
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                int op = rng.nextInt(3);
                String w = randStr(1 + rng.nextInt(8), 26, rng);
                ops[i] = new String[]{
                    (op == 0) ? "INSERT" : (op == 1) ? "SEARCH" : "STARTSWITH", w
                };
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 74: 1e5 all STARTSWITH with missing prefix
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            ops[0] = new String[]{"INSERT", "z"};
            for (int i = 1; i < q; i++) ops[i] = new String[]{"STARTSWITH", "y"};
            writeTestCase(tc++, q, ops);
        }

        // TC 75: 1e5 alternating INSERT+SEARCH different words
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                if (i % 2 == 0) ops[i] = new String[]{"INSERT", "w" + (i / 2)};
                else ops[i] = new String[]{"SEARCH", "w" + (i / 2)};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 76: deep chain insert "a","aa",..."aaa...(1000)"
        {
            List<String[]> t = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            int total = 0;
            for (int len = 1; len <= 1000 && total + len <= 500000; len++) {
                sb.append('a');
                t.add(new String[]{"INSERT", sb.toString()});
                total += len;
                if (t.size() >= 100000) break;
            }
            // pad with searches
            String lw = sb.toString();
            while (t.size() < 100000 && total + lw.length() <= 1000000) {
                t.add(new String[]{"SEARCH", lw});
                total += lw.length();
            }
            writeTestCase(tc++, t.size(), t.toArray(new String[0][]));
        }

        // TC 77: 1e5 single-char inserts cycling a-z
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                char c = (char) ('a' + (i % 26));
                ops[i] = new String[]{"INSERT", "" + c};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 78: 1e5 single-char searches cycling a-z (with insert warm-up)
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int c = 0; c < 26; c++) ops[c] = new String[]{"INSERT", "" + (char) ('a' + c)};
            for (int i = 26; i < q; i++) {
                char c = (char) ('a' + (i % 26));
                ops[i] = new String[]{"SEARCH", "" + c};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 79: 1e5 single-char STARTSWITH cycling a-z
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            ops[0] = new String[]{"INSERT", "a"};
            for (int i = 1; i < q; i++) {
                char c = (char) ('a' + (i % 26));
                ops[i] = new String[]{"STARTSWITH", "" + c};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 80: 1000 inserts a-prefixed, 1000 SEARCH "a"
        {
            int q = 2000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < 1000; i++) ops[i] = new String[]{"INSERT", "a" + i};
            for (int i = 1000; i < q; i++) ops[i] = new String[]{"SEARCH", "a"};
            writeTestCase(tc++, q, ops);
        }

        // TC 81: all possible length-2 words over {a,b,c} (9 total), search each
        {
            List<String[]> t = new ArrayList<>();
            for (char c1 = 'a'; c1 <= 'c'; c1++)
                for (char c2 = 'a'; c2 <= 'c'; c2++)
                    t.add(new String[]{"INSERT", "" + c1 + c2});
            for (char c1 = 'a'; c1 <= 'c'; c1++)
                for (char c2 = 'a'; c2 <= 'c'; c2++)
                    t.add(new String[]{"SEARCH", "" + c1 + c2});
            writeTestCase(tc++, t.size(), t.toArray(new String[0][]));
        }

        // TC 82: 1e5 INSERT unique words
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) ops[i] = new String[]{"INSERT", "w" + i};
            writeTestCase(tc++, q, ops);
        }

        // TC 83: 1e5 SEARCH unique (on empty trie, all false)
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) ops[i] = new String[]{"SEARCH", "w" + i};
            writeTestCase(tc++, q, ops);
        }

        // TC 84: 10000 mixed on full alphabet
        {
            int q = 10000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                int op = rng.nextInt(3);
                String w = randStr(1 + rng.nextInt(10), 26, rng);
                ops[i] = new String[]{
                    (op == 0) ? "INSERT" : (op == 1) ? "SEARCH" : "STARTSWITH", w
                };
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 85: all words length 1, all ops cycling
        {
            int q = 100;
            String[][] ops = new String[q][2];
            String[] types = {"INSERT", "SEARCH", "STARTSWITH"};
            for (int i = 0; i < q; i++) {
                char c = (char) ('a' + (i % 26));
                ops[i] = new String[]{types[i % 3], "" + c};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 86: 1e5 mixed, all words single-char
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            String[] types = {"INSERT", "SEARCH", "STARTSWITH"};
            for (int i = 0; i < q; i++) {
                char c = (char) ('a' + (i % 26));
                ops[i] = new String[]{types[i % 3], "" + c};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 87: 1e5 alternating INSERT+SEARCH, insert random
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                if (i % 2 == 0) ops[i] = new String[]{"INSERT", randStr(1 + rng.nextInt(10), 26, rng)};
                else ops[i] = new String[]{"SEARCH", randStr(1 + rng.nextInt(10), 26, rng)};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 88: 1e5 alternating INSERT+STARTSWITH
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                if (i % 2 == 0) ops[i] = new String[]{"INSERT", "abc" + i};
                else ops[i] = new String[]{"STARTSWITH", "abc" + (i / 2)};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 89: 1e5 INSERT then STARTSWITH same prefix
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < 50000; i++) ops[i] = new String[]{"INSERT", "pre" + i};
            for (int i = 50000; i < q; i++) ops[i] = new String[]{"STARTSWITH", "pre" + (i - 50000)};
            writeTestCase(tc++, q, ops);
        }

        // TC 90: 5000 INSERT, 5000 SEARCH, all same word
        {
            int q = 10000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < 5000; i++) ops[i] = new String[]{"INSERT", "same"};
            for (int i = 5000; i < q; i++) ops[i] = new String[]{"SEARCH", "same"};
            writeTestCase(tc++, q, ops);
        }

        // TC 91: 1e5 alternating INSERT + SEARCH, small alphabet
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                String w = randStr(1 + rng.nextInt(3), 2, rng);
                if (i % 2 == 0) ops[i] = new String[]{"INSERT", w};
                else ops[i] = new String[]{"SEARCH", w};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 92: 1e5 INSERT (single-char cycling) + SEARCH (single-char cycling)
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                char c = (char) ('a' + (i % 26));
                if (i % 2 == 0) ops[i] = new String[]{"INSERT", "" + c};
                else ops[i] = new String[]{"SEARCH", "" + c};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 93: 1e5 INSERT distinct 8-char random
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            Set<String> used = new HashSet<>();
            int idx = 0;
            while (idx < q) {
                String w = randStr(8, 26, rng);
                if (used.add(w)) ops[idx++] = new String[]{"INSERT", w};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 94: 1e5 alternating INSERT+STARTSWITH on common prefix
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                if (i % 2 == 0) ops[i] = new String[]{"INSERT", "common" + i};
                else ops[i] = new String[]{"STARTSWITH", "common"};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 95: 1e5 alternating INSERT+SEARCH same single word
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                if (i % 2 == 0) ops[i] = new String[]{"INSERT", "target"};
                else ops[i] = new String[]{"SEARCH", "target"};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 96: 1e5 alternating INSERT+STARTSWITH single word
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                if (i % 2 == 0) ops[i] = new String[]{"INSERT", "target"};
                else ops[i] = new String[]{"STARTSWITH", "target"};
            }
            writeTestCase(tc++, q, ops);
        }

        // TC 97: 1e5 INSERT of empty-ish (all single char 'a' repeated)
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) ops[i] = new String[]{"INSERT", "a"};
            writeTestCase(tc++, q, ops);
        }

        // TC 98: 1e5 SEARCH after single INSERT
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            ops[0] = new String[]{"INSERT", "onlyword"};
            for (int i = 1; i < q; i++) ops[i] = new String[]{"SEARCH", "onlyword"};
            writeTestCase(tc++, q, ops);
        }

        // TC 99: 1e5 mixed, all short 2-char over alphabet {a,b,c,d}
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            String[] types = {"INSERT", "SEARCH", "STARTSWITH"};
            for (int i = 0; i < q; i++) {
                String w = randStr(2, 4, rng);
                ops[i] = new String[]{types[i % 3], w};
            }
            writeTestCase(tc++, q, ops);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Trie Implementation and Operations.");
    }
}