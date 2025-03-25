package com.example.grind150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Week21 {


    /**
     * Q1: All Nodes Distance K in Binary Tree
     * Given the root of a binary tree, the value of a target node target, and an integer k, return an array of the values of
     * all nodes that have a distance k from the target node.
     */

    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> result = new ArrayList<>();
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();

        // Step 1: Build the parent map by performing DFS
        dfsDistanceK(root, null, parentMap);

        // Step 2: Perform BFS to find all nodes at distance K
        Set<TreeNode> visited = new HashSet<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(target);
        visited.add(target);
        int currentDistance = 0;

        while (!queue.isEmpty()) {
            if (currentDistance == K) {
                // Add all nodes at distance K to the result
                while (!queue.isEmpty()) {
                    result.add(queue.poll().val);
                }
                return result;
            }

            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null && !visited.contains(node.left)) {
                    visited.add(node.left);
                    queue.offer(node.left);
                }
                if (node.right != null && !visited.contains(node.right)) {
                    visited.add(node.right);
                    queue.offer(node.right);
                }
                TreeNode parent = parentMap.get(node);
                if (parent != null && !visited.contains(parent)) {
                    visited.add(parent);
                    queue.offer(parent);
                }
            }
            currentDistance++;
        }

        return result;  // If no nodes found at distance K
    }

    //// Build parent map using DFS
    private static void dfsDistanceK(TreeNode node, TreeNode parent, Map<TreeNode, TreeNode> parentMap) {
        if (node == null) return;
        parentMap.put(node, parent);
        dfsDistanceK(node.left, node, parentMap);
        dfsDistanceK(node.right, node, parentMap);
    }

    /**
     * Q2: 3Sum Closest
     * Given an integer array nums of length n and an integer target, find three integers in nums such that the sum is closest to target.
     * <p>
     * Return the sum of the three integers.
     */
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);

        int closestSum = Integer.MAX_VALUE;

        // The length of the numbers array
        int length = nums.length;

        for (int i = 0; i < length; i++) {
            int left = i + 1;
            int right = length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == target) {
                    return sum;
                }
                if (sum > target) {
                    // If currentSum is greater than the target,
                    // move the right pointer to the left to reduce the sum
                    --right;
                } else {
                    // If currentSum is less than the target,
                    // move the left pointer to the right to increase the sum
                    ++left;
                }
                if (Math.abs(sum - target) < Math.abs(closestSum - target)) {
                    closestSum = sum;
                }
            }
        }
        return closestSum;
    }

    /**
     * Rotate List
     * Given the head of a linked list, rotate the list to the right by k places.
     * Input: head = [1,2,3,4,5], k = 2
     * Output: [4,5,1,2,3]
     */
    public ListNode rotateRight(ListNode head, int k) {
        // Handle edge cases
        if (head == null || head.next == null || k == 0) {
            return head;
        }

        // Step 1: Calculate length and find the last node
        int length = 1;
        ListNode current = head;
        while (current.next != null) {
            length++;
            current = current.next;
        }
        ListNode lastNode = current;

        // Step 2: Handle case where k > length
        k = k % length;
        if (k == 0) {
            return head;
        }

        // Step 3: Make the list circular
        lastNode.next = head;

        // Step 4: Find the new tail (length - k - 1) steps from head
        current = head;
        int stepsToNewHead = length - k;
        for (int i = 0; i < stepsToNewHead - 1; i++) {
            current = current.next;
        }

        // Step 5: Break the circle and set new head
        ListNode newHead = current.next;
        current.next = null;

        return newHead;

    }

    /**
     * Find Minimum in Rotated Sorted Array
     * Input: nums = [3,4,5,1,2]
     * Output: 1
     * Explanation: The original array was [1,2,3,4,5] rotated 3 times.
     */
    public int findMin(int[] nums) {

        int left = 0;
        int right = nums.length - 1;
        if (nums[0] <= nums[right - 1]) {
            return nums[0];
        }

        // I
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[0] < nums[mid]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }

    /**
     * Q5: Basic Calculator
     * Input: s = "3+2*2"
     * Output: 7
     */
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int currentNumber = 0;
        char lastOperator = '+';  // Initial operator is '+' (considering first number as positive)

        // Loop through all the characters in the string
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // Skip spaces
            if (c == ' ') continue;

            // If the current character is a digit, build the number
            if (Character.isDigit(c)) {
                currentNumber = currentNumber * 10 + (c - '0');
            }
            // If it's an operator or the last character, process the previous number
            if (!Character.isDigit(c) || i == s.length() - 1) {
                if (lastOperator == '+') {
                    stack.push(currentNumber);
                } else if (lastOperator == '-') {
                    stack.push(-currentNumber);
                } else if (lastOperator == '*') {
                    stack.push(stack.pop() * currentNumber);
                } else if (lastOperator == '/') {
                    stack.push(stack.pop() / currentNumber);
                }

                // Update the operator and reset the current number
                lastOperator = c;
                currentNumber = 0;
            }
        }

        // Sum up all the values in the stack
        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }

    /**
     * Combination Sum IV
     * Given an array of distinct integers nums and a target integer target, return the number of possible combinations that add up to target.
     * Input: nums = [1,2,3], target = 4
     * Output: 7
     * Explanation:
     * The possible combination ways are:
     * (1, 1, 1, 1)
     * (1, 1, 2)
     * (1, 2, 1)
     * (1, 3)
     * (2, 1, 1)
     * (2, 2)
     * (3, 1)
     */
    public int combinationSum4(int[] nums, int target) {

        if (nums == null) return -1;

        int[] dp = new int[target + 1];
        // There is 1 way to get a sum of 0: select nothing
        dp[0] = 1;

        // Iterate over all possible sums from 1 to target
        for (int i = 1; i <= target; i++) {
            // Try every number in the list
            for (int num : nums) {
                if (i - num >= 0) {
                    dp[i] += dp[i - num];  // Add the number of ways to get (i - num) to dp[i]
                }
            }
        }

        // Return the number of ways to reach the target
        return dp[target];
    }

    public static void main(String[] args) {
        Week21 week21 = new Week21();
        // Example Binary Tree:
        //        3
        //       / \
        //      5   1
        //     / \ / \
        //    6  2 0  8
        //      / \
        //     7   4

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);

        TreeNode target = root.left;  // Target node is 5
        int K = 2;

        List<Integer> result = week21.distanceK(root, target, K);
        System.out.println("Q1 distanceK " + result);

        System.out.println("Q2 threeSumClosest " + week21.threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
        System.out.println("Q2 threeSumClosest " + week21.threeSumClosest(new int[]{0, 0, 0}, 1));

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        int k = 2;
        ListNode rotateNode = week21.rotateRight(head, k);
        System.out.println("Q3 rotateRight");
        ListNode.printList(rotateNode);

        System.out.println("Q4 findMin " + week21.findMin(new int[]{3, 4, 5, 1, 2}));
        System.out.println("Q4 findMin " + week21.findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));
        System.out.println("Q4 findMin " + week21.findMin(new int[]{11, 13, 15, 17}));

        System.out.println("Q5 calculate " + week21.calculate("3*2+5"));
        System.out.println("Q5 calculate " + week21.calculate("3+2*2"));
        System.out.println("Q5 calculate " + week21.calculate(" 3/2 "));
        System.out.println("Q5 calculate " + week21.calculate(" 3+5 / 2"));

        int[] nums = {1, 2, 3};
        System.out.println("Q6 combinationSum4 " + week21.combinationSum4(nums, 4));
        System.out.println("Q6 combinationSum4 " + week21.combinationSum4(new int[]{9}, 4));
    }
}


