package com.company;
import com.jsyn.unitgen.SineOscillator;

import java.util.ArrayList;


public class ChromaticScaleGenerator {

    //This array of doubles stores the scale.
    protected ArrayList<Double> scale = new ArrayList<>();

    /**
     * This is the constructor which can take a length (there are 12 notes in a chromatic octave), and a starting frequency.
     * Creates a chromatic scale with a equation from from http://pages.mtu.edu/~suits/NoteFreqCalcs.html)
     *
     * @param scalelength Determines the length of the chromatic scale.
     * @param startFreq Determines the root frequency of the scale, which all the frequencies are calculated out from.
     *                  This means that this number must be very precise, in order to make all the other frequencies
     *                  accurate.
     */
    public ChromaticScaleGenerator(int scalelength, double startFreq) {

        for (int i = 0; i < scalelength; i++) {
            scale.add(i, startFreq * Math.pow(Math.pow(2,1.0/12.0),i)); //See project report for equation theory
        }
    }

    public double CalcFreq(double rootFreq, int distanceInSemitones) {
        return rootFreq * Math.pow(Math.pow(2,1.0/12.0),distanceInSemitones);
    }


    public ArrayList<Double> getScale() {

        return scale;
    }


}

