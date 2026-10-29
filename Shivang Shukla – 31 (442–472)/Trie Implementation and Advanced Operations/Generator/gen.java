import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Trie reference solver using hashmap-based children.
    private static List<String> solve(int q, String[][] ops) {
        Map<Long, Integer> child = new HashMap<>();
        List<Integer> pCount = new ArrayList<>();   // words passing through this node
        List<Integer> endCnt = new ArrayList<>();   // is this node the end of a word
        pCount.add(0);
        endCnt.add(0);

        List<String> out = new ArrayList<>();

        for (int i = 0; i < q; i++) {
            String op = ops[i][0];
            String w  = ops[i][1];

            if (op.equals("INSERT")) {
                // First check if word already exists
                int node = 0;
                boolean exists = true;
                for (int j = 0; j < w.length(); j++) {
                    long key = ((long) node << 5) | (w.charAt(j) - 'a');
                    Integer nxt = child.get(key);
                    if (nxt == null) { exists = false; break; }
                    node = nxt;
                }
                if (exists && node != 0) {
                    // need to verify endCnt at final node
                    // walk again
                    int cur = 0;
                    for (int j = 0; j < w.length(); j++) {
                        long key = ((long) cur << 5) | (w.charAt(j) - 'a');
                        cur = child.get(key);
                    }
                    if (endCnt.get(cur) > 0) continue;
                }
                // insert fresh
                node = 0;
                for (int j = 0; j < w.length(); j++) {
                    long key = ((long) node << 5) | (w.charAt(j) - 'a');
                    Integer nxt = child.get(key);
                    if (nxt == null) {
                        nxt = pCount.size();
                        child.put(key, nxt);
                        pCount.add(0);
                        endCnt.add(0);
                    }
                    node = nxt;
                    pCount.set(node, pCount.get(node) + 1);
                }
                endCnt.set(node, 1);
            } else if (op.equals("DELETE")) {
                int node = 0;
                boolean ok = true;
                for (int j = 0; j < w.length(); j++) {
                    long key = ((long) node << 5) | (w.charAt(j) - 'a');
                    Integer nxt = child.get(key);
                    if (nxt == null) { ok = false; break; }
                    node = nxt;
                }
                if (!ok || endCnt.get(node) == 0) continue;
                endCnt.set(node, 0);
                int cur = 0;
                for (int j = 0; j < w.length(); j++) {
                    long key = ((long) cur << 5) | (w.charAt(j) - 'a');
                    cur = child.get(key);
                    pCount.set(cur, pCount.get(cur) - 1);
                }
            } else if (op.equals("SEARCH")) {
                int node = 0;
                boolean ok = true;
                for (int j = 0; j < w.length(); j++) {
                    long key = ((long) node << 5) | (w.charAt(j) - 'a');
                    Integer nxt = child.get(key);
                    if (nxt == null) { ok = false; break; }
                    node = nxt;
                }
                out.add((ok && endCnt.get(node) > 0) ? "true" : "false");
            } else if (op.equals("PREFIX")) {
                int node = 0;
                boolean ok = true;
                for (int j = 0; j < w.length(); j++) {
                    long key = ((long) node << 5) | (w.charAt(j) - 'a');
                    Integer nxt = child.get(key);
                    if (nxt == null) { ok = false; break; }
                    node = nxt;
                }
                out.add(ok ? "true" : "false");
            } else if (op.equals("COUNT")) {
                int node = 0;
                boolean ok = true;
                for (int j = 0; j < w.length(); j++) {
                    long key = ((long) node << 5) | (w.charAt(j) - 'a');
                    Integer nxt = child.get(key);
                    if (nxt == null) { ok = false; break; }
                    node = nxt;
                }
                out.add(ok ? String.valueOf(pCount.get(node)) : "0");
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

        // ===== Hand-crafted (00 - 19) =====
        writeTestCase(tc++, 10, new String[][]{
            {"INSERT","apple"},{"INSERT","app"},{"SEARCH","app"},
            {"PREFIX","ap"},{"COUNT","ap"},{"DELETE","app"},
            {"SEARCH","app"},{"SEARCH","apple"},{"COUNT","ap"},{"PREFIX","appl"}
        });
        writeTestCase(tc++, 6, new String[][]{
            {"INSERT","cat"},{"INSERT","car"},{"INSERT","cat"},
            {"COUNT","ca"},{"DELETE","dog"},{"SEARCH","cat"}
        });
        writeTestCase(tc++, 2, new String[][]{{"INSERT","a"},{"SEARCH","a"}});
        writeTestCase(tc++, 2, new String[][]{{"INSERT","a"},{"SEARCH","b"}});
        writeTestCase(tc++, 1, new String[][]{{"SEARCH","x"}});
        writeTestCase(tc++, 1, new String[][]{{"PREFIX","x"}});
        writeTestCase(tc++, 1, new String[][]{{"COUNT","x"}});
        writeTestCase(tc++, 2, new String[][]{{"DELETE","x"},{"SEARCH","x"}});
        writeTestCase(tc++, 4, new String[][]{
            {"INSERT","x"},{"INSERT","x"},{"COUNT","x"},{"SEARCH","x"}
        });
        writeTestCase(tc++, 3, new String[][]{
            {"INSERT","abc"},{"DELETE","abc"},{"SEARCH","abc"}
        });
        writeTestCase(tc++, 3, new String[][]{
            {"INSERT","abc"},{"DELETE","xyz"},{"SEARCH","abc"}
        });
        writeTestCase(tc++, 2, new String[][]{{"INSERT","a"},{"PREFIX","abc"}});
        writeTestCase(tc++, 5, new String[][]{
            {"INSERT","a"},{"INSERT","ab"},{"INSERT","abc"},
            {"COUNT","a"},{"COUNT","ab"}
        });
        writeTestCase(tc++, 6, new String[][]{
            {"INSERT","a"},{"INSERT","b"},{"DELETE","a"},{"DELETE","b"},
            {"SEARCH","a"},{"COUNT","a"}
        });
        writeTestCase(tc++, 4, new String[][]{
            {"INSERT","ab"},{"INSERT","abc"},{"COUNT","ab"},{"COUNT","abc"}
        });
        writeTestCase(tc++, 5, new String[][]{
            {"INSERT","aa"},{"INSERT","ab"},{"COUNT","a"},
            {"PREFIX","aa"},{"PREFIX","ac"}
        });
        writeTestCase(tc++, 6, new String[][]{
            {"INSERT","a"},{"INSERT","ab"},{"INSERT","abc"},
            {"DELETE","ab"},{"SEARCH","ab"},{"COUNT","a"}
        });
        writeTestCase(tc++, 5, new String[][]{
            {"INSERT","a"},{"INSERT","b"},{"INSERT","c"},
            {"COUNT","a"},{"SEARCH","b"}
        });
        writeTestCase(tc++, 5, new String[][]{
            {"INSERT","apple"},{"INSERT","apply"},{"INSERT","apples"},
            {"COUNT","appl"},{"COUNT","app"}
        });
        writeTestCase(tc++, 8, new String[][]{
            {"INSERT","x"},{"DELETE","x"},{"INSERT","x"},{"DELETE","x"},
            {"INSERT","x"},{"SEARCH","x"},{"COUNT","x"},{"PREFIX","x"}
        });

        // ===== Small random (20 - 39) =====
        for (int k = 0; k < 20; k++) {
            int q = 10 + rng.nextInt(40);
            List<String> ins = new ArrayList<>();
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                int op = rng.nextInt(5);
                if (op == 0) {
                    String w = randStr(1 + rng.nextInt(3), 3, rng);
                    ops[i] = new String[]{"INSERT", w};
                    ins.add(w);
                } else if (op == 1) {
                    String w = ins.isEmpty() ? randStr(1 + rng.nextInt(3), 3, rng)
                                             : ins.get(rng.nextInt(ins.size()));
                    ops[i] = new String[]{"SEARCH", w};
                } else if (op == 2) {
                    ops[i] = new String[]{"PREFIX", randStr(1 + rng.nextInt(3), 3, rng)};
                } else if (op == 3) {
                    String w = ins.isEmpty() ? randStr(1 + rng.nextInt(3), 3, rng)
                                             : ins.get(rng.nextInt(ins.size()));
                    ops[i] = new String[]{"DELETE", w};
                } else {
                    ops[i] = new String[]{"COUNT", randStr(1 + rng.nextInt(3), 3, rng)};
                }
            }
            writeTestCase(tc++, q, ops);
        }

        // ===== Medium random (40 - 59) =====
        for (int k = 0; k < 20; k++) {
            int q = 200 + rng.nextInt(800);
            List<String> ins = new ArrayList<>();
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                int op = rng.nextInt(5);
                if (op == 0) {
                    String w = randStr(1 + rng.nextInt(10), 5, rng);
                    ops[i] = new String[]{"INSERT", w}; ins.add(w);
                } else if (op == 1) {
                    String w = ins.isEmpty() ? randStr(1 + rng.nextInt(10), 5, rng)
                                             : ins.get(rng.nextInt(ins.size()));
                    ops[i] = new String[]{"SEARCH", w};
                } else if (op == 2) {
                    ops[i] = new String[]{"PREFIX", randStr(1 + rng.nextInt(10), 5, rng)};
                } else if (op == 3) {
                    String w = ins.isEmpty() ? randStr(1 + rng.nextInt(10), 5, rng)
                                             : ins.get(rng.nextInt(ins.size()));
                    ops[i] = new String[]{"DELETE", w};
                } else {
                    ops[i] = new String[]{"COUNT", randStr(1 + rng.nextInt(10), 5, rng)};
                }
            }
            writeTestCase(tc++, q, ops);
        }

        // ===== Large / stress (60 - 99) =====

        // 60: 1e5 INSERT+SEARCH single-char words
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                char c = (char) ('a' + (i / 2) % 26);
                ops[i] = new String[]{(i % 2 == 0) ? "INSERT" : "SEARCH", "" + c};
            }
            writeTestCase(tc++, q, ops);
        }

        // 61: alternate INSERT/DELETE same word
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) ops[i] = new String[]{(i % 2 == 0) ? "INSERT" : "DELETE", "abc"};
            writeTestCase(tc++, q, ops);
        }

        // 62: 1e5 SEARCH on empty
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) ops[i] = new String[]{"SEARCH", "abc"};
            writeTestCase(tc++, q, ops);
        }

        // 63: chain inserts (long single prefix)
        {
            List<String[]> t = new ArrayList<>();
            int totalChars = 0, len = 1;
            while (totalChars + len <= 100000 && t.size() < 100000) {
                char[] arr = new char[len];
                Arrays.fill(arr, 'a');
                t.add(new String[]{"INSERT", new String(arr)});
                totalChars += len;
                len++;
            }
            while (t.size() < 100000 && totalChars + 3 <= 500000) {
                t.add(new String[]{"COUNT", "aaa"});
                totalChars += 3;
            }
            writeTestCase(tc++, t.size(), t.toArray(new String[0][]));
        }

        // 64: shared prefix chain
        {
            int q = 1000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                if (i % 4 == 0) ops[i] = new String[]{"INSERT", "prefix" + i};
                else if (i % 4 == 1) ops[i] = new String[]{"COUNT", "prefix"};
                else if (i % 4 == 2) ops[i] = new String[]{"PREFIX", "prefix" + (i / 4)};
                else ops[i] = new String[]{"DELETE", "prefix" + (i - 1)};
            }
            writeTestCase(tc++, q, ops);
        }

        // 65: single chars insert + count
        {
            int q = 100;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                char c = (char) ('a' + (i % 26));
                ops[i] = new String[]{(i < 26) ? "INSERT" : "COUNT", "" + c};
            }
            writeTestCase(tc++, q, ops);
        }

        // 66: 1e5 alternating INSERT+DELETE long word + final search
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            char[] arr = new char[100]; Arrays.fill(arr, 'a');
            String lw = new String(arr);
            for (int i = 0; i < q - 1; i++) ops[i] = new String[]{(i % 2 == 0) ? "INSERT" : "DELETE", lw};
            ops[q - 1] = new String[]{"SEARCH", lw};
            writeTestCase(tc++, q, ops);
        }

        // 67: all distinct length-3 insert aaa..ccc
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            int idx = 0;
            for (char c1 = 'a'; c1 <= 'c' && idx < q; c1++)
                for (char c2 = 'a'; c2 <= 'c' && idx < q; c2++)
                    for (char c3 = 'a'; c3 <= 'c' && idx < q; c3++) {
                        ops[idx++] = new String[]{"INSERT", "" + c1 + c2 + c3};
                    }
            while (idx < q) ops[idx++] = new String[]{"INSERT", "zzz"};
            writeTestCase(tc++, q, ops);
        }

        // 68: long single word 1000 chars, 100 ops
        {
            char[] arr = new char[1000]; Arrays.fill(arr, 'a');
            String lw = new String(arr);
            int q = 100;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                int m = i % 5;
                String op = (m == 0) ? "DELETE" : (m == 1) ? "SEARCH" : (m == 2) ? "COUNT" : (m == 3) ? "PREFIX" : "INSERT";
                ops[i] = new String[]{op, lw};
            }
            writeTestCase(tc++, q, ops);
        }

        // 69: 100 inserts length-2 words, COUNT on "a"
        {
            int q = 200;
            String[][] ops = new String[q][2];
            Set<String> used = new HashSet<>();
            int idx = 0;
            for (char c1 = 'a'; c1 <= 'z' && idx < 100; c1++)
                for (char c2 = 'a'; c2 <= 'd' && idx < 100; c2++) {
                    String w = "" + c1 + c2;
                    if (used.add(w)) ops[idx++] = new String[]{"INSERT", w};
                }
            while (idx < q) ops[idx++] = new String[]{"COUNT", "a"};
            writeTestCase(tc++, q, ops);
        }

        // 70: 1000 mixed on {a,b}
        {
            int q = 1000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                int op = rng.nextInt(5);
                String w = randStr(1 + rng.nextInt(5), 2, rng);
                String[] types = {"INSERT","SEARCH","PREFIX","DELETE","COUNT"};
                ops[i] = new String[]{types[op], w};
            }
            writeTestCase(tc++, q, ops);
        }

        // 71: 1000 inserts then 1000 searches/counts
        {
            int q = 2000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < 1000; i++) ops[i] = new String[]{"INSERT", "word" + i};
            for (int i = 1000; i < q; i++)
                ops[i] = new String[]{(i % 2 == 0) ? "SEARCH" : "COUNT", "word" + (i - 1000)};
            writeTestCase(tc++, q, ops);
        }

        // 72: 500 COUNT on missing prefix
        {
            int q = 500;
            String[][] ops = new String[q][2];
            ops[0] = new String[]{"INSERT", "zzz"};
            for (int i = 1; i < q; i++) ops[i] = new String[]{"COUNT", "aaaa"};
            writeTestCase(tc++, q, ops);
        }

        // 73: 1000 PREFIX on empty
        {
            int q = 1000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) ops[i] = new String[]{"PREFIX", "abc"};
            writeTestCase(tc++, q, ops);
        }

        // 74: 1000 DELETE on nonexistent
        {
            int q = 1000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) ops[i] = new String[]{"DELETE", "x" + i};
            writeTestCase(tc++, q, ops);
        }

        // 75: 1e5 all op types single-char words
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            String[] types = {"INSERT","SEARCH","PREFIX","DELETE","COUNT"};
            for (int i = 0; i < q; i++) {
                char c = (char) ('a' + (i % 26));
                ops[i] = new String[]{types[i % 5], "" + c};
            }
            writeTestCase(tc++, q, ops);
        }

        // 76: warm up 1000 a-prefix words then COUNT
        {
            int q = 5000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < 1000; i++) ops[i] = new String[]{"INSERT", "a" + i};
            for (int i = 1000; i < q; i++) ops[i] = new String[]{"COUNT", "a"};
            writeTestCase(tc++, q, ops);
        }

        // 77: interleaved insert/delete/search same word
        {
            int q = 1000;
            String[][] ops = new String[q][2];
            String[] types = {"INSERT","DELETE","SEARCH"};
            for (int i = 0; i < q; i++) ops[i] = new String[]{types[i % 3], "test"};
            writeTestCase(tc++, q, ops);
        }

        // 78: 10000 INSERT over {a,b,c}
        {
            int q = 10000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                char c = (char) ('a' + (i % 3));
                ops[i] = new String[]{"INSERT", "" + c};
            }
            writeTestCase(tc++, q, ops);
        }

        // 79: 1e5 PREFIX on single chars
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            ops[0] = new String[]{"INSERT", "a"};
            for (int i = 1; i < q; i++) {
                char c = (char) ('a' + (i % 26));
                ops[i] = new String[]{"PREFIX", "" + c};
            }
            writeTestCase(tc++, q, ops);
        }

        // 80: 1e5 INSERT no output
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) ops[i] = new String[]{"INSERT", "w" + (i % 100)};
            writeTestCase(tc++, q, ops);
        }

        // 81: warm up then COUNT 1e5 times
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            ops[0] = new String[]{"INSERT", "a"};
            ops[1] = new String[]{"INSERT", "b"};
            for (int i = 2; i < q; i++) ops[i] = new String[]{"COUNT", "a"};
            writeTestCase(tc++, q, ops);
        }

        // 82: 1000 inserts then 1000 deletes
        {
            int q = 2000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < 1000; i++) ops[i] = new String[]{"INSERT", "w" + i};
            for (int i = 1000; i < q; i++) ops[i] = new String[]{"DELETE", "w" + (i - 1000)};
            writeTestCase(tc++, q, ops);
        }

        // 83: a-z insert then COUNT each
        {
            List<String[]> t = new ArrayList<>();
            for (char c = 'a'; c <= 'z'; c++) t.add(new String[]{"INSERT", "" + c});
            for (char c = 'a'; c <= 'z'; c++) t.add(new String[]{"COUNT", "" + c});
            writeTestCase(tc++, t.size(), t.toArray(new String[0][]));
        }

        // 84: 1e5 random single-char inserts
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                char c = (char) ('a' + rng.nextInt(26));
                ops[i] = new String[]{"INSERT", "" + c};
            }
            writeTestCase(tc++, q, ops);
        }

        // 85: 500 inserts, 500 counts, 500 deletes, 500 searches
        {
            int q = 2000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < 500; i++) ops[i] = new String[]{"INSERT", "a" + i};
            for (int i = 500; i < 1000; i++) ops[i] = new String[]{"COUNT", "a"};
            for (int i = 1000; i < 1500; i++) ops[i] = new String[]{"DELETE", "a" + (i - 1000)};
            for (int i = 1500; i < q; i++) ops[i] = new String[]{"SEARCH", "a" + (i - 1500)};
            writeTestCase(tc++, q, ops);
        }

        // 86: 10000 SEARCH on missing
        {
            int q = 10000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) ops[i] = new String[]{"SEARCH", "nonexist"};
            writeTestCase(tc++, q, ops);
        }

        // 87: 1e5 mixed on {a,b}
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            String[] types = {"INSERT","SEARCH","PREFIX","DELETE","COUNT"};
            for (int i = 0; i < q; i++) ops[i] = new String[]{types[rng.nextInt(5)], randStr(1 + rng.nextInt(5), 2, rng)};
            writeTestCase(tc++, q, ops);
        }

        // 88: 1e5 mixed full alphabet
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            String[] types = {"INSERT","SEARCH","PREFIX","DELETE","COUNT"};
            for (int i = 0; i < q; i++) ops[i] = new String[]{types[rng.nextInt(5)], randStr(1 + rng.nextInt(5), 26, rng)};
            writeTestCase(tc++, q, ops);
        }

        // 89: deep chain
        {
            int q = 5000;
            String[][] ops = new String[q][2];
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                sb.append('a');
                ops[i] = new String[]{(i % 2 == 0) ? "INSERT" : "COUNT", sb.toString()};
            }
            for (int i = 1000; i < q; i++) ops[i] = new String[]{"SEARCH", sb.toString()};
            writeTestCase(tc++, q, ops);
        }

        // 90: repeated insert/delete then count
        {
            int q = 1000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q - 1; i++) ops[i] = new String[]{(i % 2 == 0) ? "INSERT" : "DELETE", "repeat"};
            ops[q - 1] = new String[]{"COUNT", "repeat"};
            writeTestCase(tc++, q, ops);
        }

        // 91: 1000 inserts, 1000 counts
        {
            int q = 2000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < 1000; i++) ops[i] = new String[]{"INSERT", "x" + i};
            for (int i = 1000; i < q; i++) ops[i] = new String[]{"COUNT", "x"};
            writeTestCase(tc++, q, ops);
        }

        // 92: 1e5 PREFIX misses
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            ops[0] = new String[]{"INSERT", "z"};
            for (int i = 1; i < q; i++) ops[i] = new String[]{"PREFIX", "y"};
            writeTestCase(tc++, q, ops);
        }

        // 93: 1e5 alternating INSERT+SEARCH different words
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                if (i % 2 == 0) ops[i] = new String[]{"INSERT", "w" + (i / 2)};
                else ops[i] = new String[]{"SEARCH", "w" + (i / 2)};
            }
            writeTestCase(tc++, q, ops);
        }

        // 94: 1e5 alternating INSERT+DELETE different words
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                if (i % 2 == 0) ops[i] = new String[]{"INSERT", "d" + (i / 2)};
                else ops[i] = new String[]{"DELETE", "d" + (i / 2)};
            }
            writeTestCase(tc++, q, ops);
        }

        // 95: 1e5 mixed, small alphabet, all types
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            String[] types = {"INSERT","SEARCH","PREFIX","DELETE","COUNT"};
            for (int i = 0; i < q; i++) ops[i] = new String[]{types[i % 5], randStr(1 + rng.nextInt(8), 3, rng)};
            writeTestCase(tc++, q, ops);
        }

        // 96: 1e5 all INSERT distinct length-4
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            for (int i = 0; i < q; i++) {
                ops[i] = new String[]{"INSERT", "w" + i};
            }
            writeTestCase(tc++, q, ops);
        }

        // 97: 1e5 all COUNT on "a"
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            ops[0] = new String[]{"INSERT", "a"};
            for (int i = 1; i < q; i++) ops[i] = new String[]{"COUNT", "a"};
            writeTestCase(tc++, q, ops);
        }

        // 98: 1e5 PREFIX all-missing on non-empty trie
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            ops[0] = new String[]{"INSERT", "abc"};
            for (int i = 1; i < q; i++) ops[i] = new String[]{"PREFIX", "xyz"};
            writeTestCase(tc++, q, ops);
        }

        // 99: 1e5 mixed, all single-char, only {a,b,c}
        {
            int q = 100000;
            String[][] ops = new String[q][2];
            String[] types = {"INSERT","SEARCH","PREFIX","DELETE","COUNT"};
            for (int i = 0; i < q; i++) {
                char c = (char) ('a' + (i % 3));
                ops[i] = new String[]{types[i % 5], "" + c};
            }
            writeTestCase(tc++, q, ops);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Trie Implementation and Advanced Operations.");
    }
}