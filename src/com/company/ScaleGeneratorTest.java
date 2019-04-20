package com.company;
import java.util.ArrayList;
import java.util.Arrays;

public class ScaleGeneratorTest {

    //this method generates a chromatic scale from any starting frequency.
    //A frequency table can be found on http://pages.mtu.edu/~suits/notefreqs.html
    //This array of doubles stores the scale.
    //protected double[] scale;
    protected ArrayList<Double> scale = new ArrayList<Double>();


    //This is the constructor which can take a length (there are 12 notes in a chromatic octave), and a starting frequency.
    // I refer to the table mentioned above.
    public ScaleGeneratorTest(int scalelength, double startFreq) {
        //scale = new double[scaleLength];

        for (int i = 0; i < scalelength; i++) {
            // The frequency needs to double every \{12}12 notes (because there are \{7}7 white notes and \{5}5 black notes in each octave.)
            scale.add(i, startFreq * Math.pow(2, i / 12.0));
        }
    }

    public ArrayList<Double> getScale() {

        return scale;
    }

    public static void main(String[] args) {

        ScaleGeneratorTest scaleGenerator = new ScaleGeneratorTest(8, 440);


    }
}

