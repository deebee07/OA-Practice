/*
https://leetcode.com/discuss/interview-question/762546/Amazon-or-OA-2020-or-Amazon-Fresh-Promotion/649777

Amazon Fresh is running a promotion in which customers receive prizes for purchasing a secret combination of fruits. The combination will change each day, and the team running the promotion wants to use a code list to make it easy to change the combination. The code list contains groups of fruits. Both the order of the groups within the code list and the order of the fruits within the groups matter. However, between the groups of fruits, any number, and type of fruit is allowable. The term "anything" is used to allow for any type of fruit to appear in that location within the group.
Consider the following secret code list: [[apple, apple], [banana, anything, banana]]
Based on the above secret code list, a customer who made either of the following purchases would win the prize:
orange, apple, apple, banana, orange, banana
apple, apple, orange, orange, banana, apple, banana, banana
Write an algorithm to output 1 if the customer is a winner else output 0.

Input
The input to the function/method consists of two arguments:
codeList, a list of lists of strings representing the order and grouping of specific fruits that must be purchased in order to win the prize for the day.
shoppingCart, a list of strings representing the order in which a customer purchases fruit.
Output
Return an integer 1 if the customer is a winner else return 0.
Note
'anything' in the codeList represents that any fruit can be ordered in place of 'anything' in the group. 'anything' has to be something, it cannot be "nothing."
'anything' must represent one and only one fruit.
If secret code list is empty then it is assumed that the customer is a winner.

Example 1:

Input: codeList = [[apple, apple], [banana, anything, banana]] shoppingCart = [orange, apple, apple, banana, orange, banana]
Output: 1
Explanation:
codeList contains two groups - [apple, apple] and [banana, anything, banana].
The second group contains 'anything' so any fruit can be ordered in place of 'anything' in the shoppingCart. The customer is a winner as the customer has added fruits in the order of fruits in the groups and the order of groups in the codeList is also maintained in the shoppingCart.
Example 2:

Input: codeList = [[apple, apple], [banana, anything, banana]]
shoppingCart = [banana, orange, banana, apple, apple]
Output: 0
Explanation:
The customer is not a winner as the customer has added the fruits in order of groups but group [banana, orange, banana] is not following the group [apple, apple] in the codeList.
Example 3:

Input: codeList = [[apple, apple], [banana, anything, banana]] shoppingCart = [apple, banana, apple, banana, orange, banana]
Output: 0
Explanation:
The customer is not a winner as the customer has added the fruits in an order which is not following the order of fruit names in the first group.
Example 4:

Input: codeList = [[apple, apple], [apple, apple, banana]] shoppingCart = [apple, apple, apple, banana]
Output: 0
Explanation:
The customer is not a winner as the first 2 fruits form group 1, all three fruits would form group 2, but can't because it would contain all fruits of group 1.

*/
/*
Solution 1
Intuitive Java solution inspired by 392. Is subsequence. Use two pointers i and j respectively for an element in two given lists. For a specific code codelist[i], check the subarray with the same length of codelist[i] starting from j in shoppingcart.
*/
public class FindFruitCombo {

    public static int winPrize(String[][] codeList, String[] shoppingCart) {
        // checking corner cases
        if(codeList == null || codeList.length == 0)
            return 1;
        if(shoppingCart == null || shoppingCart.length == 0)
            return 0;

        int i = 0, j = 0;
        //int codeLen = codeList[i].length;
        while (i < codeList.length && j + codeList[i].length <= shoppingCart.length) {
            boolean match = true;
            for (int k = 0; k < codeList[i].length; k++) {
                if (!codeList[i][k].equals("anything") && !shoppingCart[j+k].equals(codeList[i][k])) {
                    match = false;
                    break;
                }
            }
            if (match) {
                j += codeList[i].length;
                i++;
            } else {
                j++;
            }
        }
        return (i == codeList.length) ? 1 : 0;
    }

    public static void test(String[][] codeList, String[] shoppingCart, int expect) {
        System.out.println(winPrize(codeList, shoppingCart) == expect);
    }

    public static void main(String[] args) {
        // test cases
        String[][] codeList1 = { { "apple", "apple" }, { "banana", "anything", "banana" } };
        String[] shoppingCart1 = {"orange", "apple", "apple", "banana", "orange", "banana"};
        String[][] codeList2 = { { "apple", "apple" }, { "banana", "anything", "banana" } };
        String[] shoppingCart2 = {"banana", "orange", "banana", "apple", "apple"};
        String[][] codeList3 = { { "apple", "apple" }, { "banana", "anything", "banana" } };
        String[] shoppingCart3 = {"apple", "banana", "apple", "banana", "orange", "banana"};
        String[][] codeList4 = { { "apple", "apple" }, { "apple", "apple", "banana" } };
        String[] shoppingCart4 = {"apple", "apple", "apple", "banana"};
        String[][] codeList5 = { { "apple", "apple" }, { "banana", "anything", "banana" } };
        String[] shoppingCart5 = {"orange", "apple", "apple", "banana", "orange", "banana"};
        String[][] codeList6 = { { "apple", "apple" }, { "banana", "anything", "banana" }  };
        String[] shoppingCart6 = {"apple", "apple", "orange", "orange", "banana", "apple", "banana", "banana"};
        String[][] codeList7= { { "anything", "apple" }, { "banana", "anything", "banana" }  };
        String[] shoppingCart7 = {"orange", "grapes", "apple", "orange", "orange", "banana", "apple", "banana", "banana"};
        String[][] codeList8 = {{"apple", "orange"}, {"orange", "banana", "orange"}};
        String[] shoppingCart8 = {"apple", "orange", "banana", "orange", "orange", "banana", "orange", "grape"};
        String[][] codeList9= { { "anything", "anything", "anything", "apple" }, { "banana", "anything", "banana" }  };
        String[] shoppingCart9 = {"orange", "apple", "banana", "orange", "apple", "orange", "orange", "banana", "apple", "banana"};

        // test
        test(codeList1, shoppingCart1, 1);
        test(codeList2, shoppingCart2, 0);
        test(codeList3, shoppingCart3, 0);
        test(codeList4, shoppingCart4, 0);
        test(codeList5, shoppingCart5, 1);
        test(codeList6, shoppingCart6, 1);
        test(codeList7, shoppingCart7, 1);
        test(codeList8, shoppingCart8, 1);
        test(codeList9, shoppingCart9, 1);
    }
}


// Solution 2
// private static int solve(String[][] codeList, String[] shoppingCart) {
// 	if(codeList == null || codeList.length == 0)
// 		return 1;
// 	if(shoppingCart == null || shoppingCart.length == 0)
// 		return 0;
// 	int i=0,j=0;
// 	for(int k=0;k<shoppingCart.length;k++) {
// 		if(codeList[i][j].equals(shoppingCart[k]) || codeList[i][j].equals("anything")) {
// 			j++;
// 			if(j == codeList[i].length) {
// 				i++;
// 				j=0;
// 			}
// 			if(i == codeList.length)
// 				return 1;
// 		}else {
// 			j = codeList[i][0].equals("anything") ? 1: 0;
// 		}
// 	}
// 	return 0;
// }


// Solution 3 Array List
// https://leetcode.com/playground/eEatKB4z
// public class AmazonFreshPromotionCode {
    
//     public static int isCustomerWinner(List<String> shoppingList, List<List<String>> codeList)
//     {
//         //checking for edge cases
//         if(codeList.size() == 0)
//             return 1;
//         if(shoppingList == null || shoppingList.size() == 0)
//             return  0;

//         int codeListIdx = 0;
//         List<String> code = codeList.get(codeListIdx);
//         // we keep 2 pointers for iterating shoppingList
//         // Each time if there is a mismatch, itemEnd starts checking from itemBegin + 1
//         int codeIdx = 0, itemBegin = 0, itemEnd = 0;

//         while(itemEnd < shoppingList.size())
//         {
//             // since "anything" has to be something, it cannot be "nothing"
//             if(code.get(codeIdx).equals("anything") && (shoppingList.get(itemEnd).equals("") || shoppingList.get(itemEnd).equals(" ")))
//                 return 0;
            
//             // if there is "anything" in code or matches with shopping item, we move ahead to check next shopping cart item and next code value
//             if(code.get(codeIdx).equals("anything") || shoppingList.get(itemEnd).equals(code.get(codeIdx)))
//             {
//                 codeIdx++;
//                 itemEnd++;
//             }
//             else
//             {
//                 // else if there is no match, we begin checking the shoppingList again leaving first checked item and check the next item with the first code value again.
//                 // eg : CodeList = [[anything, anything, anything, apple],[...]]  ShoppingList = [orange, apple, banana, orange, apple, orange, ...]
//                 // Here, CL[0] is anything so check next item with next code; next CL[1] is anything so check next; next CL[2] is also anything so check next. But CL[3] is not equal to SL[3], so now we check CL[0] again with SL[1] in the hope that CL items might match with the SL items ahead.
//                 codeIdx = 0;
//                 itemEnd = ++itemBegin;
//             }

//             // Checking if we are done with a code in CodeList, we move to next code.
//             // If we reach at the end of CodeList successfully, it means success as there are no more codes to check.
//             if (codeIdx == code.size())
//             {
//                 codeListIdx++;

//                 if (codeListIdx == codeList.size())
//                 {
//                     return 1;
//                 }
//                 else
//                 {
//                     // Else we update code value with new list present at new CodeList index
//                     // And since we found a code match in our Shopping list, we update itemBegin counter to point next to matched item index
//                     code = codeList.get(codeListIdx);
//                     codeIdx = 0;
//                     itemBegin = itemEnd;
//                 }
//             }
//         }

//         return 0;
//     }
    
//     public static void main(String[] args)
//     {
//         List<List<String>> codeList1 = new ArrayList<>(Arrays.asList(Arrays.asList("apple", "apple"), Arrays.asList("banana", "anything", "banana")));
//         List<String> shoppingList1 = new ArrayList<String>(Arrays.asList("orange", "apple", "apple", "banana", "orange", "banana"));

//         System.out.println("Result1 = " + isCustomerWinner(shoppingList1, codeList1));

//         List<List<String>> codeList2 = new ArrayList<>(Arrays.asList(Arrays.asList("apple", "apple"), Arrays.asList("banana", "anything", "banana")));
//         List<String> shoppingList2 = new ArrayList<String>(Arrays.asList("apple", "apple", "orange", "orange", "banana", "apple", "banana", "banana"));

//         System.out.println("Result2 = " + isCustomerWinner(shoppingList2, codeList2));

//         List<List<String>> codeList3 = new ArrayList<>(Arrays.asList(Arrays.asList("apple", "apple"), Arrays.asList("banana", "anything", "banana")));
//         List<String> shoppingList3 = new ArrayList<String>(Arrays.asList("banana", "orange", "banana", "apple", "apple"));

//         System.out.println("Result3 = " + isCustomerWinner(shoppingList3, codeList3));

//         List<List<String>> codeList4 = new ArrayList<>(Arrays.asList(Arrays.asList("apple", "apple"), Arrays.asList("banana", "anything", "banana")));
//         List<String> shoppingList4 = new ArrayList<String>(Arrays.asList("apple", "banana", "apple", "banana", "orange", "banana"));

//         System.out.println("Result4 = " + isCustomerWinner(shoppingList4, codeList4));

//         List<List<String>> codeList5 = new ArrayList<>(Arrays.asList(Arrays.asList("apple", "apple"), Arrays.asList("apple", "apple", "banana")));
//         List<String> shoppingList5 = new ArrayList<String>(Arrays.asList("apple", "apple", "apple", "banana"));

//         System.out.println("Result5 = " + isCustomerWinner(shoppingList5, codeList5));
        
//         List<List<String>> codeList6 = new ArrayList<>(Arrays.asList(Arrays.asList("apple", "anything"), Arrays.asList("anything", "anything", "banana")));
//         List<String> shoppingList6 = new ArrayList<String>(Arrays.asList("apple", "apple", "orange", "banana", "orange", "banana"));

//         System.out.println("Result6 = " + isCustomerWinner(shoppingList6, codeList6));
//     }
// }

