public class BinaryTreeNode<T> {
    public T value;
    public BinaryTreeNode<T> left;
    public BinaryTreeNode<T> right;

    public BinaryTreeNode(T value) {
        this.value = value;
        left = null;
        right = null;
    }

    public void insert(T value){
        if(this.left == null){
            left = new BinaryTreeNode<>(value);
        } else if (this.right == null) {
            right = new BinaryTreeNode<>(value);
        } else {
            this.left.insert(value);
        }
    }

    public int heigth(){
        //Inicializamos la altura de cada lado
        int leftHeigth = 0;
        int rightHeigth = 0;

        //If left it's not null we go to get the left side, and then the same with the right side
        if(left != null){
            leftHeigth = this.left.heigth();
        }
        if(right != null){
            rightHeigth = this.right.heigth();
        }

        //We return the max value of each side
        return 1 + Math.max(leftHeigth, rightHeigth);
    }

}
