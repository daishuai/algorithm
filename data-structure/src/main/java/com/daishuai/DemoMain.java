package com.daishuai;


import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author admin
 * @version 1.0.0
 * @description TODO
 * @createTime 2022-11-29 23:24:48
 */
public class DemoMain {

    public static void main(String[] args) {
        String code = "CUSTOM_CONFIG";
        int preIndex = 0;
        String[] codes = code.split("\\.");
        for (int i = 0; i < codes.length - 1; i++) {
            int index = code.indexOf(".", preIndex);
            System.out.println(code.substring(0, index));
            preIndex = index + 1;
        }
    }

    public static int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        // 排列，组合，二项式定理
        int times = minutesToTest / minutesToDie;
        int pigs = 0;
        int maxBuckets = 1;
        while (maxBuckets < buckets) {
            pigs ++;
            maxBuckets = maxBuckets * (times + 1);
        }
        return pigs;
    }

    public static boolean isMatch(String s, String p) {
        //dp[i][j] = dp[i-1][j-1] && s[i] = s[j]
        int m = s.length();
        int n = p.length();
        char[] cs = s.toCharArray();
        char[] cp = p.toCharArray();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 1; i <= n; i++) {
            if (cp[i - 1] == '*') {
                dp[0][i] = dp[0][i - 2];
            }
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (cp[j - 1] != '*') {
                    dp[i][j] = dp[i - 1][j - 1] && (cp[j - 1] == '.' || cs[i - 1] == cp[j - 1]);
                } else {
                    if (cp[j -2] != '.' && cp[j-2] != cs[i-1]) {
                        dp[i][j] = dp[i][j-2];
                    } else {
                        dp[i][j] = dp[i][j-2] || dp[i-1][j];
                    }
                }
            }
        }
        return dp[m][n];
    }

    public static int maxArea(int[] nums) {
        int maxArea = 0;
        for (int i = 0, j = nums.length - 1; i < j; ) {
            maxArea = Math.max(maxArea, Math.min(nums[i], nums[j]) * (j - i));
            if (nums[i] > nums[j]) {
                j--;
            } else {
                i++;
            }
        }
        return maxArea;
    }

    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int a = 1;
        while (x / 10 / a > 0) {
            a *= 10;
        }
        while (a > 1) {
            int left = x / a;
            int right = x % 10;
            if (left != right) {
                return false;
            }
            x = x % a / 10;
            a = a / 100;
        }
        return true;
    }
}

class Solution {

    public static int reverse(int x) {
        if (x == 0) {
            return 0;
        }
        int r = 0;
        boolean isFs = false;
        if (x < 0) {
            isFs = true;
            x = -x;
        }
        int a = 1;
        while (x / 10 / a > 0) {
            a *= 10;
        }
        int b = 1;
        int c = a;
        while (c > 0 && x % c >= 0) {
            if ((r + x / c * b) > Integer.MAX_VALUE / (r + x / c * b)) {
                return 0;
            }
            r += x / c * b;
            x = x % c;
            b *= 10;
            c = a / b;
        }
        return isFs ? -r : r;
    }

    public static String convert(String s, int numRows) {
        List<StringBuffer> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuffer());
        }
        int row = 0;
        int flag = 1;
        for (int i = 0; i < s.toCharArray().length; i++) {
            rows.get(row).append(s.charAt(i));
            if (row + 1 == numRows) {
                flag = -1;
            }
            if (row == 0) {
                flag = 1;
            }
            row += flag;
        }
        String s1 = "";
        for (int i = 0; i < rows.size(); i++) {
            s1 += rows.get(i);
        }
        return s1;
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length = nums1.length + nums2.length;
        boolean isSquare = length % 2 == 0;
        int mid = length / 2;
        int left = 0;
        int right = 0;
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i <= mid; i++) {
            left = right;
            if (index1 < nums1.length && (index2 >= nums2.length || nums1[index1] < nums2[index2])) {
                right = nums1[index1++];
            } else {
                right = nums2[index2++];
            }
        }
        return isSquare ? (left + right) / 2.0 : right;
    }

    public static String longestPalindromeV1(String s) {
        int maxLenght = 0;
        int start = 0;
        char[] chars = s.toCharArray();
        int left = 0;
        int right = 0;
        for (int i = 0; i < chars.length; i++) {
            int lenth = 1;
            // 先向左
            left = i - 1;
            right = i + 1;
            while (left >= 0 && chars[left] == chars[i]) {
                left--;
                lenth++;
            }
            while (right < s.length() && chars[right] == chars[i]) {
                right++;
                lenth++;
            }
            while (left >= 0 && right < s.length() && chars[left] == chars[right]) {
                left--;
                right++;
                lenth += 2;
            }
            if (lenth > maxLenght) {
                maxLenght = lenth;
                start = left + 1;
            }
        }
        return s.substring(start, start + maxLenght);
    }

    public static String longestPalindromeV2(String s) {
        int start = 0;
        int maxLength = 0;
        int length = s.length();
        boolean[][] dp = new boolean[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j <= i; j++) {
                dp[j][i] = s.charAt(i) == s.charAt(j) && ((i - j) <= 1 || dp[j + 1][i - 1]);
                if (dp[j][i] && (i - j + 1 > maxLength)) {
                    maxLength = i - j + 1;
                    start = j;
                }
            }
        }
        return s.substring(start, start + maxLength);
    }

    public static double findMedianSortedArraysV2(int[] nums1, int[] nums2) {
        int length = nums1.length + nums2.length;
        int left = (length + 1) / 2;
        int right = (left + 2) / 2;
        return (findK(nums1, 0, nums2, 0, left) + findK(nums1, 0, nums2, 0, right)) / 2.0D;
    }

    private static int findK(int[] num1, int index1, int[] num2, int index2, int k) {
        if (num1.length == 0) {
            return num2[index2 + k - 1];
        }
        if (num2.length == 0) {
            return num1[index1 + k - 1];
        }
        if (k == 1) {
            return Math.min(num1[index1], num2[index2]);
        }
        int a = k / 2;
        int end1 = Math.min(index1 + a, num1.length);
        int end2 = Math.min(index2 + a, num2.length);
        if (num1[end1 - 1] > num2[end2 - 1]) {
            a = end2 - index2;
            index2 = end2;
        } else {
            a = end1 - index1;
            index1 = end1;
        }
        return findK(num1, index1, num2, index2, k - a);
    }
}
