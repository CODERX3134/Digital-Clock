import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DigitalClock extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setId("anchor-pane");
        Scene scene = new Scene(anchorPane,400,300);
        FontAwesomeIconView closeIcon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
        closeIcon.setId("close-icon");
        Label close = new Label("",closeIcon);
        close.setId("close");
        close.setLayoutX(375);
        close.setLayoutY(5);
        Label clock = new Label("Digital Clock");
        clock.setId("clock");
        clock.setLayoutY(100);
        clock.setLayoutX(100);
        Label digitalClock = new Label("Digital Clock");
        digitalClock.setId("digital-clock");
        digitalClock.setLayoutX(130);
        digitalClock.setLayoutY(150);
        // the digital clock updates the second
        final Timeline digitalTime = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        e ->{
                            Calendar calendar = GregorianCalendar.getInstance();
                            String hourString = pad(2, '0', calendar.get(Calendar.HOUR) == 0 ? "12": calendar.get(Calendar.HOUR)+ "");
                            String minuteString = pad(2, '0', calendar.get(Calendar.MINUTE)+ "");
                            String secondString = pad(2,'0', calendar.get(Calendar.SECOND)+ "");
                            String am_pmString = calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM":"PM";
                            digitalClock.setText(hourString+":"+minuteString+":"+secondString+":"+am_pmString);
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );

        // Time never ends
        digitalTime.setCycleCount(Animation.INDEFINITE);

        // start the time
        digitalTime.play();

        anchorPane.setOnMousePressed(e ->{
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        anchorPane.setOnMouseDragged( e ->{
            primaryStage.setX(e.getScreenX() - xOffset);
            primaryStage.setY(e.getScreenY() - yOffset);
        });

        close.setOnMouseClicked(e -> System.exit(0));

        anchorPane.getChildren().addAll(digitalClock,clock,close);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args){
        launch();
    }

    private String pad(int fieldWidth, char padChar, String s){
        StringBuilder sb = new StringBuilder();
        for (int i = s.length(); i<fieldWidth; i++){
            sb.append(padChar);
        }
        sb.append(s);
        return sb.toString();
    }
}
