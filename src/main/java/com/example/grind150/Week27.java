package com.example.grind150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.grind150.ListNode.createList;
import static com.example.grind150.ListNode.printList;

public class Week27 {

    /**
     * Palindrome Pairs
     * A palindrome pair is a pair of integers (i, j) such that:
     * <p>
     * 0 <= i, j < words.length,
     * i != j, and
     * words[i] + words[j] (the concatenation of the two strings) is a palindrome.
     * Input: words = ["abcd","dcba","lls","s","sssll"]
     * Output: [[0,1],[1,0],[3,2],[2,4]]
     * Explanation: The palindromes are ["abcddcba","dcbaabcd","slls","llssssll"]
     */
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<>();
        Map<String, Integer> wordMap = new HashMap<>();
        // Step 1: Store all words and their indices in the map
        for (int i = 0; i < words.length; i++) {
            wordMap.put(words[i], i);
        }

        // Step 2: Check each word and split it into prefix and suffix
        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            // Step 2a: Check if there is a palindrome pair formed by the current word
            for (int j = 0; j <= word.length(); j++) {
                String prefix = word.substring(0, j);
                String suffix = word.substring(j);

                if (isPalindrome(prefix)) {

                    String reverseSuffix = new StringBuilder(suffix).reverse().toString();
                    if (wordMap.containsKey(reverseSuffix) && wordMap.get(reverseSuffix) != i) {
                        result.add(Arrays.asList(wordMap.get(reverseSuffix), i));
                    }

                }
                // Check if the suffix is a palindrome and if the reverse of the prefix exists
                // Avoid duplicate work, we should only do this if j != word.length() (to avoid same pair twice)
                if (j != word.length() && isPalindrome(suffix)) {
                    String reversePrefix = new StringBuilder(prefix).reverse().toString();
                    if (wordMap.containsKey(reversePrefix) && wordMap.get(reversePrefix) != i) {
                        result.add(Arrays.asList(i, wordMap.get(reversePrefix)));
                    }
                }
            }
        }
        return result;

    }

    private boolean isPalindrome(String prefix) {
        int left = 0;
        int right = prefix.length() - 1;
        while (left < right) {
            if (prefix.charAt(left) != prefix.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     * Reverse Nodes in k-Group
     * Input: head = [1,2,3,4,5], k = 2
     * Output: [2,1,4,3,5]
     * 1 -> 2 -> 3 -> 4 -> 5
     * k = 3
     * 3 -> 2 -> 1 -> 4 -> 5
     * Input: 1->2->3->4->5->6, k = 2
     * Output: 2->1->4->3->6->5
     * Input: 1->2->3->4->5, k = 3
     * Output: 3->2->1->4->5
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // If k is 1 or list is empty, no reversal needed
        if (head == null || k == 1) {
            return head;
        }
        int count = 0;
        ListNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        // Step 2: Create a dummy node to simplify the head manipulation
        // Dummy node to handle the head case easily
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (count > k) {

            // Store the start of the next group
            ListNode nextGroupStart = head;
            for (int i = 0; i < k - 1; i++) {
                nextGroupStart = nextGroupStart.next;
            }
            ListNode nextGroup = nextGroupStart.next;

            // Reverse k nodes
            ListNode curr = head;
            ListNode prevNode = nextGroup;
            for (int i = 0; i < k; i++) {
                ListNode nextNode = curr.next;
                curr.next = prevNode;
                prevNode = curr;
                curr = nextNode;
            }

            // Connect with the previous group
            ListNode temp = prev.next;
            prev.next = nextGroupStart;
            prev = temp;
            head = nextGroup;
            count -= k;
        }
        return dummy.next;

    }

    /**
     * Sudoku Solver
     * A sudoku solution must satisfy all of the following rules:
     * <p>
     * Each of the digits 1-9 must occur exactly once in each row.
     * Each of the digits 1-9 must occur exactly once in each column.
     * Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
     * The '.' character indicates empty cells.
     */
    public void solveSudoku(char[][] board) {
        solveSudokuu(board);
    }

    private boolean solveSudokuu(char[][] board) {

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                // if empty row
                if (board[row][col] == '.') {
                    // Try placing digits 1 to 9
                    for (char num = '1'; num <= '9'; num++) {
                        // Check if placing num is valid
                        if (isValidEntry(board, row, col, num)) {
                            board[row][col] = num; // Place num
                            if (solveSudokuu(board)) {
                                return true; // If the rest of the board can be solved, return true
                            }
                            board[row][col] = '.'; // Backtrack if no solution
                        }
                    }
                    return false; // If no valid num could be placed, return false
                }
            }
        }
        return true; // If all cells are filled correctly
    }
    private boolean isValidEntry(char[][] board, int row, int col, char num) {
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num) return false;
            if (board[row][i] == num) return false;
            int boxRow = 3 * (row / 3) + i / 3;  // Calculate the starting row of the 3x3 subgrid
            int boxCol = 3 * (col / 3) + i % 3;  // Calculate the starting column of the 3x3 subgrid
            if (board[boxRow][boxCol] == num) return false;  // Subgrid check
        }
        return true;
    }
    // Function to print the Sudoku board
    public void printBoard(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }


    /**
     * First Missing Positive
     *Given an unsorted integer array nums. Return the smallest positive integer that is not present in nums.
     *
     * You must implement an algorithm that runs in O(n) time and uses O(1) auxiliary space.
     * Input: nums = [1,2,0]
     * Output: 3
     */

    private int firstMissingPositive(int[] nums) {

        int size = nums.length;

        // Iterate over the array elements.
        for (int i = 0; i < size; ++i) {
            // While the current number is in the range [1, size] and it is not in the
            // correct position
            // (Which means nums[i] does not equal to nums[nums[i] - 1])
            while (nums[i] > 0 && nums[i] <= size && nums[i] != nums[nums[i] - 1]) {
                // Swap nums[i] with nums[nums[i] - 1]
                // The goal is to place each number in its corresponding index based on its
                // value.
                swap(nums, i, nums[i] - 1);
            }
        }
        // Now that nums is reorganized, loop through the array
        // to find the first missing positive number.
        for (int i = 0; i < size; ++i) {
            // If the number doesn't match its index (+1 because we are looking for positive
            // numbers),
            // that index (+1) is the first missing positive number.
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        // If no missing number found within [1, size], return size + 1 as the first
        // missing positive number
        return size + 1;
    }
    private void swap(int[] nums, int firstIndex, int secondIndex) {
        int temp = nums[firstIndex];
        nums[firstIndex] = nums[secondIndex];
        nums[secondIndex] = temp;
    }
    public static void main(String[] args) {
        Week27 week27 = new Week27();
        String[] words = {"abcd", "dcba", "lls", "s", "sssll"};
        List<List<Integer>> pairs = week27.palindromePairs(words);
        System.out.println("Q1 palindromePairs " + pairs);

        // Test case 1: 1->2->3->4->5->6, k = 2
        int[] arr1 = {1, 2, 3, 4, 5, 6};
        ListNode list1 = createList(arr1);
        System.out.print("Original List 1: ");
        printList(list1);
        ListNode result1 = week27.reverseKGroup(list1, 2);
        System.out.print("Reversed List 1: ");
        printList(result1); // Output: 2->1->4->3->6->5

        // Test case 2: 1->2->3->4->5, k = 3
        int[] arr2 = {1, 2, 3, 4, 5};
        ListNode list2 = createList(arr2);
        System.out.print("Original List 2: ");
        printList(list2);
        ListNode result2 = week27.reverseKGroup(list2, 3);
        System.out.print("Reversed List 2: ");
        printList(result2); // Output: 3->2->1->4->5


        // Example input: A partially filled Sudoku puzzle
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '8', '.', '.', '.', '3', '9'},
                {'4', '.', '9', '6', '.', '.', '8', '2', '5'},
                {'7', '.', '.', '.', '2', '9', '.', '6', '1'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'9', '.', '.', '5', '7', '.', '.', '.', '6'},
                {'.', '8', '7', '.', '3', '6', '.', '4', '2'}
        };

        week27.solveSudoku(board);

        // Print the solved board
        System.out.println("Q3 Solved Sudoku:" +Arrays.deepToString(board));
        week27.printBoard(board);


        // Test cases
        int[] nums1 = {1, 2, 0};
        System.out.println("Q4 firstMissingPositive "+week27.firstMissingPositive(nums1)); // Output: 3

        int[] nums2 = {3, 4, -1, 1};
        System.out.println("Q4 firstMissingPositive "+week27.firstMissingPositive(nums2)); // Output: 2

        int[] nums3 = {7, 8, 9, 11, 12};
        System.out.println("Q4 firstMissingPositive "+week27.firstMissingPositive(nums3)); // Output: 1
    }

}
