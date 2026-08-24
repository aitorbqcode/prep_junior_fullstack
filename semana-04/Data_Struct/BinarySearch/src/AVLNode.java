// Añade el factor de equilibrio a cada nodo
class AVLNode<T extends Comparable<T>> {
    T value;
    AVLNode<T> left, right;
    int height;  // altura del subárbol con raíz en este nodo



    public AVLNode(T value) {
        this.value = value;
        this.height = 1;
    }


}