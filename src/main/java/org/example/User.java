package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static User loggedInUser;
    private String username;
    private String password;
    private char[][] lastSavedGameMap;
    private ArrayList<char[][]> favoriteMaps = new ArrayList<>();
    private int highScore;

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        allUsers.add(this);
    }

    public static boolean isUsernameExist(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUsernameAndPasswordMatched(String username, String password) {
        for (User user : allUsers) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static void serialize() {
        try {
            FileWriter writer = new FileWriter("src/Database.json");
            writer.write(new Gson().toJson(User.getAllUsers()));
            writer.close();
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public static void deserialize() {
        try{
            String usersToJson = new String(Files.readAllBytes(Paths.get("src/Database.json")));
            allUsers = new Gson().fromJson(usersToJson, new TypeToken<List<User>>(){}.getType());
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void sortUsers(){
        for (int i=0; i<allUsers.size(); i++){
            for (int j=0; j<allUsers.size()-1; j++){
                if(allUsers.get(j).highScore < allUsers.get(j+1).highScore){
                    Collections.swap(allUsers, j, j+1);
                }
            }
        }
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers){
            if(user.username.equals(username)){
                return user;
            }
        }
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int score) {
        this.highScore = score;
    }

    public ArrayList<char[][]> getFavoriteMaps() {
        return favoriteMaps;
    }

    public void addToFavoriteMaps(char[][] map){
        favoriteMaps.add(map);
    }

    public char[][] getLastSavedGameMap() {
        return lastSavedGameMap;
    }

    public void setLastSavedGameMap(char[][] lastSavedGameMap) {
        this.lastSavedGameMap = lastSavedGameMap;
    }

    public boolean haveThisMap(char[][] selectedMap) {
        for (char[][] map : favoriteMaps){
            if(map == selectedMap){
                return true;
            }
        }
        return false;
    }
}
