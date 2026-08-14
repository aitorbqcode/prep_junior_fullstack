class Solution {
    static int maxSum(TreeNode root) {
        if(root == null){
            return 0;
        }

        int leftSum = 0;
        int rightSum = 0;

        if(root.left != null){
            leftSum = maxSum(root.left);
        } if (root.right != null){
            rightSum = maxSum(root.right);
        }

        //When we have negative numbers we use this to sum the node with value.
        if(leftSum == 0 && rightSum != 0){
            return root.value + rightSum;
        } else if (rightSum == 0 && leftSum != 0){
            return root.value + leftSum;
        }

        return root.value + Math.max(leftSum, rightSum);
    }

    public static void main(String[] args) {
        TreeNode left = TreeNode.leaf(4).withLeaves(-80, -60);
        TreeNode right = TreeNode.leaf(10).withLeftLeaf(-90);
        TreeNode root = TreeNode.join(5, left, right);

        System.out.println(maxSum(root));
    }
}