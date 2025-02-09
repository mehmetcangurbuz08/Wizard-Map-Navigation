import java.util.ArrayList;
import java.util.Objects;
// Create a class to hold node information
class Node {
    int x, y, type; // Coordinates of the nodes and type
    ArrayList<Edge> neighbors; //Arraylist of edges of the node
    boolean passable; // Create a boolean for passability

    // Constructor for the node class
    Node(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.passable = (type != 1); // Initially passable if type is 0

        this.neighbors = new ArrayList<>();
    }
    // Adding an edge to the given node
    void addNeighbor(Node to, double travelTime) {
        neighbors.add(new Edge(to, travelTime));
    }
    // Checking if the node is pssable or not
    void updatePassability() {
        this.passable = this.type < 1; // Update passability based on type
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
