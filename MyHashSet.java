// Custom implementation of a HashSet
class MyHashSet<T> {
    private int capacity = 1021; // Initial capacity of the set
    private int size = 0;
    private final double loadFactor = 0.9;
    private MyLinkedList<T>[] buckets; // Array of buckets
    // Constructor for Hashset
    @SuppressWarnings("unchecked")
    public MyHashSet() {
        buckets = new MyLinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new MyLinkedList<>(); // Initialize each bucket with an empty linked list.
        }
    }

    //Computes the bucket index for a given key based on its hash code.
    private int getBucketIndex(T key) {
        return (key == null ? 0 : (key.hashCode() & 0x7FFFFFFF)) % capacity;
    }

    // Adds a key to the set if it does not already exist.
    public void add(T key) {
        int index = getBucketIndex(key);
        MyLinkedList<T> bucket = buckets[index];

        if (!bucket.contains(key)) {
            bucket.add(key);
            size++;

            if ((double) size / capacity >= loadFactor) {
                resize();
            }
        }
    }

    //Checks if the set contains the given key.
    public boolean contains(T key) {
        int index = getBucketIndex(key);
        MyLinkedList<T> bucket = buckets[index];

        for (T entry : bucket) {
            if (entry.equals(key)) return true;
        }
        return false;
    }


    //Resizes the hash table when the load factor threshold is exceeded.
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = nextPrime(capacity * 2); // Find the next prime number for the new capacity.
        MyLinkedList<T>[] newBuckets = new MyLinkedList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new MyLinkedList<>();
        }

        // Rehash all existing elements into the new buckets.
        for (int i = 0; i < capacity; i++) {
            MyLinkedList<T> bucket = buckets[i];
            for (T key : bucket) {
                int newIndex = (key == null ? 0 : (key.hashCode() & 0x7FFFFFFF)) % newCapacity;
                newBuckets[newIndex].add(key);
            }
        }

        // Refresh the buckets and capacity to the new resized values.
        buckets = newBuckets;
        capacity = newCapacity;
    }

    // Finds the next prime number bigger than or equal to the given number.
    private int nextPrime(int num) {
        while (!isPrime(num)) {
            num++;
        }
        return num;
    }

    //Checks if a number is prime.
    private boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num <= 3) return true;
        if (num % 2 == 0 || num % 3 == 0) return false;

        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) return false;
        }
        return true;
    }

}
