package com.example.grind150;

import java.util.ArrayList;
import java.util.List;

public class ListNode {

    int val;
    ListNode next;
    public List<ListNode> neighbors;
    ListNode() {};
    ListNode(int val) {
        this.val = val;
        neighbors = new ArrayList<>();
    }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println();
    }

    public  static  ListNode createList(int[] arr) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int val : arr) {
            current.next = new ListNode(val);
            current = current.next;
        }
        return dummy.next;
    }

    // Helper function to create a linked list and set up a cycle (for testing)
    public static ListNode createList(int[] values, int pos) {
        ListNode head = null;
        ListNode temp = null;
        ListNode cycleNode = null;

        for (int i = 0; i < values.length; i++) {
            ListNode newNode = new ListNode(values[i]);
            if (head == null) {
                head = newNode;
                temp = head;
            } else {
                temp.next = newNode;
                temp = temp.next;
            }

            // Mark the node at position "pos" for the cycle connection
            if (i == pos) {
                cycleNode = newNode;
            }
        }

        // If pos is valid, create a cycle by connecting the last node to the node at "pos"
        if (cycleNode != null) {
            temp.next = cycleNode;
        }

        return head;
    }

}
