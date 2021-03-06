package com.company;

public class HarmonicMinorScale extends ChromaticScaleGenerator {


    /**Removing the unused notes in a chromatic scale turns it into a harmonic scale.
     *
     */
    public HarmonicMinorScale(int scaleLength, double startFreq) {
        super(scaleLength, startFreq);
        scale.remove(1);
        scale.remove(3);
        scale.remove(4);
        scale.remove(6);
        scale.remove(8);
    }


}

