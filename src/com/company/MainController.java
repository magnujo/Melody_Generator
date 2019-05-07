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
    private double rootnote2;
    private String complexity="medium complexity";
    private boolean toClear;


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
        rootnote2 = hashTest.noteFinder(rootnote);

        isMajor = true;
        clicked = true;
    }
    @FXML

    public void btn3(){
        clicked = false;
        osc.sineLineOut.stop();

    }

    @FXML
    public void btn4(){

        //reset

        clicked = false;
        osc.sineLineOut.stop();
        counter=0;
        toClear=true;

    }

    @FXML

    public void btn1() {
        s = textField.getText();
        System.out.println("playing " + s);
        complexity = choiceBox.getSelectionModel().getSelectedItem();

        minorScala = new MinorScaleTest(13,hashTest.frequencyFinder(s));
        rootnote =  minorScala.getScale().get(0);
        rootnote2 = hashTest.noteFinder(rootnote);

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

            int e = osc.getPlayingNoteNum(counter);

            drawNotePaper(g, e);

            counter++;

        }
    }

    private void drawNotePaper(GraphicsContext g, int e) {
        if(toClear){g.clearRect(0,0,1000,1000);
        toClear=false;
        }

        g.setFill(Color.BLACK);
         int row=0;
         int offset=0;
         int xPos;

        if(counter>100) {
            row = 1;
            offset=100;
        }
        if(counter>200) {
            row = 2;
        }
        if(counter>300) {
            row = 3;
        }
        if(counter>400) {
            row = 4;
        }
        xPos = counter-offset*row;



        //noder
            g.fillOval(5 + xPos * 5, 215 - rootnote2 - e * 5+row*offset, 6, 6);
            g.fillRect(5 + xPos * 5, 215 - rootnote2 - 12 - e * 5+row*offset, 2, 15);

            //if(d>200||d<160)
            // g.fillRect(2 + counter * 5, +187 -d-e*5, 10, 2);

            //nodepapir
            g.fillRect(0, 75+row*offset, 500, 1);
            g.fillRect(0, 85+row*offset, 500, 1);
            g.fillRect(0, 95+row*offset, 500, 1);
            g.fillRect(0, 105+row*offset, 500, 1);
            g.fillRect(0, 115+row*offset, 500, 1);





    }


}
