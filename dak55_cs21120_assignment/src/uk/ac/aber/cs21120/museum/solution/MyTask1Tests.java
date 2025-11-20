package uk.ac.aber.cs21120.museum.solution;

// Make sure you have JUnit 5 added to your project
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is a test class for some of the methods ill be implementing, I learnt using JUNIT from youtube
 * More specifically this video helped me learn how to create tests with junit was a video made by Coding with John, https://youtu.be/vZm0lHciFsQ?si=6zWsrzJE-nVED_SI .
 */
public class MyTask1Tests {

    private Museum museum;
    //Making sure to create a new museum before each test is run.
    @BeforeEach
    void setUp() {
        museum = new Museum();
    }
    //this is a very simple test to check the details
    @Test
    void testRoomDetailsAreCorrect() {

        // case 1
        roomVertex mainHall = museum.rooms.get("Main Hall");
        //check if main hall is not null
        Assertions.assertNotNull(mainHall, "Main Hall should not be null");
        //checking the details of the atrium
        Assertions.assertEquals(20, mainHall.getExhibitionSize(), "Main Hall exhibition size is wrong");
        Assertions.assertEquals(10, mainHall.getMaxVisitors(), "Main Hall visitor count is wrong");

        // case 2
        roomVertex atrium = museum.rooms.get("Atrium");
        //check if atrium is not null
        Assertions.assertNotNull(atrium, "Atrium should not be null");
        //checking the details of the atrium
        Assertions.assertEquals(0, atrium.getExhibitionSize(), "Atrium exhibition size is wrong");
        Assertions.assertEquals(10, atrium.getMaxVisitors(), "Atrium visitor count is wrong");

        // case 3
        roomVertex southRoom = museum.rooms.get("South Room");
        Assertions.assertNotNull(southRoom, "South Room should not be null");

        Assertions.assertEquals(15,southRoom.getExhibitionSize(), "South Room exhibition size is wrong");
        Assertions.assertEquals(15,southRoom.getMaxVisitors(), "South Room visitor count is wrong");

        // case 4
        roomVertex e1 = museum.rooms.get("E1");
        Assertions.assertNotNull(e1, "North Room should not be null");

        Assertions.assertEquals(5,e1.getExhibitionSize(), "E1 exhibition size is wrong");
        Assertions.assertEquals(5, e1.getMaxVisitors(), "E1 visitor count is wrong");

    }




}