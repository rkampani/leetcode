package com.example.grind150;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import static com.example.grind150.ListNode.printList;
import static com.example.grind150.Utils.log;

public class Week3 {

    /**
     * Q1:Add Binary
     * Given two binary strings a and b, return their sum as a binary string.
     */
    public String addBinary(String a, String b) {
        if (a == null && b == null) return null;
        StringBuilder result = new StringBuilder();

        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;

        // Traverse both strings from the end towards the beginning
        while (i >= 0 || j >= 0 || carry != 0) {
            int sum = carry;

            if (i >= 0) {
                sum += a.charAt(i) - '0';  // Convert char to int (0 or 1)
                i--;
            }

            if (j >= 0) {
                sum += b.charAt(j) - '0';  // Convert char to int (0 or 1)
                j--;
            }

            result.append(sum % 2);  // Append the current bit (sum modulo 2)
            carry = sum / 2;
        }
        return result.reverse().toString();
    }

    /**
     * Q2: Diameter of Binary Tree
     * Given the root of a binary tree, return the length of the diameter of the tree.
     * The diameter at this node is the sum of the heights of the left and right subtrees
     */
    int maxDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        calculateHeight(root);
        return maxDiameter;
    }

    private int calculateHeight(TreeNode root) {
        if (root == null) return 0;

        int left = calculateHeight(root.left);
        int right = calculateHeight(root.right);
        // The diameter at this node is the sum of the heights of the left and right subtrees
        maxDiameter = Math.max(left + right, maxDiameter);
        return Math.max(left, right) + 1;
    }

    /**
     * Q3:Middle of the Linked List
     * Given the head of a singly linked list, return the middle node of the linked list..
     */
    public ListNode middleNode(ListNode head) {
        if (head == null) return null;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * Q4: Maximum Depth of Binary Tree
     * Given the root of a binary tree, return its maximum depth.
     * <p>
     * A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;

        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;
    }

    /**
     * Q5 Contains Duplicate
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Q6 :canAttendMeetings
     * Given an array of meeting intervals where intervals[i] = [start, end], determine if a person can attend all meetings in a single room.
     * [[0, 30], [5, 10], [15, 20]]
     */
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null) return false;
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        for (int i = 1; i < intervals.length; i++) {
            int start = intervals[i][0];
            int prevEnd = intervals[i - 1][1];
            if (prevEnd > start) {
                return false;
            }
        }
        return true;
    }

    /**
     * Q6 :Meeting Rooms
     * Given an array of meeting intervals, find the minimum number of meeting rooms required to accommodate all meetings.
     * [[0, 30], [5, 10], [15, 20]]
     * [{0, 5}, {5, 10}, {10, 15}, {10, 20}, {15, 25}];
     */
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null) return 0;
        Arrays.sort(intervals, Comparator.comparing(a -> a[0]));
        // Min-heap to track the earliest end time of meetings
        PriorityQueue<Integer> priorityQueue = new PriorityQueue();
        priorityQueue.add(intervals[0][1]);
        // Process the remaining meetings
        for (int i = 1; i < intervals.length; i++) {
            int start = intervals[i][0];
            int end = intervals[i][1];

            // If the room with the earliest end time is free (start >= heap.peek())
            if (start >= priorityQueue.peek()) {
                priorityQueue.poll(); // Remove the earliest end time
            }

            // Add the current meeting's end time to the heap
            priorityQueue.add(end);
        }
        return priorityQueue.size();
    }

    /**
     * Q7 :Roman to Integer
     * "III" = 3
     * LVIII = 58
     */
    private static Map<String, Integer> ROMAN_INTEGER_MAP = new HashMap<>();

    static {
        ROMAN_INTEGER_MAP.put("M", 1000);
        ROMAN_INTEGER_MAP.put("D", 500);
        ROMAN_INTEGER_MAP.put("C", 100);
        ROMAN_INTEGER_MAP.put("L", 50);
        ROMAN_INTEGER_MAP.put("X", 10);
        ROMAN_INTEGER_MAP.put("V", 5);
        ROMAN_INTEGER_MAP.put("I", 1);
    }

    public int romanToInt(String s) {
        if (s == null) return 0;
        int length = s.length();
        String lastSymbol = String.valueOf(s.charAt(length - 1));
        int lastValue = ROMAN_INTEGER_MAP.get(lastSymbol);
        int sum = lastValue;
        for (int i = length - 2; i >= 0; i--) {
            int currentValue = ROMAN_INTEGER_MAP.get(String.valueOf(s.charAt(i)));

            if (currentValue >= lastValue) {
                sum += currentValue;
            } else {
                sum -= currentValue;
            }
            lastValue = currentValue;
        }
        return sum;
    }

    /**
     * Q8:Backspace String Compare
     * Given two strings s and t, return true if they are equal when both are typed into empty text editors. '#' means a backspace character.
     * Input: s = "ab#c", t = "ad#c"
     * Output: true
     * Explanation: Both s and t become "ac".
     */
    public boolean backspaceCompare(String s, String t) {
        return backspace(s).equals(backspace(t));
    }

    private String backspace(String input) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (c == '#') {
                if (stringBuilder.length() > 0) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Q9: Counting Bits
     * Given an integer n, return an array ans of length n + 1
     * such that for each i (0 <= i <= n), ans[i] is the number of 1's in the binary representation of i.
     */
    public int[] countBits(int n) {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            /**
             * count[i >> 1] is the count of 1's in i // 2.
             * (i & 1) is 1 if i is odd (meaning it ends with a 1), and 0 if i is even.
             */
            ans[i] = ans[i >> 1] + (i & 1);
        }
        return ans;
    }

    public static void main(String[] args) {
        Week3 week3 = new Week3();

        System.out.println("Q1 Add Binary: " + week3.addBinary("1010", "1011"));
        System.out.println("Q1 Add Binary: " + week3.addBinary("11", "1"));
        System.out.println("Q1 Add Binary: " + week3.addBinary("1101", "1"));

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        System.out.println("Q2: Diameter of the binary tree: " + week3.diameterOfBinaryTree(root));

        // Create a Linked List: 1 -> 2 -> 3 -> 4 -> 5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        System.out.print("Q3: Middle of the Linked List: ");
        printList(week3.middleNode(head));

        System.out.println("Q4: Maximum Depth of Binary Tree: " + week3.maxDepth(root));

        System.out.println("Q5: Contains Duplicate " + week3.containsDuplicate(new int[]{1, 2, 2, 3, 4, 6}));
        System.out.println("Q5: Contains Duplicate " + week3.containsDuplicate(new int[]{1, 2, 3, 4, 6}));

        int[][] intervals = {{0, 30}, {5, 10}, {15, 20}};
        int[][] intervals2 = {{0, 5}, {5, 10}, {10, 15}, {10, 20}, {15, 25}};
        System.out.println("Q6a: canAttendMeetings " + week3.canAttendMeetings(intervals));
        System.out.println("Q6a: canAttendMeetings " + week3.canAttendMeetings(intervals2));
        System.out.println("Q6b: minMeetingRooms " + week3.minMeetingRooms(intervals));
        System.out.println("Q6b: minMeetingRooms " + week3.minMeetingRooms(intervals2));

        System.out.println("Q7 romanToInt " + week3.romanToInt("III"));
        System.out.println("Q7 romanToInt " + week3.romanToInt("LVIII"));

        System.out.println("Q8 backspaceCompare " + week3.backspaceCompare("ab#c", "ad#c"));
        System.out.println("Q8 backspaceCompare " + week3.backspaceCompare("ab##", "c#d#"));
        System.out.println("Q8 backspaceCompare " + week3.backspaceCompare("a#c", "t"));
        System.out.println("Q8 backspaceCompare " + week3.backspaceCompare("LVIII", "LV#III"));

        log("Q9 countBits ", week3.countBits(9));
        log("Q9 countBits ", week3.countBits(2));
        log("Q9 countBits ", week3.countBits(5));
    }

}
