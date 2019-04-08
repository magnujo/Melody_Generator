package com.company;

public class ScaleGenerator {
    public static final double perfectE = 440;
    private static double[] scale = new double[7];

    public ScaleGenerator(){
        for (int i = 0; i < scale.length ; i++) {
            // The frequency needs to double every \{12}12 notes (because there are \{7}7 white notes and \{5}5 black notes in each octave.)
            scale[i] = 440*Math.pow(2,i/12.0);
        }

    }

    public static void main(String[] args) {

        ScaleGenerator scaleGenerator = new ScaleGenerator();

        System.out.println(scaleGenerator.scale[6]);
    }
}

