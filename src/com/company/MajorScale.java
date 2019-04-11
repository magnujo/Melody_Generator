package com.company;

import java.util.Arrays;

public class MajorScale extends ScaleGenerator {

    public MajorScale(int scaleLength, double startFreq) {

        //ScaleLength has to be +4 because there are 4 less tones in a major scale than in a Chromatic scale, and it helps the user logically if we tell them they are creating a scale of just
        super(scaleLength+4, startFreq);

        this.scale = new double[scaleLength-1];

        this.scale[0]=scale[0];
        this.scale[1]=scale[2];
        this.scale[2]=scale[4];
        this.scale[3]=scale[5];
        this.scale[4]=scale[7];
        this.scale[5]=scale[9];
        this.scale[6]=scale[11];
        this.scale[7]=scale[12];

    }

    public static void main(String[] args) {

        MajorScale majorScale = new MajorScale(9,16.35);

        System.out.println(Arrays.toString(majorScale.getScale()));
    }
}
