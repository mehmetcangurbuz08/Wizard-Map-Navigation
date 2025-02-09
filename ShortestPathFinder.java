// Create a class for finding the shortest path to the target node from given starting node
public class ShortestPathFinder {
    // This function provide us the shortest path between given nodes by using Dijkstra's algorithm
    public MyLinkedList<Node> findShortestWithDijkstra(Graph graph, Node start, Node target) {
        // Create pathTimes and previousNodes Hashmaps
        MyHashMap<Node, Double> pathTimes = new MyHashMap<>();
        MyHashMap<Node, Node> previousNodes = new MyHashMap<>();
        MyPriorityQueue<Node> priorityQueueWithMinHeap = new MyPriorityQueue<>((node1, node2) -> {
            double PathTime1 = pathTimes.getOrDefault(node1, Double.MAX_VALUE);
            double PathTime2 = pathTimes.getOrDefault(node2, Double.MAX_VALUE);
            return Double.compare(PathTime1, PathTime2);
        });
        //Initialize time for the starting node as 0.
        pathTimes.put(start, 0.0);
        //Add starting node to the queue
        priorityQueueWithMinHeap.add(start);
        //Looping the nodes in the queue
        while (!priorityQueueWithMinHeap.isEmpty()) {
            Node current = priorityQueueWithMinHeap.poll();
            //When the target node is equals to current break the while loop
            if (current.equals(target)) {
                break;
            }
            //Loop the neighbours of the current node
            for (Edge edge : current.neighbors) {
                Node neighbor = edge.to;
                //If the neighbour is not passable skip it
                if (!neighbor.passable) continue;
                // Calculate the time between current and neighbour
                double newTime = pathTimes.get(current) + edge.timeForEdge;
                // if the newTime is shorter than the others update the shortest time for neighbour and put it in the queue
                if (newTime < pathTimes.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    pathTimes.put(neighbor, newTime);
                    previousNodes.put(neighbor, current);
                    priorityQueueWithMinHeap.add(neighbor);
                }
            }
        }
        //Return create path function for returning the shortest path in linked list
        return CreatePath(start, target, previousNodes);
    }

    // This function create a path from previous nodes map
    private MyLinkedList<Node> CreatePath(Node start, Node target, MyHashMap<Node, Node> previousNodes) {
        //Create a linked list to hold path
        MyLinkedList<Node> path = new MyLinkedList<>();
        //Start from the last node for traverse the path
        Node current = target;
        // Traverse back through the previousNodes map and add these nodes to the linked list
        while (current != null) {
            path.addFirst(current);
            current = previousNodes.get(current);
        }
        return path; // Return the created path by using linked list
    }



}