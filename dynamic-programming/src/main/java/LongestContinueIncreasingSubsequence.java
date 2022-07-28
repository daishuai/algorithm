/**
 * @author Daishuai
 * @version 1.0.0
 * @className LongestContinueIncreasingSubsequence.java
 * @description 动态规划-最长连续递增子序列
 * 给定一个未经排序的整数数组，找到最长且 连续递增的子序列，并返回该序列的长度。
 * @createTime 2022年07月19日 23:46:00
 */
public class LongestContinueIncreasingSubsequence {

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 4, 7, 6, 8, 9, 10, 5};
        System.out.println(lengthOfLCIS(nums));
    }

    public static int lengthOfLCIS(int[] nums) {
        int length = nums.length;
        if (length == 1) {
            return 1;
        }
        int[] lengths = new int[length];
        lengths[0] = 1;
        int max = 1;
        for (int i = 1; i < length; i++) {
            if (nums[i] > nums[i - 1]) {
                lengths[i] = lengths[i - 1] + 1;
            } else {
                lengths[i] = 1;
            }
            max = Math.max(lengths[i], max);
        }
        return max;
    }
}
