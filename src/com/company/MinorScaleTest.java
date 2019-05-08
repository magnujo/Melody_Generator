package com.company;

import com.jsyn.unitgen.SawtoothOscillator;

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
        OscGeneratorRhythm osc = new OscGeneratorRhythm();
osc.Play(0.1);
osc.Play(0.1);

    }
}
