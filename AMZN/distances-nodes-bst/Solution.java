/*
https://leetcode.com/problems/insert-into-a-binary-search-tree/
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
https://leetcode.com/discuss/interview-question/376375/
https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/

Given a list of unique integers nums, construct a BST from it (you need to insert nodes one-by-one with the given order to get the BST) and find the distance between two nodes node1 and node2. Distance is the number of edges between two nodes. If any of the given nodes does not appear in the BST, return -1.

Example 1:

Input: nums = [2, 1, 3], node1 = 1, node2 = 3
Output: 2
Explanation:
     2
   /   \
  1     3

*/

public class Solution {
    Time complexity: O(n * h), where n is the number of nodes and h is the height of the tree. In the worst case tree is not balanced (elements are already in sorted order) and complexity will be O(n^2).
Space complexity: O(n).

public int bstDistance(int[] nums, int node1, int node2) {
    TreeNode root = buildBST(nums, node1, node2);
    if (root == null) return -1;
    TreeNode lca = lca(root, node1, node2);
    return getDistance(lca, node1) + getDistance(lca, node2);
}

private int getDistance(TreeNode src, int dest) {
    if (src.val == dest) return 0;
    TreeNode node = src.left;
    if (src.val < dest) {
        node = src.right;
    }
    return 1 + getDistance(node, dest);
}

private TreeNode lca(TreeNode root, int node1, int node2) {
    while (true) {
        if (root.val > node1 && root.val > node2) {
            root = root.left;
        } else if (root.val < node1 && root.val < node2) {
            root = root.right;
        } else {
            return root;
        }
    }
}

private TreeNode buildBST(int[] nums, int node1, int node2) {
    TreeNode root = null;
    boolean found1 = false;
    boolean found2 = false;
    for (int val : nums) {
        if (val == node1) found1 = true;
        if (val == node2) found2 = true;
        
        TreeNode node = new TreeNode(val);
        if (root == null) {
            root = node;
            continue;
        }
        addToBST(root, node);
    }
    if (!found1 || !found2) return null;
    return root;
}

private void addToBST(TreeNode root, TreeNode node) {
    for (TreeNode curr = root; true; ) {
        if (curr.val > node.val) {
            if (curr.left == null) {
                curr.left = node;
                break;
            } else {
                curr = curr.left;
            }
        } else {
            if (curr.right == null) {
                curr.right = node;
                break;
            } else {
                curr = curr.right;
            }
        }
    }
}
}




//Solution 2

static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x){
        this.val = x;
    }
}

public static int distBwTwoNodes(int[] nums ,  int node1, int node2)
{
    TreeNode root = new TreeNode(nums[0]);
    boolean n1 = false;
    boolean n2 = false;
    if(nums[0] == node1)
        n1=true;
    if(nums[0] == node2)
        n2=true;

    // construct BST
    for(int i=1;i<nums.length;i++){
        insertIntoBST(root, nums[i]);
        if(nums[i] == node1)
            n1=true;
        if(nums[i] == node2)
            n2=true;
    }

    if(n1==false || n2==false)
        return -1;
    TreeNode lca = lowestCommonAncestor(root, new TreeNode(node1), new TreeNode(node2));
    int d1 = finDis(lca,node1,0);
    int d2 = finDis(lca,node2,0);
    return d1+d2;
}

public static int finDis(TreeNode lca, int val, int d){
    if(lca.val==val)
        return d;

    if(lca.val < val)
        return finDis(lca.right,val,++d);


    return finDis(lca.left,val,++d);
}

// insert a val to BSt with root given root
public static TreeNode insertIntoBST(TreeNode root, int val)
{
    TreeNode droot = root;
    TreeNode par =  find(droot, val, null);
    if(par==null)
        return par;
    if(par.val < val)
        par.right = new TreeNode(val);
    else
        par.left = new TreeNode(val);
    return root;
}

public static TreeNode find(TreeNode root, int val, TreeNode p)
{
    if(root==null)
        return p;

    if(root.val > val)
        return find(root.left, val, root);


    return find(root.right, val, root);

}

public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if(root.val > Math.max(p.val, q.val))
        return lowestCommonAncestor(root.left, p, q);
    else if(root.val < Math.min(p.val ,q.val))
        return lowestCommonAncestor(root.right, p, q);
    return root;
}