import java.util.Random;

/**
* Child of GameEvent, overrides the getReward method and adds the timer variable
**/
public class TimedEvent extends GameEvent{
    private int timer;

    public TimedEvent(String title, String description, int timer) {
        super(title, description);
        this.timer = timer;
    }

    public int getTimer() {
        return timer;
    }

    //Unlike normal events, timed events give a random number of coins up to 50. So the method must be overridden.
    @Override
    public String getEventReward() {
        Random rand = new Random();
        int randomNum = rand.nextInt(51);
        String reward = String.valueOf(randomNum);
        return "Reward: " + reward + " gold";
    }
}