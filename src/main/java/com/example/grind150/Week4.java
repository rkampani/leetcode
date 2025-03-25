package com.example.grind150;

import java.util.HashSet;
import java.util.Set;

import static com.example.grind150.Utils.log;

public class Week4 {

    /**
     * Q1: Same Tree
     * Given the roots of two binary trees p and q, write a function to check if they are the same or not.
     * <p>
     * Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (q == null && p == null) return true;
        if (p == null || q == null || p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * Q2: NumberOfBits
     * Given a positive integer n, write a function that returns the number of set
     * bits in its binary representation (also known as the Hamming weight).
     * For n = 4, which in binary is 100:
     * <p>
     * n = 4 (binary 100): The rightmost set bit is 1 (position 0). Perform n = n & (n - 1):
     * n = 4 & 3 = 100 & 011 = 000 & count =1
     */
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {

            n = n & (n - 1);
            count++;
        }
        return count;
    }

    /**
     * Q3:Longest Common Prefix
     * Write a function to find the longest common prefix string amongst an array of strings.*
     * Input: strs = ["flower","flow","flight"]
     * Output: "fl"
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null) return "";

        // Start by assuming the entire first string is the common prefix.
        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            // Keep reducing the prefix length until it matches the start of strs[i].
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        return prefix;
    }

    /**
     * Q4 Single Number
     * Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.
     */
    public int singleNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                return num;
            }
        }
        return 0;
    }

    /**
     * Since XOR of a number with itself is 0 and with 0 is the number itself,
     * this will cancel out all pairs leaving the single number alone
     * eg: 2 ^ 2 = 0
     * 2 ^ 0 = 2
     */
    public int singleNumberUsingBitWise(int[] nums) {
        int answer = 0;
        for (int num : nums) {

            answer = num ^ answer;
        }
        return answer;
    }

    /**
     * Q5: Palindrome Linked List
     * Given the head of a singly linked list, return true if it is a palindrome or false otherwise.
     * Input: head = [1,2,2,1]
     * Output: true
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null) return false;

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode secondHalf = reverseListNode(slow);
        ListNode firstHalf = head;

        return isPalindrome(firstHalf, secondHalf);

    }

    private boolean isPalindrome(ListNode firstHalf, ListNode secondHalf) {

        while (firstHalf != null && secondHalf != null) {
            if (firstHalf.val != secondHalf.val) {
                return false;
            }
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }
        return true;
    }

    private ListNode reverseListNode(ListNode secondHalf) {
        ListNode current = secondHalf;
        ListNode prev = null;
        while (current != null) {
            ListNode temp = current.next;
            current.next = prev;
            prev = current;
            current = temp;
        }
        return prev;
    }


    /**
     * Q6: MoveZeros
     */
    public int[] moveZeroes(int[] nums) {
        if (nums == null) return nums;
        int index = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[index] = num;
                index++;
            }
        }
        while (index < nums.length) {
            nums[index] = 0;
            index++;
        }
        return nums;
    }

    /**
     * Q7:Symmetric Tree
     * Given the root of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).
     * Input: root = [1,2,2,3,4,4,3]
     * Output: true
     * Input: root = [1,2,2,null,3,null,3]
     * Output: false
     */
    public boolean isSymmetric(TreeNode root) {
        return isSymmetric(root, root);
    }

    private boolean isSymmetric(TreeNode root, TreeNode root1) {

        if (root == null && root1 == null) return true;
        if (root == null || root1 == null) return false;
        if (root.val != root1.val) {
            return false;
        }
        return isSymmetric(root.left, root1.right) && isSymmetric(root.right, root1.left);
    }


    /**
     * Q8: MissingNumber
     * Given an array nums containing n distinct numbers in the range [0, n], return the only number in
     * the range that is missing from the array.
     * XOR Basics:
     * <p>
     * a ^ a = 0 — XOR of a number with itself results in 0.
     * a ^ 0 = a — XOR of a number with 0 results in the number itself.
     * XOR is commutative and associative, meaning the order of operations doesn't matter.
     */
    public int missingNumber(int[] nums) {
        int n = nums.length;    // Length of the array
        int answer = n;         // Initially assume the missing number is n
        for (int i = 0; i < n; i++) {
            answer ^= i ^ nums[i];  // XOR current index and the number at that index
        }
        return answer;   // Return the result, which is the missing number
    }

    /**
     * Q9: PalindromeNumber
     * Given an integer x, return true if x is a palindrome, and false otherwise.
     */
    public boolean isPalindromeNumberUsingString(int x) {
        String str = String.valueOf(x);
        int start = 0;
        int end = str.length() - 1;
        while (start < end) {
            if (str.charAt(start) != str.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public static boolean isPalindromeNumber(int x) {
        // Handle negative numbers
        if (x < 0) {
            return false;
        }

        // Handle numbers that end in 0 but are not 0
        if (x % 10 == 0 && x != 0) {
            return false;
        }

        int reversedHalf = 0;
        while (x > reversedHalf) {
            // Reverse the last digit of x and add it to reversedHalf
            reversedHalf = reversedHalf * 10 + x % 10;
            x /= 10; // Remove the last digit from x
        }

        // If x is equal to reversedHalf or if x is equal to reversedHalf/10 for odd-length numbers
        return x == reversedHalf || x == reversedHalf / 10;
    }

    /**
     * Q10: Convert Sorted Array to Binary Search Tree
     * Given an integer array nums where the elements are sorted in ascending order, convert it to a height-balanced binary search tree.
     * balanced Binary Search Tree (BST), we can take advantage of the fact that a balanced
     * BST has the property that the root is always the middle element of the array
     **/
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBSTHelper(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBSTHelper(int[] nums, int left, int right) {
        // Base case: when the left index exceeds the right index, return null
        if (left > right) {
            return null;
        }
        // Find the middle element of the array
        int mid = left + (right - left) / 2;
        // Create the root node with the middle element
        TreeNode root = new TreeNode(nums[mid]);

        // Recursively build the left and right subtrees
        root.left = sortedArrayToBSTHelper(nums, left, mid - 1);
        root.right = sortedArrayToBSTHelper(nums, mid + 1, right);

        return root;
    }

    public static void main(String[] args) {

        Week4 week4 = new Week4();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        System.out.println("Q1: isSameTree " + week4.isSameTree(root, root));

        System.out.println("Q2: hammingWeight " + week4.hammingWeight(4));
        System.out.println("Q2: hammingWeight " + week4.hammingWeight(128));
        System.out.println("Q2: hammingWeight " + week4.hammingWeight(15));
        System.out.println("Q2: hammingWeight " + week4.hammingWeight(2147483645));

        System.out.println("Q3: longestCommonPrefix " + week4.longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
        System.out.println("Q3: longestCommonPrefix " + week4.longestCommonPrefix(new String[]{"dog", "racecar", "car"}));
        System.out.println("Q3: longestCommonPrefix " + week4.longestCommonPrefix(new String[]{"", "apple", "apple"}));
        System.out.println("Q3: longestCommonPrefix " + week4.longestCommonPrefix(new String[]{"apple", "apple", "apple"}));

        System.out.println("Q4: singleNumber " + week4.singleNumber(new int[]{1, 2, 2, 3}));
        System.out.println("Q4: singleNumber " + week4.singleNumber(new int[]{1, 2, 3}));
        System.out.println("Q4: singleNumber " + week4.singleNumber(new int[]{2, 3, 5, 3, 2}));
        System.out.println("Q4: singleNumberUsingBitWise " + week4.singleNumberUsingBitWise(new int[]{1, 2, 2, 3}));
        System.out.println("Q4: singleNumberUsingBitWise " + week4.singleNumberUsingBitWise(new int[]{1, 2, 3}));
        System.out.println("Q4: singleNumberUsingBitWise " + week4.singleNumberUsingBitWise(new int[]{2, 3, 5, 3, 2}));

        //[1,2,2,1]
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(2);
        listNode.next.next.next = new ListNode(1);
        //[1,2]
        ListNode listNode1 = new ListNode(1);
        listNode1.next = new ListNode(2);
        System.out.println("Q5: isPalindromeLinkedNode " + week4.isPalindrome(listNode));
        System.out.println("Q5: isPalindromeLinkedNode " + week4.isPalindrome(listNode1));


        log("Q6: moveZeros", week4.moveZeroes(new int[]{0, 1, 0, 3, 12}));
        log("Q6: moveZeros", week4.moveZeroes(new int[]{0}));

        // Example tree 1: [1,2,2,3,4,4,3]
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(2);
        root1.left.left = new TreeNode(3);
        root1.left.right = new TreeNode(4);
        root1.right.left = new TreeNode(4);
        root1.right.right = new TreeNode(3);

        // Example tree 2: [1,2,2,null,3,null,3]
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(2);
        root2.left.right = new TreeNode(3);
        root2.right.right = new TreeNode(3);
        System.out.println("Q7 isSymmetric " + week4.isSymmetric(root1));
        System.out.println("Q7 isSymmetric " + week4.isSymmetric(root2));

        System.out.println("Q8 missingNumber " + week4.missingNumber(new int[]{3, 7, 1, 2, 8, 4, 5}));
        System.out.println("Q8 missingNumber " + week4.missingNumber(new int[]{1}));
        System.out.println("Q8 missingNumber " + week4.missingNumber(new int[]{3, 0, 1}));
        System.out.println("Q8 missingNumber " + week4.missingNumber(new int[]{9, 6, 4, 2, 3, 5, 7, 0, 1}));

        System.out.println("Q9 isPalindromeNumberUsingString " + week4.isPalindromeNumberUsingString(121));
        System.out.println("Q9 isPalindromeNumberUsingString " + week4.isPalindromeNumberUsingString(-121));
        System.out.println("Q9 isPalindromeNumberUsingString " + week4.isPalindromeNumberUsingString(-10));
        System.out.println("Q9 isPalindromeNumber " + week4.isPalindromeNumber(121));
        System.out.println("Q9 isPalindromeNumber " + week4.isPalindromeNumber(-121));
        System.out.println("Q9 isPalindromeNumber " + week4.isPalindromeNumber(-10));


        System.out.println("Q10: sortedArrayToBST ");
        TreeNode.inorderTraversal(week4.sortedArrayToBST(new int[]{-10, -3, 0, 5, 9}));
        System.out.println("Q10: sortedArrayToBST ");
        TreeNode.inorderTraversal(week4.sortedArrayToBST(new int[]{1, 3}));
        System.out.println("Q10: sortedArrayToBST ");
        TreeNode.inorderTraversal(week4.sortedArrayToBST(new int[]{1, 3, 6, 8}));
    }


}
