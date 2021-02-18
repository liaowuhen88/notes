package com.zwc.notes.rk.stack;

import java.util.Stack;

/**
 * @author zhangwenchao19
 * 栈排序,
 */
public class StackSort {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack();
        stack.push(9);
        stack.push(3);
        stack.push(5);
        stack.push(4);
        stack.push(8);
        stack.push(6);
        stack.push(2);
        stack.push(7);
        stack.push(1);
        System.out.println(stack);

        Stack help = sort(stack);

        System.out.println(help);

    }


    private static Stack sort(Stack<Integer> stack) {
        Stack<Integer> help = new Stack();
        int curl = stack.pop();
        help.push(curl);

        while (!stack.empty()) {
            int val = stack.pop();

            while (!help.empty()) {
                int valHelp = help.pop();
                if (val < valHelp) {
                    help.push(valHelp);
                    break;
                } else {
                    stack.push(valHelp);
                }
            }
            help.push(val);
            System.out.println("help:" + help);
        }
        return help;
    }
}
