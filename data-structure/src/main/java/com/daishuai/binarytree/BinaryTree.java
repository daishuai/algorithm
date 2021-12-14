package com.daishuai.binarytree;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class BinaryTree {

    public static void main(String[] args) {

        String binaryTreeStr = "5_3_6_#_#_3_#_#_9_#_4_#_9_#_#";

        // 二叉树的反序列化
        Node node = preorderDeserializeBinaryTree(binaryTreeStr);
        // 二叉树的序列化

        // 1、二叉树的宽度优先遍历
        //widthFirstTraversal(node);
        // 2、二叉树的深度优先遍历（二叉树的先序优先遍历）

        // 3、二叉树的中序遍历（递归和非递归方式）
        // 3.1、递归方式实现
        //inorderTraversalRecursion(node);
        // 3.2、非递归方式实现
        // 4、二叉树的先序遍历（递归和非递归方式）
        // 4.1、递归方式实现
        //preorderTraversalRecursion(node);
        // 4.2 非递归方式实现
        //preorderTraversalNonRecursion(node);
        // 5、二叉树的后序遍历（递归和非递归方式）
        // 5.1、递归方式实现
        postorderTraversalRecursion(node);
        // 5.2、非递归方式实现

        // 6、判断一棵二叉树是否为搜索二叉树：左树比他小，右树比他大

        // 7、判断一颗二叉树是否为完全二叉树

        // 8、判断一颗二叉树是否为满二叉树

        // 9、判断一颗二叉树是否为平衡二叉树

        // 10、计算二叉树的宽度

        // 11、计算二叉树的深度
    }

    /**
     * 二叉树的宽度优先遍历
     *
     * @param root  根节点
     */
    public static void widthFirstTraversal(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.println(node.value);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    /**
     * 先序反序列化二叉树
     *
     * @param binaryTreeStr 序列化后的二叉树
     * @return  二叉树根节点
     */
    public static Node preorderDeserializeBinaryTree(String binaryTreeStr) {
        if (StringUtils.isBlank(binaryTreeStr)) {
            return null;
        }
        String[] values = binaryTreeStr.split("_");
        Queue<String> queue = new LinkedList<>(Arrays.asList(values));
        return reconstructPreOrder(queue);
    }

    private static Node reconstructPreOrder(Queue<String> queue) {
        String value = queue.poll();
        if (value == null || "#".equals(value)) {
            return null;
        }
        Node node = new Node();
        node.value = Integer.parseInt(value);
        node.left = reconstructPreOrder(queue);
        node.right = reconstructPreOrder(queue);
        return node;
    }

    /**
     * 递归方式实现二叉树的先序遍历
     *
     * @param node  二叉树根节点
     */
    public static void preorderTraversalRecursion(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.value);
        preorderTraversalRecursion(node.left);
        preorderTraversalRecursion(node.right);
    }

    /**
     * 非递归方式实现先序遍历
     *
     * @param root  二叉树根节点
     */
    public static void preorderTraversalNonRecursion(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.println(node.value);
            if (node.right != null) {
                stack.add(node.right);
            }
            if (node.left != null) {
                stack.add(node.left);
            }
        }
    }

    /**
     * 递归方式实现二叉树的后续遍历
     *
     * @param node  二叉树根节点
     */
    public static void postorderTraversalRecursion(Node node) {
        if (node == null) {
            return;
        }
        postorderTraversalRecursion(node.left);
        postorderTraversalRecursion(node.right);
        System.out.println(node.value);
    }

    /**
     * 非递归方式实现二叉树的后序遍历
     *
     * @param root  二叉树的根节点
     */
    public static void postorderTraversalNonRecursion(Node root) {

    }

    /**
     * 递归方式实现二叉树中序遍历
     *
     * @param node  二叉树根节点
     */
    public static void inorderTraversalRecursion(Node node) {
        if (node == null) {
            return;
        }
        inorderTraversalRecursion(node.left);
        System.out.println(node.value);
        inorderTraversalRecursion(node.right);
    }
}
