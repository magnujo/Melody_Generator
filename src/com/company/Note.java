package com.company;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Note {
    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    private double xPos;
    private double yPos;
    private int counter;
    private String complexity;
    private ArrayList<Double> scale;


    Note(ArrayList<Double> scale, int i, String complexity){

         this.complexity = complexity;
         counter = i;
         this.scale = scale;


    }


}
