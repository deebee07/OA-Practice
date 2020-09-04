/*
Given a string s and an int k, return all unique substrings of s of size k with k distinct characters.

Example 1:

Input: s = "abcabc", k = 3
Output: ["abc", "bca", "cab"]
Example 2:

Input: s = "abacab", k = 3
Output: ["bac", "cab"]
Example 3:

Input: s = "awaglknagawunagwkwagl", k = 4
Output: ["wagl", "aglk", "glkn", "lkna", "knag", "gawu", "awun", "wuna", "unag", "nagw", "agwk", "kwag"]
Explanation: 
Substrings in order are: "wagl", "aglk", "glkn", "lkna", "knag", "gawu", "awun", "wuna", "unag", "nagw", "agwk", "kwag", "wagl" 
"wagl" is repeated twice, but is included in the output once.
Constraints:

The input string consists of only lowercase English letters [a-z]
0 ≤ k ≤ 26
https://leetcode.com/playground/LRBxfw5W

*/

// https://leetcode.com/discuss/interview-question/344976/Amazon-or-OA-2019-or-Substrings-of-size-K-with-K-distinct-chars
public class Main {
    
    public static List<String> kSubstring(String s, int k) {
        Set<Character> window = new HashSet<>();
        Set<String> result = new HashSet<>();
        for (int start = 0, end = 0; end < s.length(); end++) {
            for (; window.contains(s.charAt(end)); start++) {
                window.remove(s.charAt(start)); // decrease windowLength if we come across character already in window. (move start ahead)
            }

            window.add(s.charAt(end));

            if (window.size() == k) {
                result.add(s.substring(start, end + 1));
                window.remove(s.charAt(start++));
            }
        }
        return new ArrayList<>(result);
    }
    
    public static void main(String[] args) {
        System.out.println(kSubstring("awaglknagawunagwkwagl", 4));
    }
}

// Solution 2

    // public static List<String> kSubstring(String s, int k) {
    //     // result values should be unique
    //     Set<String> result = new HashSet<>();

    //     if (s == null || s.length() == 0) {
    //         return new ArrayList<>();
    //     }

    //     Map<Character, Integer> map = new HashMap<>();

    //     for (int start = 0 - k + 1, end = 0; end < s.length(); end++, start++) {
    //         map.put(s.charAt(end), map.getOrDefault(s.charAt(end), 0) + 1);

    //         if (map.size() == k && end - start == (k - 1)) {
    //             result.add(s.substring(start, end + 1));
    //         }

    //         if (start >= 0) {
    //             map.put(s.charAt(start), map.get(s.charAt(start)) - 1);
    //             if (map.get(s.charAt(start)) == 0) {
    //                 map.remove(s.charAt(start));
    //             }
    //         }
    //     }

    //     return new ArrayList<>(result);
    // }