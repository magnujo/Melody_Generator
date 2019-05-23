package com.company;
import com.jsyn.unitgen.SineOscillator;

import java.util.ArrayList;


public class ChromaticScaleGenerator {

    //this method generates a chromatic scale from any starting frequency.
    //A frequency table can be found on http://pages.mtu.edu/~suits/notefreqs.html
    //This array of doubles stores the scale.
    protected ArrayList<Double> scale = new ArrayList<>();
    protected ArrayList<Double> scale2 = new ArrayList<>();


    //This is the constructor which can take a length (there are 12 notes in a chromatic octave), and a starting frequency.
    // I refer to the table mentioned above.
    public ChromaticScaleGenerator(int scalelength, double startFreq) {

        for (int i = 0; i < scalelength; i++) {
            // The frequency needs to double every \{12}12 notes (because there are \{7}7 white notes and \{5}5 black notes in each octave.)
            scale.add(i, startFreq * Math.pow(2, i / 12.0)); // Homemade equation that works? (propably inspired by http://pages.mtu.edu/~suits/NoteFreqCalcs.html)
            //scale.add(i,
        }
    }


    /**
     * Creates a chromatic scale with a equation from from http://pages.mtu.edu/~suits/NoteFreqCalcs.html)
     *
     * @param scalelength Determines the length of the chromatic scale.
     * @param startFreq Determines the root frequency of the scale, which all the frequencies are calculated out from.
     *                  This means that this number must be very precise, in order to make all the other frequencies
     *                  accurate.
     */
    public void ScaleGeneratorEquation2(int scalelength, double startFreq) {

        for (int i = 0; i < scalelength; i++) {
            // The frequency needs to double every \{12}1,2 notes (because there are \{7}7 white notes and \{5}5 black notes in each octave.)
            scale2.add(i, startFreq * Math.pow(Math.pow(2,1.0/12.0),i));
            //scale.add(i,
        }
    }

    public double CalcFreq(double rootFreq, int distanceInSemitones) {
        return rootFreq * Math.pow(Math.pow(2,1.0/12.0),distanceInSemitones);
    }


    public ArrayList<Double> getScale() {

        return scale;
    }

    public ArrayList<Double> getScale2() {

        return scale2;
    }


}

