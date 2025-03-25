package com.example.grind150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Week9 {

    /**
     * Q2.  Accounts Merge
     * Given a list of accounts where each element accounts[i] is a list of strings, where the
     * first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.
     * Input: accounts = [["John","johnsmith@mail.com","john_newyork@mail.com"],["John","johnsmith@mail.com","john00@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
     * Output: [["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
     */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        UnionFind unionFind = new UnionFind();
        Map<String, String> emailToName = new HashMap<>();
        // Step 1: Union emails within the same account
        for (List<String> account : accounts) {
            String name = account.get(0);  // Account name
            String firstEmail = account.get(1);  // First email in the account
            emailToName.put(firstEmail, name);  // Store the name for each email

            // Union all emails in this account
            for (int i = 2; i < account.size(); i++) {
                unionFind.union(firstEmail, account.get(i));
                emailToName.put(account.get(i), name);  // Associate emails with the account name
            }
        }

        // Step 2: Group emails by their root
        Map<String, List<String>> groups = new HashMap<>();
        for (String email : emailToName.keySet()) {
            String root = unionFind.find(email);
            groups.putIfAbsent(root, new ArrayList<>());
            groups.get(root).add(email);
        }

        // Step 3: Collect and return the results
        List<List<String>> result = new ArrayList<>();
        for (List<String> emails : groups.values()) {
            Collections.sort(emails);  // Sort emails in lexicographical order
            List<String> account = new ArrayList<>();
            account.add(emailToName.get(emails.get(0)));  // Add the name from any email in the group
            account.addAll(emails);  // Add the sorted emails
            result.add(account);
        }

        return result;
    }

    /**
     * Q3.Sort Colors
     * Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.
     * We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.
     * <p>
     * You must solve this problem without using the library's sort function.
     * <p>
     * Example 1:
     * <p>
     * Input: nums = [2,0,2,1,1,0]
     * Output: [0,0,1,1,2,2]
     * Example 2:
     * <p>
     * Input: nums = [2,0,1]
     * Output: [0,1,2]
     */
    public void sortColors(int[] nums) {
        if (nums == null) return;
        int low = 0;
        int high = nums.length - 1;
        int mid = 0;
        while (mid <= high) {

            if (nums[mid] == 0) {
                swap(nums, low, mid);
                mid++;
                low++;
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                swap(nums, mid, high);
                high--;
            }

        }
        System.out.println("Q3 sortColors " + Arrays.toString(nums));
        return;
    }

    private void swap(int[] nums, int low, int mid) {
        int temp = nums[low];
        nums[low] = nums[mid];
        nums[mid] = temp;
    }

    /**
     * Q4.Word Break
     * iven a string s and a dictionary of strings wordDict, return true if s can be segmented into a space-separated sequence of one or more dictionary words.
     * <p>
     * Note that the same word in the dictionary may be reused multiple times in the segmentation.
     * Example 1:
     * <p>
     * Input: s = "leetcode", wordDict = ["leet","code"]
     * Output: true
     * Explanation: Return true because "leetcode" can be segmented as "leet code".
     */

    public boolean wordBreak(String s, List<String> wordDict) {
        if (wordDict.isEmpty()) return false;
        if (s == null) return false;

        int n = s.length();
        Set<String> wordSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                // Check if substring s[j..i-1] is in the dictionary a
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }

    /**
     * Q5: Partition Equal Subset Sum
     * Given an integer array nums, return true if you can partition the array into two subsets such that the sum of the elements in both subsets is equal or false otherwise.
     *
     *Input: nums = [1,5,11,5]
     * Output: true
     * Explanation: The array can be partitioned as [1, 5, 5] and [11].
     * Input: nums = [1,2,3,5]
     * Output: false
     * Explanation: The array cannot be partitioned into equal sum subsets.
     */
    public boolean canPartition(int[] nums) {
        if(nums == null) return false;
        int totalSum =0;
        for(int num: nums) {
            totalSum+=num;
        }
        if (totalSum % 2 != 0) {
            return false;
        }
        int target = totalSum / 2;
        // DP array to check if it's possible to get the target sum
        boolean[] dp = new boolean[target + 1];
        dp[0] = true; // Base case: a sum of 0 is always possible

        // Iterate over each number in the array
        for (int num : nums) {
            // Update dp array in reverse order to avoid overwriting values of dp[i-1]
            for (int i = target; i >= num; i--) {
                dp[i] = dp[i] || dp[i - num];
            }
        }

        return dp[target];

    }

    /**
     * Q6: String to Integer (atoi)
     * given a string representing an integer, and you need to convert it to the integer value.
     * The problem is similar to the standard function atoi (ASCII to Integer) in C, which converts a string to an integer.
     * The function should ignore any leading whitespace.
     * It should handle optional signs: '+' or '-'.
     * The function should stop converting when it encounters a non-digit character.
     * The result should be clamped to the 32-bit signed integer range: [-2^31, 2^31 - 1] ([-2147483648, 2147483647]).
     * If the result is outside this range, return the appropriate clamped value.
     * If no valid conversion can be performed, return 0.
     */
    public int myAtoi(String str) {
        // Step 1: Remove leading spaces
        str = str.trim();

        // Step 2: Handle empty string
        if (str.isEmpty()) {
            return 0;
        }
        // Step 3: Initialize variables
        int sign = 1; // 1 for positive, -1 for negative
        int result = 0;
        int i = 0;
        // Step 4: Check for optional sign
        if (str.charAt(i) == '+' || str.charAt(i) == '-') {
            sign = (str.charAt(i) == '-') ? -1 : 1;
            i++;  // Move to the next character
        }
        // Step 5: Convert the digits to integer
        while (i < str.length()) {
            char ch = str.charAt(i);
            if (ch < '0' || ch > '9') {
                break;  // Stop if we encounter a non-digit character
            }

            // Convert the character to its integer value
            int digit = ch - '0';

            // Check for overflow before updating the result
            if (result > (Integer.MAX_VALUE - digit) / 10) {
                return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            result = result * 10 + digit;
            i++;
        }

        // Step 6: Apply the sign
        return result * sign;

    }
    public static void main(String[] args) {
        Week9 week9 = new Week9();

        TimeMap timeMap = new TimeMap();
        timeMap.set("foo", "bar", 1);
        System.out.println("Q1 timeMap " + timeMap.get("foo", 1));
        System.out.println("Q1 timeMap " + timeMap.get("foo", 3));
        timeMap.set("foo", "bar2", 4);
        System.out.println("Q1 timeMap " + timeMap.get("foo", 4));
        System.out.println("Q1 timeMap " + timeMap.get("foo", 5));

        List<List<String>> accounts = new ArrayList<>();
        accounts.add(Arrays.asList("John", "john1@mail.com", "john2@mail.com"));
        accounts.add(Arrays.asList("John", "john2@mail.com", "john3@mail.com"));
        accounts.add(Arrays.asList("Mary", "mary@mail.com"));
        accounts.add(Arrays.asList("John", "john4@mail.com"));

        List<List<String>> accounts2 = new ArrayList<>();
        accounts2.add(Arrays.asList("Gabe", "Gabe0@m.co", "Gabe3@m.co", "Gabe1@m.co"));
        accounts2.add(Arrays.asList("Kevin", "Kevin3@m.co", "Kevin5@m.co", "Kevin0@m.co"));
        accounts2.add(Arrays.asList("Ethan", "Ethan5@m.co", "Ethan4@m.co", "Ethan0@m.co"));
        accounts2.add(Arrays.asList("Hanzo", "Hanzo3@m.co", "Hanzo1@m.co", "Hanzo0@m.co"));
        accounts2.add(Arrays.asList("Fern", "Fern5@m.co", "Fern1@m.co", "Fern0@m.co"));

        System.out.println("Q2 accountsMerge " + week9.accountsMerge(accounts));
        System.out.println("Q2 accountsMerge " + week9.accountsMerge(accounts2));

        week9.sortColors(new int[]{2, 0, 2, 1, 1, 0});
        week9.sortColors(new int[]{2, 0, 1});

        System.out.println("Q4 wordBreak "+week9.wordBreak("leetcode", Arrays.asList("leet","code")) );
        System.out.println("Q4 wordBreak "+week9.wordBreak("applepenapple", Arrays.asList("apple","pen")) );
        System.out.println("Q4 wordBreak "+week9.wordBreak("catsandog", Arrays.asList("cats","dog","sand","and","cat")) );

        int[] nums1 = {1, 5, 11, 5};
        System.out.println("Q5 canPartition "+week9.canPartition(nums1));  // Output: true
        int[] nums2 = {1, 2, 3, 5};
        System.out.println("Q5 canPartition "+week9.canPartition(nums2));

        System.out.println("Q6 myAtoi "+week9.myAtoi("42"));  // Output: 42
        System.out.println("Q6 myAtoi "+week9.myAtoi("   -42"));  // Output: -42
        System.out.println("Q6 myAtoi "+week9.myAtoi("4193 with words"));  // Output: 4193
        System.out.println("Q6 myAtoi "+week9.myAtoi("words and 987"));  // Output: 0
        System.out.println("Q6 myAtoi "+week9.myAtoi("-91283472332"));
    }
}
