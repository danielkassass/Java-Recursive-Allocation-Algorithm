package uk.ac.aber.cs21120.museum.solution;

import java.io.FileNotFoundException;

public class testmain {
    public static void main(String[] args) throws FileNotFoundException {
        Museum museum = new Museum();
        museum.getMermaid();
        boolean result = museum.allocateExhibits();
        System.out.println(result);
        museum.getAllocation("Main Hall");
    }
}
