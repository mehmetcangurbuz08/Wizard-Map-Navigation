import java.util.ArrayList;
import java.util.Comparator;
// Custom Priority queue implementation
public class MyPriorityQueue<T> {
    private final ArrayList<T> heap;
    private final Comparator<T> comparator;

    public MyPriorityQueue(Comparator<T> comparator) {
        this.heap = new ArrayList<>(); // Default initial capacity, dynamically grows
        this.comparator = comparator;
    }

    // Add an element to the priority queue
    public void add(T item) {
        if (item == null) throw new IllegalArgumentException("Null elements are not allowed.");
        heap.add(item);
        percolateUp(heap.size() - 1); // Refresh the heap property
    }

    // Remove and return the smallest element
    public T poll() {
        if (heap.isEmpty()) return null;

        T top = heap.getFirst();
        T last = heap.removeLast();

        if (!heap.isEmpty()) {
            heap.set(0, last);
            percolateDown(0);
        }
        return top;
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // Restore the heap property from a given index upwards
    private void percolateUp(int index) {
        T item = heap.get(index);
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            T parent = heap.get(parentIndex);

            // Break if the item is in the correct position
            if (comparator.compare(item, parent) >= 0) break;

            // Move the parent down
            heap.set(index, parent);
            index = parentIndex;
        }
        heap.set(index, item); // Place the item in the correct position
    }

    // Restore the heap property from a given index downwards
    private void percolateDown(int index) {
        T item = heap.get(index);
        int size = heap.size();

        while (index < size / 2) { // Stop when a leaf node is reached
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;

            // Select the smaller child
            int smallestChild = leftChild;
            if (rightChild < size && comparator.compare(heap.get(rightChild), heap.get(leftChild)) < 0) {
                smallestChild = rightChild;
            }

            // Break if the item is in the correct position
            if (comparator.compare(item, heap.get(smallestChild)) <= 0) break;

            // Move the smaller child up
            heap.set(index, heap.get(smallestChild));
            index = smallestChild;
        }
        heap.set(index, item); // Place the item in the correct position
    }
}
