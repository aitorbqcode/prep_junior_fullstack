public class BinarySearchTree<T extends Comparable<T>> {

    public static class Node<T> {
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

    // Recursive insert
    private Node<T> insertRecursive(Node<T> current, T value) {
        // Si llegamos a un espacio vacío, creamos el nodo aquí
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


    // Search a value
    public boolean search(T value) {
        return searchRecursive(this.root, value);
    }

    // Recursive search
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

    public void delete(T value) {
        root = deleteRecursive(root, value);
    }

    private Node<T> deleteRecursive(Node<T> current, T value) {
        if (current == null) {
            return null; // El elemento no se encuentra en el árbol
        }

        int comparison = value.compareTo(current.value);

        // 1. Buscar el nodo a eliminar
        if (comparison < 0) {
            current.left = deleteRecursive(current.left, value);
        } else if (comparison > 0) {
            current.right = deleteRecursive(current.right, value);
        } else {
            // ¡Nodo encontrado! Aplicar los 3 casos:

            // CASO 1: Nodo hoja (sin hijos)
            if (current.left == null && current.right == null) {
                return null;
            }

            // CASO 2: Nodo con UN SOLO hijo
            if (current.left == null) {
                return current.right; // Retorna el hijo derecho para sustituir al padre
            }
            if (current.right == null) {
                return current.left;  // Retorna el hijo izquierdo para sustituir al padre
            }

            // CASO 3: Nodo con DOS hijos
            // A. Buscar el valor mínimo del subárbol derecho (sucesor inorden)
            T smallestValue = findMinValue(current.right);

            // B. Reemplazar el valor del nodo actual por el del sucesor inorden
            current.value = smallestValue;

            // C. Eliminar el sucesor inorden del subárbol derecho
            current.right = deleteRecursive(current.right, smallestValue);
        }

        return current;
    }

    // Método auxiliar para encontrar el mínimo (el nodo más a la izquierda)
    private T findMinValue(Node<T> node) {
        return node.left == null ? node.value : findMinValue(node.left);
    }
}