package com.company;

public class MajorScale extends ScaleGenerator {

    public MajorScale(int scaleLength, double startFreq) {
        super(scaleLength, startFreq);

        //Kan muligvis gøres smartere med et loop
            scale.remove(1);
            scale.remove(2);
            scale.remove(4);
            scale.remove(5);
            scale.remove(6);
    }


}
