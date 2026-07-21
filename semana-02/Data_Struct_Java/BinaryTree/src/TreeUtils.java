public class TreeUtils {

    public int sumTree(Node<Integer> root) {
        //If node is null we return 0
        if(root == null){
            return 0;
        }
        //If not we return the value of the node plus the other conected nodes
        return root.value + sumTree(root.left) + sumTree(root.right);
    }
}