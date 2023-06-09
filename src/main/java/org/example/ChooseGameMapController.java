package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class ChooseGameMapController extends Application {
    private static char[][] selectedMap;
    private static char[][] selectedChangeableMap;
    @FXML
    private Pane livesPane;
    @FXML
    private HBox bottomHBox;
    @FXML
    private VBox rightVBox;
    @FXML
    private GridPane gameMap;
    private int numberOfLives = 3;
    private ArrayList<ImageView> livesImage = new ArrayList<>();
    private static MediaPlayer gameMusic = new MediaPlayer(new Media(ChooseGameMapController.class.getResource("2 - Riot.mp3").toExternalForm()));
    private static MediaPlayer startGameSound = new MediaPlayer(new Media(ChooseGameMapController.class.getResource("pacman_beginning.wav").toExternalForm()));

    public static MediaPlayer getGameMusic() {
        return gameMusic;
    }

    public static MediaPlayer getStartGameSound() {
        return startGameSound;
    }

    public static char[][] getSelectedMap() {
        return selectedMap;
    }

    public static void setSelectedMap(char[][] selectedMap) {
        if(selectedMap == null){
            ChooseGameMapController.selectedMap= null;
        } else {
            ChooseGameMapController.selectedMap = copyMap(selectedMap);
        }
    }

    public static void setSelectedChangeableMap(char[][] selectedChangeableMap) {
        if(selectedChangeableMap == null){
            ChooseGameMapController.selectedChangeableMap = null;
        } else {
            ChooseGameMapController.selectedChangeableMap = copyMap(selectedChangeableMap);
        }
    }

    public static char[][] getSelectedChangeableMap() {
        return selectedChangeableMap;
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(getClass().getResource("choosegamemap.fxml"));
        stage.setTitle("Game");
        Scene scene = new Scene(root, 1284, 672);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        addMapToFavoritesButton();
        addBackButton();
        addLivesPart();
    }

    public void showAndSelectMap1() {
        char[][] map = MapResource.getMap1();
        setSelectedMap(map);
        showMap(map);
    }

    public void showAndSelectMap2() {
        char[][] map = MapResource.getMap2();
        setSelectedMap(map);
        showMap(map);
    }

    public void showAndSelectMap3() {
        char[][] map = MapResource.getMap3();
        setSelectedMap(map);
        showMap(map);
    }

    public void showAndSelectRandomMap() {
        char[][] map = MapResource.getRandomMap(31);
        setSelectedMap(map);
        showMap(map);
    }

    private void showMap(char[][] map1) {
        gameMap.getChildren().clear();
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 31; j++) {
                if (i % 2 == 1 && j % 2 == 1) {
                    Circle circle = new Circle(20, 20f, 7);
                    circle.setFill(Color.DEEPSKYBLUE);
                    gameMap.add(circle, i, j);
                    GridPane.setHalignment(circle, HPos.CENTER);
                } else if (i % 2 == 1) {
                    if (map1[i][j] == '1') {
                        Line line = new Line();
                        line.setEndY(30 + line.getEndY());
                        line.setStrokeWidth(1.75);
                        line.setStroke(Color.WHITE);
                        gameMap.add(line, j, i);
                    }
                } else if (j % 2 == 1) {
                    if (map1[i][j] == '1') {
                        Line line = new Line();
                        line.setEndX(30 + line.getEndX());
                        line.setStrokeWidth(1.75);
                        line.setStroke(Color.WHITE);
                        gameMap.add(line, j, i);
                    }
                } else {
                    Circle circle = new Circle(1, 1f, 1);
                    circle.setFill(Color.BLACK);
                    gameMap.add(circle, i, j);
                }
            }
        }
        App.getStage().show();
    }

    private void addLivesPart() {
        addLivesText();
        addIncreaseAndDecreaseButtons();
        addLivesPictures();
        addChangeLivesText();
    }

    private void addIncreaseAndDecreaseButtons() {
        addPlusOrMinusButton(true);
        addPlusOrMinusButton(false);
    }

    private void addPlusOrMinusButton(boolean isPlus){
        Button button = new Button();
        button.setText(isPlus ? "+" : "-");
        button.setOnMouseClicked(mouseEvent -> {
            boolean condition = isPlus ? numberOfLives < 5 : numberOfLives > 2;
            if(condition){
                if (isPlus) {
                    numberOfLives++;
                } else {
                    numberOfLives--;
                }
                removeAllImages();
                addLivesPictures();
            }
        });
        button.setLayoutY(button.getLayoutX() - 20);
        button.setLayoutX(isPlus ? 100 : 70);
        button.setPrefWidth(25);
        livesPane.getChildren().add(button);
    }

    private void removeAllImages(){
        livesPane.getChildren().removeIf(node -> node instanceof ImageView);
    }

    private void addLivesPictures() {
        for (int i=0; i<numberOfLives; i++){
            ImageView view = createLiveImage(i);
            livesPane.getChildren().add(view);
        }
    }

    private ImageView createLiveImage (int index){
        ImageView view = new ImageView();
        view.setImage(new Image(String.valueOf(getClass().getResource("/org/example/Pacman.PNG"))));
        view.setFitWidth(30);
        view.setFitHeight(30);
        view.setY(view.getY() + 10);
        view.setX(10 + index* 40);
        livesImage.add(view);
        return view;
    }

    private void addChangeLivesText() {
        Text text = new Text("Max : 5, Min : 2");
        text.setFill(Color.WHITE);
        text.setLayoutY(text.getLayoutY() + 70);
        text.setLayoutX(10);
        text.setFont(Font.font("Tahoma", 16));
        livesPane.getChildren().add(text);
    }

    private void addLivesText() {
        Text text = new Text("Lives: ");
        text.setFill(Color.WHITE);
        text.setLayoutX(10);
        text.setFont(Font.font("Tahoma", 16));
        livesPane.getChildren().add(text);
    }

    private void addBackButton() {
        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setOnMouseClicked(mouseEvent -> {
            try {
                if (User.getLoggedInUser() == null) {
                    setSelectedMap(null);
                    new App().start(App.getStage());
                } else {
                    setSelectedMap(null);
                    new MainMenuController().start(App.getStage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        bottomHBox.getChildren().add(backButton);
    }

    private void addMapToFavoritesButton() {
        if (User.getLoggedInUser() != null) {
            Button addMap = new Button();
            addMap.setText("Add map to favorites");
            addMap.setOnMouseClicked(mouseEvent -> {
                if (selectedMap == null) {
                    JOptionPane.showMessageDialog(null, "You should select a map first");
                } else if (User.getLoggedInUser().haveThisMap(selectedMap)) {
                    JOptionPane.showMessageDialog(null, "You already have this map");
                } else {
                    User.getLoggedInUser().addToFavoriteMaps(selectedMap);
                    JOptionPane.showMessageDialog(null, "Map added to your favorites");
                    refreshRightVBox();
                }
            });
            bottomHBox.getChildren().add(addMap);
            refreshRightVBox();
        }
    }

    private void refreshRightVBox() {
        rightVBox.getChildren().clear();
        if (User.getLoggedInUser().getFavoriteMaps().size() > 0) {
            Text text = new Text();
            text.setText("Select a favorite map");
            text.setFill(Color.WHITE);
            text.setId("text");
            rightVBox.getChildren().add(text);
            int index = 1;
            for (char[][] map : User.getLoggedInUser().getFavoriteMaps()) {
                Button button = new Button();
                button.setText("Favorite map " + index);
                button.setOnMouseClicked(mouseEvent -> {
                    setSelectedMap(map);
                    showMap(map);
                });
                rightVBox.getChildren().add(button);
                index++;
            }
        }
    }

    @FXML
    private void startGameClicked() throws Exception {
        if(selectedMap == null){
            JOptionPane.showMessageDialog(null, "You must select a map before starting game");
        } else {
            setSelectedChangeableMap(selectedMap);
            startGameSound.play();
            gameMusic.setCycleCount(-1);
            gameMusic.play();
            createNewGameScene(numberOfLives, 0);
        }
    }

    public static void createNewGameScene(int numberOfLives, int score) throws IOException {
        FXMLLoader loader = new FXMLLoader(ChooseGameMapController.class.getResource("game.fxml"));
        Stage stage = App.getStage();
        Pane root = loader.load();
        GameController controller = loader.getController();
        controller.setNumberOfLives(numberOfLives);
        controller.changeScore(score);
        stage.setTitle("Game");
        Scene scene = new Scene(root, 1284, 672);
        stage.setScene(scene);
        root.getChildren().get(115).requestFocus();
        stage.show();
    }

    public static char[][] copyMap (char[][] map){
        char[][] newMap = new char[31][31];
        for (int i=0; i<31; i++){
            System.arraycopy(map[i], 0, newMap[i], 0, 31);
        }
        return newMap;
    }
}