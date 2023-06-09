package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class App extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        setAndShowLoginMenuScene();
    }

    public static void main(String[] args) {
        createFileIfNeeded();
        User.deserialize();
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setAndShowLoginMenuScene() throws Exception{
        Parent root = FXMLLoader.load(App.class.getResource("loginmenu.fxml"));
        App.getStage().setTitle("PACMAN");
        Scene scene = new Scene(root, 1284, 672);
        App.getStage().setScene(scene);
        App.getStage().show();
    }

    private static void createFileIfNeeded(){
        try {
            File file = new File("src/Database.json");
            file.createNewFile();
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

}