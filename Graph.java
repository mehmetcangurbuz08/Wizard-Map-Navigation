import java.util.ArrayList;
import java.util.List;
// This class represents a graph created as a 2D array of nodes.
class Graph {
    private final Node[][] nodes; // 2D array to hold nodes
    int width;
    int height; // Lengths of the grid
    // A hashmap to organize nodes according to their type for easy access
    MyHashMap<Integer, List<Node>> nodesByType = new MyHashMap<>();
    //Constructor for a graph
    Graph(int width, int height) {
        this.width = width;
        this.height = height;
        this.nodes = new Node[width][height];
    }
    // Adds a node to the graph at the given coordinates with the given type.
    void addNode(int x, int y, int type) {
        Node newNode = new Node(x, y, type);
        nodes[x][y] = newNode; // Put the node in the 2D array
        // For node types other than 0 and 1  store in HashMap
        if (type != 0 && type != 1) {
            nodesByType.computeIfAbsent(type, k -> new ArrayList<>()).add(newNode);
        }
    }
    // Connect an edge between two nodes with given timeToGo
    void connectEdge(Node from, Node to, double timeToGo) {
        if(from.type != 1 && to.type != 1 ) {
            from.addNeighbor(to, timeToGo); // Add a directed edge
            to.addNeighbor(from, timeToGo); // Add reverse edge
        }
    }
    // Finds and returns a node at the given coordinates.
    Node findNode(int x, int y) {
        return nodes[x][y];
    }

    // Get all nodes in the given rectangular area boundaries
    public List<Node> getNodesInArea(int minX, int minY, int maxX, int maxY) {
        // List to store nodes in the area.
        List<Node> InAreaNodes = new ArrayList<>();

        // Adjust the boundaries to not exceed the graph lengths
        int startX = Math.max(0, minX);
        int startY = Math.max(0, minY);
        int endX = Math.min(width - 1, maxX);
        int endY = Math.min(height - 1, maxY);

        // Loop through the given area and add the nodes
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (nodes[x][y] != null) {
                    InAreaNodes.add(nodes[x][y]);
                }
            }
        }
        // Return the list of nodes in the area.
        return InAreaNodes;
    }

}
