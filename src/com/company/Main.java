package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(root,600,600);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/*
public class Main {

    static OscGenerator osc = new OscGenerator();
    public static void main(String[] args) {
        //osc.Saw();
        //osc.Sine(400,0.4);
        //osc.Square();
        //osc.redNoise();
        osc.RandomMelody();
       // osc.RandomMadness();
    }
}

*/
