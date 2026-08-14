public class TreeNode {
    TreeNode left;
    TreeNode right;
    int value;

    public TreeNode(int value) {
        this.value = value;
        left = null;
        right = null;
    }

    public TreeNode(int value, TreeNode left, TreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public static TreeNode leaf(int value) {
        return new TreeNode(value);
    }

    public static TreeNode join(int value, TreeNode left, TreeNode right) {
        return new TreeNode(value, left, right);
    }

    public TreeNode withLeaves(int leftVal, int rightVal) {
        this.left = new TreeNode(leftVal);
        this.right = new TreeNode(rightVal);
        return this;
    }

    public TreeNode withLeftLeaf(int leftVal) {
        this.left = new TreeNode(leftVal);
        return this;
    }
}
