// Pascal's Triangle II - Solution/Solution.java
import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        int rowIndex = scanner.nextInt();
        BigInteger val = BigInteger.ONE;
        StringBuilder sb = new StringBuilder();
        sb.append(1);
        for (int i = 1; i <= rowIndex; i++) {
            val = val.multiply(BigInteger.valueOf(rowIndex - i + 1)).divide(BigInteger.valueOf(i));
            sb.append(" ").append(val);
        }
        System.out.println(sb);
    }
}
