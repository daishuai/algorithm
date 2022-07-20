/**
 * @author Daishuai
 * @version 1.0.0
 * @ClassName MatrixMinimumPath.java
 * @Description 动态规划-矩阵最短路径
 * 求从矩阵的左上角到右下角的最小路径和，每次只能向右和向下移动。
 * @createTime 2022年07月20日 14:20:00
 */
public class MatrixMinimumPath {

    public static void main(String[] args) {
        int[][] matrix = {{1}};
        System.out.println(minimumPath(matrix));
    }

    public static int minimumPath(int[][] matrix) {
        int left, up;
        int x = matrix.length;
        int y = matrix[0].length;
        int[][] paths = new int[x][y];
        paths[0][0] = matrix[0][0];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (i == 0 && j == 0) {
                    left = up = 0;
                } else if (i == 0) {
                    left = up = paths[i][j - 1];
                } else if (j == 0) {
                    left = up = paths[i - 1][j];
                } else {
                    left = paths[i - 1][j];
                    up = paths[i][j - 1];
                }
                paths[i][j] = Math.min(left, up) + matrix[i][j];
            }
        }
        return paths[x - 1][y - 1];
    }
}
