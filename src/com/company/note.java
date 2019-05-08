package com.company;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class note {
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


    note(ArrayList<Double> scale, int i, String complexity){

         this.complexity = complexity;
         counter = i;
         this.scale = scale;


    }
 g.setFill(Color.GREEN);

    //kryds
            if(isMajor) {
        String txt = s;

        if (txt.contains("D")) {

            g.fillRect(0, 72 + row * offset, 16, 2);
            g.fillRect(0, 72 + row * offset + 5, 16, 2);
            g.fillRect(0 + 5, 72 + row * offset - 4, 2, 17);
            g.fillRect(0 + 10, 72 + row * offset - 4, 2, 17);

            g.fillRect(0+2, 87 + row * offset, 16, 2);
            g.fillRect(0+2, 87 + row * offset + 5, 16, 2);
            g.fillRect(0 + 5+2, 87 + row * offset - 4, 2, 17);
            g.fillRect(0 + 10+2, 87 + row * offset - 4, 2, 17);
        }
    }

}
