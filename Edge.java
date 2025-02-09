// This class Create an edge in a graph connects two nodes with a given travel time.
class Edge {
    Node to; // The target node
    double timeForEdge; // The time needed to go target

    // Constructor for an edge
    Edge(Node to, double travelTime) {
        this.to = to; // Set the target node.
        this.timeForEdge = travelTime; // Set the travel time for this edge.
    }
}