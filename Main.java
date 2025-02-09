/**
 * This program is an application that helps fulfill the wishes of the wizard in the story in the most optimized way. .
 * @author Mehmet Can Gürbüz, Student ID: 2022400177
 * @since Date: 19.12.2024
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    private static ArrayList<Integer> usedWizardChoices = new ArrayList<>(); //// An arraylist to keep used options.

    public static void main(String[] args) {
        // Arguments for Input file names
        String nodesFile = args[0];
        String edgesFile =args[1];
        String objFile = args[2];
        String outputFile = args[3];
        try {
            // Parse the nodesFile to create a graph
            Graph graph = parseLandFile(nodesFile);
            // Parse the edgesFile
            parseTravelTimeFile(graph, edgesFile);
            // Parse the objFile to take missions
            ArrayList<Mission> missions = parseObjFile(objFile);

            // Create a string builder for output
            StringBuilder output = new StringBuilder();
            //Crete a shortestPathFinder object for path calculations
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder();
            // Create a PathFinderForWizard for calculate the best time for wizard options
            PathFinderForWizard pathFinderForWizard = new PathFinderForWizard();
            // Create a variable for indexing mission's targets
            int objectiveIndex = 1;
            // Loop all the missions
            for (int i = 0; i < missions.size(); i++) {
                // Retrieve the current mission
                Mission mission = missions.get(i);
                //Hold the next objectives for wizard choice
                Node nextObjective;
                if (i < missions.size() - 1) {
                    // If there are more missions find the node for the next mission's objective.
                    nextObjective = graph.findNode(missions.get(i + 1).targetNodeX, missions.get(i + 1).targetNodeY);
                } else {
                    // If this is the last mission, there is no next objective
                    nextObjective = null;
                }
                // Process the current mission with using processMission function
                goToNextObjective(graph, mission, shortestPathFinder, output, objectiveIndex, nextObjective, pathFinderForWizard);

                // Increment the objective index for the next mission to use in WizardChoice function
                objectiveIndex++;
            }

            // Write all output to the output file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                writer.write(output.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // This method parses the nodes file and  create a 2D graph according to the file.
    private static Graph parseLandFile(String filename) throws IOException {
        // Create a graph object
        Graph graph;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Read the first line to take the lengths of the graph
            String[] lengths = reader.readLine().split(" ");
            int width = Integer.parseInt(lengths[0]);
            int height = Integer.parseInt(lengths[1]);

            // Initialize the graph with given lengths
            graph = new Graph(width, height);

            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into parts
                String[] parts = line.split(" ");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int type = Integer.parseInt(parts[2]);

                // Add the node to the graph with taken info's
                graph.addNode(x, y, type);
            }
        }
        // Return the created graph
        return graph;
    }

    // This method parses the edges file and adds edges to the graph according to given travel times.
    private static void parseTravelTimeFile(Graph graph, String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                // Split the line into two parts
                String[] parts = line.split(" ");

                // Parse the edge description
                String[] nodes = parts[0].split(",");
                // Parse the travel time
                double travelTime = Double.parseDouble(parts[1]);

                // Split the first node's coordinates
                String[] node1XY = nodes[0].split("-");
                // Split the second node's coordinates
                String[] node2XY = nodes[1].split("-");

                int node1X = Integer.parseInt(node1XY[0]);
                int node1Y = Integer.parseInt(node1XY[1]);
                int node2X = Integer.parseInt(node2XY[0]);
                int node2Y = Integer.parseInt(node2XY[1]);

                // Find the Node objects in the graph
                Node node1 = graph.findNode(node1X, node1Y);
                Node node2 = graph.findNode(node2X, node2Y);

                // Add an edge between node1 and node2 with the travel time
                graph.connectEdge(node1, node2, travelTime);
            }
        }
    }

    // This method parses the Obj file and gives the list of Mission objects
    private static ArrayList<Mission> parseObjFile(String filename) throws IOException {
        // Create an ArrayList to store missions.
        ArrayList<Mission> missions = new ArrayList<>();
        // Read the first line to take the radius value
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String radiusLine = reader.readLine();
            int radius = Integer.parseInt(radiusLine.trim());
            // Read the second line to take the start coordinates.
            String startLine = reader.readLine();
            // Split into x and y coordinates.
            String[] startCoordinates = startLine.trim().split(" ");
            int startNodeX = Integer.parseInt(startCoordinates[0]);
            int startNodeY = Integer.parseInt(startCoordinates[1]);

            String line;
            // Read the remaining lines which are missions
            while ((line = reader.readLine()) != null) {
                // Split the line into parts for objective coordinates and wizard's mission options.
                String[] parts = line.trim().split(" ");

                int NextNodeX = Integer.parseInt(parts[0]);
                int NextNodeY = Integer.parseInt(parts[1]);

                // If there are WizardChoices parse them.
                if (parts.length > 2) {
                    ArrayList<Integer> WizardChoices = new ArrayList<>();
                    for (int i = 2; i < parts.length; i++) {
                        WizardChoices.add(Integer.parseInt(parts[i]));
                    }
                    // Create a new Mission object with WizardChoices and add it to the list
                    missions.add(new Mission(startNodeX, startNodeY, NextNodeX, NextNodeY, radius, WizardChoices));
                } else {
                    // Create a new Mission object without WizardChoices and add it to the list.
                    missions.add(new Mission(startNodeX, startNodeY, NextNodeX, NextNodeY, radius));
                }

                // Update the starting coordinates for the next mission
                startNodeX = NextNodeX;
                startNodeY = NextNodeY;
            }
        }
        // Return the list of parsed missions
        return missions;
    }
    // This method moves towards the next objective in the mission
    private static void goToNextObjective(Graph graph, Mission mission, ShortestPathFinder shortestPathFinder, StringBuilder output, int objectiveIndex, Node nextTarget, PathFinderForWizard pathFinderForWizard) throws IOException {
        // Find the start and target nodes in graph for the mission.
        Node startNode = graph.findNode(mission.startNodeX, mission.startNodeY);
        Node targetNode = graph.findNode(mission.targetNodeX, mission.targetNodeY);
        // Create a node object to hold currentNode nodes in the path
        Node currentNode = startNode;

        // Refresh nodes within the mission's radius according to their passability
        refreshInRadius(graph, currentNode, mission.radius);

        // Loop until the current node equals to the target node.
        while (!currentNode.equals(targetNode)) {

            // Find the shortest path from the current node to the target node
            MyLinkedList<Node> path = shortestPathFinder.findShortestWithDijkstra(graph, currentNode, targetNode);
            // Boolean to skip the first node in the path
            boolean isFirst = true;
            for (Node nextStep : path) {
                if (isFirst) {
                    isFirst = false;// Skip the first node
                    continue;
                }

                // Add movement info to the output.
                output.append("Moving to ").append(nextStep.x).append("-").append(nextStep.y).append("\n");

                currentNode = nextStep; // Update the current node to the next step

                refreshInRadius(graph, currentNode, mission.radius); // Refresh nodes within the radius of the new current node

                // Check the path for nodes impassibility.
                boolean IsImpassablePath = false;
                for (Node node : path) {
                    if (!node.passable) { // If any node is impassable, mark the boolean.
                        IsImpassablePath = true;
                        break;
                    }
                }
                // If the path is impassable, give a message and break the loop to recalculate the path
                if (IsImpassablePath) {
                    output.append("Path is impassable!\n");
                    break;
                }
            }
        }

        // Add a message that the objective has been reached.
        output.append("Objective ").append(objectiveIndex).append(" reached!\n");

        // If the mission has WizardChoices, find the best choice and log it.
        if (mission.WizardChoices != null) {
            int bestOption = WizardChoice(graph, currentNode, mission.WizardChoices, nextTarget, pathFinderForWizard);
            output.append("Number ").append(bestOption).append(" is chosen!\n");
        }

    }

    // This method refresh the passability status of nodes within a gives radius around the current node.
    private static void refreshInRadius(Graph graph, Node current, int radius) {
        // Calculate the square of the radıus
        int rSquared = radius * radius;

        // Calculate the boundary of the area constrained by the graph's lengths
        int minX = Math.max(0, current.x - radius); // Create the minimum x is not less than 0.
        int minY = Math.max(0, current.y - radius); // Create the minimum y is not less than 0.
        int maxX = Math.min(graph.width - 1, current.x + radius); // Create the maximum x is within the graph's width.
        int maxY = Math.min(graph.height - 1, current.y + radius); // Create the maximum y is within the graph's height.

        // Retrieve all nodes in the rectangular area defined by the boundaries.
        List<Node> nodesInCircle = graph.getNodesInArea(minX, minY, maxX, maxY);

        // Iterate through all nodes in the area
        for (Node node : nodesInCircle) {
            // Calculate the squared distances
            int x_Difference = node.x - current.x;
            int y_Difference = node.y - current.y;

            // Check if the node in the circular area by the given radius.
            if (Math.pow(x_Difference,2 ) + Math.pow(y_Difference,2 ) <= rSquared ) {
                // Update node's passability status.
                node.updatePassability();
            }
        }
    }


    // This method selects the best wizardChoice according to the shortest path time to the next objective.
    private static int WizardChoice(Graph graph, Node current, ArrayList<Integer> WizardChoice, Node nextTarget, PathFinderForWizard pathFinderForWizard) {
        // Initialize the best option
        int bestChoice = -1;
        // Initialize the best time to the maximum value.
        double bestTime = Double.MAX_VALUE;
        //Loop each wizard choice
        for (int choice : WizardChoice) {
            // Skip this choice if it has already been used
            if (usedWizardChoices.contains(choice)) {
                continue;
            }
            // Calculate the shortest path time for this choice using the wizard pathfinder.
            double time = pathFinderForWizard.getShortestPathTime(graph, current, nextTarget, choice);

            // Update the best choice if the current is better
            if (time < bestTime) {
                bestTime = time;
                bestChoice = choice;
            }

        }

        // Permanently make the nodes with the best choice passable
        if (bestChoice != -1) {
            // Add the bestChoice to the list of used wizard choices.
            usedWizardChoices.add(bestChoice);
            // Get all nodes of the best option's type from the graph's hashmap according to type.
            List<Node> bestNodes = graph.nodesByType.getOrDefault(bestChoice, Collections.emptyList());
            for (Node node : bestNodes) {
                node.type = 0; // Make nodes type 0
                node.updatePassability();  // Refresh passability
            }
        }
        // Return the best Choice selected
        return bestChoice;
    }
}
