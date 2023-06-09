package org.example;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.swing.*;
import java.io.IOException;

public class MoveTransition extends Transition {
    private static MediaPlayer pacmanDeathSound;
    private KeyCode keyCode;
    private ImageAndLocation imageAndLocation;
    private double firstLocationX;
    private double firstLocationY;
    private ImageAndLocation pacman;
    private GameController currentGameController;
    private int ghostNumber;
    private int whichPicForAnimation;

    public MoveTransition(KeyCode keyCode, ImageAndLocation ghostPic, ImageAndLocation pacman, GameController controller,
                          int ghostNumber, int whichPicForAnimation) {
        this.currentGameController = controller;
        this.pacman = pacman;
        this.imageAndLocation = ghostPic;
        this.keyCode = keyCode;
        this.firstLocationX = imageAndLocation.getX();
        this.firstLocationY = imageAndLocation.getY();
        setCycleDuration(Duration.millis(500));
        this.ghostNumber = ghostNumber;
        this.whichPicForAnimation = whichPicForAnimation;
    }


    public MoveTransition(KeyCode keyCode, ImageAndLocation pacman, GameController controller) {
        this.currentGameController = controller;
        this.imageAndLocation = pacman;
        this.keyCode = keyCode;
        this.firstLocationX = pacman.getX();
        this.firstLocationY = pacman.getY();
        setCycleDuration(Duration.millis(250));
    }

    @Override
    protected void interpolate(double v) {
        if (currentGameController.isGameStopped()) {
            return;
        }
        if (pacman == null) {
            setPacmanAnimations(v);
        } else {
            imageAndLocation.setImage(new Image(getClass().getResource("/org/example/SPRITE/GHOST" + ghostNumber
                    + whichPicForAnimation + ".jpg").toExternalForm()));
        }
        if (keyCode == KeyCode.UP) {
            imageAndLocation.setY(firstLocationY - v * 36);
        } else if (keyCode == KeyCode.DOWN) {
            imageAndLocation.setY(firstLocationY + v * 36);
        } else if (keyCode == KeyCode.LEFT) {
            imageAndLocation.setX(firstLocationX - v * 36);
        } else if (keyCode == KeyCode.RIGHT) {
            imageAndLocation.setX(firstLocationX + v * 36);
        }
        if (pacman != null && imageAndLocation.getBoundsInParent().intersects(pacman.getLayoutBounds())) {
            ghostAtePacman();
        }
    }

    private void setPacmanAnimations(double v) {
        double whichPicture = v * 6;
        if (whichPicture < 1) {
            imageAndLocation.setImage(new Image(getClass().getResource("/org/example/SPRITE/PACMAN2.jpg").toExternalForm()));
        } else if (whichPicture < 2) {
            imageAndLocation.setImage(new Image(getClass().getResource("/org/example/SPRITE/PACMAN1.jpg").toExternalForm()));
        } else if (whichPicture < 3) {
            imageAndLocation.setImage(new Image(getClass().getResource("/org/example/SPRITE/PACMAN2.jpg").toExternalForm()));
        } else if (whichPicture < 4) {
            imageAndLocation.setImage(new Image(getClass().getResource("/org/example/SPRITE/PACMAN3.jpg").toExternalForm()));
        } else if (whichPicture < 5) {
            imageAndLocation.setImage(new Image(getClass().getResource("/org/example/SPRITE/PACMAN4.jpg").toExternalForm()));
        } else {
            imageAndLocation.setImage(new Image(getClass().getResource("/org/example/SPRITE/PACMAN3.jpg").toExternalForm()));
        }
    }

    private void ghostAtePacman() {
        if(!MainMenuController.areGameSoundsMuted()){
            pacmanDeathSound = new MediaPlayer(new Media(ChooseGameMapController.class.getResource("pacman_death.wav").toExternalForm()));
            pacmanDeathSound.play();
        }
        currentGameController.getMoveGhostTimeline().stop();
        currentGameController.setGameStopped(true);
        if (currentGameController.getNumberOfLives() <= 1) {
            MainMenuController.setGameSoundsMuted(false);
            ChooseGameMapController.getGameMusic().stop();
            if (User.getLoggedInUser() != null && currentGameController.getScore() > User.getLoggedInUser().getHighScore()) {
                User.getLoggedInUser().setHighScore(currentGameController.getScore());
            }
            int result = JOptionPane.showConfirmDialog(null, "You lost! Do you want to start a new Game?");
            ChooseGameMapController.setSelectedMap(null);
            ChooseGameMapController.setSelectedChangeableMap(null);
            try {
                if (result == JOptionPane.OK_OPTION) {
                    new ChooseGameMapController().start(App.getStage());
                } else {
                    if (User.getLoggedInUser() == null) {
                        App.setAndShowLoginMenuScene();
                    } else {
                        new MainMenuController().start(App.getStage());
                    }
                }
                return;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        try {
            ChooseGameMapController.createNewGameScene(currentGameController.getNumberOfLives() - 1, currentGameController.getScore());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
