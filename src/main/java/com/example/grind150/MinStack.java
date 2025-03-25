package com.example.grind150;

import java.util.Stack;

public class MinStack {
    int min;
    Stack<Integer> stack = new Stack<>();

    public void push(int value) {
        if (value <= min) {
            stack.push(min);
            min = value;
        }
        stack.push(value);
    }

    public void pop() {
        if (stack.pop() == min) {
            min = stack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }
}
