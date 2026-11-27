// Pascal's Triangle III - Solution/Solution.java
import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();
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
        System.out.print(sb);
    }
}
