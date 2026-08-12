import java.util.LinkedList;
import java.util.Queue;

public class TreeTraversals {

    //Preorder
    public static void preOrder(BinaryTreeNode<Integer> root){
        if(root == null){
            return;
        }
        System.out.println(root.getValue() + " "); //Print the root
        preOrder(root.getLeft());
        preOrder(root.getRight());
    }

    //Inorder
    public static void inOrder(BinaryTreeNode<Integer> root){
        if(root == null){
            return;
        }
        inOrder(root.getLeft());
        System.out.println(root.getValue() + " ");
        inOrder(root.getRight());
    }

    //PostOrder
    public static void postOrder(BinaryTreeNode<Integer> root){
        if(root == null){
            return;
        }
        postOrder(root.getLeft());
        postOrder(root.getRight());
        System.out.println(root.getValue() + " ");
    }

    // Testear
    public static void main(String[] args) {
        // Construcción del árbol del ejemplo
        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(4);
        root.setLeft(new BinaryTreeNode<>(2));
        root.setRight(new BinaryTreeNode<>(6));

        root.getLeft().setLeft(new BinaryTreeNode<>(1));
        root.getLeft().setRight(new BinaryTreeNode<>(3));

        root.getRight().setLeft(new BinaryTreeNode<>(5));
        root.getRight().setRight(new BinaryTreeNode<>(7));

        System.out.print("Preorden:  "); preOrder(root);  System.out.println();
        System.out.print("Inorden:   "); inOrder(root);   System.out.println();
        System.out.print("Postorden: "); postOrder(root); System.out.println();
    }
}