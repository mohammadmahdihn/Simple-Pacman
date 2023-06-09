package org.example;

import com.google.gson.Gson;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class CircleInMap extends Circle {
    private int xInMap;
    private int yInMap;

    public CircleInMap (int xInMap, int yInMap, double v, double v1, double v2){
        super(v, v1, v2);
        setXInMap(xInMap);
        setYInMap(yInMap);
    }
    public static CircleInMap getCircleByLocation(int x, int y,ArrayList<CircleInMap> circles){
        for (CircleInMap circle : circles){
            if(circle.xInMap == x && circle.yInMap == y){
                return circle;
            }
        }
        return null;
    }

    public int getXInMap() {
        return xInMap;
    }

    public void setXInMap(int xInMap) {
        this.xInMap = xInMap;
    }

    public int getYInMap() {
        return yInMap;
    }

    public void setYInMap(int yInMap) {
        this.yInMap = yInMap;
    }
}
