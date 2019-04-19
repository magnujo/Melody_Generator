package com.company;


import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

public class MainController {
    public static ArrayList<Double> chosenScales = new ArrayList<>();
    HashTest hashTest = new HashTest();
    ArrayList textList = new ArrayList();



    @FXML
    TextFlow textFlow;

    @FXML
    TextField textField;


    public void btn() {

        String s = textField.getText();
        System.out.println("playing " + s);
        MajorScale skala = new MajorScale(9,hashTest.frequencyFinder(s));

        OscGenerator osc = new OscGenerator();
        osc.RandomMelody(skala.getScale());

    }

    public void btn1() {
        String s = textField.getText();
        System.out.println("playing " + s);
        MinorScale skala = new MinorScale(9,hashTest.frequencyFinder(s));

        OscGenerator osc = new OscGenerator();
        osc.RandomMelody(skala.getScale());

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




}
