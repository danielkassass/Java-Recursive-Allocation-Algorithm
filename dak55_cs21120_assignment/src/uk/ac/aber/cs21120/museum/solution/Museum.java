package uk.ac.aber.cs21120.museum.solution;

import uk.ac.aber.cs21120.museum.interfaces.IMuseum;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Museum implements IMuseum {
    //declaring 2 Maps to store rooms in the museum, aswell as my exhibitions that exists.

    Map<String, roomVertex> rooms = new HashMap<>();
    Map<String, exhibit> exhibits = new HashMap<>();
    private static final Set<String> directions = Set.of("north", "east", "south", "west");





    public Museum() {
        //Declaring room objects using my helper method
        roomVertex mainHall        = createRoom("Main Hall", 20,10);
        roomVertex secondHall      = createRoom("Second Hall", 20,10);
        roomVertex southRoom       = createRoom("South Room", 15,15);
        roomVertex northRoom       = createRoom("North Room", 10,10);
        roomVertex atrium          = createRoom("Atrium", 0,10);
        roomVertex longRoom        = createRoom("Long Room", 10,10);
        roomVertex exhibitionHall  = createRoom("Exhibition Hall", 20,20);
        roomVertex E1              = createRoom("E1", 5,5);
        roomVertex E2              = createRoom("E2", 4,4);
        roomVertex E3              = createRoom("E3", 5,5);
        //using another helper method I add the connections for the rooms in both directions.
        connectRooms(mainHall, northRoom,       3, "north", "south");
        connectRooms(mainHall, secondHall,      4, "west",  "east");
        connectRooms(mainHall, atrium,          4, "east",  "west");
        connectRooms(secondHall, southRoom,     4, "south", "north");
        connectRooms(northRoom, exhibitionHall, 2, "east",  "west");
        connectRooms(exhibitionHall, longRoom,  5, "south", "north");
        connectRooms(atrium, longRoom,          2, "east",  "west");
        connectRooms(atrium, E1,                2, "south", "north");
        connectRooms(longRoom, E2,              2, "south", "north");
        connectRooms(E1, E2,                    1, "east",  "west");
        connectRooms(E2, E3,                    1, "east",  "west");
    }

    //method used to declare room object and store the object in the rooms map
    private roomVertex createRoom(String name, int exhibitionSize, int maxVisitors) {
        roomVertex room = new roomVertex(name, exhibitionSize, maxVisitors);
        rooms.put(name, room);
        return room;
    }

    //method use to add an edge between 2 rooms in both of their edges map to ensure its bidirectional
    private void connectRooms(roomVertex a, roomVertex b, int width, String dirAB, String dirBA) {
        a.addEdge(b, width, dirAB);
        b.addEdge(a, width, dirBA);
    }

    @Override
    public Set<String> getRoomNames() {
        return new HashSet<>(rooms.keySet());
    }

    @Override
    public int getTotalMaxSize(String roomName) {
        if (rooms.containsKey(roomName)) {  //first check if the room exists,if it does return the size else throw an exception
            roomVertex room = rooms.get(roomName);
            return room.getExhibitionSize();
        } else throw new IllegalArgumentException("Room does not exist");
    }
    //method to return the exhibit names, This will be used for my testing to ensure that when i Return all the exhibits in the rooms using my getAllExhibits method it contains all the names.
    public Set<String> getExhibitNames() {
        return new HashSet<>(exhibits.keySet());
    }

    @Override
    public int getTotalMaxVisitors(String roomName) {
        //check if the room actually exists in the case it doesn't we throw an exception
        if (rooms.containsKey(roomName)) {
            roomVertex room = rooms.get(roomName);
            return room.getMaxVisitors();
        } else throw new IllegalArgumentException("Room does not exist");
    }

    @Override
    public int getConnection(String room1, String room2, String direction) {
        //i use 2 temp objects so i can use methods/operations on them
        roomVertex current = rooms.get(room1);
        roomVertex destination = rooms.get(room2);
        int width = 0;

        if (!rooms.containsKey(room2) || !rooms.containsKey(room1)) { //simple check to ensure that the room we are passing actually exists
            throw new IllegalArgumentException("Room does not exist");
        }

        if (current == null || destination == null) {
            return -1;
        }

        String directionLowerCase = direction.toLowerCase(); //set string to lowercase so that even if capital letters are in the string it still works
        edge edge = current.edges.get(directionLowerCase); // retrieve the edge from the first/ current room.

        if (!directions.contains(directionLowerCase)) {    //making sure that the direction is in our set of cardinal directions
            throw new IllegalArgumentException("Invalid direction");
        }
        //check if the direction we got is actually equal to the direction of the edge IntelliJ recommended Object.equals over == because its null safe
        //and if the destination we use as a parameter is actually the one in that direction
        if (edge != null && edge.getDestination().equals(destination)) {
            width = edge.getWidth();
        }

        return width;
    }

    @Override
    public void walk() {
        roomVertex current = rooms.get("Main Hall"); // start room
        System.out.println("Walk method active");
        boolean running = true; //this is the boolean ill be using as the condition for the loop
        System.out.println("Which direction would you like to explore?");
        System.out.println("North \nSouth \nEast \nWest");
        //I used a while loop so that the method runs at least once.
        do {
            Scanner scanner = new Scanner(System.in);
            String userChoice = null;


            System.out.println("You are in " + current.getName());
            System.out.println("Which direction would you like to explore?");

            if (scanner.hasNextLine()) {
                userChoice = scanner.nextLine();
                //make sure all the input is converted to lowercase so that the code is not case sensitive
                userChoice = userChoice.toLowerCase();
                if (userChoice.toLowerCase().equals("quit")) {
                    running = false;
                    System.out.println("Bye!");
                    break;
                }
            }
            //if exit is inputted exit the method
            if (userChoice.equals("exit")) {
                running = false;
                scanner.close();
                System.out.println("Exiting Walk method...");
            }
            //this block is to make sure that the input of the user is a cardinal direction and not some gibberish.
            if (!directions.contains(userChoice)) {
                    System.out.println("Invalid direction, please try again");
                    continue;
            }
            //first check that the edge is not equal to null then
            //retrieve the room at the other end of the edge and set it to the current room
            if (current.getEdge(userChoice) != null) {
                current = current.getEdge(userChoice).getDestination();
            } else {
                System.out.println("No room exists there silly!");
            }

        } while (running);
    }

    //big issue for me was getting used to using maps i keep iterating over them as if they are lists and when i do
    //i get a null error Cannot invoke "uk.ac.aber.cs21120.museum.solution.roomVertex.mermaidConnections()" because "room" is null

    /**
     * This method
     * @return
     */
    @Override
    public String getMermaid() {
        StringBuilder mermaidString = new StringBuilder();
        //making sure to add the appropriate diagram type
        mermaidString.append("graph TD\n");

        //add the ids of the rooms at the beginning of the code using a for loop, IntelliJ made this an enhanced for loop.
        for (roomVertex room : rooms.values()) {
            //since mermaid doesnt allow for spaces in the syntax we replace the spaces in the names with underscores.
            String fromId = room.getName().replace(" ", "_");
            mermaidString.append(fromId)
                         .append("[")
                         .append(room.getName())
                         .append("]\n");
        }
        //add the String representation of the edges for the nodes to the main mermaid string
        for (roomVertex room : rooms.values()) {
            mermaidString.append(room.toMermaidEdges()).append("\n");
        }

        System.out.println(mermaidString);
        return mermaidString.toString();
    }

    /**
     *
     * Depth first search helper method which is used by hasValidPath();
     * this method attempt to reach the destination from the current room
     * while ensuring that every door along the path has a width which is greater of equal to the door width of the starting room.
     *
     * @param current the room we begin the search at.
     * @param goal the room we want to reach
     * @param visited a set of room names to keep track of which rooms we have already visited
     * @param maxSize minimum door width required for the exhibit to pass
     * @return true if a valid path to the destination exists, otherwise return false
     */
    //this is my helper method which i will use to implement DFS for my hasValidPath method
    private boolean traverse(roomVertex current, roomVertex goal, Set<String> visited, int maxSize) {
        //make sure to not visit the same node twice to avoid cycles so we don't end up in an infinite loop and get a stack
        //overflow error
        if (visited.contains(current.getName())) {
            return false;
        }
        //check if we have visited the goal if yes then we return true.
        if (current.equals(goal)) {
            return true;
        }

        //we add the current room to the visited set
        visited.add(current.getName());
        //loop though the edges and for each loop we use a temp variable currentEdge.
        for (edge currentEdge : current.edges.values()) {
            //get the neighbor room from the current rooms edges map
            roomVertex neighbor = currentEdge.getDestination();
            //make sure that the door widths don't conflict
            if (currentEdge.getWidth() >= maxSize) {
                //we recursively call traverse again and if it returns true we return true
                //making the loop end.
                if (traverse(neighbor, goal, visited, maxSize)) {
                    return true;
                }
            }

        }
        //if the recursive call fails call ends and doesn't return true
        //return false meaning there is no valid path
        return false;
    }

    /**
     * this method makes use of the dfs (traverse) method to check if there exists a valid path between 2 rooms .
     * @param room1 room name of the room at which our path begins at
     * @param room2 name of the destination room
     * @param maxSize minimum width require along the doors
     * @return true if a valid path exists, otherwise return false.
     */
    @Override
    public boolean hasValidPath(String room1, String room2, int maxSize) {
        //boolean to be returned as the result.
        boolean hasValidPath;
        //retrieve the rooms we want
        roomVertex startingRoom = rooms.get(room1);
        roomVertex destinationRoom = rooms.get(room2);
        //simple null check
        if (startingRoom == null || destinationRoom == null) {
            return false;
        }
        //check if we are already in the desired room to go to
        if (startingRoom.equals(destinationRoom)) {
            return true;
        }
        //This is where we store the already visited nodes
        Set<String> visited = new HashSet<>();
        //this is where we actually call the traversal method and set my result boolean to the value it returns
        hasValidPath = traverse(startingRoom, destinationRoom, visited, maxSize);

        return hasValidPath;

    }

    /**
     * Method which uses scanner object to read the csv file which contains all the exhibit data
     * Once the data is retrieved it is used to declare and initialize new exhibit objects which are then stored inside the Map of exhibits for the museum class.
     */
    public void generateExhibits () {
        //Declare the scanner object as null and the object is only set to a scanner if we manage to find the file.
        //the reason I had to do this was because I cannot change the signature of the generateExhibs method to throw
        //and exception since then i will also be forced to change the allocateExhibits signature aswell for it to throw
        //an exception which would violate the rules of an interface
        Scanner scanner = null;

        try{
            File file = new File("src/uk/ac/aber/cs21120/museum/data/exhibits.csv");
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        //array list to store the names for them to be using later in the method as the keys for the exhibit map.
        ArrayList<String> exhibitsNamesList = new ArrayList<>();

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //since it's a csv file its seperated by commas so we use that as the delimiter to separate the values.

                String[] row = line.split(","); // store string in a row array

                String exhibitName = row[0];
                String exhibitionTheme = row[1];
                //convert the number from string form to an int so it can be stored as an int.
                int exhibitionSize = Integer.parseInt(row[2]);
                int exhibitionWidth = Integer.parseInt(row[3]);

                exhibit exhibit = new exhibit(exhibitName, exhibitionTheme, exhibitionSize, exhibitionWidth);
                //adding the name to the array list
                exhibitsNamesList.add(exhibitName);
                exhibits.put(exhibitName, exhibit);
            }
            //code used for debugging and making sure that the exhibits are generated correctly.
//        for (int i = 0; i < exhibits.size() ; i++) {
//            String currentName = exhibitsNamesList.get(i);
//            System.out.println(i+1 + ": " + exhibits.get(currentName).toString() + "\n");
//        }
    }

    /**
     * Checks whether an exhibit has already been allocated to a room.
     *
     * @param exhibit exhibit to be considered for placement.
     * @return true if exhibit is already placed inside another room, otherwise returns false
     */
    //helper method to make sure that the exhibit isnt already allocated inside any of the rooms.
    public boolean exhibitAlreadyAllocated(exhibit exhibit) {
        //loop through all the rooms in the museum and check if in their exhibit map weather the exhibit we are trying to currently allocate as already been
        //allocated to a different room.
        for (roomVertex room : rooms.values()) {
            if (room.exhibits != null && room.exhibits.containsKey(exhibit.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether placing a given exhibit into a given room is legal.
     * This validates theme consistency, visitor capacity, exhibition size,
     * door width constraints, and prevents allocation to the Atrium.
     *
     * @param exhibit exhibit being considered for placement
     * @param room target room for the exhibit
     * @return true if all constraints are satisfied
     */

    //helper method to make sure that the allocation passes all the constraints.
    public boolean allocationIsValid (exhibit exhibit, roomVertex room) {

        if(!exhibits.containsKey(exhibit.getName()) || !rooms.containsKey(room.getName())) {
            throw new IllegalArgumentException("Either the room or the exhibits do not exist");
        }

        roomVertex currentRoom = rooms.get(room.getName());
        int totalSize = 0;
        int totalVisitors = 0;
        //check that the exhibit hasn't already been allocated.
        if (exhibitAlreadyAllocated(exhibit)) {
            return false;
        }

        //make sure to not allow allocation inside the atrium
        if (room.getName().equals("Atrium")) {
            return false;
        }

        //make sure that there is a valid path from the Main Hall to the currentRoom with the door widths not being less than the size of the exhibit.
        if(!hasValidPath("Main Hall", currentRoom.getName(),exhibit.getExhibitionSize())) {
            return false;
        }

        //in the scenario that the room already has exhibits allocated to it we check if their themes are the same.
        if (!currentRoom.exhibits.isEmpty()) {
            // pick any one exhibit from the map
            exhibit selectedExhibit = currentRoom.exhibits.values().iterator().next(); //i
            // check if the themes are the same.
            if (!room.getExhibitTheme().equals(exhibit.getExhibitionTheme())) {
                return false;
            }
        } else {
            room.setExhibitTheme(exhibit.getExhibitionTheme());
        }

        if(currentRoom.exhibits.isEmpty()) {
            totalSize = exhibit.getExhibitionSize();
            totalVisitors = exhibit.getVisitors();
        } else {
            totalSize = currentRoom.exhibits.values().stream().mapToInt(e -> e.getExhibitionSize()).sum()
                    + exhibit.getExhibitionSize();

            totalVisitors = currentRoom.exhibits.values().stream().mapToInt(e -> e.getVisitors()).sum()
                    + exhibit.getVisitors();
        }

        //make sure the sum of max visitors at a time doesnt exceed the capacity of the current room
        if (totalSize > currentRoom.getExhibitionSize() || totalVisitors > currentRoom.getMaxVisitors()) {
            return false;
        }

        return true;
    }
    /**
     * the method to allocate all exhibits to rooms using a recursive
     * backtracking algorithm. Exhibits and rooms are assumed to be
     * sorted largest-first so that the search space is reduced.
     *
     * @param exhibitList ordered list of exhibits still to be allocated
     * @param roomList ordered list of rooms to attempt allocations in
     * @return true if all exhibits can be allocated, false otherwise
     */

    //helper method for allocating the exhibits which contains the logic used to recursively attempt to allocate the exhibits to the rooms.
    public boolean recursiveAllocator ( List<exhibit> exhibitList , List<roomVertex> roomList ) {
        //List<exhibit> remainingExhibits = new ArrayList<>(exhibitList); old position of the list which caused the error
        //check if the list of unallocated exhibits is empty, if it is that means that the allocation has been successful
        if (exhibitList.isEmpty()) {
            return true;
        }
        //get the first element in the list
        exhibit currentExhibit = exhibitList.getFirst();
        //loop through all the rooms.
        for (roomVertex currentRoom : roomList) {
            //check if the allocation is valid, if it is add the exhibit to the room and remove the exhibit from the list
            if (allocationIsValid(currentExhibit, currentRoom)) {
                currentRoom.exhibits.put(currentExhibit.getName(), currentExhibit);
                List<exhibit> remainingExhibits = new ArrayList<>(exhibitList);//new position
                //create a new list for the recursion algorithm to use, this will store the exhibits waiting to still be allocated
                remainingExhibits.remove(0);

                //if the allocator returns true thus the allocation has been successful meaning there is no need for us to keep looping.
                if (recursiveAllocator(remainingExhibits,roomList)) {
                    return true;
                }

                //this is logic to assist the back tracking basically, if the allocation fails we remove the exhibit from the room.
                currentRoom.exhibits.remove(currentExhibit.getName());

                // if that was the only exhibit in the room, clear the room’s theme
                if (currentRoom.exhibits.isEmpty()) {
                    currentRoom.setExhibitTheme(null);


                }
            }
        }
        return false;
    }

    /**
     * will allocate all the exhibits to rooms, returning false if it fails to do that. With the exhibits and rooms you have been given it should return true.
     * @return is the allocations have been completed successfully return true, otherwise return false.
     */
    @Override
    public boolean allocateExhibits() {
        //The idea is to sort the exhibits and rooms based on side so the allocator would try and fit the biggest exhibits in the biggest rooms first.
        generateExhibits();
        //declaring a list of exhibits.
        List<exhibit> exhibitList = new ArrayList<>(exhibits.values());
        //sort the list of exhibits largest first.
        //intelliJ AI suggested a change from
        //Collections.sort(exhibitList, (a, b) -> Integer.compare(b.getExhibitionSize(), a.getExhibitionSize()));
        exhibitList.sort((a, b) -> Integer.compare(b.getExhibitionSize(), a.getExhibitionSize()));
        //declaring a list of rooms
        List<roomVertex> roomList = new ArrayList<>(rooms.values());
        //sort the rooms largest exhibition size first.
        //intelliJ AI suggested a change from
        //Collections.sort(roomList, (a, b) -> Integer.compare(b.getExhibitionSize(), a.getExhibitionSize()));
        //to using List sort
        roomList.sort((a, b) -> Integer.compare(b.getExhibitionSize(), a.getExhibitionSize()));
        //using the helper method same as the DFS logic if the allocator returns true, that means it's been successful
        // in allocation thus if the condition in the if statement is met we return true.
        if (recursiveAllocator(exhibitList, roomList)) {
            return true;
        } else return false;
    }

    /**
     * The method should return a string listing the exhibits allocated to a given room. It should throw an IllegalArgument if the room does not exist.
     * @param roomName room which we will check while exhibits have been allocated to it.
     * @return a comma-separated list of exhibit names in string form.
     */
    @Override
    public String getAllocation(String roomName) {
        StringBuilder allocation = new StringBuilder();


        //check if the room parameter exists.
        if (!rooms.containsKey(roomName)) {
            throw new IllegalArgumentException("Room doesn't exist");
        } else {
            allocation.append(roomName).append(": ");
        }

        roomVertex currentRoom = rooms.get(roomName);
        // case if the room has no exhibits, in theory only the atrium should be empty.
        if (currentRoom.exhibits.isEmpty()) {
            return roomName + ": is Empty";
        }

        //loop through the exhibits and append their names separated by commas
        int counter = 0;
        //counter variable used to help with formating the string by adding commas at appropriate places.
        //basically it would ensure that commas are only to be place if the room has more than one exhibit.
        for (exhibit exhibit : currentRoom.exhibits.values()) {
            //if statement to make sure to not add a comma at the beginning of the string
            if (counter > 0) {
                allocation.append(",");
            }
            allocation.append(exhibit.getName());
            counter++;
        }

        return allocation.toString();

    }




}
