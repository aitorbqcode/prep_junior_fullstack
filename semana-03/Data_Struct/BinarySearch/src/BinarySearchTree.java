public class BinarySearchTree<T extends Comparable<T>> {

    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node<T> root;

    public BinarySearchTree() {
        this.root = null;
    }

    // 1. Método público accesible desde fuera
    public void insert(T value) {
        this.root = insertRecursive(this.root, value);
    }

    // 2. Método auxiliar privado que viaja por el árbol
    private Node<T> insertRecursive(Node<T> current, T value) {
        // Caso base: si llegamos a un espacio vacío, creamos el nodo aquí
        if (current == null) {
            return new Node<>(value);
        }

        int comparison = value.compareTo(current.value);

        if (comparison < 0) {
            // El valor es menor -> bajamos por la izquierda y "enganchamos" el resultado
            current.left = insertRecursive(current.left, value);
        } else if (comparison > 0) {
            // El valor es mayor -> bajamos por la derecha y "enganchamos" el resultado
            current.right = insertRecursive(current.right, value);
        }

        // Retornamos el nodo actual para mantener la estructura intacta
        return current;
    }


    // 1. Método público
    public boolean search(T value) {
        return searchRecursive(this.root, value);
    }

    // 2. Método auxiliar privado recursivo
    private boolean searchRecursive(Node<T> current, T value) {
        // Caso base 1: Llegamos al final del árbol y no encontramos el valor
        if (current == null) {
            return false;
        }

        int comparison = value.compareTo(current.value);

        // Caso base 2: Encontramos el valor
        if (comparison == 0) {
            return true;
        }

        // Navegación
        if (comparison < 0) {
            return searchRecursive(current.left, value); // Buscar a la izquierda
        } else {
            return searchRecursive(current.right, value); // Buscar a la derecha
        }
    }
}