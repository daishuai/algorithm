/**
 * @author Daishuai
 * @version 1.0.0
 * @className LongestIncreasingSubsequence.java
 * @description 动态规划-最长递增子序列
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度
 * @createTime 2022年07月19日 23:04:00
 */
public class LongestIncreasingSubsequence {

    public static void main(String[] args) {
        System.out.println(lengthOfLIS(new int[]{10}));
        System.out.println(lengthOfLIS(new int[]{10, 9}));
        System.out.println(lengthOfLIS(new int[]{10, 9, 2}));
        System.out.println(lengthOfLIS(new int[]{10, 9, 2, 5}));
        System.out.println(lengthOfLIS(new int[]{10, 9, 2, 5, 3}));
        System.out.println(lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7}));
        System.out.println(lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101}));
        System.out.println(lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
        System.out.println(lengthOfLIS(new int[]{0, 1, 0, 3, 2, 3}));
        System.out.println(lengthOfLIS(new int[]{1, 3, 6, 7, 9, 4, 10, 5, 6}));
    }

    public static int lengthOfLIS(int[] numbs) {
        int length = numbs.length;
        if (length == 1) {
            return 1;
        }
        int[] lengths = new int[length];
        lengths[0] = 1;
        int maxLength = lengths[0];
        for (int i = 1; i < numbs.length; i++) {
            lengths[i] = 1;
            for (int j = 0; j < i; j++) {
                if (numbs[j] >= numbs[i]) {
                    continue;
                }
                lengths[i] = Math.max(lengths[i], lengths[j] + 1);
            }
            maxLength = Math.max(maxLength, lengths[i]);
        }
        return maxLength;
    }
}
