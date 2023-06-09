package org.example;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;

public class MainMenuController extends Application {

    private static boolean areGameSoundsMuted = false;

    public static boolean areGameSoundsMuted() {
        return areGameSoundsMuted;
    }

    public static void setGameSoundsMuted(boolean areGameSoundsMuted) {
        MainMenuController.areGameSoundsMuted = areGameSoundsMuted;
    }

    public void logout() throws Exception {
        User.setLoggedInUser(null);
        App.setAndShowLoginMenuScene();
    }

    public void changePassword() {
        String newPassword = JOptionPane.showInputDialog("Enter new password: ");
        if (newPassword != null && newPassword.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter new password first");
            changePassword();
        } else if (newPassword != null) {
            User.getLoggedInUser().setPassword(newPassword);
            JOptionPane.showMessageDialog(null, "Password Changed");
        }
    }

    public void removeAccount() throws Exception{
        int result = JOptionPane.showConfirmDialog(null, "Are you sure?");
        if(result == JOptionPane.OK_OPTION){
            User.getAllUsers().remove(User.getLoggedInUser());
            User.setLoggedInUser(null);
            App.setAndShowLoginMenuScene();
        }
    }

    public void startNewGame() throws Exception {
        new ChooseGameMapController().start(App.getStage());
    }

    public void scoreboard(MouseEvent mouseEvent) throws Exception {
        User.sortUsers();
        new ScoreboardController().start(App.getStage());
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainmenu.fxml"));
        stage.setTitle("Main Menu");
        Scene scene = new Scene(root, 1284, 672);
        stage.setScene(scene);
        stage.show();
    }
}
