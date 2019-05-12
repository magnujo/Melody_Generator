package com.company;

import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.CheckBox;


import javax.imageio.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    private HashTest hashTest = new HashTest();



    //this variable is the play counter, it will go up for every note that should be played.
    private int counter;
    //This is the initial scale that gets played
    private String s = "C3";
    //set all the lengths of all scales being generated with one variable
    private int scaleLengths = 13;

    //oscillator
    private OscGenerator osc = new OscGenerator(scaleLengths);

    //instancing class variable scales, with null.
    private MajorScaleTest majorScala;
    private MinorScaleTest minorScala;
    private HarmonicMinorScale harmonicMinorScale;

    //booleans
    private boolean runonce = false;
    private boolean clicked = false;
    private boolean isMajor;
    private boolean isMinor;
    private boolean isHarmonicMinor;
    private boolean isMuted;
    private boolean toClear;

    //the root note
    private double rootNote;

    //rhythm complexity
    private String complexity = "medium complexity";

    //arraylist of notes to be played.
    ArrayList<Note> notes = new ArrayList<>();

    @FXML
    CheckBox mute;


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
                majorScala = new MajorScaleTest(scaleLengths, hashTest.frequencyFinder(s));
                rootNote = hashTest.noteFinder(majorScala.getScale().get(0));
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
                minorScala = new MinorScaleTest(scaleLengths, hashTest.frequencyFinder(s));
                rootNote = hashTest.noteFinder(minorScala.getScale().get(0));
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
                harmonicMinorScale = new HarmonicMinorScale(scaleLengths, hashTest.frequencyFinder(s));
                rootNote = hashTest.noteFinder(harmonicMinorScale.getScale().get(0));
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



    //Button to print image
    @FXML
    public void pictureBtn() {

        try {
            WritableImage snapshot = new WritableImage(600,546);
            WritableImage snapshot2 = canvas.getScene().snapshot(snapshot);

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
        osc = new OscGenerator(scaleLengths);

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
        GraphicsContext g = canvas.getGraphicsContext2D();
        if (toClear) {
            g.clearRect(0, 0, 1000, 1000);
            toClear = false;
        }


//if the button major or minor are pressed
        if (clicked) {
            complexity = choiceBox.getSelectionModel().getSelectedItem();




            if (!runonce) {
                osc.SetupSine();


                runonce = true;
            }


            isMuted= mute.isSelected();

            if(isMuted){
                osc.sineLineOut.stop();
            }

            if (counter < osc.intRhytmList.size()) {

                if (isMajor) {
                    osc.RandomMelody(majorScala.getScale(), counter, complexity,"sine",isMuted);
                    notes.add(new Note(majorScala.getScale(), counter, complexity));

                } if(isMinor) {
                    osc.RandomMelody(minorScala.getScale(), counter, complexity,"sine",isMuted);
                    notes.add(new Note(minorScala.getScale(), counter, complexity));

                }
                if(isHarmonicMinor){
                    osc.RandomMelody(harmonicMinorScale.getScale(), counter, complexity,"sine",isMuted);
                    notes.add(new Note(harmonicMinorScale.getScale(), counter, complexity));

                }

            } else {
                osc.sineLineOut.stop();
                counter=0;
                clicked = false;
            }
            g.setFill(Color.RED);

            int e = osc.getPlayingNoteNum(counter);

            drawNotePaper(g, e);


            counter++;

        }
    }

    private void drawNotePaper(GraphicsContext g, int e) {


        g.setFill(Color.BLACK);
        int row = 0;
        int offset = 0;
        int xPos;
        int xOffset=0;

        if (counter > 50) {
            row = 1;
            offset = 100;
            xOffset = 50;

        }
        if (counter > 100) {
            row = 2;
        }
        if (counter > 150) {
            row = 3;
        }
        if (counter > 200) {
            row = 4;
        }
        xPos = counter - xOffset * row;


        //noder
        notes.get(counter).setxPos(25 + xPos * 10);
        notes.get(counter).setyPos(215 - rootNote - e * 5 + row * offset);

        g.fillOval(notes.get(counter).getxPos(), notes.get(counter).getyPos(), 6, 6);
        g.fillRect(notes.get(counter).getxPos(), notes.get(counter).getyPos() - 12, 2, 15);
        g.fillRect(notes.get(counter).getxPos(), notes.get(counter).getyPos() - 14, 10, 1);


        //if(d>200||d<160)
        // g.fillRect(2 + counter * 5, +187 -d-e*5, 10, 2);

        //nodepapir
        g.fillRect(0, 75 + row * offset, 560, 1);
        g.fillRect(0, 85 + row * offset, 560, 1);
        g.fillRect(0, 95 + row * offset, 560, 1);
        g.fillRect(0, 105 + row * offset, 560, 1);
        g.fillRect(0, 115 + row * offset, 560, 1);

        //line seperator

        for (int i = 0; i <13 ; i++) {

            g.fillRect(0+i*80, 75 + row * offset, 4, 41);

        }


        //beatcounter
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
        if (isMinor||isHarmonicMinor) {
            ArrayList<Kryds> krydser = new ArrayList<>();

            if (s.contains("B")) {

                krydser.add(new Kryds(g, row, offset, 0, 0));
                krydser.add(new Kryds(g, row, offset, 0, 15));

            }

            if (s.contains("C")) {

                krydser.add(new Kryds(g, row, offset, 5, 5,true));
                krydser.add(new Kryds(g, row, offset, 0, 20,true));
                krydser.add(new Kryds(g, row, offset, 10, 25,true));

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
