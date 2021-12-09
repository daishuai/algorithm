package com.daishuai.linkedlist;

public class LinkedList {

    public static void main(String[] args) {
        int[] array1 = {1, 3, 5, 6, 7, 10, 12};
        int[] array2 = {2, 5, 7, 8, 9, 12, 18};
        int[] array3 = {3,2,5,1,2,5,9,5,4,8};
        Node node1 = Node.buildNode(array1);
        Node node2 = Node.buildNode(array2);
        Node node3 = Node.buildNode(array3);

        // 打印两个链表中值相同的节点
        printEqualValueNode(node1, node2);
        // 将链表中大于num的节点放到右边，等于num的节点放到中间，小于num的节点放到左边
        Node partitionNode = partitionNode(node3, 6);
        Node.printNode(partitionNode);
    }

    public static void printEqualValueNode(Node node1, Node node2) {
        while (node1 != null && node2 != null) {
            if (node1.value > node2.value) {
                node2 = node2.next;
            } else if (node1.value < node2.value) {
                node1 = node1.next;
            } else {
                System.out.println(node1.value);
                node1 = node1.next;
                node2 = node2.next;
            }
        }
    }

    public static Node partitionNode(Node node, int num) {
        Node smallHead = null, smallTail = null, midHead = null, midTail = null, bigHead = null, bigTail = null;
        while (node != null) {
            if (node.value < num) {
                if (smallHead == null) {
                    smallHead = node;
                    smallTail = smallHead;
                } else {
                    smallTail.next = node;
                    smallTail = smallTail.next;
                }
            }
            if (node.value == num) {
                if (midHead == null) {
                    midHead = node;
                    midTail = midHead;
                } else {
                    midTail.next = node;
                    midTail = midTail.next;
                }
            }
            if (node.value > num) {
                if (bigHead == null) {
                    bigHead = node;
                    bigTail = bigHead;
                } else {
                    bigTail.next = node;
                    bigTail = bigTail.next;
                }
            }
            node = node.next;
        }
        if (smallTail != null && midTail != null) {
            smallTail.next = midHead;
            midTail.next = bigHead;
            return smallHead;
        }
        if (smallTail == null && midTail != null) {
            midTail.next = bigHead;
            return midHead;
        }
        if (smallTail != null) {
            smallTail.next = bigHead;
            return smallHead;
        }
        return bigHead;

    }
}
