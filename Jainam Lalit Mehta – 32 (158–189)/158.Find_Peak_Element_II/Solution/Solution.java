public class Solution {

    // Binary search on columns. For the middle column, find its largest cell.
    // That cell is a peak in its column, so it only has to beat its left and right
    // neighbours. If a neighbour is larger, a peak must exist on that side.
    public int[] locatePeak(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;

        int low = 0;
        int high = cols - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            int bestRow = 0;
            for (int r = 1; r < rows; r++) {
                if (grid[r][mid] > grid[bestRow][mid]) {
                    bestRow = r;
                }
            }

            int value = grid[bestRow][mid];
            int leftValue = mid > 0 ? grid[bestRow][mid - 1] : -1;
            int rightValue = mid < cols - 1 ? grid[bestRow][mid + 1] : -1;

            if (value > leftValue && value > rightValue) {
                return new int[]{bestRow, mid};
            } else if (rightValue > value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        // Not reached for valid input, because a peak always exists.
        return new int[]{-1, -1};
    }

}
