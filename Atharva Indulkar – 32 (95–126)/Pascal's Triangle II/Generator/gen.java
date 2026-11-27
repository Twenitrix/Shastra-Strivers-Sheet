// Pascal's Triangle II - Generator/gen.java
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

        for (int r = 0; r < 100; r++) {
            String fileName = String.format("%02d", r);
            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(inputDir, "input" + fileName + ".txt"))));
            inWriter.println(r);
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDir, "output" + fileName + ".txt"))));
            outWriter.println(solve(r));
            outWriter.close();
        }
        System.out.println("Testcases generated successfully!");
    }

    static String solve(int rowIndex) {
        BigInteger val = BigInteger.ONE;
        StringBuilder sb = new StringBuilder();
        sb.append(1);
        for (int i = 1; i <= rowIndex; i++) {
            val = val.multiply(BigInteger.valueOf(rowIndex - i + 1)).divide(BigInteger.valueOf(i));
            sb.append(" ").append(val);
        }
        return sb.toString();
    }
}
