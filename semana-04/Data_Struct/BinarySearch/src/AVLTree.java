public class AVLTree<T extends Comparable<T>> {

    public static class AVLNode<T> {
        T value;
        AVLNode<T> left, right;
        int height;  // altura del subárbol con raíz en este nodo

        public AVLNode(T value) {
            this.value = value;
            this.height = 1;
        }
    }

    private AVLTree.AVLNode<T> root;

    public AVLTree() {
        this.root = null;
    }

    public int height(AVLNode<T> node){
        if (node == null){
            return 0;
        }
        return node.height;
    }

    public int getBalance(AVLNode<T> node){
        if(node == null){
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    public AVLNode<T> rotateRight(AVLNode<T> node){
        AVLNode<T> newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;

        // Update the heights
        node.height = 1 + Math.max(height(node.left), height(node.right));
        newRoot.height = 1 + Math.max(height(newRoot.left), height(newRoot.right));

        return newRoot;
    }

    public AVLNode<T> rotateLeft(AVLNode<T> node){
        AVLNode<T> newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;

        // Update the heights
        node.height = 1 + Math.max(height(node.left), height(node.right));
        newRoot.height = 1 + Math.max(height(newRoot.left), height(newRoot.right));

        return newRoot;
    }

    public void insert(T value) {
        this.root = insertRecursive(this.root, value);
    }

    private AVLNode<T> insertRecursive(AVLNode<T> current, T value) {
        // Si llegamos a un espacio vacío, creamos el nodo aquí
        if (current == null) {
            return new AVLTree.AVLNode<>(value);
        }

        int comparison = value.compareTo(current.value);

        if (comparison < 0) {
            // El valor es menor -> bajamos por la izquierda y "enganchamos" el resultado
            current.left = insertRecursive(current.left, value);
        } else if (comparison > 0) {
            // El valor es mayor -> bajamos por la derecha y "enganchamos" el resultado
            current.right = insertRecursive(current.right, value);
        }

        //Actualizar la altura del nodo actual
        current.height = 1 + Math.max(height(current.left), height(current.right));

        //Get Balance
        int balance = getBalance(current);

        // Caso 1: Izquierda - Izquierda (Rotación Simple Derecha)
        if (balance > 1 && value.compareTo(current.left.value) < 0) {
            return rotateRight(current);
        }

        // Caso 2: Derecha - Derecha (Rotación Simple Izquierda)
        if (balance < -1 && value.compareTo(current.right.value) > 0) {
            return rotateLeft(current);
        }

        // Caso 3: Izquierda - Derecha (Rotación Doble: Izquierda y luego Derecha)
        if (balance > 1 && value.compareTo(current.left.value) > 0) {
            current.left = rotateLeft(current.left);
            return rotateRight(current);
        }

        // Caso 4: Derecha - Izquierda (Rotación Doble: Derecha y luego Izquierda)
        if (balance < -1 && value.compareTo(current.right.value) < 0) {
            current.right = rotateRight(current.right);
            return rotateLeft(current);
        }

        // Retornamos el nodo actual para mantener la estructura intacta
        return current;
    }
}