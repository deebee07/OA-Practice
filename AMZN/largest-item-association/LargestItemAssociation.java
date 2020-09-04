/*
https://leetcode.com/discuss/interview-question/782606/

Amazon has developed a systen to proovide recommendations to the customers regarding the items they can purchase. Based on historical customer purchase information, an item association can be defined as if an item A is ordered by a customer it is likely that item B is also ordered by same customer. All items that are linked together by a item association can be considered to be in the same group.
When an item has no association with other item it is considered to be in its own association group of size 1.


Given a list of item association relationships (groups of items likely to be grouped together) Write program the prints the largest item association group. If same number of association group then item first that appears in lexographical order.


Input:
itemAssociation list, pairs of strings representing items that are ordered together.

Output:
return a list of String representing largest item association group, sorted lexographically.


public class LargestItemAssociation {

	public List<String> largestItemAssociation(List<PairString> itemAssociation) {
		// write your code here
	}
}

public class PairString {
    String first;
    String second;

    public PairString(String first, String second) {
        this.first = first;
        this.second = second;
    }

}

https://leetcode.com/playground/6UpJYuXU

 */

public class LargestItemAssociation {
public static class PairString {
private String first;
private String second;

	public PairString(String first, String second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public String toString() {
		return first + " ," + second;
	}
}

public static void main(String[] args) {
	List<PairString> pairs = Arrays.asList( //
			new PairString("item1", "item2"), // -> item1, item3, item2
			new PairString("item3", "item4"), //
			new PairString("item2", "item5") //
	);

	System.out.println(largestItemAssociation(pairs));
}

// Time complexity O(n2) and space is O(n)
public static List<String> largestItemAssociation(List<PairString> pairs) {
	if (pairs.isEmpty())
		return null;
	PriorityQueue<Set<String>> max_heap = new PriorityQueue<>(//
			(l1, l2) -> Integer.compare(l2.size(), l1.size()));//
	Collections.sort(pairs, (a, b) -> a.first.compareTo(b.first));
	for (int pair = 0; pair < pairs.size(); pair++) {
		Set<String> buildList = new TreeSet<>(Arrays.asList(pairs.get(pair).first, pairs.get(pair).second));
		for (int inner = pair + 1; inner < pairs.size(); inner++) {
			mergeTag(buildList, pairs.get(inner));
		}
		max_heap.add(buildList);
	}

	return max_heap.poll().stream().collect(Collectors.toList());
}

private static void mergeTag(Set<String> buildList, PairString pairString) {
	if (buildList.contains(pairString.first) && buildList.contains(pairString.second))
		return;
	if (buildList.contains(pairString.first)) {
		buildList.add(pairString.second);
	} else if (buildList.contains(pairString.second)) {
		buildList.add(pairString.first);
	}

}
}




// Solution 2 Java Recursion

// private static LinkedHashMap<String, ArrayList> map = new LinkedHashMap<>();

// private static List largestItemAssociation(List<PairString> pairs) {
//     ArrayList<String> result = new ArrayList<>();
//     for (PairString pair : pairs) {
//         if (map.containsKey(pair.first)) {
//             map.get(pair.first).add(pair.second);
//         } else {
//             map.put(pair.first, new ArrayList<String>(Arrays.asList(pair.second)));
//         }
//     }
//     for (PairString pair : pairs) {
//         ArrayList<String> current = new ArrayList<>();
//         current.addAll(recursion(pair.first, new ArrayList()));
//         if (result.size() < current.size()) {
//             result = new ArrayList<>();
//             result.addAll(current);
//         } else if (result.size() == current.size()) {
//             List<ArrayList<String>> lists = new ArrayList<>();
//             lists.add(result);
//             lists.add(current);
//             Collections.sort(lists, (list1, list2) -> {
//                 int compareResult = 0;
//                 for (int i = 0; i < list1.size() && compareResult == 0; i++) {
//                     compareResult = list1.get(i).compareTo(list2.get(i));
//                 }
//                 return compareResult;
//             });
//             result = new ArrayList<>();
//             result.addAll(lists.get(0));
//         }
//     }
//     return result;
// }

// private static List recursion(String key, List list) {
//     if (map.containsKey(key)) {
// 		list.add(key);
//         ArrayList<String> local = map.get(key);
//         for (String ss : local) {
//             recursion(ss, list);
//         }
//     } else {
// 		list.add(key);
//     }
//     return list;
// }