package uk.ac.aber.cs21120.museum.solution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class roomVertex {

    //Fields
    private final String name;
    private final int exhibitionSize;
    private final int maxVisitors;
    private String exhibitTheme;

    //Rooms that the current room leads to
    HashMap<String, edge> edges = new HashMap<>();
    HashMap<String, exhibit> exhibits = new HashMap<>();


    public roomVertex(String name, int exhibitionSize, int maxVisitors) {
        this.name = name;
        this.exhibitionSize = exhibitionSize;
        this.maxVisitors = maxVisitors;
        this.edges = new HashMap<>();
        this.exhibits = new HashMap<>();
    }

    public void addEdge(roomVertex endVertex, int width, String direction) {
        //we create a new edge with this specific fields
        edge newEdge = new edge(endVertex, width, direction);

        //we store it in the edges map for the vertex
        this.edges.put(direction, newEdge);
    }

    public void setExhibitTheme(String exhibitTheme) {
        this.exhibitTheme = exhibitTheme;
    }

    public String getExhibitTheme() {
        return exhibitTheme;
    }

    public String getName() {
        return name;
    }

    public int getMaxVisitors() {
        return maxVisitors;
    }

    public int getExhibitionSize() {
        return exhibitionSize;
    }

    public edge getEdge(String direction) {
        return this.edges.get(direction.toLowerCase());
    }

    public String getExhibits() {
        String exhibitString = "";
        return  exhibitString;
    }

    public String toMermaidEdges() {
        StringBuilder connections = new StringBuilder();
        String currentRooomId = this.name.replace(" ", "_"); //replace the spaces in the name with underscores so Main Hall --> MainHall
        //loop over all the rooms and get the values of the edge, then append to the string the correct format we use to display it in mermaid
        for (edge edge : this.edges.values()) {
            roomVertex destination = edge.getDestination();

            if (destination == null) {
                continue;
            }
            String destinationRoomID = destination.name.replace(" ", "_");
            // this basically checks if the name of the current name is alphabetically greater than the destination, this is to avoid dupes in the graph.
            // this ensures that connections are only appended to the mermaid string once, the good thing about compareTo, is that i checks the entire string, so even if 2 rooms start with the same letters
            // it would still correctly assess if we have to skip it or not
            // I got the replace method off of W3Schools.
            if (currentRooomId.compareTo(destinationRoomID) > 0) {
                continue;
            }

            //format the string in correct syntax.

            connections.append("    ")
                    .append(currentRooomId)
                    .append(" ---|")
                    .append(edge.getWidth())
                    .append("| ")
                    .append(destinationRoomID)
                    .append("\n");
        }
        return connections.toString();
    }
}
