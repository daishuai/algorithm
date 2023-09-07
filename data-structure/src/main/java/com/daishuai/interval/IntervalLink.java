package com.daishuai.interval;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * @author Keda
 * @version 1.0.0
 * @description TODO
 * @createTime 2023年09月05日 15:25:00
 */
public class IntervalLink {

    private Node root;

    private Node maxNode;

    private int max;

    private final int limit;

    public IntervalLink(int limit) {
        this.limit = limit;
        this.max = 0;
    }
    public void insert(Date start, Date end, int value) {
        if (start.equals(end)) {
            return;
        }
        if (root == null) {
            root = new Node(start, end, value);
            max = value;
            return;
        }
        this.insert(root, start, end, value);
    }

    private void insert(Node current, Date start, Date end, int value) {
        if (start.equals(end)) {
            return;
        }
        if (current.start.equals(start) && current.end.equals(end)) {
            current.value += value;
            if (max > current.value + value) {
                max = current.value + value;
                maxNode = current;
            }
            root = current;
            return;
        }
        Node tmp;
        Node right;
        Node left;
        // 1、被current节点包含
        if (current.start.equals(start) && current.end.after(end)) {
            tmp = new Node(current.start, end, current.value + value);
            right = new Node(end, current.end, current.value);
            tmp.pre = current.pre;
            tmp.next = right;
            right.pre = tmp;
            right.next = current.next;
            if (current.pre != null) {
                current.pre.next = tmp;
            }
            root = tmp;
            if (tmp.value > max) {
                max = tmp.value;
                maxNode = tmp;
            }
            return;
        }
        if (current.start.before(start) && current.end.equals(end)) {
            tmp = new Node(start, current.end, current.value + value);
            left = new Node(current.start, start, value);
            tmp.pre = left;
            tmp.next = current.next;
            left.next = tmp;
            left.pre = current.pre;
            if (current.pre != null) {
                current.pre.next = left;
            }
            root = tmp;
            if (tmp.value > max) {
                max = tmp.value;
                maxNode = tmp;
            }
            return;
        }
        if (current.start.before(start) && current.end.after(end)) {
            tmp = new Node(start, end, current.value + value);
            left = new Node(current.start, start, value);
            right = new Node(end, current.end, value);
            tmp.pre = left;
            tmp.next = right;
            left.next = tmp;
            left.pre = current.pre;
            right.pre = tmp;
            right.next = current.next;
            if (current.pre != null) {
                current.pre.next = left;
            }
            root = tmp;
            if (tmp.value > max) {
                max = tmp.value;
                maxNode = tmp;
            }
            return;
        }
        // 2、current节点被包含
        if (current.start.equals(start) && current.end.before(end)) {
            current.value += value;
            this.insertNext(current, current.end, end, value);
            if (current.value > max) {
                max = current.value;
                maxNode = current;
            }
            return;
        }
        if (current.start.after(start) && current.end.equals(end)) {
            current.value += value;
            this.insertPre(current, start, current.start, value);
            if (current.value > max) {
                max = current.value;
                maxNode = current;
            }
            return;
        }
        if (current.start.after(start) && current.end.before(end)) {
            current.value += value;
            this.insertPre(current, start, current.start, value);
            this.insertNext(current, current.end, end, value);
            if (current.value > max) {
                max = current.value;
                maxNode = current;
            }
            return;
        }
        // 3、前面部分包含
        if (start.before(current.start) && current.start.before(end) && end.before(current.end)) {
            tmp = new Node(current.start, end, current.value + value);
            right = new Node(end, current.end, current.value);
            tmp.next = right;
            tmp.pre = current.pre;
            right.next = current.next;
            right.pre = tmp;
            if (current.pre != null) {
                current.pre.next = tmp;
            }
            this.insertPre(tmp, start, current.start, value);
            if (tmp.value > max) {
                max = tmp.value;
                maxNode = tmp;
            }
            return;
        }
        // 4、后面部分包含
        if (current.start.before(start) && start.before(current.end) && current.end.before(end)) {
            tmp = new Node(start, current.end, current.value + value);
            left = new Node(current.start, start, current.value);
            tmp.pre = left;
            tmp.next = current.next;
            left.next = tmp;
            left.pre = current.pre;
            if (current.pre != null) {
                current.pre.next = left;
            }
            this.insertNext(tmp, current.end, end, value);
            if (tmp.value > max) {
                max = tmp.value;
                maxNode = tmp;
            }
            return;
        }
        // 5、不包含在前面
        if (!end.after(current.start)) {
            this.insertPre(current, start, end, value);
            return;
        }
        // 6、不包含在后面
        if (!current.end.after(start)) {
            this.insertNext(current, start, end, value);
            return;
        }
        System.out.println("都不满足");
    }

    private void insertPre(Node current, Date start, Date end, int value) {
        Node left;
        if (current.pre == null || !current.pre.end.after(start)) {
            left = new Node(start, end, value);
            left.next = current;
            left.pre = current.pre;
            if (current.pre != null) {
                current.pre.next = left;
            }
            current.pre = left;
            root = current;
            if (value > max) {
                max = value;
                maxNode = left;
            }
        } else {
            this.insert(current.pre, start, end, value);
        }
    }

    private void insertNext(Node current, Date start, Date end, int value) {
        Node right;
        if (current.next == null || !end.after(current.next.start)) {
            right = new Node(start, end, value);
            right.pre = current;
            right.next = current.next;
            if (current.next != null) {
                current.next.pre = right;
            }
            current.next = right;
            root = current;
            if (value > max) {
                max = value;
                maxNode = right;
            }
        } else {
            this.insert(current.next, start, end, value);
        }
    }

    public boolean isOverLimit(Date start, Date end, int value) {
        if (maxNode == null) {
            return value > limit;
        }
        return this.isOverLimit(maxNode, start, end, value);
    }

    private boolean isOverLimit(Node current, Date start, Date end, int value) {
        if (start.equals(end)) {
            return false;
        }
        if (current.start.equals(start) && current.end.equals(end)) {
            return current.value + value > limit;
        }
        // 1、被current节点包含
        if (current.start.equals(start) && current.end.after(end)) {
            return current.value + value > limit;
        }
        if (current.start.before(start) && current.end.equals(end)) {
            return current.value + value > limit;
        }
        if (current.start.before(start) && current.end.after(end)) {
            return current.value + value > limit;
        }
        // 2、current节点被包含
        if (current.start.equals(start) && current.end.before(end)) {
            if (current.value + value > limit) {
                return true;
            }
            return this.rightIsOverLimit(current, current.end, end, value);
        }
        if (current.start.after(start) && current.end.equals(end)) {
            if (current.value + value > limit) {
                return true;
            }
            return this.leftIsOverLimit(current, start, current.start, value);
        }
        if (current.start.after(start) && current.end.before(end)) {
            if (current.value + value > limit) {
                return true;
            }
            boolean isOverLimit= this.leftIsOverLimit(current, start, current.start, value);
            if (isOverLimit) {
                return true;
            }
            return this.rightIsOverLimit(current, current.end, end, value);
        }
        // 3、前面部分包含
        if (start.before(current.start) && current.start.before(end) && end.before(current.end)) {
            if (current.value + value > limit) {
                return true;
            }
            return this.leftIsOverLimit(current, start, current.start, value);
        }
        // 4、后面部分包含
        if (current.start.before(start) && start.before(current.end) && current.end.before(end)) {
            if (current.value + value > limit) {
                return true;
            }
            return this.rightIsOverLimit(current, current.end, end, value);
        }
        // 5、不包含在前面
        if (!end.after(current.start)) {
            return this.leftIsOverLimit(current, start, end, value);
        }
        // 6、不包含在后面
        if (!current.end.after(start)) {
            return this.rightIsOverLimit(current, start, end, value);
        }
        return false;
    }

    private boolean leftIsOverLimit(Node current, Date start, Date end, int value) {
        if (current.pre == null || !current.pre.end.after(start)) {
            return value > limit;
        } else {
            return this.isOverLimit(current.pre, start, end, value);
        }
    }

    private boolean rightIsOverLimit(Node current, Date start, Date end, int value) {
        if (current.next == null || !end.after(current.next.start)) {
            return value > limit;
        } else {
            return this.isOverLimit(current.next, start, end, value);
        }
    }

    static class Node {
        Date start;

        Date end;

        int value;

        Node next;

        Node pre;

        Node(Date start, Date end, int value) {
            this.start = start;
            this.end = end;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        String jsonStr = "[\n" +
                "  {\n" +
                "    \"start\": \"2023-01-01 08:00:00\",\n" +
                "    \"end\": \"2023-01-01 14:01:00\",\n" +
                "    \"value\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"start\": \"2023-01-01 02:00:00\",\n" +
                "    \"end\": \"2023-01-01 04:01:00\",\n" +
                "    \"value\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"start\": \"2023-01-01 09:00:00\",\n" +
                "    \"end\": \"2023-01-01 12:01:00\",\n" +
                "    \"value\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"start\": \"2023-01-01 18:00:00\",\n" +
                "    \"end\": \"2023-01-01 20:01:00\",\n" +
                "    \"value\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"start\": \"2023-01-01 21:00:00\",\n" +
                "    \"end\": \"2023-01-01 23:01:00\",\n" +
                "    \"value\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"start\": \"2023-01-01 15:00:00\",\n" +
                "    \"end\": \"2023-01-01 20:01:00\",\n" +
                "    \"value\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"start\": \"2023-01-01 05:00:00\",\n" +
                "    \"end\": \"2023-01-01 08:00:00\",\n" +
                "    \"value\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"start\": \"2023-01-01 05:00:00\",\n" +
                "    \"end\": \"2023-01-01 08:01:00\",\n" +
                "    \"value\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"start\": \"2023-01-01 05:00:00\",\n" +
                "    \"end\": \"2023-01-01 08:01:00\",\n" +
                "    \"value\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"start\": \"2023-01-01 05:00:00\",\n" +
                "    \"end\": \"2023-01-01 08:01:00\",\n" +
                "    \"value\": 1\n" +
                "  }\n" +
                "]";
        JSONArray array = JSONObject.parseArray(jsonStr);
        System.out.println(array.toString());
        IntervalLink interval = new IntervalLink(3);
        for (int i = 0; i < array.size(); i++) {
            JSONObject json = array.getJSONObject(i);
            Date start = json.getDate("start");
            Date end = json.getDate("end");
            int value = json.getIntValue("value");
            System.out.println("is over limit: " + interval.isOverLimit(start, end, value));
            interval.insert(start, end, value);
        }
    }
}
