package com.company;

import java.util.Arrays;

public class MajorScaleTest extends ScaleGeneratorTest{

    public MajorScaleTest(int scaleLength, double startFreq) {
        super(scaleLength, startFreq);

        //Kan muligvis gøres smartere med et loop
            scale.remove(1);
            scale.remove(2);
            scale.remove(4);
            scale.remove(5);
            scale.remove(6);
    }


}
