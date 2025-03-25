package com.example.grind150;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Week17 {

    /**
     * Q1:  Add Two Numbers
     * l1 = [2, 4, 3]  // represents the number 342
     * l2 = [5, 6, 4]  // represents the number 465
     * [7, 0, 8]  // represents the number 807 (342 + 465 = 807)
     */

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) return null;
        int carry = 0;
        ListNode dummyNode = new ListNode(0);
        ListNode current = dummyNode;
        while (l1 != null || l2 != null || carry != 0) {

            int l1Value = l1 == null ? 0 : l1.val;
            int l2Value = l2 == null ? 0 : l2.val;

            int sum = l1Value + l2Value + carry;
            current.next = new ListNode(sum % 10);
            current = current.next;
            carry = sum / 10;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;

        }
        return dummyNode.next;
    }

    /**
     * Q2: Generate Parentheses
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
     */
    public List<String> generateParenthesis(int n) {
        if (n <= 0) return Collections.emptyList();
        List<String> result = new ArrayList<>();
        backTrackGenerateParenthesis(result, n, 0, 0, "");
        return result;
    }

    private void backTrackGenerateParenthesis(List<String> result, int n, int open, int close, String stringBuilder) {
        if (open == n && close == n) {
            result.add(stringBuilder);
            return;
        }
        if (open > n || close > n || open < close) {
            return;
        }
        backTrackGenerateParenthesis(result, n, open + 1, close, stringBuilder + "(");
        backTrackGenerateParenthesis(result, n, open, close + 1, stringBuilder + ")");
    }

    /**
     * Q3: Sort List
     * Given the head of a linked list, return the list after sorting it in ascending order.
     * Input: head = [4,2,1,3]
     * Output: [1,2,3,4]
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) { return null; };

        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next; // moves one step at a time
            fast = fast.next.next; // moves two steps at a time
        }

        // Split the list into two halves.
        ListNode mid = slow.next;
        slow.next = null;

        // Recursively sort each half.
        ListNode leftHalf = sortList(head);
        ListNode rightHalf = sortList(mid);

        // Merge the two halves and return the merged sorted list.
        return merge(leftHalf, rightHalf);
    }

    private ListNode merge(ListNode left, ListNode right) {
        // Create a dummy node to serve as the starting point for the merged list.
        ListNode dummyHead = new ListNode();

        // Use a pointer to build the new sorted linked list.
        ListNode current = dummyHead;
        while (left != null && right != null) {
            // Choose the node with the smaller value from either left or right,
            // and append it to the current node of the merged list.
            if (left.val <= right.val) {
                current.next = left;
                left = left.next;
            } else {
                current.next = right;
                right = right.next;
            }
            current = current.next;
        }

        // If any nodes remain in either list, append them to the end of the merged
        // list.
        current.next = (left == null) ? right : left;

        // Return the head of the merged sorted list, which is the next node of the
        // dummy node.
        return dummyHead.next;
    }

    /**
     * Q4: Number of Connected Components in an Undirected Graph
     *
     *
     */
    public int countComponents(int n, int[][] edges) {
        List<List<Integer>> nodeList = new ArrayList<>();
        for(int i=0;i<n;i++) {
            nodeList.add(new ArrayList<>());
        }
        for(int[] edge: edges) {
            nodeList.get(edge[0]).add(edge[1]);
            nodeList.get(edge[1]).add(edge[0]);
        }

        boolean[] visited = new boolean[n];
        int componentsCount = 0;

        for (int i = 0; i < n; i++) {
            if(!visited[i]) {
                dfsCountComponentsGraphEdges(i, visited, nodeList);
                componentsCount++;
            }
        }
        return componentsCount;
    }

    private static void dfsCountComponentsGraphEdges(int node, boolean[] visited, List<List<Integer>> graph) {

        visited[node] = true;
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfsCountComponentsGraphEdges(neighbor, visited, graph);
            }
        }
    }


    /**
     * Q5: minimum-knight-moves
     * The Minimum Knight Moves problem is a classic problem that involves finding
     * the minimum number of moves a knight on a chessboard needs to travel from a starting position to a target position.
     * It can move two squares in one direction and one square in the perpendicular direction.
     *
     * From a given position (x, y), the knight can move to 8 possible new positions:
     */
    private static final int[] dx = {2, 2, -2, -2, 1, 1, -1, -1};
    private static final int[] dy = {1, -1, 1, -1, 2, -2, 2, -2};
    public int minKnightMoves(int x1, int y1, int x2, int y2) {
        // If the start and target are the same, no moves are needed
        if (x1 == x2 && y1 == y2) {
            return 0;
        }

        // BFS
        Queue<int[]> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        ///start position
        queue.offer(new int[]{x1, y1, 0});  // {x, y, distance}
        visited.add(x1 + "," + y1);

        while(!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int dist = current[2];

            // Try all 8 possible moves of the knight
            for (int i = 0; i < 8; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];

                // If we've reached the target, return the distance
                if (newX == x2 && newY == y2) {
                    return dist + 1;
                }

                // If the new position is not visited yet, add it to the queue
                if (!visited.contains(newX + "," + newY)) {
                    visited.add(newX + "," + newY);
                    queue.offer(new int[]{newX, newY, dist + 1});
                }
            }
        }
        return -1;
    }

    /**
     * Q6: Subarray Sum Equals K
     * Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.
     *
     * A subarray is a contiguous non-empty sequence of elements within an array.
     *Input: nums = [1,2,3], k = 3
     * Output: 2
     */
    public int subarraySum(int[] nums, int k) {

        HashMap<Integer, Integer> sumMap = new HashMap<>();

        sumMap.put(0, 1);
        int count = 0;
        int sumSoFar = 0;

        for(int num: nums) {
            sumSoFar  = sumSoFar+ num;
            // If currentSum - k has been encountered before, it means there is a subarray sum equals to k
            if(sumMap.containsKey(sumSoFar -k)) {
                count += sumMap.get(sumSoFar - k);
            }
            sumMap.put(sumSoFar, sumMap.getOrDefault(sumSoFar, 0) + 1);
        }
        return count;
    }
    public static void main(String[] args) {
        Week17 week17 = new Week17();

        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        System.out.println("Q1 addTwoNumbers ");
        ListNode result = week17.addTwoNumbers(l1, l2);
        ListNode.printList(result);

        System.out.println("Q2 generateParenthesis  " + week17.generateParenthesis(3));
        System.out.println("Q2 generateParenthesis  " + week17.generateParenthesis(1));

        ListNode head = new ListNode(4);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(3);

        ListNode sortList = week17.sortList(head);
        System.out.println("Q3 sortList ");
        ListNode.printList(sortList);

        int n = 5;  // Number of nodes
        int[][] edges = {{0, 1}, {0, 2}, {3, 4}};  // List of edges
        System.out.println("Q4 countComponentsInGraph "+week17.countComponents(n, edges));

        System.out.println("Q5 minKnightMoves " +week17.minKnightMoves(0, 0, 2, 1));  // Output: 1
        System.out.println("Q5 minKnightMoves "+week17.minKnightMoves(0, 0, 3, 3));  // Output: 2

        System.out.println("Q6 Subarray Sum Equals K " +week17.subarraySum(new int[] {1,2,3}, 3));
        System.out.println("Q6 Subarray Sum Equals K " +week17.subarraySum(new int[] {1,1,1}, 2));
        System.out.println("Q6 Subarray Sum Equals K " +week17.subarraySum(new int[] {1,2,3,4,5}, 9));
    }
}
