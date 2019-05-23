package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Rhythm {

    private int milliseconds = 60; // Skal den ikke altid være 60 og for at finde hvor lang tid et slag vare ift BPM?
    private double bpm;
    private double beatsPerMeasure;
    private double measure = milliseconds / bpm * beatsPerMeasure;
    private Random random = new Random();
    private double oneBeat;
    private double quarterNote;
    private double eightNote;
    private double sixteenthNote;
    private double halfNote;
    private double wholeNote;
    private double[] rhythmValues;
    private ArrayList<Double> loop = new ArrayList<>();

    Rhythm(double bpm) {
        this.rhythmValues = new double[5]; //Array of different note lengths
        this.bpm = bpm;
        this.beatsPerMeasure = 4;
        this.oneBeat = milliseconds / bpm;
        this.wholeNote = oneBeat * beatsPerMeasure;
        double rhythmValue = wholeNote;
        for (int i = 0; i < rhythmValues.length ; i++) {
            rhythmValues[i] = rhythmValue;
            rhythmValue = rhythmValue/2;
        }
//make a random loop
        createLoop(2,2);


        halfNote = milliseconds / bpm*2;
        quarterNote = milliseconds / bpm;
        eightNote = milliseconds/bpm/2;
        sixteenthNote = milliseconds/bpm/4;
    }
    public double getHalfNote(){return halfNote;}

    public double getWholeNote(){return wholeNote;}

    public double getSixteenthNote(){return sixteenthNote;}

    public double getQuarterNote(){return milliseconds / bpm;}

    public double getEighthNote(){return milliseconds/bpm/2;}

    public double getMeasure(){
        return oneBeat * beatsPerMeasure; }

    public double getRandomNoteLength(int minRhythmValue, int maxRhythmValue){ //rhythmComplexity = how many rhythmValues are created. //Speed value = how many fast notes
        return rhythmValues[getRandomNumberInRange(minRhythmValue,maxRhythmValue)];
    }

    private boolean createLoop(int minRhythmValue, int maxRhythmValue){

        for (int i = 0; i < 32; i++) {
            double localNoteLength = getRandomNoteLength(minRhythmValue,maxRhythmValue);
            loop.add(localNoteLength);
        }
        return true;
    }

    public ArrayList<Double> getLoop(){
        return loop;
    }


    /**
     * Method from https://www.mkyong.com/java/java-generate-random-integers-in-a-range/
     * It will generates a random integer between min (inclusive) and max (inclusive).
     * @param min is the lowest number it will choose.
     * @param max is the highest number it will choose.
     * @return
     * test
     */

    private int getRandomNumberInRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
