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
public class Interval {

    private Node root;

    private final int limit;

    public Interval(int limit) {
        this.limit = limit;
    }

    public void insert(Date start, Date end, int value) {
        if (root == null) {
            root = new Node(start, end, value);
            return;
        }
        root = this.insert(root, start, end, value);
    }

    private Node insert(Node current, Date start, Date end, int value) {
        // current.start == start and current.end == end
        if (current.start.equals(start) && current.end.equals(end)) {
            current.value += value;
            return current;
        }
        //包含关系 current.start——start——end——current.end
        if (!current.start.after(start) && !current.end.before(end)) {
            Node left = new Node(current.start, start, current.value);
            left.pre = current.pre;
            if (current.pre != null) {
                current.pre.next = left;
            }
            Node right = new Node(end, current.end, current.value);
            right.next = current.next;
            if (current.next != null) {
                current.next.pre = right;
            }
            Node mid = new Node(start, end, current.value + value);
            left.next = mid;
            right.pre = mid;
            mid.pre = left;
            mid.next = right;
            return mid;
        }
        // 被包含 start-current.start-current.end-end
        if (!current.start.before(start) && !current.end.after(end)) {
            Node left;
            if (current.pre == null) {
                left = new Node(start, current.start, value);
            } else {
                if (!current.pre.end.after(start)) {
                    left = new Node(start, current.start, value);
                    current.pre.next = left;
                    left.pre = current.pre;
                } else {
                    left = this.insert(current.pre, start, current.start, value);
                }
            }
            Node right;
            if (current.next == null) {
                right = new Node(current.end, end, value);
            } else {
                if (!current.next.start.before(end)) {
                    right = new Node(current.end, end, value);
                    current.next.pre = right;
                    right.next = current.next;
                } else {
                    right = this.insert(current.next, current.end, end, value);
                }
            }
            left.next = current;
            current.pre = left;
            right.pre = current;
            current.next = right;
            current.value += value;
            return current;
        }
        // 前面部分包含 start-current.start-end-current.end
        if (!current.start.before(start) && !end.before(current.start)) {
            Node mid = new Node(current.start, end, current.value + value);
            Node left;
            if (current.pre == null) {
                left = new Node(start, current.start, value);
            } else {
                if (!current.pre.end.after(start)) {
                    left = new Node(start, current.start, value);
                    current.pre.next = left;
                    left.pre = current.pre;
                } else {
                    left = this.insert(current.pre, start, current.start, value);
                }
            }
            Node right = new Node(end, current.end, current.value);
            right.next = current.next;
            current.value += value;
            mid.next = right;
            while (left.next != null && left.next != current) {
                left = left.next;
            }
            left.next = mid;
            mid.pre = left;
            return mid;
        }
        // 后面部分包含 current.start-start-current.end-end
        if (!current.start.after(start) && !start.after(current.end)) {
            Node mid = new Node(start, current.end, current.value + value);
            Node left = new Node(current.start, start, current.value);
            left.pre = current.pre;
            left.next = mid;
            mid.pre = left;
            Node right;
            if (current.next == null) {
                right = new Node(current.end, end, value);
            } else {
                if (!current.next.start.before(end)) {
                    right = new Node(current.end, end, value);
                    current.next.pre = right;
                    right.next = current.next;
                } else {
                    right = this.insert(current.next, current.end, end, value);
                }
            }
            while (right.pre != null && right.pre != current) {
                right = right.pre;
            }
            right.pre = mid;
            mid.next = right;
            return mid;
        }
        // 不包含在前面 start-end-current.start-current.end
        if (!end.after(current.start)) {
            Node left;
            if (current.pre == null) {
                left = new Node(start, end, value);
            } else {
                if (!current.pre.end.after(start)) {
                    left = new Node(start, end, value);
                    current.pre.next = left;
                    left.pre = current.pre;
                } else {
                    left = this.insert(current.pre, start, end, value);
                }
            }
            while (left.next != null && left.next != current) {
                left = left.next;
            }
            left.next = current;
            current.pre = left;
            return current;
        }
        // 不包含在后面 current.start-current.end-start-end
        Node right;
        if (current.next == null) {
            right = new Node(start, end, value);
        } else {
            if (!current.next.start.before(end)) {
                right = new Node(start, end, value);
                current.next.pre = right;
                right.next = current.next;
            } else {
                right = this.insert(current.next, start, end, value);
            }
        }
        while (right.pre != null && right.pre != current) {
            right = right.pre;
        }
        right.pre = current;
        current.next = right;
        return current;
    }

    public boolean overLimit(Date start, Date end, int value) {
        return this.overLimit(root, start, end, value);
    }

    private boolean overLimit(Node current, Date start, Date end, int value) {
        if (current == null) {
            return false;
        }
        //包含关系 current.start——start——end——current.end
        if (current.start.before(start) && current.end.after(end)) {
            return current.value + value > limit;
        }
        // 被包含 start-current.start-current.end-end
        if (current.start.after(start) && current.end.before(end)) {
            boolean overLimit = current.value + value > limit;
            if (!overLimit && current.pre != null && current.pre.end.after(start)) {
                overLimit = this.overLimit(current.pre, start, root.start, value);
            }
            if (!overLimit && current.next != null && current.next.start.before(end)) {
                overLimit = this.overLimit(current.next, root.end, end, value);
            }
            return overLimit;
        }
        // 前面部分包含 start-current.start-end-current.end
        if (current.start.after(start) && current.start.before(end) && end.before(current.end)) {
            boolean overLimit = current.value + value > limit;
            if (!overLimit && current.pre != null && current.pre.end.after(start)) {
                overLimit = this.overLimit(current.pre, start, root.start, value);
            }
            return overLimit;
        }
        // 后面部分包含 current.start-start-current.end-end
        if (current.start.before(start) && start.before(current.end) && current.end.before(end)) {
            boolean overLimit = current.value + value > limit;
            if (!overLimit && current.next != null && current.next.start.before(end)) {
                overLimit = this.overLimit(current.next, root.end, end, value);
            }
            return overLimit;
        }
        // 不包含在前面 start-end-current.start-current.end
        if (end.before(current.start)) {
            return this.overLimit(current.pre, start, end, value);
        }
        // 不包含在后面 current.start-current.end-start-end
        if (current.end.before(start)) {
            return this.overLimit(current.next, start, end, value);
        }
        return false;
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
                "    \"end\": \"2023-01-01 08:01:00\",\n" +
                "    \"value\": 1\n" +
                "  }\n" +
                "]";
        JSONArray array = JSONObject.parseArray(jsonStr);
        System.out.println(array.toString());
        Interval interval = new Interval(4);
        for (int i = 0; i < array.size(); i++) {
            JSONObject json = array.getJSONObject(i);
            Date start = json.getDate("start");
            Date end = json.getDate("end");
            int value = json.getIntValue("value");
            System.out.println("is over limit: " + interval.overLimit(start, end, value));
            interval.insert(start, end, value);
        }
    }
}
