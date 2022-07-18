/**
 * @author Daishuai
 * @version 1.0.0
 * @className ClimbingStairs.java
 * @description 动态规划-斐波那契数列-爬楼梯
 * 题目描述: 有 N 阶楼梯，每次可以上一阶或者两阶，求有多少种上楼梯的方法。
 * @createTime 2022年07月18日 23:33:00
 */
public class ClimbingStairs {

    public static void main(String[] args) {
        System.out.println(climbStairs(1));
        System.out.println(climbStairs(2));
        System.out.println(climbStairs(9));
        System.out.println(climbStairs(10));
        System.out.println(climbStairs(11));
    }

    public static int climbStairs(int stairs) {
        if (stairs <= 2) {
            return stairs;
        }
        int stair1 = 1, stair2 = 2;
        for (int i = 3; i <= stairs; i++) {
            int current = stair1 + stair2;
            stair1 = stair2;
            stair2 = current;
        }
        return stair2;
    }
}
