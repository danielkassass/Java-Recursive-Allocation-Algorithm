package uk.ac.aber.cs21120.museum.solution;

public class edge {
    private int width;
    private roomVertex destination;
    public String direction;

    public edge(roomVertex destination, int width, String direction) {
        this.destination = destination;
        this.width = width;
        this.direction = direction; // Store the direction
    }

    public int getWidth() {
        return this.width;
    }

    public roomVertex getDestination() {
        return destination;
    }

    public String getDirection() {
        return direction;
    }

    public String getEdgeName() {
        return destination.getName();
    }
}