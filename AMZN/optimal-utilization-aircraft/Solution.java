/*
Given 2 lists a and b. Each element is a pair of integers where the first integer represents the unique id and the second integer represents a value. Your task is to find an element from a and an element form b such that the sum of their values is less or equal to target and as close to target as possible. Return a list of ids of selected elements. If no pair is possible, return an empty list.

Example 1:

Input:
a = [[1, 2], [2, 4], [3, 6]]
b = [[1, 2]]
target = 7

Output: [[2, 1]]

Explanation:
There are only three combinations [1, 1], [2, 1], and [3, 1], which have a total sum of 4, 6 and 8, respectively.
Since 6 is the largest sum that does not exceed 7, [2, 1] is the optimal pair.
Example 2:

Input:
a = [[1, 3], [2, 5], [3, 7], [4, 10]]
b = [[1, 2], [2, 3], [3, 4], [4, 5]]
target = 10

Output: [[2, 4], [3, 2]]

Explanation:
There are two pairs possible. Element with id = 2 from the list `a` has a value 5, and element with id = 4 from the list `b` also has a value 5.
Combined, they add up to 10. Similarily, element with id = 3 from `a` has a value 7, and element with id = 2 from `b` has a value 3.
These also add up to 10. Therefore, the optimal pairs are [2, 4] and [3, 2].
Example 3:

Input:
a = [[1, 8], [2, 7], [3, 14]]
b = [[1, 5], [2, 10], [3, 14]]
target = 20

Output: [[3, 1]]
Example 4:

Input:
a = [[1, 8], [2, 15], [3, 9]]
b = [[1, 8], [2, 11], [3, 12]]
target = 20

Output: [[1, 3], [3, 2]]

*/ 
// Amazon | Online Assessment 2019 | Optimal Aircraft Utilization
// https://leetcode.com/discuss/interview-question/318918/Amazon-or-Online-Assessment-2019-or-Optimal-Aircraft-Utilization
// https://leetcode.com/discuss/interview-question/373202
// https://leetcode.com/discuss/interview-question/373202
// O(NlogN)
public class Main {
    public List<List<Integer>> aircraftUtilization(int maxTravelDist, int[][] forwardRouteList, int[][] returnRouteList){
        List<List<Integer>> res = new ArrayList<>();
        int len1 = forwardRouteList.length, len2 = returnRouteList.length;
        if(len1 == 0 || len2 == 0) return res;
        Arrays.sort(forwardRouteList, (int[] a, int[] b) -> (a[1] - b[1]));
        Arrays.sort(returnRouteList, (int[] a, int[] b) -> (a[1] - b[1]));
        int left = 0, right = len2 - 1, maxVal = -1;
        HashMap<Integer, List<List<Integer>>> map = new HashMap<>();
        while(left < len1 && right >= 0){
            int sum = forwardRouteList[left][1] + returnRouteList[right][1];
            if(sum > maxTravelDist){ --right; continue;}
            if(sum >= maxVal){
                int r = right;
                map.putIfAbsent(sum, new ArrayList<>());
                // check the duplicates 
                while(r >= 0 && returnRouteList[r][1] == returnRouteList[right][1]){
                    List<Integer> list = new ArrayList<>();
                    list.add(forwardRouteList[left][0]); list.add(returnRouteList[r][0]);
                    map.get(sum).add(list); --r;
                }
                maxVal = sum;
            }
            ++left;
        }
        return map.get(maxVal);
    }
    public static void main(String[] args) {
        Main main = new Main();
        int[] maxTravelDists = {7000, 10000, 10000};
        int[][][] forwardRouteLists ={ {{1, 2000}, {2, 4000}, {3, 6000}},
                            {{1, 2000}, {2, 5000}, {3, 7000}, {4, 10000}},
                                      {{1,3000},{2,5000},{3,7000},{4,10000}}}; 
        int[][][] returnRouteLists ={ {{1, 2000}},
                            {{1, 2000}, {2, 3000}, {3, 4000}, {4, 5000}},
                                     {{1,2000},{2,2000},{3,4000},{4,4000}}}; 
        for(int i = 0; i < maxTravelDists.length; ++i){
            System.out.println(main.aircraftUtilization(maxTravelDists[i], forwardRouteLists[i], returnRouteLists[i]));
        }
    }
}

// Solution 2:
    private List<int[]> getPairs(List<int[]> a, List<int[]> b, int target) {
        Collections.sort(a, (i,j) -> i[1] - j[1]);
        Collections.sort(b, (i,j) -> i[1] - j[1]);
        List<int[]> result = new ArrayList<>();
        int max = Integer.MIN_VALUE;
        int m = a.size();
        int n = b.size();
        int i =0;
        int j =n-1;
        while(i<m && j >= 0) {
            int sum = a.get(i)[1] + b.get(j)[1];
            if(sum > target) {
                 --j;
            } else {
                if(max <= sum) {
                    if(max < sum) {
                        max = sum;
                        result.clear();
                    }
                    result.add(new int[]{a.get(i)[0], b.get(j)[0]});
                    int index = j-1;
                    while(index >=0 && b.get(index)[1] == b.get(index+1)[1]) {
                         result.add(new int[]{a.get(i)[0], b.get(index--)[0]});
                    }
                }
                ++i;
            }
        }
        return result;
    } 

// O(n^2)
public class OptimalUtilization{
    public List<int[]> solution(List<int[]> a, List<int[]> b, int target){
        Map<Integer, List<int[]>> map = new HashMap<>();//key is sum , value is list of ids from a and b.

        for (int i = 0; i < a.size(); i ++){
            for (int j = 0; j < b.size(); j ++){
                List<int[]> sums = map.getOrDefault(a.get(i)[1] + b.get(j)[1], new ArrayList<int[]>());
                sums.add(new int[] {a.get(i)[0], b.get(j)[0]});
                map.put(a.get(i)[1] + b.get(j)[1], sums);
            }
        }

        List<Integer> allSums = new ArrayList<>();
        for (Integer i : map.keySet()){
            if (i < target){
                allSums.add(i);
            } else if (i == target){
                return map.get(target);
            }
        }
        if (allSums.size() == 0){
            return new ArrayList<>();//target is less than every possible sums.
        }
        return map.get(Collections.max(allSums));
    }
}

import java.util.*;

public class OptimalUti {
    private static List<List<Integer>> compute(int[][] a, int[][] b, int target) {
        List<List<Integer>> res = new ArrayList<>();
        TreeMap<Integer, List<List<Integer>>> tree = new TreeMap<>();

        for (int i=0; i<a.length; i++) {
            for (int j=0; j<b.length; j++) {
                int sum = a[i][1] + b[j][1]; 
                if (sum <= target) {
                    List<List<Integer>> list = tree.computeIfAbsent(sum, (k) -> new ArrayList<>());
                    list.add(Arrays.asList(a[i][0], b[j][0]));
                }
            }
        }

        return tree.floorEntry(target).getValue();
    }

    public static void main(String...s) {
        int[][][] As = {
            {{1, 2}, {2, 4}, {3, 6}},
            {{1, 3}, {2, 5}, {3, 7}, {4, 10}},
            {{1, 8}, {2, 7}, {3, 14}},
            {{1, 8}, {2, 15}, {3, 9}}
        };
        int[][][] Bs = {
            {{1, 2}},
            {{1, 2}, {2, 3}, {3, 4}, {4, 5}},
            {{1, 5}, {2, 10}, {3, 14}},
            {{1, 8}, {2, 11}, {3, 12}}
        };
        int[] targets = {7, 10, 20, 20};

        for (int i=0; i<4; i++) {
            System.out.println(compute(As[i], Bs[i], targets[i]).toString());
        }
    }
}


 public List<int[]> findPairs(int[][] a, int[][] b, int target) {
        Arrays.sort(a, (int[] o1, int[] o2) -> o1[1] - o1[1]);
        Arrays.sort(b, (int[] o1, int[] o2) -> o1[1] - o2[1]);

        List<int[]> res = new ArrayList<>();
        int i = 0, j = a.length - 1;
        int max = 0;
        while (i < b.length && j >= 0) {
            if (a[j][1] + b[i][1] > target) {
                j--;
            } else {
                if (a[j][1] + b[i][1] > max) {
                    max = a[j][1] + b[i][1];
                    res.clear();
                    res.add(new int[]{a[j][0], b[i][0]});
                } else if (a[j][1] + b[i][1] == max) {
                    res.add(new int[]{a[j][0], b[i][0]});
                }
                i++;
            }
        }      
        return res;
    }