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
import javafx.scene.control.ChoiceBox;


import java.util.ArrayList;

public class MainController {
    private static ArrayList<Double> chosenScales = new ArrayList<>();
    private HashTest hashTest = new HashTest();
    private ArrayList textList = new ArrayList();
    private OscGenerator osc;

    private int counter;
    private String s = "C3";
    private MajorScaleTest majorScala = new MajorScaleTest(13, hashTest.frequencyFinder(s));
    private MinorScaleTest minorScala = new MinorScaleTest(13, hashTest.frequencyFinder(s));

    private boolean runonce = false;
    private boolean clicked=false;
    private boolean isMajor;
    private double rootnote;
    private String complexity="medium complexity";


    @FXML ChoiceBox<String> choiceBox;

    @FXML
    Canvas canvas;
    @FXML
    TextFlow textFlow;

    @FXML
    TextField textField;

    @FXML

    public void playMajor() {

        s = textField.getText();
        System.out.println("playing " + s);
        complexity = choiceBox.getSelectionModel().getSelectedItem();
        System.out.println("using: "+complexity);
        majorScala = new MajorScaleTest(13,hashTest.frequencyFinder(s));
        rootnote =  majorScala.getScale().get(0);
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
        s = textField.getText();
        System.out.println("playing " + s);
        complexity = choiceBox.getSelectionModel().getSelectedItem();

        minorScala = new MinorScaleTest(13,hashTest.frequencyFinder(s));
        rootnote =  minorScala.getScale().get(0);

        isMajor = false;
        clicked = true;

    }

    @FXML
    public void btn2() {

        s = textField.getText();
        System.out.println("added " + s);

        textList.add(s);
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
                if (now > lastUpdate + 30 * 1000000)
                {
                    lastUpdate = now;
                    drawCanvas();
                }             }
        }.start();

    }
    private void drawCanvas() {
//if the button major or minor are pressed
        if (clicked) {

            if (!runonce) {
                osc.SetupSine();


                runonce = true;
            }

            GraphicsContext g = canvas.getGraphicsContext2D();
            if (counter < osc.intRhytmList.size()) {
                if(isMajor) {
                    osc.RandomMelody(majorScala.getScale(), counter,complexity);

                }
                else{  osc.RandomMelody(minorScala.getScale(), counter, complexity);
                }

            } else {
                osc.sineLineOut.stop();
                clicked=false;
            }
            g.setFill(Color.RED);

            //int d = osc.getPlayingNoteValue(counter-1);
            int d = (int) rootnote/2;
            int e = osc.getPlayingNoteNum(counter );

            //System.out.println(190 - d);

            g.setFill(Color.BLACK);

            //noder
                g.fillOval(5 + counter * 5, 187 - d-e*5, 6, 6);
                g.fillRect(5 + counter * 5, 187 -12 -d-e*5, 2, 15);
                //if(d>200||d<160)
                   // g.fillRect(2 + counter * 5, +187 -d-e*5, 10, 2);

                //nodepapir
                g.fillRect(0,75,500,1);
                g.fillRect(0,85,500,1);
                g.fillRect(0,95,500,1);
                g.fillRect(0,105,500,1);
                g.fillRect(0,115,500,1);

            counter++;

        }
    }




}
