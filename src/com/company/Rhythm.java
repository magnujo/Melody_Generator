package com.company;

import java.util.Random;

public class Rhythm {



    private int milliseconds = 60; // Skal den ikke altid være 60 og for at finde hvor lang tid et slag vare ift BPM?
    private double bpm;
    private double pulse;
    private double measure = milliseconds / bpm * pulse;
    private Random random = new Random();
    private double quarterNote;
    private double eightNote;
    private double sixteenthNote;
    private double halfNote;

    Rhythm(double bpm, double pulse) {
        this.bpm = bpm;
        this.pulse = pulse;
        quarterNote = milliseconds / bpm;
        eightNote = milliseconds/bpm/2;
        sixteenthNote = milliseconds/bpm/4;
        halfNote = milliseconds / bpm*2;

    }

    public double getQuarterNote(){return milliseconds / bpm;}

    public double getEighthNote(){return milliseconds/bpm/2;}

    public double getMeasure(){
      //Returns 4 if the bpm is 60 and pulse is 4 because then one measure is 4 seconds. Milliseconds is always 60?
        return milliseconds / bpm * pulse; }

    public double getRandomNoteLength(){
        double result = 0;
        switch (random.nextInt(1)) {
            case 0:
                result = eightNote;
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
    }

}
