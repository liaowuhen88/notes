package com.zwc.notes.rk.treeNode;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

public class Solution {

    public static void main(String[] args) {
        int[] pre = {1, 2, 3, 4, 5, 6, 7};
        int[] in = {3, 2, 4, 1, 6, 5, 7};
        //TreeNode node =  reConstructBinaryTree(pre,in);

        TreeNode node2 = reConstructBinaryTree2(pre, in);

        System.out.println("mm");
    }

    public static TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        System.out.println("****************");

        if (null == pre || null == in) {
            return null;
        }
        int root = pre[0];
        System.out.println("root:" + root);
        System.out.println("pre:" + JSON.toJSONString(pre));
        System.out.println("in:" + JSON.toJSONString(in));
        TreeNode treeNode = new TreeNode(root);

        int index = index(root, in);

        System.out.println("index" + index);
        int[] inleft;
        int[] preleft;
        int[] inRight;
        int[] preRight;

        inleft = Arrays.copyOfRange(in, 0, index);
        preleft = Arrays.copyOfRange(pre, 1, inleft.length + 1);

        inRight = Arrays.copyOfRange(in, index + 1, in.length);
        preRight = Arrays.copyOfRange(pre, inleft.length + 1, pre.length);


        treeNode.left = reConstructBinaryTree(preleft, inleft);
        treeNode.right = reConstructBinaryTree(preRight, inRight);


        return treeNode;
    }

    public static int index(int root, int[] in) {
        for (int i = 0; i < in.length; i++) {
            if (in[i] == root) {
                return i;
            }
        }
        return -1;
    }


    public static TreeNode reConstructBinaryTree2(int[] pre, int[] in) {
        if (pre.length == 0 || in.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(pre[0]);
        // 在中序中找到前序的根
        for (int i = 0; i < in.length; i++) {
            if (in[i] == pre[0]) {
                // 左子树，注意 copyOfRange 函数，左闭右开
                root.left = reConstructBinaryTree2(Arrays.copyOfRange(pre, 1, i + 1), Arrays.copyOfRange(in, 0, i));
                // 右子树，注意 copyOfRange 函数，左闭右开
                root.right = reConstructBinaryTree2(Arrays.copyOfRange(pre, i + 1, pre.length), Arrays.copyOfRange(in, i + 1, in.length));


                root.left = reConstructBinaryTree(Arrays.copyOfRange(pre, 1, i + 1), Arrays.copyOfRange(in, 0, i));
                root.right = reConstructBinaryTree(Arrays.copyOfRange(pre, i + 1, pre.length), Arrays.copyOfRange(in, i + 1, in.length));


                break;
            }
        }
        return root;
    }
}
