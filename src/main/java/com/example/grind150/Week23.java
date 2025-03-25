package com.example.grind150;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Week23 {

    /**
     * Q1: Word Ladder
     * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
     * Output: 5
     * Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // Convert the word list into a HashSet for O(1) lookup time.
        Set<String> wordSet = new HashSet<>(wordList);

        // If the endWord is not in the word list, return 0.
        if (!wordSet.contains(endWord)) {
            return 0;
        }

        // BFS initialization: queue for BFS and a visited set to track visited words.
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int level = 1; // We start from the beginning word, so the level is 1.

        while (!queue.isEmpty()) {
            int size = queue.size();

            // Process all words at the current level.
            for (int i = 0; i < size; i++) {
                String currentWord = queue.poll();

                // Try changing each letter of the current word to form a new word.
                for (int j = 0; j < currentWord.length(); j++) {
                    char[] wordArray = currentWord.toCharArray();

                    // Try all possible single letter changes.
                    for (char c = 'a'; c <= 'z'; c++) {
                        wordArray[j] = c;
                        String newWord = new String(wordArray);

                        // If the new word matches the endWord, return the answer.
                        if (newWord.equals(endWord)) {
                            return level + 1;
                        }

                        // If the new word is in the word set and hasn't been visited, add it to the queue.
                        if (wordSet.contains(newWord)) {
                            queue.offer(newWord);
                            wordSet.remove(newWord); // Remove the word from the set to avoid revisiting.
                        }
                    }
                }
            }

            // Increment the level after processing all words at the current level.
            level++;
        }

        // If we exit the loop, there's no valid transformation sequence.
        return 0;
    }

    /**
     * Basic Calculator
     * Given a string s representing a valid expression, implement a basic calculator to evaluate it, and return the result of the evaluation.
     * Input: s = "(1+(4+5+2)-3)+(6+8)"
     * Output: 23
     */
    public int calculate(String s) {
        // Stack to store numbers and results
        Stack<Integer> stack = new Stack<>();  // Stack for numbers
        Stack<Character> ops = new Stack<>();  // Stack for operators
        int result = 0;  // Running result
        int sign = 1;    // 1 for +, -1 for -
        int num = 0;     // Current number being built

        for (char c : s.toCharArray()) {

            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }

            // Handle operators and parentheses
            else if (c == '+' || c == '-') {
                result += sign * num;  // Add previous number with its sign
                sign = (c == '+') ? 1 : -1;  // Set new sign
                num = 0;  // Reset number
            } else if (c == '(') {
                // Push current result and sign to stacks
                stack.push(result);
                ops.push(sign > 0 ? '+' : '-');
                // Reset for new sub-expression
                result = 0;
                sign = 1;
                num = 0;
            } else if (c == ')') {
                // Add last number in parentheses
                result += sign * num;
                // Apply the sign before parentheses and add to previous result
                result = stack.pop() + (ops.pop() == '+' ? 1 : -1) * result;
                num = 0;
                sign = 1;
            }
        }
        // Add any remaining number
        result += sign * num;

        return result;
    }

    public int calculateByStack(String s) {
        // Stack to store numbers and results
        Stack<Integer> stack = new Stack<>();

        // Variable to store the current number and the result
        int currentNumber = 0;
        int result = 0;
        // Variable to keep track of the sign (+ or -)
        int sign = 1;  // 1 means positive, -1 means negative

        // Iterate through the string
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // If the character is a digit, build the current number
            if (Character.isDigit(c)) {
                currentNumber = currentNumber * 10 + (c - '0');
            }
            // If the character is a '+', add the current number to the result
            else if (c == '+') {
                result += sign * currentNumber;
                currentNumber = 0;  // Reset the current number
                sign = 1;  // Next number is positive
            }
            // If the character is a '-', subtract the current number from the result
            else if (c == '-') {
                result += sign * currentNumber;
                currentNumber = 0;  // Reset the current number
                sign = -1;  // Next number is negative
            }
            // If the character is '(', push the result and sign onto the stack
            else if (c == '(') {
                stack.push(result);
                stack.push(sign);
                result = 0;  // Reset result for the new expression
                sign = 1;    // Default sign is positive inside parentheses
            }
            // If the character is ')', pop from the stack and apply the result
            else if (c == ')') {
                result += sign * currentNumber;  // Add the current number
                currentNumber = 0;  // Reset the current number
                result *= stack.pop();  // Multiply by the sign before the '('
                result += stack.pop();  // Add the result before the '('
            }
        }

        // Add the last number to the result (if any)
        result += sign * currentNumber;

        return result;
    }

    /**
     * Maximum Profit in Job Scheduling
     * We have n jobs, where every job is scheduled to be done from startTime[i] to endTime[i], obtaining a profit of profit[i].
     * <p>
     * You're given the startTime, endTime and profit arrays, return the maximum profit you can take such that there are no two jobs in the subset with overlapping time range.
     * <p>
     * Input: startTime = [1,2,3,3], endTime = [3,4,5,6], profit = [50,10,40,70]
     * Output: 120
     * Explanation: The subset chosen is the first and fourth job.
     * Time range [1-3]+[3-6] , we get profit of 120 = 50 + 70.
     */
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][3];

        // Combine start, end, and profit into one array for sorting
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }

        // Sort jobs by end time
        Arrays.sort(jobs, Comparator.comparingInt(a -> a[1]));

        // dp[i] represents max profit up to job i
        int[] dp = new int[n];
        dp[0] = jobs[0][2];

        // For each job, find max profit by either including or excluding it
        for (int i = 1; i < n; i++) {
            int currProfit = jobs[i][2];
            int prevNonOverlap = findLastNonOverlappingJob(jobs, i);

            if (prevNonOverlap != -1) {
                currProfit += dp[prevNonOverlap];
            }

            // Max of including current job or excluding it
            dp[i] = Math.max(currProfit, dp[i - 1]);
        }

        return dp[n - 1];

    }
    //The findLastNonOverlappingJob function uses binary search to efficiently find the last job
    // that ends before the current job starts. This avoids the need for a brute force search.
    private int findLastNonOverlappingJob(int[][] jobs, int index) {
        int left = 0, right = index - 1;
        int result = -1;

        while (left <= right) {
            int mid = (left + right) / 2;
            // If job[mid] ends before the job[index] starts, it's a valid candidate

            if (jobs[mid][1] <= jobs[index][0]) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    /**Merge k Sorted Lists
     * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
     *
     * Merge all the linked-lists into one sorted linked-list and return it.
     * Input: lists = [[1,4,5],[1,3,4],[2,6]]
     * Output: [1,1,2,3,4,4,5,6]
     * Explanation: The linked-lists are:
     * [
     *   1->4->5,
     *   1->3->4,
     *   2->6
     * ]
     * merging them into one sorted list:
     * 1->1->2->3->4->4->5->6
     */
    public ListNode mergeKLists(ListNode[] lists) {

        if(lists == null) return null;
        PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
        for(ListNode node: lists) {
            if(node != null) {
                priorityQueue.offer(node);
            }
        }
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while(!priorityQueue.isEmpty()) {

            ListNode node = priorityQueue.poll();
            // Attach it to the merged list
            current.next = node;
            current = current.next;
            if (node.next != null) {
                priorityQueue.add(node.next);
            }
        }

        return dummy.next;
    }
    public static void main(String[] args) {
        Week23 week23 = new Week23();

        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");

        int result = week23.ladderLength(beginWord, endWord, wordList);
        System.out.println("Q1 ladderLength " + result);

        String[] tests = {
                //  "1 + 1",
                // " 2-1 + 2 ",
                "(1+(4+5+2)-3)+(6+8)"
        };
        for (String test : tests) {
            System.out.println("Q2 BasicCalculator " + test + " = " + week23.calculate(test));
            System.out.println("Q2 BasicCalculator " + test + " = " + week23.calculateByStack(test));
        }

        // Test case
        int[] start = {1, 2, 4, 6};
        int[] end = {3, 5, 7, 8};
        int[] profit = {50, 10, 40, 70};
        int[] start1 = {1, 2, 3, 4, 6};
        int[] end1 = {3, 5, 10, 6, 9};
        int[] profit1 = {20, 20, 100, 70, 60};
        System.out.println("Q3 JobScheduling " + week23.jobScheduling(start, end, profit));
        System.out.println("Q3 JobScheduling " + week23.jobScheduling(start1, end1, profit1));

        ListNode list1 = ListNode.createList(new int[] {1, 4, 5});
        ListNode list2 = ListNode.createList(new int[] {1, 3, 4});
        ListNode list3 = ListNode.createList(new int[] {2, 6});
        ListNode[] lists = {list1, list2, list3};
        ListNode mergeKLists = week23.mergeKLists(lists);
        System.out.println("Q4 mergeKLists ");
        ListNode.printList(mergeKLists);

    }
}
