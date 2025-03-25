package com.example.grind150;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static com.example.grind150.ListNode.createList;
import static com.example.grind150.ListNode.printList;
import static com.example.grind150.TreeNode.createTree;
import static com.example.grind150.TreeNode.insertTreeNode;
import static com.example.grind150.TreeNode.printPreOrder;
import static com.example.grind150.TreeNode.printResult;
import static com.example.grind150.Utils.log;
import static com.example.grind150.Utils.long2DArray;

public class Week1 {

    /**
     * Q1: Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
     * You may assume that each input would have exactly one solution, and you may not use the same element twice.
     */
    public int[] twoSum(int[] nums, int target) {

        if (nums == null) return null;
        Map<Integer, Integer> integerIndexMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (integerIndexMap.containsKey(complement)) {
                return new int[]{i, integerIndexMap.get(complement)};
            }
            integerIndexMap.put(nums[i], i);
        }
        return null;
    }

    /**
     * Q2: Valid Parentheses
     * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
     * <p>
     * An input string is valid if:
     * <p>
     * Open brackets must be closed by the same type of brackets.
     * Open brackets must be closed in the correct order.
     * Every close bracket has a corresponding open bracket of the same type.
     */
    public boolean isValidParenthesis(String s) {
        if (s == null || s.isEmpty()) return false;
        Stack<Character> bracketStack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '{' || c == '(' || c == '[') {
                bracketStack.push(c);
            } else {
                if (bracketStack.isEmpty() || !isMatch(bracketStack.pop(), c)) {
                    return false;
                }
            }
        }
        return bracketStack.isEmpty();
    }

    private boolean isMatch(Character pop, char c) {
        return ((pop == '(' && c == ')') || (pop == '{' && c == '}') || (pop == '[' && c == ']'));
    }

    /**
     * Q3: Merge Two Sorted Lists
     * You are given the heads of two sorted linked lists list1 and list2.
     * <p>
     * Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.
     * <p>
     * Return the head of the merged linked list.
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        if (list1 == null && list2 == null) return null;

        ListNode dummy = new ListNode();
        ListNode current = dummy;

        while (list1 != null && list2 != null) {

            if (list1.val < list2.val) {
                current.next = new ListNode(list1.val);
                list1 = list1.next;
            } else {
                current.next = new ListNode(list2.val);
                list2 = list2.next;
            }
            current = current.next;
        }
        current.next = (list1 == null) ? list2 : list1;

        return dummy.next;
    }

    /**
     * Q4: Best Time to Buy and Sell Stock
     * You are given an array prices where prices[i] is the price of a given stock on the ith day.
     * <p>
     * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
     * <p>
     * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0
     */
    public int maxProfit(int[] prices) {
        if (prices == null) return 0;
        int maxProfit = Integer.MIN_VALUE;
        int minPrice = prices[0];
        for (int price : prices) {
            maxProfit = Math.max(maxProfit, price - minPrice);
            minPrice = Math.min(minPrice, price);
        }
        return maxProfit;
    }

    /**
     * Q5: Valid Palindrome
     * A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and
     * removing all non-alphanumeric characters, it reads the same forward and backward. Alphanumeric characters include letters and numbers.
     * <p>
     * Given a string s, return true if it is a palindrome, or false otherwise.
     * example: s = "A man, a plan, a canal: Panama"
     */

    public boolean isPalindrome(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        int left = 0;
        int right = s.length() - 1;
        while (left <= right) {
            char currFirst = s.charAt(left);
            char currLast = s.charAt(right);
            if (!Character.isLetterOrDigit(currFirst)) {
                left++;
            } else if (!Character.isLetterOrDigit(currLast)) {
                right--;
            } else {
                if (s.toLowerCase().charAt(left) != s.toLowerCase().charAt(right)) {
                    return false;
                }
                left++;
                right--;
            }

        }
        return true;
    }

    /**
     * Q6: Invert Binary Tree
     */
    public TreeNode invertTree(TreeNode root) {

        if (root == null) return null;

        if (root != null) {
            TreeNode left = invertTree(root.left);
            TreeNode right = invertTree(root.right);
            root.left = right;
            root.right = left;
        }
        return root;
    }

    /**
     * Q7: Valid Anagram
     * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
     */
    public boolean isAnagram(String s, String t) {
        if (s == null && t == null) return true;
        if (s == null || t == null) return false;
        if (s.length() != t.length()) return false;
        char[] letters = new char[26];
        for (char st : s.toCharArray()) {
            letters[st - 'a']++;
        }
        for (char st : t.toCharArray()) {
            letters[st - 'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (letters[i] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Q8: Binary Search
     * Given an array of integers nums which is sorted in ascending order, and an integer target,
     * write a function to search target in nums. If target exists, then return its index. Otherwise, return -1.
     * <p>
     * You must write an algorithm with O(log n) runtime complexity.
     */
    public int search(int[] nums, int target) {
        if (nums == null) return -1;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2; //to avoid overflow
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    /**
     * Q9: Flood Fill
     * You are given an image represented by an m x n grid of integers image, where image[i][j]
     * represents the pixel value of the image. You are also given three integers sr, sc, and color.
     * Your task is to perform a flood fill on the image starting from the pixel image[sr][sc].
     * <p>
     * To perform a flood fill:
     * <p>
     * Begin with the starting pixel and change its color to color.
     * Perform the same process for each pixel that is directly adjacent (pixels that share a side with the original pixel,
     * either horizontally or vertically) and shares the same color as the starting pixel.
     * Keep repeating this process by checking neighboring pixels of the updated pixels and modifying their color if it matches
     * the original color of the starting pixel.
     * The process stops when there are no more adjacent pixels of the original color to update.
     * Return the modified image after performing the flood fill.
     */
    public int[][] floodFill(int[][] image, int startRow, int startColumn, int color) {
        int originalColor = image[startColumn][startColumn];
        if (originalColor == color) {
            return image;
        }
        dfsFloodFill(image, startRow, startColumn, color, originalColor);
        return image;
    }

    private void dfsFloodFill(int[][] image, int row, int col, int color, int originalColor) {
        // check boundary and if the color is already matched with new color
        if (row >= image.length || row < 0 || col < 0 || col >= image[0].length || image[row][col] != originalColor) {
            return;
        }
        int[] directions = {-1, 0, 1, 0, -1};
        image[row][col] = color;
        for (int k = 0; k < 4; k++) {
            int newRow = row + directions[k];
            int newCol = col + directions[k + 1];
            dfsFloodFill(image, newRow, newCol, color, originalColor);
        }
    }


    /**
     * Q10:  Lowest Common Ancestor of a Binary Search Tree
     * Given a binary search tree (BST), find the lowest common ancestor (LCA) node of two given nodes in the BST.
     *
     * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest
     * node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        while (root!= null){
            if(p.val < root.val && q.val < root.val) {
                root = root.left;
            } else if(p.val > root.val && q.val > root.val) {
                root = root.right;
            } else {
                return root;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        Week1 week1 = new Week1();

        log("Q1: Two Sum: ", week1.twoSum(new int[]{2, 7, 11, 15}, 9));
        log("Q1: Two Sum: ", week1.twoSum(new int[]{3, 2, 4}, 6));
        log("Q1: Two Sum: ", week1.twoSum(new int[]{3, 3}, 6));

        System.out.println("Q2: isValidParenthesis: " + week1.isValidParenthesis("()"));
        System.out.println("Q2: isValidParenthesis: " + week1.isValidParenthesis("()[]{}"));
        System.out.println("Q2: isValidParenthesis: " + week1.isValidParenthesis("(]"));

        int[] arr1 = {1, 2, 4};
        int[] arr2 = {1, 3, 4};

        int[] arr3 = {1};
        int[] arr4 = {1, 3, 4};

        System.out.println("Q3: Merged List:");
        printList(week1.mergeTwoLists(createList(arr1), createList(arr2)));
        System.out.println("Q3: Merged List:");
        printList(week1.mergeTwoLists(createList(arr3), createList(arr4)));

        System.out.println("Q4: maxProfit: " + week1.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        System.out.println("Q4: maxProfit: " + week1.maxProfit(new int[]{7, 6, 4, 3, 1}));

        System.out.println("Q5: isPalindrome: " + week1.isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println("Q5: isPalindrome: " + week1.isPalindrome("race a car"));
        System.out.println("Q5: isPalindrome: " + week1.isPalindrome(" "));

        Integer[] treeArray = {4, 2, 7, 1, 3, 6, 9};

        TreeNode root = createTree(treeArray, 0);
        root = week1.invertTree(root);
        System.out.println("Q6: Inverted Tree (In-order):");
        printPreOrder(root);
        System.out.println();

        treeArray = new Integer[]{2, 1, 3};
        root = createTree(treeArray, 0);
        root = week1.invertTree(root);
        System.out.println("Q6: Inverted Tree (In-order):");
        printPreOrder(root);
        System.out.println();


        System.out.println("Q7: isAnagram: " + week1.isAnagram("anagram", "nagaram"));
        System.out.println("Q7: isAnagram: " + week1.isAnagram("rat", "cat"));
        System.out.println("Q7: isAnagram: " + week1.isAnagram("rata", "cat"));
        System.out.println("Q7: isAnagram: " + week1.isAnagram("", ""));


        System.out.println("Q8: BinarySearch: " + week1.search(new int[]{7, 1, 5, 3, 6, 4}, 6));
        System.out.println("Q8: BinarySearch: " + week1.search(new int[]{-1, 0, 3, 5, 9, 12}, 9));
        System.out.println("Q8: BinarySearch: " + week1.search(new int[]{-1, 0, 3, 5, 9, 12}, 2));


        int[][] image = {
                {1, 1, 1},
                {1, 1, 0},
                {1, 0, 1}
        };
        long2DArray("Q9: floodfill: Original Array", image);
        long2DArray("Q9: floodFill: ", week1.floodFill(image, 1, 1, 2));


        TreeNode lcaRoot = null;
        lcaRoot = insertTreeNode(lcaRoot, 6);
        insertTreeNode(lcaRoot, 2);
        insertTreeNode(lcaRoot, 8);
        insertTreeNode(lcaRoot, 0);
        insertTreeNode(lcaRoot, 4);
        insertTreeNode(lcaRoot, 3);
        insertTreeNode(lcaRoot, 5);
        TreeNode p = new TreeNode(2);
        TreeNode q = new TreeNode(8);
        System.out.print("Q10 lowestCommonAncestor: ");
        printResult(week1.lowestCommonAncestor(root, p, q));
    }


}
