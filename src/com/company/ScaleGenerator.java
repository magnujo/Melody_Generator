package com.company;

import java.util.Arrays;

public class ScaleGenerator {
    //this method generates a chromatic scale from any starting frequency.
    //A frequency table can be found on http://pages.mtu.edu/~suits/notefreqs.html

    public double[] getScale() {
        return scale;
    }

    //This array of doubles stores the scale.
    protected double[] scale;

    //This is the constructor which can take a length (there are 12 notes in a chromatic octave), and a starting frequency. I refer to the table mentioned above.
    public ScaleGenerator(int scaleLength, double startFreq){

        scale = new double[scaleLength];

        for (int i = 0; i < scale.length ; i++) {
            // The frequency needs to double every \{12}12 notes (because there are \{7}7 white notes and \{5}5 black notes in each octave.)
            scale[i] = startFreq*Math.pow(2,i/12.0);
        }

    }

    public static void main(String[] args) {

        ScaleGenerator scaleGenerator = new ScaleGenerator(13,440);

        System.out.println(Arrays.toString(scaleGenerator.getScale()));
    }
}

