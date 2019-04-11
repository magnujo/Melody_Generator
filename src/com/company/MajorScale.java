package com.company;

import java.util.Arrays;

public class MajorScale extends ScaleGenerator {
    private double[] modifiedScale;

    public MajorScale(int scaleLength, double startFreq) {

        //ScaleLength has to be +4 because there are 4 less tones in a major scale than in a Chromatic scale, and it helps the user logically if we tell them they are creating a scale of just
        super(scaleLength+4, startFreq);

        this.modifiedScale = new double[scaleLength-1];

        this.modifiedScale[0]=scale[0];
        this.modifiedScale[1]=scale[2];
        this.modifiedScale[2]=scale[4];
        this.modifiedScale[3]=scale[5];
        this.modifiedScale[4]=scale[7];
        this.modifiedScale[5]=scale[9];
        this.modifiedScale[6]=scale[11];
        this.modifiedScale[7]=scale[12];

    }

    public static void main(String[] args) {

        MajorScale majorScale = new MajorScale(9,16.35);

        System.out.println(Arrays.toString(majorScale.getScale()));
    }
}
