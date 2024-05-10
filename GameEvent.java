public class GameEvent {
    private String title;
    private String description;

    public GameEvent(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getEventReward() {
        return "Reward: 5 gold";
    }
}