package uk.ac.aber.cs21120.museum.solution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Task4Tests {

    private Museum museum;
    //Making sure to create a new museum before each test is run.
    @BeforeEach
    void setUp() {
        museum = new Museum();
    }

    //this is a very simple test to check the details
    //this test case see in using the normal case exhibits if the allocation is successful
    @Test
    void allocationBehaviourIsCorrect1() {
        //making sure that the allocateExhibits returns true
        boolean result = museum.allocateExhibits();
        Assertions.assertTrue(result, "Museum allocation should be successful");
    }


    //this test is similar to the one before but we alter the exhibits values in a way to make sure it will fail
    //this test makes sure that out constraints are working correctly
    @Test
    void allocationBehaviourIsCorrect2() {
        //making sure that the allocateExhibits returns true
        museum.allocateExhibits();
        museum.exhibits.get("The Utah Teapot").setExhibitionSize(1000);

        boolean result = museum.allocateExhibits();

        Assertions.assertFalse(result, "Museum allocation should be unsuccessful");
    }


    void printOutAllocations() {
        StringBuilder output = new StringBuilder();


        List<String> rooms = new ArrayList<>(museum.getRoomNames());

        for(String room : rooms) {
            output.append(museum.getAllocation(room)).append("\n");
        }

        System.out.println(output.toString());
    }

    public static void main () {
        Task4Tests tests = new Task4Tests();
        tests.museum = new Museum();
        tests.museum.allocateExhibits();
        tests.printOutAllocations();

    }
}
