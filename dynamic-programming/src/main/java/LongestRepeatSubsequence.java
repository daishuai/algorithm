/**
 * @author Daishuai
 * @version 1.0.0
 * @ClassName LongestRepeatSubsequence.java
 * @Description 动态规划-最大重复子数组
 * 给两个整数数组 nums1 和 nums2 ，返回 两个数组中 公共的 、长度最长的子数组的长度 。
 * @createTime 2022年07月20日 11:49:00
 */
public class LongestRepeatSubsequence {

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 2, 1};
        int[] nums2 = {3, 2, 1, 4, 7};
        System.out.println(lengthOfLRS(nums1, nums2));
    }

    public static int lengthOfLRS(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        if (length1 == 0 || length2 == 0) {
            return 0;
        }
        int[][] lengths = new int[length1][length2];
        int max = 0;
        int pre;
        int length;
        for (int i = 0; i < length1; i++) {
            for (int j = 0; j < length2; j++) {
                pre = i == 0 || j == 0 ? 0 : lengths[i - 1][j - 1];
                length = nums1[i] == nums2[j] ? pre + 1 : 0;
                lengths[i][j] = length;
                max = Math.max(max, length);
            }
        }
        return max;
    }
}
