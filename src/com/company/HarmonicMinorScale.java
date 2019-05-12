package com.company;

public class HarmonicMinorScale extends ScaleGeneratorTest{



    public HarmonicMinorScale(int scaleLength, double startFreq) {
        super(scaleLength, startFreq);

        scale.remove(1);
        scale.remove(3);
        scale.remove(4);
        scale.remove(6);
        scale.remove(8);
    }


}

