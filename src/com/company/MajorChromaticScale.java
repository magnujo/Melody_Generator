package com.company;

public class MajorChromaticScale extends ChromaticScaleGenerator {
    /**Removing the unused notes in a chromatic scale turns it into a harmonic scale.
     *
     */
    public MajorChromaticScale(int scaleLength, double startFreq) {
        super(scaleLength, startFreq);
         scale.remove(1);
            scale.remove(2);
            scale.remove(4);
            scale.remove(5);
            scale.remove(6);
    }


}
