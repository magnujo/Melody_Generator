package com.company;

import com.jsyn.unitgen.SawtoothOscillatorBL;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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

/**Main controller class for the application
 *
 */

public class MainController {

    /*
     * Row variable. Keeps track of rows.
     */
    int row = 0;

    /*
     * Offset is used to determine how much we need to set the amount of distance in which we have to go out of line in relation to the previous ROW on the y axis.
     */
    int offset = 0;
    /*
     * xPos is used to store which position we are at on the x-axis.
     */

    /**
     * Total notes played variable. Used to place notes correctly on the x-axis.
     */

    int xTotal;

    /**this is for the sample recorder object which is used to record .wav files.
     *
     */
    private SampleRecorder recorder = new SampleRecorder();

    /**this hashmap is used to connect musical notation of scales ie. C3 to their respective frequencies, for use in the scalegenerator afterwards.
     *
     */
    private FrequencyHashMap frequencyMap = new FrequencyHashMap();

    /**this variable is the play counter, it will go up for every note that should be played.
     *
     */
    private int counter;
    /**This is the initial scale that gets played
     *
     */
    private String s;

    /**scaletype
     *
     */

    private String scaleType;
    /**set all the lengths of all scales being generated with one variable
     *
     */
    private int scaleLengths = 13;

    /**Instance of the oscillator we play from, from the JSYN API
     *
     */
    private OscGenerator osc = new OscGenerator(0);

    /**instancing class variable MajorScale, with null.
     *
     */
    private MajorScale majorScala;
    /**instancing class variable MajorScale, with null.
     *
     */
    private MinorScale minorScala;
    /**instancing class variable HarmonicMinorScale, with null.
     *
     */
    private HarmonicMinorScale harmonicMinorScale;

    //booleans that the user can manipulate through the GUI, which are input parameters for the program.

    /**
     * If we are playing right now.
     */
    private boolean isPlaying = false;
    /**
     * If we are using a major scale.
     */
    private boolean isMajor;
    /**
     * If we are using a minor scale.
     */
    private boolean isMinor;
    /**
     * If we are using a harmonic minor scale.
     */
    private boolean isHarmonicMinor;
    /**
     * Mute button. Can be useful when debugging.
     */
    private boolean isMuted;
    /**
     * A clear method that clears the canvas using the clearRect() method.
     */
    private boolean toClear;
    /**
     * Boolean to notify that we are recording.
     */
    private boolean isRecording;

    /**the root note, we need to know the root note as all other notes in a scale relate to this. This is used to place the notes on the XY axis.
     *
     */
    private double rootNote;

    /**rhythm complexity
     *
     */
    private String complexity = "medium complexity";

    /**arraylist of notes to be played.
     *
     */
    private ArrayList<Note> notes = new ArrayList<>();

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

    /**the play button is where the main programme is launched, and melody is supposed to play when it is clicked. This happens when the boolean isPlaying is set to true
     * There is a switch case that depends on which scale the user chooses to play from. The correct scale will then be generated, dependant on the root note that the user has specified.
     **/
    @FXML

    private void playButton() {


        s = textField.getText();
        complexity = choiceBox.getSelectionModel().getSelectedItem();
         scaleType = choiceBox1.getSelectionModel().getSelectedItem();

        switch (scaleType){
            default :
            case "major scale":
                majorScala = new MajorScale(scaleLengths, frequencyMap.frequencyFinder(s));
                rootNote = frequencyMap.noteFinder(majorScala.getScale().get(0));
                isMajor = true;
                isMinor=false;
                isHarmonicMinor=false;
                isPlaying = true;
                break;
            case "minor scale":
                s = textField.getText();
                complexity = choiceBox.getSelectionModel().getSelectedItem();
                minorScala = new MinorScale(scaleLengths, frequencyMap.frequencyFinder(s));
                rootNote = frequencyMap.noteFinder(minorScala.getScale().get(0));
                isMinor = true;
                isMajor = false;
                isHarmonicMinor=false;
                isPlaying = true;
                break;
            case "harmonic minor scale":
                s = textField.getText();
                complexity = choiceBox.getSelectionModel().getSelectedItem();
                harmonicMinorScale = new HarmonicMinorScale(scaleLengths, frequencyMap.frequencyFinder(s));
                rootNote = frequencyMap.noteFinder(harmonicMinorScale.getScale().get(0));
                isHarmonicMinor = true;
                isMajor=false;
                isMinor=false;
                isPlaying = true;
                    break;
        }

        if (isRecording) {
            recorder.startRecording(osc);
        }
        if (!osc.synth.isRunning()) {
            osc.synth.start();
        }
    }

    /**
     * This function stops everything that is playing. It also makes sure to stop any audio that is being recorded if it is happening.
     *
     * @throws IOException
     * ttt
     */

    @FXML

    public void stopButton() throws IOException {

        isPlaying = false;
        //osc.sineLineOut.stop();
        osc.lineOut.stop();
        if (isRecording) {
            osc.synth.stop();
            isRecording = false;
            recorder.stopRecording(osc);
        }
    }

    /**
     * This function resets everything that needs to be reset and stops the oscillators, essentially resetting the program so the user can play something new.
     * @throws IOException
     * Exception needed for recording.
     */

    @FXML
    public void resetButton() throws IOException {

        isPlaying = false;
        osc.lineOut.stop();
        if (isRecording) {
            osc.synth.stop();
            isRecording = false;
            recorder.stopRecording(osc);
        }
        counter = 0;
        toClear = true;
        xTotal =0;
        row=0;
        offset=0;

    }

    /**
     * This function allows the user to write new data in textformat to the file. It also runs the RESET function.
     * @throws IOException
     * Exception needed for recording.
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
            WritableImage snapshot = new WritableImage(660,546);
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
                try {
                    System.out.println("File saved as: " + outputFile.getName());
                    System.out.println("File saved to: " + outputFile.getAbsolutePath());
                    picFile.renameTo(outputFile);
                } catch (NullPointerException e) {
                    System.out.println("the picture was not saved");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This function is a button for recording audio.
     * @throws IOException
     * test
     */

    @FXML
    public void recBtn() throws IOException {
        recorder.initRecording(osc);
        isRecording = true;

        if (isPlaying) {
            recorder.startRecording(osc);
        }
    }

    /**
     * This function is what gets run first time javafx is started.
     */

    @FXML
    public void initialize() {

        GraphicsContext g = canvas.getGraphicsContext2D();

        g.setFill(Color.GREY);
        osc = new OscGenerator(0);
        osc.OscSetup(new SawtoothOscillatorBL());

        // Start and control game loop
        new AnimationTimer() {
            long lastUpdate;

            public void handle(long now) {
                if (now > lastUpdate + 10 * 1000000) {
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
        int randomness= 0;
        if (complexity.equals("medium complexity"))
        {
            randomness = 30;
        }
        if (complexity.equals("high complexity"))
        {
            randomness = 60;
        }
        GraphicsContext g = canvas.getGraphicsContext2D();

        /**
         * Gets ready to clear the canvas.
         */
        if (toClear) {
            g.clearRect(0, 0, 1000, 1000);
            toClear = false;
        }


//if the button major or minor are pressed
        if (isPlaying) {
            complexity = choiceBox.getSelectionModel().getSelectedItem();

            isMuted= mute.isSelected();

            //this triggers a noteplay event every time we run through the draw method. Which one that is triggered depends on which scale we are playing from. Major, minor... etc.
            if (counter < osc.intRhytmList.size()-1) {

                if (isMajor) {
                    osc.PlayLoop(majorScala.getScale(),isMuted,0.2,1,randomness,counter);
                    notes.add(new Note(majorScala.getScale(), counter, complexity));


                }
                if(isMinor) {
                    osc.PlayLoop(minorScala.getScale(),isMuted,0.2,1,randomness,counter);
                    notes.add(new Note(minorScala.getScale(), counter, complexity));

                }
                if(isHarmonicMinor){
                    osc.PlayLoop(harmonicMinorScale.getScale(),isMuted,0.2,1,randomness,counter);
                    notes.add(new Note(harmonicMinorScale.getScale(), counter, complexity));

                }

            } else {
                counter=0;
                isPlaying = false;
            }
            g.setFill(Color.RED);

            /* We do this because we need to get the exact note that we are playing. The way the programme is built the maincontroller doesn't know exactly what notes we have to play. Thats taken care off in the oscgenerator.java. This is in relation to the root note.*/
            int playingNoteNum = osc.getPlayingNoteNum(counter);

            //Actually draws the sheet and notes! Handles the visual part.
            drawSheet(g, playingNoteNum);
            counter++;

        }
    }

    /**
     * This function draws the sheet which holds all the notes. Its taking part of all the visuals on the canvas.
     * @param g
     * graphicscontext g parameter, used for canvas.
     * @param playingNoteNum
     * the notes that are playing and which need to be drawn, in relation to their root note.
     */

    private void drawSheet(GraphicsContext g, int playingNoteNum) {


        g.setFill(Color.BLACK);

        //rhythmvalue
        //  System.out.println( osc.getRhythmValue());
        int space=0;
        if(osc.getRhythmValue().equals("quarter")) {
            space = 32;
            g.setFill(Color.GREEN);
        }

        if(osc.getRhythmValue().equals("eight")) {
            space = 16;
            g.setFill(Color.BLUE);

        }

        if(osc.getRhythmValue().equals("sixteenth")) {
            space = 8;
            g.setFill(Color.RED);
        }

        //en takt er 64 pixels lang
        //taktart


        /* If we get above 120 we need to switch to the second row. etc. */
        //row switcher
        if (xTotal > 512) {
            row++;
            offset = 100;
            xTotal=0;
        }

        /*
         * Setting the xPosition. In relation to the offset and the row. Each row we get 60 pixels too far to the right in relation to the sheet so we need to correct for that by subtracting xOffset with the row.
         */

        //This is where the note objects get created.
        // X positions are set first. 10 is the amount of space between each note. 40 is the start position of the first note.
        notes.get(this.counter).setxPos(xTotal +128);
        xTotal = xTotal + space;

        //Y positions are set next. They are a bit more complex because the notes need to be able to be placed correctly on the sheets no matter which scale we are playing in.
        notes.get(this.counter).setyPos(215 - rootNote - playingNoteNum * 5 + row * offset);

        //This is where the note objects get drawn.
        double getyPos = notes.get(this.counter).getyPos();
        double getxPos = notes.get(this.counter).getxPos();

        g.fillOval(getxPos, getyPos, 6, 6);
        g.fillRect(getxPos, getyPos - 12, 2, 15);
        g.fillRect(getxPos, getyPos - 14, 10, 1);

        g.setFill(Color.BLACK);

        //line under notes too high up bylines // help lines

        if(getyPos >30+offset*row&& getyPos <70+offset*row) {
            g.fillRect(getxPos - 3, 66 + offset * row, 12, 2);
            if(getyPos <58+ offset * row){
                g.fillRect(getxPos - 3, 56+offset*row, 12, 2);
            }
        }
        //line under notes too far down
        if(getyPos >118+offset*row&& getyPos <150+offset*row) {
            g.fillRect(getxPos - 3, 124+offset*row, 12, 2);
            if(getyPos >130+offset*row){
                g.fillRect(getxPos - 3, 134+offset*row, 12, 2);
            }

        }

        // G-clef for the user to read placement of notes
        Image clef = new Image(new File("./data/clef 50p.png").toURI().toString());
            g.drawImage(clef,1,65 + row * offset);

            //measure
        Font font = new Font("Arial", 25);
        Font font2 = new Font("Arial", 12);

        g.setFont(font);
        g.fillText("4 " , 40, 95);
        g.fillText("4 " , 40, 115);

        g.setFont(font2);


        //Note paper. Its simply some lines! 5 of them. But they move down which each row.
        for (int i = 0; i <5 ; i++) {
            g.fillRect(0, 75 + row * offset+i*10, 640, 1);
        }


        //line seperator, every measure is 64 pixels wide, we add 24 though just for spacing.

        for (int i = 0; i <11 ; i++) {

            g.fillRect(0+i*128, 75 + row * offset, 4, 41);

        }

        //beatcounter
        g.clearRect(0, 0, 150, 50);
        g.fillText("Notes played: " + Integer.toString(this.counter), 5, 15);
        g.fillText( s + " "+ scaleType, 5, 30);

        g.setFill(Color.RED);

            //sharps
        sharps(g, row, offset);

    }

    /**
     * This is a private function that only makes sense to be used in conjunction with the sheet function.
     * @param g
     * Graphicscontext g
     * @param row
     * The row parameter is also needed to place the sharps on every row correctly.
     * @param offset
     * Offset on the y axis.
     * All the key signatures need to be accounted for. These are determined by their flats and sharps.
     * A description can be found here.
     * http://musictheoryfundamentals.com/MusicTheory/keySignatures.php
     */


    private void sharps(GraphicsContext g, int row, int offset) {
        //If we are playing in Major we will get the following sharps. We also reuse the user chose of key from earlier by doing a regex function on the textfield, to see what scale we are playing.
        if (isMajor) {
            //holds all the sharps/flats
            ArrayList<Sharp> sharps = new ArrayList<>();

            //øverste linje = 0, for hver 10 man går op går man en linje ned.
            if (s.contains("A")) {
                sharps.add(new Sharp(g, row, offset, 15, -5));
                sharps.add(new Sharp(g, row, offset, 25, 0));
                sharps.add(new Sharp(g, row, offset, 0, 15));
            }

            if (s.contains("B")) {
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
//we clear it afterwards so it doesn't get unessecarily long.
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
            //we clear it afterwards so it doesn't get unessecarily long.
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
            //This is simply to make placing the sharps easier. So the first line is 0. The next is 10, next 20, next 30. Makes placing the sharps and flats mentally much easier.
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
            //visual approximation of a sharp sign. (#)
            if(!flat) {
                g.fillRect(startX + 2, startY + row * offset, 16, 2);
                g.fillRect(startX + 2, startY + row * offset + 5, 16, 2);
                g.fillRect(startX + 5 + 2, startY + row * offset - 4, 2, 17);
                g.fillRect(startX + 10 + 2, startY + row * offset - 4, 2, 17);
            }
            //visual approximation of a flat sign. (b)
            if(flat){
                g.fillRect(startX , startY + row * offset-13, 2, 18);
                g.fillRect(startX + 2, startY + row * offset , 6, 6);
            }
        }

    }

}
