package com.example.grind150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Week15 {

    /**
     * Swap Nodes in Pairs
     * Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem
     * without modifying the values in the list's nodes (i.e., only nodes themselves may be changed.)
     * Example 1:
     * Input: head = [1,2,3,4]
     * Output: [2,1,4,3]
     * Input: head = [1,2,3]
     * Output: [2,1,3]
     */
    public ListNode swapPairs(ListNode head) {

        // Create a dummy node that acts as the previous node to the head
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prev = dummy;
        // * Input: head = [1,2,3,4]
        //     * Output: [2,1,4,3]
        while (head != null && head.next != null) {
            ListNode first = head;
            ListNode second = head.next;

            // Perform the swap
            prev.next = second;
            first.next = second.next;
            second.next = first;

            // Move prev and head pointers
            prev = first;
            head = first.next;
        }

        return dummy.next;
    }

    /**
     * Q2: Path Sum
     * Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
     * Output: [[5,4,11,2],[5,8,4,5]]
     * Explanation: There are two paths whose sum equals targetSum:
     * 5 + 4 + 11 + 2 = 22
     * 5 + 8 + 4 + 5 = 22
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if (root == null || targetSum == 0)
            return Collections.emptyList();

        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentPath = new ArrayList<>();

        // Start DFS
        dfsPathSum(root, targetSum, currentPath, result);

        return result;
    }

    private void dfsPathSum(TreeNode node, int remainingSum, List<Integer> currentPath, List<List<Integer>> result) {

        if (node == null) {
            return;
        }
        remainingSum -= node.val;
        currentPath.add(node.val);
        if (node.left == null && node.right == null && remainingSum == 0) {
            // If so, add a copy of the current path to the list of valid paths
            result.add(new ArrayList<>(currentPath));
        }

        dfsPathSum(node.left, remainingSum, currentPath, result);
        dfsPathSum(node.right, remainingSum, currentPath, result);
        currentPath.remove(currentPath.size() - 1);
    }

    /**
     * Q3 Longest Consecutive Sequence
     * Input: nums = [100,4,200,1,3,2]
     * Output: 4
     * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int longestStreak = 0;
        for (int num : nums) {

            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                // Initialize the current streak length.
                int currentStreak = 1;
                while (numSet.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                // Update the longest streak found so far.
                longestStreak = Math.max(longestStreak, currentStreak);

            }
        }
        return longestStreak;
    }

    /**
     * Q4: Rotate Array
     * Input: nums = [1,2,3,4,5,6,7], k = 3
     * Output: [5,6,7,1,2,3,4]
     */

    public void rotate(int[] nums, int k) {
        if (nums == null) return;

        reverseList(nums, 0, nums.length - 1);
        reverseList(nums, 0, k - 1);
        reverseList(nums, k, nums.length - 1);

        System.out.println("Q4 RotateArray " + Arrays.toString(nums));
    }

    private void reverseList(int[] nums, int start, int end) {

        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    /**
     * Q5:Odd Even Linked List
     * Input: head = [1,2,3,4,5]
     * Output: [1,3,5,2,4]
     */
    public ListNode oddEvenList(ListNode head) {

        if (head == null) return head;

        // Initialize pointers for odd and even lists
        ListNode odd = head; // odd =1
        ListNode even = head.next; //even =2
        ListNode evenHead = even;  // Keep the head of the even list to connect at the end
        while (even != null && even.next != null) {

            odd.next = even.next; // 1->3
            odd = odd.next;  // odd == 3
            even.next = odd.next; // 2 -> 4
            even = even.next; //even =4

        }
        // Link the last odd node to the head of the even list
        odd.next = evenHead;
        return head;
    }

    /**
     * Q6 Decode String
     * Input: s = "3[a]2[bc]"
     * Output: "aaabcbc"
     */
    public String decodeString(String s) {
        if (s == null) return null;
        Stack<String> stack = new Stack<>();
        int currentNumber = 0;
        StringBuilder currentString = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                // If the character is a digit, update the multiplier (k)
                currentNumber = currentNumber * 10 + (c - '0');  // Handle multi-digit numbers
            } else if (c == '[') {
                // If we encounter '[', push the current string and the multiplier to the stack
                stack.push(currentString.toString());
                stack.push(String.valueOf(currentNumber));
                // Reset current string for the new substring to decode
                currentString = new StringBuilder();
                // Reset the multiplier
                currentNumber = 0;
            } else if (c == ']') {
                // If we encounter ']', pop the last multiplier and previous string from the stack
                int repeatCount = Integer.parseInt(stack.pop());
                String previousString = stack.pop();
                // Append the current decoded substring to the previous string
                for (int i = 0; i < repeatCount; i++) {
                    previousString += currentString.toString();
                }
                // Set the current string to the new decoded string
                currentString = new StringBuilder(previousString);
            } else {
                // If the character is a letter, simply add it to the current string
                currentString.append(c);
            }
        }

        return currentString.toString();
    }

    public static void main(String[] args) {
        Week15 week15 = new Week15();

        // Create a linked list: 1 -> 2 -> 3 -> 4
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        // Swap nodes in pairs
        ListNode result = week15.swapPairs(node1);
        System.out.println("Q1 swapPairs ");
        ListNode.printList(result);


        // Constructing the tree:
        //       5
        //      / \
        //     4   8
        //    /   / \
        //   11  13  4
        //  /  \      \
        // 7    2      1

        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.right = new TreeNode(1);

        System.out.println("Q2 TreeSumII-pathSum" + week15.pathSum(root, 22));

        System.out.println("Q3 longestConsecutive " + week15.longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
        System.out.println("Q3 longestConsecutive " + week15.longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
        System.out.println("Q3 longestConsecutive " + week15.longestConsecutive(new int[]{1, 0, 1, 2}));

        week15.rotate(new int[]{1, 2, 3, 4, 5, 6, 7}, 3);
        week15.rotate(new int[]{-1, -100, 3, 99}, 2);


        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        System.out.println("Q5 oddEvenList ");
        ListNode.printList(week15.oddEvenList(head));


        System.out.println("Q6 decodeString " + week15.decodeString("3[a]2[bc]"));
        System.out.println("Q6 decodeString " + week15.decodeString("3[a2[c]]"));
        System.out.println("Q6 decodeString " + week15.decodeString("2[abc]3[cd]ef"));
    }
}
