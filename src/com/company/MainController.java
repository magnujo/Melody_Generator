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

import javax.sound.sampled.Line;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainController {
    public static ArrayList<Double> chosenScales = new ArrayList<>();
    HashTest hashTest = new HashTest();
    ArrayList textList = new ArrayList();
    OscGenerator osc;
    int iE;
    MajorScaleTest skala;
    boolean runonce = false;
    boolean clicked=false;
  //  ArrayList rytm = osc.getIntRhytmList();

    @FXML
    Canvas canvas;
    @FXML
    TextFlow textFlow;

    @FXML
    TextField textField;



    @FXML

    public void btn() {

        String s = textField.getText();
        System.out.println("playing " + s);
        MajorScaleTest skala = new MajorScaleTest(13,hashTest.frequencyFinder(s));
            clicked = true;


        //System.out.println(skala.getScale().size());
        //System.out.println(Arrays.toString(skala.getScale().toArray()));



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


        // MinorScale skala = new MinorScale(9,hashTest.frequencyFinder(s));

      //  OscGenerator osc = new OscGenerator();
     //   osc.RandomMelody(skala.getScale());

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
        //g.fillOval(5+5*5, 5, 25, 25);



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
            String s = textField.getText();
            // System.out.println("playing " + s);
            MajorScaleTest skala = new MajorScaleTest(13, hashTest.frequencyFinder(s));

            GraphicsContext g = canvas.getGraphicsContext2D();
            if (iE < osc.intRhytmList.size()) {
                osc.RandomMelody(skala.getScale(), iE);
                iE++;
            } else {
                osc.sineLineOut.stop();
            }
            g.setFill(Color.RED);
           // System.out.println(rytm.toString());
            // System.out.println(osc.getNotes().toString());
            System.out.println(iE);

           // System.out.println(osc.getPlayingNoteValue(iE-1));

             //ArrayList A = osc.getNotes(); //
            //System.out.println(A.get(iE-1)); //

            //System.out.println(osc.getNotes().get(iE));

            int d = osc.getPlayingNoteValue(iE-1)/2;
            g.setFill(Color.BLACK);


                g.fillOval(5 + iE * 5, 0 + d, 5, 5);

                g.fillRect(0,75,500,1);
               g.fillRect(0,85,500,1);
                g.fillRect(0,95,500,1);
               g.fillRect(0,105,500,1);
                g.fillRect(0,115,500,1);

        }
    }




}
