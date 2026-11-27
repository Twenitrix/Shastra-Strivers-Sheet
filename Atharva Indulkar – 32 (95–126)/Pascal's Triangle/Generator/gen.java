// Pascal's Triangle - Generator/gen.java
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "Testcases";
        if (new File("../Testcases").exists()) {
            baseDir = "../Testcases";
        }

        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        for (int n = 1; n <= 100; n++) {
            String fileName = String.format("%02d", n - 1);
            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(inputDir, "input" + fileName + ".txt"))));
            inWriter.println(n);
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDir, "output" + fileName + ".txt"))));
            outWriter.print(solve(n));
            outWriter.close();
        }
        System.out.println("Testcases generated successfully!");
    }

    static String solve(int n) {
        StringBuilder sb = new StringBuilder();
        BigInteger[][] triangle = new BigInteger[n][];
        for (int i = 0; i < n; i++) {
            triangle[i] = new BigInteger[i + 1];
            triangle[i][0] = BigInteger.ONE;
            triangle[i][i] = BigInteger.ONE;
            for (int j = 1; j < i; j++) {
                triangle[i][j] = triangle[i - 1][j - 1].add(triangle[i - 1][j]);
            }
            for (int j = 0; j <= i; j++) {
                sb.append(triangle[i][j]);
                if (j != i) sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
