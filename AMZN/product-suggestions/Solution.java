/*
https://leetcode.com/problems/search-suggestions-system/
1268. Search Suggestions System
Medium

666

81

Add to List

Share
Given an array of strings products and a string searchWord. We want to design a system that suggests at most three product names from products after each character of searchWord is typed. Suggested products should have common prefix with the searchWord. If there are more than three products with a common prefix return the three lexicographically minimums products.

Return list of lists of the suggested products after each character of searchWord is typed. 

 

Example 1:

Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
Output: [
["mobile","moneypot","monitor"],
["mobile","moneypot","monitor"],
["mouse","mousepad"],
["mouse","mousepad"],
["mouse","mousepad"]
]
Explanation: products sorted lexicographically = ["mobile","moneypot","monitor","mouse","mousepad"]
After typing m and mo all products match and we show user ["mobile","moneypot","monitor"]
After typing mou, mous and mouse the system suggests ["mouse","mousepad"]
Example 2:

Input: products = ["havana"], searchWord = "havana"
Output: [["havana"],["havana"],["havana"],["havana"],["havana"],["havana"]]
Example 3:

Input: products = ["bags","baggage","banner","box","cloths"], searchWord = "bags"
Output: [["baggage","bags","banner"],["baggage","bags","banner"],["baggage","bags"],["bags"]]
Example 4:

Input: products = ["havana"], searchWord = "tatiana"
Output: [[],[],[],[],[],[],[]]
 

Constraints:

1 <= products.length <= 1000
There are no repeated elements in products.
1 <= Σ products[i].length <= 2 * 10^4
All characters of products[i] are lower-case English letters.
1 <= searchWord.length <= 1000
All characters of searchWord are lower-case English letters.
*/

// Soluion 1: https://leetcode.com/problems/search-suggestions-system/discuss/471718/Java-Priority-Queue-No-Sort-or-Trie
class Solution {
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> result = new ArrayList<>();
        PriorityQueue<String> heap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 1; i <= searchWord.length(); i++) {
            String t = searchWord.substring(0, i);
            for (String product: products) {
                if (product.startsWith(t)) {
                    heap.offer(product);
                }
                if (heap.size() > 3) {
                    heap.poll();
                }
            }
            
            LinkedList<String> temp = new LinkedList<>();
            while (!heap.isEmpty()) {
                temp.addFirst(heap.poll());
            }
            result.add(temp);
        }
        return result;
    }
}

// Soluton 2 https://leetcode.com/problems/search-suggestions-system/discuss/440474/Java-trie-explained-clean-code-14ms
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        //sort words so they will be added in a sorted order to nodes 
        Arrays.sort(products);
        
        Trie root = new Trie();
        for (String prod : products) {
            Trie n = root;
            for (char ch : prod.toCharArray()) {
                int i = ch - 'a';
                if (n.next[i] == null) {
                    n.next[i] = new Trie();
                }
                n = n.next[i];
                if (n.words.size() < 3)
                    n.words.add(prod);
            }
        }
        
        List<List<String>> res = new ArrayList();
        Trie n = root;
		//start going over the search word char by char
        for (int i = 0; i < searchWord.length(); i++) {
            n = n.next[searchWord.charAt(i) - 'a'];
			//if we met null - means no more matches possible, the result of result can be filled by empty lists
            if (n == null) {
                for (int j = i; j < searchWord.length(); j++)
                    res.add(Collections.EMPTY_LIST);    
                break;
            }
            res.add(n.words);
        }
        return res;
    }
    //trie node
    class Trie {
        Trie[] next;
        List<String> words;
        Trie() {
            words = new ArrayList();
            next = new Trie[26];
        }
    }