/*
https://leetcode.com/discuss/interview-question/542597/

Given a list of reviews, a list of keywords and an integer k. Find the most popular k keywords in order of most to least frequently mentioned.

The comparison of strings is case-insensitive.
Multiple occurances of a keyword in a review should be considred as a single mention.
If keywords are mentioned an equal number of times in reviews, sort alphabetically.

Example 1:

Input:
k = 2
keywords = ["anacell", "cetracular", "betacellular"]
reviews = [
  "Anacell provides the best services in the city",
  "betacellular has awesome services",
  "Best services provided by anacell, everyone should use anacell",
]

Output:
["anacell", "betacellular"]

Explanation:
"anacell" is occuring in 2 different reviews and "betacellular" is only occuring in 1 review.
Example 2:

Input:
k = 2
keywords = ["anacell", "betacellular", "cetracular", "deltacellular", "eurocell"]
reviews = [
  "I love anacell Best services; Best services provided by anacell",
  "betacellular has great services",
  "deltacellular provides much better services than betacellular",
  "cetracular is worse than anacell",
  "Betacellular is better than deltacellular.",
]

Output:
["betacellular", "anacell"]

Explanation:
"betacellular" is occuring in 3 different reviews. "anacell" and "deltacellular" are occuring in 2 reviews, but "anacell" is lexicographically smaller.


*/
// Solution  1: 
private static String[] solve(String[] keywords, String[] reviews, int k) {
	
	if(keywords==null || keywords.length==0 || reviews == null || reviews.length==0 || k > keywords.length) return null;
	HashMap<String,Integer> map = new HashMap<String, Integer>();
	
	for(String keyword : keywords) {
		map.put(keyword, 0);
	}
	
	for(String review : reviews) {
		String[] reviewWords = review.split(" ");
		for(String eachWord : reviewWords) {
			if(map.containsKey(eachWord)) {
				map.put(eachWord, map.getOrDefault(eachWord, 0)+1);
			}
		}
	}
	
	List<String> keys = new ArrayList<>(map.keySet());
	Collections.sort(keys, (a,b) -> (map.get(a)).equals(map.get(b)) ? a.compareTo(b) : map.get(b) - map.get(a)) ;
	
	return keys.subList(0, k).toArray(new String[0]);
	
}

// Solution  2: 
public class Solution {
    private static List<String> solve(int k, String[] keywords, String[] reviews) {
        List<String> res = new ArrayList<>();
        Set<String> set = new HashSet<>(Arrays.asList(keywords));
        Map<String, Integer> map = new HashMap<>();
        for(String r : reviews) {
            String[] strs = r.split("\\W");
            Set<String> added = new HashSet<>();
            for(String s : strs) {
                s = s.toLowerCase();
                if(set.contains(s) && !added.contains(s)) {
                    map.put(s, map.getOrDefault(s, 0) + 1);
                    added.add(s);
                }
            }
        }
        Queue<Map.Entry<String, Integer>> maxHeap = new PriorityQueue<>((a, b)->a.getValue() == b.getValue() ? a.getKey().compareTo(b.getKey()) : b.getValue() - a.getValue());
        maxHeap.addAll(map.entrySet());
        while(!maxHeap.isEmpty() && k-- > 0) {
            res.add(maxHeap.poll().getKey());
        }
        return res;
    }
}

// Solution  3: 
public static String[] topKfreqWords(String[] words, String[] freqWords, int K) {
	Map<String, Integer> freqCount = new HashMap<>();
	List<String> orderedList = new ArrayList<String>();

	for (String s : words) {
		for (String w : freqWords) {
			if (s.toLowerCase().indexOf(w.toLowerCase()) != -1) {
				freqCount.put(w, freqCount.getOrDefault(w, 0) + 1);
			}
		}
	}

	freqCount.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
			.forEachOrdered(x -> {
				orderedList.add(x.getKey());
			});
	String[] res = new String[orderedList.size()];
	res = orderedList.toArray(res);
	return Arrays.copyOfRange(res, 0, K);

}