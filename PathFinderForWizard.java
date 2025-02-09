// Create a class for taking the minimum time for a wizard choice
public class PathFinderForWizard {
    //This function provide a min time betweeen given nodes
    public double getShortestPathTime(Graph graph, Node start, Node target, Integer WizardChoice) {
        // Create pathTimes and previousNodes Hashmaps
        MyHashMap<Node, Double> PathTimes = new MyHashMap<>();
        MyHashMap<Node, Node> previousNodes = new MyHashMap<>();
        // Create a priority queue to select the next node
        MyPriorityQueue<Node> PriorityQueueWithMinHeap = new MyPriorityQueue<>((node1, node2) -> {
            double PathTime1 = PathTimes.getOrDefault(node1, Double.MAX_VALUE);
            double PathTime2 = PathTimes.getOrDefault(node2, Double.MAX_VALUE);
            return Double.compare(PathTime1, PathTime2);
        });
        //Create a Hashset for already visited nodes
        MyHashSet<Node> visitedNodes = new MyHashSet<>();

        //Initialize time for the starting node as 0.
        PathTimes.put(start, 0.0);
        //Add starting node to the queue
        PriorityQueueWithMinHeap.add(start);

        //Looping the nodes in the queue
        while (!PriorityQueueWithMinHeap.isEmpty()) {
            Node current = PriorityQueueWithMinHeap.poll();

            //When the target node is equals to current return the pathTime
            if (current.equals(target)) {
                return PathTimes.get(target);
            }
            // İf current node is in the visited nodes skip the current
            if (visitedNodes.contains(current)) {
                continue;
            }
            // Adding selected node to the visited nodes hashset
            visitedNodes.add(current);

            //Loop the neighbours of the current node
            for (Edge edge : current.neighbors) {
                Node neighbor = edge.to;

                // If the neighbour is not passable, and it's type is not equal to the wizard choice skip this neighbour
                if (neighbor.type != WizardChoice && !neighbor.passable) {
                    continue;
                }
                // If the neighbour is visited skip it
                if (visitedNodes.contains(neighbor)) {
                    continue;
                }
                // Calculate the time between current and neighbour
                double newTime = PathTimes.get(current) + edge.timeForEdge;

                // if the newTime is shorter than the others update the shortest time for neighbour and put it in the queue
                if (newTime < PathTimes.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    PathTimes.put(neighbor, newTime);
                    PriorityQueueWithMinHeap.add(neighbor);
                    previousNodes.put(neighbor, current);
                }
            }
        }
        // If there is no path return the infinite value
        return Double.MAX_VALUE;
    }


}
