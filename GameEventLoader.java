import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

/**
 * Reads an event text file and creates event objects
 * The objects are divided into Events and TimedEvents based on the info of the line in the text file
 * Note: JSON was meant to be used, but I could not figure out how to get the dependencies to work in time, so a text file is used instead (still works as intended)
 */

public class GameEventLoader {
    public GameEvent readFile (String file) {
        List<GameEvent> eventList = new ArrayList<>();
        int counter = 0;
        try {
            File filename = new File(file);
            Scanner reader = new Scanner(filename);
            
            while (reader.hasNextLine()) {
                counter = counter + 1;
                String data = reader.nextLine();
                //split each line by a ; and put them into an array
                String[] sections = data.split(";");
                String title = sections[0];
                String desc = sections[1];
                String timer = sections[2];
                //if the timer part of the line is 0, make a game event, if its greater than 0, make a timed event
                //Add the objects to the list of events
                if (Integer.parseInt(timer) == 0) {
                    GameEvent event = new GameEvent(title, desc);
                    eventList.add(event);
                } else if (Integer.parseInt(timer) > 0) {
                    TimedEvent timedEvent = new TimedEvent(title, desc, Integer.parseInt(timer));
                    eventList.add(timedEvent);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return randomEvent(eventList, counter);

    }

    //takes the list of events and the number of lines and pick a random one
    public GameEvent randomEvent (List<GameEvent> eventList, int numOfEvents){
        Random rand = new Random();
        int randomNum = rand.nextInt(numOfEvents);
        return eventList.get(randomNum);
    }
}