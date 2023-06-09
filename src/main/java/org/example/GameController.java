package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private static MediaPlayer eatingFoodSound = new MediaPlayer(new Media(ChooseGameMapController.class.getResource("pacman_chomp.wav").toExternalForm()));

    @FXML
    private Pane pane;
    private int score = 0;
    private char[][] map;
    private char[][] changeableMap;
    private ArrayList<CircleInMap> circles = new ArrayList<>();
    private ImageAndLocation PACMAN = new ImageAndLocation(7, 7);
    private ImageAndLocation GHOST1 = new ImageAndLocation(0, 14);
    private ImageAndLocation GHOST2 = new ImageAndLocation(0, 0);
    private ImageAndLocation GHOST3 = new ImageAndLocation(14, 0);
    private ImageAndLocation GHOST4 = new ImageAndLocation(14, 14);
    private Text playerScoreShower;
    private long lastKeyTime = 0;
    private Integer numberOfLives;
    private ArrayList<ImageView> livesPictures = new ArrayList<>();
    private boolean isGameStopped = true;
    private Timeline moveGhostTimeline;

    public int getScore() {
        return score;
    }

    public void changeScore(int score) {
        this.score += score;
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    public void setNumberOfLives(int numberOfLives) {
        this.numberOfLives = numberOfLives;
    }

    public Integer getNumberOfLives() {
        return numberOfLives;
    }

    public void setGameStopped(boolean isGameStopped) {
        this.isGameStopped = isGameStopped;
    }

    public boolean isGameStopped() {
        return isGameStopped;
    }

    public Timeline getMoveGhostTimeline() {
        return moveGhostTimeline;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMap(ChooseGameMapController.copyMap(ChooseGameMapController.getSelectedMap()));
        changeableMap = ChooseGameMapController.getSelectedChangeableMap();
        setGhosts();
        addCirclesAndGhostsAndPacmanToMap();
        addWallsToMap();
        Text pauseAndResume = createTextWithStyle("Press 'P' to pause\nPress 'R' to resume\nPress 'M' to mute/unmute music\nPress 'S' to mute/unmute sounds", 20, 40, 50);
        pane.getChildren().add(pauseAndResume);
        Text pressKeyToStart = createTextWithStyle("Press any key to start moving", 20, 500, 50);
        pane.getChildren().add(pressKeyToStart);
        playerScoreShower = new Text("Score: ");
        playerScoreShower.setFill(Color.WHITE);
        pane.getChildren().add(playerScoreShower);
        playerScoreShower.setLayoutY(50);
        playerScoreShower.setLayoutX(1000);
        playerScoreShower.setFont(Font.font("Tahoma", 18));
        moveGhostTimeline = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.millis(500), actionEvent -> moveGhosts());
        moveGhostTimeline.getKeyFrames().add(frame);
        moveGhostTimeline.setCycleCount(-1);
        setOnKeyPressedForCommands(pressKeyToStart);
    }

    private void setOnKeyPressedForCommands(Text pressKeyToStart) {
        pane.getChildren().get(115).setOnKeyPressed(keyEvent -> {
            setPauseResumeMuteOnKeyPressed(keyEvent);
            addLivesToPane();
            if (isGameStopped) {
                isGameStopped = false;
            }
            moveGhostTimeline.setDelay(Duration.seconds(2));
            moveGhostTimeline.play();
            pressKeyToStart.setText("");
            playerScoreShower.setText("Score: " + score);
            pane.getChildren().get(115).setOnKeyPressed(keyEvent1 -> {
                setPauseResumeMuteOnKeyPressed(keyEvent1);
                if (keyEvent1.getCode().isArrowKey() && (lastKeyTime == 0 || System.currentTimeMillis() - lastKeyTime >= 250) && !isGameStopped) {
                    movePacman(keyEvent1.getCode());
                }
            });
        });
    }

    private void setPauseResumeMuteOnKeyPressed(KeyEvent keyEvent1) {
        if (keyEvent1.getCode() == KeyCode.P) {
            moveGhostTimeline.pause();
            isGameStopped = true;
        } else if (keyEvent1.getCode() == KeyCode.R) {
            moveGhostTimeline.play();
            isGameStopped = false;
        } else if (keyEvent1.getCode() == KeyCode.M){
            ChooseGameMapController.getGameMusic().setMute(!ChooseGameMapController.getGameMusic().isMute());
        } else if (keyEvent1.getCode() == KeyCode.S){
            MainMenuController.setGameSoundsMuted(!MainMenuController.areGameSoundsMuted());
            ChooseGameMapController.getStartGameSound().setMute(MainMenuController.areGameSoundsMuted());
        }
    }

    private void addLivesToPane() {
        Text text = createTextWithStyle("Lives: ", 16, 950, 120);
        pane.getChildren().add(text);
        for (int i = 0; i < numberOfLives; i++) {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(String.valueOf(getClass().getResource("/org/example/Pacman.PNG"))));
            imageView.setY(100);
            imageView.setX(1000 + i * 40);
            imageView.setFitWidth(28);
            imageView.setFitHeight(28);
            pane.getChildren().add(imageView);
            livesPictures.add(imageView);
        }
    }

    private Text createTextWithStyle(String string, int fontSize, int layoutX, int layoutY) {
        Text text = new Text(string);
        text.setFill(Color.WHITE);
        text.setLayoutY(layoutY);
        text.setLayoutX(layoutX);
        text.setFont(Font.font("Tahoma", fontSize));
        return text;
    }

    private void movePacman(KeyCode code) {
        PACMAN.toFront();
        CircleInMap circleInThisLocation = CircleInMap.getCircleByLocation(PACMAN.getYInMap(), PACMAN.getXInMap(), circles);
        if (circleInThisLocation != null && circleInThisLocation.getFill() == Color.DEEPSKYBLUE) {
            circleInThisLocation.setFill(Color.BLACK);
        }
        MoveTransition transition = new MoveTransition(code, PACMAN, this);
        if (code == KeyCode.UP && map[2 * PACMAN.getYInMap()][2 * PACMAN.getXInMap() + 1] == '0') {
            PACMAN.setRotate(270);
            if(changeableMap[2 * PACMAN.getYInMap() - 1][2 * PACMAN.getXInMap() + 1] != 'X'){
                playEatingFoodMusic();
                changeableMap[2 * PACMAN.getYInMap() - 1][2 * PACMAN.getXInMap() + 1] = 'X';
            }
            changeXAndYInMap(PACMAN, KeyCode.UP);
            lastKeyTime = System.currentTimeMillis();
            transition.play();
        } else if (code == KeyCode.DOWN && map[2 * PACMAN.getYInMap() + 2][2 * PACMAN.getXInMap() + 1] == '0') {
            PACMAN.setRotate(90);
            if(changeableMap[2 * PACMAN.getYInMap() + 3][2 * PACMAN.getXInMap() + 1] != 'X'){
                playEatingFoodMusic();
                changeableMap[2 * PACMAN.getYInMap() + 3][2 * PACMAN.getXInMap() + 1] = 'X';
            }
            changeXAndYInMap(PACMAN, KeyCode.DOWN);
            lastKeyTime = System.currentTimeMillis();
            transition.play();
        } else if (code == KeyCode.LEFT && map[2 * PACMAN.getYInMap() + 1][2 * PACMAN.getXInMap()] == '0') {
            PACMAN.setRotate(180);
            if(changeableMap[2 * PACMAN.getYInMap() + 1][2 * PACMAN.getXInMap() - 1] != 'X'){
                playEatingFoodMusic();
                changeableMap[2 * PACMAN.getYInMap() + 1][2 * PACMAN.getXInMap() - 1] = 'X';
            }
            changeXAndYInMap(PACMAN, KeyCode.LEFT);
            lastKeyTime = System.currentTimeMillis();
            transition.play();
        } else if (code == KeyCode.RIGHT && map[2 * PACMAN.getYInMap() + 1][2 * PACMAN.getXInMap() + 2] == '0') {
            PACMAN.setRotate(0);
            if(changeableMap[2 * PACMAN.getYInMap() + 1][2 * PACMAN.getXInMap() + 3] != 'X'){
                playEatingFoodMusic();
                changeableMap[2 * PACMAN.getYInMap() + 1][2 * PACMAN.getXInMap() + 3] = 'X';
            }
            changeXAndYInMap(PACMAN, KeyCode.RIGHT);
            lastKeyTime = System.currentTimeMillis();
            transition.play();
        }
        if (isResetNeeded()) {
            getMoveGhostTimeline().stop();
            setGameStopped(true);
            ChooseGameMapController.setSelectedChangeableMap(map);
            try {
                ChooseGameMapController.createNewGameScene(getNumberOfLives(), getScore() + 5);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        circleInThisLocation = CircleInMap.getCircleByLocation(PACMAN.getYInMap(), PACMAN.getXInMap(), circles);
        if (circleInThisLocation != null && circleInThisLocation.getFill() == Color.DEEPSKYBLUE) {
            changeScore(5);
            playerScoreShower.setText("Score: " + score);
        }
    }

    private void playEatingFoodMusic() {
        if (!MainMenuController.areGameSoundsMuted()) {
            eatingFoodSound = new MediaPlayer(new Media(ChooseGameMapController.class.getResource("pacman_chomp.wav").toExternalForm()));
            eatingFoodSound.play();
        }
    }

    private void moveGhosts() {
        moveAGhost(GHOST1, 1);
        moveAGhost(GHOST2, 2);
        moveAGhost(GHOST3, 3);
        moveAGhost(GHOST4, 4);
    }

    private void moveAGhost(ImageAndLocation ghostPic, int ghostNumber) {
        ArrayList<KeyCode> availableMoves = new ArrayList<>();
        if (map[2 * ghostPic.getYInMap() + 2][2 * ghostPic.getXInMap() + 1] == '0') {
            addKeyCodeToArrayList(ghostPic, availableMoves, KeyCode.DOWN);
        }
        if (map[2 * ghostPic.getYInMap()][2 * ghostPic.getXInMap() + 1] == '0') {
            addKeyCodeToArrayList(ghostPic, availableMoves, KeyCode.UP);
        }
        if (map[2 * ghostPic.getYInMap() + 1][2 * ghostPic.getXInMap()] == '0') {
            addKeyCodeToArrayList(ghostPic, availableMoves, KeyCode.LEFT);
        }
        if (map[2 * ghostPic.getYInMap() + 1][2 * ghostPic.getXInMap() + 2] == '0') {
            addKeyCodeToArrayList(ghostPic, availableMoves, KeyCode.RIGHT);
        }
        Collections.shuffle(availableMoves);
        KeyCode code = availableMoves.get(0);
        ghostPic.toFront();
        changeXAndYInMap(ghostPic, code);
        ghostPic.setLastMove(code);
        Random random = new Random();
        int whichPicForAnimation = Math.abs(random.nextInt()%4) + 1;
        MoveTransition transition = new MoveTransition(code, ghostPic, PACMAN, this, ghostNumber,
                whichPicForAnimation);
        transition.play();
    }

    private void changeXAndYInMap(ImageAndLocation ghost, KeyCode code) {
        if (code == KeyCode.UP) {
            ghost.setYInMap(ghost.getYInMap() - 1);
        } else if (code == KeyCode.DOWN) {
            ghost.setYInMap(ghost.getYInMap() + 1);
        } else if (code == KeyCode.LEFT) {
            ghost.setXInMap(ghost.getXInMap() - 1);
        } else if (code == KeyCode.RIGHT) {
            ghost.setXInMap(ghost.getXInMap() + 1);
        }
    }

    private void addKeyCodeToArrayList(ImageAndLocation ghost, ArrayList<KeyCode> availableMoves, KeyCode keyCode) {
        int howManyTimes = areKeyCodesOpposite(ghost.getLastMove(), keyCode) ? 1 : 3;
        for (int i = 0; i < howManyTimes; i++) {
            availableMoves.add(keyCode);
        }
    }

    private boolean areKeyCodesOpposite(KeyCode first, KeyCode second) {
        return (first == KeyCode.RIGHT && second == KeyCode.LEFT) ||
                (first == KeyCode.LEFT && second == KeyCode.RIGHT) ||
                (first == KeyCode.UP && second == KeyCode.DOWN) ||
                (first == KeyCode.DOWN && second == KeyCode.UP);
    }

    private void setGhosts() {
        PACMAN.setImage(new Image(String.valueOf(getClass().getResource("/org/example/SPRITE/PACMAN2.jpg"))));
        GHOST1.setImage(new Image(String.valueOf(getClass().getResource("/org/example/SPRITE/GHOST13.jpg"))));
        GHOST2.setImage(new Image(String.valueOf(getClass().getResource("/org/example/SPRITE/GHOST21.jpg"))));
        GHOST3.setImage(new Image(String.valueOf(getClass().getResource("/org/example/SPRITE/GHOST32.jpg"))));
        GHOST4.setImage(new Image(String.valueOf(getClass().getResource("/org/example/SPRITE/GHOST44.jpg"))));
        PACMAN.toFront();
        GHOST1.toFront();
        GHOST2.toFront();
        GHOST3.toFront();
        GHOST4.toFront();
    }

    private void addWallsToMap() {
        for (int i = 1; i < 31; i += 2) {
            for (int j = 0; j < 31; j += 2) {
                if (map[i][j] == '1') {
                    Line line = new Line();
                    line.setStrokeWidth(2);
                    line.setStroke(Color.WHITE);
                    line.setStartX(368 + 18 * j);
                    line.setStartY(70 + 18 * i);
                    line.setEndX(line.getStartX());
                    line.setEndY(line.getStartY() + 36);
                    pane.getChildren().add(line);
                }
            }
        }
        for (int i = 0; i < 31; i += 2) {
            for (int j = 1; j < 31; j += 2) {
                if (map[i][j] == '1') {
                    Line line = new Line();
                    line.setStrokeWidth(2);
                    line.setStroke(Color.WHITE);
                    line.setStartX(350 + 18 * j);
                    line.setStartY(88 + 18 * i);
                    line.setEndX(line.getStartX() + 36);
                    line.setEndY(line.getStartY());
                    pane.getChildren().add(line);
                }
            }
        }
    }

    private void addCirclesAndGhostsAndPacmanToMap() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                CircleInMap circle = new CircleInMap(i, j, (j + 1) * 36 + 350, (i + 1) * 36 + 70, 7);
                pane.getChildren().add(circle);
                if (changeableMap[2 * i + 1][2 * j + 1] == 'X') {
                    circle.setFill(Color.BLACK);
                } else {
                    circle.setFill(Color.DEEPSKYBLUE);
                }
                circles.add(circle);
                if (doesNeedPicture(i, j)) {
                    ImageAndLocation imageAndLocation;
                    if (i == 7 && j == 7) {
                        imageAndLocation = PACMAN;
                    } else if (i == 0 && j == 14) {
                        imageAndLocation = GHOST1;
                    } else if (i == 0 && j == 0) {
                        imageAndLocation = GHOST2;
                    } else if (i == 14 && j == 0) {
                        imageAndLocation = GHOST3;
                    } else {
                        imageAndLocation = GHOST4;
                    }
                    imageAndLocation.setY(circle.getCenterY() - 14);
                    imageAndLocation.setX(circle.getCenterX() - 14);
                    imageAndLocation.setFitWidth(28);
                    imageAndLocation.setFitHeight(28);
                    pane.getChildren().add(imageAndLocation);
                }
            }
        }
    }

    private boolean doesNeedPicture(int i, int j) {
        return (i == 7 && j == 7) || (i == 0 && j == 14) || (i == 0 && j == 0) || (i == 14 && j == 0) || (i == 14 && j == 14);
    }

    private boolean isResetNeeded() {
        for (int i = 1; i < 30; i += 2) {
            for (int j = 1; j < 30; j += 2) {
                if (changeableMap[i][j] != 'X' && i != 15 && j != 15) {
                    return false;
                }
            }
        }
        return true;
    }
}
