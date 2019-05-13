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

    private int countFast;
    private int countSlow;
    private int countElse;

    Rhythm(double bpm, double beatsPerMeasure) {
        this.rhythmValues = new double[5]; //Array of different note lengths
        this.bpm = bpm;
        this.beatsPerMeasure = beatsPerMeasure;
        this.oneBeat = milliseconds / bpm;
        this.wholeNote = oneBeat * beatsPerMeasure;
        double rhythmValue = wholeNote;
        for (int i = 0; i < rhythmValues.length ; i++) {
            rhythmValues[i] = rhythmValue;
            System.out.println("rythmValues"+"["+i+"] = "+rhythmValues[i]);
            rhythmValue = rhythmValue/2;
        }
//make a random loop

        for (int i = 0; i < 32; i++) {
            double localNoteLength = getRandomNoteLength(2,true,true);
            loop.add(localNoteLength);
        }
        System.out.println("LOOP: "+ Arrays.toString(loop.toArray()));

        System.out.println("Wholenote " +wholeNote);
        halfNote = milliseconds / bpm*2;
        System.out.println("Halfnote = "+halfNote);
        quarterNote = milliseconds / bpm;
        System.out.println("Quarternote = " +quarterNote);
        eightNote = milliseconds/bpm/2;
        System.out.println("Eightnote = "+eightNote);
        sixteenthNote = milliseconds/bpm/4;
        System.out.println("Sixteennote = "+sixteenthNote);
        System.out.println("Measure: "+getMeasure());
    }

    public ArrayList<Double> getLoop(){
        return loop;

    }

    public double getQuarterNote(){return milliseconds / bpm;}

    public double getEighthNote(){return milliseconds/bpm/2;}

    public double getMeasure(){
        //Returns 4 if the bpm is 60 and beatsPerMeasure is 4 because then one measure is 4 seconds. Milliseconds is always 60?
        return oneBeat * beatsPerMeasure; }

    public double getRandomNoteLength(int rhythmComplexity, boolean swing, boolean repetitive){ //rhythmComplexity = how many rhythmValues are created. //Speed value = how many fast notes
        // System.out.println("Random value: " +rhythmValues[random.nextInt(rhythmValues.length-1)]);
        int randomInt = random.nextInt(10);

       return rhythmValues[getRandomNumberInRange(0,3)];



/*
        double result = 0;
        switch (random.nextInt(1)) {
            case 0:
                result = eightNote;
                System.out.println("Random value: "+eightNote);
                break;
            case 1:
                result = eightNote;
                break;
            case 2:
                result = quarterNote;
                break;
            case 3:
                result = sixteenthNote;
        }
        return result;


*/

    }


    private double ComplexityAlgo(int rhythmComplexity){
        int randomInt = random.nextInt(10);


        if (rhythmComplexity<=0){
            throw new IllegalArgumentException("rhythmComplexeti needs to be from 1-3 (inclusive)");
        }
        if (rhythmComplexity>3){
            throw new IllegalArgumentException("rhythmComplexeti needs to be from 1-3 (inclusive)");
        }

        if (rhythmComplexity==2){
            if (randomInt<=9){
                System.out.println();//play loop

            }
            else {} //play random note
        }

        if (rhythmComplexity==1){} //play loop


        else {
            System.out.println(rhythmValues[random.nextInt(rhythmValues.length)]);
            return rhythmValues[random.nextInt(rhythmValues.length)];
        }


        return rhythmValues[random.nextInt(rhythmValues.length-1)];
    }






    /**
     * Method from https://www.mkyong.com/java/java-generate-random-integers-in-a-range/
     * IT will generates a random integer between min (inclusive) and max (inclusive).
     * @param min is the lowest number it will choose.
     * @param max is the highest number it will choose.
     * @return
     */

    private int getRandomNumberInRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public boolean getCounts(){
        System.out.println("Else: "+countElse);
        System.out.println("Fast: "+countFast);
        System.out.println("Slow: "+countSlow);
        return true;
    }



}
