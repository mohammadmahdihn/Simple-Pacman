package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class ScoreboardController extends Application {
    @FXML
    private VBox table;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("scoreboard.fxml"));
        stage.setTitle("Scoreboard");
        Scene scene = new Scene(root, 1284, 672);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        int lastRank = 0;
        for (int i = 1; i <= 10; i++) {
            HBox hBox = new HBox();
            hBox.setSpacing(5);
            Rectangle rectangle = getRectangle(35, 500);
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(rectangle);
            if(i<=User.getAllUsers().size()){
                int rank;
                if(i==1 || !(User.getAllUsers().get(i-1).getHighScore() == User.getAllUsers().get(i-2).getHighScore())){
                    lastRank = i;
                    rank = i;
                } else {
                    rank = lastRank;
                }
                Text text = new Text(" " + rank + ". " + User.getAllUsers().get(i - 1).getUsername());
                text.setFont(Font.font("Verdana", 18));
                stackPane.getChildren().add(text);
            }
            stackPane.setAlignment(Pos.CENTER_LEFT);
            hBox.getChildren().add(stackPane);
            table.getChildren().add(hBox);
            Rectangle rectangle1 = getRectangle(35, 150);
            StackPane stackPane1 = new StackPane();
            stackPane1.getChildren().add(rectangle1);
            if(i<=User.getAllUsers().size()){
                Text text1 = new Text(String.valueOf(User.getAllUsers().get(i - 1).getHighScore()));
                text1.setFont(Font.font("Verdana", 18));
                stackPane1.getChildren().add(text1);
            }
            stackPane1.setAlignment(Pos.CENTER);
            hBox.getChildren().add(stackPane1);
        }
    }

    public Rectangle getRectangle(double height, double width){
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.CYAN);
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);
        rectangle.setHeight(height);
        rectangle.setWidth(width);
        return rectangle;
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenuController().start(App.getStage());
    }
}
