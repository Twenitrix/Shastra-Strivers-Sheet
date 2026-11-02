import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    // Reference solver: classic greedy stack.
    // 1) If |S| is odd -> -1.
    // 2) Remove matched pairs using a stack; the remaining string has the form
    //    "}}...}{{...{" (unmatched closers followed by unmatched openers).
    // 3) Let a = unmatched '}', b = unmatched '{'. Answer = ceil(a/2) + ceil(b/2).
    //    Equivalently: (a+1)/2 + (b+1)/2.
    private static long solve(String s) {
        int n = s.length();
        if ((n & 1) == 1) return -1;

        int unmatchedOpen = 0;    // running count of '{' after matching
        int unmatchedClose = 0;   // count of '}' that couldn't be matched

        // Simpler: use a stack simulation
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '{') {
                stack.push(c);
            } else {
                if (!stack.isEmpty() && stack.peek() == '{') {
                    stack.pop();
                } else {
                    stack.push(c);
                }
            }
        }

        // Now stack contains: some '}' (unmatched closers, at bottom)
        // and some '{' (unmatched openers, at top).
        int a = 0, b = 0;
        while (!stack.isEmpty()) {
            char c = stack.pop();
            if (c == '}') a++;
            else b++;
        }
        // a = unmatched '}' , b = unmatched '{'
        return (a + 1) / 2 + (b + 1) / 2;
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

    private static String randomBrackets(int len, Random rng) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append(rng.nextBoolean() ? '{' : '}');
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // ---- Category A: Hand-crafted edge cases (00 - 19) ----

        // TC 00: example 1
        writeTestCase(tc++, "}}{{");

        // TC 01: example 2 (odd length)
        writeTestCase(tc++, "}}}");

        // TC 02: n = 1, single '{'
        writeTestCase(tc++, "{");

        // TC 03: n = 1, single '}'
        writeTestCase(tc++, "}");

        // TC 04: n = 2, "{}" — already balanced
        writeTestCase(tc++, "{}");

        // TC 05: n = 2, "}{" — 1 reversal needed
        writeTestCase(tc++, "}{");

        // TC 06: n = 2, "{{" — 1 reversal needed
        writeTestCase(tc++, "{{");

        // TC 07: n = 2, "}}" — 1 reversal needed
        writeTestCase(tc++, "}}");

        // TC 08: n = 4, "{{}}" — already balanced
        writeTestCase(tc++, "{{}}");

        // TC 09: n = 4, "}}{{" — 2 reversals
        writeTestCase(tc++, "}}{ {".replace(" ", ""));   // "}}{{" 

        // TC 10: n = 4, "{{{{" — 2 reversals
        writeTestCase(tc++, "{{{{");

        // TC 11: n = 4, "}}}}" — 2 reversals
        writeTestCase(tc++, "}}}}");

        // TC 12: n = 6, "{{{}}}" — already balanced
        writeTestCase(tc++, "{{{}}}");

        // TC 13: n = 6, "}}}}{{{" — 3 reversals
        writeTestCase(tc++, "}}}}{{{");

        // TC 14: n = 6, "}}{}}{" — 2 reversals
        writeTestCase(tc++, "}}{}}{");

        // TC 15: n = 5, odd length -> -1
        writeTestCase(tc++, "{{{{{");

        // TC 16: n = 7, odd length -> -1
        writeTestCase(tc++, "}}}}}}{");

        // TC 17: n = 8, "{{{{}}}}" — already balanced
        writeTestCase(tc++, "{{{{}}}}");

        // TC 18: n = 8, "}}}}}}}}" — 4 reversals
        writeTestCase(tc++, "}}}}}}}}");

        // TC 19: n = 8, "{{{{{{{{" — 4 reversals
        writeTestCase(tc++, "{{{{{{{{");

        // ---- Category B: Small random tests (20 - 49) ----
        for (int k = 0; k < 30; k++) {
            int len = 1 + rng.nextInt(10);          // 1..10
            writeTestCase(tc++, randomBrackets(len, rng));
        }

        // ---- Category C: Medium random tests (50 - 79) ----
        for (int k = 0; k < 30; k++) {
            int len = 100 + rng.nextInt(901);       // 100..1000
            writeTestCase(tc++, randomBrackets(len, rng));
        }

        // ---- Category D: Large / stress tests (80 - 99) ----

        // TC 80: n = 1e5, all '{'
        {
            int n = 100000;
            char[] arr = new char[n];
            Arrays.fill(arr, '{');
            writeTestCase(tc++, new String(arr));
        }

        // TC 81: n = 1e5, all '}'
        {
            int n = 100000;
            char[] arr = new char[n];
            Arrays.fill(arr, '}');
            writeTestCase(tc++, new String(arr));
        }

        // TC 82: n = 1e5, already balanced "{" * 50000 + "}" * 50000
        {
            int n = 100000;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n / 2; i++) sb.append('{');
            for (int i = 0; i < n / 2; i++) sb.append('}');
            writeTestCase(tc++, sb.toString());
        }

        // TC 83: n = 1e5, "}" * 50000 + "{" * 50000
        {
            int n = 100000;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n / 2; i++) sb.append('}');
            for (int i = 0; i < n / 2; i++) sb.append('{');
            writeTestCase(tc++, sb.toString());
        }

        // TC 84: n = 1e5, odd length -> -1
        {
            int n = 99999;
            char[] arr = new char[n];
            Arrays.fill(arr, '{');
            writeTestCase(tc++, new String(arr));
        }

        // TC 85: n = 1e5, alternating "{}" * 50000
        {
            int n = 100000;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n / 2; i++) sb.append("{}");
            writeTestCase(tc++, sb.toString());
        }

        // TC 86: n = 1e5, alternating "}{" * 50000
        {
            int n = 100000;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n / 2; i++) sb.append("}{");
            writeTestCase(tc++, sb.toString());
        }

        // TC 87 - 91: n = 1e5, fully random
        for (int k = 0; k < 5; k++) {
            int n = 100000;
            writeTestCase(tc++, randomBrackets(n, rng));
        }

        // TC 92: n = 1e5, blocks of 10: '{'*5 + '}'*5 repeated
        {
            int n = 100000;
            StringBuilder sb = new StringBuilder();
            for (int b = 0; b < n / 10; b++) {
                for (int j = 0; j < 5; j++) sb.append('{');
                for (int j = 0; j < 5; j++) sb.append('}');
            }
            writeTestCase(tc++, sb.toString());
        }

        // TC 93: n = 1e5, blocks of 10: '}'*5 + '{'*5 repeated
        {
            int n = 100000;
            StringBuilder sb = new StringBuilder();
            for (int b = 0; b < n / 10; b++) {
                for (int j = 0; j < 5; j++) sb.append('}');
                for (int j = 0; j < 5; j++) sb.append('{');
            }
            writeTestCase(tc++, sb.toString());
        }

        // TC 94: n = 1e5, random, forced even (len 100000)
        writeTestCase(tc++, randomBrackets(100000, rng));

        // TC 95: n = 99999 (odd) all '{' -> -1
        {
            int n = 99999;
            char[] arr = new char[n];
            Arrays.fill(arr, '{');
            writeTestCase(tc++, new String(arr));
        }

        // TC 96: n = 1e5, first quarter '{', rest '}'
        {
            int n = 100000;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n / 4; i++) sb.append('{');
            for (int i = n / 4; i < n; i++) sb.append('}');
            writeTestCase(tc++, sb.toString());
        }

        // TC 97: n = 1e5, first quarter '}', rest '{'
        {
            int n = 100000;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n / 4; i++) sb.append('}');
            for (int i = n / 4; i < n; i++) sb.append('{');
            writeTestCase(tc++, sb.toString());
        }

        // TC 98: n = 1e5, "}"*25000 + "{"*25000 + "}"*25000 + "{"*25000
        {
            int n = 100000;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n / 4; i++) sb.append('}');
            for (int i = 0; i < n / 4; i++) sb.append('{');
            for (int i = 0; i < n / 4; i++) sb.append('}');
            for (int i = 0; i < n / 4; i++) sb.append('{');
            writeTestCase(tc++, sb.toString());
        }

        // TC 99: n = 99998 (even, < 1e5), random
        writeTestCase(tc++, randomBrackets(99998, rng));

        System.out.println("SUCCESS: Generated " + tc + " test cases for Minimum Number of Bracket Reversals to Make an Expression Balanced.");
    }
}