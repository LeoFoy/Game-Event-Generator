import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.util.Duration;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
* Creates the main application gui
* sets labels to reflect the generated event objects
* Operates the timer feature
**/

public class Main extends Application{
    private Timeline timeline;
    @Override
    public void start(Stage primaryStage) throws IOException {
        //setup labels
        Label titleLabel = new Label("");
        titleLabel.setStyle("-fx-font-size: 20px;");
        Label descLabel = new Label("");
        descLabel.setStyle("-fx-font-size: 14px;");
        descLabel.setWrapText(true);
        Label timer = new Label("");
        timer.setStyle("-fx-font-size: 0px;");
        Label reward = new Label("");
        reward.setStyle("-fx-font-size: 14px;");

        //create buttons
        Button button = new Button("Generate Event");
        VBox.setMargin(button, new javafx.geometry.Insets(0, 0, 50, 0));

        //create VBox
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(button, titleLabel, descLabel, timer, reward);
        vbox.setPrefSize(600, 500);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setStyle("-fx-padding: 10;");

        //set action for the event generator button
        button.setOnAction(e -> {
                timer.setText("");
                GameEvent event = genEvent();
                titleLabel.setText(event.getTitle());
                descLabel.setText(event.getDescription());
                reward.setText(event.getEventReward());

                //if the object is a Timed event, sets up timer in the display
                if (event instanceof TimedEvent) {
                    //create stop button
                    timer.setStyle("-fx-font-size: 50px;");
                    Button stopButton = new Button("Stop Timer");
                    stopButton.setOnAction(s -> {
                        //set display to neutral and remove the button
                        stopTimer();
                        titleLabel.setText("");
                        descLabel.setText("");
                        timer.setText("");
                        reward.setText("");
                        button.setDisable(false);
                        vbox.getChildren().remove(stopButton);
                    });
                    vbox.getChildren().add(stopButton);

                    button.setDisable(true);
                    int init_minutes = ((TimedEvent) event).getTimer();
                    AtomicInteger currentMinutes = new AtomicInteger(init_minutes);
                    AtomicInteger currentSeconds = new AtomicInteger(0);
                    //create timer functionality
                    timeline = new Timeline(new KeyFrame(Duration.seconds(1), t -> {
                        int minutes = currentMinutes.get();
                        int seconds = currentSeconds.get();

                        if (seconds == 0) {
                            if (minutes == 0) {
                                button.setDisable(false);
                                vbox.getChildren().remove(stopButton);
                                stopTimer();
                                timer.setStyle("-fx-text-fill: black; -fx-font-size: 50px;");
                                timer.setText("Time's Up!");
                                return;
                            } else {
                                currentMinutes.decrementAndGet();
                                currentSeconds.set(59);
                            }
                        } else {
                            currentSeconds.getAndDecrement();
                        }

                        timer.setText(String.format("%02d:%02d", minutes, seconds));

                        //if the timer is at half, the text changes to orange, if it's at a fourth, the text turns red
                        if ((minutes*60) + seconds <= ((init_minutes*60)/4)) {
                            timer.setStyle("-fx-text-fill: red; -fx-font-size: 50px;");
                        } else if (minutes <= (init_minutes/2) && seconds == 0) {
                            timer.setStyle("-fx-text-fill: orange; -fx-font-size: 50px;");
                        }

                    }));
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    timeline.play();


                } else {
                    
                }

        });

        //create scene
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Event Generator");
        primaryStage.show();
    }

    //makes a new event from the event loader and returns the object
    public GameEvent genEvent() {
        GameEventLoader loader = new GameEventLoader();
        return loader.readFile("events.txt");
    }

    //stops the timer
    public void stopTimer(){
        timeline.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}