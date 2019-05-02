package com.company;
import java.util.ArrayList;
import java.util.Arrays;

public class MajorScale extends ScaleGenerator {
    private double[] modifiedScale;


    public MajorScale(int scaleLength, double startFreq) {

        /*ScaleLength has to be +4 because there are 4 less tones in a major scale than in a Chromatic scale,
        and it helps the user logically if we tell them they are creating a scale of just*/
        super(scaleLength+5, startFreq);

        //this.scale.remove(1,3,6,8,10);

        this.modifiedScale = new double[8];

        this.modifiedScale[0]=scale[0];
        this.modifiedScale[1]=scale[2];
        this.modifiedScale[2]=scale[4];
        this.modifiedScale[3]=scale[5];
        this.modifiedScale[4]=scale[7];
        this.modifiedScale[5]=scale[9];
        this.modifiedScale[6]=scale[11];
        this.modifiedScale[7]=scale[12];

        this.scale = modifiedScale;
    }

    public static void main(String[] args) {

        MajorScale majorScale = new MajorScale(9,261.63);

        System.out.println(Arrays.toString(majorScale.getScale()));
    }
}
