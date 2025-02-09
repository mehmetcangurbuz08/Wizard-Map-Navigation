import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
// Custom LinkedList implementation
class MyLinkedList<E> implements Iterable<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    // Add element at the end of the list
    public void add(E value) {
        Node<E> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }
    // Check if the list contains a specific element
    public boolean contains(E value) {
        Node<E> current = head;
        while (current != null) {
            if (current.value.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }



    // Add element at the beginning of the list (addFirst)
    public void addFirst(E value) {
        Node<E> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // Get the first element of the list (getFirst)
    public E getFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }
        return head.value;
    }

    // Check if the list is empty (isEmpty)
    public boolean isEmpty() {
        return size == 0;
    }

    // Convert to List
    public List<E> toList() {
        List<E> list = new ArrayList<>(size);
        Node<E> current = head;
        while (current != null) {
            list.add(current.value);
            current = current.next;
        }
        return list;
    }

    // Iterable interface implementation
    @Override
    public Iterator<E> iterator() {
        return new MyLinkedListIterator();
    }

    // Internal iterator class
    private class MyLinkedListIterator implements Iterator<E> {
        private Node<E> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E value = current.value;
            current = current.next;
            return value;
        }
    }

    // Internal Node class
    private static class Node<E> {
        E value;
        Node<E> next;
        Node<E> prev;

        Node(E value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }
}
