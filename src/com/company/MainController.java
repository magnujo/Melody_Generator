package com.company;

import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.ChoiceBox;


import javax.imageio.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private HarmonicMinorScale harmonicMinorScale = new HarmonicMinorScale(13, hashTest.frequencyFinder(s));


    private boolean runonce = false;
    private boolean clicked = false;
    private boolean isMajor;
    private boolean isMinor;
    private boolean isHarmonicMinor;
    private double rootnote;
    private double rootnote2;
    private String complexity = "medium complexity";
    private boolean toClear;
    ArrayList<Note> notes = new ArrayList<>();


    @FXML
    ChoiceBox<String> choiceBox;

    @FXML
    ChoiceBox<String> choiceBox1;

    @FXML
    Canvas canvas;
    @FXML
    TextFlow textFlow;

    @FXML
    TextField textField;

    @FXML

    public void playButton() {
        s = textField.getText();
        System.out.println("playing " + s);
        complexity = choiceBox.getSelectionModel().getSelectedItem();
        System.out.println("using: " + complexity);

        String scaleType = choiceBox1.getSelectionModel().getSelectedItem();

        switch (scaleType){
            case "major scale":
                majorScala = new MajorScaleTest(13, hashTest.frequencyFinder(s));
                rootnote = majorScala.getScale().get(0);
                rootnote2 = hashTest.noteFinder(rootnote);
                isMajor = true;
                isMinor=false;
                isHarmonicMinor=false;
                clicked = true;
                break;
            case "minor scale":
                s = textField.getText();
                System.out.println("playing " + s);
                complexity = choiceBox.getSelectionModel().getSelectedItem();
                System.out.println("using: " + complexity);
                minorScala = new MinorScaleTest(13, hashTest.frequencyFinder(s));
                rootnote = minorScala.getScale().get(0);
                rootnote2 = hashTest.noteFinder(rootnote);
                isMinor = true;
                isMajor = false;
                isHarmonicMinor=false;
                clicked = true;
                break;
            case "harmonic minor scale":
                s = textField.getText();
                System.out.println("playing " + s);
                complexity = choiceBox.getSelectionModel().getSelectedItem();
                System.out.println("using: " + complexity);
                harmonicMinorScale = new HarmonicMinorScale(13, hashTest.frequencyFinder(s));
                rootnote = minorScala.getScale().get(0);
                rootnote2 = hashTest.noteFinder(rootnote);
                isHarmonicMinor = true;
                isMajor=false;
                isMinor=false;
                clicked = true;
                    break;

        }



    }

    @FXML

    public void playbutton() {
        clicked = false;
        osc.sineLineOut.stop();

    }

    @FXML
    public void resetButton() {

        //reset

        clicked = false;
        osc.sineLineOut.stop();
        counter = 0;
        toClear = true;

    }

    @FXML
    public void addScale() {

        s = textField.getText();
        System.out.println("added " + s);

        textList.add(s);
        chosenScales.add(hashTest.frequencyFinder(s));
        textFlow.getChildren().clear();

        for (int i = 0; i < textList.size(); i++) {
            Text text = new Text(textList.get(i) + " ");
            text.setFont(new Font(25));
            text.setFill(Color.DARKORCHID);
            textFlow.getChildren().add(text);
        }

    }

    //Button to print image
    @FXML
    public void pictureBtn() {


       // WritableImage snapshot = canvas.getScene().snapshot(null);




        //RenderedImage renderedImage = snapshot


        try {
            WritableImage snapshot = new WritableImage(600,546);
            File picFile = new File("./data/canvasPicture.png");
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", picFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void recBtn() throws FileNotFoundException {
        SampleRecorder recorder = new SampleRecorder();
        recorder.startRecording(osc);
    }

    @FXML
    public void initialize() {
        GraphicsContext g = canvas.getGraphicsContext2D();


        g.setFill(Color.GREY);
        osc = new OscGenerator();

        // Start and control game loop
        new AnimationTimer() {
            long lastUpdate;

            public void handle(long now) {
                if (now > lastUpdate + 30 * 1000000) {
                    lastUpdate = now;
                    drawCanvas();
                }
            }
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
                if (isMajor) {
                    osc.RandomMelody(majorScala.getScale(), counter, complexity,"sine");
                    notes.add(new Note(majorScala.getScale(), counter, complexity));

                } if(isMinor) {
                    osc.RandomMelody(minorScala.getScale(), counter, complexity,"sine");
                    notes.add(new Note(minorScala.getScale(), counter, complexity));

                }
                if(isHarmonicMinor){
                    osc.RandomMelody(harmonicMinorScale.getScale(), counter, complexity,"sine");
                    notes.add(new Note(harmonicMinorScale.getScale(), counter, complexity));

                }

            } else {
                osc.sineLineOut.stop();
                clicked = false;
            }
            g.setFill(Color.RED);

            int e = osc.getPlayingNoteNum(counter);

            drawNotePaper(g, e);


            counter++;

        }
    }

    private void drawNotePaper(GraphicsContext g, int e) {
        if (toClear) {
            g.clearRect(0, 0, 1000, 1000);
            toClear = false;
        }

        g.setFill(Color.BLACK);
        int row = 0;
        int offset = 0;
        int xPos;

        if (counter > 100) {
            row = 1;
            offset = 100;
        }
        if (counter > 200) {
            row = 2;
        }
        if (counter > 300) {
            row = 3;
        }
        if (counter > 400) {
            row = 4;
        }
        xPos = counter - offset * row;

        //noder
        notes.get(counter).setxPos(5 + xPos * 5);
        notes.get(counter).setyPos(215 - rootnote2 - e * 5 + row * offset);

        g.fillOval(notes.get(counter).getxPos(), notes.get(counter).getyPos(), 6, 6);
        g.fillRect(notes.get(counter).getxPos(), notes.get(counter).getyPos() - 12, 2, 15);

        //if(d>200||d<160)
        // g.fillRect(2 + counter * 5, +187 -d-e*5, 10, 2);

        //nodepapir
        g.fillRect(0, 75 + row * offset, 542, 1);
        g.fillRect(0, 85 + row * offset, 542, 1);
        g.fillRect(0, 95 + row * offset, 542, 1);
        g.fillRect(0, 105 + row * offset, 542, 1);
        g.fillRect(0, 115 + row * offset, 542, 1);

        if (counter % 15 == 0) {
            g.fillRect(counter + xPos * 5 - row * 45, 75 + row * offset, 3, 40);
        }
        g.clearRect(0, 0, 100, 20);
        g.fillText("beats: " + Integer.toString(counter), 15, 15);

        g.setFill(Color.RED);

            //kryds
        kryds(g, row, offset);

    }

    private void kryds(GraphicsContext g, int row, int offset) {
        if (isMajor) {
            ArrayList<Kryds> krydser = new ArrayList<>();

            //øverste linje = 0, for hver 10 man går op går man en linje ned.
            if (s.contains("A")) {
                krydser.add(new Kryds(g, row, offset, 15, -5));
                krydser.add(new Kryds(g, row, offset, 25, 0));
                krydser.add(new Kryds(g, row, offset, 0, 15));
            }

            if (s.contains("B")) {
                krydser.add(new Kryds(g, row, offset, 15, -5));
                krydser.add(new Kryds(g, row, offset, 0, 0));
                krydser.add(new Kryds(g, row, offset, 25, 10));
                krydser.add(new Kryds(g, row, offset, 11, 15));
                krydser.add(new Kryds(g, row, offset, 35, 25));
            }


            if (s.contains("D")) {
                krydser.add(new Kryds(g, row, offset, 15, 0));
                krydser.add(new Kryds(g, row, offset, 0, 15));
            }

            if (s.contains("E")) {
                krydser.add(new Kryds(g, row, offset, 15, -5));
                krydser.add(new Kryds(g, row, offset, 0, 0));
                krydser.add(new Kryds(g, row, offset, 25, 10));
                krydser.add(new Kryds(g, row, offset, 11, 15));

            }

            if (s.contains("F")) {
                krydser.add(new Kryds(g, row, offset, 0, 20, true));
            }
            if (s.contains("G")) {
                krydser.add(new Kryds(g, row, offset, 0, 0));
            }
            for (Kryds krydser1 : krydser) {
                krydser1.invoke();
            }

            krydser.clear();
        }
//minor
        if (!isMajor) {
            ArrayList<Kryds> krydser = new ArrayList<>();

            if (s.contains("B")) {

                krydser.add(new Kryds(g, row, offset, 0, 0));

                krydser.add(new Kryds(g, row, offset, 0, 15));

            }
            if (s.contains("D")) {

                krydser.add(new Kryds(g, row, offset, 0, 20,true));

            }


            if (s.contains("E")) {

                krydser.add(new Kryds(g, row, offset, 0, 0));

            }

            if (s.contains("F")) {
                krydser.add(new Kryds(g, row, offset, 5, 5,true));

                krydser.add(new Kryds(g, row, offset, 15, 10,true));

                krydser.add(new Kryds(g, row, offset, 0, 25,true));

                krydser.add(new Kryds(g, row, offset, 10, 30,true));
            }
            if (s.contains("G")) {

                krydser.add(new Kryds(g, row, offset, 5, 5,true));


                krydser.add(new Kryds(g, row, offset, 0, 20,true));
            }

            for (Kryds krydser1 : krydser) {
                krydser1.invoke();
            }
            krydser.clear();

        }
    }

    private class Kryds {
        private GraphicsContext g;
        private int row;
        private int offset;
        private int startX;
        private int startY;
        private boolean B; //if its a B....

        public Kryds(GraphicsContext g, int row, int offset, int startX, int startY) {
            this.g = g;
            this.row = row;
            this.offset = offset;
            this.startX = startX;
            this.startY = startY+72;
        }

        public Kryds(GraphicsContext g, int row, int offset, int startX, int startY, boolean B) {
            this.g = g;
            this.row = row;
            this.offset = offset;
            this.startX = startX;
            this.startY = startY+72;
            this.B=B;

        }

        public void invoke() {
            if(!B) {
                g.fillRect(startX + 2, startY + row * offset, 16, 2);
                g.fillRect(startX + 2, startY + row * offset + 5, 16, 2);
                g.fillRect(startX + 5 + 2, startY + row * offset - 4, 2, 17);
                g.fillRect(startX + 10 + 2, startY + row * offset - 4, 2, 17);
            }
            if(B){
                g.fillRect(startX , startY + row * offset-13, 2, 18);
                g.fillRect(startX + 2, startY + row * offset , 6, 6);
            }
        }

    }








}
