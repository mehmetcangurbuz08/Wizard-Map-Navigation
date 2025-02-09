# Wizard Navigation System

## Overview
The Wizard Navigation System is a graph-based pathfinding application designed to find optimal routes on a dynamic map. The project utilizes classic shortest pathfinding algorithms (Dijkstra's Algorithm and A* Search) and incorporates a dynamic optimization mechanism, referred to as the "Wizard Helper," which adjusts the path based on special conditions encountered during traversal.

---

## Features
- **Graph Representation:** The map is modeled as a weighted graph using nodes (locations) and edges (paths with associated costs or distances).
- **Dynamic Pathfinding:** Automatically switches between Dijkstra and A* algorithms depending on the map's complexity and requirements.
- **Wizard Helper Mechanism:** Optimizes paths dynamically when certain high-cost nodes or obstacles are encountered.
- **Efficient Data Structures:** Custom implementations of priority queues, hash maps, and linked lists ensure optimized memory usage and fast pathfinding on large maps.

---

## Project Structure

```
WizardMap/
|-- Edge.java                   # Represents edges between nodes with weights
|-- Graph.java                  # Graph data structure implementation
|-- Main.java                   # Entry point of the application
|-- Mission.java                # Manages user-defined missions on the map
|-- MyHashMap.java              # Custom implementation of a hash map
|-- MyHashSet.java              # Custom implementation of a hash set
|-- MyLinkedList.java           # Custom linked list implementation
|-- MyPriorityQueue.java        # Custom priority queue implementation
|-- Node.java                   # Represents nodes within the graph
|-- PathFinderForWizard.java    # Handles wizard-assisted pathfinding logic
|-- ShortestPathFinder.java     # Implements core shortest path algorithms
```

---

## How It Works

1. **Graph Initialization:**
    - The graph is built using **Node** and **Edge** objects, where each node represents a location and each edge stores the distance or cost between two locations.

2. **Pathfinding:**
    - The system automatically selects the optimal algorithm:
        - **Dijkstra's Algorithm** is used for static, uniformly weighted graphs.
    

3. **Wizard Helper Mechanism:**
    - When certain conditions are met (e.g., encountering high-cost nodes or obstacles), the wizard helper dynamically adjusts the edge weights to find a better path.

4. **Optimized Data Structures:**
    - The project leverages custom data structures, including a priority queue (**MyPriorityQueue**) for efficient pathfinding, and hash maps for quick access to node connections.

---

## Algorithms Used
- **Dijkstra's Algorithm:** Finds the shortest path by visiting nodes with the smallest accumulated weight.
- **A* Search Algorithm:** Incorporates heuristics to speed up pathfinding by estimating the cost to reach the target.
- **Dynamic Optimization:** Adjusts weights dynamically based on runtime conditions using helper functions.

---

## Example Workflow
1. The user specifies a start and end point on the map.
2. The system builds the graph and selects the optimal pathfinding algorithm.
3. If any obstacles or high-cost nodes are detected, the wizard mechanism optimizes the path.
4. The shortest path is returned to the user.

---

## Installation and Usage

1. Clone the repository:
    ```bash
    git clone <repository-url>
    cd WizardMap
    ```

2. Compile the Java files:
    ```bash
    javac *.java
    ```

3. Run the application:
    ```bash
    java Main
    ```

---

## Future Improvements
- Expand the wizard mechanism to handle more complex scenarios.
- Integrate additional heuristics for A* search to improve efficiency on larger maps.
- Add a graphical user interface (GUI) to visualize the pathfinding process.
