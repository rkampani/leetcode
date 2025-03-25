package com.example.grind150;

import java.util.Stack;

public class QueueImplUsingStack {

    private Stack<Integer> pushStack = new Stack<>();
    private Stack<Integer> popStack = new Stack<>();

    QueueImplUsingStack() {

    }

    public void push(int val) {
        pushStack.push(val);
    }

    public int pop() {
        moveStacks();
        return popStack.pop();
    }

    public int peek() {
        moveStacks();
        return popStack.peek();
    }

    public boolean empty() {
        return popStack.isEmpty() && pushStack.isEmpty();
    }

    private void moveStacks() {
        if (popStack.isEmpty()) {
            while (!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }
    }

    public static void main(String[] args) {
        QueueImplUsingStack queueImplUsingStack = new QueueImplUsingStack();
        queueImplUsingStack.push(1);
        queueImplUsingStack.push(2);
        queueImplUsingStack.push(3);
        System.out.println("QueueImplUsingStack "+queueImplUsingStack.pop());
        System.out.println("QueueImplUsingStack "+queueImplUsingStack.pop());
        System.out.println("QueueImplUsingStack "+queueImplUsingStack.pop());
    }
}
