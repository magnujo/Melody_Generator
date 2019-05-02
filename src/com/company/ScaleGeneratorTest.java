package com.company;
import java.util.ArrayList;
import java.util.Arrays;

public class ScaleGeneratorTest {

    //this method generates a chromatic scale from any starting frequency.
    //A frequency table can be found on http://pages.mtu.edu/~suits/notefreqs.html
    //This array of doubles stores the scale.
    //protected double[] scale;
    protected ArrayList<Double> scale = new ArrayList<Double>();
    protected ArrayList<Double> scale2 = new ArrayList<Double>();


    //This is the constructor which can take a length (there are 12 notes in a chromatic octave), and a starting frequency.
    // I refer to the table mentioned above.
    public ScaleGeneratorTest(int scalelength, double startFreq) {
        //scale = new double[scaleLength];

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
     * @param startFreq Determines the root frequency of the scale, which all the frequencies are calculated out of.
     *                  This means that this number must be very precise.
     */
    public void ScaleGeneratorEquation2(int scalelength, double startFreq) {

        for (int i = 0; i < scalelength; i++) {
            // The frequency needs to double every \{12}1,2 notes (because there are \{7}7 white notes and \{5}5 black notes in each octave.)
            scale2.add(i, startFreq * Math.pow(Math.pow(2,1.0/12.0),i));
            //scale.add(i,
        }
    }

    public double CalcFreq(double rootFreq, int distanceInSemitones) {

        //   double a = Math.pow(2,1.0/12.0); // same as the 12th square root of 2;
return rootFreq * Math.pow(Math.pow(2,1.0/12.0),distanceInSemitones);


    }


    public ArrayList<Double> getScale() {

        return scale;
    }

    public ArrayList<Double> getScale2() {

        return scale2;
    }

    public static void main(String[] args) {

        ScaleGeneratorTest scaleGenerator = new ScaleGeneratorTest(8, 440);
        System.out.println(scaleGenerator.CalcFreq(55.0,-21));

    }
}

