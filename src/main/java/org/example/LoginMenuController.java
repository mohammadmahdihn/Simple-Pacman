package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;

public class LoginMenuController {
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;

    public void signup() {
        if (password.getText().equals("") || username.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter username and password first.");
        } else if (User.isUsernameExist(username.getText())) {
            JOptionPane.showMessageDialog(null, "This username already exists");
        } else {
            new User(username.getText(), password.getText());
            JOptionPane.showMessageDialog(null, "User created successfully");
        }
    }

    public void login() throws Exception {
        if (password.getText().equals("") || username.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter username and password first.");
        } else if (!User.isUsernameExist(username.getText())) {
            JOptionPane.showMessageDialog(null, "This username doesn't exist");
        } else if (!User.isUsernameAndPasswordMatched(username.getText(), password.getText())) {
            JOptionPane.showMessageDialog(null, "Password is wrong");
        } else {
            User.setLoggedInUser(User.getUserByUsername(username.getText()));
            new MainMenuController().start(App.getStage());
        }
    }

    public void startGameAsGuest () throws Exception {
        new ChooseGameMapController().start(App.getStage());
    }

    public void exitGame(){
        User.serialize();
        System.exit(0);
    }
}
