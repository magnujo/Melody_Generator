package com.company;

import java.util.Arrays;

public class MajorScale extends ScaleGenerator {
    private static double[] majorScale;

    public MajorScale(int scaleLength, double startFreq) {

        //ScaleLength has to be +4 because there are 4 less tones in a major scale than in a Chromatic scale, and it helps the user logically if we tell them they are creating a scale of just
        super(scaleLength+4, startFreq);

        double[] majorScale;
        this.majorScale = new double[scaleLength-1];

        MajorScale.majorScale[0]=scale[0];
        MajorScale.majorScale[1]=scale[2];
        MajorScale.majorScale[2]=scale[4];
        MajorScale.majorScale[3]=scale[5];
        MajorScale.majorScale[4]=scale[7];
        MajorScale.majorScale[5]=scale[9];
        MajorScale.majorScale[6]=scale[11];
        MajorScale.majorScale[7]=scale[12];

    }

    public static void main(String[] args) {

        MajorScale MajorScale = new MajorScale(9,16.35);

        System.out.println(Arrays.toString(majorScale));
    }
}
