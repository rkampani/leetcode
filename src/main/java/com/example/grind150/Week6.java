package com.example.grind150;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Week6 {

    /**
     * Q1:Longest Substring Without Repeating Characters
     * Given a string s, find the length of the longest substring without duplicate characters.
     * Example 1:
     * <p>
     * Input: s = "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     */
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int start = 0;
        int maxLength = 0;
        for (int end = 0; end < s.length(); end++) {

            while (set.contains(s.charAt(end))) {
                set.remove(s.charAt(start));
                start++;
            }

            set.add(s.charAt(end));
            maxLength = Math.max(maxLength, end - start + 1);
        }
        return maxLength;
    }

    /**
     * Q2: 3Sum
     * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]]
     * such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
     * <p>
     * Notice that the solution set must not contain duplicate triplets.
     * Input: nums = [-1,0,1,2,-1,-4]
     * Output: [[-1,-1,2],[-1,0,1]]
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        // Step 1: Sort the array
        Arrays.sort(nums);

        // Step 2: Iterate over the array
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates for the first element
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            // Step 3: Two pointers approach
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {
                    // Found a valid triplet
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // Move left pointer to avoid duplicates
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    // Move right pointer to avoid duplicates
                    while (left < right && nums[right] == nums[right - 1]) right--;

                    // Move both pointers after finding a valid triplet
                    left++;
                    right--;
                } else if (sum < 0) {
                    // Move the left pointer to the right to increase the sum
                    left++;
                } else {
                    // Move the right pointer to the left to decrease the sum
                    right--;
                }
            }
        }

        return result;
    }

    /**
     * Q3: Binary Tree Level Order Traversal
     * Given the root of a binary tree, return the level order traversal of its nodes' values.
     * (i.e., from left to right, level by level).
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        Queue<TreeNode> queue = new ArrayDeque();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; ++i) {
                TreeNode currentNode = queue.poll();
                level.add(currentNode.val);

                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }
            result.add(level);
        }
        return result;
    }

    /**
     * Q4 Clone Graph
     * Given a reference of a node in a connected undirected graph.
     * <p>
     * Return a deep copy (clone) of the graph.
     */
    public ListNode cloneGraph(ListNode node) {
        if (node == null) return null;
        Map<ListNode, ListNode> visited = new HashMap<>();
        return dfsCloneGraph(node, visited);
    }

    private ListNode dfsCloneGraph(ListNode node, Map<ListNode, ListNode> visited) {

        if (visited.containsKey(node)) {
            return visited.get(node);
        }
        ListNode clone = new ListNode(node.val);
        visited.put(node, clone);
        for (ListNode neighblor : node.neighbors) {
            clone.neighbors.add(dfsCloneGraph(neighblor, visited));
        }
        return clone;
    }

    /**
     * Q5:Evaluate Reverse Polish Notation
     * The valid operators are '+', '-', '*', and '/'.
     * Input: tokens = ["2","1","+","3","*"]
     * Output: 9
     * Explanation: ((2 + 1) * 3) = 9
     */
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        int sum = 0;
        for (String c : tokens) {

            if (isOperator(c)) {
                int a = stack.pop();
                int b = stack.pop();
                if (c.equals("+")) {
                    sum = a + b;
                } else if (c.equals("-")) {
                    sum = b - a;
                } else if (c.equals("*")) {
                    sum = b * a;
                } else if (c.equals("/")) {
                    sum = b / a;
                }
                stack.push(sum);
            } else {
                stack.push(Integer.valueOf(c));
            }

        }
        return sum;
    }

    private boolean isOperator(String c) {
        return Objects.equals(c, "+") || Objects.equals(c, "-") || Objects.equals(c, "*") || Objects.equals(c, "/");
    }

    /**
     * Course Schedule
     * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
     * You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first
     * if you want to take course ai.
     * <p>
     * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
     * Return true if you can finish all courses. Otherwise, return false.
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList());
        }
        int[] visited = new int[numCourses];
        for (int[] pre : prerequisites) {
            int course = pre[0];
            int preReq = pre[1];
            graph.get(preReq).add(course);
        }
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) { // If the course has not been visited
                if (hasCycleCanFinish(i, graph, visited)) {
                    return false; // If cycle is detected, return false
                }
            }
        }
        return true;
    }

    private boolean hasCycleCanFinish(int course, List<List<Integer>> graph, int[] visited) {
        if (visited[course] == 1) {
            return true; // Cycle detected
        }
        if (visited[course] == 2) {
            return false; // Already visited, no cycle
        }

        visited[course] = 1; // Mark the node as visiting

        // Explore all the neighbors (prerequisites)
        for (int neighbor : graph.get(course)) {
            if (hasCycleCanFinish(neighbor, graph, visited)) {
                return true; // If any neighbor causes a cycle, return true
            }
        }

        visited[course] = 2; // Mark the node as visited
        return false;
    }

    public static void main(String[] args) {
        Week6 week6 = new Week6();


        System.out.println("Q1 lengthOfLongestSubstring " + week6.lengthOfLongestSubstring("abcabcbb"));
        System.out.println("Q1 lengthOfLongestSubstring " + week6.lengthOfLongestSubstring("bbbbb"));
        System.out.println("Q1 lengthOfLongestSubstring " + week6.lengthOfLongestSubstring("pwwkew"));

        // Example 1
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        int[] nums2 = {0, 0, 0, 0};
        int[] nums3 = {-1, 0, 1};
        System.out.println("Q2 threeSum " + week6.threeSum(nums1));
        System.out.println("Q2 threeSum " + week6.threeSum(nums2));
        System.out.println("Q2 threeSum " + week6.threeSum(nums3));

        // Constructing the tree:
        //       3
        //      / \
        //     9  20
        //        /  \
        //       15   7
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        TreeNode root1 = new TreeNode(1);
        System.out.println("Q3 LevelOrderTraversal " + week6.levelOrder(root));
        System.out.println("Q3 LevelOrderTraversal " + week6.levelOrder(root1));


        // Creating a simple graph for testing
        // Graph:   1
        //   / \
        //  2   4
        //  |
        //  3
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);

        node1.neighbors.add(node2);
        node1.neighbors.add(node4);
        node2.neighbors.add(node1);
        node2.neighbors.add(node3);
        node3.neighbors.add(node2);
        node4.neighbors.add(node1);

        System.out.println("Q4 cloneGraph ");
        ListNode.printList(week6.cloneGraph(node1));


        System.out.println("Q5 evalRPN " + week6.evalRPN(new String[]{"2", "1", "+", "3", "*"}));
        System.out.println("Q5 evalRPN " + week6.evalRPN(new String[]{"4", "13", "5", "/", "+"}));


        int numCourses = 2;
        int[][] prerequisites = {{1, 0}}; // 1 requires 0 as a prerequisite

        System.out.println("Q6 canFinish " + week6.canFinish(numCourses, prerequisites)); // Expected output: true

        numCourses = 2;
        prerequisites = new int[][]{{1, 0}, {0, 1}}; // 1 requires 0, and 0 requires 1, creating a cycle
        System.out.println("Q6 canFinish " + week6.canFinish(numCourses, prerequisites)); // Expected output: false

    }
}
