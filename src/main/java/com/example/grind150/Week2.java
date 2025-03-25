package com.example.grind150;

import java.util.HashMap;
import java.util.Map;

import static com.example.grind150.ListNode.createList;
import static com.example.grind150.ListNode.printList;
import static com.example.grind150.TreeNode.insertTreeNode;

public class Week2 {

    /**
     * Q1:Balanced Binary Tree
     * Given a binary tree, determine if it is height-balanced.
     * A Balanced Binary Tree is a binary tree in which the depth (height) of the left and right subtrees of every node differ
     * by at most one. In other words,
     * the tree is balanced if, for every node, the height of the left subtree and the height of the right subtree differ by no more than 1.
     */
    public boolean isBalancedBinaryTree(TreeNode root) {
        return heightCheck(root) >= 0;
    }

    private int heightCheck(TreeNode root) {
        if (root == null) return 0;

        int leftHeight = heightCheck(root.left);
        int rightHeight = heightCheck(root.right);
        // Check if left or right subtree is unbalanced or if they differ in height by
        // more than 1.
        if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1; // Tree is not balanced.
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Q@: Linked List Cycle
     * Given head, the head of a linked list, determine if the linked list has a cycle in it.
     * <p>
     * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously
     * following the next pointer. Internally,
     * pos is used to denote the index of the node that tail's next pointer is connected to. Note that pos is not passed as a parameter.
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;

        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /**
     * Q3:  First Bad Version
     * You are a product manager and currently leading a team to develop a new product. Unfortunately,
     * the latest version of your product fails the quality check. Since each version is developed based on the previous version,
     * all the versions after a bad version are also bad.
     * <p>
     * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad.
     */
    public int firstBadVersion(int n) {

        int left = 0;
        int right = n;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (isBadVersion(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }

        }
        return left;
    }


    private boolean isBadVersion(int mid) {
        return true;
    }

    /**
     * Q4: Given two strings ransomNote and magazine, return true if ransomNote can be constructed by using the letters
     * from magazine and false otherwise.
     * <p>
     * Each letter in magazine can only be used once in ransomNote.
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        if (magazine == null || ransomNote == null) return false;
        if (magazine.length() < ransomNote.length()) return false;

        Map<Character, Integer> charzterIntegerCount = new HashMap<>();
        for (char c : magazine.toCharArray()) {
            charzterIntegerCount.put(c, charzterIntegerCount.getOrDefault(c, 0) + 1);
        }

        for (char c : ransomNote.toCharArray()) {
            int cnt = charzterIntegerCount.getOrDefault(c, 0);
            if (cnt <= 0) return false;
            else charzterIntegerCount.put(c, charzterIntegerCount.getOrDefault(c, 0) - 1);
        }
        return true;

    }

    /**
     * Q5: Climbing Stairs
     * You are climbing a staircase. It takes n steps to reach the top.
     * <p>
     * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
     */
    public int climbStairs(int n) {
        if (n == 0) return 0;

        int prev1 = 1;
        int prev2 = 1;
        for (int i = 2; i <= n; i++) {
            int climb = prev2 + prev1;
            prev2 = prev1;
            prev1 = climb;

        }

        return prev1;
    }

    public int climbStairsDP(int n) {
        if (n == 0) return 0;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; ++i) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];

    }

    /**
     * Q6: Longest Palindrome
     * Given a string s which consists of lowercase or uppercase letters, return the length of the
     * longest palindrome that can be built with those letters.
     */
    public int longestPalindrome(String s) {

        if (s == null || s.isEmpty()) return -1;
        Map<Character, Integer> charcaterIndexCount = new HashMap<>();

        for (char c : s.toCharArray()) {
            charcaterIndexCount.put(c, charcaterIndexCount.getOrDefault(c, 0) + 1);
        }
        int length = 0;
        boolean hasOddFrequency = false;
        for (int count : charcaterIndexCount.values()) {
            length += (count / 2) * 2;

            if (count % 2 != 0) {
                hasOddFrequency = true;
            }
        }
        if (hasOddFrequency) {
            length += 1;
        }

        return length;
    }

    /**
     * Q7: Reverse Linked List
     * Given the head of a singly linked list, reverse the list, and return the reversed list.
     */
    public ListNode reverseList(ListNode head) {
        if (head == null) return null;

        ListNode prev = null;
        ListNode current = head;
        while (current != null) {
            ListNode temp = current.next;
            current.next = prev;
            prev = current;
            current = temp;
        }
        return prev;
    }

    /**
     * Q8: Major Element
     * Given an array nums of size n, return the majority element.
     *
     * The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority element always exists in the array.
     *
     */
    public int majorityElement(int[] nums) {
        if(nums == null) return -1;

        int candidate = -1;
        int count = 0;
        for (int num : nums) {
            if (count == 0) {
                candidate = num; // Set new candidate
            }
            count += (num == candidate) ? 1 : -1;
        }
        return candidate;

    }
    public static void main(String[] args) {

        Week2 week2 = new Week2();

        TreeNode root = null;
        root = insertTreeNode(root, 3);
        insertTreeNode(root, 9);
        insertTreeNode(root, 20);
        insertTreeNode(root, 15);
        insertTreeNode(root, 7);

        TreeNode unbalancedRoot = null;
        unbalancedRoot = insertTreeNode(unbalancedRoot, 1);
        insertTreeNode(unbalancedRoot, 2);
        insertTreeNode(unbalancedRoot, 3);
        insertTreeNode(unbalancedRoot, 4);
        insertTreeNode(unbalancedRoot, 5);
        insertTreeNode(unbalancedRoot, 6);
        System.out.println("Q1: isBalancedBinaryTree: " + week2.isBalancedBinaryTree(root));
        System.out.println("Q1: isBalancedBinaryTree: " + week2.isBalancedBinaryTree(unbalancedRoot));

        int[] values = {3, 2, 0, -4};
        int pos = 1;  // The index where the tail's next pointer will connect (cycle start)
        int[] valuesNoCycle = {1, 2, 3};
        int posNoCycle = -1;  // No cycle
        System.out.println("Q2: hasCycle: " + week2.hasCycle(createList(values, pos)));
        System.out.println("Q2: hasCycle: " + week2.hasCycle(createList(valuesNoCycle, posNoCycle)));

        System.out.println("Q4: canConstruct: " + week2.canConstruct("a", "b"));
        System.out.println("Q4: canConstruct: " + week2.canConstruct("aa", "ab"));
        System.out.println("Q4: canConstruct: " + week2.canConstruct("aa", "aab"));

        System.out.println("Q5: climbStairs: " + week2.climbStairs(2));
        System.out.println("Q5: climbStairs: " + week2.climbStairs(3));
        System.out.println("Q5: climbStairs Using DP: " + week2.climbStairsDP(2));
        System.out.println("Q5: climbStairs Using DP: " + week2.climbStairsDP(3));

        System.out.println("Q6: longestPalindrome: " + week2.longestPalindrome("abccccdd"));
        System.out.println("Q6: longestPalindrome: " + week2.longestPalindrome("a"));
        System.out.println("Q6: longestPalindrome: " + week2.longestPalindrome("bb"));


        System.out.println("Q7: reverseList: " );
        printList(week2.reverseList(createList(values)));
        System.out.println("Q7: reverseList: " );
        printList(week2.reverseList(createList(valuesNoCycle)));


        System.out.println("Q8: majorityElement: " + week2.majorityElement(new int[]{3, 2, 3}));
        System.out.println("Q8: majorityElement: " + week2.majorityElement(new int[]{2, 2, 1, 1, 1, 2, 2}));

    }
}
