package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainController {
    public static ArrayList<Double> chosenScales = new ArrayList<>();
    HashTest hashTest = new HashTest();


    @FXML
TextField textField = new TextField();

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

    public void btn2() {

        String s = textField.getText();
        System.out.println("added " + s);
        HashTest hashTest = new HashTest();
        chosenScales.add(hashTest.frequencyFinder(s));

    }




}
