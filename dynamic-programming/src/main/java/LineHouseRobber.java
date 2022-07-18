/**
 * @author Daishuai
 * @version 1.0.0
 * @className LineHouseRobber.java
 * @description 动态规划-斐波那契数列-强盗抢劫
 * 题目描述: 抢劫一排住户，但是不能抢邻近的住户，求最大抢劫量。
 * @createTime 2022年07月18日 23:41:00
 */
public class LineHouseRobber {

    public static void main(String[] args) {
        System.out.println(houseRobber());
        System.out.println(houseRobber(1));
        System.out.println(houseRobber(2,1));
        System.out.println(houseRobber(2,1,1));
        System.out.println(houseRobber(1,3,1));
    }

    public static int houseRobber(int ... values) {
        int houses = values.length;
        if (houses == 0) {
            return 0;
        }
        int house1 = 0, house2 = 0, house3 = 0;
        for (int value : values) {
            int current = Math.max(house1, house2) + value;
            house1 = house2;
            house2 = house3;
            house3 = current;
        }
        return Math.max(house2, house3);
    }
}
