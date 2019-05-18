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
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.imageio.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    //this is for the sample recorder object which is used to record .wav files.
    private SampleRecorder recorder = new SampleRecorder();

    //this hashmap is used to connect musical notation of scales ie. C3 to their respective frequencies, for use in the scalegenerator afterwards.
    private HashTest hashTest = new HashTest();

    //this variable is the play counter, it will go up for every note that should be played.
    private int counter;
    //This is the initial scale that gets played
    private String s;

    //scaletype
    private String scaleType;
    //set all the lengths of all scales being generated with one variable
    private int scaleLengths = 13;

    //oscillator
    private OscGenerator osc = new OscGenerator(0);

    //instancing class variable scales, with null.
    private MajorScaleTest majorScala;
    private MinorScaleTest minorScala;
    private HarmonicMinorScale harmonicMinorScale;

    //booleans that the user can manipulate through the GUI, which are input parameters for the program.
    private boolean isPlaying = false;
    private boolean isMajor;
    private boolean isMinor;
    private boolean isHarmonicMinor;
    private boolean isMuted;
    private boolean toClear;
    private boolean isRecording;

    //the root note, we need to know the root note as all other notes in a scale relate to this. This is used to place the notes on the XY axis.
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
    TextField writeField;

    @FXML

    /**the play button is where the main programme is launched, and melody is supposed to play when it is clicked. This happens when the boolean isPlaying is set to true
     * There is a switch case that depends on which scale the user chooses to play from. The correct scale will then be generated, dependant on the root note that the user has specified.
    **/
    public void playButton() {


        s = textField.getText();
        complexity = choiceBox.getSelectionModel().getSelectedItem();
         scaleType = choiceBox1.getSelectionModel().getSelectedItem();

        switch (scaleType){
            default :
            case "major scale":
                majorScala = new MajorScaleTest(scaleLengths, hashTest.frequencyFinder(s));
                rootNote = hashTest.noteFinder(majorScala.getScale().get(0));
                isMajor = true;
                isMinor=false;
                isHarmonicMinor=false;
                isPlaying = true;
                break;
            case "minor scale":
                s = textField.getText();
                complexity = choiceBox.getSelectionModel().getSelectedItem();
                minorScala = new MinorScaleTest(scaleLengths, hashTest.frequencyFinder(s));
                rootNote = hashTest.noteFinder(minorScala.getScale().get(0));
                isMinor = true;
                isMajor = false;
                isHarmonicMinor=false;
                isPlaying = true;
                break;
            case "harmonic minor scale":
                s = textField.getText();
                complexity = choiceBox.getSelectionModel().getSelectedItem();
                harmonicMinorScale = new HarmonicMinorScale(scaleLengths, hashTest.frequencyFinder(s));
                rootNote = hashTest.noteFinder(harmonicMinorScale.getScale().get(0));
                isHarmonicMinor = true;
                isMajor=false;
                isMinor=false;
                isPlaying = true;
                    break;
        }

        if (isRecording) {
            recorder.startRecording(osc);
        }
        if (!osc.synthSine.isRunning()) {
            osc.synthSine.start();
        }
    }

    /**
     * This function stops everything that is playing. It also makes sure to stop any audio that is being recorded if it is happening.
     *
     * @throws IOException
     */

    @FXML

    public void stopButton() throws IOException {

        isPlaying = false;
        osc.sineLineOut.stop();
        if (isRecording) {
            osc.synthSine.stop();
            isRecording = false;
            recorder.stopRecording(osc);
        }
    }

    /**
     * This function resets everything that needs to be reset and stops the oscillators, essentially resetting the program so the user can play something new.
     * @throws IOException
     */

    @FXML
    public void resetButton() throws IOException {

        isPlaying = false;
        osc.sineLineOut.stop();
        if (isRecording) {
            osc.synthSine.stop();
            isRecording = false;
            recorder.stopRecording(osc);
        }
        counter = 0;
        toClear = true;


    }

    /**
     * This function allows the user to write new data in textformat to the file. It also runs the RESET function.
     * @throws IOException
     */
    @FXML
    public void refresh() throws IOException {


        try {
            FileWriter writer = new FileWriter(".idea/data");
            BufferedWriter buffer = new BufferedWriter(writer);
            buffer.write(writeField.getText());
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        osc.refreshFileReader();
        resetButton();
    }


    /**
     * This function simply takes a screenshot of the canvas. Which is handy if the user wants to print out the sheets on physical paper for playing on physical instruments as well.
     */

    @FXML
    public void pictureBtn() {

        try {
            WritableImage snapshot = new WritableImage(600,546);
            WritableImage snapshot2 = canvas.getScene().snapshot(snapshot);

            File picFile = new File("./data/canvasPicture.png");
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", picFile);

            File outputFile;
            FileChooser fileChooser = new FileChooser();
            String userDir = System.getProperty("user.home");
            fileChooser.setInitialDirectory(new File(userDir+"/Desktop"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG","*.png"));
            outputFile = fileChooser.showSaveDialog(new Stage());

            if (picFile != null) {
                System.out.println("File saved as: "+outputFile.getName());
                System.out.println("File saved to: "+outputFile.getAbsolutePath());
                picFile.renameTo(outputFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This function is a button for recording audio.
     * @throws IOException
     */

    @FXML
    public void recBtn() throws IOException {
        recorder.initRecording(osc);
        isRecording = true;
    }

    /**
     * This function is what gets run first time javafx is started.
     */

    @FXML
    public void initialize() {




        GraphicsContext g = canvas.getGraphicsContext2D();


        g.setFill(Color.GREY);
        osc = new OscGenerator(0);
        osc.SetupSine();

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

    /**
     * This is the draw function where all graphics are handled. It is run 30 times every second.
     */

    private void drawCanvas() {
        GraphicsContext g = canvas.getGraphicsContext2D();

        if (toClear) {
            g.clearRect(0, 0, 1000, 1000);
            toClear = false;
        }


//if the button major or minor are pressed
        if (isPlaying) {
            complexity = choiceBox.getSelectionModel().getSelectedItem();

            isMuted= mute.isSelected();

            if(isMuted){
                osc.sineLineOut.stop();
            }

            if (counter < osc.intRhytmList.size()) {

                if (isMajor) {
                    osc.Play(majorScala.getScale(), counter, complexity,"sine",isMuted);
                    notes.add(new Note(majorScala.getScale(), counter, complexity));

                } if(isMinor) {
                    osc.Play(minorScala.getScale(), counter, complexity,"sine",isMuted);
                    notes.add(new Note(minorScala.getScale(), counter, complexity));

                }
                if(isHarmonicMinor){
                    osc.Play(harmonicMinorScale.getScale(), counter, complexity,"sine",isMuted);
                    notes.add(new Note(harmonicMinorScale.getScale(), counter, complexity));

                }

            } else {
                osc.sineLineOut.stop();
                counter=0;
                isPlaying = false;
            }
            g.setFill(Color.RED);

            int playingNoteNum = osc.getPlayingNoteNum(counter);

            drawSheet(g, playingNoteNum);


            counter++;

        }
    }

    /**
     * This function draws the sheet which holds all the notes.
     * @param g
     * @param e
     */

    private void drawSheet(GraphicsContext g, int e) {


        g.setFill(Color.BLACK);
        int row = 0;
        int offset = 0;
        int xPos;
        int xOffset=0;

        if (counter > 60) {
            row = 1;
            offset = 100;
            xOffset = 60;

        }
        if (counter > 120) {
            row = 2;
        }
        if (counter > 180) {
            row = 3;
        }
        if (counter > 240) {
            row = 4;
        }
        xPos = counter - xOffset * row;


        //notes
        notes.get(counter).setxPos(25 + xPos * 10);
        notes.get(counter).setyPos(215 - rootNote - e * 5 + row * offset);

        g.fillOval(notes.get(counter).getxPos(), notes.get(counter).getyPos(), 6, 6);
        g.fillRect(notes.get(counter).getxPos(), notes.get(counter).getyPos() - 12, 2, 15);
        g.fillRect(notes.get(counter).getxPos(), notes.get(counter).getyPos() - 14, 10, 1);

        //line under notes too high up bylines // help lines

        if(notes.get(counter).getyPos()>30+offset*row&&notes.get(counter).getyPos()<70+offset*row) {
            g.fillRect(notes.get(counter).getxPos() - 3, 66+offset*row, 12, 2);
            if(notes.get(counter).getyPos()<58+offset*row){
                g.fillRect(notes.get(counter).getxPos() - 3, 56+offset*row, 12, 2);
            }
        }
        //line under notes too far down
        if(notes.get(counter).getyPos()>118+offset*row&&notes.get(counter).getyPos()<150+offset*row) {
            g.fillRect(notes.get(counter).getxPos() - 3, 124+offset*row, 12, 2);
            if(notes.get(counter).getyPos()>130+offset*row){
                g.fillRect(notes.get(counter).getxPos() - 3, 134+offset*row, 12, 2);
            }

        }

        //if(d>200||d<160)
        // g.fillRect(2 + counter * 5, +187 -d-e*5, 10, 2);

        //nodepapir
        g.fillRect(0, 75 + row * offset, 640, 1);
        g.fillRect(0, 85 + row * offset, 640, 1);
        g.fillRect(0, 95 + row * offset, 640, 1);
        g.fillRect(0, 105 + row * offset, 640, 1);
        g.fillRect(0, 115 + row * offset, 640, 1);

        //line seperator

        for (int i = 0; i <9 ; i++) {

            g.fillRect(0+i*80, 75 + row * offset, 4, 41);

        }


        //beatcounter
        g.clearRect(0, 0, 150, 50);
        g.fillText("Notes played: " + Integer.toString(counter), 5, 15);
        g.fillText( s + " "+ scaleType, 5, 30);


        g.setFill(Color.RED);

            //sharps
        sharps(g, row, offset);

    }

    /**
     * This is a private function that only makes sense to be used in conjunction with the sheet function.
     * @param g
     * @param row
     * @param offset
     */

    private void sharps(GraphicsContext g, int row, int offset) {
        if (isMajor) {
            ArrayList<Sharp> sharps = new ArrayList<>();

            //øverste linje = 0, for hver 10 man går op går man en linje ned.
            if (s.contains("A")) {
                sharps.add(new Sharp(g, row, offset, 15, -5));
                sharps.add(new Sharp(g, row, offset, 25, 0));
                sharps.add(new Sharp(g, row, offset, 0, 15));
            }

            if (s.contains("flat")) {
                sharps.add(new Sharp(g, row, offset, 15, -5));
                sharps.add(new Sharp(g, row, offset, 0, 0));
                sharps.add(new Sharp(g, row, offset, 25, 10));
                sharps.add(new Sharp(g, row, offset, 11, 15));
                sharps.add(new Sharp(g, row, offset, 35, 25));
            }


            if (s.contains("D")) {
                sharps.add(new Sharp(g, row, offset, 15, 0));
                sharps.add(new Sharp(g, row, offset, 0, 15));
            }

            if (s.contains("E")) {
                sharps.add(new Sharp(g, row, offset, 15, -5));
                sharps.add(new Sharp(g, row, offset, 0, 0));
                sharps.add(new Sharp(g, row, offset, 25, 10));
                sharps.add(new Sharp(g, row, offset, 11, 15));

            }

            if (s.contains("F")) {
                sharps.add(new Sharp(g, row, offset, 0, 20, true));
            }
            if (s.contains("G")) {
                sharps.add(new Sharp(g, row, offset, 0, 0));
            }
            for (Sharp sharp : sharps) {
                sharp.invoke();
            }

            sharps.clear();
        }
//minor
        if (isMinor||isHarmonicMinor) {
            ArrayList<Sharp> sharps = new ArrayList<>();

            if (s.contains("B")) {

                sharps.add(new Sharp(g, row, offset, 0, 0));
                sharps.add(new Sharp(g, row, offset, 0, 15));

            }

            if (s.contains("C")) {

                sharps.add(new Sharp(g, row, offset, 5, 5,true));
                sharps.add(new Sharp(g, row, offset, 0, 20,true));
                sharps.add(new Sharp(g, row, offset, 10, 25,true));

            }


            if (s.contains("D")) {

                sharps.add(new Sharp(g, row, offset, 0, 20,true));

            }


            if (s.contains("E")) {

                sharps.add(new Sharp(g, row, offset, 0, 0));

            }

            if (s.contains("F")) {
                sharps.add(new Sharp(g, row, offset, 5, 5,true));

                sharps.add(new Sharp(g, row, offset, 15, 10,true));

                sharps.add(new Sharp(g, row, offset, 0, 25,true));

                sharps.add(new Sharp(g, row, offset, 10, 30,true));
            }
            if (s.contains("G")) {

                sharps.add(new Sharp(g, row, offset, 5, 5,true));


                sharps.add(new Sharp(g, row, offset, 0, 20,true));
            }

            for (Sharp sharp : sharps) {
                sharp.invoke();
            }
            sharps.clear();

        }
    }

    /**
     * This is a private class of the private function that is wholly dependent on the private class, sharps. A sharp, is thus an object of the type; sharp, that is added to the list of sharps. 
     */

    private class Sharp {
        private GraphicsContext g;
        private int row;
        private int offset;
        private int startX;
        private int startY;
        private boolean flat; //if its a flat....

        public Sharp(GraphicsContext g, int row, int offset, int startX, int startY) {
            this.g = g;
            this.row = row;
            this.offset = offset;
            this.startX = startX;
            this.startY = startY+72;
        }

        public Sharp(GraphicsContext g, int row, int offset, int startX, int startY, boolean flat) {
            this.g = g;
            this.row = row;
            this.offset = offset;
            this.startX = startX;
            this.startY = startY+72;
            this.flat = flat;

        }

        public void invoke() {
            if(!flat) {
                g.fillRect(startX + 2, startY + row * offset, 16, 2);
                g.fillRect(startX + 2, startY + row * offset + 5, 16, 2);
                g.fillRect(startX + 5 + 2, startY + row * offset - 4, 2, 17);
                g.fillRect(startX + 10 + 2, startY + row * offset - 4, 2, 17);
            }
            if(flat){
                g.fillRect(startX , startY + row * offset-13, 2, 18);
                g.fillRect(startX + 2, startY + row * offset , 6, 6);
            }
        }

    }








}
