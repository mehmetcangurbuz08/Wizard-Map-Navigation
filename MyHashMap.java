import java.util.function.Function;

// Custom implementation of a HashMap
class MyHashMap<K, V> {
    // Initial capacity (a prime number for better hashing).
    private int capacity = 1021;
    //Number of entries
    private int size = 0;
    //Load factor for resizing.
    private final double loadFactor = 0.9;
    // Array of buckets which contains a linked list
    private MyLinkedList<Entry<K, V>>[] buckets;

    // Constructor for HashMap
    @SuppressWarnings("unchecked")
    public MyHashMap() {
        buckets = new MyLinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new MyLinkedList<>();
        }
    }

    // Create a class to represents a key-value pair in the hash map.
    static class Entry<K, V> {
        final K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    //Calculates a secure hash code for the key and returns the bucket index.
    private int getBucketIndex(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % capacity);
    }


    //Retrieves the value associated with the given key
    public V get(K key) {
        int index = getBucketIndex(key);
        MyLinkedList<Entry<K, V>> bucket = buckets[index];
        for (Entry<K, V> entry : bucket) {
            if (equalsKey(entry.key, key)) {
                return entry.value; // Return the value if the key matches.
            }
        }
        return null; // Return null if the key is not found.
    }

    //Retrieves the value for a key if present, or computes and stores it if absent.
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        int index = getBucketIndex(key);
        MyLinkedList<Entry<K, V>> bucket = buckets[index];

        // Check if the key already exists and return its value.
        for (Entry<K, V> entry : bucket) {
            if (equalsKey(entry.key, key)) {
                return entry.value;
            }
        }

        // Compute a new value for the key and add it to the map.
        V newValue = mappingFunction.apply(key);
        if (newValue != null) { // Only add non-null values.
            bucket.add(new Entry<>(key, newValue));
            size++;

            // Resize if the load factor is exceeded.
            if ((double) size / capacity >= loadFactor) {
                resize();
            }
        }
        return newValue;
    }

    // Retrieves the value associated with the key or returns a default value if not found.

    public V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        return (value != null) ? value : defaultValue;
    }

    // Adds or updates a key-value pair in the map.
    public void put(K key, V value) {
        int index = getBucketIndex(key);
        MyLinkedList<Entry<K, V>> bucket = buckets[index];
        for (Entry<K, V> entry : bucket) {
            if (equalsKey(entry.key, key)) {
                entry.value = value; // Update the value if the key already exists.
                return;
            }
        }
        bucket.add(new Entry<>(key, value)); // Add a new entry if the key does not exist.
        size++;

        // Resize if the load factor is exceeded.
        if ((double) size / capacity >= loadFactor) {
            resize();
        }
    }

    // Helper method: Checks if two keys are equal.

    private boolean equalsKey(K a, K b) {
        return (a == b) || (a != null && a.equals(b));
    }

    // Resizes the hash map when the load factor is exceeded.
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = nextPrime(capacity * 2); // Find the next prime number for new capacity.
        MyLinkedList<Entry<K, V>>[] newBuckets = new MyLinkedList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new MyLinkedList<>();
        }

        // Rehash all existing entries into the new buckets.
        for (int i = 0; i < capacity; i++) {
            MyLinkedList<Entry<K, V>> bucket = buckets[i];
            for (Entry<K, V> entry : bucket) {
                int newIndex = Math.abs(entry.key.hashCode() % newCapacity);
                newBuckets[newIndex].add(entry);
            }
        }

        // Update the bucket array and capacity.
        buckets = newBuckets;
        capacity = newCapacity;
    }

    //Helper method: Checks if a number is prime.

    private boolean isPrime(int num) {
        if (num <= 1)
            return false;
        if (num <= 3)
            return true;
        if (num % 2 == 0 || num % 3 == 0)
            return false;
        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0)
                return false;
        }
        return true;
    }

    //Helper method: Finds the next prime number greater than or equal to the specified number.
    private int nextPrime(int num) {
        while (!isPrime(num)) {
            num++;
        }
        return num;
    }
}
