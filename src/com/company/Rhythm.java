package com.company;

import java.util.Random;

public class Rhythm {



    private double milliseconds;
    private double bpm;
    private double pulse;
    private double measure = milliseconds / bpm * pulse;
    private Random random = new Random();
    private double quarterNote;
    private double eightNote;
    private double sixteenthNote;
    private double halfNote;

    Rhythm(double bpm, double pulse, double milliseconds) {
        this.bpm = bpm;
        this.pulse = pulse;
        this.milliseconds = milliseconds;
        quarterNote = milliseconds / bpm;
        eightNote = milliseconds/bpm/2;
        sixteenthNote = milliseconds/bpm/4;
        halfNote = milliseconds / bpm*2;

    }

    public double getQuarterNote(){return milliseconds / bpm;}

    public double getEighthNote(){return milliseconds/bpm/2;}

    public double getMeasure(){return milliseconds / bpm * pulse; }

    public double getRandomNoteLength(){
        double result = 0;
        switch (random.nextInt(1)) {
            case 0:
                result = sixteenthNote;
                break;
            case 1:
                result = eightNote;


        }
        return result;
    }

}
