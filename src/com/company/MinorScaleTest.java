package com.company;

import java.util.Arrays;

public class MinorScaleTest extends ScaleGeneratorTest{

    public MinorScaleTest(int scaleLength, double startFreq) {
        super(scaleLength, startFreq);

        scale.remove(3);
        scale.remove(4);
        scale.remove(5);
        scale.remove(6);
        scale.remove(7);
    }

    public static void main(String[] args) {

        MajorScaleTest scaleGenerator = new MajorScaleTest(12, 65.41);
        System.out.println(Arrays.toString(scaleGenerator.getScale().toArray()));
    }
}
