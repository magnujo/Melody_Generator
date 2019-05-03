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

import java.util.ArrayList;
import java.util.Arrays;

public class MainController {
    public static ArrayList<Double> chosenScales = new ArrayList<>();
    HashTest hashTest = new HashTest();
    ArrayList textList = new ArrayList();
    OscGenerator osc;

    @FXML
    Canvas canvas;
    @FXML
    TextFlow textFlow;

    @FXML
    TextField textField;


    public void btn() {

        String s = textField.getText();
        System.out.println("playing " + s);
        MajorScaleTest skala = new MajorScaleTest(13,hashTest.frequencyFinder(s));

        //System.out.println(skala.getScale().size());
        //System.out.println(Arrays.toString(skala.getScale().toArray()));

       osc.RandomMelody(skala.getScale());
    }

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
        osc = new OscGenerator(g);
        //g.fillOval(5+5*5, 5, 25, 25);



        // Start and control game loop
        /*new AnimationTimer(){
            long lastUpdate;
            public void handle (long now)
            {
                if (now > lastUpdate + 50 * 1000000)
                {
                    lastUpdate = now;
                    drawCanvas();
                }             }
        }.start();
        */
    }
    private void drawCanvas() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.GREY);

        System.out.println(osc.notes.toString());

        for (int i = 0; i < osc.notes.size() ; i++) {


            g.fillOval(5+i*5, 5, 25, 25);

        }

    }




}
