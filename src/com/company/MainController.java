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
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainController {


    public void btn() {
        MajorScale skala = new MajorScale(9,440);

        OscGenerator osc = new OscGenerator();
        osc.RandomMelody(skala.getScale());

    }

    public void btn1() {
        MinorScale skala = new MinorScale(9,440);

        OscGenerator osc = new OscGenerator();
        osc.RandomMelody(skala.getScale());


    }

    public void btn2() {


    }




}
