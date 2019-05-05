package com.company;


import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Rotate;



import java.awt.*;
import java.util.ArrayList;

public class MainController {
    private static ArrayList<Double> chosenScales = new ArrayList<>();
    private HashTest hashTest = new HashTest();
    private ArrayList textList = new ArrayList();
    private OscGenerator osc;
    private int iE;
    private String s = "C3";
    private MajorScaleTest majorScala = new MajorScaleTest(13, hashTest.frequencyFinder(s));
    private MinorScaleTest minorScala = new MinorScaleTest(13, hashTest.frequencyFinder(s));

    private boolean runonce = false;
    private boolean clicked=false;
    boolean isMajor;

    @FXML
    Canvas canvas;
    @FXML
    TextFlow textFlow;

    @FXML
    TextField textField;

    @FXML

    public void btn() {

        s = textField.getText();
        System.out.println("playing " + s);
        majorScala = new MajorScaleTest(13,hashTest.frequencyFinder(s));
        isMajor = true;
        clicked = true;
    }
    @FXML

    public void btn3(){
        clicked = false;
        osc.sineLineOut.stop();
    }

    @FXML

    public void btn1() {
        String s = textField.getText();
        System.out.println("playing " + s);
        minorScala = new MinorScaleTest(13,hashTest.frequencyFinder(s));
        isMajor = false;
        clicked = true;

    }

    @FXML
    public void btn2() {

        String s = textField.getText();
        System.out.println("added " + s);
        textList.add(s);
        HashTest hashTest = new HashTest();
        chosenScales.add(hashTest.frequencyFinder(s));
        textFlow.getChildren().clear();

        for (int i = 0; i < textList.size(); i++) {
            Text text = new Text(textList.get(i)+" ");
            text.setFont(new Font(25));
            text.setFill(Color.DARKORCHID);
            textFlow.getChildren().add(text);
       }

    }

    @FXML
    public void initialize()
    {
        GraphicsContext g = canvas.getGraphicsContext2D();

        g.setFill(Color.GREY);
        osc = new OscGenerator();

        // Start and control game loop
        new AnimationTimer(){
            long lastUpdate;
            public void handle (long now)
            {
                if (now > lastUpdate + 50 * 1000000)
                {
                    lastUpdate = now;
                    drawCanvas();
                }             }
        }.start();

    }
    private void drawCanvas() {

        if (clicked) {

            if (!runonce) {
                osc.SetupSine();

                runonce = true;
            }

            GraphicsContext g = canvas.getGraphicsContext2D();
            if (iE < osc.intRhytmList.size()) {
                if(isMajor)
                osc.RandomMelody(majorScala.getScale(), iE);
                else{                osc.RandomMelody(minorScala.getScale(), iE);
                }

                iE++;
            } else {
                osc.sineLineOut.stop();
            }
            g.setFill(Color.RED);

            int d = osc.getPlayingNoteValue(iE-1)/2;
            g.setFill(Color.BLACK);

            //noder
                g.fillOval(5 + iE * 5, 0 + d, 6, 6);
                g.fillRect(5 + iE * 5, 0 -12+d, 2, 15);
                if(d>110||d<70)
                    g.fillRect(2 + iE * 5, +2 +d, 10, 2);

                //nodepapir
                g.fillRect(0,75,500,1);
                g.fillRect(0,85,500,1);
                g.fillRect(0,95,500,1);
                g.fillRect(0,105,500,1);
                g.fillRect(0,115,500,1);


        }
    }




}
