import java.util.ArrayList;

// This class holds a mission which includes a starting point, a target point, a radius and optional wizard choices.
public class Mission {
    int startNodeX; // X-coordinate of the starting node
    int startNodeY; // Y-coordinate of the starting node
    int targetNodeX; // X-coordinate of the target node
    int targetNodeY; // Y-coordinate of the target node
    int radius;  //Radius for the visible area
    ArrayList<Integer> WizardChoices; // List of choices provided by the wizard

    // Constructor for a mission without wizard
    public Mission(int startNodeX, int startNodeY, int targetNodeX, int targetNodeY, int radius) {
        this.startNodeX = startNodeX;
        this.startNodeY = startNodeY;
        this.targetNodeX = targetNodeX;
        this.targetNodeY = targetNodeY;
        this.radius = radius;
        this.WizardChoices = null; // No wizard choices for this mission.
    }

    // Constructor for a mission with wizard
    public Mission(int startNodeX, int startNodeY, int targetNodeX, int targetNodeY, int radius, ArrayList<Integer> options) {
        this.startNodeX = startNodeX;
        this.startNodeY = startNodeY;
        this.targetNodeX = targetNodeX;
        this.targetNodeY = targetNodeY;
        this.radius = radius;
        this.WizardChoices = options; // Set the list of wizard choices for this mission.
    }
}
