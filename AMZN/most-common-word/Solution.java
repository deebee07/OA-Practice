/*

https://leetcode.com/problems/most-common-word/

Given a paragraph and a list of banned words, return the most frequent word that is not in the list of banned words.  It is guaranteed there is at least one word that isn't banned, and that the answer is unique.

Words in the list of banned words are given in lowercase, and free of punctuation.  Words in the paragraph are not case sensitive.  The answer is in lowercase.

 

Example:

Input: 
paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
banned = ["hit"]
Output: "ball"
Explanation: 
"hit" occurs 3 times, but it is a banned word.
"ball" occurs twice (and no other word does), so it is the most frequent non-banned word in the paragraph. 
Note that words in the paragraph are not case sensitive,
that punctuation is ignored (even if adjacent to words, such as "ball,"), 
and that "hit" isn't the answer even though it occurs more because it is banned.
 

Note:

1 <= paragraph.length <= 1000.
0 <= banned.length <= 100.
1 <= banned[i].length <= 10.
The answer is unique, and written in lowercase (even if its occurrences in paragraph may have uppercase symbols, and even if it is a proper noun.)
paragraph only consists of letters, spaces, or the punctuation symbols !?',;.
There are no hyphens or hyphenated words.
Words only consist of letters, never apostrophes or other punctuation symbols.

 */

public class Solution {
        public String mostCommonWord(String p, String[] banned) {
        Set<String> ban = new HashSet<>(Arrays.asList(banned));
        
        Map<String, Integer> count = new HashMap<>();
        
        
        String[] words = p.replaceAll("\\W+" , " ").toLowerCase().split("\\s+");
        for (String w : words) if (!ban.contains(w)) count.put(w, count.getOrDefault(w, 0) + 1);
        return Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}


//Solution 2: https://leetcode.com/problems/most-common-word/discuss/429450/Java-Runtime%3A-O(N)-4ms-99-or-Memory%3A-O(N)-36mb-96

//Solution 3: https://leetcode.com/problems/most-common-word/discuss/790507/Java-easy-solution-without-regex-~95-time-and-~93-storage

// Java 8

// public String mostCommonWord(String paragraph, String[] banned) {

// 	paragraph = paragraph.replaceAll("[\\!\\?',;\\.]", " ").toLowerCase();

// 	Map<String, Integer> counts = new HashMap<>();
// 	Set<String> bans = Arrays.stream(banned).collect(Collectors.toSet());

// 	Arrays.stream(paragraph.split("\\s+"))
// 		.filter(s -> !bans.contains(s))
// 		.forEach(s -> counts.put(s, counts.getOrDefault(s, 0) + 1));

// 	return counts.keySet().stream()
// 		.reduce((a,b) -> counts.get(a) > counts.get(b) ? a : b)
// 		.get();
// }