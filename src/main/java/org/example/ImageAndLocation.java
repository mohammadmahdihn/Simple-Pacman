package org.example;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class ImageAndLocation extends ImageView {
    private int yInMap;
    private int xInMap;
    private KeyCode lastMove;

    public ImageAndLocation (int yInMap, int xInMap){
        setYInMap(yInMap);
        setXInMap(xInMap);
        setLastMove(KeyCode.ENTER);
    }

    public int getYInMap() {
        return yInMap;
    }

    public void setYInMap(int yInMap) {
        this.yInMap = yInMap;
    }

    public int getXInMap() {
        return xInMap;
    }

    public void setXInMap(int xInMap) {
        this.xInMap = xInMap;
    }

    public KeyCode getLastMove() {
        return lastMove;
    }

    public void setLastMove(KeyCode lastMove) {
        this.lastMove = lastMove;
    }

    public void setXAndYInMap(int yInMap, int xInMap){
        setYInMap(yInMap);
        setXInMap(xInMap);
    }
}
